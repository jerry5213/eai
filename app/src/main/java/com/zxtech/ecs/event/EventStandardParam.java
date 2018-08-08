package com.zxtech.ecs.event;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by syp523 on 2018/5/28.
 */

public class EventStandardParam {

    private Map<String,String> param = new HashMap<>();

    public EventStandardParam() {
    }

    public EventStandardParam(Map<String, String> param) {
        this.param = param;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }
}
