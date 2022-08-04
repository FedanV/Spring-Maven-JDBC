package com.foxminded.vitaliifedan.task5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

class BasicCharCounterTest {

    static BasicCharCounter basicCharCounter;

    @BeforeAll
    static void setup() {
        basicCharCounter = new BasicCharCounter();
    }

    @Test
    void Should_Exception_When_GetNull() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> basicCharCounter.count(null));
        Assertions.assertEquals("'text' must be String, but was get null", thrown.getMessage());
    }

    @Test
    void Should_ReturnMap_When_GetString() {
        String text = "Lorem ipum dolor";
        Map<Character, Integer> actualResult = basicCharCounter.count(text);
        Map<Character, Integer> expectedResult = Map.of(
                ' ', 2,
                'd', 1,
                'e', 1,
                'i', 1,
                'l', 2,
                'm', 2,
                'o', 3,
                'p', 1,
                'r', 2,
                'u', 1
        );
        Assertions.assertEquals(expectedResult, actualResult);
    }

}
