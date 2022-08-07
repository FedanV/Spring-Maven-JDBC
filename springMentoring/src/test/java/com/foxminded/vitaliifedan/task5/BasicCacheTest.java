package com.foxminded.vitaliifedan.task5;

import com.foxminded.vitaliifedan.task5.caches.BasicCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

class BasicCacheTest {

    static BasicCache<String, Map<Character, Integer>> basicCache;

    @BeforeAll
    static void setup() {
        basicCache = new BasicCache<>();
    }

    @Test
    void Should_ReturnMap_When_GetStringInMethodGet() {
        Map<Character, Integer> characters = Map.of('c', 1);
        basicCache.put("test", characters);
        Assertions.assertEquals(characters, basicCache.get("test"));
    }

    @Test
    void Should_ReturnTrue_When_MethodContains_GetExistingKey() {
        Map<Character, Integer> characters = Map.of('c', 1);
        basicCache.put("test", characters);
        Assertions.assertTrue(basicCache.contains("test"));
    }


}
