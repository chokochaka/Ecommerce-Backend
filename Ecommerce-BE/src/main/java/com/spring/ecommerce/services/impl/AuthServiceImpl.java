package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.config.Constant;
import com.spring.ecommerce.dto.auth.ForgotPasswordDto;
import com.spring.ecommerce.dto.auth.PasswordDto;
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
import com.spring.ecommerce.services.JWTService;
import com.spring.ecommerce.services.MailService;
import com.spring.ecommerce.utils.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;
    private final MailService mailService;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenDto register(SignUpDto signUpDto) {
        // default role is USER
        Role userRole = roleRepository.findByRoleName(
                RoleEnum.ROLE_USER.getRoleName()).orElseThrow();

        User savedUser = userRepository.save(User.builder()
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .verificationCode(RandomString.GenerateRandomString(64))
                .enabled(false)
                .roles(Set.of(userRole))
                .build());
        String accessToken = jwtService.generateToken(savedUser);
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

        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = replaceRefreshToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }

    public String logout(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow();
        refreshTokenRepository.deleteByUser(user);
        return "Logout successfully";
    }

    public String active(String email, String verificationCode) {
        User user = userRepository.findByEmail(email)
                .orElseThrow();
        if (!Objects.equals(user.getVerificationCode(), verificationCode)) {
            throw new RuntimeException("Invalid verification code");
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "Account activated successfully";
    }

    public TokenDto refreshToken(RefreshTokenDto refreshTokenDto) {
        RefreshToken newRefreshToken = verifyRefreshToken(refreshTokenDto.getRefreshToken());
        User user = newRefreshToken.getUser();
        String accessToken = jwtService.generateToken(user);
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getRefreshToken())
                .build();
    }


    public String changePassword(PasswordDto passwordDto, String email, String authHeader, boolean isForgotPassword) {
        if (!Objects.equals(passwordDto.password(), passwordDto.repeatPassword())) {
            throw new RuntimeException("Password and Repeat Password must be same");
        }
        if (!isForgotPassword) { // change password actively by user
            String emailFromJwt = jwtService.extractUsername(authHeader.substring(7));
            if (!Objects.equals(emailFromJwt, email)) {
                throw new RuntimeException("Bad Authentication");
            }
        }
        if (passwordDto instanceof ForgotPasswordDto forgotPasswordDto) {
            // Validate the OTP
            if (mailService.verifyForgotPasswordOtp(forgotPasswordDto.otp(), email)) {
                throw new RuntimeException("Invalid OTP");
            }
        }

        userRepository.updatePassword(email, passwordEncoder.encode(passwordDto.password()));
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

    private RefreshToken replaceRefreshToken(User user) {

        refreshTokenRepository.deleteByUser(user);
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(Constant.TIME.FOURTEEN_DAYS))
                .user(user)
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

