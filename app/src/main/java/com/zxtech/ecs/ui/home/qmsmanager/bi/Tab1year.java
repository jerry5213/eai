package com.zxtech.ecs.ui.home.qmsmanager.bi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.zxtech.ecs.R;

import java.util.ArrayList;

public class Tab1year extends Fragment {

    HorizontalBarChart LtChart;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xAxis;
    Intent intent;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);


        LtChart = (HorizontalBarChart) rootView.findViewById(R.id.LT2);
        leftAxis = LtChart.getAxisLeft();
        rightAxis = LtChart.getAxisRight();
        xAxis = LtChart.getXAxis();

        setData(4, 50);

        return rootView;
    }

    private void setData(int count, int range) {

        final ArrayList<String> xVals = new ArrayList<>();
        xVals.add("2018");
        xVals.add("2017");
        xVals.add("2016");
        xVals.add("2015");

        ArrayList<BarEntry> yVals = new ArrayList<>();
        float barWidth = 3f;
        float spaceForBar = 5f;

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            yVals.add(new BarEntry(i, val));

        }

        BarDataSet set1;
        set1 = new BarDataSet(yVals, "Data Set");

        BarData data = new BarData(set1);


        set1.setColors(ContextCompat.getColor(getContext(), R.color.main_aa));


        LtChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //  data.setBarWidth(barWidth);
        LtChart.getXAxis().setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setGranularityEnabled(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);

        LtChart.setData(data);
        LtChart.animateY(3000);
    }
}

