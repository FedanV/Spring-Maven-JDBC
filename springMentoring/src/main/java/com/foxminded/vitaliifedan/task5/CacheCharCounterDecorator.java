package com.foxminded.vitaliifedan.task5;

import com.foxminded.vitaliifedan.task5.caches.Cache;

import java.util.Map;

public class CacheCharCounterDecorator implements CharCounter {

    private final Cache<String, Map<Character, Integer>> cache;
    private final CharCounter charCounter;

    public CacheCharCounterDecorator(CharCounter charCounter, Cache<String, Map<Character, Integer>> cache) {
        this.charCounter = charCounter;
        this.cache = cache;
    }

    @Override
    public Map<Character, Integer> count(String text) {
        if (!cache.contains(text)) {
            cache.put(text, charCounter.count(text));
        }
        return cache.get(text);
    }
}
