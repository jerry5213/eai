package com.zxtech.ecs.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by syp521 on 2017/10/26.
 */

public class IconFont extends AppCompatTextView {

    public IconFont(Context context) {
        super(context);
        init(context);
    }

    public IconFont(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont/eai.ttf");
        setTypeface(iconfont);
    }
}
