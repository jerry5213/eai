package com.zxtech.ecs.model;

import java.io.Serializable;

/**
 * Created by syp523 on 2017/12/27.
 */

public class Collection implements Serializable {
    private static final long serialVersionUID = 1257944661480652794L;

    private String Guid;

    private boolean HaveHome;

    private String ElevatorProduct;

    private String ElevatorType;

    private int HW;

    private int HD;

    private int FloorCountNum;

    private int ElevatorPrice;

    private int ComfortLevel;

    private int SafetyLevel;

    private int TrafficEfficiency;

    private int Aesthetic;

    private int EnergyLevel;

    private String CreateDate;

    private String CollectionName;
    private String Es_EIL;
    private String Es_V;
    private String Es_Angle;

    private String Es_SW;
    private String Es_LS;
    private String Es_TH;
    private String Es_HD;

    private String El_Load;
    private String El_V;


    public String getEl_Load() {
        return El_Load;
    }

    public void setEl_Load(String el_Load) {
        El_Load = el_Load;
    }

    public String getEl_V() {
        return El_V;
    }

    public void setEl_V(String el_V) {
        El_V = el_V;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public boolean isHaveHome() {
        return HaveHome;
    }

    public void setHaveHome(boolean haveHome) {
        HaveHome = haveHome;
    }

    public String getElevatorType() {
        return ElevatorType;
    }

    public void setElevatorType(String elevatorType) {
        ElevatorType = elevatorType;
    }

    public int getHW() {
        return HW;
    }

    public void setHW(int HW) {
        this.HW = HW;
    }

    public int getHD() {
        return HD;
    }

    public void setHD(int HD) {
        this.HD = HD;
    }

    public int getFloorCountNum() {
        return FloorCountNum;
    }

    public void setFloorCountNum(int floorCountNum) {
        FloorCountNum = floorCountNum;
    }

    public int getElevatorPrice() {
        return ElevatorPrice;
    }

    public void setElevatorPrice(int elevatorPrice) {
        ElevatorPrice = elevatorPrice;
    }

    public int getComfortLevel() {
        return ComfortLevel;
    }

    public void setComfortLevel(int comfortLevel) {
        ComfortLevel = comfortLevel;
    }

    public int getSafetyLevel() {
        return SafetyLevel;
    }

    public void setSafetyLevel(int safetyLevel) {
        SafetyLevel = safetyLevel;
    }

    public int getTrafficEfficiency() {
        return TrafficEfficiency;
    }

    public void setTrafficEfficiency(int trafficEfficiency) {
        TrafficEfficiency = trafficEfficiency;
    }

    public int getAesthetic() {
        return Aesthetic;
    }

    public void setAesthetic(int aesthetic) {
        Aesthetic = aesthetic;
    }

    public int getEnergyLevel() {
        return EnergyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        EnergyLevel = energyLevel;
    }

    public String getCreateDate() {
        return this.CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCollectionName() {
        return CollectionName;
    }

    public void setCollectionName(String collectionName) {
        CollectionName = collectionName;
    }

    public String getEs_V() {
        return Es_V;
    }

    public void setEs_V(String es_V) {
        Es_V = es_V;
    }

    public String getEs_Angle() {
        return Es_Angle;
    }

    public void setEs_Angle(String es_Angle) {
        Es_Angle = es_Angle;
    }

    public String getEs_SW() {
        return Es_SW;
    }

    public void setEs_SW(String es_SW) {
        Es_SW = es_SW;
    }

    public String getEs_LS() {
        return Es_LS;
    }

    public void setEs_LS(String es_LS) {
        Es_LS = es_LS;
    }

    public String getEs_TH() {
        return Es_TH;
    }

    public void setEs_TH(String es_TH) {
        Es_TH = es_TH;
    }

    public String getEs_HD() {
        return Es_HD;
    }

    public void setEs_HD(String es_HD) {
        Es_HD = es_HD;
    }

    public String getEs_EIL() {
        return Es_EIL;
    }

    public void setEs_EIL(String es_EIL) {
        Es_EIL = es_EIL;
    }

    public String getElevatorProduct() {
        return ElevatorProduct;
    }

    public void setElevatorProduct(String elevatorProduct) {
        ElevatorProduct = elevatorProduct;
    }
}
