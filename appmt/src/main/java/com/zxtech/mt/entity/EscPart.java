package com.zxtech.mt.entity;

/**
 * Created by Chw on 2016/7/26.
 */
public class EscPart {

    private String id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EscPart(String id, String name) {
        this.id = id;
        this.name = name;
    }
}