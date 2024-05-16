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

    @PostMapping("/verify-email/{recipientEmail}") // step 1
    public ResponseEntity<String> verifyEmail(@PathVariable String recipientEmail) {
        return ResponseEntity.ok(mailService.sendOtpForgotPassword(recipientEmail));
    }

    // step 2
    // fe need to track client mail
//    @PostMapping("/verify-otp/{otp}/{recipientEmail}")
//    public ResponseEntity<String> verifyOtp(
//            @PathVariable("otp") Integer otp,
//            @PathVariable("recipientEmail") String recipientEmail
//    ) {
//        return ResponseEntity.ok(mailService.verifyForgotPasswordOtp(otp, recipientEmail));
//    }
}
