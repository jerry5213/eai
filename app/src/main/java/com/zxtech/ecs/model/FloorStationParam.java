package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/4/3.
 */

public class FloorStationParam {

    private String height;
    private String engineeringFloor;
    private String identifyingFloor;
    private String serviceA;
    private String serviceACode;
    private String serviceC;
    private String serviceCCode;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getEngineeringFloor() {
        return engineeringFloor;
    }

    public void setEngineeringFloor(String engineeringFloor) {
        this.engineeringFloor = engineeringFloor;
    }

    public String getIdentifyingFloor() {
        return identifyingFloor;
    }

    public void setIdentifyingFloor(String identifyingFloor) {
        this.identifyingFloor = identifyingFloor;
    }

    public String getServiceA() {
        return serviceA;
    }

    public void setServiceA(String serviceA) {
        this.serviceA = serviceA;
    }

    public String getServiceC() {
        return serviceC;
    }

    public String getServiceACode() {
        return serviceACode;
    }

    public void setServiceACode(String serviceACode) {
        this.serviceACode = serviceACode;
    }

    public void setServiceC(String serviceC) {
        this.serviceC = serviceC;
    }

    public String getServiceCCode() {
        return serviceCCode;
    }

    public void setServiceCCode(String serviceCCode) {
        this.serviceCCode = serviceCCode;
    }

    public FloorStationParam() {
    }

    public FloorStationParam(String height, String engineeringFloor, String identifyingFloor, String serviceA,String serviceACode, String serviceC,String serviceCCode) {
        this.height = (height == null ? "" : height);
        this.engineeringFloor = engineeringFloor;
        this.identifyingFloor = identifyingFloor;
        this.serviceA = serviceA;
        this.serviceACode = serviceACode;
        this.serviceC = serviceC;
        this.serviceCCode = serviceCCode;
    }
}
