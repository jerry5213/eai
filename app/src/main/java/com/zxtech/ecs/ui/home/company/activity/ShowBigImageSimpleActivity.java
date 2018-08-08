package com.zxtech.ecs.ui.home.company.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SYP521 on 2018/3/7.
 */

public class ShowBigImageSimpleActivity extends BaseActivity {

    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_big_image_simple;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        String url = getIntent().getStringExtra("url");
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//启动缓存
                .into(imageView);

    }

    @OnClick(R.id.iv_close)
    public void click(){
        finish();
    }
}
