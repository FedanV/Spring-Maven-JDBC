package com.foxminded.vitaliifedan.task4;

import com.foxminded.vitaliifedan.task4.factories.FormatterFactory;
import com.foxminded.vitaliifedan.task4.formatters.Formatter;
import com.foxminded.vitaliifedan.task4.models.Result;
import com.foxminded.vitaliifedan.task4.models.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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
        Result result = new Result(0, 1234, 0, 1, List.of(
                new Step(0, 0, 0, 0)
        ));
        String expectResult = "0 / 1234 = 0";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

    @Test
    void Should_ClassicFormattedString_When_GetDividendLessThanDivisor() {
        Result result = new Result(123, 12345, 0, 1, List.of(
                new Step(0, 0, 0, 0)
        ));
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

    @Test
    void Should_CorrectSpaces_When_QuotientLengthIsOne() {
        Result result = new Result(122345, 3, 40781, 2, Arrays.asList(
                new Step(1, 0, 1, 0),
                new Step(12, 12, 0, 4),
                new Step(2, 0, 2, 0),
                new Step(23, 21, 2, 7),
                new Step(24, 24, 0, 8),
                new Step(5, 3, 2, 1)
        ));
        String expectResult = "_122345│3\n" +
                " 12    │-----\n" +
                " --    │40781\n" +
                "  _2\n" +
                "   0\n" +
                "   -\n" +
                "  _23\n" +
                "   21\n" +
                "   --\n" +
                "   _24\n" +
                "    24\n" +
                "    --\n" +
                "     _5\n" +
                "      3\n" +
                "      -\n" +
                "      2";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

    @Test
    void Should_CorrectSpaces_When_MinuendLengthIsOne() {
        Result result = new Result(156654, 4, 39163, 2, Arrays.asList(
                new Step(1, 0, 1, 0),
                new Step(15, 12, 3, 3),
                new Step(36, 36, 0, 9),
                new Step(6, 4, 2, 1),
                new Step(25, 24, 1, 6)
        ));
        String expectResult = "_156654│4\n" +
                " 12    │-----\n" +
                " --    │39163\n" +
                " _36\n" +
                "  36\n" +
                "  --\n" +
                "   _6\n" +
                "    4\n" +
                "    -\n" +
                "   _25\n" +
                "    24\n" +
                "    --\n" +
                "      2";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

    @Test
    void Should_CorrectSpacesForReminder_When_MinuendContainsZero() {
        Result result = new Result(10001, 1, 10001, 0, Arrays.asList(
                new Step(1, 1, 0, 1),
                new Step(0, 0, 0, 0),
                new Step(0, 0, 0, 0),
                new Step(0, 0, 0, 0),
                new Step(1, 1, 0, 1)
        ));
        String expectResult = "_10001│1\n" +
                " 1    │-----\n" +
                " -    │10001\n" +
                " _0\n" +
                "  0\n" +
                "  -\n" +
                "  _0\n" +
                "   0\n" +
                "   -\n" +
                "   _0\n" +
                "    0\n" +
                "    -\n" +
                "    _1\n" +
                "     1\n" +
                "     -\n" +
                "     0";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

}
