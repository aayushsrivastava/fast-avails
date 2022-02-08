package com.tsf.avails.frameavails.avails.controller;

import com.tsf.avails.frameavails.avails.domain.FrameDetails;
import com.tsf.avails.frameavails.avails.service.FrameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/frames")
@Slf4j
public class FramesController {

    private FrameService frameService;

    @Autowired
    public FramesController(FrameService frameService) {
        this.frameService = frameService;
    }

    @PostMapping("/byIds")
    public ResponseEntity<List<FrameDetails>> fetchFrames(@RequestBody String[] frameIds) {
        Long startTime = System.currentTimeMillis();
        List<FrameDetails> frameDetails = this.frameService.fetchFramesFor(frameIds);
        Long endTime = System.currentTimeMillis();
        log.info("Ready to render the response {}", endTime-startTime);
        return new ResponseEntity<>(frameDetails, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody String fileName) {
        readData(fileName);
        return new ResponseEntity<>("DONE!", HttpStatus.OK);
    }

    public void readData(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                this.frameService.create(FrameDetails.fromText(sc.nextLine()));
            }
            sc.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
