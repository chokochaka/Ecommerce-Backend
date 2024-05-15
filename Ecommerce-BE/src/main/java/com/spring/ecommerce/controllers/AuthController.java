package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.ChangePassword;
import com.spring.ecommerce.dto.auth.RefreshTokenDto;
import com.spring.ecommerce.dto.auth.SignInDto;
import com.spring.ecommerce.dto.auth.SignUpDto;
import com.spring.ecommerce.dto.auth.TokenDto;
import com.spring.ecommerce.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(
            @RequestBody SignUpDto registerRequest
    ) {
        TokenDto authResponse = authServiceImpl.register(registerRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> authenticate(
            @RequestBody SignInDto request
    ) {
        return ResponseEntity.ok(authServiceImpl.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto> refreshToken(
            @RequestBody RefreshTokenDto request
    ) {
        return ResponseEntity.ok(authServiceImpl.refreshToken(request));
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePassword changePassword,
            @PathVariable String email
    ) {
        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
            return new ResponseEntity<>("Password and Repeat Password must be same", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok(authServiceImpl.changePassword(changePassword.password(), email));
    }

}
