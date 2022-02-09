package com.tsf.avails.frameavails.avails.repository;

import com.tsf.avails.frameavails.avails.domain.DateRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
@Slf4j
public class FrameAvailsRepository {

    private final ValueOperations<String, String> stringStringValueOperations;

    @Autowired
    public FrameAvailsRepository(StringRedisTemplate redisTemplate) {
        stringStringValueOperations = redisTemplate.opsForValue();
    }

    public Map<String, String> getAvails(DateRange dateRange, List<String> frameIds) {
        String datePrefix = dateRange.lastTuesdayAsDate();
        List<String> keys = frameIds.stream().map(fid -> fid + datePrefix).collect(Collectors.toList());
        Map<String, String> availsMap = new HashMap<>(frameIds.size());
        List<String> availAsString = stringStringValueOperations.multiGet(keys);
        IntStream.range(0, availAsString.size()).forEach(i -> availsMap.put(frameIds.get(i), availAsString.get(i)));
        return availsMap;
    }

}
