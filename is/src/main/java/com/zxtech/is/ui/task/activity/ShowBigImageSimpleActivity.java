package com.zxtech.is.ui.task.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;

import butterknife.BindView;

/**
 * Created by SYP521 on 2018/3/7.
 */

public class ShowBigImageSimpleActivity extends BaseActivity {

    @BindView(R2.id.imageView)
    ImageView imageView;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_is_show_big_image_simple;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(mToolbar);
        String url = getIntent().getStringExtra("url");
        Glide.with(this)
                .load(url)
                .error(R.mipmap.ic_launcher)//没有网络
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//启动缓存
                .into(imageView);

    }
}
