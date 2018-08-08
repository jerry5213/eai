package com.zxtech.ecs.adapter;

import android.content.Context;


import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.DropDownBean;

import java.util.List;

public class DropDownAdapter extends CommonAdapter<DropDownBean> {


	public DropDownAdapter(Context context, int layoutId, List<DropDownBean> datas) {
		super(context, layoutId, datas);
	}

	@Override
	public void convert(ViewHolder holder, DropDownBean t, int position) {
		holder.setText(R.id.text, t.toString());

	}

}
