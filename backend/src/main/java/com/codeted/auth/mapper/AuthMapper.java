package com.codeted.auth.mapper;

import com.codeted.auth.dto.UserProfileResponse;
import com.codeted.auth.dto.UserSummaryResponse;
import com.codeted.auth.entity.Permission;
import com.codeted.auth.entity.Role;
import com.codeted.auth.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthMapper {

    public UserProfileResponse toProfile(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getPublicId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                extractRoleNames(user),
                user.getCustomer() != null ? user.getCustomer().getPublicId() : null
        );
    }

    public UserSummaryResponse toSummary(User user) {
        return new UserSummaryResponse(
                user.getPublicId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getStatus(),
                user.getRole(),
                extractRoleNames(user),
                user.getCustomer() != null ? user.getCustomer().getPublicId() : null
        );
    }

    public Set<String> extractRoleNames(User user) {
        return user.getRoles().stream().map(Role::getName).collect(java.util.stream.Collectors.toCollection(java.util.LinkedHashSet::new));
    }

    public Set<String> extractPermissionCodes(Role role) {
        return role.getPermissions().stream().map(Permission::getCode)
                .collect(java.util.stream.Collectors.toCollection(java.util.LinkedHashSet::new));
    }
}
