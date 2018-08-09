package com.zxtech.esp.ui.more;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxtech.common.util.T;
import com.zxtech.esp.R;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.RankingVO;
import com.zxtech.esp.util.BizUtil;

import g.api.adapter.GBaseAdapter;
import g.api.adapter.GViewHolder;

/**
 * Created by SYP521 on 2017/7/14.
 */

public class MyRankingAdapter extends GBaseAdapter<RankingVO.Data> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder(parent.getContext());
            convertView = viewHolder.getConvertView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.showData(getItem(position),position);

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    static class ViewHolder extends GViewHolder{

        TextView tv_nickname;
        TextView tv_personality;
        TextView tv_rank;
        ImageView owner_photo;
        DisplayImageOptions opts;

        public ViewHolder(Context context) {
            super(context);
            tv_nickname = findView(R.id.tv_nickname);
            tv_personality = findView(R.id.tv_personality);
            tv_rank = findView(R.id.tv_rank);
            owner_photo = findView(R.id.owner_photo);
            opts = BizUtil.getDefaultImageOPTSBuilder(T.dip2px(context,30.0f)).showImageOnFail(R.mipmap.avatar_default).build();
        }

        @Override
        protected int onSetConvertViewResId() {
            return R.layout.adapter_list_ranking;
        }

        public void showData(RankingVO.Data data,int position) {

            tv_nickname.setText(data.getNick_name());
            tv_personality.setText(data.getTotal_score()+"");
            tv_rank.setText(position+1+"");
            ImageLoader.getInstance().displayImage(URL.getInstance().getFullUrl(data.getPhoto_url()),owner_photo,opts);
        }
    }
}
