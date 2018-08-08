package com.zxtech.gks.model.vo;

import java.io.Serializable;

/**
 * Created by SYP521 on 2017/7/3.
 */

public class BaseVO<T> implements Serializable {

    public static final int STATE_FAIL = 0;
    public static final int STATE_SUCC = 1;

    private int flag;//状态 0 1
    private String reason;//不成功原因（给开发者看的）
    private String msg;//信息（给用户看的）
    private T data;//数据
    public boolean isSuccess() {
        return flag == 1;
    }

    public int getStatus() {
        return flag;
    }

    public String getMsg() {
        return msg;
    }

    public String getReason() {
        return reason;
    }

    public T getData() {
        return data;
    }
}
