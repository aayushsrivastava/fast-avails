package com.tsf.avails.frameavails.avails.domain;

public enum AvailabilityStatus {
    A("available"),
    R("reserved"),
    B("booked");

    private String description;

    AvailabilityStatus(String description) {
        this.description = description;
    }
}
