package com.codeted.auth.service;

import com.codeted.auth.dto.CreateUserRequest;
import com.codeted.auth.dto.UserSummaryResponse;
import com.codeted.auth.entity.Role;
import com.codeted.auth.entity.User;
import com.codeted.auth.mapper.AuthMapper;
import com.codeted.auth.repository.RoleRepository;
import com.codeted.auth.repository.UserRepository;
import com.codeted.crm.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthMapper authMapper;

    @InjectMocks
    private UserManagementServiceImpl userManagementService;

    @Test
    void createShouldEncodePasswordAndAssignRole() {
        Role role = new Role();
        role.setName("ROLE_EDITOR");

        when(passwordEncoder.encode("secret123")).thenReturn("encoded-secret");
        when(roleRepository.findByName("ROLE_EDITOR")).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(authMapper.toSummary(any(User.class))).thenReturn(
                new UserSummaryResponse(UUID.randomUUID(), "cesar", "Cesar Salirrosas", "cesar@example.com", "active", "ROLE_EDITOR", Set.of("ROLE_EDITOR"), null)
        );

        UserSummaryResponse response = userManagementService.create(
                new CreateUserRequest("cesar", "Cesar Salirrosas", "cesar@example.com", "secret123", "active", Set.of("ROLE_EDITOR"), null)
        );

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getPassword()).isEqualTo("encoded-secret");
        assertThat(captor.getValue().getRole()).isEqualTo("ROLE_EDITOR");
        assertThat(response.primaryRole()).isEqualTo("ROLE_EDITOR");
    }
}
