package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/5/18.
 */

public class QuotePrice implements Cloneable {



    /**
     * EQS_ProductNo : XJ2018-01182-01
     * Guid : 17bd3a67-e2ce-4fdc-abb8-492b4d2469af
     * EQS_ProductGuid : 959e890a-0641-4758-ba9d-e8d59f618e69
     * ProductMainInfo : TBS
     * ProductName : Elevator
     * ProductCount : 1
     * NonStandardLevel : null
     * StdPrice_Equi : 17716
     * StdPrice_Inst : 0
     * FloatRate_Equi : 1.00000
     * FloatRate_Inst : 1.00000
     * HoistingCost : 0
     * RealPrice_Equi : 5050
     * RealPrice_Inst : 0
     * InspectionFee : 0
     * InspectionFeeForSale : 0
     * EquiObligatePriceForSale : 0
     * InstOtherPriceForSale : 0
     * RealPrice_Total : 5050
     * ExcludTax_Equi : null
     * LIFSValue_Equi : 5054
     * NetSales_Equi : null
     * ExcludTax_Inst : null
     * LIFSValue_Inst : 0
     * NetSales_Inst : null
     * NetSales_Total : null
     * FLCMII_Equi : null
     * FLCMII_Rate_Equi : null
     * CMII_Inst : null
     * CMII_Rate_Inst : null
     * FLCMII : null
     * FLCMII_Rate : null
     * AvgMaterialCost : null
     * Freight : 0
     * MaterialCostsSL : null
     * EquiObligatePrice : null
     * InstOtherPrice : null
     * AgentCommission : null
     * BiddingExpenses : null
     * FCCost_Equi : null
     * FCCMII_Equi : null
     * FCCMII_Rate_Equi : null
     * FCCMII_Rate : null
     * CreateDate : null
     * ModifiedDate : null
     * PriceState : null
     * DisableState : false
     * EQS_Guid : 959e890a-0641-4758-ba9d-e8d59f618e69
     * ElevatorType : TBS
     * TypeId : Elevator
     * ElevatorNo : L1
     * YSFXS : 0.016
     * ElevatorLoad : null
     * Speed : null
     * RungsWidth : null
     * Angle : null
     * ElevatorCount : 0
     * CreateDateStr : null
     * InstanceNodeName : 标准询价
     * InstanceGuid : 00000000-0000-0000-0000-000000000000
     * NonState : null
     * VersionNum : null
     * IsConfirmVersion : null
     * ElevatorProduct : 家用电梯
     * ProductNo : null
     * GuaranteeDate : null
     * DeliveryDate : null
     * FreeInsuranceDate : null
     * IsNonstandard : null
     * IsProductUpdate : null
     * LDSState : 0
     * DSState : 0
     * LDSProductGuid : null
     * DSProductGuid : null
     * LDSTaskId : null
     * DrawingState : null
     * IsTransactUserName : false
     * IsXSDXM : false
     * EQSState : null
     * CutStringElevatorNo : null
     */

    private String EQS_ProductNo;
    private String Guid;
    private String EQS_ProductGuid;
    private String ProductMainInfo;
    private String ProductName;
    private int ProductCount;
    private Object NonStandardLevel;
    private String StdPrice_Equi;
    private String StdPrice_Inst;
    private String FloatRate_Equi;
    private String FloatRate_Inst;
    private String HoistingCost;
    private String RealPrice_Equi;
    private String RealPrice_Inst;
    private String InspectionFee;
    private String InspectionFeeForSale;
    private String EquiObligatePriceForSale;
    private String InstOtherPriceForSale;
    private String RealPrice_Total;
    private Object ExcludTax_Equi;
    private String LIFSValue_Equi;
    private Object NetSales_Equi;
    private Object ExcludTax_Inst;
    private String LIFSValue_Inst;
    private Object NetSales_Inst;
    private Object NetSales_Total;
    private Object FLCMII_Equi;
    private Object FLCMII_Rate_Equi;
    private Object CMII_Inst;
    private Object CMII_Rate_Inst;
    private Object FLCMII;
    private Object FLCMII_Rate;
    private Object AvgMaterialCost;
    private String Freight;
    private Object MaterialCostsSL;
    private Object EquiObligatePrice;
    private Object InstOtherPrice;
    private Object AgentCommission;
    private Object BiddingExpenses;
    private Object FCCost_Equi;
    private Object FCCMII_Equi;
    private Object FCCMII_Rate_Equi;
    private Object FCCMII_Rate;
    private Object CreateDate;
    private Object ModifiedDate;
    private Object PriceState;
    private boolean DisableState;
    private String EQS_Guid;
    private String ElevatorType;
    private String TypeId;
    private String ElevatorNo;
    private String YSFXS;
    private Object ElevatorLoad;
    private Object Speed;
    private Object RungsWidth;
    private Object Angle;
    private int ElevatorCount;
    private Object CreateDateStr;
    private String InstanceNodeName;
    private String InstanceGuid;
    private Object NonState;
    private Object VersionNum;
    private Object IsConfirmVersion;
    private String ElevatorProduct;
    private Object ProductNo;
    private Object GuaranteeDate;
    private Object DeliveryDate;
    private Object FreeInsuranceDate;
    private Object IsNonstandard;
    private Object IsProductUpdate;
    private int LDSState;
    private int DSState;
    private Object LDSProductGuid;
    private Object DSProductGuid;
    private Object LDSTaskId;
    private Object DrawingState;
    private boolean IsTransactUserName;
    private boolean IsXSDXM;
    private Object EQSState;
    private Object CutStringElevatorNo;

    public String getEQS_ProductNo() {
        return EQS_ProductNo;
    }

    public void setEQS_ProductNo(String EQS_ProductNo) {
        this.EQS_ProductNo = EQS_ProductNo;
    }

    //设备折后价
    public String getEquiDiscountPrice() {
        if (StdPrice_Equi != null && FloatRate_Equi != null) {
            int a = Integer.valueOf(StdPrice_Equi);
            float b = Float.valueOf(FloatRate_Equi);
            int c = (int) (a * b);
            return String.valueOf(c);
        }
        return String.valueOf(0);
    }

    //安装费折后价
    public String getInstDiscountPrice() {
        if (StdPrice_Inst != null && FloatRate_Inst != null) {
            int a = Integer.valueOf(StdPrice_Inst);
            float b = Float.valueOf(FloatRate_Inst);
            int c = (int) (a * b);
            return String.valueOf(c);
        }
        return String.valueOf(0);

    }

    //免保费
    public String getFreePremium() {
        int sum = 0;
        if (LIFSValue_Equi != null) {
            sum += Integer.valueOf(LIFSValue_Equi);
        }
        if (LIFSValue_Inst != null) {
            sum += Integer.valueOf(LIFSValue_Inst);
        }
        return String.valueOf(sum);
    }

    //设备实际报价
    public int getEquiRealPrice() {
        int sum = 0;

        String equiDiscountPrice = getEquiDiscountPrice();
        sum += Integer.valueOf(equiDiscountPrice);

        String freePremium = getFreePremium();
        sum += Integer.valueOf(freePremium);

        if (Freight != null) {
            sum += Integer.valueOf(Freight);
        }
        if (EquiObligatePriceForSale != null) {
            sum += Integer.valueOf(EquiObligatePriceForSale);
        }
        return sum;

    }

    //设备实际报价
    public void setEquiRealPrice(String realPrice) {
        int price = Integer.valueOf(realPrice);
        String freePremium = getFreePremium();
        price -= Integer.valueOf(freePremium);

        if (Freight != null) {
            price -= Integer.valueOf(Freight);
        }
        if (EquiObligatePriceForSale != null) {
            price -= Integer.valueOf(EquiObligatePriceForSale);
        }

        if (StdPrice_Equi != null) {
            int std = Integer.valueOf(StdPrice_Equi);
            FloatRate_Equi = String.valueOf(Float.valueOf(price) / Float.valueOf(std));
        }

    }

    //安装实际报价
    public int getInstRealPrice() {
        int sum = 0;

        String instDiscountPrice = getInstDiscountPrice();
        sum += Integer.valueOf(instDiscountPrice);

        if (InspectionFeeForSale != null) {
            sum += Integer.valueOf(InspectionFeeForSale);
        }
        if (InstOtherPriceForSale != null) {
            sum += Integer.valueOf(InstOtherPriceForSale);
        }
        return sum;
    }

    //安装实际报价
    public void setInstRealPrice(String realPrice) {

        int price = Integer.valueOf(realPrice);
        String freePremium = getFreePremium();
        price -= Integer.valueOf(freePremium);

        if (InspectionFeeForSale != null) {
            price -= Integer.valueOf(InspectionFeeForSale);
        }
        if (InstOtherPriceForSale != null) {
            price -= Integer.valueOf(InstOtherPriceForSale);
        }

        if (StdPrice_Inst != null) {
            int std = Integer.valueOf(StdPrice_Inst);
            FloatRate_Inst = String.valueOf(Float.valueOf(price) / Float.valueOf(std));
        }

    }

    //修改设备浮动率
    public void setEquiFloatRate(String rate) {
        if (StdPrice_Equi != null && rate != null) {
            FloatRate_Equi = rate;
            getEquiRealPrice();
        }
    }

    //修改安装浮动率
    public void setInstFloatRate(String rate) {
        if (StdPrice_Inst != null && rate != null) {
            FloatRate_Inst = rate;
            getInstRealPrice();
        }
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public String getEQS_ProductGuid() {
        return EQS_ProductGuid;
    }

    public void setEQS_ProductGuid(String EQS_ProductGuid) {
        this.EQS_ProductGuid = EQS_ProductGuid;
    }

    public String getProductMainInfo() {
        return ProductMainInfo;
    }

    public void setProductMainInfo(String ProductMainInfo) {
        this.ProductMainInfo = ProductMainInfo;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public int getProductCount() {
        return ProductCount;
    }

    public void setProductCount(int ProductCount) {
        this.ProductCount = ProductCount;
    }

    public Object getNonStandardLevel() {
        return NonStandardLevel;
    }

    public void setNonStandardLevel(Object NonStandardLevel) {
        this.NonStandardLevel = NonStandardLevel;
    }

    public String getStdPrice_Equi() {
        return StdPrice_Equi;
    }

    public void setStdPrice_Equi(String StdPrice_Equi) {
        this.StdPrice_Equi = StdPrice_Equi;
    }

    public String getStdPrice_Inst() {
        return StdPrice_Inst;
    }

    public void setStdPrice_Inst(String StdPrice_Inst) {
        this.StdPrice_Inst = StdPrice_Inst;
    }

    public String getFloatRate_Equi() {
        return FloatRate_Equi;
    }

    public void setFloatRate_Equi(String FloatRate_Equi) {
        this.FloatRate_Equi = FloatRate_Equi;
    }

    public String getFloatRate_Inst() {
        return FloatRate_Inst;
    }

    public void setFloatRate_Inst(String FloatRate_Inst) {
        this.FloatRate_Inst = FloatRate_Inst;
    }

    public String getHoistingCost() {
        return HoistingCost;
    }

    public void setHoistingCost(String HoistingCost) {
        this.HoistingCost = HoistingCost;
    }

    public String getRealPrice_Equi() {
        return RealPrice_Equi;
    }

    public void setRealPrice_Equi(String RealPrice_Equi) {
        this.RealPrice_Equi = RealPrice_Equi;
    }

    public String getRealPrice_Inst() {
        return RealPrice_Inst;
    }

    public void setRealPrice_Inst(String RealPrice_Inst) {
        this.RealPrice_Inst = RealPrice_Inst;
    }

    public String getInspectionFee() {
        return InspectionFee;
    }

    public void setInspectionFee(String InspectionFee) {
        this.InspectionFee = InspectionFee;
    }

    public String getInspectionFeeForSale() {
        return InspectionFeeForSale;
    }

    public void setInspectionFeeForSale(String InspectionFeeForSale) {
        this.InspectionFeeForSale = InspectionFeeForSale;
    }

    public String getEquiObligatePriceForSale() {
        return EquiObligatePriceForSale;
    }

    public void setEquiObligatePriceForSale(String EquiObligatePriceForSale) {
        this.EquiObligatePriceForSale = EquiObligatePriceForSale;
    }

    public String getInstOtherPriceForSale() {
        return InstOtherPriceForSale;
    }

    public void setInstOtherPriceForSale(String InstOtherPriceForSale) {
        this.InstOtherPriceForSale = InstOtherPriceForSale;
    }

    public String getRealPrice_Total() {
        return RealPrice_Total;
    }

    public void setRealPrice_Total(String RealPrice_Total) {
        this.RealPrice_Total = RealPrice_Total;
    }

    public Object getExcludTax_Equi() {
        return ExcludTax_Equi;
    }

    public void setExcludTax_Equi(Object ExcludTax_Equi) {
        this.ExcludTax_Equi = ExcludTax_Equi;
    }

    public String getLIFSValue_Equi() {
        return LIFSValue_Equi;
    }

    public void setLIFSValue_Equi(String LIFSValue_Equi) {
        this.LIFSValue_Equi = LIFSValue_Equi;
    }

    public Object getNetSales_Equi() {
        return NetSales_Equi;
    }

    public void setNetSales_Equi(Object NetSales_Equi) {
        this.NetSales_Equi = NetSales_Equi;
    }

    public Object getExcludTax_Inst() {
        return ExcludTax_Inst;
    }

    public void setExcludTax_Inst(Object ExcludTax_Inst) {
        this.ExcludTax_Inst = ExcludTax_Inst;
    }

    public String getLIFSValue_Inst() {
        return LIFSValue_Inst;
    }

    public void setLIFSValue_Inst(String LIFSValue_Inst) {
        this.LIFSValue_Inst = LIFSValue_Inst;
    }

    public Object getNetSales_Inst() {
        return NetSales_Inst;
    }

    public void setNetSales_Inst(Object NetSales_Inst) {
        this.NetSales_Inst = NetSales_Inst;
    }

    public Object getNetSales_Total() {
        return NetSales_Total;
    }

    public void setNetSales_Total(Object NetSales_Total) {
        this.NetSales_Total = NetSales_Total;
    }

    public Object getFLCMII_Equi() {
        return FLCMII_Equi;
    }

    public void setFLCMII_Equi(Object FLCMII_Equi) {
        this.FLCMII_Equi = FLCMII_Equi;
    }

    public Object getFLCMII_Rate_Equi() {
        return FLCMII_Rate_Equi;
    }

    public void setFLCMII_Rate_Equi(Object FLCMII_Rate_Equi) {
        this.FLCMII_Rate_Equi = FLCMII_Rate_Equi;
    }

    public Object getCMII_Inst() {
        return CMII_Inst;
    }

    public void setCMII_Inst(Object CMII_Inst) {
        this.CMII_Inst = CMII_Inst;
    }

    public Object getCMII_Rate_Inst() {
        return CMII_Rate_Inst;
    }

    public void setCMII_Rate_Inst(Object CMII_Rate_Inst) {
        this.CMII_Rate_Inst = CMII_Rate_Inst;
    }

    public Object getFLCMII() {
        return FLCMII;
    }

    public void setFLCMII(Object FLCMII) {
        this.FLCMII = FLCMII;
    }

    public Object getFLCMII_Rate() {
        return FLCMII_Rate;
    }

    public void setFLCMII_Rate(Object FLCMII_Rate) {
        this.FLCMII_Rate = FLCMII_Rate;
    }

    public Object getAvgMaterialCost() {
        return AvgMaterialCost;
    }

    public void setAvgMaterialCost(Object AvgMaterialCost) {
        this.AvgMaterialCost = AvgMaterialCost;
    }

    public String getFreight() {
        return Freight;
    }

    public void setFreight(String Freight) {
        this.Freight = Freight;
    }

    public Object getMaterialCostsSL() {
        return MaterialCostsSL;
    }

    public void setMaterialCostsSL(Object MaterialCostsSL) {
        this.MaterialCostsSL = MaterialCostsSL;
    }

    public Object getEquiObligatePrice() {
        return EquiObligatePrice;
    }

    public void setEquiObligatePrice(Object EquiObligatePrice) {
        this.EquiObligatePrice = EquiObligatePrice;
    }

    public Object getInstOtherPrice() {
        return InstOtherPrice;
    }

    public void setInstOtherPrice(Object InstOtherPrice) {
        this.InstOtherPrice = InstOtherPrice;
    }

    public Object getAgentCommission() {
        return AgentCommission;
    }

    public void setAgentCommission(Object AgentCommission) {
        this.AgentCommission = AgentCommission;
    }

    public Object getBiddingExpenses() {
        return BiddingExpenses;
    }

    public void setBiddingExpenses(Object BiddingExpenses) {
        this.BiddingExpenses = BiddingExpenses;
    }

    public Object getFCCost_Equi() {
        return FCCost_Equi;
    }

    public void setFCCost_Equi(Object FCCost_Equi) {
        this.FCCost_Equi = FCCost_Equi;
    }

    public Object getFCCMII_Equi() {
        return FCCMII_Equi;
    }

    public void setFCCMII_Equi(Object FCCMII_Equi) {
        this.FCCMII_Equi = FCCMII_Equi;
    }

    public Object getFCCMII_Rate_Equi() {
        return FCCMII_Rate_Equi;
    }

    public void setFCCMII_Rate_Equi(Object FCCMII_Rate_Equi) {
        this.FCCMII_Rate_Equi = FCCMII_Rate_Equi;
    }

    public Object getFCCMII_Rate() {
        return FCCMII_Rate;
    }

    public void setFCCMII_Rate(Object FCCMII_Rate) {
        this.FCCMII_Rate = FCCMII_Rate;
    }

    public Object getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Object CreateDate) {
        this.CreateDate = CreateDate;
    }

    public Object getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(Object ModifiedDate) {
        this.ModifiedDate = ModifiedDate;
    }

    public Object getPriceState() {
        return PriceState;
    }

    public void setPriceState(Object PriceState) {
        this.PriceState = PriceState;
    }

    public boolean isDisableState() {
        return DisableState;
    }

    public void setDisableState(boolean DisableState) {
        this.DisableState = DisableState;
    }

    public String getEQS_Guid() {
        return EQS_Guid;
    }

    public void setEQS_Guid(String EQS_Guid) {
        this.EQS_Guid = EQS_Guid;
    }

    public String getElevatorType() {
        return ElevatorType;
    }

    public void setElevatorType(String ElevatorType) {
        this.ElevatorType = ElevatorType;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String TypeId) {
        this.TypeId = TypeId;
    }

    public String getElevatorNo() {
        return ElevatorNo;
    }

    public void setElevatorNo(String ElevatorNo) {
        this.ElevatorNo = ElevatorNo;
    }

    public String getYSFXS() {
        return YSFXS;
    }

    public void setYSFXS(String YSFXS) {
        this.YSFXS = YSFXS;
    }

    public Object getElevatorLoad() {
        return ElevatorLoad;
    }

    public void setElevatorLoad(Object ElevatorLoad) {
        this.ElevatorLoad = ElevatorLoad;
    }

    public Object getSpeed() {
        return Speed;
    }

    public void setSpeed(Object Speed) {
        this.Speed = Speed;
    }

    public Object getRungsWidth() {
        return RungsWidth;
    }

    public void setRungsWidth(Object RungsWidth) {
        this.RungsWidth = RungsWidth;
    }

    public Object getAngle() {
        return Angle;
    }

    public void setAngle(Object Angle) {
        this.Angle = Angle;
    }

    public int getElevatorCount() {
        return ElevatorCount;
    }

    public void setElevatorCount(int ElevatorCount) {
        this.ElevatorCount = ElevatorCount;
    }

    public Object getCreateDateStr() {
        return CreateDateStr;
    }

    public void setCreateDateStr(Object CreateDateStr) {
        this.CreateDateStr = CreateDateStr;
    }

    public String getInstanceNodeName() {
        return InstanceNodeName;
    }

    public void setInstanceNodeName(String InstanceNodeName) {
        this.InstanceNodeName = InstanceNodeName;
    }

    public String getInstanceGuid() {
        return InstanceGuid;
    }

    public void setInstanceGuid(String InstanceGuid) {
        this.InstanceGuid = InstanceGuid;
    }

    public Object getNonState() {
        return NonState;
    }

    public void setNonState(Object NonState) {
        this.NonState = NonState;
    }

    public Object getVersionNum() {
        return VersionNum;
    }

    public void setVersionNum(Object VersionNum) {
        this.VersionNum = VersionNum;
    }

    public Object getIsConfirmVersion() {
        return IsConfirmVersion;
    }

    public void setIsConfirmVersion(Object IsConfirmVersion) {
        this.IsConfirmVersion = IsConfirmVersion;
    }

    public String getElevatorProduct() {
        return ElevatorProduct;
    }

    public void setElevatorProduct(String ElevatorProduct) {
        this.ElevatorProduct = ElevatorProduct;
    }

    public Object getProductNo() {
        return ProductNo;
    }

    public void setProductNo(Object ProductNo) {
        this.ProductNo = ProductNo;
    }

    public Object getGuaranteeDate() {
        return GuaranteeDate;
    }

    public void setGuaranteeDate(Object GuaranteeDate) {
        this.GuaranteeDate = GuaranteeDate;
    }

    public Object getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(Object DeliveryDate) {
        this.DeliveryDate = DeliveryDate;
    }

    public Object getFreeInsuranceDate() {
        return FreeInsuranceDate;
    }

    public void setFreeInsuranceDate(Object FreeInsuranceDate) {
        this.FreeInsuranceDate = FreeInsuranceDate;
    }

    public Object getIsNonstandard() {
        return IsNonstandard;
    }

    public void setIsNonstandard(Object IsNonstandard) {
        this.IsNonstandard = IsNonstandard;
    }

    public Object getIsProductUpdate() {
        return IsProductUpdate;
    }

    public void setIsProductUpdate(Object IsProductUpdate) {
        this.IsProductUpdate = IsProductUpdate;
    }

    public int getLDSState() {
        return LDSState;
    }

    public void setLDSState(int LDSState) {
        this.LDSState = LDSState;
    }

    public int getDSState() {
        return DSState;
    }

    public void setDSState(int DSState) {
        this.DSState = DSState;
    }

    public Object getLDSProductGuid() {
        return LDSProductGuid;
    }

    public void setLDSProductGuid(Object LDSProductGuid) {
        this.LDSProductGuid = LDSProductGuid;
    }

    public Object getDSProductGuid() {
        return DSProductGuid;
    }

    public void setDSProductGuid(Object DSProductGuid) {
        this.DSProductGuid = DSProductGuid;
    }

    public Object getLDSTaskId() {
        return LDSTaskId;
    }

    public void setLDSTaskId(Object LDSTaskId) {
        this.LDSTaskId = LDSTaskId;
    }

    public Object getDrawingState() {
        return DrawingState;
    }

    public void setDrawingState(Object DrawingState) {
        this.DrawingState = DrawingState;
    }

    public boolean isIsTransactUserName() {
        return IsTransactUserName;
    }

    public void setIsTransactUserName(boolean IsTransactUserName) {
        this.IsTransactUserName = IsTransactUserName;
    }

    public boolean isIsXSDXM() {
        return IsXSDXM;
    }

    public void setIsXSDXM(boolean IsXSDXM) {
        this.IsXSDXM = IsXSDXM;
    }

    public Object getEQSState() {
        return EQSState;
    }

    public void setEQSState(Object EQSState) {
        this.EQSState = EQSState;
    }

    public Object getCutStringElevatorNo() {
        return CutStringElevatorNo;
    }

    public void setCutStringElevatorNo(Object CutStringElevatorNo) {
        this.CutStringElevatorNo = CutStringElevatorNo;
    }

    @Override
    public QuotePrice clone() throws CloneNotSupportedException {
        return (QuotePrice)super.clone();
    }
}
