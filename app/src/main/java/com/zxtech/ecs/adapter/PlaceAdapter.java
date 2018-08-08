package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.Parameters;

import java.util.List;

/**
 * Created by syp523 on 2018/3/26.
 */

public class PlaceAdapter extends BaseQuickAdapter<Parameters.Option, BaseViewHolder> {
    private String selectedOptionValue;

    public PlaceAdapter(int layoutResId, @Nullable List<Parameters.Option> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Parameters.Option option) {
        Glide.with(mContext).load(option.getImagePath()).into((ImageView) holder.getView(R.id.image));
        if (selectedOptionValue != null && selectedOptionValue.equals(option.getValue())) {
            holder.setImageResource(R.id.selected_iv, R.drawable.selected);
        } else {
            holder.setImageResource(R.id.selected_iv, 0);
        }
    }

    public String getSelectedOptionValue() {
        return selectedOptionValue;
    }

    public void setSelectedOptionValue(String selectedOptionValue) {
        this.selectedOptionValue = selectedOptionValue;
    }
}
