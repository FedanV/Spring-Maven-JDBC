package com.foxminded.vitaliifedan.task5.caches;

import com.foxminded.vitaliifedan.task5.CharCounter;

import java.util.HashMap;
import java.util.Map;

public class BasicCacheDecorator implements CharCounter, Cache<String, Map<Character, Integer>> {

    private final Map<String, Map<Character, Integer>> cache;
    private final CharCounter charCounter;

    public BasicCacheDecorator(CharCounter charCounter) {
        this.charCounter = charCounter;
        this.cache = new HashMap<>();
    }

    public BasicCacheDecorator(CharCounter charCounter, Map<String, Map<Character, Integer>> cache) {
        this.cache = cache;
        this.charCounter = charCounter;
    }

    @Override
    public Map<Character, Integer> get(String key) {
        return cache.get(key);
    }

    @Override
    public void put(String key, Map<Character, Integer> value) {
        cache.put(key, value);
    }

    @Override
    public boolean contains(String key) {
        return cache.containsKey(key);
    }

    @Override
    public Map<Character, Integer> count(String text) {
        String key = text.trim();
        if (!contains(key)) {
            put(key, charCounter.count(key));
        }
        return get(key);
    }

}
