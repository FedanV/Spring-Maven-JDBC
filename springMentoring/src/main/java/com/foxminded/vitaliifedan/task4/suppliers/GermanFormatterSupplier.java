package com.foxminded.vitaliifedan.task4.suppliers;

import com.foxminded.vitaliifedan.task4.formatters.Formatter;
import com.foxminded.vitaliifedan.task4.formatters.GermanFormatter;

public class GermanFormatterSupplier implements FormatterSupplier {
    @Override
    public String name() {
        return "german";
    }

    @Override
    public Formatter get() {
        return new GermanFormatter();
    }
}