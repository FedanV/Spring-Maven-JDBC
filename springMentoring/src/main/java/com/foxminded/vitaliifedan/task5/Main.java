package com.foxminded.vitaliifedan.task5;

import com.foxminded.vitaliifedan.task5.formatters.BasicFormat;
import com.foxminded.vitaliifedan.task5.formatters.Formatter;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String text = "Lorem 1";
        BasicCharCounter basic = new BasicCharCounter();
        Map<Character, Integer> count = basic.count(text);
        Formatter basicFormat = new BasicFormat();
        String apply = basicFormat.apply(count);
        System.out.println(apply);

    }

}
