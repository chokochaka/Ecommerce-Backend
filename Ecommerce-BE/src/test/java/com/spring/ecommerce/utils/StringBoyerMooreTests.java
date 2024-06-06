package com.spring.ecommerce.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
public class StringBoyerMooreTests {

    @Autowired
    private StringBoyerMoore stringBoyerMoore;

    @Test
    public void testBMSearch_FullMatch() {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        int[] expected = {10};
        int[] result = stringBoyerMoore.BMSearch(text, pattern);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBMSearch_NoMatch() {
        String text = "HELLO WORLD";
        String pattern = "ABC";
        int[] expected = {};
        int[] result = stringBoyerMoore.BMSearch(text, pattern);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBMSearch_EmptyText() {
        String text = "";
        String pattern = "ABC";
        int[] expected = {};
        int[] result = stringBoyerMoore.BMSearch(text, pattern);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBMSearch_SingleCharacterMatch() {
        String text = "A";
        String pattern = "A";
        int[] expected = {0};
        int[] result = stringBoyerMoore.BMSearch(text, pattern);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBMSearch_SingleCharacterNoMatch() {
        String text = "A";
        String pattern = "B";
        int[] expected = {};
        int[] result = stringBoyerMoore.BMSearch(text, pattern);
        assertArrayEquals(expected, result);
    }
}
