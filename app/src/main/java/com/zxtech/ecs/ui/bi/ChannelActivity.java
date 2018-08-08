package com.zxtech.ecs.ui.bi;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.FunnelView;
import com.zxtech.ecs.widget.PercentBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/7/3.
 */

public class ChannelActivity extends BaseActivity {


    private List<Data> list = new ArrayList<>();

    @BindViews({R.id.chart1, R.id.chart2, R.id.chart3})
    PieChart mCharts[];
    @BindView(R.id.funnel)
    FunnelView funnel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.area_tv)
    TextView area_tv;


    private String[] mPartTitle = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_channel;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.sales_channel_management));
        initData();
        refreshView(0);

        for (int i = 0; i < mCharts.length; i++) {
            PieChart mChart = mCharts[i];
            mChart.setUsePercentValues(true);
            mChart.getDescription().setEnabled(false);
            mChart.setExtraOffsets(5, 0, 5, 0);

            mChart.setDragDecelerationFrictionCoef(0.95f);

            //mChart.setCenterTextTypeface(mTfLight);
            mChart.setCenterText(mPartTitle[i]);

            mChart.setDrawHoleEnabled(true);
            mChart.setHoleColor(Color.WHITE);

            mChart.setTransparentCircleColor(Color.WHITE);
            mChart.setTransparentCircleAlpha(110);

            mChart.setHoleRadius(58f);
            mChart.setTransparentCircleRadius(61f);

            mChart.setDrawCenterText(true);

            mChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            mChart.setRotationEnabled(true);
            mChart.setHighlightPerTapEnabled(true);

            // mChart.setUnit(" €");
            // mChart.setDrawUnitsInChart(true);

            // add a selection listener
            // mChart.setOnChartValueSelectedListener(this);

            setData(i, mChart, 0);

            mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
            // mChart.spin(2000, 0, 360);


            Legend l = mChart.getLegend();
            l.setEnabled(false);
//            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//            l.setOrientation(Legend.LegendOrientation.VERTICAL);
//            l.setDrawInside(false);
//            l.setXEntrySpace(7f);
//            l.setYEntrySpace(0f);
//            l.setYOffset(0f);

            // entry label styling
            mChart.setEntryLabelColor(Color.WHITE);
            // mChart.setEntryLabelTypeface(mTfRegular);
            mChart.setEntryLabelTextSize(12f);
        }
    }

    private void refreshView(int dataIndex) {
        Data data = list.get(dataIndex);
        funnel.setNewData(data.orderCount, data.projCount);
        setData(0, mCharts[0], dataIndex);
        setData(1, mCharts[1], dataIndex);
        setData(2, mCharts[2], dataIndex);
        int sum = 0;
        for (int i = 0; i < data.orderCount.length; i++) {
            sum += data.orderCount[i];
        }
        area_tv.setText(data.area + " " + getString(R.string.total$) + sum + getString(R.string.units));
    }


    private void setData(int index, PieChart mChart, int areaIndex) {


        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        Data data1 = list.get(areaIndex);
        if (index == 0) {
            Company[] companys = data1.companys;
            for (int i = 0; i < companys.length; i++) {
                entries.add(new PieEntry(companys[i].number,
                        companys[i].name
                ));
            }
        } else if (index == 1) {
            float[] target = data1.target;
            for (int i = 0; i < target.length; i++) {
                entries.add(new PieEntry(target[i],
                        (i == 0 ? getString(R.string.target) : getString(R.string.nontarget))
                ));
            }
        } else {

            float[] ad = data1.AD;
            String label = "";
            for (int i = 0; i < ad.length; i++) {
                if (i == 0) {
                    label = getString(R.string.other);
                } else if (i == 1) {
                    label = "B";
                } else {
                    label = "A";
                }
                entries.add(new PieEntry(ad[i],
                        label
                ));
            }

        }


        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.whole:
                refreshView(0);
                break;
            case R.id.northeast:
                refreshView(1);
                break;
            case R.id.north:
                refreshView(2);
                break;
            case R.id.centre:
                refreshView(3);
                break;
            case R.id.east:
                refreshView(4);
                break;
            case R.id.southwest:
                refreshView(5);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initData() {
        mPartTitle = new String[]{getString(R.string.branch_office), getString(R.string.regional), "A&D"};
        Data data = new Data(getString(R.string.nationwide), new float[]{36, 64}, new float[]{61, 22, 17}, new int[]{13142, 1123, 17082}, new int[]{549, 122, 985}, new Company[]{new Company("东北", 38), new Company("华北", 12), new Company("华中", 14), new Company("华东", 17), new Company("华南", 19)});
        Data data1 = new Data(getString(R.string.northeast), new float[]{57, 43}, new float[]{49, 29, 22}, new int[]{8463, 0, 3662}, new int[]{305, 0, 254}, new Company[]{new Company("长春", 23), new Company("沈阳", 25), new Company("哈尔滨", 37), new Company("大连", 15)});
        Data data2 = new Data(getString(R.string.north), new float[]{73, 27}, new float[]{48, 17, 35}, new int[]{101, 0, 3638}, new int[]{3, 0, 184}, new Company[]{new Company("西安", 15), new Company("太原", 18), new Company("青岛", 25), new Company("兰州", 4), new Company("京津翼", 11), new Company("济南", 28)});
        Data data3 = new Data(getString(R.string.central), new float[]{68, 32}, new float[]{78, 15, 7}, new int[]{501, 0, 3930}, new int[]{6, 0, 193}, new Company[]{new Company("郑州", 35), new Company("武汉", 26), new Company("南昌", 10), new Company("合肥", 29)});
        Data data4 = new Data(getString(R.string.east), new float[]{56, 44}, new float[]{58, 25, 17}, new int[]{1267, 1123, 2795}, new int[]{45, 42, 184}, new Company[]{new Company("苏州", 20), new Company("南京", 30), new Company("杭州", 10), new Company("广东", 16), new Company("福建", 24)});
        Data data5 = new Data(getString(R.string.southwest), new float[]{66, 34}, new float[]{74, 22, 4}, new int[]{3382, 0, 2433}, new int[]{197, 0, 163}, new Company[]{new Company("重庆", 15), new Company("昆明", 37), new Company("贵阳", 32), new Company("成都", 16)});


        list.add(data);
        list.add(data1);
        list.add(data2);
        list.add(data3);
        list.add(data4);
        list.add(data5);
    }


    class Data {
        public String area;
        public float[] target;
        public float[] AD;
        public int[] projCount;
        public int[] orderCount;

        public Company[] companys;

        public Data(String area, float[] target, float[] AD, Company[] companys) {
            this.area = area;
            this.target = target;
            this.AD = AD;
            this.companys = companys;
        }

        public Data(String area, float[] target, float[] AD, int[] projCount, int[] orderCount, Company[] companys) {
            this.area = area;
            this.target = target;
            this.AD = AD;
            this.projCount = projCount;
            this.orderCount = orderCount;
            this.companys = companys;
        }
    }

    public static class Company {
        public String name;
        public float number;

        public Company(String name, float number) {
            this.name = name;
            this.number = number;
        }
    }


}
