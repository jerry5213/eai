package com.zxtech.ecs.event;

/**
 * Created by syp523 on 2018/6/29.
 */

public class EventAction<T> {

    private int code;
    private T data;

    public static final int PRODUCT_CHANGE = 0x001;
    public static final int QUOTE_SUBMIT = 0x002;
    public static final int CONTRACT_CHANGE = 0x003;
    public static final int CONTRACT_CHANGE_PLACE = 0x004; //合同变更修改交换地点
    public static final int LOGIN_REFRESH_MENU = 0x005; //登录刷新菜单
    public static final int WX_LOGIN = 0x006; //微信登录
    public static final int BID_ADD_ATTACHMENT = 0x007; //招投标添加附件
    public static final int CONTRACT_CHANGE_DELETE = 0x008; //合同变更删除取消变更产品


    public EventAction(int code) {
        this.code = code;
    }

    public EventAction(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
