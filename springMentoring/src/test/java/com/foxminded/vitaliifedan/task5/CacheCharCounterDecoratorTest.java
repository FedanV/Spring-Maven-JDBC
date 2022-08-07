package com.foxminded.vitaliifedan.task5;

import com.foxminded.vitaliifedan.task5.caches.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheCharCounterDecoratorTest {

    @Mock
    CharCounter charCounter;

    @Mock
    Cache<String, Map<Character, Integer>> cache;

    @InjectMocks
    CacheCharCounterDecorator charCounterDecorator;

    @Test
    void Should_GetValueFromCache_When_KeyExistsInCache() {

        Map<Character, Integer> characters = Map.of('t', 1);
        when(cache.get("test")).thenReturn(characters);
        when(cache.contains("test")).thenReturn(true);
        Assertions.assertEquals(characters,charCounterDecorator.count("test"));
        InOrder inOrder = Mockito.inOrder(cache);
        inOrder.verify(cache).contains("test");
        inOrder.verify(cache, never()).put("test", charCounter.count("test"));
        inOrder.verify(cache).get("test");

    }

    @Test
    void Should_PutValueInCache_When_KeyNotExistInCache() {

        Map<Character, Integer> characters = Map.of('t', 1);
        when(cache.get("test")).thenReturn(characters);
        when(cache.contains("test")).thenReturn(false);
        InOrder inOrder = Mockito.inOrder(cache);
        charCounterDecorator.count("test");
        inOrder.verify(cache).contains("test");
        inOrder.verify(cache).put("test", charCounter.count("test"));
        inOrder.verify(cache).get("test");

    }

}
