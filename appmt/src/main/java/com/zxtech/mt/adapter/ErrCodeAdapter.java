package com.zxtech.mt.adapter;

import android.content.Context;


import com.zxtech.mtos.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Chw on 2016/7/7.
 */
public class ErrCodeAdapter extends CommonAdapter<Map<String,String>> {
    public ErrCodeAdapter(Context context, List<Map<String,String>> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Map<String,String> map, int position) {
        holder.setText(R.id.code_textview,map.get("code"));

        holder.setText(R.id.content_textview,map.get("description"));
    }
}
