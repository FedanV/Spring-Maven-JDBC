package com.foxminded.vitaliifedan.task4;

import com.foxminded.vitaliifedan.task4.factories.FormatterFactory;
import com.foxminded.vitaliifedan.task4.formatters.Formatter;
import com.foxminded.vitaliifedan.task4.models.Result;
import com.foxminded.vitaliifedan.task4.models.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ClassicFormatterTest {
    static Formatter formatter;

    @BeforeAll
    static void setup() {
        formatter = FormatterFactory.getInstance().get("classic");
    }

    @Test
    void Should_ClassicFormattedString_When_GetDividendAndDivisorTheSame() {
        Result result = new Result(100, 100, 1, 0, Arrays.asList(
                new Step(100, 100, 0, 1)
        ));
        String expectResult = "_100│100\n" +
                " 100│-\n" +
                " ---│1\n" +
                "   0";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

    @Test
    void Should_ClassicFormattedString_When_GetDividendZero() {
        Result result = new Result(0, 1234, 0, 1, null);
        String expectResult = "0 / 1234 = 0";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

    @Test
    void Should_ClassicFormattedString_When_GetDividendLessThanDivisor() {
        Result result = new Result(123, 12345, 0, 1, null);
        String expectResult = "123 / 12345 = 0";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

    @Test
    void Should_ClassicFormattedString_When_GetPositiveDividendAndDivisor() {
        Result result = new Result(78945, 4, 19736, 1, Arrays.asList(
                new Step(7, 4, 3, 1),
                new Step(38, 36, 2, 9),
                new Step(29, 28, 1, 7)
        ));
        String expectResult = "_78945│4\n" +
                " 4    │-----\n" +
                " -    │19736\n" +
                "_38\n" +
                " 36\n" +
                " --\n" +
                " _29\n" +
                "  28\n" +
                "  --\n" +
                "     1";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

}
