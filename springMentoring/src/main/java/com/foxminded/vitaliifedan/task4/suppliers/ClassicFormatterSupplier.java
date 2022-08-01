package com.foxminded.vitaliifedan.task4.suppliers;

import com.foxminded.vitaliifedan.task4.formatters.ClassicFormatter;
import com.foxminded.vitaliifedan.task4.formatters.Formatter;

public class ClassicFormatterSupplier implements FormatterSupplier {
    @Override
    public String name() {
        return "classic";
    }

    @Override
    public Formatter get() {
        return new ClassicFormatter();
    }
}