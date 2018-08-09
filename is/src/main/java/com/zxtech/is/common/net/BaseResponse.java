package com.zxtech.is.common.net;

/**
 *
 */
public class BaseResponse<T> {

    private String flag;

    private String message;

    private T data;

    protected boolean isSuccess() {
        if ("1".equals(flag)) {
            return true;
        }
        return false;
    }

    private String error;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
