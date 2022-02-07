package com.tsf.avails.frameavails.avails.service;

import com.tsf.avails.frameavails.avails.domain.DateRange;
import com.tsf.avails.frameavails.avails.domain.FrameAvails;
import com.tsf.avails.frameavails.avails.domain.FrameAvailsEntityCreator;
import com.tsf.avails.frameavails.avails.entity.FrameAvailsEntity;
import com.tsf.avails.frameavails.avails.repository.FrameAvailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrameAvailsService {

    private FrameAvailsRepository respository;
    private FrameAvailsEntityCreator creator;

    @Autowired
    public FrameAvailsService(FrameAvailsRepository repository) {
        this.respository = repository;
        this.creator = new FrameAvailsEntityCreator();
    }

    public FrameAvails fetchAvailsFor(DateRange frames, String[] dateRange) {
        return null;
    }

    public void create(FrameAvails frameAvails) {
        List<FrameAvailsEntity> frameAvailsEntities = this.creator.fromDomain(frameAvails);
        frameAvailsEntities.forEach(e -> this.respository.create(e));
    }
}
