package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.auth.RefreshTokenDto;
import com.spring.ecommerce.dto.auth.SignInDto;
import com.spring.ecommerce.dto.auth.SignUpDto;
import com.spring.ecommerce.dto.auth.TokenDto;
import com.spring.ecommerce.enums.RoleEnum;
import com.spring.ecommerce.models.RefreshToken;
import com.spring.ecommerce.models.Role;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repository.RoleRepository;
import com.spring.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public TokenDto register(SignUpDto registerRequest) {
        // default role is user
        Role userRole = roleRepository.findByRoleName(
                RoleEnum.ROLE_USER.getRoleName()
        ).orElseThrow();
        var user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Set.of(userRole))
                .build();
        var savedUser = userRepository.save(user);
        String accessToken = jwtService.generateToken(savedUser);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(registerRequest.getEmail());
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public TokenDto login(SignInDto loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow();

        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(loginRequest.getEmail());
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }

    public TokenDto refreshToken(RefreshTokenDto oldRefreshToken) {
        RefreshToken newRefreshToken = refreshTokenService.verifyRefreshToken(oldRefreshToken.getRefreshToken());
        User user = newRefreshToken.getUser();
        String accessToken = jwtService.generateToken(user);
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

