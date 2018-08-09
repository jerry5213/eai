package com.zxtech.esp.ui.msg;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxtech.common.util.T;
import com.zxtech.esp.R;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.BbsPostReplyVO;
import com.zxtech.esp.util.BizUtil;

import g.api.adapter.GBaseAdapter;

/**
 * Created by SYP521 on 2017/7/15.
 */

public class BbsReplyAdapter extends GBaseAdapter<BbsPostReplyVO.Data> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder(parent.getContext());
            if(TextUtils.isEmpty(getItem(position).getParent_id())){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_course_talk1,parent,false);
            }else{
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_course_talk2,parent,false);
            }
            viewHolder.tv_owner = convertView.findViewById(R.id.tv_owner);
            viewHolder.tv_date = convertView.findViewById(R.id.tv_date);
            viewHolder.tv_content = convertView.findViewById(R.id.tv_content);
            viewHolder.tv_comment = convertView.findViewById(R.id.tv_comment);
            viewHolder.owner_photo = convertView.findViewById(R.id.owner_photo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.showData(getItem(position));
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        String typeId = getItem(position).getParent_id();
        if(TextUtils.isEmpty(typeId)){
            return 0;
        }
        return 1;
    }

    static class ViewHolder  implements View.OnClickListener {

        private TextView tv_owner;
        private TextView tv_date;
        private TextView tv_content;
        private TextView tv_comment;
        private ImageView owner_photo;
        private DisplayImageOptions opts;

        public ViewHolder(Context context){
            opts = BizUtil.getDefaultImageOPTSBuilder(T.dip2px(context,30.0f)).showImageOnFail(R.mipmap.avatar_default).build();
        }

        @Override
        public void onClick(View v) {

            int i = v.getId();
            if (i == R.id.tv_comment) {
                BbsPostReplyVO.Data data = (BbsPostReplyVO.Data) v.getTag();
                ((ForumReplyActivity) getActivityFromView(v)).initPopupWindow(data);

            } else {
            }
        }

        public void showData(BbsPostReplyVO.Data data){
            tv_owner.setText(data.getOwner_name());
            tv_date.setText(data.getCreate_date());
            tv_content.setText(data.getContent());
            BizUtil.setIconFont(tv_comment, "\ue608");
            tv_comment.setOnClickListener(this);
            tv_comment.setTag(data);
            if(TextUtils.isEmpty(data.getParent_id())){
                ImageLoader.getInstance().displayImage(URL.getInstance().getFullUrl(data.getPhoto_url()),owner_photo,opts);
            }else{
                owner_photo.setImageResource(R.mipmap.icon_back);
            }
        }
    }

    public static Activity getActivityFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
