package com.foxminded.vitaliifedan.task5;

import com.foxminded.vitaliifedan.task5.formatters.BasicFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.LinkedHashMap;
import java.util.Map;

class BasicFormatTest {

    static BasicFormat basicFormat;

    @BeforeAll
    static void setup() {
        basicFormat = new BasicFormat();
    }

    @Test
    void Should_Exception_When_GetNull() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> basicFormat.apply(null));
        Assertions.assertEquals("'characters' must be Map<Character, Integer>, but was get null", thrown.getMessage());
    }

    @Test
    void Should_FormattedString_When_GetMap() {
        Map<Character, Integer> map = new LinkedHashMap<>();
        map.put('p', 1);
        map.put('r', 2);
        map.put('u', 1);
        String actualResult = basicFormat.apply(map);
        String expectedResult = "\"p\" - 1\n" +
                "\"r\" - 2\n" +
                "\"u\" - 1\n";

        Assertions.assertEquals(expectedResult, actualResult);
    }
}
