package com.zxtech.mt.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zxtech.mt.entity.MtWorkPlanAddtion;
import com.zxtech.mt.widget.AutoGridView;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syp523 on 2017/8/2.
 */

public class MtCheckPhotoAdapter extends CommonAdapter<MtWorkPlanAddtion> {
    private PhotoCallBack callBack = null;
    public MtCheckPhotoAdapter(Context context, List<MtWorkPlanAddtion> datas, int layoutId) {
        super(context, datas, layoutId);


    }

    @Override
    public void convert(ViewHolder holder, MtWorkPlanAddtion addtion, final int position) {
        holder.setText(R.id.item_title_textview,addtion.getDict_name());
        AutoGridView item_gridview = holder.getView(R.id.item_gridview);
        item_gridview.setAdapter(new GridAdapter(mContext,addtion.getImages()));

        TextView item_title_textview = holder.getView(R.id.item_title_textview);
        item_title_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.selectedPhoto(position);
            }
        });
    }


    public  void setListener(PhotoCallBack callBack){
        this.callBack = callBack;
    }
    public interface PhotoCallBack{

        void selectedPhoto(int position);
    }
}


