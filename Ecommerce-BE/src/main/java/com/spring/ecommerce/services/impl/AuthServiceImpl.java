package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.config.Constant;
import com.spring.ecommerce.dto.auth.RefreshTokenDto;
import com.spring.ecommerce.dto.auth.SignInDto;
import com.spring.ecommerce.dto.auth.SignUpDto;
import com.spring.ecommerce.dto.auth.TokenDto;
import com.spring.ecommerce.enums.RoleEnum;
import com.spring.ecommerce.models.RefreshToken;
import com.spring.ecommerce.models.Role;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.RefreshTokenRepository;
import com.spring.ecommerce.repositories.RoleRepository;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtServiceImpl jwtServiceImpl;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenDto register(SignUpDto signUpDto) {
        // default role is USER
        Role userRole = roleRepository.findByRoleName(
                RoleEnum.ROLE_USER.getRoleName()).orElseThrow();

        User savedUser = userRepository.save(User.builder()
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .roles(Set.of(userRole))
                .build());
        String accessToken = jwtServiceImpl.generateToken(savedUser);
        RefreshToken refreshToken = generateRefreshToken(savedUser.getEmail());

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public TokenDto login(SignInDto signInDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getEmail(),
                        signInDto.getPassword()
                )
        );
        var user = userRepository.findByEmail(signInDto.getEmail())
                .orElseThrow();

        String accessToken = jwtServiceImpl.generateToken(user);
        RefreshToken refreshToken = replaceRefreshToken(signInDto.getEmail());

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }

    public TokenDto refreshToken(RefreshTokenDto refreshTokenDto) {
        RefreshToken newRefreshToken = verifyRefreshToken(refreshTokenDto.getRefreshToken());
        User user = newRefreshToken.getUser();
        String accessToken = jwtServiceImpl.generateToken(user);
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getRefreshToken())
                .build();
    }


    public String changePassword(String password, String email, String authHeader, boolean isForgotPassword) {
        if (!isForgotPassword) { // change password actively by user
            String emailFromJwt = jwtServiceImpl.extractUsername(authHeader.substring(7));
            if (!Objects.equals(emailFromJwt, email)) {
                throw new RuntimeException("Bad Authentication");
            }
        }
        userRepository.updatePassword(email, passwordEncoder.encode(password));
        return "Password changed successfully";
    }

    //    Start utils function
    private RefreshToken generateRefreshToken(String email) {
        User userGetByEmail = userRepository.findByEmail(email)
                .orElseThrow();

        RefreshToken refreshToken = userGetByEmail.getRefreshToken();
        if (refreshToken == null) {
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plusMillis(Constant.TIME.FOURTEEN_DAYS))
                    .user(userGetByEmail)
                    .build();
            refreshTokenRepository.save(refreshToken);
        }
        return refreshToken;
    }

    private RefreshToken replaceRefreshToken(String email) {
        User userGetByEmail = userRepository.findByEmail(email)
                .orElseThrow();

        refreshTokenRepository.deleteByUser(userGetByEmail);
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(Constant.TIME.FOURTEEN_DAYS))
                .user(userGetByEmail)
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    private RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow();
        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }
}

