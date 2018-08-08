package com.zxtech.gks.model.vo.type;

/**
 * Created by SYP521 on 2018/1/4.
 */

public class CustomerProperty {

    private int ID;
    private int ItemID;
    private String Text;
    private String Value;
    private String Description;
    private int Order;
    private Object IsDel;

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

    public Object getIsDel() {
        return IsDel;
    }

    public void setIsDel(Object IsDel) {
        this.IsDel = IsDel;
    }

    @Override
    public String toString() {
        return Text;
    }
}
