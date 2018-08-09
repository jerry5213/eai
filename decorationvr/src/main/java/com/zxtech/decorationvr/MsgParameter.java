package com.zxtech.decorationvr;

/**
 * Created by syp466 on 2016/11/16.
 */

public final class MsgParameter {
    public MsgParameter(String name, int val){
        this.name = name;
        this.type = 0;
        this.intValue = val;
    }

    public MsgParameter(String name, float val){
        this.name = name;
        this.type = 2;
        this.floatValue = val;
    }

    public MsgParameter(String name, double val){
        this.name = name;
        this.type = 3;
        this.doubleValue = val;
    }

    public MsgParameter(String name, String val){
        this.name = name;
        this.type = 4;
        this.stringValue = val;
    }

    public String name;
    public int type;

    public int intValue;
    public float floatValue;
    public double doubleValue;
    public String stringValue;
}
