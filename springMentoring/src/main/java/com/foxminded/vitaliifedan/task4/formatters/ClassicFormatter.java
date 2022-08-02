package com.foxminded.vitaliifedan.task4.formatters;

import com.foxminded.vitaliifedan.task4.models.Calculator;
import com.foxminded.vitaliifedan.task4.models.Result;
import com.foxminded.vitaliifedan.task4.models.Step;

import java.util.List;

public class ClassicFormatter implements Formatter {

    @Override
    public String format(Result result) {

        StringBuilder classicResult = new StringBuilder();

        if (result.getDividend() < result.getDivisor()) {
            String zeroQuotient = String.format("%d / %d = %d", result.getDividend(), result.getDivisor(), result.getQuotient());
            return classicResult.append(zeroQuotient).toString();
        }

        classicResult.append(createHeader(result));
        classicResult.append(createBody(result));
        classicResult.append(createReminder(result));

        return classicResult.toString();
    }

    private String createReminder(Result result) {

        int reminderIndent = Calculator.length(result.getDividend()) - Calculator.length(result.getReminder());
        return assembleString(reminderIndent + 1, ' ') + result.getReminder();

    }

    private String createHeader(Result result) {

        List<Step> steps = result.getSteps();
        int dividendLength = Calculator.length(result.getDividend());
        int subtrahendLength = Calculator.length(steps.get(0).getSubtrahend());
        int minuendLength = Calculator.length(steps.get(0).getMinuend());
        int quotientLength = Calculator.length(result.getQuotient());
        int indent = Calculator.length(minuendLength + 1 - subtrahendLength);

        StringBuilder headerString = new StringBuilder();

        String firstRow = String.format("_%d│%d", result.getDividend(), result.getDivisor());
        headerString.append(firstRow).append("\n");

        String secondRow = assembleString(indent, ' ') +
                steps.get(0).getSubtrahend() +
                assembleString(dividendLength - subtrahendLength, ' ') +
                "│" + assembleString(quotientLength, '-');
        headerString.append(secondRow).append("\n");

        String thirdRow = assembleString(indent, ' ') +
                assembleString(subtrahendLength, '-') +
                assembleString(dividendLength - subtrahendLength, ' ') +
                "│" + result.getQuotient();
        headerString.append(thirdRow).append("\n");

        return headerString.toString();
    }

    private String createBody(Result result) {

        StringBuilder bodyString = new StringBuilder();
        List<Step> steps = result.getSteps();

        for (int i = 1; i < steps.size(); i++) {

            int subtrahendLength = Calculator.length(steps.get(i).getSubtrahend());
            String minuend = assembleString(i - 1, ' ') + "_" + steps.get(i).getMinuend();
            bodyString.append(minuend).append("\n");
            String subtrahend = assembleString(i, ' ') + steps.get(i).getSubtrahend();
            bodyString.append(subtrahend).append("\n");
            String end = assembleString(i, ' ') + assembleString(subtrahendLength, '-');
            bodyString.append(end).append("\n");

        }

        return bodyString.toString();
    }

}