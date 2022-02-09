package com.tsf.avails.frameavails.avails.controller;

import com.tsf.avails.frameavails.avails.domain.FrameDetails;
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

@RestController
@RequestMapping("/frames")
@Slf4j
public class FramesController {

    private FrameAvailsService frameAvailsService;

    @Autowired
    public FramesController(FrameAvailsService frameService) {
        this.frameAvailsService = frameService;
    }

    @PostMapping("/byIds")
    public ResponseEntity<List<FrameDetails>> fetchFrames(@RequestBody List<String> frameIds) {
        Long startTime = System.currentTimeMillis();
        List<FrameDetails> frameDetails = this.frameAvailsService.fetchFramesFor(frameIds);
        Long endTime = System.currentTimeMillis();
        log.info("Ready to render the response {}", endTime - startTime);
        return new ResponseEntity<>(frameDetails, HttpStatus.OK);
    }

}
