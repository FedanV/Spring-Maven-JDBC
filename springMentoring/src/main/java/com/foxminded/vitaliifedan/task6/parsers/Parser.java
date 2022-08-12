package com.foxminded.vitaliifedan.task6.parsers;

import com.foxminded.vitaliifedan.task6.models.Racer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Parser {

    List<Racer> racers;

    public Parser() {
        this.racers = new ArrayList<>();
    }

    public Parser(List<Racer> racers) {
        this.racers = racers;
    }

    public List<Racer> getRacers() {
        return racers;
    }

    public List<Racer> parseFiles(Path pathToStartLog, Path pathToEndLog, Path pathToAbbreviation) {
        parseLogsFile(pathToStartLog);
        parseLogsFile(pathToEndLog);
        parseAbbreviationFile(pathToAbbreviation);
        return racers.stream().sorted().toList();
    }

    private void parseLogsFile(Path pathToFile) {

        try (Stream<String> lines = Files.lines(pathToFile)) {
            lines.map(this::parseLogLine).forEach(arr -> {
                Optional<Racer> racer = racers.stream().filter(r -> r.getAbbreviation().equals(arr.get(0))).findFirst();
                if (racer.isPresent()) {
                    racer.get().getTimes().add(LocalDateTime.parse(arr.get(1) + "T" + arr.get(2)));
                } else {
                    Racer newRacer = new Racer();
                    newRacer.setAbbreviation(arr.get(0));
                    newRacer.getTimes().add(LocalDateTime.parse(arr.get(1) + "T" + arr.get(2)));
                    racers.add(newRacer);
                }
            });
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void parseAbbreviationFile(Path pathToFile) {

        try (Stream<String> lines = Files.lines(pathToFile)) {
            lines.map(this::parseAbbreviationLine).forEach(arr -> {
                Optional<Racer> racer = racers.stream().filter(r -> r.getAbbreviation().equals(arr.get(0))).findFirst();
                if (racer.isPresent()) {
                    racer.get().setFullName(arr.get(1));
                    racer.get().setCar(arr.get(2));
                } else {
                    Racer newRacer = new Racer();
                    newRacer.setAbbreviation(arr.get(0));
                    newRacer.setFullName(arr.get(1));
                    newRacer.setCar(arr.get(2));
                    racers.add(newRacer);
                }
            });
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<String> parseLogLine(String line) {
        List<String> groups = new ArrayList<>();
        String regex = "([A-Z]{3})(\\d{4}-\\d{2}-\\d{2})_(\\d{2}:\\d{2}:\\d{2}\\.\\d{3})";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(line);
        if (matcher.find()) {
            groups.add(matcher.group(1));
            groups.add(matcher.group(2));
            groups.add(matcher.group(3));
        }
        return groups;
    }

    private List<String> parseAbbreviationLine(String line) {

        List<String> groups = new ArrayList<>();
        String regex = "([A-Z]{3})_(\\w.+?)_(\\w.+)";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(line);
        if (matcher.find()) {
            groups.add(matcher.group(1));
            groups.add(matcher.group(2));
            groups.add(matcher.group(3));
        }

        return groups;
    }


}
