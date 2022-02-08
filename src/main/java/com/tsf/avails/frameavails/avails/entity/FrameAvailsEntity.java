package com.tsf.avails.frameavails.avails.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

@Getter
@AllArgsConstructor
public class FrameAvailsEntity implements Serializable {

    private String frameId;
    private Map<String, String> availsStatus;

}
