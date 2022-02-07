package com.tsf.avails.frameavails.avails.domain;

import com.tsf.avails.frameavails.avails.entity.FrameAvailsEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FrameAvailsEntityCreator {

    public List<FrameAvailsEntity> fromDomain(FrameAvails frameAvails) {
        String frameId = frameAvails.getFrameId();
        return frameAvails.getAvailStatus().keySet().stream().map(date -> {
            return this.createFrameAvailsEntityRecord(frameId, date, frameAvails.getAvailStatus().get(date));
        }).collect(Collectors.toList());
    }

    private FrameAvailsEntity createFrameAvailsEntityRecord(String frameId, String date, AvailabilityStatus status) {
        return new FrameAvailsEntity(frameId + date, status.toString());
    }
}
