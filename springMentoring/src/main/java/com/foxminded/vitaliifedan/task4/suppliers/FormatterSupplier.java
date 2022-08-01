package com.foxminded.vitaliifedan.task4.suppliers;

import com.foxminded.vitaliifedan.task4.formatters.Formatter;

import java.util.function.Supplier;

public interface FormatterSupplier extends Supplier<Formatter> {
    String name();
}
