package com.zxtech.ecs.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.Setting;

import java.util.List;

/**
 * Created by syp523 on 2018/2/2.
 */

public class SettingAdapter extends CommonAdapter<Setting> {
    public SettingAdapter(Context context, int layoutId, List<Setting> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Setting setting, int position) {
        holder.setText(R.id.title_tv,setting.getTitle());
        holder.setImageResource(R.id.icon_iv,setting.getDrawable());
    }

}
