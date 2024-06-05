package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.MailBodyDto;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendMail(MailBodyDto mailBodyDto) throws MessagingException;

    String sendOtpForgotPassword(String recipientEmail) throws MessagingException;

    String sendVerifyAccount(String recipientEmail) throws MessagingException;

    boolean verifyForgotPasswordOtp(Integer otp, String recipientEmail);
}