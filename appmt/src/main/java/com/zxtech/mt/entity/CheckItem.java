package com.zxtech.mt.entity;




import java.util.Date;

/**
 * Created by Chw on 2016/6/27.
 */
public class CheckItem {

    private String id;
    private String name;
    private String desc;
    private Date updateDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public CheckItem(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }
}
