package com.tsf.avails.frameavails.avails.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@Component
public class CodeExecTimekeeper {

    private Map<String, Long> executionTimeMap;

    public CodeExecTimekeeper() {
        this.executionTimeMap = new HashMap<>();
    }

    public <T> T profileExecution(String name, Supplier<T> supplier) {
        long startTime = System.currentTimeMillis();
        T returnVal = supplier.get();
        long endTime = System.currentTimeMillis();
        this.executionTimeMap.put(name, this.executionTimeMap.getOrDefault(name, 0L) + (endTime - startTime));
        return returnVal;
    }

    public void logTimes() {
        this.executionTimeMap.keySet().forEach(k -> log.info("Total time for {} op is {} ms", k, executionTimeMap.get(k)));
    }

    public void resetFor(String key) {
        this.executionTimeMap.remove(key);
    }

    public void resetAll() {
        this.executionTimeMap.clear();
    }

}
