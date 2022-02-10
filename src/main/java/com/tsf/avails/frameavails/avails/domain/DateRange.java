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

    public DateRange(String fromDateTime, String toDateTime) {
        this.from = LocalDate.parse(fromDateTime, formatter);
        this.to = LocalDate.parse(toDateTime, formatter);
    }

//    public List<String> allDatesWithinRange() {
//        LocalDate currentFrom = from;
//        LocalDate currentTo = to;
//        List<String> allDates = new ArrayList<>();
//        do {
//            allDates.add(currentFrom.format(formatter));
//            currentFrom = currentFrom.plusDays(1);
//        } while (currentFrom.isBefore(currentTo));
//
//        return allDates;
//    }

    public String lastTuesdayAsDate() {
        return "01022022";
    }

    public int getClassicStartPos() {
        LocalDate availsStart = LocalDate.parse(lastTuesdayAsDate(), formatter);
        long daysBetween = ChronoUnit.DAYS.between(availsStart, from);
        return (int) (daysBetween / 7);
    }

    public int getClassicEndPos() {
        LocalDate availsStart = LocalDate.parse(lastTuesdayAsDate(), formatter);
        long daysBetween = ChronoUnit.DAYS.between(availsStart, to);
        return (int) (daysBetween / 7);
    }

    public int getDigitalEndPos() {
        LocalDate availsStart = LocalDate.parse(lastTuesdayAsDate(), formatter);
        long daysBetween = ChronoUnit.DAYS.between(availsStart, to);
        return (int) ((1+daysBetween)*24*9);
    }

    public int getDigitalStartPos() {
        LocalDate availsStart = LocalDate.parse(lastTuesdayAsDate(), formatter);
        long daysBetween = ChronoUnit.DAYS.between(availsStart, from);
        return (int) (daysBetween*24*9);
    }
}
