package com.foxminded.vitaliifedan.task4;

import com.foxminded.vitaliifedan.task4.factories.FormatterFactory;
import com.foxminded.vitaliifedan.task4.formatters.Formatter;
import com.foxminded.vitaliifedan.task4.models.Calculator;
import com.foxminded.vitaliifedan.task4.models.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GermanFormatterTest {

    static Calculator calculator;
    static Formatter formatter;

    @BeforeAll
    static void setup() {
        calculator = new Calculator();
        formatter = FormatterFactory.getInstance().get("german");
    }

    @Test
    void Should_GermanFormattedString_When_GetDividendAndDivisorTheSame() {
        Result result = new Result(100, 100, 1, 0);
        String expectResult = "_100 ÷ 100 => 1\n" +
                " 100\n" +
                " ---\n" +
                "   0\n";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

    @Test
    void Should_GermanFormattedString_When_GetDivisorOne() {
        Result result = new Result(1234, 1, 1234, 0);
        String expectResult = "_1234 ÷ 1 => 1234\n" +
                " 1\n" +
                " -\n" +
                " _2\n" +
                "  2\n" +
                "  -\n" +
                "  _3\n" +
                "   3\n" +
                "   -\n" +
                "   _4\n" +
                "    4\n" +
                "    -\n" +
                "    0\n";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

    @Test
    void Should_GermanFormattedString_When_GetDividendZero() {
        Result result = new Result(0, 1234, 0, 1);
        String expectResult = "0 / 1234 = 0";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

    @Test
    void Should_GermanFormattedString_When_GetDividendLessThanDivisor() {
        Result result = new Result(123, 12345, 0, 1);
        String expectResult = "123 / 12345 = 0";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

    @Test
    void Should_GermanFormattedString_When_GetPositiveDividendAndDivisor() {
        Result result = new Result(10000123, 10, 1000012, 3);
        String expectResult = "_10000123 ÷ 10 => 1000012\n" +
                " 10\n" +
                " --\n" +
                "     _12\n" +
                "      10\n" +
                "      --\n" +
                "      _23\n" +
                "       20\n" +
                "       --\n" +
                "        3\n";
        Assertions.assertEquals(expectResult, formatter.format(result));
    }

}
