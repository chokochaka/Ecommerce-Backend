package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.auth.RefreshTokenDto;
import com.spring.ecommerce.dto.auth.SignInDto;
import com.spring.ecommerce.dto.auth.SignUpDto;
import com.spring.ecommerce.dto.auth.TokenDto;
import com.spring.ecommerce.models.RefreshToken;
import com.spring.ecommerce.services.AuthService;
import com.spring.ecommerce.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(
            @RequestBody SignUpDto registerRequest
    ) {
        TokenDto authResponse = authService.register(registerRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> authenticate(
            @RequestBody SignInDto request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto> refreshToken(
            @RequestBody RefreshTokenDto request
    ) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

}
