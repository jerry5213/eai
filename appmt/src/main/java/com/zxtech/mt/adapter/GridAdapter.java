package com.zxtech.mt.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zxtech.mt.imagepicker.ImageItem;
import com.zxtech.mt.imagepicker.ImagePicker;
import com.zxtech.mt.imagepicker.ImagePreviewDelActivity;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mtos.R;

import java.util.ArrayList;

/**
 * Created by syp523 on 2017/8/2.
 */

public class GridAdapter extends BaseAdapter {

    private ArrayList<String> imgIds;

    private Context mContext;

    public GridAdapter(Context context,ArrayList<String> imgIds) {
        this.mContext = context;
        this.imgIds = imgIds;
    }

    @Override
    public int getCount() {
        return imgIds.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView =  LayoutInflater.from(mContext).inflate(R.layout.item_grid_photo,
                    null);
            ImageView img = (ImageView) convertView
                    .findViewById(R.id.lv_item_iv_img);
            ViewGroup.LayoutParams p = img.getLayoutParams();
            p.width = p.height = mGetScreenWidth() / 3 - 20;
            convertView.setTag(img);
        }
        ImageView iv = ((ImageView) convertView.getTag());
        Glide.with(mContext).load(SPUtils.get(mContext,"RESOURCE_URL","")+imgIds.get(position)).into(iv);
        iv.setClickable(true);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ImageItem> list = new ArrayList<>();
                for (String url:imgIds) {
                    ImageItem item = new ImageItem();
                    item.path = url;
                    list.add(item);
                }
                Intent intentPreview = new Intent(mContext, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, list);
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                mContext.startActivity(intentPreview);
               // (A)mContext.startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
            }
        });
        return convertView;
    }


    private int mGetScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
