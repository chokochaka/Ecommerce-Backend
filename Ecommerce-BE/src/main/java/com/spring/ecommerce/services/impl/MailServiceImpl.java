package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.config.Constant;
import com.spring.ecommerce.dto.MailBodyDto;
import com.spring.ecommerce.models.ForgotPassword;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.ForgotPasswordRepository;
import com.spring.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;

    public void sendMail(MailBodyDto mailBodyDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBodyDto.recipient());
        message.setFrom(Constant.MY_EMAIL);
        message.setSubject(mailBodyDto.subject());
        message.setText(mailBodyDto.text());
        javaMailSender.send(message);
    }

    public String sendOtpForgotPassword(String recipientEmail) {
        User user = userRepository.findByEmail(recipientEmail).orElseThrow();
        int otpForgotPassword = otpGenerator();

        MailBodyDto mailBodyDto = MailBodyDto.builder()
                .recipient(recipientEmail)
                .subject("Forgot Password Verification OTP")
                .text("Your OTP is: " + otpForgotPassword)
                .build();

        Instant now = Instant.now();
        ForgotPassword forgotPasswordSaved = ForgotPassword.builder()
                .otp(otpForgotPassword)
                .issuedAt(now)
                .expiresAt(now.plusMillis(Constant.TIME.THIRTY_MINUTES))
                .user(user)
                .build();
        sendMail(mailBodyDto);
        forgotPasswordRepository.save(forgotPasswordSaved);

        return "OTP sent successfully";
    }

    public boolean verifyForgotPasswordOtp(Integer otp, String recipientEmail) {
        User user = userRepository.findByEmail(recipientEmail).orElseThrow();
        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp, user).orElseThrow();
        forgotPasswordRepository.deleteById(forgotPassword.getId());

        return !forgotPassword.getExpiresAt().isBefore(Instant.now());
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }


}
