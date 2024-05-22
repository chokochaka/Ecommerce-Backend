package com.spring.ecommerce.utils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StringKMP {

    // Function to build the partial match table (LPS array)
    private int[] computeLPSArray(String pattern) {
        int length = pattern.length();
        int[] lps = new int[length];
        int len = 0;
        int i = 1;

        lps[0] = 0; // LPS[0] is always 0

        // Loop to fill lps array
        while (i < length) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else { // (pattern[i] != pattern[len])
                if (len != 0) {
                    len = lps[len - 1];
                } else { // if (len == 0)
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    // KMP search algorithm that returns an array of starting indices
    public int[] KMPSearch(String text, String pattern) {
        int M = pattern.length();
        int N = text.length();

        // Create the lps array that will hold the longest prefix suffix values for pattern
        int[] lps = computeLPSArray(pattern);

        int i = 0; // index for text
        int j = 0; // index for pattern

        List<Integer> resultIndices = new ArrayList<>();

        while (i < N) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
                resultIndices.add(i - j);
                j = lps[j - 1];
            } else if (i < N && pattern.charAt(j) != text.charAt(i)) { // mismatch after j matches
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        // Convert the list to an array
        int[] resultArray = new int[resultIndices.size()];
        for (int k = 0; k < resultIndices.size(); k++) {
            resultArray[k] = resultIndices.get(k);
        }

        return resultArray;
    }
}
