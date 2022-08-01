package com.foxminded.vitaliifedan.task4.formatters;

import com.foxminded.vitaliifedan.task4.models.Division;
import com.foxminded.vitaliifedan.task4.models.Result;

public class ClassicFormatter implements Formatter {

    @Override
    public String format(Result result) {

        if (result.getDividend() < result.getDivisor()) {
            return "" + result.getDividend() + " / " + result.getDivisor() + " = " + result.getQuotient();
        }

        Division division = new Division();
        StringBuilder stringResult = division.makeDivision(result.getDividend(), result.getDivisor());

        int[] index = new int[3];
        int j = 0;
        for (int i = 0; i < stringResult.length(); i++) {
            if (stringResult.charAt(i) == '\n') {
                index[j] = i;
                j++;
            }
            if (j == 3) {
                break;
            }
        }
        int countOfIndents = division.calculateDigits(result.getDividend()) + 1 - index[0];
        stringResult.insert(index[2], division.assebleString(countOfIndents, ' ') + "│" + result.getQuotient());
        stringResult.insert(index[1], division.assebleString(countOfIndents, ' ') + "│"
                + division.assebleString(String.valueOf(result.getQuotient()).length(), '-'));
        stringResult.insert(index[0], "│" + result.getDivisor());
        stringResult.replace(1, index[0], String.valueOf(result.getDividend()));


        return stringResult.toString();
    }

}