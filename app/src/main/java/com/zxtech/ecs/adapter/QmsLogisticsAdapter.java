package com.zxtech.ecs.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.LogisticsInfoEntity;

import java.util.List;

/**
 * AutoCheckMessage
 */

public class QmsLogisticsAdapter extends BaseQuickAdapter<LogisticsInfoEntity.LogisticsListBean, BaseViewHolder> {
    public QmsLogisticsAdapter(int layoutResId, @Nullable List<LogisticsInfoEntity.LogisticsListBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //设置数据颜色，防止view 复用，必须每个设置
        if (position == 0) {  //上顶部背景透明，点是灰色,字体是绿色
            holder.setBackgroundColor(R.id.view_top_line, Color.TRANSPARENT);
            holder.setBackgroundRes(R.id.iv_expres_spot, R.drawable.express_point_new);
        } else {
            holder.setBackgroundRes(R.id.iv_expres_spot, R.drawable.express_point_old);
        }

    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        return super.getItemView(layoutResId, parent);
    }

    @Override
    protected void convert(BaseViewHolder helper, LogisticsInfoEntity.LogisticsListBean item) {
        helper.setText(R.id.tv_content1, item.getContent1())
                .setText(R.id.tv_content2, item.getContent2())
                .setText(R.id.tv_qms_express_time, item.getContent3());
    }
}
