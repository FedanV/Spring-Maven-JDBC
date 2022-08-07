package com.foxminded.vitaliifedan.task5;

import com.foxminded.vitaliifedan.task5.caches.Cache;

import java.util.Map;

public class CacheCharCounterDecorator implements CharCounter {

    private Cache<String, Map<Character, Integer>> cache;
    private CharCounter charCounter;

    public CacheCharCounterDecorator(CharCounter charCounter, Cache<String, Map<Character, Integer>> cache) {
        this.charCounter = charCounter;
        this.cache = cache;
    }

    @Override
    public Map<Character, Integer> count(String text) {
        String key = text.trim();
        if (!cache.contains(key)) {
            cache.put(key, charCounter.count(key));
        }
        return cache.get(key);
    }
}