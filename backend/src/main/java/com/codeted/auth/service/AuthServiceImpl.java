package com.codeted.auth.service;

import com.codeted.auth.dto.AuthResponse;
import com.codeted.auth.dto.LoginRequest;
import com.codeted.auth.dto.RefreshTokenRequest;
import com.codeted.auth.dto.UserProfileResponse;
import com.codeted.auth.entity.RefreshToken;
import com.codeted.auth.entity.User;
import com.codeted.auth.mapper.AuthMapper;
import com.codeted.auth.repository.RefreshTokenRepository;
import com.codeted.auth.repository.UserRepository;
import com.codeted.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String TOKEN_TYPE = "Bearer";

    @Value("${app.security.jwt.refresh-expiration-ms}")
    private long refreshTokenExpirationMs;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final AuthMapper authMapper;

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        revokeActiveRefreshTokens(user);

        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = createRefreshToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                TOKEN_TYPE,
                jwtService.getAccessTokenExpirationMs(),
                authMapper.toProfile(user)
        );
    }

    @Override
    @Transactional
    public AuthResponse refresh(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.refreshToken())
                .orElseThrow(() -> new BadCredentialsException("Refresh token inválido"));

        if (refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new BadCredentialsException("Refresh token expirado o revocado");
        }

        User user = refreshToken.getUser();
        String accessToken = jwtService.generateAccessToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                TOKEN_TYPE,
                jwtService.getAccessTokenExpirationMs(),
                authMapper.toProfile(user)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getCurrentUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return authMapper.toProfile(user);
    }

    @Override
    @Transactional
    public void logout(RefreshTokenRequest request) {
        refreshTokenRepository.findByToken(request.refreshToken()).ifPresent(token -> {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        });
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(Instant.now().plusMillis(refreshTokenExpirationMs));
        refreshToken.setRevoked(false);
        return refreshTokenRepository.save(refreshToken);
    }

    private void revokeActiveRefreshTokens(User user) {
        refreshTokenRepository.findAllByUserAndRevokedFalse(user).forEach(token -> token.setRevoked(true));
    }
}
