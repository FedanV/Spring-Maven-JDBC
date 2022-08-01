package com.foxminded.vitaliifedan.task4.models;

import java.util.Objects;

public class Result {

    private final int dividend;
    private final int divisor;
    private final int quotient;
    private final int reminder;

    public Result(int dividend, int divisor, int quotient, int reminder) {
        this.dividend = dividend;
        this.divisor = divisor;
        this.quotient = quotient;
        this.reminder = reminder;
    }

    public int getDividend() {
        return dividend;
    }

    public int getDivisor() {
        return divisor;
    }

    public int getQuotient() {
        return quotient;
    }

    public int getReminder() {
        return reminder;
    }

    @Override
    public String toString() {
        return "Result{" +
                "dividend=" + dividend +
                ", divisor=" + divisor +
                ", quotient=" + quotient +
                ", reminder=" + reminder +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return dividend == result.dividend && divisor == result.divisor && quotient == result.quotient && reminder == result.reminder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dividend, divisor, quotient, reminder);
    }
}
