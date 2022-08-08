package com.foxminded.vitaliifedan.task5;

import java.util.HashMap;
import java.util.Map;

public class BasicCharCounter implements CharCounter {

    @Override
    public Map<Character, Integer> count(String text) {

        if (text == null) {
            throw new IllegalArgumentException("'text' must be String, but was get null");
        }

        Map<Character, Integer> countUniqueCharacter = new HashMap<>();
        String[] words = text.toLowerCase().split("");
        for (String word : words) {
            for (Character c : word.toCharArray()) {
                if (countUniqueCharacter.containsKey(c)) {
                    countUniqueCharacter.put(c, countUniqueCharacter.get(c) + 1);
                } else {
                    countUniqueCharacter.put(c, 1);
                }
            }
        }
        return countUniqueCharacter;
    }

}
