package com.tsf.avails.frameavails.avails.controller;

import com.tsf.avails.frameavails.avails.domain.AvailabilityStatus;
import com.tsf.avails.frameavails.avails.domain.DateRange;
import com.tsf.avails.frameavails.avails.domain.FrameAvails;
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

import java.util.Map;

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
    public ResponseEntity<FrameAvails> findBulkAvails(@RequestBody BulkAvailsRequest availsRequest) {
        DateRange dateRange = new DateRange(availsRequest.getFromDateTime(), availsRequest.getToDateTime());
        String[] frames = availsRequest.getFrameIds();
        return new ResponseEntity<>(frameAvailsService.fetchAvailsFor(dateRange, frames), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<FrameAvails> createAvails(@RequestBody FrameAvails frameAvails) {
//        FrameAvails frameAvails = new FrameAvails("1234567898", Map.of("12022022", AvailabilityStatus.A));
        frameAvailsService.create(frameAvails);
        return new ResponseEntity<>(frameAvails, HttpStatus.OK);
    }


}
