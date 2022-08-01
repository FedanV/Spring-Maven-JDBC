package com.foxminded.vitaliifedan.task4;

import com.foxminded.vitaliifedan.task4.models.Calculator;
import com.foxminded.vitaliifedan.task4.models.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    static Calculator calculator;

    @BeforeAll
    static void setup() {
        calculator = new Calculator();
    }

    @Test
    void Should_Exception_When_GetDivisorZero() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> calculator.divide(3, 0));
        Assertions.assertEquals("Divisor cannot be 0, division by zero", thrown.getMessage());
    }

    @Test
    void Should_ReminderOne_When_GetDividendLessThanDivisor() {
        Result actualResult = calculator.divide(4, 10);
        Result expectedExpected = new Result(4, 10, 0, 1);
        Assertions.assertEquals(expectedExpected, actualResult);
    }

    @Test
    void Should_QuotientZero_When_GetDividendLessThanDivisor() {
        Result actualResult = calculator.divide(4, 10);
        Result expectedExpected = new Result(4, 10, 0, 1);

        Assertions.assertEquals(expectedExpected, actualResult);
    }

    @Test
    void Should_ResultWithPositiveDividend_When_GetNegativeDividendInInput() {
        Result actualResult = calculator.divide(-20, 10);
        Result expectedExpected = new Result(20, 10, 2, 0);
        Assertions.assertEquals(expectedExpected, actualResult);
    }

    @Test
    void Should_ResultWithPositiveDivisor_When_GetNegativeDivisorInInput() {
        Result actualResult = calculator.divide(-20, 10);
        Result expectedExpected = new Result(20, 10, 2, 0);
        Assertions.assertEquals(expectedExpected, actualResult);
    }


}
