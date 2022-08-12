package com.foxminded.vitaliifedan.task6;

import com.foxminded.vitaliifedan.task6.models.Racer;
import com.foxminded.vitaliifedan.task6.parsers.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class ParserTest {
    Path pathToFileFolder = Paths.get(
            "src", "test", "resources", "task6");

    Path startLogs = pathToFileFolder.resolve("start.log");
    Path endLogs = pathToFileFolder.resolve("end.log");
    Path abbreviations = pathToFileFolder.resolve("abbreviations.txt");
    static Parser parser;

    @BeforeAll
    static void setup() {
        parser = new Parser();
    }

    @Test
    void should_ReturnListOfRacers_When_GetPathToFiles() {
        Racer racer1 = new Racer(
                "SVF",
                "Sebastian Vettel",
                "FERRARI",
                Arrays.asList(LocalDateTime.parse("2018-05-24T12:02:58.917"), LocalDateTime.parse("2018-05-24T12:04:03.332"))
        );
        Racer racer2 = new Racer(
                "BHS",
                "Brendon Hartley",
                "SCUDERIA TORO ROSSO HONDA",
                Arrays.asList(LocalDateTime.parse("2018-05-24T12:14:51.985"), LocalDateTime.parse("2018-05-24T12:16:05.164"))
        );

        List<Racer> actualResult = parser.parseFiles(startLogs, endLogs, abbreviations);
        List<Racer> expectedResult = Arrays.asList(racer1, racer2);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
