package com.foxminded.vitaliifedan.task5.caches;

import java.util.HashMap;
import java.util.Map;

public class BasicCache implements Cache<String, Map<Character, Integer>> {

    private final Map<String, Map<Character, Integer>> cache;

    public BasicCache() {
        this.cache = new HashMap<>();
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

}
