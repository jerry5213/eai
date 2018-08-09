package com.zxtech.mt.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.zxtech.mt.activity.SpaceImageDetailActivity;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.common.VolleySingleton;
import com.zxtech.mt.entity.MtWorkPlan;
import com.zxtech.mt.imagepicker.ImageGridActivity;
import com.zxtech.mt.utils.BitmapCache;
import com.zxtech.mt.utils.DialogUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.widget.SelectDialog;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chw on 2017/1/20.
 */
public class MtPhotoAdapter extends CommonAdapter<MtWorkPlan> {

    public MtPhotoAdapter(Context context, List<MtWorkPlan> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final MtWorkPlan mtWorkPlan, int position) {
        holder.setText(R.id.elevator_code_textview,mtWorkPlan.getElevator_code());
        holder.setText(R.id.mtdate_textview,mtWorkPlan.getPlan_date());
        holder.setText(R.id.work_type_textview,mtWorkPlan.getWork_type_name());
        final ImageView upload_image = holder.getView(R.id.upload_image);

//        if (TextUtils.isEmpty(mtWorkPlan.getMt_photo_url())) {
//            upload_image.setBackgroundResource(R.drawable.edit_light_grey);
//        }else{
        Glide.with(mContext).load(SPUtils.get(mContext,"RESOURCE_URL","")+mtWorkPlan.getMt_photo_url()).placeholder(R.drawable.edit_light_grey)
                    .error(R.drawable.edit_light_grey).into(upload_image);
//        }
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(mtWorkPlan.getMt_photo_url())) {

                    Intent intent = new Intent(mContext, SpaceImageDetailActivity.class);
                    intent.putExtra("url", SPUtils.get(mContext,"RESOURCE_URL","")+mtWorkPlan.getMt_photo_url());
                    ((Activity)mContext).startActivity(intent);
                    ((Activity)mContext).overridePendingTransition(0, 0);
                }

            }
        });
    }

}


