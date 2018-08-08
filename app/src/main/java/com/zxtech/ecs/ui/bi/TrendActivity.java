package com.zxtech.ecs.ui.bi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/3/23.
 */

public class TrendActivity extends BaseActivity implements OnChartValueSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.chart1)
    LineChart mChart;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bi_trend;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.sales_trends));

        mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        //设置一页最大显示个数为6，超出部分就滑动
        float ratio = (float) 12/(float) 6;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        mChart.zoom(2f,1f,0,0);
        mChart.setPinchZoom(true);

        // set an alternative background
        //背景色
        mChart.setBackgroundColor(Color.WHITE);

        // add data
        setData(12, 4500);

        mChart.animateX(2000);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        //示意配置
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.DKGRAY);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(11f);
        xAxis.setLabelCount(5);
        xAxis.setTextColor(Color.DKGRAY);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        final String lable[] = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月","11月","12月"};
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return lable[(int) (value)];
            }
        });


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.DKGRAY);
        //最大最小区间
        leftAxis.setAxisMaximum(5000f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(false);
        final String yTexts[] = {"0", "1000万元", "2000万元", "3000万元", "4000万元", "5000万元"};
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return yTexts[(int) (value/1000)];
            }
        });

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
    }


    private void setData(int count, float range) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult) + 300;
            yVals1.add(new Entry(i, val));
        }

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + 450;
            yVals2.add(new Entry(i, val));
//            if(i == 10) {
//                yVals2.add(new Entry(i, val + 50));
//            }
        }


        LineDataSet set1, set2;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "上年销售金额");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(Color.parseColor("#f1c40f"));
            set1.setCircleColor(Color.parseColor("#f1c40f"));
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            //是否填充
            set1.setDrawFilled(true);
            set1.setValueTextColor(Color.DKGRAY);
            //曲线 或直线
            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            set1.setFillAlpha(80);
            set1.setFillColor(Color.parseColor("#f1c40f"));
            //set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(yVals2, "销售金额");
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(Color.parseColor("#3498db"));
            set2.setCircleColor(Color.parseColor("#3498db"));
            set2.setLineWidth(1f);
            set2.setValueTextColor(Color.DKGRAY);
            set2.setCircleRadius(3f);
           // set2.setFillAlpha(80);
            set2.setDrawFilled(false);
            set2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            set2.setFillColor(Color.parseColor("#3498db"));
            set2.setDrawCircleHole(false);
           // set2.setHighLightColor(Color.rgb(244, 117, 117));

            //set2.setFillFormatter(new MyFillFormatter(900f));


            // create a data object with the datasets
            LineData data = new LineData(set1, set2);
            data.setValueTextColor(Color.DKGRAY);
            data.setValueTextSize(9f);

            // set data
            mChart.setData(data);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
