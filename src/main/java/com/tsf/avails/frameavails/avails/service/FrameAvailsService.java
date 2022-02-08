package com.tsf.avails.frameavails.avails.service;

import com.tsf.avails.frameavails.avails.domain.DateRange;
import com.tsf.avails.frameavails.avails.domain.FrameAvails;
import com.tsf.avails.frameavails.avails.domain.FrameAvailsEntityTransformer;
import com.tsf.avails.frameavails.avails.domain.FrameDetails;
import com.tsf.avails.frameavails.avails.entity.FrameAvailsEntity;
import com.tsf.avails.frameavails.avails.entity.FrameEntity;
import com.tsf.avails.frameavails.avails.repository.FrameAvailsRepository;
import com.tsf.avails.frameavails.avails.repository.FrameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FrameAvailsService {

    private FrameRepository frameRepository;
    private FrameAvailsRepository frameAvailsRepository;
    private FrameAvailsEntityTransformer transformer;
    private List<Long> timeKeeper;

    @Autowired
    public FrameAvailsService(FrameAvailsRepository repository, FrameRepository frameRepository) {
        this.frameAvailsRepository = repository;
        this.frameRepository = frameRepository;
        this.transformer = new FrameAvailsEntityTransformer();
        this.timeKeeper = new ArrayList<>();
    }

    public List<FrameDetails> fetchFramesFor(List<String> frameIds) {
        List<FrameDetails> frameDetails = frameIds.stream().map(e -> frameRepository.get(e, timeKeeper)).map(FrameEntity::toDomain).collect(Collectors.toList());
        log.info("Total time taken by db lookup {}", timeKeeper.stream().reduce(0L, Long::sum));
        return frameDetails;
    }

    public List<FrameDetails> fetchAvailsFor(DateRange dateRange, List<String> frameIds) {
        List<FrameDetails> frameDetails = fetchFramesFor(frameIds);
        Map<String, String> frameAvailsMap = frameAvailsRepository.get(dateRange, frameIds);
        frameDetails.forEach(f -> f.populateAvails(frameAvailsMap.get(f.getFrameId()), dateRange));
        return frameDetails;
    }

    public void create(FrameAvails frameAvails) {
        List<FrameAvailsEntity> frameAvailsEntities = this.transformer.fromDomain(frameAvails);
        frameAvailsEntities.forEach(e -> this.frameAvailsRepository.create(e));
    }
}
