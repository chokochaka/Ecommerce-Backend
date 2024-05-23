package com.spring.ecommerce.utils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* use for search product by variation
String text = "ABABDABACDABABCABAB";
String pattern = "AB";
=> [0, 2, 5, 10, 12, 15, 17]
- Link Test Algo: https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/solutions/744624/boyer-moore-substring-search-using-both-bad-character-and-good-suffix-heuristics-explained/
 + extremely large cases included, can test both BoyerMoore and KMP
*/
@Service
public class StringBoyerMoore {

    private static final int ALPHABET_SIZE = 256;

    // Function to create the bad character heuristic table
    private int[] preprocessBadCharacter(String pattern) {
        int m = pattern.length();
        int[] badChar = new int[ALPHABET_SIZE];

        // Initialize all occurrences as -1
        Arrays.fill(badChar, -1);

        // Fill the actual value of the last occurrence of a character in the pattern
        for (int i = 0; i < m; i++) {
            badChar[pattern.charAt(i)] = i;
        }

        return badChar;
    }

    // Boyer-Moore search algorithm that returns an array of starting indices
    public int[] BMSearch(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        int[] badChar = preprocessBadCharacter(pattern);

        List<Integer> resultIndices = new ArrayList<>();

        int s = 0; // s is the shift of the pattern with respect to text

        while (s <= (n - m)) {
            int j = m - 1;

            // Keep reducing index j of pattern while characters of pattern and text are matching at this shift s
            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j)) {
                j--;
            }

            // If the pattern is present at current shift, then index j will become -1 after the above loop
            if (j < 0) {
                resultIndices.add(s);

                // Shift the pattern so that the next character in text aligns with the last occurrence of it in pattern
                s += (s + m < n) ? m - badChar[text.charAt(s + m)] : 1;
            } else {
                // Shift the pattern so that the bad character in text aligns with the last occurrence of it in pattern
                s += Math.max(1, j - badChar[text.charAt(s + j)]);
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
