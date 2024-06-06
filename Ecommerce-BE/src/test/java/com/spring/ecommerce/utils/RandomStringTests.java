package com.spring.ecommerce.utils;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomStringTests {

    @Test
    public void testGenerateRandomString_Length() {
        int length = 10;
        String randomString = RandomString.GenerateRandomString(length);
        assertEquals(length, randomString.length());
    }

    @Test
    public void testGenerateRandomString_CharacterSet() {
        int length = 100;
        String randomString = RandomString.GenerateRandomString(length);
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (char c : randomString.toCharArray()) {
            assertTrue(charSet.indexOf(c) >= 0, "Character " + c + " is not in the allowed character set.");
        }
    }

    @Test
    public void testGenerateRandomString_Uniqueness() {
        int length = 10;
        Set<String> generatedStrings = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            String randomString = RandomString.GenerateRandomString(length);
            assertTrue(generatedStrings.add(randomString), "Random string is not unique: " + randomString);
        }
    }

    @Test
    public void testGenerateRandomString_DifferentLengths() {
        for (int length = 1; length <= 100; length++) {
            String randomString = RandomString.GenerateRandomString(length);
            assertEquals(length, randomString.length());
        }
    }

    @Test
    public void testGenerateRandomString_DifferentCalls() {
        int length = 10;
        String randomString1 = RandomString.GenerateRandomString(length);
        String randomString2 = RandomString.GenerateRandomString(length);
        assertNotEquals(randomString1, randomString2, "Random strings should not be the same");
    }
}
