package com.tsf.avails.frameavails.avails.domain;

import com.tsf.avails.frameavails.avails.config.CodeExecTimekeeper;
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
    private Map<String, String> availsDetails;

    public static FrameDetails fromText(String nextLine) {
        String[] tokens = nextLine.split(",");
        return new FrameDetails(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[8], tokens[9], tokens[10], tokens[11], tokens[12], new HashMap<>(4370));
    }

    public void populateAvails(String availsAsString, DateRange dateRange, CodeExecTimekeeper codeExecTimekeeper) {
        if ("classic".equals(this.type)) {
            codeExecTimekeeper.profileExecution("AllocateObjClassicAvails", () -> {
                populateClassicAvails(availsAsString.substring(dateRange.getClassicStartPos(), 1 + dateRange.getClassicEndPos()));
                return null;
            });

        } else if ("digital".equals(this.type)) {
            codeExecTimekeeper.profileExecution("AllocateObjDigitalAvails", () -> {
                populateDigitalAvails(availsAsString.substring(dateRange.getDigitalStartPos(), dateRange.getDigitalEndPos()), dateRange);
                return null;
            });
        }
    }

    private void populateClassicAvails(String availsAsString) {
        for (int i = 0; i < availsAsString.length(); i++) {
            availsDetails.put("1022022_" + i, availsAsString.substring(i, i + 1));
        }
    }

    public void populateDigitalAvails(String availsAsString, DateRange dateRange) {
        for (int i = 0; i < availsAsString.length(); i = i + 9) {
            StringBuilder availsData = new StringBuilder(25);
            availsData.append(availsAsString, i, i + 3).append("|");
            availsData.append(availsAsString, i + 3, i + 6).append("|");
            availsData.append(availsAsString, i + 6, i + 9);
            availsDetails.put("1022022_" + i, availsData.toString());
        }
    }

}
