package com.zxtech.ecs.model;

import java.util.List;

/**
 * Created by syp521 on 2018/4/10.
 */

public class QmsBaseBean <T>{

    private String Result;
    private String ErrorCode;
    private String ErrorMsg;
    private List<T> datas;

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
