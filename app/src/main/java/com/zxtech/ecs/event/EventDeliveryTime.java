package com.zxtech.ecs.event;

/**
 * Created by SYP521 on 2018/8/3.
 * 排产交期
 */

public class EventDeliveryTime {

    private String ExpectedTime = "";
    private String PlannedTime = "";
    private String RealTime = "";

    public String getExpectedTime() {
        return ExpectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        ExpectedTime = expectedTime;
    }

    public String getPlannedTime() {
        return PlannedTime;
    }

    public void setPlannedTime(String plannedTime) {
        PlannedTime = plannedTime;
    }

    public String getRealTime() {
        return RealTime;
    }

    public void setRealTime(String realTime) {
        RealTime = realTime;
    }
}
