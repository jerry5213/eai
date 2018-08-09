package com.zxtech.esp.data.bean;

import android.content.Context;

import com.zxtech.esp.util.GsonCallBack;

/**
 * Created by SYP521 on 2017/7/3.
 */

public class BaseVO<T> {
    public static final int STATE_FAIL = 0;
    public static final int STATE_SUCC = 1;

    private int status;//状态 0 1
    private String reason;//不成功原因（给开发者看的）
    private String msg;//信息（给用户看的）
    private T data;//数据

    public boolean handleSelf(Context context, GsonCallBack callBack) {
        if (status == STATE_SUCC) {
            return true;
        } else {
                if (callBack != null)
                    callBack.onFailure(msg);
            return false;
        }
    }

    public int getStatus() {
        return status;
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
