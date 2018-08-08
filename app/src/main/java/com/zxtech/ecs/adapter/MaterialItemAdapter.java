package com.zxtech.ecs.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.DropDownBean;
import com.zxtech.ecs.model.Parameters;

import java.util.List;

/**
 * Created by syp523 on 2018/1/30.
 */

public class MaterialItemAdapter extends CommonAdapter<Parameters.Option> {
    private String selectedValue = null;
    public MaterialItemAdapter(Context context, int layoutId, List<Parameters.Option> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Parameters.Option option, int position) {
        Glide.with(mContext).load(option.getImagePath()).into((ImageView) holder.getView(R.id.imageView));
        holder.setText(R.id.title,option.getText());
        if (selectedValue != null && selectedValue.equals(option.getValue())) {
            holder.setImageResource(R.id.selected_iv,R.drawable.selected);
        }else{
            holder.setImageResource(R.id.selected_iv,0);
        }
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }
}
