package com.zxtech.is.net;

/**
 *
 */
public class BasicResponse<T> extends BaseResponse<T> {

    private String flag;

    @Override
    public boolean isSuccess() {

        if ("1".equals(flag)) {
            return true;
        }
        return false;

    }

    public String getStatus() {
        return flag;
    }

    public void setStatus(String status) {
        this.flag = status;
    }

}
