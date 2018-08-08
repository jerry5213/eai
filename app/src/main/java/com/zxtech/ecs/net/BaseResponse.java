package com.zxtech.ecs.net;

/**
 *
 */
public class BaseResponse<T> {

    private String status;
    private String msg;
    private T data;



    protected boolean isSuccess(){
        if ("success".equals(status)) {
            return true;
        }
        return false;
    }

    protected boolean isMessage(){
        if ("message".equals(status)) {
            return true;
        }
        return false;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }
}
