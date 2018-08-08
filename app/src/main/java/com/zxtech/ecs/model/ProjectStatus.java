package com.zxtech.ecs.model;

/**
 * Created by syp521 on 2018/2/7.
 */

public class ProjectStatus {

    /**
     * sort : 1
     * statusId : P-Doing
     * statusName : 报备中
     */

    private int sort;
    private String statusId;
    private String statusName;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }
}
