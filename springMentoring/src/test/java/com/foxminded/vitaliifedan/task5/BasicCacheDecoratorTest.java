package com.foxminded.vitaliifedan.task5;

import com.foxminded.vitaliifedan.task5.caches.BasicCacheDecorator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

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
        Map<Character, Integer> characters = new HashMap<>();
        characters.put('c', 1);
        when(cache.get(Mockito.anyString())).thenReturn(characters);
        Assertions.assertEquals(characters, basicCacheDecorator.get("t"));
    }
}
