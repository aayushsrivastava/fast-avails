package com.tsf.avails.frameavails.avails.controller;

import com.tsf.avails.frameavails.avails.config.CodeExecTimekeeper;
import com.tsf.avails.frameavails.avails.domain.DateRange;
import com.tsf.avails.frameavails.avails.domain.FrameDetails;
import com.tsf.avails.frameavails.avails.presenter.BulkAvailsRequest;
import com.tsf.avails.frameavails.avails.service.FrameAvailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private String lastDataRefreshDate;
    private FrameAvailsService frameAvailsService;
    private CodeExecTimekeeper codeExecTimekeeper;

    @Autowired
    public FrameAvailsController(@Value("${lastDataRefreshddmmyyyy}") String lastDataRefreshDate,
                                 FrameAvailsService frameAvailsService,
                                 CodeExecTimekeeper codeExecTimekeeper) {
        this.lastDataRefreshDate = lastDataRefreshDate;
        this.frameAvailsService = frameAvailsService;
        this.codeExecTimekeeper = codeExecTimekeeper;
    }

    @PostMapping("/fetch")
    public ResponseEntity<List<FrameDetails>> findBulkAvails(@RequestBody BulkAvailsRequest availsRequest) {
        List<FrameDetails> frameDetails = codeExecTimekeeper.profileExecution("ReadyToRender", () -> {
            DateRange dateRange = new DateRange(availsRequest.getFromDateTime(), availsRequest.getToDateTime(), lastDataRefreshDate);
            List<String> frames = availsRequest.getFrameIds();
            try {
                return frameAvailsService.fetchAvailsFor(dateRange, frames);
            } catch (ExecutionException | InterruptedException e) {
                log.error(e.getMessage());
            }
            return null;
        });

        codeExecTimekeeper.logTimes();
        codeExecTimekeeper.resetAll();
        return new ResponseEntity<>(frameDetails, HttpStatus.OK);
    }

}
