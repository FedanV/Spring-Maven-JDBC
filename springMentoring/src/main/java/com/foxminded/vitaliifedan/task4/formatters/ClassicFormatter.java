package com.foxminded.vitaliifedan.task4.formatters;

import com.foxminded.vitaliifedan.task4.models.Result;
import com.foxminded.vitaliifedan.task4.models.Step;

import java.util.List;

import static com.foxminded.vitaliifedan.utils.NumberUtils.length;
import static com.foxminded.vitaliifedan.utils.StringUtils.assembleString;

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

        int reminderIndent = length(result.getDividend()) - length(result.getReminder());
        return assembleString(reminderIndent + 1, ' ') + result.getReminder();

    }

    private String createHeader(Result result) {


        List<Step> steps = result.getSteps();
        int index = 0;

        while (steps.get(index).getStepQuotient() == 0) {
            index += 1;
        }

        int dividendLength = length(result.getDividend());
        int subtrahendLength = length(steps.get(index).getSubtrahend());
        int minuendLength = length(steps.get(index).getMinuend());
        int quotientLength = length(result.getQuotient());
        int indent = length(minuendLength + 1 - subtrahendLength);

        StringBuilder headerString = new StringBuilder();

        String firstRow = String.format("_%d│%d", result.getDividend(), result.getDivisor());
        headerString.append(firstRow).append("\n");

        String secondRow = assembleString(indent, ' ') +
                steps.get(index).getSubtrahend() +
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