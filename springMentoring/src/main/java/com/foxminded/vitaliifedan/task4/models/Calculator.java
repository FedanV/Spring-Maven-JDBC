package com.foxminded.vitaliifedan.task4.models;

import java.util.ArrayList;
import java.util.List;

import static com.foxminded.vitaliifedan.utils.NumberUtils.numberToDigits;

public class Calculator {

    public Result divide(int dividend, int divisor) {

        if (divisor == 0) {
            throw new IllegalArgumentException("Divisor cannot be 0, division by zero");
        }

        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);

        if (dividend < divisor) {
            return new Result(dividend, divisor, 0, 1, List.of(
                    new Step(0, 0, 0, 0)
            ));
        }

        List<Step> steps = new ArrayList<>();
        int[] digits = numberToDigits(dividend);
        int minuend = 0;
        int quotient = 0;

        for (int digit : digits) {
            minuend = minuend * 10 + digit;
            int stepQuotient = minuend / divisor;
                int subtrahend = stepQuotient * divisor;
                int difference = minuend - subtrahend;
                quotient = quotient * 10 + stepQuotient;
                steps.add(new Step(minuend, subtrahend, difference, stepQuotient));
                minuend = difference;
        }

        int reminder = minuend;
        return new Result(dividend, divisor, quotient, reminder, steps);

    }

}
