package com.tsf.avails.frameavails.avails.repository;

import com.tsf.avails.frameavails.avails.entity.FrameEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
@Slf4j
public class FrameRepository {

    private final HashOperations hashOperations;

    @Autowired
    public FrameRepository(RedisTemplate<String, FrameEntity> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }

    public FrameEntity get(String frameId, List<Long> timeKeeper) {
        Long startTime = System.currentTimeMillis();
        List<String> keys = Arrays.asList("env", "mo", "format", "type", "lat", "lon", "geoid", "city", "statecode", "state", "statelat", "statelon");
        List<String> values = hashOperations.multiGet(frameId, keys);
        Long endTime = System.currentTimeMillis();
        Long timeTaken = endTime - startTime;
        timeKeeper.add(timeTaken);
        return new FrameEntity(frameId, values);
    }

}
