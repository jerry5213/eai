package com.zxtech.ecs.ui.bi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/3/23.
 */

public class Top10Activity extends BaseActivity implements OnChartValueSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.chart1)
    HorizontalBarChart mChart;

    String texts[] = {"上海", "北京", "辽宁", "吉林", "浙江", "河北", "山西", "内蒙古"};
    String colors[] = {"#aa2ecc71", "#aaf1c40f", "#aae74c3c", "#aa3498db", "#aaff7043", "#aa607d8b", "#aa5e3ab1", "#aae91e63"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bi_top;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar);
        mChart.setOnChartValueSelectedListener(this);
        // mChart.setHighlightEnabled(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(false);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        mChart.setDrawGridBackground(false);

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        //  xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);
        xl.setLabelCount(texts.length);
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return texts[(int) (value / 10)];
            }
        });

        YAxis yl = mChart.getAxisLeft();
        yl.setEnabled(false);
        // yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(false);
        //设置是否绘制网格线
        yl.setDrawGridLines(true);
        yl.setLabelCount(texts.length, false);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = mChart.getAxisRight();
        // yr.setTypeface(mTfLight);
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(true);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        setData(texts.length, 26000);
        mChart.setFitBars(true);
       //mChart.animateY(2500);
        mChart.animateXY(2500,2500);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
    }

    private void setData(int count, float range) {

        float barWidth = 9f;
        float spaceForBar = 10f;
        BarDataSet set1;
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        for (int i = 0; i < count; i++) {
            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
            float val = (float) (Math.random() * range);
            //
            BarEntry barEntry = new BarEntry(i * spaceForBar, val,
                    getResources().getDrawable(R.drawable.open));
            yVals1.add(barEntry);
            set1 = new BarDataSet(yVals1, texts[i]);
            set1.setColor(Color.parseColor(colors[i]));
            set1.setDrawIcons(false);

            dataSets.add(set1);
        }


        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            //  set1.setValues(yVals1);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {


            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
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
