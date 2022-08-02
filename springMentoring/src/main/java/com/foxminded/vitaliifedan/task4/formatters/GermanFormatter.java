package com.foxminded.vitaliifedan.task4.formatters;

import com.foxminded.vitaliifedan.task4.models.Calculator;
import com.foxminded.vitaliifedan.task4.models.Result;
import com.foxminded.vitaliifedan.task4.models.Step;

import java.util.List;

public class GermanFormatter implements Formatter {

    @Override
    public String format(Result result) {

        StringBuilder germanResult = new StringBuilder();

        if (result.getDividend() < result.getDivisor()) {
            String zeroQuotient = String.format("%d / %d = %d", result.getDividend(), result.getDivisor(), result.getQuotient());
            return germanResult.append(zeroQuotient).toString();
        }

        germanResult.append(createHeader(result));
        germanResult.append(createBody(result));
        germanResult.append(createReminder(result));

        return germanResult.toString();
    }

    private String createReminder(Result result) {

        int reminderIndent = Calculator.length(result.getDividend()) - Calculator.length(result.getReminder());
        return assembleString(reminderIndent + 1, ' ') + result.getReminder();

    }

    private String createHeader(Result result) {

        List<Step> steps = result.getSteps();
        int subtrahendLength = Calculator.length(steps.get(0).getSubtrahend());
        int minuendLength = Calculator.length(steps.get(0).getMinuend());
        int indent = Calculator.length(minuendLength + 1 - subtrahendLength);

        StringBuilder headerString = new StringBuilder();

        String firstRow = String.format("_%d ÷ %d => %d", result.getDividend(), result.getDivisor(), result.getQuotient());
        headerString.append(firstRow).append("\n");

        String secondRow = assembleString(indent, ' ') + steps.get(0).getSubtrahend();
        headerString.append(secondRow).append("\n");

        String thirdRow = assembleString(indent, ' ') + assembleString(subtrahendLength, '-');
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


