package com.foxminded.vitaliifedan.task4.formatters;

import com.foxminded.vitaliifedan.task4.models.Result;
import com.foxminded.vitaliifedan.task4.models.Step;

import java.util.List;

import static com.foxminded.vitaliifedan.utils.NumberUtils.length;
import static com.foxminded.vitaliifedan.utils.StringUtils.assembleString;

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

        int reminderIndent = length(result.getDividend()) - length(result.getReminder());
        return assembleString(reminderIndent + 1, ' ') + result.getReminder();

    }

    private String createHeader(Result result) {

        List<Step> steps = result.getSteps();
        int index = 0;

        while (steps.get(index).getStepQuotient() == 0) {
            index += 1;
        }

        int subtrahendLength = length(steps.get(index).getSubtrahend());
        int minuendLength = length(steps.get(index).getMinuend());
        int indent = length(minuendLength + 1 - subtrahendLength);

        StringBuilder headerString = new StringBuilder();

        String firstRow = String.format("_%d ÷ %d => %d", result.getDividend(), result.getDivisor(), result.getQuotient());
        headerString.append(firstRow).append("\n");

        String secondRow = assembleString(indent, ' ') + steps.get(index).getSubtrahend();
        headerString.append(secondRow).append("\n");

        String thirdRow = assembleString(indent, ' ') + assembleString(subtrahendLength, '-');
        headerString.append(thirdRow).append("\n");

        return headerString.toString();
    }

    private String createBody(Result result) {
        StringBuilder bodyString = new StringBuilder();
        List<Step> steps = result.getSteps();
        int index = 0;

        while (steps.get(index).getStepQuotient() == 0) {
            index += 1;
        }
        int indent = 1;
        for (int i = index + 1; i < steps.size(); i++) {
            int subtrahendLength = length(steps.get(i).getSubtrahend());

            if (steps.get(i - 1).getDifference() == 0) {
                indent += length(steps.get(i - 1).getSubtrahend());
                String minuend = assembleString(indent - 1, ' ') + "_" + steps.get(i).getMinuend();
                bodyString.append(minuend).append("\n");
                String subtrahend = assembleString(indent, ' ') + steps.get(i).getSubtrahend();
                bodyString.append(subtrahend).append("\n");
                String end = assembleString(indent, ' ') + assembleString(subtrahendLength, '-');
                bodyString.append(end).append("\n");
            } else {
                if (length(steps.get(i - 1).getMinuend()) > 1) {
                    indent += 1;
                }
                String minuend = assembleString(indent - 1, ' ') + "_" + steps.get(i).getMinuend();
                bodyString.append(minuend).append("\n");
                String subtrahend = assembleString(indent, ' ') + steps.get(i).getSubtrahend();
                bodyString.append(subtrahend).append("\n");
                String end = assembleString(indent, ' ') + assembleString(subtrahendLength, '-');
                bodyString.append(end).append("\n");

            }
        }

        return bodyString.toString();
    }
}


