package com.tsf.avails.frameavails.avails.entity;

import com.tsf.avails.frameavails.avails.domain.FrameDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FrameEntity implements Serializable {

    private String frameId;
    private String env;
    private String mo;
    private String format;
    private String type;
    private String lat;
    private String lon;
    private String geoid;
    private String city;
    private String statecode;
    private String state;
    private String statelat;
    private String statelon;

    public FrameEntity(String frameId, List<String> values) {
        this.frameId = frameId;
        this.env = values.get(0);
        this.mo = values.get(1);
        this.format = values.get(2);
        this.type = values.get(3);
        this.lat = values.get(4);
        this.lon = values.get(5);
        this.geoid = values.get(6);
        this.city = values.get(7);
        this.statecode = values.get(8);
        this.state = values.get(9);
        this.statelat = values.get(10);
        this.statelon = values.get(11);
    }

    public static FrameEntity fromDomain(FrameDetails frameDetails) {
        return new FrameEntity(frameDetails.getFrameId(), frameDetails.getEnv(), frameDetails.getMo(),
                frameDetails.getFormat(), frameDetails.getType(), frameDetails.getLat(), frameDetails.getLon(),
                frameDetails.getGeoid(), frameDetails.getCity(), frameDetails.getStatecode(), frameDetails.getState(),
                frameDetails.getStatelat(), frameDetails.getStatelon());
    }

    public FrameDetails toDomain() {
        return new FrameDetails(this.frameId, this.env, this.mo, this.format, this.type, this.lat, this.lon, this.geoid, this.city, this.statecode, this.state, this.statelat, this.statelon, new HashMap<>());
    }

    public Map<String, String> allKeyValueMap() {
        return new HashMap<>() {
            {
                put("frameId", frameId);
                put("env", env);
                put("mo", mo);
                put("format", format);
                put("type", type);
                put("lat", lat);
                put("lon", lon);
                put("geoid", geoid);
                put("city", city);
                put("statecode", statecode);
                put("state", state);
                put("statelat", statelat);
                put("statelon", statelon);
            }
        };
    }
}
