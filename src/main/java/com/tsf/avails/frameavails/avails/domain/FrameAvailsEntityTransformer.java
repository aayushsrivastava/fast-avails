package com.tsf.avails.frameavails.avails.domain;

import com.tsf.avails.frameavails.avails.entity.FrameAvailsEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FrameAvailsEntityTransformer {

    public List<FrameAvailsEntity> fromDomain(FrameAvails frameAvails) {
        String frameId = frameAvails.getFrameId();
        return frameAvails.getAvailStatus().keySet().stream().map(date -> {
            return this.createFrameAvailsEntityRecord(frameId, date, frameAvails.getAvailStatus().get(date));
        }).collect(Collectors.toList());
    }

    public FrameAvails toDomain(List<FrameAvailsEntity> frameAvailsEntities) {
        FrameAvails frameAvails = new FrameAvails();
        frameAvails.setFrameId(frameAvailsEntities.get(0).getFrameId());
        frameAvailsEntities.forEach(f -> frameAvails.addStatus(f.getStartDate(), AvailabilityStatus.valueOf(f.getStatus())));
        return frameAvails;
    }

    private FrameAvailsEntity createFrameAvailsEntityRecord(String frameId, String date, AvailabilityStatus status) {
        return new FrameAvailsEntity(frameId, date, status.toString());
    }
}
