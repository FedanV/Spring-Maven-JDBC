package com.foxminded.vitaliifedan.task5.caches;

public interface Cache<K, T> {

    T get(K key);

    void put(K key, T value);

    boolean contains(K key);

}
