package com.foxminded.vitaliifedan.task6.formatters;

import com.foxminded.vitaliifedan.task6.models.Racer;
import com.foxminded.vitaliifedan.task6.utils.StringUtils;

import java.util.List;

public class Format implements Formatter {
    @Override
    public String apply(List<Racer> racers, int separatorNumber) {

        StringBuilder result = new StringBuilder();
        int lineSize = 0;
        for (int i = 0; i < racers.size(); i++) {
            if (i != separatorNumber) {
                String format = String.format("%2d.%-20s |%-30s |%-8s",
                        i + 1,
                        racers.get(i).getFullName(),
                        racers.get(i).getCar(),
                        StringUtils.minutesSecondsMillisToStringFormat(racers.get(i).getTimeDiff())
                );
                lineSize = format.length();
                result.append(format).append("\n");
            } else {
                result.append("-".repeat(lineSize)).append("\n");
                String format = String.format("%2d.%-20s |%-30s |%-8s",
                        i + 1,
                        racers.get(i).getFullName(),
                        racers.get(i).getCar(),
                        StringUtils.minutesSecondsMillisToStringFormat(racers.get(i).getTimeDiff())
                );
                result.append(format).append("\n");
            }
        }
        return result.toString();
    }

}
