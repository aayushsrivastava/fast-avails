package com.tsf.avails.frameavails.avails.domain;

public enum AvailabilityStatus {
    A("available"),
    R("reserved"),
    B("booked");

    private String status;

    AvailabilityStatus(String description) {

        this.status = description;
    }
}
