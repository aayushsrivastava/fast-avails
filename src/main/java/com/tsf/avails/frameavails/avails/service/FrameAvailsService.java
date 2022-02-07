package com.tsf.avails.frameavails.avails.service;

import com.tsf.avails.frameavails.avails.domain.DateRange;
import com.tsf.avails.frameavails.avails.domain.FrameAvails;
import com.tsf.avails.frameavails.avails.domain.FrameAvailsEntityTransformer;
import com.tsf.avails.frameavails.avails.entity.FrameAvailsEntity;
import com.tsf.avails.frameavails.avails.repository.FrameAvailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FrameAvailsService {

    private FrameAvailsRepository repository;
    private FrameAvailsEntityTransformer transformer;

    @Autowired
    public FrameAvailsService(FrameAvailsRepository repository) {
        this.repository = repository;
        this.transformer = new FrameAvailsEntityTransformer();
    }

    public List<FrameAvails> fetchAvailsFor(DateRange dateRange, List<String> frameIds) {
        List<FrameAvails> avails = new ArrayList<>();
        for (String frameId : frameIds) {
            List<FrameAvailsEntity> entities = dateRange
                    .allDatesWithinRange()
                    .stream()
                    .map(date -> repository.get(frameId, date))
                    .collect(Collectors.toList());

            avails.add(transformer.toDomain(entities));
        }

        return avails;
    }

    public void create(FrameAvails frameAvails) {
        List<FrameAvailsEntity> frameAvailsEntities = this.transformer.fromDomain(frameAvails);
        frameAvailsEntities.forEach(e -> this.repository.create(e));
    }
}
