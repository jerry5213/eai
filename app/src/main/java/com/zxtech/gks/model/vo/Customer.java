package com.zxtech.gks.model.vo;

import java.io.Serializable;

/**
 * Created by SYP521 on 2017/12/31.
 */

public class Customer implements Serializable {

    private String Guid;
    private String CustomerName;
    private String CustomerProperty;
    private String PhoneNumber;
    private String BankName;
    private String CreateDate;
    private String BankAccount;
    private String CustomerContact;
    private String BankOnCity;
    private String CustomerAdd;
    private String TaxNumber;
    private String TEL;
    private String PostalCode;
    private String CreateUserId;
    private String CustomerRelationship;
    private String PS;
    private boolean Enabled;

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getCustomerProperty() {
        return CustomerProperty;
    }

    public void setCustomerProperty(String CustomerProperty) {
        this.CustomerProperty = CustomerProperty;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String BankAccount) {
        this.BankAccount = BankAccount;
    }

    public String getCustomerContact() {
        return CustomerContact;
    }

    public void setCustomerContact(String CustomerContact) {
        this.CustomerContact = CustomerContact;
    }

    public String getBankOnCity() {
        return BankOnCity;
    }

    public void setBankOnCity(String BankOnCity) {
        this.BankOnCity = BankOnCity;
    }

    public String getCustomerAdd() {
        return CustomerAdd;
    }

    public void setCustomerAdd(String CustomerAdd) {
        this.CustomerAdd = CustomerAdd;
    }

    public String getTaxNumber() {
        return TaxNumber;
    }

    public void setTaxNumber(String TaxNumber) {
        this.TaxNumber = TaxNumber;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String PostalCode) {
        this.PostalCode = PostalCode;
    }

    public String getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(String CreateUserId) {
        this.CreateUserId = CreateUserId;
    }

    public String getCustomerRelationship() {
        return CustomerRelationship;
    }

    public void setCustomerRelationship(String CustomerRelationship) {
        this.CustomerRelationship = CustomerRelationship;
    }

    public String getPS() {
        return PS;
    }

    public void setPS(String PS) {
        this.PS = PS;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean Enabled) {
        this.Enabled = Enabled;
    }
}
