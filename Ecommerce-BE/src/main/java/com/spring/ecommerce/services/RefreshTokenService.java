package com.spring.ecommerce.services;

import com.spring.ecommerce.models.RefreshToken;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repository.RefreshTokenRepository;
import com.spring.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(String email) {
        User userGetByEmail = userRepository.findByEmail(email)
                .orElseThrow();

        RefreshToken refreshToken = userGetByEmail.getRefreshToken();
        if (refreshToken == null) {
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plusMillis(7L * 24 * 60 * 60 * 1000 * 10000))
                    .user(userGetByEmail)
                    .build();
            refreshTokenRepository.save(refreshToken);
        }
        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow();
        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }
}
