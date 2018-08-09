package com.zxtech.is.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zxtech.is.util.DensityUtil;
import com.zxtech.is.util.ScreenUtils;

/**
 * Created by duomi on 2018/4/25.
 */

public class BoardView extends View {
    private Context context;
    private Paint mBitmapPaint;// 画布的画笔
    private Paint mPaint;// 真实的画笔


    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;

    private int screenWidth, screenHeight;
    private float mX, mY;// 临时点坐标
    private static final float TOUCH_TOLERANCE = 4;

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initCanvas();
    }

    public void initCanvas() {
        setPaintStyle();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        //画布大小
        screenWidth = ScreenUtils.getScreenWidth(context);
        screenHeight = ScreenUtils.getScreenHeight(context);
        screenWidth = screenWidth - DensityUtil.dip2px(context, 130);
        screenHeight = DensityUtil.dip2px(context, 200);
        mBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(Color.argb(0, 0, 0, 0));
        mCanvas = new Canvas(mBitmap);  //所有mCanvas画的东西都被保存在了mBitmap中
        mCanvas.drawColor(Color.TRANSPARENT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 每次down下去重新new一个Path
                mPath = new Path();
                //每一次记录的路径对象是不一样的
//                dp = new DrawPath();
//                dp.path = mPath;
//                dp.paint = mPaint;
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

    public void redo() {
        mBitmap.eraseColor(Color.argb(0, 0, 0, 0));
        invalidate();
    }

    public Bitmap getmBitmap() {
        return drawBg4Bitmap(Color.WHITE, mBitmap);
    }


    private Bitmap drawBg4Bitmap(int color, Bitmap orginBitmap) {

        Paint paint = new Paint();

        paint.setColor(color);

        Bitmap bitmap = Bitmap.createBitmap(orginBitmap.getWidth(),

                orginBitmap.getHeight(), orginBitmap.getConfig());

        Canvas canvas = new Canvas(bitmap);
        /**去锯齿*/
        paint.setAntiAlias(true);

        canvas.drawRect(0, 0, orginBitmap.getWidth(), orginBitmap.getHeight(), paint);
//        /**设置相交模式  */
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(orginBitmap, 0, 0, paint);

        return bitmap;
    }

    //初始化画笔样式
    private void setPaintStyle() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);// 设置外边缘
        mPaint.setStrokeCap(Paint.Cap.ROUND);// 形状
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLACK);

    }


    private void touch_start(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(mY - y);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            // 从x1,y1到x2,y2画一条贝塞尔曲线，更平滑(直接用mPath.lineTo也可以)
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            //mPath.lineTo(mX,mY);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        mCanvas.drawPath(mPath, mPaint);
        //将一条完整的路径保存下来
//        savePath.add(dp);
        mPath = null;// 重新置空
    }

    @Override
    public void onDraw(Canvas canvas) {
        //canvas.drawColor(0xFFAAAAAA);
        // 将前面已经画过得显示出来
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        if (mPath != null) {
            // 实时的显示
            canvas.drawPath(mPath, mPaint);
        }
    }
}
