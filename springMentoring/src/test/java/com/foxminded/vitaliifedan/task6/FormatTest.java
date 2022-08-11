package com.foxminded.vitaliifedan.task6;

import com.foxminded.vitaliifedan.task6.formatters.Format;
import com.foxminded.vitaliifedan.task6.models.Racer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FormatTest {

    @Mock
    Racer racer;

    @Mock
    List<Racer> racers = new ArrayList<>();
    static Format format;

    @BeforeAll
    static void setup() {
        format = new Format();
    }

    @Test
    void Should_ReturnFormattedString_When_GetListOfRacers() {
        when(racers.size()).thenReturn(2);
        when(racers.get(0)).thenReturn(racer);
        when(racers.get(1)).thenReturn(racer);
        when(racer.getFullName()).thenReturn("Daniel Ricciardo");
        when(racer.getCar()).thenReturn("RED BULL RACING TAG HEUER");
        when(racer.getTimeDiff()).thenReturn(LocalDateTime.parse("2018-05-25T12:20:59.963"));
        String actualResult = format.apply(racers, 1);
        String expectedResult = " 1.Daniel Ricciardo     |RED BULL RACING TAG HEUER      |20:59.963\n" +
                "------------------------------------------------------------------\n" +
                " 2.Daniel Ricciardo     |RED BULL RACING TAG HEUER      |20:59.963\n";
        Assertions.assertEquals(expectedResult, actualResult);
    }

}
