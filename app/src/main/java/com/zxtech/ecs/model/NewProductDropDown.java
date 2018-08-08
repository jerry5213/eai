package com.zxtech.ecs.model;

import java.util.List;

/**
 * Created by syp521 on 2018/5/8.
 */

public class NewProductDropDown {

    private List<String> elevatorTypeList;
    private List<String> elevatorProductList;
    private List<DropDown> deliveryDateList;
    private List<DropDown> elevatorLoadList;
    private List<DropDown> speedList;
    private List<DropDown> rungsWidthList;
    private List<DropDown> angleList;

    public List<String> getElevatorTypeList() {
        return elevatorTypeList;
    }

    public void setElevatorTypeList(List<String> elevatorTypeList) {
        this.elevatorTypeList = elevatorTypeList;
    }

    public List<String> getElevatorProductList() {
        return elevatorProductList;
    }

    public void setElevatorProductList(List<String> elevatorProductList) {
        this.elevatorProductList = elevatorProductList;
    }

    public List<DropDown> getDeliveryDateList() {
        return deliveryDateList;
    }

    public void setDeliveryDateList(List<DropDown> deliveryDateList) {
        this.deliveryDateList = deliveryDateList;
    }

    public List<DropDown> getElevatorLoadList() {
        return elevatorLoadList;
    }

    public void setElevatorLoadList(List<DropDown> elevatorLoadList) {
        this.elevatorLoadList = elevatorLoadList;
    }

    public List<DropDown> getSpeedList() {
        return speedList;
    }

    public void setSpeedList(List<DropDown> speedList) {
        this.speedList = speedList;
    }

    public List<DropDown> getRungsWidthList() {
        return rungsWidthList;
    }

    public void setRungsWidthList(List<DropDown> rungsWidthList) {
        this.rungsWidthList = rungsWidthList;
    }

    public List<DropDown> getAngleList() {
        return angleList;
    }

    public void setAngleList(List<DropDown> angleList) {
        this.angleList = angleList;
    }

    public static class RungsWidthListBean {
        /**
         * ProECode : DIM_STEP_WIDTH
         * Value : 1000
         * Text : 1000
         * TypeId : Escalator
         * IsDefault : false
         * SequenceId : 3
         * ControlList : null
         * ImagePath : null
         * ImageText : null
         * PartId : null
         * Text_En : null
         * EscImgPath : null
         * tb_Param_Define : null
         */

        private String ProECode;
        private String Value;
        private String Text;
        private String TypeId;
        private boolean IsDefault;
        private int SequenceId;
        private Object ControlList;
        private Object ImagePath;
        private Object ImageText;
        private Object PartId;
        private Object Text_En;
        private Object EscImgPath;
        private Object tb_Param_Define;

        public String getProECode() {
            return ProECode;
        }

        public void setProECode(String ProECode) {
            this.ProECode = ProECode;
        }

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

        public String getTypeId() {
            return TypeId;
        }

        public void setTypeId(String TypeId) {
            this.TypeId = TypeId;
        }

        public boolean isIsDefault() {
            return IsDefault;
        }

        public void setIsDefault(boolean IsDefault) {
            this.IsDefault = IsDefault;
        }

        public int getSequenceId() {
            return SequenceId;
        }

        public void setSequenceId(int SequenceId) {
            this.SequenceId = SequenceId;
        }

        public Object getControlList() {
            return ControlList;
        }

        public void setControlList(Object ControlList) {
            this.ControlList = ControlList;
        }

        public Object getImagePath() {
            return ImagePath;
        }

        public void setImagePath(Object ImagePath) {
            this.ImagePath = ImagePath;
        }

        public Object getImageText() {
            return ImageText;
        }

        public void setImageText(Object ImageText) {
            this.ImageText = ImageText;
        }

        public Object getPartId() {
            return PartId;
        }

        public void setPartId(Object PartId) {
            this.PartId = PartId;
        }

        public Object getText_En() {
            return Text_En;
        }

        public void setText_En(Object Text_En) {
            this.Text_En = Text_En;
        }

        public Object getEscImgPath() {
            return EscImgPath;
        }

        public void setEscImgPath(Object EscImgPath) {
            this.EscImgPath = EscImgPath;
        }

        public Object getTb_Param_Define() {
            return tb_Param_Define;
        }

        public void setTb_Param_Define(Object tb_Param_Define) {
            this.tb_Param_Define = tb_Param_Define;
        }
    }

    public static class AngleListBean {
        /**
         * ProECode : FS026
         * Value : 30
         * Text : 30°
         * TypeId : Escalator
         * IsDefault : false
         * SequenceId : 1
         * ControlList : null
         * ImagePath : null
         * ImageText : null
         * PartId : null
         * Text_En : null
         * EscImgPath : null
         * tb_Param_Define : null
         */

        private String ProECode;
        private String Value;
        private String Text;
        private String TypeId;
        private boolean IsDefault;
        private int SequenceId;
        private Object ControlList;
        private Object ImagePath;
        private Object ImageText;
        private Object PartId;
        private Object Text_En;
        private Object EscImgPath;
        private Object tb_Param_Define;

        public String getProECode() {
            return ProECode;
        }

        public void setProECode(String ProECode) {
            this.ProECode = ProECode;
        }

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

        public String getTypeId() {
            return TypeId;
        }

        public void setTypeId(String TypeId) {
            this.TypeId = TypeId;
        }

        public boolean isIsDefault() {
            return IsDefault;
        }

        public void setIsDefault(boolean IsDefault) {
            this.IsDefault = IsDefault;
        }

        public int getSequenceId() {
            return SequenceId;
        }

        public void setSequenceId(int SequenceId) {
            this.SequenceId = SequenceId;
        }

        public Object getControlList() {
            return ControlList;
        }

        public void setControlList(Object ControlList) {
            this.ControlList = ControlList;
        }

        public Object getImagePath() {
            return ImagePath;
        }

        public void setImagePath(Object ImagePath) {
            this.ImagePath = ImagePath;
        }

        public Object getImageText() {
            return ImageText;
        }

        public void setImageText(Object ImageText) {
            this.ImageText = ImageText;
        }

        public Object getPartId() {
            return PartId;
        }

        public void setPartId(Object PartId) {
            this.PartId = PartId;
        }

        public Object getText_En() {
            return Text_En;
        }

        public void setText_En(Object Text_En) {
            this.Text_En = Text_En;
        }

        public Object getEscImgPath() {
            return EscImgPath;
        }

        public void setEscImgPath(Object EscImgPath) {
            this.EscImgPath = EscImgPath;
        }

        public Object getTb_Param_Define() {
            return tb_Param_Define;
        }

        public void setTb_Param_Define(Object tb_Param_Define) {
            this.tb_Param_Define = tb_Param_Define;
        }
    }
}
