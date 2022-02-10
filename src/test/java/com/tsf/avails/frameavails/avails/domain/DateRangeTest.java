package com.tsf.avails.frameavails.avails.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeTest {

    private DateRange dateRange;

    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldCalculateIndexForStartDateAsAvailStartDate() {
        this.dateRange = new DateRange("01022022", "01032022");

        assertEquals(dateRange.getClassicStartPos(), 0);
    }

    @Test
    void shouldCalculateIndexForStartDateTwoWeeksAwayFromStartDate() {
        this.dateRange = new DateRange("08022022", "01032022");

        assertEquals(dateRange.getClassicStartPos(), 1);
    }

    @Test
    void shouldCalculateIndexForStartDateTwoAndHalfWeeksFromStartDate() {
        this.dateRange = new DateRange("18022022", "01032022");

        assertEquals(dateRange.getClassicStartPos(), 2);
    }

}