package com.codeted.auth.service;

import com.codeted.auth.dto.AuthResponse;
import com.codeted.auth.dto.LoginRequest;
import com.codeted.auth.dto.RefreshTokenRequest;
import com.codeted.auth.dto.UserProfileResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse refresh(RefreshTokenRequest request);

    UserProfileResponse getCurrentUserProfile();

    void logout(RefreshTokenRequest request);
}
