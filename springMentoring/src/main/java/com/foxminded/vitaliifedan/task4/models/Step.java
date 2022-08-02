package com.foxminded.vitaliifedan.task4.models;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return minuend == step.minuend && subtrahend == step.subtrahend && difference == step.difference && stepQuotient == step.stepQuotient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minuend, subtrahend, difference, stepQuotient);
    }
}
