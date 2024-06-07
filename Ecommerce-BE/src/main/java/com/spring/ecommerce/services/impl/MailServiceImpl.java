package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.config.Constant;
import com.spring.ecommerce.dto.MailBodyDto;
import com.spring.ecommerce.models.ForgotPassword;
import com.spring.ecommerce.models.User;
import com.spring.ecommerce.repositories.ForgotPasswordRepository;
import com.spring.ecommerce.repositories.UserRepository;
import com.spring.ecommerce.services.MailService;
import com.spring.ecommerce.utils.MailBodyHtml;
import com.spring.ecommerce.utils.RandomString;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String myEmail;

    private final MailBodyHtml mailBodyHtml;

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;

    public void sendMail(MailBodyDto mailBodyDto) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
        message.setTo(mailBodyDto.recipient());
        message.setFrom(myEmail);
        message.setSubject(mailBodyDto.subject());
        message.setText(mailBodyDto.text(), true);
        javaMailSender.send(mimeMessage);
    }

    public String sendOtpForgotPassword(String recipientEmail) throws MessagingException {
        User user = userRepository.findByEmail(recipientEmail).orElseThrow();
        int otpForgotPassword = otpGenerator();

        Instant now = Instant.now();
        ForgotPassword forgotPasswordSaved = ForgotPassword.builder()
                .otp(otpForgotPassword)
                .issuedAt(now)
                .expiresAt(now.plusMillis(Constant.TIME.THIRTY_MINUTES))
                .user(user)
                .build();

        user.setForgotPassword(forgotPasswordSaved);

        String emailContent = mailBodyHtml.verifyAccountContent(
                "forgotPassword",
                Integer.toString(otpForgotPassword),
                recipientEmail,
                "Forgot Password",
                "Reset My Password"
        );

        MailBodyDto mailBodyDto = MailBodyDto.builder()
                .recipient(recipientEmail)
                .subject("Forgot Password")
                .text(emailContent)
                .build();

        sendMail(mailBodyDto);
        forgotPasswordRepository.save(forgotPasswordSaved);
        userRepository.save(user);

        return "OTP sent successfully";
    }

    public String sendVerifyAccount(String recipientEmail) throws MessagingException {
        String verificationCode = RandomString.GenerateRandomString(64);
        userRepository.updateVerificationCode(recipientEmail, verificationCode);

        String emailContent = mailBodyHtml.verifyAccountContent(
                "activeAccount",
                verificationCode,
                recipientEmail,
                "Account Verification",
                "Verify My Account"
        );
        MailBodyDto mailBodyDto = MailBodyDto.builder()
                .recipient(recipientEmail)
                .subject("Account Verification code")
                .text(emailContent)
                .build();

        sendMail(mailBodyDto);
        return "Verification Code sent successfully";
    }

    public boolean verifyForgotPasswordOtp(Integer otp, String recipientEmail) {
        User user = userRepository.findByEmail(recipientEmail).orElseThrow();
        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp, user).orElseThrow();
        forgotPasswordRepository.deleteById(forgotPassword.getId());

        return forgotPassword.getExpiresAt().isBefore(Instant.now());
    }

    @Override
    public String sendNotificationOrderApproved(String recipientEmail, String commodityCode) throws MessagingException {

        String emailContent = mailBodyHtml.verifyAccountContent(
                "orderApproved",
                commodityCode,
                recipientEmail,
                "Order Confirmation",
                "Go To Shop"
        );
        MailBodyDto mailBodyDto = MailBodyDto.builder()
                .recipient(recipientEmail)
                .subject("Order Confirmation")
                .text(emailContent)
                .build();

        sendMail(mailBodyDto);
        return "Order Confirmation mail sent successfully";
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }


}
