package com.zxtech.ecs.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;
import com.zxtech.ecs.adapter.ExpandableItemAdapter;
import com.zxtech.ecs.util.AppUtil;
import com.zxtech.ecs.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.zxtech.ecs.common.Constants.LANGUAGE_EN;

/**
 * Created by syp523 on 2018/3/14.
 */

public class Programme implements Serializable {


    private static final long serialVersionUID = 9204411333780725508L;
    /**
     * price : 262000
     * dimensions : [{"name":"美观度","value":"2","proECode":"BF-MGD","name_En":null,"params":[{"name":"轿厢装潢","value":"443发纹不锈钢","proECode":"Cab_Deco_Type","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"轿壁材质","value":"NA","proECode":"Mat_CabP","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"围壁装修厚度","value":"0","proECode":"T_Deco_Panel","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"input","description":""},{"name":"吊顶预留","value":"NO","proECode":"Deco_C","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"吊顶型号","value":"BF-821D","proECode":"Deco_C_Model","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"轿底预留","value":"NO","proECode":"FL_Rece","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"地板型号","value":"BF-906F","proECode":"FL_Model","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"扶手型号","value":"NA","proECode":"HR_Model","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"A侧轿门材质","value":"443发纹不锈钢","proECode":"A_Car_DP_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"A侧第一种厅门材质","value":"443发纹不锈钢","proECode":"A_G1_LDP_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"A侧第二种厅门材质","value":"钢板喷涂","proECode":"A_G2_LDP_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"门套类型","value":"小门套","proECode":"Jamb_Type","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"A侧第一种门套材质","value":"443发纹不锈钢","proECode":"A_G1_LDJ_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"A侧第二种门套材质","value":"钢板喷涂","proECode":"A_G2_LDJ_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"COP型号","value":"BF-101P","proECode":"M_COP_M","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"COP材质","value":"304发纹不锈钢","proECode":"M_COP_FP_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"COP显示器","value":"点阵","proECode":"M_COP_DP_Type","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"外呼型号","value":"BF-201H","proECode":"M_LD_Fix_M","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"外呼面板材质","value":"304发纹不锈钢","proECode":"M_LD_Fix_FL_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"余层外呼型号","value":"BF-201H","proECode":"R_LD_Fix_M","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""}]},{"name":"服务","value":"2","proECode":"SEVICE","name_En":null,"params":[{"name":"群控数量","value":"1","proECode":"NEG","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"input","description":""},{"name":"布置方式","value":"110","proECode":"Ele_Pos","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"控制类型","value":"单梯","proECode":"Ele_Group","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"残障功能","value":"NO","proECode":"Handicapped_Kit","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"电梯核心部件质保周期","value":"1","proECode":"WCC","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"强制关门","value":"NO","proECode":"ANN","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"语音报站","value":"NO","proECode":"Voice_ANN","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"防捣乱功能","value":"NO","proECode":"Anti_Nui","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"分散待梯","value":"NO","proECode":"Di_Parking_LS","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"制卡设备","value":"NO","proECode":"IC_CWD","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""}]},{"name":"功能配置","value":"2","proECode":"OPTIONALF","name_En":null,"params":[{"name":"担架功能","value":"NO","proECode":"Strc_Sevice","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"视频功能","value":"NO","proECode":"Surveilliance","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"input","description":""},{"name":"预留视频接口","value":"NO","proECode":"Survieliance_Interface","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"远程监控","value":"NO","proECode":"RMI","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"楼宇自动化接口","value":"NO","proECode":"BAI","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"input","description":""},{"name":"小区监控","value":"NO","proECode":"Com_Mon","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"预开门","value":"NO","proECode":"ADO","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"关门延时功能","value":"NO","proECode":"Clo_Delay","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"开门延时功能","value":"NO","proECode":"Opening_Delay","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"备用电源转换装置","value":"NO","proECode":"BPS","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"input","description":""}]},{"name":"安全增项","value":"2","proECode":"SafetyF","name_En":null,"params":[{"name":"停电自动平层","value":"NO","proECode":"Lev_WO_Power","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"厅门防火要求","value":"NO","proECode":"F_Rated_Door","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"防火等级","value":"NA","proECode":"F_Rated_level","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"主钢丝绳进口","value":"NO","proECode":"Imp_Mrope","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"安全钳进口","value":"NO","proECode":"Imp_Safety","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"限速器进口","value":"NO","proECode":"Imp_Govenor","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"缓冲器进口","value":"NO","proECode":"Imp_Buff","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"门保护装置","value":"单一光幕","proECode":"Door_Prot","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"限速器钢丝绳进口","value":"NO","proECode":"Imp_Gov_rope","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"消防服务","value":"NO","proECode":"FFS","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""}]},{"name":"舒适度","value":"2","proECode":"Comfortable","name_En":null,"params":[{"name":"轿内隔音处理","value":"无隔音处理","proECode":"Noise_INS","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"轿厢高度","value":"2300","proECode":"CH","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"音频功能","value":"NO","proECode":"Audio","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"滚轮导靴","value":"标准","proECode":"RG","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"门机型号","value":"FECO.02A","proECode":"Car_DOper_Model","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"导滑器/导靴进口","value":"NO","proECode":"Imp_GS","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"司机操作","value":"NO","proECode":"Att_Service","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""}]}]
     */

    private int price;
    private List<DimensionsBean> dimensions;
    private String specialNonStandard;
    private String elevatorType;
    private String elevatorProduct;
    private String priceDrawingNo;
    private String salesParameterDrawingNo;
    private String specialNonStandardPath;



    public String getElevatorType() {
        return elevatorType;
    }

    public void setElevatorType(String elevatorType) {
        this.elevatorType = elevatorType;
    }

    public String getElevatorProduct() {
        return elevatorProduct;
    }

    public void setElevatorProduct(String elevatorProduct) {
        this.elevatorProduct = elevatorProduct;
    }

    public String getPriceDrawingNo() {
        return priceDrawingNo;
    }

    public void setPriceDrawingNo(String priceDrawingNo) {
        this.priceDrawingNo = priceDrawingNo;
    }

    public String getSalesParameterDrawingNo() {
        return salesParameterDrawingNo;
    }

    public void setSalesParameterDrawingNo(String salesParameterDrawingNo) {
        this.salesParameterDrawingNo = salesParameterDrawingNo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSpecialNonStandard() {
        return specialNonStandard;
    }

    public void setSpecialNonStandard(String specialNonStandard) {
        this.specialNonStandard = specialNonStandard;
    }

    public String getSpecialNonStandardPath() {
        return specialNonStandardPath==null ? "":specialNonStandardPath ;
    }

    public void setSpecialNonStandardPath(String specialNonStandardPath) {
        this.specialNonStandardPath = specialNonStandardPath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<DimensionsBean> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<DimensionsBean> dimensions) {
        this.dimensions = dimensions;
    }

    public int getDimensionValue(String proCode) {
        if (dimensions == null) {
            return 2;
        } else {
            int retValue = 1;
            for (int i = 0; i < dimensions.size(); i++) {
                String value = dimensions.get(i).getValue();
                String proECode = dimensions.get(i).getProECode();
                if (proCode.equals(proECode)) {
                    retValue = Util.isNumeric(value) ? (Integer.parseInt(value)) : 2;
                    continue;
                }
            }
            return retValue;
        }
    }


    public static class DimensionsBean extends AbstractExpandableItem<DimensionsBean.ParamsBean> implements MultiItemEntity, Serializable {
        private static final long serialVersionUID = 8614435410466082908L;
        /**
         * name : 美观度
         * value : 2
         * proECode : BF-MGD
         * name_En : null
         * params : [{"name":"轿厢装潢","value":"443发纹不锈钢","proECode":"Cab_Deco_Type","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"轿壁材质","value":"NA","proECode":"Mat_CabP","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"围壁装修厚度","value":"0","proECode":"T_Deco_Panel","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"input","description":""},{"name":"吊顶预留","value":"NO","proECode":"Deco_C","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"吊顶型号","value":"BF-821D","proECode":"Deco_C_Model","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"轿底预留","value":"NO","proECode":"FL_Rece","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"地板型号","value":"BF-906F","proECode":"FL_Model","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"扶手型号","value":"NA","proECode":"HR_Model","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"A侧轿门材质","value":"443发纹不锈钢","proECode":"A_Car_DP_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"A侧第一种厅门材质","value":"443发纹不锈钢","proECode":"A_G1_LDP_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"A侧第二种厅门材质","value":"钢板喷涂","proECode":"A_G2_LDP_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"门套类型","value":"小门套","proECode":"Jamb_Type","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"A侧第一种门套材质","value":"443发纹不锈钢","proECode":"A_G1_LDJ_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"A侧第二种门套材质","value":"钢板喷涂","proECode":"A_G2_LDJ_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"COP型号","value":"BF-101P","proECode":"M_COP_M","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"COP材质","value":"304发纹不锈钢","proECode":"M_COP_FP_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"COP显示器","value":"点阵","proECode":"M_COP_DP_Type","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"外呼型号","value":"BF-201H","proECode":"M_LD_Fix_M","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"外呼面板材质","value":"304发纹不锈钢","proECode":"M_LD_Fix_FL_Mat","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""},{"name":"余层外呼型号","value":"BF-201H","proECode":"R_LD_Fix_M","hasDescription":false,"name_En":null,"imagePath":"","videoPath":"","dataType":"select","description":""}]
         */

        private String name;
        private String value;
        private String proECode;
        private String name_En;
        private List<ParamsBean> params;

        public String getName() {
            if (LANGUAGE_EN.equals(AppUtil.getAppLanguage())) {
                return name_En;
            }
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getProECode() {
            return proECode;
        }

        public void setProECode(String proECode) {
            this.proECode = proECode;
        }

        public String getName_En() {
            return name_En;
        }

        public void setName_En(String name_En) {
            this.name_En = name_En;
        }

        public List<ParamsBean> getParams() {
            return params;
        }

        public void setParams(List<ParamsBean> params) {
            this.params = params;
        }

        @Override
        public int getLevel() {
            return ExpandableItemAdapter.TYPE_LEVEL_0;
        }

        @Override
        public int getItemType() {
            return 0;
        }


        public static class ParamsBean implements MultiItemEntity, Serializable {
            private static final long serialVersionUID = 5388604845220852051L;
            /**
             * name : 轿厢装潢
             * value : 443发纹不锈钢
             * proECode : Cab_Deco_Type
             * hasDescription : false
             * name_En : null
             * imagePath :
             * videoPath :
             * dataType : select
             * description :
             */

            private String name;
            private String value;
            private String proECode;
            private boolean hasDescription;
            private String name_En;
            private String imagePath;
            private String videoPath;
            private String dataType;
            private String description;
            private String voiceFlag;
            private boolean different;
            private String text;
            private String text_En;
            private List<ScriptInfo> scriptInfo = new ArrayList<>();

            public static long getSerialVersionUID() {
                return serialVersionUID;
            }

            public String getVoiceFlag() {
                return voiceFlag;
            }

            public void setVoiceFlag(String voiceFlag) {
                this.voiceFlag = voiceFlag;
            }

            public String getName() {
                if (LANGUAGE_EN.equals(AppUtil.getAppLanguage())) {
                    return name_En;
                }
                return name;
            }

            public String getName(String language) {
                if (LANGUAGE_EN.equals(language)) {
                    return name_En;
                }
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getProECode() {
                return proECode;
            }

            public void setProECode(String proECode) {
                this.proECode = proECode;
            }

            public boolean isHasDescription() {
                return hasDescription;
            }

            public void setHasDescription(boolean hasDescription) {
                this.hasDescription = hasDescription;
            }

            public String getName_En() {
                return name_En;
            }

            public void setName_En(String name_En) {
                this.name_En = name_En;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public String getVideoPath() {
                return videoPath;
            }

            public void setVideoPath(String videoPath) {
                this.videoPath = videoPath;
            }

            public String getDataType() {
                return dataType;
            }

            public void setDataType(String dataType) {
                this.dataType = dataType;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public boolean isDifferent() {
                return different;
            }

            public void setDifferent(boolean different) {
                this.different = different;
            }

            public List<ScriptInfo> getScriptInfo() {
                return scriptInfo;
            }

            public void setScriptInfo(List<ScriptInfo> scriptInfo) {
                this.scriptInfo = scriptInfo;
            }

            public String getText() {
                if (LANGUAGE_EN.equals(AppUtil.getAppLanguage())) {
                    return text_En;
                }
                return text;
            }

            public String getText(String language) {
                if (LANGUAGE_EN.equals(language)) {
                    return text_En;
                }
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getText_En() {
                return text_En;
            }

            public void setText_En(String text_En) {
                this.text_En = text_En;
            }

            @Override
            public int getItemType() {
                return 1;
            }
        }

        public static class ScriptInfo implements Serializable {
            private static final long serialVersionUID = -3400000643501712722L;
            private String scriptName;
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


}
