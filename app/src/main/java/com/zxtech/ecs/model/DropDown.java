package com.zxtech.ecs.model;

/**
 * Created by syp521 on 2018/4/9.
 */

public class DropDown {

    /**
     * ID : 201
     * ItemID : 2
     * Text : 直销
     * Value : 直销
     * Description : 设备合同类型
     * Order : 0
     * IsDel : false
     */

    private int ID;
    private int ItemID;
    private String Text;
    private String Value;
    private String Description;
    private int Order;
    private boolean IsDel;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int Order) {
        this.Order = Order;
    }

    public boolean isIsDel() {
        return IsDel;
    }

    public void setIsDel(boolean IsDel) {
        this.IsDel = IsDel;
    }

    @Override
    public String toString() {
        return Text;
    }

}
