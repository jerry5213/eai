package com.zxtech.mt.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by syp523 on 2017/7/25.
 */

public class FontView extends android.support.v7.widget.AppCompatTextView {


    /*
     * 控件在xml加载的时候是调用两个参数的构造函数 ，为了自定义的控件的完整性我们可以
     * 都把构造函数写出来
     */
    public FontView(Context context) {
        super(context);
        init(context);
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FontView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context) {
        //设置字体图标
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/myicon.ttf");
        this.setTypeface(font);
    }
}
