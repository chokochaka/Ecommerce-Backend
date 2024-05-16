package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.auth.RefreshTokenDto;
import com.spring.ecommerce.dto.auth.SignInDto;
import com.spring.ecommerce.dto.auth.SignUpDto;
import com.spring.ecommerce.dto.auth.TokenDto;

public interface AuthService {
    TokenDto register(SignUpDto registerRequest);

    TokenDto login(SignInDto loginRequest);

    TokenDto refreshToken(RefreshTokenDto oldRefreshToken);

    String changePassword(String password, String email, String authHeader, boolean isForgotPassword);
}
