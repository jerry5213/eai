package com.zxtech.mt.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.entity.MtWorkPlanAddtion;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by syp523 on 2017/8/10.
 */

public class MtFormPhotoAdapter extends CommonAdapter<MtWorkPlanAddtion> {
    public MtFormPhotoAdapter(Context context, List<MtWorkPlanAddtion> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, MtWorkPlanAddtion addtion, int position) {
        ImageView imageview = holder.getView(R.id.imageview);
        if (position == mDatas.size()-1) {
            imageview.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.camera));
        }else{
            Glide.with(mContext).load(SPUtils.get(mContext,"RESOURCE_URL","")+addtion.getAddition_url()).placeholder(R.drawable.camera)
                    .error(R.drawable.camera).into(imageview);
        }

    }

    public void addItem(MtWorkPlanAddtion addtion){
        mDatas.add(mDatas.size()-1,addtion);
        this.notifyDataSetChanged();
    }
}
