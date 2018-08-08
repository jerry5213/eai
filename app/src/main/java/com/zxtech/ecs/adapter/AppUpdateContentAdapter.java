package com.zxtech.ecs.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;

import java.util.List;

/**
 * Created by syp523 on 2018/3/13.
 */

public class AppUpdateContentAdapter extends CommonAdapter<String> {
    public AppUpdateContentAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.content_tv,(position+1)+". "+s);
    }
}
