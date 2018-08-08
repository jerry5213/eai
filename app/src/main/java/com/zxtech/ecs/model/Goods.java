package com.zxtech.ecs.model;

import java.util.Map;

/**
 * Created by syp523 on 2018/3/3.
 */

public class Goods {

    private Map<String,String> goods;

    private Map<String,String> styles;

    public Goods(Map<String, String> goods, Map<String, String> styles) {
        this.goods = goods;
        this.styles = styles;
    }

    public Goods() {
    }

    public Map<String, String> getGoods() {
        return goods;
    }

    public void setGoods(Map<String, String> goods) {
        this.goods = goods;
    }

    public Map<String, String> getStyles() {
        return styles;
    }

    public void setStyles(Map<String, String> styles) {
        this.styles = styles;
    }
}
