package com.tsf.avails.frameavails.avails.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FrameDetails {

    private String frameId;
    private String env;
    private String mo;
    private String format;
    private String type;
    private String lat;
    private String lon;
    private String geoid;
    private String city;
    private String statecode;
    private String state;
    private String statelat;
    private String statelon;
    private Map<String, AvailabilityStatus> availsDetails;

    public static FrameDetails fromText(String nextLine) {
        String[] tokens = nextLine.split(",");
        return new FrameDetails(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[8], tokens[9], tokens[10], tokens[11], tokens[12], new HashMap<>());
    }

    public void populateAvails(String availsAsString, DateRange dateRange) {
        for (int i = 0; i < availsAsString.length(); i++) {
            availsDetails.put("1022022" + i, AvailabilityStatus.valueOf(availsAsString.substring(i, i + 1)));
        }
    }
}
