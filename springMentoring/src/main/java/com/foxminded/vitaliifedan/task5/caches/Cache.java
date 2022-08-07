package com.foxminded.vitaliifedan.task5.caches;

public interface Cache<K, V> {

    V get(K key);

    void put(K key, V value);

    boolean contains(K key);

}
