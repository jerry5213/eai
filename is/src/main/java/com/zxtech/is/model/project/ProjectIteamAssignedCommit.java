package com.zxtech.is.model.project;

import java.io.Serializable;
import java.util.List;

/**
 * Created by syp661 on 2018/4/23.
 */

public class ProjectIteamAssignedCommit implements Serializable {

    private String peAssigneeGuid;
    private String feAssigneeGuid;
    private String teAssigneeGuid;
    private String taskCheck;
    private List<String> elevatorGuidList;
    private List<String> procInstIdList;
    private List<String> taskIdList;

    public String getPeAssigneeGuid() {
        return peAssigneeGuid;
    }

    public void setPeAssigneeGuid(String peAssigneeGuid) {
        this.peAssigneeGuid = peAssigneeGuid;
    }

    public String getFeAssigneeGuid() {
        return feAssigneeGuid;
    }

    public void setFeAssigneeGuid(String feAssigneeGuid) {
        this.feAssigneeGuid = feAssigneeGuid;
    }

    public String getTeAssigneeGuid() {
        return teAssigneeGuid;
    }

    public void setTeAssigneeGuid(String teAssigneeGuid) {
        this.teAssigneeGuid = teAssigneeGuid;
    }

    public String getTaskCheck() {
        return taskCheck;
    }

    public void setTaskCheck(String taskCheck) {
        this.taskCheck = taskCheck;
    }

    public List<String> getElevatorGuidList() {
        return elevatorGuidList;
    }

    public void setElevatorGuidList(List<String> elevatorGuidList) {
        this.elevatorGuidList = elevatorGuidList;
    }

    public List<String> getProcInstIdList() {
        return procInstIdList;
    }

    public void setProcInstIdList(List<String> procInstIdList) {
        this.procInstIdList = procInstIdList;
    }

    public List<String> getTaskIdList() {
        return taskIdList;
    }

    public void setTaskIdList(List<String> taskIdList) {
        this.taskIdList = taskIdList;
    }
}
