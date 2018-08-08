package com.zxtech.gks.model.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by syp523 on 2017/12/6.
 */

public class PageParamBean<E> {

    private int TotalCount ;
    @SerializedName("Data")
    private List<E> data ;

    @SerializedName("totalCount")
    private int totalCount2 ;
    @SerializedName("data")
    private List<E> data2 ;


    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public List<E> getData2() {
        return data2;
    }

    public void setData2(List<E> data2) {
        this.data2 = data2;
    }

    public int getTotalCount2() {
        return totalCount2;
    }

    public void setTotalCount2(int totalCount2) {
        this.totalCount2 = totalCount2;
    }
}
