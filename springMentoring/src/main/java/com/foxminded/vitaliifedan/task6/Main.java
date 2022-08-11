package com.foxminded.vitaliifedan.task6;

import com.foxminded.vitaliifedan.task6.formatters.Format;
import com.foxminded.vitaliifedan.task6.models.Racer;
import com.foxminded.vitaliifedan.task6.parsers.Parser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Path pathToFileFolder = Paths.get("springMentoring", "src", "main", "resources", "task6", "files");

        Path startLogs = pathToFileFolder.resolve("start.log");
        Path endLogs = pathToFileFolder.resolve("end.log");
        Path abbreviations = pathToFileFolder.resolve("abbreviations.txt");

        Parser parser = new Parser();
        List<Racer> racers = parser.parseFiles(startLogs, endLogs, abbreviations);
        Format format = new Format();
        String apply = format.apply(racers, 15);
        System.out.println(apply);


    }


}
