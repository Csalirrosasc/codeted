package com.codeted.security;

import com.codeted.auth.entity.User;
import com.codeted.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Stream.concat(
                                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())),
                                user.getRoles().stream().flatMap(role -> role.getPermissions().stream())
                                        .map(permission -> new SimpleGrantedAuthority(permission.getCode()))
                        )
                        .distinct()
                        .toList()
        );
    }
}
