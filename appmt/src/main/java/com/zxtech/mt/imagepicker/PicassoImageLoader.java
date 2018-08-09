package com.zxtech.mt.imagepicker;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/3/28
 * 描    述：我的Github地址  https://github.com/jeasonlzy0216
 * 修订历史：
 * ================================================
 */

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mtos.R;

public class PicassoImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)//
                .load(SPUtils.get(activity,"RESOURCE_URL","")+path)//
                .placeholder(R.drawable.default_image)//
                .error(R.drawable.default_image)//
                .override(width, height)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    }
}
