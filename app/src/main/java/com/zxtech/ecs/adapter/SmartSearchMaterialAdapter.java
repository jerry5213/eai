package com.zxtech.ecs.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.util.AppUtil;
import com.zxtech.ecs.util.ColorTemplate;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by syp523 on 2018/1/22.
 */

public class SmartSearchMaterialAdapter extends CommonAdapter<Map<String, String>> {
    private int selectedPosition = -1;

    public SmartSearchMaterialAdapter(Context context, int layoutId, List<Map<String, String>> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Map<String, String> s, int position) {
        TextView part_tv = holder.getView(R.id.material_tv);
        if (Constants.LANGUAGE_ZH.equals(AppUtil.getAppLanguage())) {
            part_tv.setText(s.get("material_name"));
        } else {
            part_tv.setText(s.get("material_name_en"));
        }


        if (selectedPosition == position) {
            part_tv.setBackground(ColorTemplate.getBorder(mContext, true, position));
            part_tv.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            part_tv.setBackground(ColorTemplate.getBorder(mContext, false, position));
            part_tv.setTextColor(mContext.getResources().getColor(R.color.default_text_black_color));
        }
    }


    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}
