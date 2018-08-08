package com.zxtech.ecs.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.CompanyEntity;
import com.zxtech.ecs.model.ProductElevatorEntity;

import java.util.List;

/**
 * @auth: hexl
 * @date: 2018/2/2
 * @desc:
 */

public class ProjectCaseAdapter extends BaseQuickAdapter<CompanyEntity.ResultInfoBean, BaseViewHolder> {
	private Context mContext;

	public ProjectCaseAdapter(int layoutResId, @Nullable List<CompanyEntity.ResultInfoBean> data) {
		super(layoutResId, data);
	}

	public void setContext(Context context) {
		mContext = context;
	}

	@Override
	protected void convert(BaseViewHolder helper, CompanyEntity.ResultInfoBean item) {
		helper.setText(R.id.tv_title,item.getTitle());
		ImageView ivImg = helper.getView(R.id.iv_img);
		Glide.with(mContext)
				.load(item.getCoverPath())
				.placeholder(R.drawable.default_image)
				.error(R.drawable.default_image)
				.into(ivImg);
	}
}
