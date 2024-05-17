package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.auth.ChangePasswordDto;
import com.spring.ecommerce.dto.auth.PasswordDto;
import com.spring.ecommerce.dto.auth.RefreshTokenDto;
import com.spring.ecommerce.dto.auth.SignInDto;
import com.spring.ecommerce.dto.auth.SignUpDto;
import com.spring.ecommerce.dto.auth.TokenDto;

public interface AuthService {
    TokenDto register(SignUpDto registerRequest);

    TokenDto login(SignInDto loginRequest);

    TokenDto refreshToken(RefreshTokenDto oldRefreshToken);

    String changePassword(PasswordDto changePasswordDto, String email, String authHeader, boolean isForgotPassword);

    String logout(String email);

    String active(String email, String s);
}
