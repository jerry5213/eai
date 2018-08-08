package com.zxtech.ecs.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by syp523 on 2018/7/24.
 */

public class BidAttachment {

    private String Guid;
    private String FileName;
    private String Path;
    private String CreateTime;
    private String CreateUserName;

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getCreateTime() {
        return CreateTime != null ? CreateTime.substring(0,10) : CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCreateUserName() {
        return CreateUserName;
    }

    public void setCreateUserName(String createUserName) {
        CreateUserName = createUserName;
    }
}
