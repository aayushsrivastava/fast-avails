package com.tsf.avails.frameavails.avails.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FrameAvails {

    @JsonProperty("frameId")
    private String frameId;

    @JsonProperty("avails")
    private Map<String, AvailabilityStatus> availStatus;

}
