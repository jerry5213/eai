package com.zxtech.gks.model.bean;

/**
 * Created by syp521 on 2018/3/1.
 */

public class SaveResult {

    private boolean Results;
    private Object ErrorViewControlId;
    private Object ErrorMessage;
    private String PostInfo;
    private Object PostMessage;
    private Object PostInfoList;
    private Object PostPriceList;

    public boolean isResults() {
        return Results;
    }

    public void setResults(boolean Results) {
        this.Results = Results;
    }

    public Object getErrorViewControlId() {
        return ErrorViewControlId;
    }

    public void setErrorViewControlId(Object ErrorViewControlId) {
        this.ErrorViewControlId = ErrorViewControlId;
    }

    public Object getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(Object ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public String getPostInfo() {
        return PostInfo;
    }

    public void setPostInfo(String PostInfo) {
        this.PostInfo = PostInfo;
    }

    public Object getPostMessage() {
        return PostMessage;
    }

    public void setPostMessage(Object PostMessage) {
        this.PostMessage = PostMessage;
    }

    public Object getPostInfoList() {
        return PostInfoList;
    }

    public void setPostInfoList(Object PostInfoList) {
        this.PostInfoList = PostInfoList;
    }

    public Object getPostPriceList() {
        return PostPriceList;
    }

    public void setPostPriceList(Object PostPriceList) {
        this.PostPriceList = PostPriceList;
    }
}
