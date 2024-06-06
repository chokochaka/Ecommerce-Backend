package com.spring.ecommerce.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
public class StringKMPTests {

    @Autowired
    private StringKMP stringKMP;

    @Test
    public void testKMPSearch_FullMatch() {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        int[] expected = {10};
        int[] result = stringKMP.KMPSearch(text, pattern);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testKMPSearch_MultipleMatches() {
        String text = "ABABDABACDABABCABABABABCABAB";
        String pattern = "ABABCABAB";
        int[] expected = {10, 19};
        int[] result = stringKMP.KMPSearch(text, pattern);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testKMPSearch_NoMatch() {
        String text = "HELLO WORLD";
        String pattern = "ABC";
        int[] expected = {};
        int[] result = stringKMP.KMPSearch(text, pattern);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testKMPSearch_EmptyText() {
        String text = "";
        String pattern = "ABC";
        int[] expected = {};
        int[] result = stringKMP.KMPSearch(text, pattern);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testKMPSearch_SingleCharacterMatch() {
        String text = "A";
        String pattern = "A";
        int[] expected = {0};
        int[] result = stringKMP.KMPSearch(text, pattern);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testKMPSearch_SingleCharacterNoMatch() {
        String text = "A";
        String pattern = "B";
        int[] expected = {};
        int[] result = stringKMP.KMPSearch(text, pattern);
        assertArrayEquals(expected, result);
    }
}
