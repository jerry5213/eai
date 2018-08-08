package com.zxtech.ecs.model;

import java.util.List;

/**
 * Created by syp523 on 2018/7/2.
 */

public class ContractChangeSummary {


    /**
     * Result : true
     * applicationTime : null
     * recoveryTime : 2018-06-30
     * changeReason : 内部原因
     * dicInfoList_bus : [{"changBefore":"税点：","changAfter":"税点：11%"},{"changBefore":"交货地点：","changAfter":"交货地点：葫芦岛"},{"changBefore":"运输方式：","changAfter":"运输方式：工厂代办"}]
     * dicInfoList_tec : [{"elevatorNo":"L2","deviceNo":"","elevatorType":"STKJS1000","changBefore":"A侧轿门样式： A侧第一种门套类型： A侧第一种厅门数量：16 A侧第一种门套数量：16 A侧第一种厅门样式： A侧第二种门套类型： A侧第二种厅门样式： C侧轿门样式： C侧第一种门套类型： C侧第一种厅门样式： C侧第二种门套类型： C侧第二种厅门样式： 轿厢深度：1500 轿厢宽度：1600 门数：5 控制类型：单梯 布置方式： 楼层数：16 残操材质： 扶手位置： 井道深度：2200 井道宽度：2100 轿厢中心至井道左壁：1050 额定载重：1000 左侧门垛尺寸：500 COP材质： 主操纵盘安装位置：右前 外呼面板材质： 机房深度：2200 机房宽度：2200 顶层高度：4200 出入口宽度：900 站数： 门数： 层数： 副操材质： 停站数：16","changAfter":"A侧轿门样式：BF-622M A侧第一种门套类型：小门套 A侧第一种厅门数量：6 A侧第一种门套数量：6 A侧第一种厅门样式：BF-622M A侧第二种门套类型：无选配 A侧第二种厅门样式：无选配 C侧轿门样式：无 C侧第一种门套类型：无选配 C侧第一种厅门样式：无选配 C侧第二种门套类型：无选配 C侧第二种厅门样式：无选配 轿厢深度：1350 轿厢宽度：1400 门数：6 控制类型：并联 布置方式：双梯并联 楼层数：6 残操材质：无选配 扶手位置：后壁 井道深度：2050 井道宽度：1900 轿厢中心至井道左壁：950 额定载重：800 左侧门垛尺寸：450 COP材质：发纹不锈钢 主操纵盘安装位置：左侧 外呼面板材质：发纹不锈钢 机房深度：2050 机房宽度：1900 顶层高度：4100 出入口宽度：800 站数：6 门数：6 层数：6 副操材质：无选配 停站数：6"}]
     * ResultMessage : null
     */

    private boolean Result;
    private String applicationTime;
    private String recoveryTime;
    private String changeReason;
    private Object ResultMessage;
    private List<DicInfoListBusBean> dicInfoList_bus;
    private List<DicInfoListTecBean> dicInfoList_tec;

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean Result) {
        this.Result = Result;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getRecoveryTime() {
        return recoveryTime;
    }

    public void setRecoveryTime(String recoveryTime) {
        this.recoveryTime = recoveryTime;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public Object getResultMessage() {
        return ResultMessage;
    }

    public void setResultMessage(Object ResultMessage) {
        this.ResultMessage = ResultMessage;
    }

    public List<DicInfoListBusBean> getDicInfoList_bus() {
        return dicInfoList_bus;
    }

    public void setDicInfoList_bus(List<DicInfoListBusBean> dicInfoList_bus) {
        this.dicInfoList_bus = dicInfoList_bus;
    }

    public List<DicInfoListTecBean> getDicInfoList_tec() {
        return dicInfoList_tec;
    }

    public void setDicInfoList_tec(List<DicInfoListTecBean> dicInfoList_tec) {
        this.dicInfoList_tec = dicInfoList_tec;
    }

    public static class DicInfoListBusBean {
        /**
         * changBefore : 税点：
         * changAfter : 税点：11%
         */

        private String changBefore;
        private String changAfter;

        public String getChangBefore() {
            return changBefore.replace("rn","\n");
        }

        public void setChangBefore(String changBefore) {
            this.changBefore = changBefore;
        }

        public String getChangAfter() {
            return changAfter.replace("n","\n");
        }

        public void setChangAfter(String changAfter) {
            this.changAfter = changAfter;
        }
    }

    public static class DicInfoListTecBean {
        /**
         * elevatorNo : L2
         * deviceNo :
         * elevatorType : STKJS1000
         * changBefore : A侧轿门样式： A侧第一种门套类型： A侧第一种厅门数量：16 A侧第一种门套数量：16 A侧第一种厅门样式： A侧第二种门套类型： A侧第二种厅门样式： C侧轿门样式： C侧第一种门套类型： C侧第一种厅门样式： C侧第二种门套类型： C侧第二种厅门样式： 轿厢深度：1500 轿厢宽度：1600 门数：5 控制类型：单梯 布置方式： 楼层数：16 残操材质： 扶手位置： 井道深度：2200 井道宽度：2100 轿厢中心至井道左壁：1050 额定载重：1000 左侧门垛尺寸：500 COP材质： 主操纵盘安装位置：右前 外呼面板材质： 机房深度：2200 机房宽度：2200 顶层高度：4200 出入口宽度：900 站数： 门数： 层数： 副操材质： 停站数：16
         * changAfter : A侧轿门样式：BF-622M A侧第一种门套类型：小门套 A侧第一种厅门数量：6 A侧第一种门套数量：6 A侧第一种厅门样式：BF-622M A侧第二种门套类型：无选配 A侧第二种厅门样式：无选配 C侧轿门样式：无 C侧第一种门套类型：无选配 C侧第一种厅门样式：无选配 C侧第二种门套类型：无选配 C侧第二种厅门样式：无选配 轿厢深度：1350 轿厢宽度：1400 门数：6 控制类型：并联 布置方式：双梯并联 楼层数：6 残操材质：无选配 扶手位置：后壁 井道深度：2050 井道宽度：1900 轿厢中心至井道左壁：950 额定载重：800 左侧门垛尺寸：450 COP材质：发纹不锈钢 主操纵盘安装位置：左侧 外呼面板材质：发纹不锈钢 机房深度：2050 机房宽度：1900 顶层高度：4100 出入口宽度：800 站数：6 门数：6 层数：6 副操材质：无选配 停站数：6
         */

        private String elevatorNo;
        private String deviceNo;
        private String elevatorType;
        private String changBefore;
        private String changAfter;

        public String getElevatorNo() {
            return elevatorNo;
        }

        public void setElevatorNo(String elevatorNo) {
            this.elevatorNo = elevatorNo;
        }

        public String getDeviceNo() {
            return deviceNo;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public String getElevatorType() {
            return elevatorType;
        }

        public void setElevatorType(String elevatorType) {
            this.elevatorType = elevatorType;
        }

        public String getChangBefore() {
            return changBefore;
        }

        public void setChangBefore(String changBefore) {
            this.changBefore = changBefore;
        }

        public String getChangAfter() {
            return changAfter;
        }

        public void setChangAfter(String changAfter) {
            this.changAfter = changAfter;
        }
    }
}
