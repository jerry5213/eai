package com.zxtech.is.model.smt;

import java.util.Date;

public class IsSmtInstallationElevator {

    private String guid;
    private String elevatorguid;
    private String procinstid;
    private Integer status;
    private Integer isvalid;
    private String scaffold;
    private String installtype;
    private Boolean delFlg;
    private Date createTime;
    private String createUser;
    private Date lastUpdateTime;
    private String lastUpdateUser;
    private String taskId;


    /**
     * @return guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     * @param guid
     */
    public void setGuid(String guid) {
        this.guid = guid == null ? null : guid.trim();
    }

    /**
     * @return elevatorGuid
     */
    public String getElevatorguid() {
        return elevatorguid;
    }

    /**
     * @param elevatorguid
     */
    public void setElevatorguid(String elevatorguid) {
        this.elevatorguid = elevatorguid == null ? null : elevatorguid.trim();
    }

    /**
     * @return procInstId
     */
    public String getProcinstid() {
        return procinstid;
    }

    /**
     * @param procinstid
     */
    public void setProcinstid(String procinstid) {
        this.procinstid = procinstid == null ? null : procinstid.trim();
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return isValid
     */
    public Integer getIsvalid() {
        return isvalid;
    }

    /**
     * @param isvalid
     */
    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }

    /**
     * @return scaffold
     */
    public String getScaffold() {
        return scaffold;
    }

    /**
     * @param scaffold
     */
    public void setScaffold(String scaffold) {
        this.scaffold = scaffold == null ? null : scaffold.trim();
    }

    /**
     * @return installType
     */
    public String getInstalltype() {
        return installtype;
    }

    /**
     * @param installtype
     */
    public void setInstalltype(String installtype) {
        this.installtype = installtype == null ? null : installtype.trim();
    }

    /**
     * @return del_flg
     */
    public Boolean getDelFlg() {
        return delFlg;
    }

    /**
     * @param delFlg
     */
    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * @return last_update_time
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * @return last_update_user
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * @param lastUpdateUser
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser == null ? null : lastUpdateUser.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}