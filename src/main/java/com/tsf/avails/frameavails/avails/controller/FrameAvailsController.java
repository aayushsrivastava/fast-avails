package com.tsf.avails.frameavails.avails.controller;

import com.tsf.avails.frameavails.avails.domain.DateRange;
import com.tsf.avails.frameavails.avails.domain.FrameDetails;
import com.tsf.avails.frameavails.avails.presenter.BulkAvailsRequest;
import com.tsf.avails.frameavails.avails.service.FrameAvailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/avails")
public class FrameAvailsController {

    private FrameAvailsService frameAvailsService;

    @Autowired
    public FrameAvailsController(FrameAvailsService frameAvailsService) {
        this.frameAvailsService = frameAvailsService;
    }

    @PostMapping("/fetch")
    public ResponseEntity<List<FrameDetails>> findBulkAvails(@RequestBody BulkAvailsRequest availsRequest) throws ExecutionException, InterruptedException {
        Long startTime = System.currentTimeMillis();
        DateRange dateRange = new DateRange(availsRequest.getFromDateTime(), availsRequest.getToDateTime());
        List<String> frames = availsRequest.getFrameIds();
        List<FrameDetails> frameDetails = frameAvailsService.fetchAvailsFor(dateRange, frames);
        Long endTime = System.currentTimeMillis();
        log.info("Ready to render the response {}", endTime - startTime);
        return new ResponseEntity<>(frameDetails, HttpStatus.OK);
    }

}
