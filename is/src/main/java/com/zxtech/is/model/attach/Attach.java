package com.zxtech.is.model.attach;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.zxtech.is.net.BaseResponse;

import java.io.Serializable;

/**
 * Created by syp600 on 2018/4/21.
 */

public class Attach implements Serializable {

    private String filename;
    private String filetype;
    private String filepath;
    private String createUser;
    private String createTime;
    private String guid;
    private String attachguid;
    private String createUserName;
    private String category;
    private Bitmap bitmap;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAttachguid() {
        return attachguid;
    }

    public void setAttachguid(String attachguid) {
        this.attachguid = attachguid;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
