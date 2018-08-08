package com.zxtech.gks.model.vo.contract;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SYP521 on 2017/12/15.
 */

public class WorkFlowNode {

    private String CompleteTime;
    private String CreateTime;
    private String InstanceNodeName;
    private String SubmitDescription;
    private String SubmitResult;
    private String TransactUserName;

    public String getCompleteTime() {

        try {
            if (TextUtils.isEmpty(CompleteTime)) {
                return "";
            }
            long timeInMills = Long.parseLong(CompleteTime.substring(6, 19));
            return new SimpleDateFormat("yyyy/MM/dd").format(new Date(timeInMills));
        } catch (Exception e) {
            e.printStackTrace();
            return CompleteTime;
        }
    }

    public String getCreateTime() {

        try {
            if (TextUtils.isEmpty(CreateTime)) {
                return "";
            }
            long timeInMills = Long.parseLong(CreateTime.substring(6, 19));
            return new SimpleDateFormat("yyyy/MM/dd").format(new Date(timeInMills));
        } catch (Exception e) {
            e.printStackTrace();
            return CreateTime;
        }
    }

    public String getInstanceNodeName() {
        return InstanceNodeName;
    }

    public void setInstanceNodeName(String InstanceNodeName) {
        this.InstanceNodeName = InstanceNodeName;
    }

    public String getSubmitDescription() {
        return SubmitDescription;
    }

    public void setSubmitDescription(String SubmitDescription) {
        this.SubmitDescription = SubmitDescription;
    }

    public String getSubmitResult() {
        return SubmitResult;
    }

    public void setSubmitResult(String SubmitResult) {
        this.SubmitResult = SubmitResult;
    }

    public String getTransactUserName() {
        return TransactUserName;
    }

    public void setTransactUserName(String TransactUserName) {
        this.TransactUserName = TransactUserName;
    }
}
