package com.foxminded.vitaliifedan.task5;

import com.foxminded.vitaliifedan.task5.caches.BasicCacheDecorator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicCacheDecoratorTest {

    @Mock
    CharCounter charCounter;

    @Mock
    Map<String, Map<Character, Integer>> cache;

    @InjectMocks
    BasicCacheDecorator basicCacheDecorator;

    @Test
    void Should_ReturnMap_When_GetStringInMethodGet() {
        Map<Character, Integer> characters = Map.of('c', 1);
        when(cache.get(Mockito.anyString())).thenReturn(characters);
        Assertions.assertEquals(characters, basicCacheDecorator.get("test"));
    }

    @Test
    void Should_AddElementInMap_When_MethodPut_GetKeyAndValue() {
        Map<Character, Integer> value = Map.of('c', 1);
        basicCacheDecorator.put("test", value);
        Mockito.verify(cache).put("test", value);
    }

    @Test
    void Should_ReturnTrue_When_MethodContains_GetExistingKey() {
        when(cache.containsKey("test")).thenReturn(true);
        Assertions.assertTrue(basicCacheDecorator.contains("test"));
    }

    @Test
    void Should_CallMethodGet_When_MethodCount_GetExistingKeyInCache() {
        Map<Character, Integer> map = Map.of('t', 1);
        when(cache.get("test")).thenReturn(map);
        Assertions.assertEquals(map, basicCacheDecorator.count("test"));
    }

    @Test
    void Should_CallMethodPutAndThenGet_When_MethodCount_GetNotExistingKeyInCache() {

        Map<Character, Integer> map = Map.of('t', 1);
        when(charCounter.count("test")).thenReturn(map);
        when(cache.containsKey("test")).thenReturn(false);
        basicCacheDecorator.count("test");
        InOrder inOrder = Mockito.inOrder(cache);
        inOrder.verify(cache).put("test", map);
        inOrder.verify(cache).get("test");

    }

}
