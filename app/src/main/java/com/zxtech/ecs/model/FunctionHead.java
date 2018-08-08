package com.zxtech.ecs.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;


import java.io.Serializable;

import static com.zxtech.ecs.adapter.ExpandableItemAdapter.TYPE_LEVEL_0;

/**
 * Created by syp523 on 2018/3/22.
 */

public class FunctionHead extends AbstractExpandableItem<Parameters> implements MultiItemEntity, Serializable {
    private String title;

    private boolean showRemark;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShowRemark() {
        return showRemark;
    }

    public void setShowRemark(boolean showRemark) {
        this.showRemark = showRemark;
    }

    public FunctionHead(String title, boolean showRemark) {
        this.title = title;
        this.showRemark = showRemark;
    }

    public FunctionHead() {
    }

    public FunctionHead(String title) {
        this.title = title;
    }

    @Override
    public int getLevel() {
        return TYPE_LEVEL_0;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
