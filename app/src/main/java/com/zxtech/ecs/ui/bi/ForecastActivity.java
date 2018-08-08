package com.zxtech.ecs.ui.bi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/7/3.
 */

public class ForecastActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lineChart)
    LineChart lineChart;
    @BindView(R.id.barChart)
    BarChart barChart;
    @BindView(R.id.area_tv)
    TextView area_tv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_forecast;
    }

    private String[][] mTitles = new String[][]{{"Q1", "Q2", "Q3", "Q4"}, {"PO7", "PO8", "PO9", "PO10"}, {"W1", "W2", "W3", "W4"}};
    private int selectedMode = 0;
    private String selectedArea = "全国";

    private static final int SEASON = 0;
    private static final int MONTH = 1;
    private static final int WEEK = 2;

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.sales_projection));

        initLineData();
        initBarData();
        setup(lineChart);

        lineChart.setExtraBottomOffset(5f);
        lineChart.setExtraRightOffset(5f);
        barChart.setExtraBottomOffset(15f);

        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setLabelCount(3);
        lineChart.getXAxis().setGranularity(1f);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setLabelCount(3);
        barChart.getXAxis().setGranularity(1f);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setTouchEnabled(true);
        barChart.setDrawGridBackground(false);
        // enable scaling and dragging
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setTextSize(8f);
        leftAxis.setTextColor(Color.DKGRAY);
        leftAxis.setValueFormatter(new DefaultAxisValueFormatter(3));

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(8f);
        xAxis.setTextColor(Color.DKGRAY);

        barChart.getAxisRight().setEnabled(false);

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (selectedMode == SEASON) {
                    selectedMode = MONTH;
                    lineChart.getXAxis().setLabelCount(3);
                    barChart.getXAxis().setLabelCount(3);
                } else if (selectedMode == MONTH) {
                    selectedMode = WEEK;
                    lineChart.getXAxis().setLabelCount(4);
                    barChart.getXAxis().setLabelCount(4);
                } else {
                    lineChart.getXAxis().setLabelCount(4);
                    barChart.getXAxis().setLabelCount(4);
                }
                refreshView(selectedArea, selectedMode);
            }

            @Override
            public void onNothingSelected() {

            }
        });


        // add some transparency to the color with "& 0x90FFFFFF"

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mTitles[selectedMode][(int) value - 1];
            }
        };

        lineChart.getXAxis().setValueFormatter(formatter);
        barChart.getXAxis().setValueFormatter(formatter);

        refreshView(getString(R.string.nationwide), selectedMode);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        if (!TextUtils.isEmpty(title)) {
            area_tv.setText(title);
            selectedMode = SEASON;
            lineChart.getXAxis().setLabelCount(3);
            barChart.getXAxis().setLabelCount(3);
        }

        switch (item.getItemId()) {

            case R.id.whole:
                refreshView(title, selectedMode);
                break;
            case R.id.northeast:
                refreshView(title, selectedMode);
                break;
            case R.id.north:
                refreshView(title, selectedMode);
                break;
            case R.id.centre:
                refreshView(title, selectedMode);
                break;
            case R.id.east:
                refreshView(title, selectedMode);
                break;
            case R.id.southwest:
                refreshView(title, selectedMode);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshView(String title, int modeIndex) {
        ArrayList<Entry> entryArrayList = lineMap.get(title + modeIndex);
        setLineData(entryArrayList);
        ArrayList<BarEntry> barEntryArrayList = barMap.get(title + modeIndex);
        setBarData(barEntryArrayList);

    }

    private HashMap<String, ArrayList<Entry>> lineMap = new HashMap<>();
    private HashMap<String, ArrayList<BarEntry>> barMap = new HashMap<>();
    ArrayList<Entry> entryArrayList;
    ArrayList<BarEntry> barEntryArrayList;

    private void initBarData() {
        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 1500));
        barEntryArrayList.add(new BarEntry(2, 1700));
        barEntryArrayList.add(new BarEntry(3, 2000));
        barMap.put(getString(R.string.nationwide) + SEASON, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 200));
        barEntryArrayList.add(new BarEntry(2, 400));
        barEntryArrayList.add(new BarEntry(3, 700));
        barMap.put(getString(R.string.nationwide) + MONTH, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 800));
        barEntryArrayList.add(new BarEntry(2, 700));
        barEntryArrayList.add(new BarEntry(3, 800));
        barEntryArrayList.add(new BarEntry(4, 700));
        barMap.put(getString(R.string.nationwide) + WEEK, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 300));
        barEntryArrayList.add(new BarEntry(2, 400));
        barEntryArrayList.add(new BarEntry(3, 600));
        barMap.put(getString(R.string.northeast) + SEASON, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 5));
        barEntryArrayList.add(new BarEntry(2, 50));
        barEntryArrayList.add(new BarEntry(3, 180));
        barMap.put(getString(R.string.northeast) + MONTH, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 120));
        barEntryArrayList.add(new BarEntry(2, 60));
        barEntryArrayList.add(new BarEntry(3, 90));
        barEntryArrayList.add(new BarEntry(4, 160));
        barMap.put(getString(R.string.northeast) + WEEK, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 430));
        barEntryArrayList.add(new BarEntry(2, 800));
        barEntryArrayList.add(new BarEntry(3, 620));
        barMap.put(getString(R.string.north) + SEASON, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 50));
        barEntryArrayList.add(new BarEntry(2, 210));
        barEntryArrayList.add(new BarEntry(3, 200));
        barMap.put(getString(R.string.north) + MONTH, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 228));
        barEntryArrayList.add(new BarEntry(2, 220));
        barEntryArrayList.add(new BarEntry(3, 212));
        barEntryArrayList.add(new BarEntry(4, 200));
        barMap.put(getString(R.string.north) + WEEK, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 375));
        barEntryArrayList.add(new BarEntry(2, 324));
        barEntryArrayList.add(new BarEntry(3, 400));
        barMap.put(getString(R.string.central) + SEASON, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 66));
        barEntryArrayList.add(new BarEntry(2, 50));
        barEntryArrayList.add(new BarEntry(3, 189));
        barMap.put(getString(R.string.central) + MONTH, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 190));
        barEntryArrayList.add(new BarEntry(2, 185));
        barEntryArrayList.add(new BarEntry(3, 185));
        barEntryArrayList.add(new BarEntry(4, 170));
        barMap.put(getString(R.string.central) + WEEK, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 292));
        barEntryArrayList.add(new BarEntry(2, 292));
        barEntryArrayList.add(new BarEntry(3, 292));
        barMap.put(getString(R.string.east) + SEASON, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 88));
        barEntryArrayList.add(new BarEntry(2, 50));
        barEntryArrayList.add(new BarEntry(3, 130));
        barMap.put(getString(R.string.east) + MONTH, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 180));
        barEntryArrayList.add(new BarEntry(2, 202));
        barEntryArrayList.add(new BarEntry(3, 245));
        barEntryArrayList.add(new BarEntry(4, 140));
        barMap.put(getString(R.string.east) + WEEK, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 50));
        barEntryArrayList.add(new BarEntry(2, 102));
        barEntryArrayList.add(new BarEntry(3, 345));
        barMap.put(getString(R.string.southwest) + SEASON, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 50));
        barEntryArrayList.add(new BarEntry(2, 20));
        barEntryArrayList.add(new BarEntry(3, 116));
        barMap.put(getString(R.string.southwest) + MONTH, barEntryArrayList);

        barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 156));
        barEntryArrayList.add(new BarEntry(2, 148));
        barEntryArrayList.add(new BarEntry(3, 120));
        barEntryArrayList.add(new BarEntry(4, 95));
        barMap.put(getString(R.string.southwest) + WEEK, barEntryArrayList);
    }

    private void initLineData() {

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 66));
        entryArrayList.add(new Entry(2, 80));
        entryArrayList.add(new Entry(3, 63));
        lineMap.put(getString(R.string.nationwide) + SEASON, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 52));
        entryArrayList.add(new Entry(2, 58));
        entryArrayList.add(new Entry(3, 72));
        lineMap.put(getString(R.string.nationwide) + MONTH, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 85));
        entryArrayList.add(new Entry(2, 78));
        entryArrayList.add(new Entry(3, 80));
        entryArrayList.add(new Entry(4, 72));
        lineMap.put(getString(R.string.nationwide)+ WEEK, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 78));
        entryArrayList.add(new Entry(2, 71));
        entryArrayList.add(new Entry(3, 27));
        lineMap.put(getString(R.string.northeast) + SEASON, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 7));
        entryArrayList.add(new Entry(2, 12));
        entryArrayList.add(new Entry(3, 41));
        lineMap.put(getString(R.string.northeast) + MONTH, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 27));
        entryArrayList.add(new Entry(2, 11));
        entryArrayList.add(new Entry(3, 23));
        entryArrayList.add(new Entry(4, 41));
        lineMap.put(getString(R.string.northeast) + WEEK, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 63));
        entryArrayList.add(new Entry(2, 106));
        entryArrayList.add(new Entry(3, 80));
        lineMap.put(getString(R.string.north) + SEASON, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 34));
        entryArrayList.add(new Entry(2, 59));
        entryArrayList.add(new Entry(3, 112));
        lineMap.put(getString(R.string.north) + MONTH, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 155));
        entryArrayList.add(new Entry(2, 145));
        entryArrayList.add(new Entry(3, 129));
        entryArrayList.add(new Entry(4, 112));
        lineMap.put(getString(R.string.north) + WEEK, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 74));
        entryArrayList.add(new Entry(2, 55));
        entryArrayList.add(new Entry(3, 70));
        lineMap.put(getString(R.string.central) + SEASON, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 73));
        entryArrayList.add(new Entry(2, 45));
        entryArrayList.add(new Entry(3, 83));
        lineMap.put(getString(R.string.central) + MONTH, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 100));
        entryArrayList.add(new Entry(2, 93));
        entryArrayList.add(new Entry(3, 90));
        entryArrayList.add(new Entry(4, 83));
        lineMap.put(getString(R.string.central) + WEEK, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 64));
        entryArrayList.add(new Entry(2, 122));
        entryArrayList.add(new Entry(3, 104));
        lineMap.put(getString(R.string.east) + SEASON, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 338));
        entryArrayList.add(new Entry(2, 68));
        entryArrayList.add(new Entry(3, 88));
        lineMap.put(getString(R.string.east) + MONTH, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 99));
        entryArrayList.add(new Entry(2, 114));
        entryArrayList.add(new Entry(3, 138));
        entryArrayList.add(new Entry(4, 88));
        lineMap.put(getString(R.string.east) + WEEK, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 39));
        entryArrayList.add(new Entry(2, 33));
        entryArrayList.add(new Entry(3, 54));
        lineMap.put(getString(R.string.southwest) + SEASON, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 50));
        entryArrayList.add(new Entry(2, 48));
        entryArrayList.add(new Entry(3, 60));
        lineMap.put(getString(R.string.southwest) + MONTH, entryArrayList);

        entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(1, 99));
        entryArrayList.add(new Entry(2, 92));
        entryArrayList.add(new Entry(3, 70));
        entryArrayList.add(new Entry(4, 60));
        lineMap.put(getString(R.string.southwest) + WEEK, entryArrayList);

    }


    private void setLineData(ArrayList<Entry> values) {

        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");
            set1.setColor(Color.BLACK);
        }

        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setLabel("Result Scores");
        set1.setDrawCircleHole(false);
        set1.setColor(ColorTemplate.rgb("#aa00aaff"));
        set1.setCircleColor(ColorTemplate.rgb("#aa00aaff"));
        set1.setLineWidth(1.8f);
        set1.setCircleRadius(3.6f);
        LineData lineData = new LineData(set1);
        lineData.setValueTextSize(8f);
        lineData.setValueTextColor(Color.DKGRAY);
        lineData.setValueFormatter(new PercentFormatter(new DecimalFormat("###,###,##0")));

        // set data
        lineChart.setData(lineData);
        lineChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }

    private void setBarData(ArrayList<BarEntry> entries) {

        BarDataSet set;
        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set.setValues(entries);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(entries, "Sinus Function");
            set.setColor(ColorTemplate.rgb("#aa00aaff"));
        }
        // BAR-CHART

        BarData barData = new BarData(set);
        barData.setValueTextSize(8f);
        barData.setValueTextColor(Color.DKGRAY);
        barData.setValueFormatter(new DefaultValueFormatter(0));

        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }


    protected void setup(Chart<?> chart) {


        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        if (chart instanceof BarLineChartBase) {

            BarLineChartBase mChart = (BarLineChartBase) chart;

            mChart.setDrawGridBackground(false);

            // enable scaling and dragging
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);

            // if disabled, scaling can be done on x- and y-axis separately
            mChart.setPinchZoom(false);

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
            leftAxis.setTextSize(8f);
            leftAxis.setTextColor(Color.DKGRAY);
            leftAxis.setValueFormatter(new PercentFormatter(new DecimalFormat("###,###,##0")));

            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(8f);
            xAxis.setTextColor(Color.DKGRAY);

            mChart.getAxisRight().setEnabled(false);
        }
    }


}
