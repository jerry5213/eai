package com.zxtech.ecs.model;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;
import com.zxtech.ecs.util.AppUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zxtech.ecs.common.Constants.LANGUAGE_EN;
import static com.zxtech.ecs.common.Constants.LANGUAGE_ZH;

/**
 * Created by syp523 on 2018/1/23.
 */

public class Parameters implements MultiItemEntity, Serializable {
    private static final long serialVersionUID = -874866924137983501L;
    @SerializedName("DataType")
    private String dataType;
    @SerializedName("Name")
    private String name;
    @SerializedName("Name_En")
    private String name_en;
    @SerializedName("ProECode")
    private String proECode;
    @SerializedName("DefaultValue")
    private String defaultValue;
    @SerializedName("Position")
    private String Position;
    @SerializedName("Option")
    private List<Option> option = new ArrayList<>();
    @SerializedName("PartType")
    private String PartType;
    @SerializedName("ScriptInfo")
    private List<ScriptInfo> scriptInfo = new ArrayList<>();
    @SerializedName("HasChildren")
    private boolean hasChildren;
    @SerializedName("IsPosition")
    private boolean IsPosition;
    private boolean selected;
    private int returnOperation;
    private boolean isStandard;


    public boolean isStandard() {
        return isStandard;
    }

    public void setStandard(boolean standard) {
        isStandard = standard;
    }

    public int getReturnOperation() {
        return returnOperation;
    }

    public void setReturnOperation(int returnOperation) {
        this.returnOperation = returnOperation;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getOptionText(String value) {
        if (TextUtils.isEmpty(value) || option == null) {
            return value;
        }
        for (Option op : option) {
            if (value.equals(op.getValue())) {
                return op.getText();
            }
        }
        return value;
    }

    public String getOptionBigimage(String optionValue) {
        if (TextUtils.isEmpty(optionValue) || option == null) {
            return null;
        }
        for (Option op : option) {
            if (optionValue.equals(op.getValue())) {
                return op.getEscImagePath();
            }
        }
        return null;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public boolean isPosition() {
        return IsPosition;
    }

    public void setPosition(boolean position) {
        IsPosition = position;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        if (LANGUAGE_EN.equals(AppUtil.getAppLanguage())) {
            return name_en;
        }
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getProECode() {
        return proECode;
    }

    public void setProECode(String proECode) {
        this.proECode = proECode;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<Option> getOption() {
        return option;
    }

    public void setOption(List<Option> option) {
        this.option = option;
    }

    public List<ScriptInfo> getScriptInfo() {
        return scriptInfo;
    }

    public void setScriptInfo(List<ScriptInfo> scriptInfo) {
        this.scriptInfo = scriptInfo;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getPartType() {
        return PartType;
    }

    public void setPartType(String partType) {
        PartType = partType;
    }

    public Parameters() {
    }

    @Override
    public int getItemType() {
        return 1;
    }

    public Parameters(String dataType) {
        this.dataType = dataType;
    }

    public Parameters(String dataType, String name, String proECode, String defaultValue) {
        this.dataType = dataType;
        this.name = name;
        this.proECode = proECode;
        this.defaultValue = defaultValue;
    }

    public static class Option implements Serializable{
        private static final long serialVersionUID = 6628194372164178261L;
        @SerializedName("Text")
        private String text;
        @SerializedName("Value")
        private String value;
        @SerializedName("ImagePath")
        private String imagePath;
        @SerializedName("Text_En")
        private String text_En;
        @SerializedName("EscImagePath")
        private String escImagePath;

        public String getText() {
            if (LANGUAGE_EN.equals(AppUtil.getAppLanguage())) {
                return text_En;
            }
            return text;
        }

        public String getText_En() {
            return text_En;
        }

        public void setText_En(String text_En) {
            this.text_En = text_En;
        }

        public String getEscImagePath() {
            return escImagePath;
        }

        public void setEscImagePath(String escImagePath) {
            this.escImagePath = escImagePath;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public Option(String text, String value) {
            this.text = text;
            this.value = value;
        }

        @Override
        public String toString() {
            if (LANGUAGE_EN.equals(AppUtil.getAppLanguage())) {
                return text_En;
            }
            return text;
        }

        public Option() {
        }
    }

    public static class ScriptInfo implements Serializable{
        private static final long serialVersionUID = 7992849757312670402L;
        @SerializedName("ScriptName")
        private String scriptName;
        @SerializedName("CustomScripts")
        private String customScripts;

        public String getScriptName() {
            return scriptName;
        }

        public void setScriptName(String scriptName) {
            this.scriptName = scriptName;
        }

        public String getCustomScripts() {
            return customScripts;
        }

        public void setCustomScripts(String customScripts) {
            this.customScripts = customScripts;
        }
    }

}
