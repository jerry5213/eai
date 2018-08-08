package com.zxtech.ecs.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.zxtech.ecs.R;
import com.zxtech.ecs.util.DensityUtil;


public class SpiderWeb extends RelativeLayout  {


    protected boolean init_flag, data_flag;
    protected float padding;
    protected float x, y, r;
    protected int num, max;
    protected int[] progress;

    private int real_max;
    private int[] real_progress;
    private String[] text;

    private Paint paint;
    private Paint paint_s;
    private Paint paint_text;
    private Path path;
    private Paint paint_bg;

    private int mBackgroundColorResId = 0;
    protected Context mContext;

    public SpiderWeb(Context context) {
        this(context, null);
    }

    public SpiderWeb(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpiderWeb(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mBackgroundColorResId = getResources().getColor(R.color.main_aa);
        mContext = context;
        setup(context, attrs);
    }

    protected void setup(Context context, AttributeSet attrs) {
        setWillNotDraw(false);

        padding = 45;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#909090"));


        paint_s = new Paint();
        paint_s.setAntiAlias(true);
        paint_s.setStyle(Paint.Style.STROKE);
        paint_s.setColor(mBackgroundColorResId);
        paint_s.setStrokeWidth(5);

        paint_bg = new Paint();
        paint_bg.setAntiAlias(true);
        paint_bg.setStyle(Paint.Style.STROKE);
        paint_bg.setColor(Color.WHITE);

        paint_text = new Paint();
        paint_text.setAntiAlias(true);
        paint_text.setColor(Color.parseColor("#282828"));
        paint_text.setTextSize(28);

        path = new Path();

        max = 150;
    }

    public void setAreaLineColor(int lineColor) {
        mBackgroundColorResId = lineColor;
    }

    public boolean setData(int real_max, int[] real_progress, String[] text) {
        if (real_progress != null && real_progress.length >= 3 && real_progress.length <= 10 && real_max >= 1) {
            this.num = real_progress.length;
            this.real_max = real_max;
            this.progress = new int[real_progress.length];
            this.real_progress = new int[real_progress.length];
            for (int i = 0; i < real_progress.length; i++) {
                this.real_progress[i] = fitRealProgress(real_progress[i], real_max);
                this.progress[i] = calcProgress(this.real_progress[i]);
            }
            this.text = new String[real_progress.length];
            if (text != null && text.length == real_progress.length) {
                for (int i = 0; i < real_progress.length; i++) {
                    this.text[i] = text[i] == null ? "" : text[i];
                }
            }
            data_flag = true;
        } else {
            Toast.makeText(getContext(), "spiderweb data error", Toast.LENGTH_SHORT).show();
            data_flag = false;
        }
        return data_flag;
    }

    @Override
    protected void onLayout(boolean c, int l, int t, int r, int b) {
        super.onLayout(c, l, t, r, b);
        float w = r - l;
        float h = b - t;
        this.r = (Math.min(w, h) - padding * 2f) / 2f;
        this.x = w / 2;
        this.y = h / 2 + padding / 2f;
        init_flag = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawSuper(canvas);
    }

    protected void onDrawSuper(Canvas canvas) {
        if (init_flag && data_flag) {
            drawBackground(canvas);
            drawLngLine(canvas);
            drawText(canvas);
            //drawTitle(canvas);
            drawLatLine(canvas);
            drawArea(canvas);


        }
    }

    private void drawBackground(Canvas canvas) {

        path.reset();
        for (int j = 0; j < num; j++) {
            Point point = calcPoint(j, max);
            if (j == 0) {
                path.moveTo(point.x, point.y);
            } else {
                path.lineTo(point.x, point.y);
            }
        }
        path.close();
        canvas.drawPath(path, paint_bg);

    }

    protected void drawLngLine(Canvas canvas) {
        for (int i = 0; i < num; i++) {
            Point point = calcPoint(i, max);
            canvas.drawLine(x, y, point.x, point.y, paint);
        }
    }

    protected void drawText(Canvas canvas) {
        for (int i = 0; i < num; i++) {
            int t_h = getFontHeight(paint_text, text[i]);
            int t_w = getFontWidth(paint_text, text[i]);

            double length = Math.sqrt(Math.pow(t_w / 2f, 2) + Math.pow(t_h / 2f, 2));
            //  Point point = calcPoint(i, (float) (length + r));
            Point point = calcPoint(i, max);
            switch (i) {
                case 0:
                    canvas.drawText(text[i], point.x, point.y, paint_text);
                    //  canvas.drawText("80%", point.x  , point.y+t_h, paint_text);
                    break;
                case 1:
                    canvas.drawText(text[i], point.x, point.y, paint_text);
                    break;
                case 2:
                    canvas.drawText(text[i], point.x, point.y + t_h, paint_text);
                    break;
                case 3:
                    canvas.drawText(text[i], point.x, point.y + t_h, paint_text);
                    break;
                case 4:
                    canvas.drawText(text[i], point.x - t_w, point.y, paint_text);
                    break;
            }
            //canvas.drawText(text[i], point.x , point.y , paint_text);
        }
    }


    protected void drawLatLine(Canvas canvas) {
        for (int i = 1; i <= real_max; i++) {
            path.reset();
            for (int j = 0; j < num; j++) {
                Point point = calcPoint(j, max * i / real_max);
                if (j == 0) {
                    path.moveTo(point.x, point.y);
                } else {
                    path.lineTo(point.x, point.y);
                }
            }

            path.close();
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, paint);
        }
    }

    protected void drawArea(Canvas canvas) {

        path.reset();

        for (int i = 0; i < num; i++) {
            Point point = calcPoint(i, progress[i]);
            if (i == 0) {
                path.moveTo(point.x, point.y);
            } else {
                path.lineTo(point.x, point.y);
            }
        }
        path.close();
        paint_s.setColor(mBackgroundColorResId);
        paint_s.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint_s);

        path.reset();
        //绘制连接点圆点
        for (int i = 0; i < num; i++) {
            Point point = calcPoint(i, progress[i]);
            path.addCircle(point.x, point.y, 10, Path.Direction.CW);
        }
        path.close();
        paint_s.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint_s);
    }

    public float calcDegree(int position) {
        float degree = 0;
        if (position >= 0 && position < num) {
            degree = 360f / num * position;
        }
        return degree;
    }

    public Point calcPoint(int position, int progress) {
        if (position >= 0 && position < num && progress >= 0 && progress <= max) {
            float l = progress * r / max;
            return calcPoint(position, l);
        }
        return new Point(0, 0);
    }

    public Point calcPoint(int position, float length) {
        double d = calcDegree(position) / 180f * Math.PI;
        int x = (int) (this.x + length * Math.sin(d));
        int y = (int) (this.y - length * Math.cos(d));
        return new Point(x, y);
    }

    protected int calcProgress(int real_progress) {
        return (int) (1f * max * real_progress / real_max);
    }

    protected void updateProgress(int position, int progress) {
        this.progress[position] = progress;
        invalidate();
    }

    protected void updateProgressByReal(int position, int progress) {
        this.real_progress[position] = fitRealProgress(Math.round(1f * progress / max * real_max), real_max);
        this.progress[position] = calcProgress(this.real_progress[position]);
        invalidate();
    }

    private static int fitRealProgress(int real_progress, int real_max) {
        return Math.min(Math.max(1, real_progress), real_max);
    }

    private static int getFontWidth(Paint paint, String str) {
        if (str == null || str.equals(""))
            return 0;
        Rect rect = new Rect();
        int length = str.length();
        paint.getTextBounds(str, 0, length, rect);
        return rect.width();
    }

    private static int getFontHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, 1, rect);
        return rect.height();
    }

    //get


    public int getReal_max() {
        return real_max;
    }

    public int[] getReal_progress() {
        return real_progress;
    }

    public Paint getPaint() {
        return paint;
    }

    public Paint getPaint_s() {
        return paint_s;
    }

    public Path getPath() {
        return path;
    }

}
