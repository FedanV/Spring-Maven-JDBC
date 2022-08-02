package com.foxminded.vitaliifedan.task4.models;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    public Result divide(int dividend, int divisor) {

        if (divisor == 0) {
            throw new IllegalArgumentException("Divisor cannot be 0, division by zero");
        }

        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);

        if (dividend < divisor) {
            return new Result(dividend, divisor, 0, 1, null);
        }

        List<Step> steps = new ArrayList<>();
        int[] digits = numberToDigits(dividend);
        int minuend = 0;
        int quotient = 0;

        for (int digit : digits) {
            minuend = minuend * 10 + digit;
            int stepQuotient = minuend / divisor;
            if (minuend >= divisor) {
                int subtrahend = stepQuotient * divisor;
                int difference = minuend - subtrahend;
                quotient = quotient * 10 + stepQuotient;
                steps.add(new Step(minuend, subtrahend, difference, stepQuotient));
                minuend = difference;
            } else {
                quotient = quotient * 10 + stepQuotient;
            }
        }

        int reminder = minuend;
        return new Result(dividend, divisor, quotient, reminder, steps);

    }

    public static int length(int number) {
        return number < 10 ? 1 : (int) Math.log10(number) + 1;
    }

    private static int[] numberToDigits(int number) {
        int lengthNumber = length(number);
        int[] result = new int[lengthNumber];
        for (int i = lengthNumber - 1; i >= 0; i--) {
            result[i] = number % 10;
            number /= 10;
        }
        return result;
    }
}
