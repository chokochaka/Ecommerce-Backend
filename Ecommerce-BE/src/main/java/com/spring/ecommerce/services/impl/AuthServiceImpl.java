package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.auth.RefreshTokenDto;
import com.spring.ecommerce.dto.auth.SignInDto;
import com.spring.ecommerce.dto.auth.SignUpDto;
import com.spring.ecommerce.dto.auth.TokenDto;
import com.spring.ecommerce.enums.RoleEnum;
import com.spring.ecommerce.models.RefreshToken;
import com.spring.ecommerce.models.Role;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.RoleRepository;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtServiceImpl jwtServiceImpl;
    private final RefreshTokenServiceImpl refreshTokenServiceImpl;

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
        RefreshToken refreshToken = refreshTokenServiceImpl.generateRefreshToken(savedUser.getEmail());

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
        RefreshToken refreshToken = refreshTokenServiceImpl.generateRefreshToken(signInDto.getEmail());
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }

    public TokenDto refreshToken(RefreshTokenDto refreshTokenDto) {
        RefreshToken newRefreshToken = refreshTokenServiceImpl.verifyRefreshToken(refreshTokenDto.getRefreshToken());
        User user = newRefreshToken.getUser();
        String accessToken = jwtServiceImpl.generateToken(user);
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getRefreshToken())
                .build();
    }

    public String changePassword(String password, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        userRepository.updatePassword(encodedPassword, email);
        return "Password changed successfully";
    }
}

