package com.zxtech.gks.model.vo.PrProductDetail;

import java.io.Serializable;

/**
 * Created by SYP521 on 2017/12/7.
 */

public class PermissionListBean implements Serializable {

    String HiddenFunctionNo;
    String DisabledFunctionNo;
    String VisibleFunctionNoList;

    public String getHiddenFunctionNo() {
        return HiddenFunctionNo;
    }

    public String getDisabledFunctionNo() {
        return DisabledFunctionNo;
    }

    public String getVisibleFunctionNoList() {
        return VisibleFunctionNoList;
    }
}
