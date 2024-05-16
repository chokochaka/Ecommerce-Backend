package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.MailBodyDto;

public interface MailService {
    void sendMail(MailBodyDto mailBodyDto);

    String sendOtpForgotPassword(String recipientEmail);

    String sendVerifyAccount(String recipientEmail);

    boolean verifyForgotPasswordOtp(Integer otp, String recipientEmail);
}