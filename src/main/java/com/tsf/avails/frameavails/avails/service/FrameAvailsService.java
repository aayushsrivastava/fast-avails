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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FrameAvailsService {

    private final ExecutorService executorService;
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
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public List<FrameDetails> fetchFramesFor(List<String> frameIds) {
        List<FrameDetails> frameDetails = frameIds.stream().map(e -> frameRepository.get(e, timeKeeper)).map(FrameEntity::toDomain).collect(Collectors.toList());
        log.info("Total time taken by db lookup {}", timeKeeper.stream().reduce(0L, Long::sum));
        timeKeeper.clear();
        return frameDetails;
    }

    public List<FrameDetails> fetchAvailsFor(DateRange dateRange, List<String> frameIds) throws ExecutionException, InterruptedException {
        FutureTask<List<FrameDetails>> frameFetch = new FutureTask<>(() -> fetchFramesFor(frameIds));
        FutureTask<Map<String, String>> availsFetch = new FutureTask<>(() -> frameAvailsRepository.getAvails(dateRange, frameIds));
        executorService.submit(frameFetch);
        executorService.submit(availsFetch);
        List<FrameDetails> frameDetails = frameFetch.get();
        Map<String, String> availsMap = availsFetch.get();
        frameDetails.forEach(f -> f.populateAvails(availsMap.get(f.getFrameId()), dateRange));
        return frameDetails;
    }

    public void create(FrameAvails frameAvails) {
        List<FrameAvailsEntity> frameAvailsEntities = this.transformer.fromDomain(frameAvails);
        frameAvailsEntities.forEach(e -> this.frameAvailsRepository.create(e));
    }

}
