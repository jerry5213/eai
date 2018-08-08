package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.FileList;

import java.util.List;

/**
 * @auth: hexl
 * @date: 2018/3/1
 * @desc:
 */

public class PhotoAdapter extends BaseQuickAdapter<FileList, BaseViewHolder> {

	public PhotoAdapter(int layoutResId, @Nullable List<FileList> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, FileList item) {
		//Bitmap bitmap = BitmapFactory.decodeFile(item.getFileUrl());
		//helper.setImageBitmap(R.id.iv_qms_img,bitmap);

		Glide.with(mContext).load(item.getFileUrl()).into(((ImageView) helper.getView(R.id.iv_qms_img)));
		//helper.addOnClickListener(R.id.iv_qms_img);
	}
}
