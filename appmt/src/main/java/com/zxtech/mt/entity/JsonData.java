package com.zxtech.mt.entity;

import java.io.Serializable;

/**
 * Created by chw on 2017/7/24.
 */

public class JsonData implements Serializable {



    private String status;



    private String msg;



    private String data;


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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
