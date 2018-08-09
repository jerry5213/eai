package com.zxtech.mt.entity;

/**
 * Created by Chw on 2016/7/11.
 */
public class MtWorkItem {
    private String id;
    private String remark;

    private String result;

    private String result_id;

    private String item_code;

    private String item_content;

    private String item_type;

    private String version_id;

    private String courseguid;

    public String getCourseguid() {
        return courseguid;
    }

    public void setCourseguid(String courseguid) {
        this.courseguid = courseguid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    private String coursename;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult_id() {
        return result_id;
    }

    public void setResult_id(String result_id) {
        this.result_id = result_id;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_content() {
        return item_content;
    }

    public void setItem_content(String item_content) {
        this.item_content = item_content;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public MtWorkItem(String id, String remark, String result, String result_id, String item_code, String item_content, String item_type) {
        this.id = id;
        this.remark = remark;
        this.result = result;
        this.result_id = result_id;
        this.item_code = item_code;
        this.item_content = item_content;
        this.item_type = item_type;
    }

    public MtWorkItem() {
    }

    public String getVersion_id() {
        return version_id;
    }

    public void setVersion_id(String version_id) {
        this.version_id = version_id;
    }
}
