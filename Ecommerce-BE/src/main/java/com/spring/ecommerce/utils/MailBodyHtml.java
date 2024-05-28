package com.spring.ecommerce.utils;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailBodyHtml {

    @Value("${security.api.shop}")
    private String shopClientUrl;

    public String verifyAccountContent(String type, String verificationCode, String email, String title, String buttonString) {
        String verificationUrl = shopClientUrl + "/mail/verify?type=" + type + "&code=" + verificationCode + "&email=" + email;
        return "<div style=\"font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9;\">"
                + "<div style=\"text-align: center; padding-bottom: 20px;\">"
                + "<img src=\"https://static.ybox.vn/2019/5/4/1557336851764-1556352293823-1554338968918-1554219635299-55.png\" alt=\"Your Company Logo\" style=\"max-width: 150px;\"/>"
                + "</div>"
                + "<h2 style=\"color: #4CAF50; text-align: center;\">" + title + "</h2>"
                + "<p style=\"font-size: 16px;\">Dear user,</p>"
                + "<p style=\"font-size: 16px;\">Thank you for being here with us. To complete your process, please click the link below:</p>"
                + "<p style=\"text-align: center;\"><a href=\"" + verificationUrl + "\" style=\"color: #ffffff; background-color: #4CAF50; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;\">" + buttonString + "</a></p>"
                + "<p style=\"font-size: 16px;\">If the button above doesn't work, copy and paste the above URL into your browser.</p>"
                + "<p style=\"font-size: 16px;\">Best regards,<br>Nashtech Shop</p>"
                + "<div style=\"text-align: center; padding-top: 20px;\">"
                + "<hr style=\"border-top: 1px solid #ddd;\"/>"
                + "<p style=\"font-size: 12px; color: #777;\">&copy; 2024 Nashtech Shop. All rights reserved.<br>"
                + "<a href=\"https://www.nashtechglobal.com/\" style=\"color: #4CAF50; text-decoration: none;\">Privacy Policy</a> | "
                + "<a href=\"https://www.nashtechglobal.com/\" style=\"color: #4CAF50; text-decoration: none;\">Terms of Service</a></p>"
                + "</div>"
                + "</div>";
    }

}
