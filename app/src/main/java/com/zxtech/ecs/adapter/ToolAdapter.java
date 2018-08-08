package com.zxtech.ecs.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ToolBean;

import java.util.List;

/**
 * Created by syp523 on 2018/7/9.
 */

public class ToolAdapter extends BaseQuickAdapter<ToolBean, BaseViewHolder> {
    private Integer selectedPosition;

    public ToolAdapter(int layoutResId, @Nullable List<ToolBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToolBean item) {
        helper.setText(R.id.text, item.getText());

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setStroke(2, item.getColor());
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setCornerRadius(8);
        if (selectedPosition != null && selectedPosition == helper.getAdapterPosition()) {
            gd.setColor(item.getColor());
            helper.setTextColor(R.id.text, mContext.getResources().getColor(R.color.white));
        } else {
            gd.setColor(GradientDrawable.RECTANGLE);
            helper.setTextColor(R.id.text, item.getColor());
        }
        helper.getView(R.id.text).setBackgroundDrawable(gd);
    }

    public Integer getSelectedPosition() {
        return selectedPosition;
    }

    public void clearSelectedPosition() {
        selectedPosition = null;
        this.notifyDataSetChanged();
    }

    public void setSelectedPosition(Integer selectedPosition) {
        this.selectedPosition = selectedPosition;
        this.notifyDataSetChanged();
    }
}