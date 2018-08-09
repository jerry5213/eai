package com.zxtech.is.ui.team.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.BuildConfig;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.attach.Attach;

import java.util.List;

/**
 * Created by syp600 on 2018/4/20.
 */

public class AddBlacklistPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public AddBlacklistPhotoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.common_img_close).addOnClickListener(R.id.common_img_iv);
        String fileType = item.substring(item.lastIndexOf(".") + 1);
        switch (fileType) {
            case "doc":
                break;
            case "xlsx":
                break;
            case "txt":
                break;
            case "pdf":
                break;
            case "jpg":
            case "png":
                Glide.with(mContext).load(item).into(((ImageView) helper.getView(R.id.common_img_iv)));
                break;
        }
    }
}
