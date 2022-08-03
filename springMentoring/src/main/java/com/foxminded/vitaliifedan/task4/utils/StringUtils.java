package com.foxminded.vitaliifedan.task4.utils;

public class StringUtils {

    private StringUtils() {
    }

    public static String assembleString(int countSymbols, char symbol) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < countSymbols; i++) {
            string.append(symbol);
        }
        return string.toString();
    }
}
