package com.tsf.avails.frameavails.avails.service;

import com.tsf.avails.frameavails.avails.domain.FrameDetails;
import com.tsf.avails.frameavails.avails.entity.FrameEntity;
import com.tsf.avails.frameavails.avails.repository.FrameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FrameService {

    private FrameRepository repository;
    private List<Long> timeKeeper;

    @Autowired
    public FrameService(FrameRepository repository) {
        this.repository = repository;
        this.timeKeeper = new ArrayList<>();
    }

    public List<FrameDetails> fetchFramesFor(String[] frameIds) {
        List<FrameDetails> collect = Arrays.asList(frameIds).stream().map(e -> repository.get(e, timeKeeper)).map(FrameEntity::toDomain).collect(Collectors.toList());
        log.info("Total time taken by db lookup {}", timeKeeper.stream().reduce(0L, Long::sum));
        return collect;
    }

    public void create(FrameDetails frameDetails) {
        repository.create(FrameEntity.fromDomain(frameDetails));
    }
}
