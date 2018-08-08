package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/6/21.
 */

public class PayMethod {


    /**
     * Guid : 8abef59b-0c1e-48c4-8e54-2a6ef5056afa
     * Property : 设备付款
     * Description : 合同签订后 ~ 天内，支付设备总价款 ^ %作为定金。
     * CreateUserId : f886f3ef-5dd5-4572-b189-fb3ea5ef113b
     * CreateDate : 2017/4/19 9:03:59
     * Enabled : True
     * ContractProperty : A41
     * IsForAgent : True
     * IsChecked : False
     * Days :
     * Percent :
     */

    private String Guid;
    private String Property;
    private String Description;
    private String CreateUserId;
    private String CreateDate;
    private String Enabled;
    private String ContractProperty;
    private String IsForAgent;
    private String IsChecked;
    private String Days;
    private String Percent;

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public String getProperty() {
        return Property;
    }

    public void setProperty(String Property) {
        this.Property = Property;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(String CreateUserId) {
        this.CreateUserId = CreateUserId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public String getEnabled() {
        return Enabled;
    }

    public void setEnabled(String Enabled) {
        this.Enabled = Enabled;
    }

    public String getContractProperty() {
        return ContractProperty;
    }

    public void setContractProperty(String ContractProperty) {
        this.ContractProperty = ContractProperty;
    }

    public String getIsForAgent() {
        return IsForAgent;
    }

    public void setIsForAgent(String IsForAgent) {
        this.IsForAgent = IsForAgent;
    }

    public String getIsChecked() {
        return IsChecked;
    }

    public void setIsChecked(String IsChecked) {
        this.IsChecked = IsChecked;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String Days) {
        this.Days = Days;
    }

    public String getPercent() {
        return Percent;
    }

    public void setPercent(String Percent) {
        this.Percent = Percent;
    }

    // 拆分合同签订后 ~ 天内，支付设备总价款 ^ %作为定金。
    public String getBeforeText(){
        return Description.split("~")[0].trim();
    }

    public String getMiddleText(){
        int i = Description.indexOf("~");
        int i1 = Description.indexOf("^");
        return Description.substring(i+1,i1).trim();
    }

    public String getAfterText(){
        return Description.split("\\^")[1].trim();
    }

    public String getFirstEditText(){
        if (Description.contains("~")) {
            return Days;
        }
        return null;
    }

    public String getSecondEditText(){
        if (Description.contains("\\^")) {
            return Percent;
        }
        return Percent;
    }

    public boolean isCheck(){
        return "True".equals(IsChecked);
    }
}
