package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;
import android.util.TypedValue;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.CompanyEntity;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * @auth: chw
 * @date: 2018/2/2
 * @desc:
 */

public class PropagandistVideoAdapter extends BaseQuickAdapter<CompanyEntity.ResultInfoBean, BaseViewHolder> {


    public PropagandistVideoAdapter(int layoutResId, @Nullable List<CompanyEntity.ResultInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyEntity.ResultInfoBean item) {

        final JZVideoPlayerStandard player = helper.getView(R.id.player_list_video);
        player.titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        player.setUp(item.getFileInfoList().get(0).getPath(), JZVideoPlayerStandard.SCREEN_WINDOW_LIST, item.getTitle());
        Glide.with(mContext).load(item.getFileInfoList().get(0).getCoverPath()).into(player.thumbImageView);

//		player.startButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//点击播放按钮开始下载文件到本地,
//
//				player.startVideo();
//			}
//		});
    }
}
