package com.zxtech.esp.ui.msg;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxtech.common.util.T;
import com.zxtech.esp.R;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.MyItemTouchCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;
import g.api.adapter.GRecyclerAdapter;
import g.api.adapter.GRecyclerViewHolder;

/**
 * Created by SYP521 on 2017/7/25.
 */

public class BbsCreateImageAdapter extends GRecyclerAdapter<PhotoInfo,BbsCreateImageAdapter.ViewHolder> implements MyItemTouchCallback.ItemTouchAdapter{


    public BbsCreateImageAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bbs_image_add, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.setData(getItem(position));
        holder.showData();
    }

    public void addData(int position,PhotoInfo photoInfo) {

        getDatas().add(position,photoInfo);
        notifyItemInserted(position);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {

        List<PhotoInfo> results = getDatas();
        if (fromPosition==results.size()-1 || toPosition==results.size()-1){
            return;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(results, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(results, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        getDatas().remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends GRecyclerViewHolder<PhotoInfo> {

        ImageView imageView;
        DisplayImageOptions opts;
        RelativeLayout r_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            opts = BizUtil.getDefaultImageOPTSBuilder(0).build();
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            r_layout = (RelativeLayout) itemView.findViewById(R.id.r_layout);
        }

        @Override
        public boolean isOpenItemClick() {
            return true;
        }

        @Override
        protected void onShowData(PhotoInfo photoInfo) {
            super.onShowData(photoInfo);
            int sw = context.getResources().getDisplayMetrics().widthPixels;
            r_layout.getLayoutParams().height = (int) ((sw - T.dip2px(context,40)) / 3.0f);
            r_layout.getLayoutParams().width = (int) ((sw - T.dip2px(context,40)) / 3.0f);
            if("default".equals(photoInfo.getPhotoPath())){
                imageView.setImageResource(R.mipmap.img_add);
            }else{
                ImageLoader.getInstance().displayImage("file:///"+photoInfo.getPhotoPath(),imageView,opts);
            }
        }

        @Override
        public void onItemClick(View v, PhotoInfo photoInfo) {
            super.onItemClick(v, photoInfo);
            if("default".equals(photoInfo.getPhotoPath())){
                if (getDatas().size()>=6){
                    T.showToast(v.getContext(),"最多只能添加五张图片");
                }else{
                    ((ForumCreateActivity)getActivityFromView(v)).muti();
                }
            }else{
                Intent intent = new Intent(v.getContext(), LPhotoPreviewActivity.class);
                ArrayList list = new ArrayList<>(getDatas());
                list.remove(list.size()-1);
                intent.putExtra("photo_list",list);
                int location = list.indexOf(photoInfo);
                intent.putExtra("position",location);
                v.getContext().startActivity(intent);
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
