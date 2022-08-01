package com.foxminded.vitaliifedan.task4.models;

public class Calculator {

    public Result divide(int dividend, int divisor) {

        if (divisor == 0) {
            throw new IllegalArgumentException("Divisor cannot be 0, division by zero");
        }

        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);

        if (dividend < divisor) {
            return new Result(dividend, divisor, 0, 1);
        }

        Division division = new Division();
        division.makeDivision(dividend, divisor);

        int reminder = Integer.parseInt(division.getReminder().toString());
        int quotient = Integer.parseInt(division.getQuotient().toString());

        return new Result(dividend, divisor, quotient, reminder);

    }
}
