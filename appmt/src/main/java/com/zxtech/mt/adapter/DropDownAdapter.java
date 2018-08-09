package com.zxtech.mt.adapter;

import android.content.Context;


import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by Chw on 2016/8/10.
 */
public class DropDownAdapter extends CommonAdapter<String> {

	public DropDownAdapter(Context context, List<String> datas, int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(ViewHolder holder, String t, int position) {
		holder.setText(R.id.text, t);

	}

}
