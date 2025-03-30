package com.example.fintechapp.helpers;

public class TextAnalyzer {
    public static int countWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        return text.trim().split("\\s+").length;
    }

    public static int countCharacters(String text) {
        return text != null ? text.length() : 0;
    }
}
