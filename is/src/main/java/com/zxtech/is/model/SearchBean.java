package com.zxtech.is.model;


import com.zxtech.is.util.DateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.zxtech.is.common.Constants.ELEVATORTYPE;

/**
 * Created by syp523 on 2017/11/16.
 */

public class SearchBean {


    private String budget;

    private SalePara salesParameter;

    private Price price;

    private List<Dimen> dimensions = null;

    public SearchBean(String budget, SalePara salesParameter, List<Dimen> dimensions) {
        this.budget = budget;
        this.salesParameter = salesParameter;
        this.price = new Price();
        this.dimensions = dimensions;
    }

    public SearchBean() {

    }

    public static class SalePara implements Serializable {
        private static final long serialVersionUID = 5322179644026290121L;
        private int Price = 0;
        private String RunTime;
        private String DrawingNumber;
        private ElevatorPara InputParameter;

        public SalePara() {
            this.RunTime = DateUtil.getCurrentDate();
            this.DrawingNumber = "YITIHUA--001";
        }

        public int getPrice() {
            return Price;
        }

        public void setPrice(int price) {
            Price = price;
        }

        public String getRunTime() {
            return RunTime;
        }

        public void setRunTime(String runTime) {
            RunTime = runTime;
        }

        public String getDrawingNumber() {
            return DrawingNumber;
        }

        public void setDrawingNumber(String drawingNumber) {
            DrawingNumber = drawingNumber;
        }

        public ElevatorPara getInputParameter() {
            return InputParameter;
        }

        public void setInputParameter(ElevatorPara inputParameter) {
            InputParameter = inputParameter;
        }
    }

    public static class ElevatorPara implements Serializable {
        private static final long serialVersionUID = 4738450185649044041L;
        private String ELEVATORTYPE;
        private String MACHINEROOM;
        private String QTY_NUMBER_OF_FLOORS;
        private String DIM_SHAFT_WIDTH_WW;
        private String DIM_SHAFT_DEPTH_WD;

        public ElevatorPara() {
        }

        public String getELEVATORTYPE() {
            return ELEVATORTYPE;
        }

        public void setELEVATORTYPE(String ELEVATORTYPE) {
            this.ELEVATORTYPE = ELEVATORTYPE;
        }

        public String getMACHINEROOM() {
            return MACHINEROOM;
        }

        public void setMACHINEROOM(String MACHINEROOM) {
            this.MACHINEROOM = MACHINEROOM;
        }

        public String getQTY_NUMBER_OF_FLOORS() {
            return QTY_NUMBER_OF_FLOORS;
        }

        public void setQTY_NUMBER_OF_FLOORS(String QTY_NUMBER_OF_FLOORS) {
            this.QTY_NUMBER_OF_FLOORS = QTY_NUMBER_OF_FLOORS;
        }

        public String getDIM_SHAFT_WIDTH_WW() {
            return DIM_SHAFT_WIDTH_WW;
        }

        public void setDIM_SHAFT_WIDTH_WW(String DIM_SHAFT_WIDTH_WW) {
            this.DIM_SHAFT_WIDTH_WW = DIM_SHAFT_WIDTH_WW;
        }

        public String getDIM_SHAFT_DEPTH_WD() {
            return DIM_SHAFT_DEPTH_WD;
        }

        public void setDIM_SHAFT_DEPTH_WD(String DIM_SHAFT_DEPTH_WD) {
            this.DIM_SHAFT_DEPTH_WD = DIM_SHAFT_DEPTH_WD;
        }
    }

    public static class Price implements Serializable {
        private static final long serialVersionUID = 166024018847545568L;
        private String InputParameter = null;
        private String DrawingNumber;
        private String RunTime;
        private String Price;

        public Price() {
            this.DrawingNumber = ELEVATORTYPE + "_PRICE";
            this.RunTime = DateUtil.getCurrentDate();
            this.Price = "0";
        }

        public String getInputParameter() {
            return InputParameter;
        }

        public void setInputParameter(String inputParameter) {
            InputParameter = inputParameter;
        }

        public String getDrawingNumber() {
            return DrawingNumber;
        }

        public void setDrawingNumber(String drawingNumber) {
            DrawingNumber = drawingNumber;
        }

        public String getRunTime() {
            return RunTime;
        }

        public void setRunTime(String runTime) {
            RunTime = runTime;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }
    }

    public static class Dimen implements Serializable {
        private static final long serialVersionUID = 6786257692467364569L;
        private String RunTime;
        private int OrderId = 0;
        private String DimensionName;
        private String DimensionParamName;
        private String IsFix;
        private String DimensionCaption = null;
        private String Value;
        private List<String> ValueRange = new ArrayList<>();

        public Dimen() {
            ValueRange.add("2");
            ValueRange.add("4");
            ValueRange.add("6");
            ValueRange.add("8");
            ValueRange.add("10");
            this.RunTime = DateUtil.getCurrentDate();

        }

        public String getIsFix() {
            return IsFix;
        }

        public void setIsFix(String isFix) {
            IsFix = isFix;
        }

        public String getRunTime() {
            return RunTime;
        }

        public void setRunTime(String runTime) {
            RunTime = runTime;
        }

        public int getOrderId() {
            return OrderId;
        }

        public void setOrderId(int orderId) {
            OrderId = orderId;
        }

        public String getDimensionName() {
            return DimensionName;
        }

        public void setDimensionName(String dimensionName) {
            DimensionName = dimensionName;
        }

        public String getDimensionParamName() {
            return DimensionParamName;
        }

        public void setDimensionParamName(String dimensionParamName) {
            DimensionParamName = dimensionParamName;
        }

        public String getDimensionCaption() {
            return DimensionCaption;
        }

        public void setDimensionCaption(String dimensionCaption) {
            DimensionCaption = dimensionCaption;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }

        public List<String> getValueRange() {
            return ValueRange;
        }

        public void setValueRange(List<String> valueRange) {
            ValueRange = valueRange;
        }
    }


    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public SalePara getSalesParameter() {
        return salesParameter;
    }

    public void setSalesParameter(SalePara salesParameter) {
        this.salesParameter = salesParameter;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public List<Dimen> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Dimen> dimensions) {
        this.dimensions = dimensions;
    }
}
