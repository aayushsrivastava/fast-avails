package com.tsf.avails.frameavails.avails.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@RedisHash("FrameAvails")
public class FrameAvailsEntity implements Serializable {

    private String availsId;
    private String status;

}
