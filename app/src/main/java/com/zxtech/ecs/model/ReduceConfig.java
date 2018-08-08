package com.zxtech.ecs.model;

import com.zxtech.ecs.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import static com.zxtech.ecs.common.Constants.LANGUAGE_EN;

/**
 * Created by syp523 on 2018/2/2.
 */

public class ReduceConfig {

    private String reducePrice;

    private List<ReduceParam> reduceParams = new ArrayList<>();

    private ReduceParam firstReduceParam = new ReduceParam();

    public String getChangeInfo(){
        String info = "";
        for (ReduceParam reduceParam:reduceParams) {
            info+=reduceParam.getParamName()+"\n";
            info+=reduceParam.getChangeBefore();
            info+="->";
            info+=reduceParam.getChangeAfater();
        }
        return info;
    }

    public String getFirstReduce(){
        String info = "";
        if (firstReduceParam != null) {
            info+=firstReduceParam.getParamName()+"\n";
            info+="\"";
            info+=firstReduceParam.getChangeBefore();
            info+="\" ";
            if (LANGUAGE_EN.equals(AppUtil.getAppLanguage())) {
                info+="To ";
            }else{
                info+="改成 ";
            }
            info+="\"";
            info+=firstReduceParam.getChangeAfater();
            info+="\"";
        }
        return info;
    }

    public String getReducePrice() {
        return reducePrice;
    }

    public void setReducePrice(String reducePrice) {
        this.reducePrice = reducePrice;
    }

    public List<ReduceParam> getReduceParams() {
        return reduceParams;
    }

    public void setReduceParams(List<ReduceParam> reduceParams) {
        this.reduceParams = reduceParams;
    }

    public ReduceParam getFirstReduceParam() {
        return firstReduceParam;
    }

    public void setFirstReduceParam(ReduceParam firstReduceParam) {
        this.firstReduceParam = firstReduceParam;
    }

    public static class ReduceParam {

        private String paramName;

        private String changeInfo;

        private String changeBefore;

        private String changeAfater;

        public String getChangeBefore() {
            return changeBefore;
        }

        public void setChangeBefore(String changeBefore) {
            this.changeBefore = changeBefore;
        }

        public String getChangeAfater() {
            return changeAfater;
        }

        public void setChangeAfater(String changeAfater) {
            this.changeAfater = changeAfater;
        }

        public String getParamName() {
            return paramName;
        }

        public void setParamName(String paramName) {
            this.paramName = paramName;
        }

        public String getChangeInfo() {
            return changeInfo;
        }

        public void setChangeInfo(String changeInfo) {
            this.changeInfo = changeInfo;
        }

        public ReduceParam(String paramName, String changeInfo) {
            this.paramName = paramName;
            this.changeInfo = changeInfo;
        }

        public ReduceParam(String paramName, String changeBefore, String changeAfater) {
            this.paramName = paramName;
            this.changeBefore = changeBefore;
            this.changeAfater = changeAfater;
        }

        public ReduceParam() {
        }
    }
}
