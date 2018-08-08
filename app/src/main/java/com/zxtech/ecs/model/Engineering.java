package com.zxtech.ecs.model;

import android.text.TextUtils;

/**
 * Created by syp523 on 2018/4/4.
 */

public class Engineering {


    /**
     * Guid : c5557cfa-e343-43d4-a669-2d4e31636cf8
     * ProjectGuid : 37c2f14a-49d8-4da1-8fa1-8dc6a20c4fd7
     * DrawingNo : TJ2018-00973-001
     * VersonNum : A
     * ProjectName : BF测试0001
     * SalesmanUserName : BF销售员
     * BranchName : 沈阳分公司
     * strSpecification : STKJS1000(800/1.0)
     * layoutName :
     * elevatorNoList : A:L1
     * createDate : 2018-04-03 15:10:28
     * instanceNodeName : 土建图申请
     * DrawingState : 未发起
     */

    private String Guid;
    private String ProjectGuid;
    private String DrawingNo;
    private String VersonNum;
    private String ProjectName;
    private String SalesmanUserName;
    private String BranchName;
    private String strSpecification;
    private String layoutName;
    private String elevatorNoList;
    private String createDate;
    private String instanceNodeName;
    private String DrawingState;
    private String TypeId;
    private String ElevatorType;
    private String ElevatorProduct;
    private String TaskGuId;
    private String ElevatorAssignGuid;


    public String getElevatorNumber() {
        if (TextUtils.isEmpty(elevatorNoList)) {
            return "";
        } else {
            String s = "";
            String[] split = elevatorNoList.split(";");
            switch (split.length) {
                case 1:
                    s += "单梯";
                    break;
                case 2:
                    s += "双梯";
                    break;
                case 3:
                    s += "三梯";
                    break;
                case 4:
                    s += "四梯";
                    break;
                case 5:
                    s += "五梯";
                    break;
                case 6:
                    s += "六梯";
                    break;
                case 7:
                    s += "七梯";
                    break;
                case 8:
                    s += "八梯";
                    break;
            }
            return s;
        }
    }


    public boolean showDownload() {
        boolean isShow = false;
        if (DrawingState != null && DrawingState.equals("已出图")) {
            isShow = true;
        }

        return isShow;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public String getProjectGuid() {
        return ProjectGuid;
    }

    public void setProjectGuid(String ProjectGuid) {
        this.ProjectGuid = ProjectGuid;
    }

    public String getDrawingNo() {
        return DrawingNo;
    }

    public void setDrawingNo(String DrawingNo) {
        this.DrawingNo = DrawingNo;
    }

    public String getVersonNum() {
        return VersonNum;
    }

    public void setVersonNum(String VersonNum) {
        this.VersonNum = VersonNum;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public String getSalesmanUserName() {
        return SalesmanUserName;
    }

    public void setSalesmanUserName(String SalesmanUserName) {
        this.SalesmanUserName = SalesmanUserName;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String BranchName) {
        this.BranchName = BranchName;
    }

    public String getStrSpecification() {
        return strSpecification;
    }

    public void setStrSpecification(String strSpecification) {
        this.strSpecification = strSpecification;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public String getElevatorNoList() {
        return elevatorNoList;
    }

    public void setElevatorNoList(String elevatorNoList) {
        this.elevatorNoList = elevatorNoList;
    }

    public String getCreateDate() {
        if (createDate != null) {
            return createDate.substring(0, 10);
        }
        return "";
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getInstanceNodeName() {
        return instanceNodeName;
    }

    public void setInstanceNodeName(String instanceNodeName) {
        this.instanceNodeName = instanceNodeName;
    }

    public String getDrawingState() {
        return DrawingState;
    }

    public void setDrawingState(String DrawingState) {
        this.DrawingState = DrawingState;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    public String getElevatorType() {
        return ElevatorType;
    }

    public void setElevatorType(String elevatorType) {
        ElevatorType = elevatorType;
    }

    public String getElevatorProduct() {
        return ElevatorProduct;
    }

    public void setElevatorProduct(String elevatorProduct) {
        ElevatorProduct = elevatorProduct;
    }

    public String getTaskGuId() {
        return TaskGuId;
    }

    public void setTaskGuId(String taskGuId) {
        TaskGuId = taskGuId;
    }

    public String getElevatorAssignGuid() {
        return ElevatorAssignGuid;
    }

    public void setElevatorAssignGuid(String elevatorAssignGuid) {
        ElevatorAssignGuid = elevatorAssignGuid;
    }
}
