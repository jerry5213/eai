package com.zxtech.mt.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zxtech.module.common.base.BaseApplication;
import com.zxtech.module.common.base.IApplicationDelegate;
import com.zxtech.mt.imagepicker.CropImageView;
import com.zxtech.mt.imagepicker.ImagePicker;
import com.zxtech.mt.imagepicker.PicassoImageLoader;
import com.zxtech.mt.utils.DensityUtil;

import java.util.Locale;

/**
 * Created by syp523 on 2016/11/25.
 */
public class MyApplication extends BaseApplication {
    /**
     * (non-Javadoc)
     *
     * @see android.app.Application#onCreate()
     */
    private static  Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        initImageLoader(mContext);
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        imagePicker.setFocusWidth(DensityUtil.px2dip(mContext,800));
        imagePicker.setFocusHeight(DensityUtil.px2dip(mContext,800));
        imagePicker.setOutPutX(400);
        imagePicker.setOutPutY(200);
        imagePicker.setShowCamera(false);
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(true);
        imagePicker.setCrop(false);
    }


    public static  Context getContext(){
        return mContext;
    }

    protected void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(3);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheSize(104857600);
        config.tasksProcessingOrder(QueueProcessingType.FIFO);
         ImageLoader.getInstance().init(config.build());

    }


}
