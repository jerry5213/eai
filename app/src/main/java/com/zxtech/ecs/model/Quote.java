package com.zxtech.ecs.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by syp523 on 2017/11/14.
 */

public class Quote implements Serializable{

    private static final long serialVersionUID = 3418431975441496868L;
    private HashMap<String,String> Parameters;
    private String Price;
    private HashMap<String,Integer> Dimensions;

    public HashMap<String, String> getParameters() {
        return Parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        Parameters = parameters;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public HashMap<String, Integer> getDimensions() {
        return Dimensions;
    }

    public void setDimensions(HashMap<String, Integer> dimensions) {
        Dimensions = dimensions;
    }
}


