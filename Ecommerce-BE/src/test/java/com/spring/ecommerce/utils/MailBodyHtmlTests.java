package com.spring.ecommerce.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MailBodyHtmlTests {

    @Autowired
    private MailBodyHtml mailBodyHtml;

    @Test
    public void testVerifyAccountContent() {
        // Inject shopClientUrl for the test context
        ReflectionTestUtils.setField(mailBodyHtml, "shopClientUrl", "http://example.com");

        String type = "registration";
        String verificationCode = "123456";
        String email = "user@example.com";
        String title = "Verify Your Account";
        String buttonString = "Verify Now";

        String result = mailBodyHtml.verifyAccountContent(type, verificationCode, email, title, buttonString);

        assertTrue(result.contains("http://example.com/mail/verify?type=registration&code=123456&email=user@example.com"), "Verification URL is incorrect");
        assertTrue(result.contains("Verify Your Account"), "Email title is incorrect");
        assertTrue(result.contains("Verify Now"), "Button string is incorrect");
        assertTrue(result.contains("Thank you for being here with us."), "Body text is incorrect");
    }
}
