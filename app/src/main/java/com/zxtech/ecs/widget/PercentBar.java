package com.zxtech.ecs.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.ecs.R;


public class PercentBar extends LinearLayout {


    protected Context mContext;

    private boolean data_flag;

    private int[] arrays = new int[]{};

    public PercentBar(Context context) {
        this(context, null);
    }

    public PercentBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

    }


    @Override
    protected void onLayout(boolean c, int l, int t, int r, int b) {
        super.onLayout(c, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data_flag) {
            int[] colorfulArr = mContext.getResources().getIntArray(R.array.bi_sale);
            int height = this.getHeight();
            float sum = 0;
            for (int i = 0; i < this.arrays.length; i++) {
                sum += this.arrays[i];
            }
            this.removeAllViews();
            for (int i = 0; i < this.arrays.length; i++) {
                TextView view = new TextView(mContext);
                view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int) (height * this.arrays[i] / sum)));
                view.setBackgroundColor(colorfulArr[i % 5]);
                view.setText((int) (this.arrays[i] / sum * 100) + "%");
                view.setGravity(Gravity.CENTER);
                this.addView(view);
            }

            setWillNotDraw(true);
        }
    }




    public void setArrays(int[] arrays) {
        this.arrays = arrays;
        data_flag = true;
        setWillNotDraw(false);
    }
}
