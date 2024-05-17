package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.MailBodyDto;

public interface MailService {
    void sendMail(MailBodyDto mailBodyDto);

    // The mail service just has only one task sending emails. User service will prepare data.
    // TODO: fix later
    String sendOtpForgotPassword(String recipientEmail);

    String sendVerifyAccount(String recipientEmail);

    boolean verifyForgotPasswordOtp(Integer otp, String recipientEmail);
}