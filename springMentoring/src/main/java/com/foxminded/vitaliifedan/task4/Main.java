package com.foxminded.vitaliifedan.task4;

import com.foxminded.vitaliifedan.task4.factories.FormatterFactory;
import com.foxminded.vitaliifedan.task4.formatters.Formatter;
import com.foxminded.vitaliifedan.task4.models.Calculator;
import com.foxminded.vitaliifedan.task4.models.Result;

public class Main {

    public static void main(String[] args) {
        Calculator c = new Calculator();
        Result result = c.divide(78945, 4);
        Formatter classicFormatter = FormatterFactory.getInstance().get("classic");
        Formatter germanFormatter = FormatterFactory.getInstance().get("german");
        String classic = classicFormatter.format(result);
        String german = germanFormatter.format(result);
        System.out.println(classic);
        System.out.println(german);
    }

}
