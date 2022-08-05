package com.foxminded.vitaliifedan.task5;

import com.foxminded.vitaliifedan.task5.caches.LruCacheDecorator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LruCacheDecoratorTest {
    LruCacheDecorator lruCacheDecorator;
    CharCounter charCounter;

    @BeforeEach
    void setup() {
        charCounter = mock(CharCounter.class);
        lruCacheDecorator = new LruCacheDecorator(charCounter, 3);
    }


    @Test
    void Should_EmptyMap_WhenKeyIsNull() {

        Assertions.assertEquals(Collections.emptyMap(), lruCacheDecorator.get("test"));

    }

    @Test
    void Should_ReturnMap_When_MethodGet_ReceiveExistingKey() {
        String key = "test";
        Map<Character, Integer> value = Map.of('t', 1);
        lruCacheDecorator.put(key, value);
        Assertions.assertEquals(value, lruCacheDecorator.get(key));

    }

    @Test
    void Should_RemoveFirstAddedKey_When_Added4ElementsInCache() {

        Map<Character, Integer> value1 = Map.of('t', 1);
        Map<Character, Integer> value2 = Map.of('e', 2);
        Map<Character, Integer> value3 = Map.of('s', 3);
        Map<Character, Integer> value4 = Map.of('u', 4);
        lruCacheDecorator.put("test1", value1);
        lruCacheDecorator.put("test2", value2);
        lruCacheDecorator.put("test3", value3);
        lruCacheDecorator.put("test4", value4);

        List<Boolean> actualResult = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            actualResult.add(lruCacheDecorator.contains("test" + i));
        }
        List<Boolean> expectedResult = Arrays.asList(false, true, true, true);
        Assertions.assertEquals(expectedResult, actualResult);

    }

    @Test
    void Should_RemoveSecondAddedKey_When_FirstElemUsesMoreThanSecondElem() {

        Map<Character, Integer> value1 = Map.of('t', 1);
        Map<Character, Integer> value2 = Map.of('e', 2);
        Map<Character, Integer> value3 = Map.of('s', 3);
        lruCacheDecorator.put("test1", value1);
        lruCacheDecorator.put("test2", value2);
        lruCacheDecorator.put("test3", value3);
        lruCacheDecorator.get("test1");
        Map<Character, Integer> value4 = Map.of('u', 4);
        lruCacheDecorator.put("test4", value4);

        List<Boolean> actualResult = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            actualResult.add(lruCacheDecorator.contains("test" + i));
        }
        List<Boolean> expectedResult = Arrays.asList(true, false, true, true);
        Assertions.assertEquals(expectedResult, actualResult);

    }

    @Test
    void Should_ReturnMapWithCharactersCount_When_MethodCount_GetText() {

        Map<Character, Integer> map = Map.of('t', 1);
        when(charCounter.count("test")).thenReturn(map);
        Assertions.assertEquals(map, lruCacheDecorator.count("test"));


    }


}
