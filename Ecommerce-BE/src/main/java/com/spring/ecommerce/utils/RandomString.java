package com.spring.ecommerce.utils;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomString {
    public static String GenerateRandomString(int length) {
        // Use SecureRandom for cryptographically secure random generation
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charSet.length());
            sb.append(charSet.charAt(index));
        }
        return sb.toString();
    }
}
