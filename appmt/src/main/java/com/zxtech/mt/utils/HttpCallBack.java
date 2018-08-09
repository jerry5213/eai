package com.zxtech.mt.utils;

/**
 * Created by syp523 on 2017/7/31.
 */

public abstract class HttpCallBack<T> {
    public abstract void onSuccess(T data);
    public abstract void onFail(String msg);
}
