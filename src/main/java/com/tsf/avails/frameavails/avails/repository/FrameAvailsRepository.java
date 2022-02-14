package com.tsf.avails.frameavails.avails.repository;

import com.tsf.avails.frameavails.avails.config.CodeExecTimekeeper;
import com.tsf.avails.frameavails.avails.domain.DateRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.zip.GZIPInputStream;

@Repository
@Slf4j
public class FrameAvailsRepository {

    private final ValueOperations<String, String> stringStringValueOperations;
    private String lastRefreshDate;
    private Base64.Decoder decoder;

    @Autowired
    public FrameAvailsRepository(StringRedisTemplate redisTemplate, @Value("${lastDataRefreshddmmyyyy}") String lastRefreshDate) {
        stringStringValueOperations = redisTemplate.opsForValue();
        this.lastRefreshDate = lastRefreshDate;
        this.decoder = Base64.getDecoder();
    }

    public Map<String, String> getAvails(DateRange dateRange, List<String> frameIds, CodeExecTimekeeper codeExecTimekeeper) {
        Map<String, String> availsMap = codeExecTimekeeper.profileExecution("DBQuery:AvailsGET", () -> {
            List<String> keys = frameIds.stream().map(fid -> fid + lastRefreshDate).collect(Collectors.toList());
            Map<String, String> availsMapRaw = new HashMap<>(frameIds.size());
            List<String> availAsString = stringStringValueOperations.multiGet(keys).stream().map(this::decompress).collect(Collectors.toList());
            IntStream.range(0, availAsString.size()).forEach(i -> availsMapRaw.put(frameIds.get(i), availAsString.get(i)));
            return availsMapRaw;
        });

        return availsMap;
    }

    public String decompress(String str) {
        try {
            byte[] decodedVal = decoder.decode(str.getBytes(StandardCharsets.UTF_8));
            GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(decodedVal));
            byte[] bytes = gzipInputStream.readAllBytes();
            return new String(bytes);
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        }

        return "";
    }

}
