package com.tsf.avails.frameavails.avails.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeTest {

    private DateRange dateRange;
    private final int BYTES_NEEDED_FOR_ONE_AVAIL_RECORD = 9;

    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldCalculateIndexForStartDateAsAvailStartDate() {
        this.dateRange = new DateRange("01022022", "01032022");

        assertEquals(dateRange.getClassicStartPos(), 0);
    }

    @Test
    void shouldCalculateIndexForStartDateOneWeekAwayFromAvailsStartDate() {
        this.dateRange = new DateRange("08022022", "01032022");

        assertEquals(dateRange.getClassicStartPos(), 1);
    }

    @Test
    void shouldCalculateIndexForStartDateTwoAndHalfWeeksFromStartDate() {
        this.dateRange = new DateRange("18022022", "01032022");

        assertEquals(dateRange.getClassicStartPos(), 2);
    }

    @Test
    void shouldCalculateIndexForDigitalStartDateSameAsAvailsStartDate() {
        this.dateRange = new DateRange("01022022", "01032022");

        assertEquals(dateRange.getDigitalStartPos(), 0);
    }

    @Test
    void shouldCalculateIndexForDigitalStartDateOneDayFromAvailsStartDate() {
        this.dateRange = new DateRange("02022022", "01032022");

        assertEquals(dateRange.getDigitalStartPos(), 24 * BYTES_NEEDED_FOR_ONE_AVAIL_RECORD);
    }

    @Test
    void shouldCalculateIndexForDigitalEndDateOneDayFromAvailsStartDate() {
        this.dateRange = new DateRange("01022022", "02022022");

        assertEquals(dateRange.getDigitalEndPos(), 48 * BYTES_NEEDED_FOR_ONE_AVAIL_RECORD);
    }
}