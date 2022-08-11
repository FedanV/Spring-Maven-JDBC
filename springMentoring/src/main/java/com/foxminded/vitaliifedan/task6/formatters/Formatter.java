package com.foxminded.vitaliifedan.task6.formatters;

import com.foxminded.vitaliifedan.task6.models.Racer;

import java.util.List;

public interface Formatter {

    String apply(List<Racer> racers, int separatorNumber);
}
