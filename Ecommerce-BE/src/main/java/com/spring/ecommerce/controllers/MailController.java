package com.spring.ecommerce.controllers;

import com.spring.ecommerce.repository.UserRepository;
import com.spring.ecommerce.services.AuthService;
import com.spring.ecommerce.services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final MailService mailService;

    @PostMapping("/verify-email/{recipientEmail}")
    public ResponseEntity<String> verifyEmail(@PathVariable String recipientEmail) {
        return ResponseEntity.ok(mailService.verifyEmail(recipientEmail));
    }

    @PostMapping("/verify-otp/{otp}/{recipientEmail}") // fe need to track client mail
    public ResponseEntity<String> verifyOtp(
            @PathVariable("otp") Integer otp,
            @PathVariable("recipientEmail") String recipientEmail
    ) {
        return ResponseEntity.ok(mailService.verifyOtp(otp, recipientEmail));
    }
}
