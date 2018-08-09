package com.zxtech.is.model.s2;

public class S2Request {

    //项目guid
    private String projectGuid;
    //流程key
    private String procDefKey;
    //任务id
    private String taskId;
    //分类
    private String category;
    //任务key
    private String taskDefKey;

    private S2Elevator s2Elevator;

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getProcDefKey() {
        return procDefKey;
    }

    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public S2Elevator getS2Elevator() {
        return s2Elevator;
    }

    public void setS2Elevator(S2Elevator s2Elevator) {
        this.s2Elevator = s2Elevator;
    }
}
