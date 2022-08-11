package com.foxminded.vitaliifedan.task6.models;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Racer implements Comparable<Racer> {

    private String abbreviation;
    private String fullName;
    private String car;
    private List<LocalDateTime> times;

    public Racer() {
        this.times = new ArrayList<>();
    }

    public Racer(String abbreviation, String fullName, String car, List<LocalDateTime> times) {
        this.abbreviation = abbreviation;
        this.fullName = fullName;
        this.car = car;
        this.times = times;
    }

    public List<LocalDateTime> getTimes() {
        return times;
    }

    public void setTimes(List<LocalDateTime> times) {
        this.times = times;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getCar() {
        return car;
    }

    public LocalDateTime getTimeDiff() {
        long startDateToMilli = times.get(0).toInstant(ZoneOffset.UTC).toEpochMilli();
        return times.get(1).minus(startDateToMilli, ChronoUnit.MILLIS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Racer racer = (Racer) o;
        return Objects.equals(abbreviation, racer.abbreviation) && Objects.equals(fullName, racer.fullName) && Objects.equals(car, racer.car) && Objects.equals(times, racer.times);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abbreviation, fullName, car, times);
    }

    @Override
    public int compareTo(Racer o) {
        return this.getTimeDiff().compareTo(o.getTimeDiff());
    }

    @Override
    public String toString() {
        return "Racer{" +
                "abbreviation='" + abbreviation + '\'' +
                ", fullName='" + fullName + '\'' +
                ", car='" + car + '\'' +
                ", times=" + times +
                '}';
    }
}
