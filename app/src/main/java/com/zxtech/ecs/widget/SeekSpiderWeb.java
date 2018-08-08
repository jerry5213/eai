package com.zxtech.ecs.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.zxtech.ecs.R;

import java.util.ArrayList;
import java.util.List;

public class SeekSpiderWeb extends SpiderWeb implements SeekBar.OnSeekBarChangeListener {

    private TouchUpListener listener = null;

    private boolean showThumbDrawable;

    public SeekSpiderWeb(Context context) {
        super(context);

    }

    public SeekSpiderWeb(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeekSpiderWeb(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    private static int MSG_REFRESH_SEEK_BAR = 100;
    private List<SeekBar> seekBars;
    private Handler handler;
    private boolean seek_flag;

    @Override
    protected void setup(Context context, AttributeSet attrs) {
        super.setup(context, attrs);
        seekBars = new ArrayList<SeekBar>();
        handler = new Handler(context.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MSG_REFRESH_SEEK_BAR)
                    refresh();
            }
        };
    }

    public void refresh() {
        if (data_flag && init_flag) {
            clcSeekBars();
            genSeekBars();
        }
    }

    protected void clcSeekBars() {
        for (SeekBar sb : seekBars) {
            removeView(sb);
        }
        seekBars.clear();
    }

    protected void genSeekBars() {
        for (int i = 0; i < num; i++) {
            SeekBar sb = new SeekBar(getContext());
            sb.setProgressDrawable(null);
            sb.setMax(max);
            if (showThumbDrawable) {
                sb.setThumb(mContext.getResources().getDrawable(R.drawable.thumb));
            }else {
                 sb.getThumb().setAlpha(0);
            }
            sb.setProgress(progress[i]);
            sb.setTag(i);
            int sb_h = sb.getThumb().getMinimumHeight();
            LayoutParams lp = new LayoutParams((int) (r + sb_h), sb_h);
            lp.leftMargin = (int) (x - sb_h / 2f);
            lp.topMargin = (int) (y - sb_h / 2f);
            sb.setPivotX(sb_h / 2);
            sb.setPivotY(sb_h / 2);
            sb.setRotation(calcDegree(i) - 90);
            sb.setOnSeekBarChangeListener(this);
            addView(sb, lp);
            seekBars.add(sb);
        }
    }

    @Override
    protected void onLayout(boolean c, int l, int t, int r, int b) {
        super.onLayout(c, l, t, r, b);
        if (!seek_flag) {
            seek_flag = true;
            handler.sendEmptyMessage(MSG_REFRESH_SEEK_BAR);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int min = max / getReal_max();
        updateProgress(Integer.parseInt(seekBar.getTag().toString()), progress);
        if (progress <= min) {
            seekBar.setProgress(min);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int position = Integer.parseInt(seekBar.getTag().toString());
        updateProgressByReal(position, seekBar.getProgress());
        seekBar.setProgress(progress[position]);
        if (listener != null) {
            listener.onProgressValue(position,progress[position]/30-1);
        }

    }

    public void setShowThumbDrawable(boolean showThumbDrawable) {
        this.showThumbDrawable = showThumbDrawable;
    }

    public void setListener(TouchUpListener listener){
        this.listener = listener;
    }

    public interface TouchUpListener{
        void onProgressValue(int ability_id, int val);

    }

}

