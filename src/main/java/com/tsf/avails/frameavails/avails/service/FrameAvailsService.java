package com.tsf.avails.frameavails.avails.service;

import com.tsf.avails.frameavails.avails.config.CodeExecTimekeeper;
import com.tsf.avails.frameavails.avails.domain.DateRange;
import com.tsf.avails.frameavails.avails.domain.FrameDetails;
import com.tsf.avails.frameavails.avails.entity.FrameEntity;
import com.tsf.avails.frameavails.avails.repository.FrameAvailsRepository;
import com.tsf.avails.frameavails.avails.repository.FrameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private CodeExecTimekeeper codeExecTimekeeper;
    private FrameAvailsRepository frameAvailsRepository;

    @Autowired
    public FrameAvailsService(FrameAvailsRepository repository, FrameRepository frameRepository, CodeExecTimekeeper codeExecTimekeeper) {
        this.frameAvailsRepository = repository;
        this.frameRepository = frameRepository;
        this.codeExecTimekeeper = codeExecTimekeeper;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public List<FrameDetails> fetchFramesFor(List<String> frameIds) {
        return frameIds.stream().map(e -> frameRepository.get(e, codeExecTimekeeper)).map(FrameEntity::toDomain).collect(Collectors.toList());
    }

    public List<FrameDetails> fetchAvailsFor(DateRange dateRange, List<String> frameIds) throws ExecutionException, InterruptedException {
        FutureTask<List<FrameDetails>> frameFetch = new FutureTask<>(() -> fetchFramesFor(frameIds));
        FutureTask<Map<String, String>> availsFetch = new FutureTask<>(() -> frameAvailsRepository.getAvails(dateRange, frameIds, codeExecTimekeeper));
        executorService.submit(frameFetch);
        executorService.submit(availsFetch);
        List<FrameDetails> frameDetails = frameFetch.get();
        Map<String, String> availsMap = availsFetch.get();
        frameDetails.forEach(f -> f.populateAvails(availsMap.get(f.getFrameId()), dateRange, codeExecTimekeeper));
        return frameDetails;
    }

}
