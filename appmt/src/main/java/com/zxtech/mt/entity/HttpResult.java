package com.zxtech.mt.entity;

/**
 * Created by syp523 on 2017/7/31.
 */

public class HttpResult {

    private Object data;
    private String status;
    private String msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
