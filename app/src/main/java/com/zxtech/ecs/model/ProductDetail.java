package com.zxtech.ecs.model;

import java.util.List;

/**
 * Created by syp523 on 2018/5/18.
 */

public class ProductDetail {


    /**
     * SysEQSList : [{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":7,"TypeId":1,"PriceCode":"BasePrice","PriceName":"基价","SalePrice":"107000","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":9,"TypeId":1,"PriceCode":"$CGJJ-CPrice20180416160752","PriceName":"超高加价","SalePrice":"-1260","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":10,"TypeId":1,"PriceCode":"$BTCJJ-CPrice20180416160752","PriceName":"不停层减价","SalePrice":"-3000","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":12,"TypeId":1,"PriceCode":"JXCZHDJJ","PriceName":"轿厢材质厚度加价","SalePrice":"1000","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":13,"TypeId":1,"PriceCode":"HBZJBJJ","PriceName":"后壁中间壁加价","SalePrice":"200","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":14,"TypeId":1,"PriceCode":"CBZJBJJ","PriceName":"侧壁中间壁加价","SalePrice":"400","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":33,"TypeId":1,"PriceCode":"WZHXSJJ","PriceName":"外召唤显示加价","SalePrice":"100","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":34,"TypeId":1,"PriceCode":"CCXPJJ","PriceName":"残操选配加价","SalePrice":"900","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":35,"TypeId":1,"PriceCode":"YYBZJJ","PriceName":"语音报站加价","SalePrice":"800","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":36,"TypeId":1,"PriceCode":"FSXPJJ","PriceName":"扶手选配加价","SalePrice":"600","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":37,"TypeId":1,"PriceCode":"MBHGNJJ","PriceName":"门保护功能加价","SalePrice":"700","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":42,"TypeId":1,"PriceCode":"FCXSXPJJ","PriceName":"副操显示选配加价","SalePrice":"750","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":48,"TypeId":1,"PriceCode":"CZXICKJJ","PriceName":"操纵箱IC卡装置加价","SalePrice":"1000","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":49,"TypeId":1,"PriceCode":"ICKPJJ","PriceName":"IC卡片加价","SalePrice":"35","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":52,"TypeId":1,"PriceCode":"SPZDXPJJ","PriceName":"视频终端选配加价","SalePrice":"500","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":53,"TypeId":1,"PriceCode":"DZAQJJJ","PriceName":"对重安全钳加价","SalePrice":"6000","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":54,"TypeId":1,"PriceCode":"$ACJMCZJJ-CPrice20180416160752","PriceName":"A侧轿门材质加价","SalePrice":"-500","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":60,"TypeId":1,"PriceCode":"YCJKJJ","PriceName":"远程监控加价","SalePrice":"1500","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":62,"TypeId":1,"PriceCode":"DTKTJJ","PriceName":"电梯空调加价","SalePrice":"5000","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":64,"TypeId":1,"PriceCode":"AQMXPJJ","PriceName":"安全门选配加价","SalePrice":"1000","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":69,"TypeId":1,"PriceCode":"JXDGJJ","PriceName":"轿厢导轨加价","SalePrice":"2120","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":70,"TypeId":1,"PriceCode":"$TMCZCJ-CPrice20180416160752","PriceName":"厅门材质差价","SalePrice":"-400","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":71,"TypeId":1,"PriceCode":"$MTCZCJ-CPrice20180416160752","PriceName":"门套材质差价","SalePrice":"-200","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":71,"TypeId":1,"PriceCode":"GuaranteeDate","PriceName":"设备质保金加价","SalePrice":"0","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":77,"TypeId":1,"PriceCode":"$QYCWZHXSJJ-CPrice20180416160752","PriceName":"其余层外召唤显示加价","SalePrice":"1","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":78,"TypeId":1,"PriceCode":"GMYSGNJJ","PriceName":"关门延时功能加价","SalePrice":"500","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":81,"TypeId":1,"PriceCode":"JDYLJJ","PriceName":"轿底预留加价","SalePrice":"600","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":83,"TypeId":1,"PriceCode":"$ZCMWANJJ-CPrice20180416160752","PriceName":"主操盲文按钮加价","SalePrice":"1","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":84,"TypeId":1,"PriceCode":"$ZCANFGJJ-CPrice20180416160752","PriceName":"主操按钮发光加价","SalePrice":"1","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":85,"TypeId":1,"PriceCode":"$FCMWANJJ-CPrice20180416160752","PriceName":"副操盲文按钮加价","SalePrice":"1","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":86,"TypeId":1,"PriceCode":"$FCANFGJJ-CPrice20180416160752","PriceName":"副操按钮发光加价","SalePrice":"1","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":88,"TypeId":1,"PriceCode":"$CCANJJ-CPrice20180416160752","PriceName":"残操按钮选配加价","SalePrice":"1","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":89,"TypeId":1,"PriceCode":"$WZMWANJJ-CPrice20180416160752","PriceName":"外召盲文按钮加价","SalePrice":"1","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":90,"TypeId":1,"PriceCode":"SJCZJJ","PriceName":"司机操作加价","SalePrice":"500","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":91,"TypeId":1,"PriceCode":"XFGNJJ","PriceName":"消防功能加价","SalePrice":"200","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":93,"TypeId":1,"PriceCode":"UPSJJ","PriceName":"UPS加价","SalePrice":"1500","TPPrice":null},{"EQS_ProductGuid":"df438652-823b-4080-9179-8cd1d462be24","ItemId":94,"TypeId":1,"PriceCode":"VIPJJ","PriceName":"VIP加价","SalePrice":"500","TPPrice":null}]
     * NonStandardLsit : []
     * InstanceNodeName : 流程结束
     */

    private String InstanceNodeName;
    private List<SysEQSListBean> SysEQSList;
    private List<?> NonStandardLsit;

    public String getInstanceNodeName() {
        return InstanceNodeName;
    }

    public void setInstanceNodeName(String InstanceNodeName) {
        this.InstanceNodeName = InstanceNodeName;
    }

    public List<SysEQSListBean> getSysEQSList() {
        return SysEQSList;
    }

    public void setSysEQSList(List<SysEQSListBean> SysEQSList) {
        this.SysEQSList = SysEQSList;
    }

    public List<?> getNonStandardLsit() {
        return NonStandardLsit;
    }

    public void setNonStandardLsit(List<?> NonStandardLsit) {
        this.NonStandardLsit = NonStandardLsit;
    }

    public static class SysEQSListBean {
        /**
         * EQS_ProductGuid : df438652-823b-4080-9179-8cd1d462be24
         * ItemId : 7
         * TypeId : 1
         * PriceCode : BasePrice
         * PriceName : 基价
         * SalePrice : 107000
         * TPPrice : null
         */

        private String EQS_ProductGuid;
        private int ItemId;
        private int TypeId;
        private String PriceCode;
        private String PriceName;
        private int SalePrice;
        private Object TPPrice;

        public String getEQS_ProductGuid() {
            return EQS_ProductGuid;
        }

        public void setEQS_ProductGuid(String EQS_ProductGuid) {
            this.EQS_ProductGuid = EQS_ProductGuid;
        }

        public int getItemId() {
            return ItemId;
        }

        public void setItemId(int ItemId) {
            this.ItemId = ItemId;
        }

        public int getTypeId() {
            return TypeId;
        }

        public void setTypeId(int TypeId) {
            this.TypeId = TypeId;
        }

        public String getPriceCode() {
            return PriceCode;
        }

        public void setPriceCode(String PriceCode) {
            this.PriceCode = PriceCode;
        }

        public String getPriceName() {
            return PriceName;
        }

        public void setPriceName(String PriceName) {
            this.PriceName = PriceName;
        }

        public int getSalePrice() {
            return SalePrice;
        }

        public void setSalePrice(int SalePrice) {
            this.SalePrice = SalePrice;
        }

        public Object getTPPrice() {
            return TPPrice;
        }

        public void setTPPrice(Object TPPrice) {
            this.TPPrice = TPPrice;
        }
    }
}
