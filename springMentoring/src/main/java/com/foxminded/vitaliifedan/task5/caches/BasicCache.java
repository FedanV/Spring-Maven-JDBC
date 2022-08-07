package com.foxminded.vitaliifedan.task5.caches;

import java.util.HashMap;
import java.util.Map;

public class BasicCache<K, V> implements Cache<K, V> {

    private final Map<K, V> cache;

    public BasicCache() {
        this.cache = new HashMap<>();
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public boolean contains(K key) {
        return cache.containsKey(key);
    }

}
