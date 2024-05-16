package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.auth.*;
import com.spring.ecommerce.services.AuthService;
import com.spring.ecommerce.services.impl.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MailServiceImpl mailService;

    @PostMapping(value = {"/register", "signup"})
    public ResponseEntity<TokenDto> register(
            @RequestBody SignUpDto registerRequest
    ) {
        TokenDto authResponse = authService.register(registerRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(value = {"/login", "signin"})
    public ResponseEntity<TokenDto> login(
            @RequestBody SignInDto request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout/{email}")
    public ResponseEntity<String> logout(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(authService.logout(email));
    }

    @PostMapping("/active")
    public ResponseEntity<String> active(
            @RequestBody ActiveAccountDto activeAccountDto
    ) {
        return ResponseEntity.ok(authService.active(activeAccountDto.email(), activeAccountDto.verificationCode()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto> refreshToken(
            @RequestBody RefreshTokenDto refreshTokenDto
    ) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenDto));
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<String> changePassword(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ChangePasswordDto changePasswordDto,
            @PathVariable String email
    ) {
        if (!Objects.equals(changePasswordDto.password(), changePasswordDto.repeatPassword())) {
            return new ResponseEntity<>("Password and Repeat Password must be same", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok(authService.changePassword(changePasswordDto.password(), email, authHeader, false));
    }

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordDto forgotPasswordDto,
            @PathVariable String email
    ) {
        if (!Objects.equals(forgotPasswordDto.password(), forgotPasswordDto.repeatPassword())) {
            return new ResponseEntity<>("Password and Repeat Password must be same", HttpStatus.EXPECTATION_FAILED);
        }
        if (!mailService.verifyForgotPasswordOtp(forgotPasswordDto.otp(), email)) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
        return ResponseEntity.ok(authService.changePassword(forgotPasswordDto.password(), email, "", true));
    }

}
