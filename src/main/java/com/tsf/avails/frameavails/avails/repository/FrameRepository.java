package com.tsf.avails.frameavails.avails.repository;

import com.tsf.avails.frameavails.avails.config.CodeExecTimekeeper;
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

    public FrameEntity get(String frameId, CodeExecTimekeeper timeKeeper) {
        List<String> values = timeKeeper.profileExecution("DBQuery:FramesGET", () -> {
            List<String> keys = Arrays.asList("env", "mo", "format", "type", "lat", "lon", "geoid", "city", "statecode", "state", "statelat", "statelon");
            return (List<String>)hashOperations.multiGet(frameId, keys);
        });
        return new FrameEntity(frameId, values);
    }

}
