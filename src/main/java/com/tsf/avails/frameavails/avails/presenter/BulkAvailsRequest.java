package com.tsf.avails.frameavails.avails.presenter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BulkAvailsRequest {

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("recordsPerPage")
    private Integer recordsPerPage;

    @JsonProperty("fromDateTime")
    private String fromDateTime;

    @JsonProperty("toDateTime")
    private String toDateTime;

    @JsonProperty("frameIds")
    private String[] frameIds;

}
