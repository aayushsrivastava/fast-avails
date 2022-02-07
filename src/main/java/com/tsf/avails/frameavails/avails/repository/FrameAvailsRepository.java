package com.tsf.avails.frameavails.avails.repository;

import com.tsf.avails.frameavails.avails.domain.FrameAvails;
import com.tsf.avails.frameavails.avails.entity.FrameAvailsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class FrameAvailsRepository {

    private final HashOperations hashOperations;

    @Autowired
    public FrameAvailsRepository(RedisTemplate<String, FrameAvails> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }

    public void create(FrameAvailsEntity avails) {
        hashOperations.put(avails.getAvailsId(), "status", avails.getStatus());
        log.info(String.format("Avails with ID %s saved", avails.getAvailsId()));
    }

    public FrameAvailsEntity get(String availsId) {
        return (FrameAvailsEntity) hashOperations.get(availsId, "status");
    }

}
