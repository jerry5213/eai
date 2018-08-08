package com.zxtech.ecs.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zxtech.ecs.R;

/**
 * Created by syp523 on 2018/7/4.
 */

public class FunnelView extends View {

    private Paint paint;
    private Paint textPaint;
    private Path path;
    private int[] orderCount = new int[]{};
    private int[] projCount = new int[]{};
    private String projectStr = "";
    private String orderStr = "";

    public FunnelView(Context context) {
        this(context, null);
    }

    public FunnelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FunnelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        projectStr = context.getString(R.string.number_of_projects);
        orderStr = context.getString(R.string.number_of_orders);
    }

    private void init() {
        paint = new Paint();
        textPaint = new Paint();
        textPaint.setTextSize(40);
        textPaint.setColor(Color.parseColor("#737373"));
        textPaint.setStrokeWidth(1);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Point distancePoint = null;
        Point distanceTextPoint = null;
        int firstDiff = 40;
        int secondDiff = 50;
        int spac = 20;
        Point leftPoint = new Point(0, 0);
        Point rightPoint = new Point(this.getWidth(), 0);
        Point bottomPoint = new Point(this.getWidth() / 2, this.getHeight());
        float lDistance = leftPoint.getDistance(bottomPoint);
        float rDistance = rightPoint.getDistance(bottomPoint);

        paint.setColor(Color.parseColor("#aa00aaff"));
        path.moveTo(leftPoint.x, leftPoint.y);
        path.lineTo(rightPoint.x, rightPoint.y);
        distancePoint = getDistancePoint(rightPoint, bottomPoint, lDistance / (lDistance * getRatio(0)), false);

        path.lineTo(distancePoint.x, distancePoint.y);
        distancePoint = getDistancePoint(leftPoint, bottomPoint, lDistance / (lDistance * getRatio(0)), true);
        path.lineTo(distancePoint.x, distancePoint.y);
        path.close();


        canvas.drawPath(path, paint);
        distanceTextPoint = getDistancePoint(leftPoint, bottomPoint, lDistance / (lDistance * 0.01 + firstDiff), true);
        canvas.drawText(projectStr + projCount[0], distanceTextPoint.x, distanceTextPoint.y, textPaint);
        distanceTextPoint = getDistancePoint(leftPoint, bottomPoint, lDistance / (lDistance * 0.01 + firstDiff + secondDiff), true);
        canvas.drawText(orderStr + orderCount[0], distanceTextPoint.x, distanceTextPoint.y, textPaint);

        path.reset();
        if (orderCount[1] != 0) {
            paint.setColor(Color.parseColor("#aa96c814"));
            distancePoint = getDistancePoint(leftPoint, bottomPoint, lDistance / (lDistance * getRatio(0) + spac), true);
            path.moveTo(distancePoint.x, distancePoint.y);
            distancePoint = getDistancePoint(rightPoint, bottomPoint, lDistance / (lDistance * getRatio(0) + spac), false);
            path.lineTo(distancePoint.x, distancePoint.y);
            distancePoint = getDistancePoint(rightPoint, bottomPoint, lDistance / (lDistance * (getRatio(0) + getRatio(1))), false);
            path.lineTo(distancePoint.x, distancePoint.y);
            distancePoint = getDistancePoint(leftPoint, bottomPoint, lDistance / (lDistance * (getRatio(0) + getRatio(1))), true);
            path.lineTo(distancePoint.x, distancePoint.y);
            path.close();
            canvas.drawPath(path, paint);

            distanceTextPoint = getDistancePoint(leftPoint, bottomPoint, lDistance / (lDistance * getRatio(0) + spac + firstDiff), true);
            canvas.drawText(projectStr + projCount[1], distanceTextPoint.x, distanceTextPoint.y, textPaint);
            distanceTextPoint = getDistancePoint(leftPoint, bottomPoint, lDistance / (lDistance * getRatio(0) + spac + firstDiff + secondDiff), true);
            canvas.drawText(orderStr + orderCount[1], distanceTextPoint.x, distanceTextPoint.y, textPaint);

            path.reset();
        }

        paint.setColor(Color.parseColor("#aafeb300"));
        distancePoint = getDistancePoint(leftPoint, bottomPoint, lDistance / (lDistance * (getRatio(0) + getRatio(1)) + spac), true);
        path.moveTo(distancePoint.x, distancePoint.y);
        distancePoint = getDistancePoint(rightPoint, bottomPoint, lDistance / (lDistance * (getRatio(0) + getRatio(1)) + spac), false);
        path.lineTo(distancePoint.x, distancePoint.y);
        distancePoint = getDistancePoint(rightPoint, bottomPoint, lDistance / lDistance, false);
        path.lineTo(distancePoint.x, distancePoint.y);
        distancePoint = getDistancePoint(leftPoint, bottomPoint, lDistance / lDistance, true);
        path.lineTo(distancePoint.x, distancePoint.y);
        path.close();
        canvas.drawPath(path, paint);
        distanceTextPoint = getDistancePoint(leftPoint, bottomPoint, lDistance / (lDistance * (getRatio(0) + getRatio(1)) + spac + firstDiff), true);
        canvas.drawText(projectStr + projCount[2], distanceTextPoint.x, distanceTextPoint.y, textPaint);
        distanceTextPoint = getDistancePoint(leftPoint, bottomPoint, lDistance / (lDistance * (getRatio(0) + getRatio(1)) + spac + firstDiff + secondDiff), true);
        canvas.drawText(orderStr + orderCount[2], distanceTextPoint.x, distanceTextPoint.y, textPaint);
        path.reset();

    }

    private Point getDistancePoint(Point leftPoint, Point bottomPoint, double lenthUnit, boolean left) {
        // 第一步：求得直线方程相关参数y=kx+b
        double y = ((bottomPoint.y - leftPoint.y)) / lenthUnit + leftPoint.y;
        double x = 0;
        if (left) {
            x = (Math.abs(bottomPoint.x - leftPoint.x)) / lenthUnit + leftPoint.x;
        } else {
            x = leftPoint.x - (Math.abs(bottomPoint.x - leftPoint.x)) / lenthUnit;
        }

        return new Point((float) x, (float) y);
    }

    private float getRatio(int index) {
        float sum = 0;
        for (int i = 0; i < orderCount.length; i++) {
            sum += orderCount[i];
        }
        Log.d("chw", "比例===" + orderCount[index] / sum);
        return orderCount[index] / sum;
    }

    public void setOrderCount(int[] orderCount) {
        this.orderCount = orderCount;
    }

    public void setProjCount(int[] projCount) {
        this.projCount = projCount;
    }

    public void setNewData(int[] orderCount, int[] projCount) {
        this.orderCount = orderCount;
        this.projCount = projCount;
        this.invalidate();
    }

    class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getDistance(Point p) {
            double _x = Math.abs(this.x - p.x);
            double _y = Math.abs(this.y - p.y);
            return (float) Math.sqrt(_x * _x + _y * _y);
        }
    }
}
