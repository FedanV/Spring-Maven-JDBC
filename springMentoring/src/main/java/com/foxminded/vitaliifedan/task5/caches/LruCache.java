package com.foxminded.vitaliifedan.task5.caches;


import java.util.HashMap;
import java.util.Map;

public class LruCache<K, V> implements Cache<K, V> {
    private final Map<K, LinkedNode> cache;
    private final LinkedNode head;
    private final LinkedNode tail;
    private final int capacity;
    private int size;

    public LruCache(int capacity) {

        this.capacity = capacity;

        cache = new HashMap<>(capacity);
        head = new LinkedNode();
        tail = new LinkedNode();

        head.next = tail;
        tail.prev = head;

    }

    @Override
    public V get(K key) {

        LinkedNode node = cache.get(key);
        if (node == null) {
            return null;
        }
        remove(node);
        moveToHead(node);
        return node.value;

    }

    @Override
    public void put(K key, V value) {

        LinkedNode node = cache.get(key);
        if (node != null) {
            node.value = value;
            remove(node);
            moveToHead(node);
        } else {
            node = new LinkedNode();
            node.value = value;
            node.key = key;
            cache.put(key, node);
            moveToHead(node);
            size++;

            if (size > capacity) {
                cache.remove(tail.prev.key);
                remove(tail.prev);
                size--;
            }
        }

    }

    private void moveToHead(LinkedNode node) {

        LinkedNode temp = head.next;
        head.next = node;
        node.prev = head;
        node.next = temp;
        temp.prev = node;

    }

    private void remove(LinkedNode node) {

        node.prev.next = node.next;
        node.next.prev = node.prev;

    }

    @Override
    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    private class LinkedNode {

        V value;
        K key;
        LinkedNode next;
        LinkedNode prev;

    }

}
