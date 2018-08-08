package com.zxtech.ecs.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syp523 on 2018/1/30.
 */

public class ScriptReturnParam {

    private String ParamType;

    private String ParamCode;

    private List<ReturnParamOption> ParamValue = new ArrayList<>();
    //0，不处理， 1 控件禁用，2 控件启用 ， 3 赋值 ，4 重构下拉框
    private int operation;

    public static final int GONE = 0;
    public static final int DISABLE = 1;
    public static final int ENABLE = 2;
    public static final int SET = 3;
    public static final int RESET = 4;

    public int getOperation() {
        if (ParamValue != null && ParamValue.size() > 0) {
            String paramValue = ParamValue.get(0).getValue();

            if ("_".equals(ParamValue)) {
                return 0;
            } else if ("0".equals(ParamType) && "0".equals(paramValue)) {
                return 1;
            } else if ("0".equals(ParamType) && "1".equals(paramValue)) {
                return 2;
            } else if ("1".equals(ParamType)) {
                return 3;
            } else if ("2".equals(ParamType)) {
                return 4;
            }
        }
        return operation;
    }

    public String getFirstParamValue() {
        if (ParamValue != null && ParamValue.size() > 0) {
            return ParamValue.get(0).getValue();
        }
        return "";
    }

    public List<Parameters.Option> getResetOptions() {
        List<Parameters.Option> options = new ArrayList<>();
        if (ParamValue != null) {
            for (int i = 0; i < ParamValue.size(); i++) {
                ReturnParamOption returnParamOption = ParamValue.get(i);
                Parameters.Option option = new Parameters.Option();
                option.setImagePath(returnParamOption.getImgPath());
                option.setText(returnParamOption.getText());
                option.setValue(returnParamOption.getValue());
                options.add(option);
            }
        }
        return options;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getParamType() {
        return ParamType;
    }

    public void setParamType(String paramType) {
        ParamType = paramType;
    }

    public String getParamCode() {
        return ParamCode;
    }

    public void setParamCode(String paramCode) {
        ParamCode = paramCode;
    }

    public List<ReturnParamOption> getParamValue() {
        return ParamValue;
    }

    public void setParamValue(List<ReturnParamOption> paramValue) {
        ParamValue = paramValue;
    }

    public static class ReturnParamOption {

        /**
         * Value : 1.0
         * Text : 1.0
         * Text_En : 1.0
         * ImgPath : null
         * BigImgPath :
         */

        private String Value;
        private String Text;
        private String Text_En;
        private String ImgPath;
        private String BigImgPath;

        public String getValue() {
            return Value;
        }

        public void setValue(String Value) {
            this.Value = Value;
        }

        public String getText() {
            return Text;
        }

        public void setText(String Text) {
            this.Text = Text;
        }

        public String getText_En() {
            return Text_En;
        }

        public void setText_En(String Text_En) {
            this.Text_En = Text_En;
        }

        public String getImgPath() {
            return ImgPath;
        }

        public void setImgPath(String ImgPath) {
            this.ImgPath = ImgPath;
        }

        public String getBigImgPath() {
            return BigImgPath;
        }

        public void setBigImgPath(String BigImgPath) {
            this.BigImgPath = BigImgPath;
        }
    }
}
