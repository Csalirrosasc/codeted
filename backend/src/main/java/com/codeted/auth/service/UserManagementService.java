package com.codeted.auth.service;

import com.codeted.auth.dto.CreateUserRequest;
import com.codeted.auth.dto.UserSummaryResponse;
import com.codeted.auth.dto.UpdateUserRequest;

import java.util.List;
import java.util.UUID;

public interface UserManagementService {

    List<UserSummaryResponse> findAll();

    UserSummaryResponse create(CreateUserRequest request);

    UserSummaryResponse update(UUID publicId, UpdateUserRequest request);
}
