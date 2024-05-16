package com.spring.ecommerce.controllers;

import com.spring.ecommerce.services.impl.MailServiceImpl;
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
    private final MailServiceImpl mailService;

    @PostMapping("/send-forgot-password/{recipientEmail}") // step 1
    public ResponseEntity<String> sendOtpForgotPassword(@PathVariable String recipientEmail) {
        return ResponseEntity.ok(mailService.sendOtpForgotPassword(recipientEmail));
    }
}
