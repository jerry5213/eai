package com.zxtech.mt.entity;

/**
 * Created by Chw on 2016/7/12.
 */
public class MtWorkItemResultRel {

    private String id;

    private String plan_id;

    private String item_code;

    private String result;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public MtWorkItemResultRel(String id, String plan_id, String item_code,String result) {
        this.id = id;
        this.plan_id = plan_id;
        this.item_code = item_code;
        this.result = result;
    }

    public MtWorkItemResultRel() {
    }
}
