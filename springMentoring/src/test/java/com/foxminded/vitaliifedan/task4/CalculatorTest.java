package com.foxminded.vitaliifedan.task4;

import com.foxminded.vitaliifedan.task4.models.Calculator;
import com.foxminded.vitaliifedan.task4.models.Result;
import com.foxminded.vitaliifedan.task4.models.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Stack;

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
    void Should_ResultWithZeroQuatient_When_GetDividendLessThanDivisor() {
        Result actualResult = calculator.divide(4, 10);
        Result expectedExpected = new Result(4, 10, 0, 1, null);
        Assertions.assertEquals(expectedExpected, actualResult);
    }

    @Test
    void Should_ResultWithSteps_When_GetNegativeDivisorAndDividendn() {
        Result actualResult = calculator.divide(-1234, -12);
        Result expectedExpected = new Result(1234, 12, 102, 10, Arrays.asList(
                new Step(12, 12, 0, 1),
                new Step(34, 24, 10, 2)
        ));
        Assertions.assertEquals(expectedExpected, actualResult);
    }

}
