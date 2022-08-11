package com.foxminded.vitaliifedan.task6.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {

    private StringUtils() {
    }

    public static String minutesSecondsMillisToStringFormat(LocalDateTime time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("m:ss.SSS");
        return time.format(dateTimeFormatter);
    }
}
