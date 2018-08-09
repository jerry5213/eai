package com.zxtech.is.common.net;

/**
 * Created by duomi on 2018/4/18.
 */

public class PageResponse<T> extends BaseResponse<T> {
    private int rowCount;

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}
