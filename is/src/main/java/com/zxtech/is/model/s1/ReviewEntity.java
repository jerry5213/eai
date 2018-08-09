package com.zxtech.is.model.s1;

import java.util.List;

/**
 * Created by syp600 on 2018/5/4.
 */

public class ReviewEntity {

    //流程id
    private String procinstid;
    //任务id
    private String taskId;
    //提交选项 0.驳回 1.通过
    private String checkResult;
    //审核意见
    private String comments;

    public String getProcinstid() {
        return procinstid;
    }

    public void setProcinstid(String procinstid) {
        this.procinstid = procinstid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
