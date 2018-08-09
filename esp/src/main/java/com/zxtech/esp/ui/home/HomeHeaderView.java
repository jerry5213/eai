package com.zxtech.esp.ui.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxtech.esp.R;

/**
 * Created by SYP521 on 2017/7/4.
 */

public class HomeHeaderView extends FrameLayout {
    private ImageView iv_head;

    public HomeHeaderView(Context context) {
        super(context);
        setup(context, null);
    }

    public HomeHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public HomeHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_item_home_header, this);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        iv_head.getLayoutParams().height = (int) (context.getResources().getDisplayMetrics().widthPixels * 322f / 750f);
    }

    public void setHeaderImg(String url) {
        ImageLoader.getInstance().displayImage(url, iv_head);
    }
}
