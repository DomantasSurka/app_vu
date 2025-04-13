package com.example.fintechapp.helpers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TextAnalyzerTest {

    // -------------------------
    // Tests for countWords()
    // -------------------------

    @Test
    public void countWords_emptyText_returnsZero() {
        assertEquals(0, TextAnalyzer.countWords(""));
    }

    @Test
    public void countWords_nullText_returnsZero() {
        assertEquals(0, TextAnalyzer.countWords(null));
    }

    @Test
    public void countWords_onlySpaces_returnsZero() {
        assertEquals(0, TextAnalyzer.countWords("     "));
    }

    @Test
    public void countWords_onlyTabs_returnsZero() {
        assertEquals(0, TextAnalyzer.countWords("\t\t\t"));
    }

    @Test
    public void countWords_normalSentence_returnsWordCount() {
        String input = "This is a simple test";
        assertEquals(5, TextAnalyzer.countWords(input));
    }

    @Test
    public void countWords_multipleSpacesBetweenWords_returnsCorrectCount() {
        String input = "This   is    spaced    text";
        assertEquals(4, TextAnalyzer.countWords(input));
    }

    @Test
    public void countWords_mixedWhitespace_returnsCorrectCount() {
        String input = "Word\twith spaces \n and tabs";
        assertEquals(5, TextAnalyzer.countWords(input));
    }

    // -------------------------
    // Tests for countCharacters()
    // -------------------------

    @Test
    public void countCharacters_nullText_returnsZero() {
        assertEquals(0, TextAnalyzer.countCharacters(null));
    }

    @Test
    public void countCharacters_emptyText_returnsZero() {
        assertEquals(0, TextAnalyzer.countCharacters(""));
    }

    @Test
    public void countCharacters_onlySpaces_returnsCorrectCount() {
        assertEquals(5, TextAnalyzer.countCharacters("     "));
    }

    @Test
    public void countCharacters_regularText_returnsCorrectCount() {
        String input = "Hello, world!";
        assertEquals(13, TextAnalyzer.countCharacters(input));
    }

    @Test
    public void countCharacters_withTabsAndNewlines_returnsCorrectCount() {
        String input = "Line1\nLine2\tEnd";
        assertEquals(15, TextAnalyzer.countCharacters(input));
    }
}
