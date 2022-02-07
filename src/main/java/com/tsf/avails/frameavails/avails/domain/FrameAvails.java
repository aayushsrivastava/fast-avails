package com.tsf.avails.frameavails.avails.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FrameAvails {

    @JsonProperty("frameId")
    private String frameId;

    @JsonProperty("avails")
    private Map<String, AvailabilityStatus> availStatus = new HashMap<>();

    public void addStatus(String date, AvailabilityStatus status) {
        this.availStatus.put(date, status);
    }
}
