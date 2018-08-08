package com.zxtech.ecs.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Programme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syp523 on 2018/1/5.
 */

public class ConvertUtil {


    public static JsonObject convertALLParamJSON(Programme programme) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.CODE_PRICE, programme.getPrice());
        for (int i = 0; i < programme.getDimensions().size(); i++) {
            Programme.DimensionsBean dimensionsBean = programme.getDimensions().get(i);
            jsonObject.addProperty(dimensionsBean.getProECode(), dimensionsBean.getValue());
            for (int j = 0; j < dimensionsBean.getParams().size(); j++) {
                Programme.DimensionsBean.ParamsBean paramsBean = dimensionsBean.getParams().get(j);
                jsonObject.addProperty(paramsBean.getProECode(), paramsBean.getValue());
            }
        }
        return jsonObject;
    }

    public static HashMap<String, Programme.DimensionsBean.ParamsBean> convertParamMap(Programme programme) {
        HashMap<String, Programme.DimensionsBean.ParamsBean> map = new HashMap<>();
        if (programme.getDimensions() != null) {
            for (int i = 0; i < programme.getDimensions().size(); i++) {
                Programme.DimensionsBean dimensionsBean = programme.getDimensions().get(i);
                for (int j = 0; j < dimensionsBean.getParams().size(); j++) {
                    Programme.DimensionsBean.ParamsBean paramsBean = dimensionsBean.getParams().get(j);
                    map.put(paramsBean.getProECode(), paramsBean);
                }
            }
        }

        return map;
    }


    public static HashMap<String, String> convertParamValueMap(Programme programme) {
        HashMap<String, String> map = new HashMap<>();
        if (programme.getDimensions() != null) {
            for (int i = 0; i < programme.getDimensions().size(); i++) {
                Programme.DimensionsBean dimensionsBean = programme.getDimensions().get(i);
                for (int j = 0; j < dimensionsBean.getParams().size(); j++) {
                    Programme.DimensionsBean.ParamsBean paramsBean = dimensionsBean.getParams().get(j);
                    map.put(paramsBean.getProECode(), paramsBean.getValue());
                }
            }
        }

        return map;
    }


    public static HashMap<String, Programme.DimensionsBean.ParamsBean> convertParamBean(Programme programme) {
        HashMap<String, Programme.DimensionsBean.ParamsBean> map = new HashMap<>();
        for (int i = 0; i < programme.getDimensions().size(); i++) {
            Programme.DimensionsBean dimensionsBean = programme.getDimensions().get(i);
            for (int j = 0; j < dimensionsBean.getParams().size(); j++) {
                Programme.DimensionsBean.ParamsBean paramsBean = dimensionsBean.getParams().get(j);
                map.put(paramsBean.getProECode(), paramsBean);
            }
        }
        return map;
    }

    public static List<Programme.DimensionsBean.ParamsBean> convertProgramme(Programme programme) {
        List<Programme.DimensionsBean.ParamsBean> paramsBeanList = new ArrayList<>();
        for (int i = 0; i < programme.getDimensions().size(); i++) {
            Programme.DimensionsBean dimensionsBean = programme.getDimensions().get(i);
            paramsBeanList.addAll(dimensionsBean.getParams());
        }
        return paramsBeanList;
    }


    public static JsonArray joinDimens(HashMap<String, String> rawMap, Map<String, Boolean> fixDimensMap) {

        JsonArray dimenArray = new JsonArray();
        JsonObject dimenJson = null;
        String[] dpNames = new String[]{Constants.DIMEN_SSD, Constants.DIMEN_JNDJ, Constants.DIMEN_MGD, Constants.DIMEN_CZFW, Constants.DIMEN_AQDJ};
        String[] dpParamNames = new String[]{Constants.DIMEN_FlAG_SSD, Constants.DIMEN_FLAG_JNDJ, Constants.DIMEN_FLAG_MGD, Constants.DIMEN_FLAG_CZFW, Constants.DIMEN_FLAG_AQDJ};
        JsonArray rangeArr = new JsonArray();
        rangeArr.add("2");
        rangeArr.add("4");
        rangeArr.add("6");
        rangeArr.add("8");
        rangeArr.add("10");
        for (int i = 0; i < 5; i++) {
            dimenJson = new JsonObject();
            dimenJson.addProperty("DimensionName", dpNames[i]);
            dimenJson.addProperty("RunTime", DateUtil.getCurrentDate());
            dimenJson.addProperty("Value", rawMap.get(dpNames[i]));
            dimenJson.add("ValueRange", rangeArr);
            dimenJson.addProperty("OrderId", 0);
            dimenJson.addProperty("DimensionParamName", dpParamNames[i]);
            if (fixDimensMap != null && (fixDimensMap.get(dpNames[i]) == null
                    || !fixDimensMap.get(dpNames[i]))) {
                dimenJson.addProperty("IsFix", "true");
            } else {
                dimenJson.addProperty("IsFix", "false");
            }
            dimenArray.add(dimenJson);
        }
        return dimenArray;
    }


    // 接口要求必须这个结构
    public static JsonObject joinPrice(String drawingNumber) {
        JsonObject priceParamJson = new JsonObject();
        priceParamJson.addProperty("DrawingNumber", drawingNumber);
        priceParamJson.addProperty("Price", 0);
        priceParamJson.addProperty("RunTime", DateUtil.getCurrentDate());
        return priceParamJson;
    }

}
