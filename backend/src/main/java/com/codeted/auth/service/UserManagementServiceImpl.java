package com.codeted.auth.service;

import com.codeted.auth.dto.CreateUserRequest;
import com.codeted.auth.dto.UserSummaryResponse;
import com.codeted.auth.dto.UpdateUserRequest;
import com.codeted.auth.entity.Role;
import com.codeted.auth.entity.User;
import com.codeted.auth.mapper.AuthMapper;
import com.codeted.auth.repository.RoleRepository;
import com.codeted.auth.repository.UserRepository;
import com.codeted.crm.entity.Customer;
import com.codeted.crm.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;

    @Override
    public List<UserSummaryResponse> findAll() {
        return userRepository.findAll().stream().map(authMapper::toSummary).toList();
    }

    @Override
    @Transactional
    public UserSummaryResponse create(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setStatus(request.status());
        Set<Role> roles = resolveRoles(request.roleNames());
        user.setRoles(roles);
        user.setRole(roles.stream().findFirst().map(Role::getName).orElse("ROLE_CLIENT"));
        user.setCustomer(resolveCustomer(request.customerPublicId()));
        return authMapper.toSummary(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserSummaryResponse update(UUID publicId, UpdateUserRequest request) {
        User user = userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setStatus(request.status());
        Set<Role> roles = resolveRoles(request.roleNames());
        user.setRoles(roles);
        user.setRole(roles.stream().findFirst().map(Role::getName).orElse(user.getRole()));
        user.setCustomer(resolveCustomer(request.customerPublicId()));
        return authMapper.toSummary(userRepository.save(user));
    }

    private Set<Role> resolveRoles(Set<String> roleNames) {
        Set<Role> roles = new LinkedHashSet<>();
        if (roleNames == null || roleNames.isEmpty()) {
            Role clientRole = roleRepository.findByName("ROLE_CLIENT")
                    .orElseThrow(() -> new EntityNotFoundException("Rol base no encontrado"));
            roles.add(clientRole);
            return roles;
        }

        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado: " + roleName));
            roles.add(role);
        }

        return roles;
    }

    private Customer resolveCustomer(UUID customerPublicId) {
        if (customerPublicId == null) {
            return null;
        }

        return customerRepository.findByPublicId(customerPublicId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
    }
}
