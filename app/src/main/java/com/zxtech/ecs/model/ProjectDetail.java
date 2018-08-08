package com.zxtech.ecs.model;

/**
 * Created by syp600 on 2018/7/6.
 */

public class ProjectDetail {

    private String detailGuid;

    public String getDetailGuid() {
        return detailGuid;
    }

    public void setDetailGuid(String detailGuid) {
        this.detailGuid = detailGuid;
    }

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    public String getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(String projectStage) {
        this.projectStage = projectStage;
    }

    public String getExSignDate() {
        return exSignDate;
    }

    public void setExSignDate(String exSignDate) {
        this.exSignDate = exSignDate;
    }

    public String getExPayDate() {
        return exPayDate;
    }

    public void setExPayDate(String exPayDate) {
        this.exPayDate = exPayDate;
    }

    public String getExDeliveryDate() {
        return exDeliveryDate;
    }

    public void setExDeliveryDate(String exDeliveryDate) {
        this.exDeliveryDate = exDeliveryDate;
    }

    public String getExEQSDate() {
        return exEQSDate;
    }

    public void setExEQSDate(String exEQSDate) {
        this.exEQSDate = exEQSDate;
    }

    private String projectState;
    private String projectStage;
    private String exSignDate;
    private String exPayDate;
    private String exDeliveryDate;
    private String exEQSDate;


}
