package com.codeted.auth.service;

import com.codeted.auth.dto.PermissionResponse;
import com.codeted.auth.dto.RoleResponse;
import com.codeted.auth.dto.UpdateRolePermissionsRequest;
import com.codeted.auth.entity.Permission;
import com.codeted.auth.entity.Role;
import com.codeted.auth.repository.PermissionRepository;
import com.codeted.auth.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleManagementServiceImpl implements RoleManagementService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public List<RoleResponse> findAll() {
        return roleRepository.findAllByOrderByNameAsc().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public RoleResponse updatePermissions(UUID publicId, UpdateRolePermissionsRequest request) {
        Role role = roleRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
        Set<Permission> permissions = new LinkedHashSet<>();
        List<Permission> allPermissions = permissionRepository.findAll();

        for (String permissionCode : request.permissionCodes()) {
            Permission permission = allPermissions.stream()
                    .filter(item -> item.getCode().equals(permissionCode))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Permiso no encontrado: " + permissionCode));
            permissions.add(permission);
        }

        role.setPermissions(permissions);
        return toResponse(roleRepository.save(role));
    }

    private RoleResponse toResponse(Role role) {
        return new RoleResponse(
                role.getPublicId(),
                role.getName(),
                role.getDescription(),
                role.isSystemRole(),
                role.getPermissions().stream()
                        .map(permission -> new PermissionResponse(
                                permission.getPublicId(),
                                permission.getCode(),
                                permission.getModule(),
                                permission.getDescription()
                        ))
                        .collect(java.util.stream.Collectors.toCollection(java.util.LinkedHashSet::new))
        );
    }
}
