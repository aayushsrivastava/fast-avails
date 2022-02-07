package com.tsf.avails.frameavails.avails.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DateRange {

    //    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//    private LocalDate from;
//    private LocalDate to;
    private String fromDateTime;
    private String toDateTime;

    public DateRange(String fromDateTime, String toDateTime) {
//        this.from = LocalDate.parse(fromDateTime, formatter);
//        this.to = LocalDate.parse(toDateTime, formatter);
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

//    public List<String> allDatesWithinRange() {
//        List<String> allDates = new ArrayList<>();
//        do {
//            allDates.add(from.toString());
//            from = from.plusDays(1);
//        } while (from.isBefore(to));
//
//        return allDates;
//    }
}
