package com.foxminded.vitaliifedan.task5.formatters;

import java.util.Map;

public class BasicFormat implements Formatter {

    @Override
    public String apply(Map<Character, Integer> characters) {

        if (characters == null) {
            throw new IllegalArgumentException("'characters' must be Map<Character, Integer>, but was get null");
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : characters.entrySet()) {
            String row = String.format("\"%s\" - %d", entry.getKey(), entry.getValue());
            result.append(row).append("\n");
        }
        return result.toString();
    }

}
