package com.zxtech.ecs.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by syp523 on 2017/12/28.
 */

public class MyLeftItemDecoration extends RecyclerView.ItemDecoration {
    private int lineHeight = 1;

    public MyLeftItemDecoration() {
    }

    public MyLeftItemDecoration(int lineHeight) {
        this.lineHeight = lineHeight;
    }
    /**
     *
     * @param outRect 边界
     * @param view recyclerView ItemView
     * @param parent recyclerView
     * @param state recycler 内部数据管理
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //设定底部边距为1px
        outRect.set(lineHeight, 0, 0, 0);
    }
}



