package com.foxminded.vitaliifedan.task6;

import com.foxminded.vitaliifedan.task6.formatters.Format;
import com.foxminded.vitaliifedan.task6.models.Racer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class FormatTest {
    static Format format;

    @BeforeAll
    static void setup() {
        format = new Format();
    }

    @Test
    void should_ReturnFormattedString_When_GetListOfRacers() {
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
        Racer racer3 = new Racer(
                "SPF",
                "Sergio Perez",
                "FORCE INDIA MERCEDES",
                Arrays.asList(LocalDateTime.parse("2018-05-24T12:12:01.035"), LocalDateTime.parse("2018-05-24T12:13:13.883"))
        );
        List<Racer> racers = Arrays.asList(racer1, racer2, racer3);
        String actualResult = format.apply(racers, 2);
        String expectedResult = " 1.Sebastian Vettel     |FERRARI                        |1:04.415\n" +
                " 2.Brendon Hartley      |SCUDERIA TORO ROSSO HONDA      |1:13.179\n" +
                "-----------------------------------------------------------------\n" +
                " 3.Sergio Perez         |FORCE INDIA MERCEDES           |1:12.848\n";
        Assertions.assertEquals(expectedResult, actualResult);
    }

}
