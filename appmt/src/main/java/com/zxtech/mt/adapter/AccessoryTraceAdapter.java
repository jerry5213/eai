package com.zxtech.mt.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.mt.entity.Icon;
import com.zxtech.mtos.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syp523 on 2017/8/28.
 */

public class AccessoryTraceAdapter extends BaseQuickAdapter<HashMap<String,String>, BaseViewHolder> {

    public AccessoryTraceAdapter(@Nullable List<HashMap<String, String>> data) {
        super(R.layout.item_trace, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HashMap<String, String> item) {
        helper.setText(R.id.title_textview,item.get("title"));
        helper.setText(R.id.content_textview,item.get("content"));

    }
}
