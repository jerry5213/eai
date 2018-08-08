package com.zxtech.ecs.ui.bi;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Display;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

/**
 * Created by syp523 on 2018/3/23.
 */

public class SaleActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindViews({R.id.chart1, R.id.chart2, R.id.chart3, R.id.chart4})
    List<PieChart> mCharts;

    private String[] texts = new String[]{"销售收入", "销售利润", "费用", "应收款"};
    private String[] texts_en = new String[]{"Sales Revenue", "Sales Profit", "Cost", "Receivables"};
    private String[] prices = new String[]{"24,800.5万", "4,801.5万", "2,801.8万", "10,801.1万"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bi_sale;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.sales_statistics));
        for (int i = 0; i < mCharts.size(); i++) {
            PieChart mChart = mCharts.get(i);
            mChart.setBackgroundColor(Color.WHITE);

            //  moveOffScreen();

            mChart.setUsePercentValues(true);
            mChart.getDescription().setEnabled(false);

            mChart.setCenterText(prices[i]);

            mChart.setDrawHoleEnabled(true);
            mChart.setHoleColor(Color.WHITE);


            mChart.setTransparentCircleColor(Color.WHITE);
            mChart.setTransparentCircleAlpha(110);

            mChart.setHoleRadius(58f);
            mChart.setTransparentCircleRadius(61f);

            mChart.setDrawCenterText(true);

            mChart.setRotationEnabled(false);
            mChart.setHighlightPerTapEnabled(true);

            mChart.setMaxAngle(180f); // HALF CHART
            mChart.setRotationAngle(180f);
            mChart.setCenterTextOffset(0, -20);

            setData(mChart, i, 100);

            mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

            Legend l = mChart.getLegend();
            l.setEnabled(true);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);

            // entry label styling
            mChart.setEntryLabelColor(Color.WHITE);
            mChart.setEntryLabelTextSize(12f);
        }
    }

    private void setData(PieChart mChart, int count, float range) {

        ArrayList<PieEntry> values = new ArrayList<PieEntry>();

        values.add(new PieEntry((float) ((Math.random() * range) + range / 5), texts[count]));

        PieDataSet dataSet = new PieDataSet(values, texts_en[count]);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS[count]);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "";
            }
        });
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText(String str) {

        SpannableString s = new SpannableString(str);
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

}
