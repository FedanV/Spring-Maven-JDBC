package com.foxminded.vitaliifedan.task4.models;

public class Step {
    private final int minuend;
    private final int subtrahend;
    private final int difference;
    private final int stepQuotient;

    public Step(int minuend, int subtrahend, int difference, int stepQuotient) {
        this.minuend = minuend;
        this.subtrahend = subtrahend;
        this.difference = difference;
        this.stepQuotient = stepQuotient;
    }

    @Override
    public String toString() {
        return "Step{" +
                "minuend=" + minuend +
                ", subtrahend=" + subtrahend +
                ", difference=" + difference +
                ", stepQuotient=" + stepQuotient +
                '}';
    }

    public int getMinuend() {
        return minuend;
    }

    public int getSubtrahend() {
        return subtrahend;
    }

    public int getDifference() {
        return difference;
    }

    public int getStepQuotient() {
        return stepQuotient;
    }
}
