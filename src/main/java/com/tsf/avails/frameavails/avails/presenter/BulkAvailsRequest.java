package com.tsf.avails.frameavails.avails.presenter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BulkAvailsRequest {

    @JsonProperty("fromDateTime")
    private String fromDateTime;

    @JsonProperty("toDateTime")
    private String toDateTime;

    @JsonProperty("frameIds")
    private List<String> frameIds;

}
