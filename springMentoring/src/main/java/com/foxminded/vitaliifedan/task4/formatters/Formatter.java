package com.foxminded.vitaliifedan.task4.formatters;

import com.foxminded.vitaliifedan.task4.models.Result;

public interface Formatter {
    String format(Result result);

    default String assembleString(int countSymbols, char symbol) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < countSymbols; i++) {
            string.append(symbol);
        }
        return string.toString();
    }
}
