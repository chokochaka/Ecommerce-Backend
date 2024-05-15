package com.spring.ecommerce.services;

import com.spring.ecommerce.config.Constant;
import com.spring.ecommerce.dto.MailBody;
import com.spring.ecommerce.models.ForgotPassword;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repository.ForgotPasswordRepository;
import com.spring.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;

    public void sendMail(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.recipient());
        message.setSubject(mailBody.subject());
        message.setText(mailBody.text());
        javaMailSender.send(message);
    }

    public String verifyEmail(String recipientEmail) {
        User user = userRepository.findByEmail(recipientEmail).orElseThrow();
        int otpForgotPassword = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .recipient(recipientEmail)
                .subject("Forgot Password Verification OTP")
                .text("Your OTP is: " + otpForgotPassword)
                .build();
        ForgotPassword forgotPasswordSaved = ForgotPassword.builder()
                .otp(otpForgotPassword)
                .expiresAt(Instant.now().plusMillis(Constant.TIME.THIRTY_MINUTES))
                .user(user)
                .build();
        sendMail(mailBody);
        forgotPasswordRepository.save(forgotPasswordSaved);
        return "OTP sent successfully";
    }

    public String verifyOtp(Integer otp, String recipientEmail) {
        User user = userRepository.findByEmail(recipientEmail).orElseThrow();
        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp, user).orElseThrow();
        if (forgotPassword.getExpiresAt().isBefore(Instant.now())) {
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            return "OTP expired";
        }
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }


}
