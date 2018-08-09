package com.zxtech.is.model.s1;

public class S1Request {

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

    private S1Elevator s1Elevator;

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

    public S1Elevator getS1Elevator() {
        return s1Elevator;
    }

    public void setS1Elevator(S1Elevator s1Elevator) {
        this.s1Elevator = s1Elevator;
    }
}
