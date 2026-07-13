package com.codeted.auth.service;

import com.codeted.auth.dto.RoleResponse;
import com.codeted.auth.dto.UpdateRolePermissionsRequest;

import java.util.List;
import java.util.UUID;

public interface RoleManagementService {

    List<RoleResponse> findAll();

    RoleResponse updatePermissions(UUID publicId, UpdateRolePermissionsRequest request);
}
