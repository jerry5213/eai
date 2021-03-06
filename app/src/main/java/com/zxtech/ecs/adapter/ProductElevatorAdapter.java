package com.zxtech.ecs.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.CompanyEntity;
import com.zxtech.ecs.model.ProductIntroductiono;

import java.util.List;

/**
 * @auth: hexl
 * @date: 2018/2/2
 * @desc:
 */

public class ProductElevatorAdapter extends BaseQuickAdapter<CompanyEntity.ResultInfoBean, BaseViewHolder> {
	private Context mContext;

	public ProductElevatorAdapter(int layoutResId, @Nullable List<CompanyEntity.ResultInfoBean> data) {
		super(layoutResId, data);
	}

	public void setContext(Context context) {
		mContext = context;
	}

	@Override
	protected void convert(BaseViewHolder helper, CompanyEntity.ResultInfoBean item) {
		ImageView imageView = helper.getView(R.id.iv_img);
		if (!item.getFileType().contains("pdf")) {
			helper.setVisible(R.id.iv_type,false);
		}else {
			helper.setVisible(R.id.iv_type,true);
		}
		Glide.with(mContext)
				.load(item.getCoverPath())
				.error(R.drawable.default_image)
				.into(imageView);
	}
}
