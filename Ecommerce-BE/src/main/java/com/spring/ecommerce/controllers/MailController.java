package com.spring.ecommerce.controllers;

import com.spring.ecommerce.services.MailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
@Tag(name = "Mail", description = "Mail API")
public class MailController {
    private final MailService mailService;

    @PostMapping("/send-forgot-password/{recipientEmail}") // step 1
    public ResponseEntity<String> sendOtpForgotPassword(@PathVariable String recipientEmail) {
        return ResponseEntity.ok(mailService.sendOtpForgotPassword(recipientEmail));
    }

    @PostMapping("/send-verify-account/{recipientEmail}") // step 1
    public ResponseEntity<String> sendVerifyAccount(@PathVariable String recipientEmail) {
        return ResponseEntity.ok(mailService.sendVerifyAccount(recipientEmail));
    }
}
