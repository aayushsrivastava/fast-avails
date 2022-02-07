package com.tsf.avails.frameavails.avails.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DateRange {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private LocalDate from;
    private LocalDate to;

    public DateRange(String fromDateTime, String toDateTime) {
        this.from = LocalDate.parse(fromDateTime, formatter);
        this.to = LocalDate.parse(toDateTime, formatter);
    }

    public List<String> allDatesWithinRange() {
        List<String> allDates = new ArrayList<>();
        do {
            allDates.add(from.toString());
            from = from.plusDays(1);
        } while (from.isBefore(to));

        return allDates;
    }
}
