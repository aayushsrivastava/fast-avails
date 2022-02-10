package com.tsf.avails.frameavails.avails.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
public class DateRange {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
    private LocalDate from;
    private LocalDate to;
    private String lastDataRefreshDate;

    public DateRange(String fromDateTime, String toDateTime, String lastDataRefreshDate) {
        this.from = LocalDate.parse(fromDateTime, formatter);
        this.to = LocalDate.parse(toDateTime, formatter);
        this.lastDataRefreshDate = lastDataRefreshDate;
    }

    public int getClassicStartPos() {
        LocalDate availsStart = LocalDate.parse(lastDataRefreshDate, formatter);
        long daysBetween = ChronoUnit.DAYS.between(availsStart, from);
        return (int) (daysBetween / 7);
    }

    public int getClassicEndPos() {
        LocalDate availsStart = LocalDate.parse(lastDataRefreshDate, formatter);
        long daysBetween = ChronoUnit.DAYS.between(availsStart, to);
        return (int) (daysBetween / 7);
    }

    public int getDigitalEndPos() {
        LocalDate availsStart = LocalDate.parse(lastDataRefreshDate, formatter);
        long daysBetween = ChronoUnit.DAYS.between(availsStart, to);
        return (int) ((1+daysBetween)*24*9);
    }

    public int getDigitalStartPos() {
        LocalDate availsStart = LocalDate.parse(lastDataRefreshDate, formatter);
        long daysBetween = ChronoUnit.DAYS.between(availsStart, from);
        return (int) (daysBetween*24*9);
    }
}
