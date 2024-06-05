package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.auth.ActiveAccountDto;
import com.spring.ecommerce.dto.auth.ForgotPasswordDto;
import com.spring.ecommerce.dto.auth.RefreshTokenDto;
import com.spring.ecommerce.dto.auth.SignInDto;
import com.spring.ecommerce.dto.auth.SignUpDto;
import com.spring.ecommerce.dto.auth.TokenDto;
import com.spring.ecommerce.services.AuthService;
import com.spring.ecommerce.services.MailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;

    @PostMapping(value = {"/register", "signup"})
    public ResponseEntity<TokenDto> register(
            @Valid @RequestBody SignUpDto registerRequest
    ) throws MessagingException {
        TokenDto authResponse = authService.register(registerRequest);
        mailService.sendVerifyAccount(registerRequest.getEmail());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(value = {"/login", "signin"})
    public ResponseEntity<TokenDto> login(
            @Valid @RequestBody SignInDto loginRequest
    ) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout/{email}")
    public ResponseEntity<String> logout(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(authService.logout(email));
    }

    @PostMapping("/active")
    public ResponseEntity<String> active(
            @Valid @RequestBody ActiveAccountDto activeAccountDto
    ) {
        return ResponseEntity.ok(authService.active(activeAccountDto.email(), activeAccountDto.verificationCode()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto> refreshToken(
            @Valid @RequestBody RefreshTokenDto refreshTokenDto
    ) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenDto));
    }

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<String> forgotPassword(
            @Valid @RequestBody ForgotPasswordDto forgotPasswordDto,
            @PathVariable String email
    ) {
        return ResponseEntity.ok(authService.changePassword(forgotPasswordDto, email, "", true));
    }

}
