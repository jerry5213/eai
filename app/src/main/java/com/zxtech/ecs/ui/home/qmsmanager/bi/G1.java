package com.zxtech.ecs.ui.home.qmsmanager.bi;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;

import java.util.ArrayList;

import butterknife.BindView;


public class G1 extends BaseActivity {

    String texts[] = {"jan", "feb", "mar", "per", "may", "jun", "jul"};
    String colors[] = {"#aa2ecc71", "#aaf1c40f", "#aae74c3c", "#aa3498db", "#aaff7043", "#aa607d8b", "#aa5e3ab1", "#aae91e63"};

    private String[] test= {"2018", "2017", "2016", "2015"};

    HorizontalBarChart mChart;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_g1;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar);

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> mSortAdapter = new ArrayAdapter<CharSequence>(this , android.R.layout.simple_spinner_item, test);
        mSortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mSortAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("2015")) {
                    setData(12, 50);
                    //Toast.makeText(this,"yeaft", Toast.LENGTH_LONG).show();
                    mChart.animateY(3000);
                }

                if(selectedItem.equals("2016")) {
                    setData(12, 50);
                    //   Toast.makeText(getContext(),"yeaft", Toast.LENGTH_LONG).show();
                    mChart.animateY(3000);
                }

                if(selectedItem.equals("2017")) {
                    setData(12, 50);
                    //   Toast.makeText(getContext(),"yeaft", Toast.LENGTH_LONG).show();
                    mChart.animateY(3000);
                }

                if(selectedItem.equals("2018")) {
                    setData(12, 50);
                    //   Toast.makeText(getContext(),"yeaft", Toast.LENGTH_LONG).show();
                    mChart.animateY(3000);
                }



            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });



        TextView title = (TextView) findViewById(R.id.g1title);
        TextView g1_par1 = (TextView) findViewById(R.id.graph1_par1) ;
        TextView g1_par1_val = (TextView) findViewById(R.id.graph1_par1_val) ;
        TextView g1_par2 = (TextView) findViewById(R.id.graph1_par2) ;
        TextView g1_par2_val = (TextView) findViewById(R.id.graph1_par2_val) ;

        toolbar_title.setText(getIntent().getStringExtra("head"));
        // Toast.makeText(this, getIntent().getStringExtra("head"), Toast.LENGTH_LONG).show();
        title.setText(getIntent().getStringExtra("head"));
        g1_par1.setText(getIntent().getStringExtra("a1"));
        g1_par1_val.setText((getIntent().getStringExtra("a1val")));
        g1_par2.setText(getIntent().getStringExtra("a2"));
        g1_par2_val.setText((getIntent().getStringExtra("a1val")));

        mChart = (HorizontalBarChart) findViewById(R.id.hbr1);
        setData(8, 50);
    }

    private void setData(int count, int range)
    {
        ArrayList<BarEntry> yVals = new ArrayList<>();
        float barWidth = 9.5f;
        float spaceForBar = 10f;

        for (int i = 0; i<count; i ++)
        {
            float val = (float)(Math.random()*range);
            yVals.add(new BarEntry( i, val, "f"));
        }

        final ArrayList<String> xVals = new ArrayList<>();
        xVals.add("January");
        xVals.add("February");
        xVals.add("March");
        xVals.add("April");
        xVals.add("May");
        xVals.add("June");
        xVals.add("July");
        xVals.add("August");
        xVals.add("September");
        xVals.add("October");
        xVals.add("November");
        xVals.add("December");

        //
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setGranularityEnabled(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(8);
        xAxis.setLabelCount(xVals.size());
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        BarDataSet set1;
        set1 = new BarDataSet(yVals,"months");

        BarData data = new BarData(set1);

        if (getIntent().getStringExtra("head").equals("COPQ报表")){
            set1.setColors(ContextCompat.getColor(this, R.color.yellow));
        }
        if (getIntent().getStringExtra("head").equals("CEPPM报表")){
            set1.setColors(ContextCompat.getColor(this, R.color.green));
        }

        if (getIntent().getStringExtra("head").equals("TOPFTB报表")){
            set1.setColors(ContextCompat.getColor(this, R.color.grass_green));
        }



        //data.setBarWidth(barWidth);


        mChart.getXAxis().setDrawGridLines(false);

        mChart.getXAxis().setDrawLabels(true);
        mChart.setPinchZoom(false);
        mChart.setDoubleTapToZoomEnabled(false);
     //   mChart.getRendererLeftYAxis();




        mChart.setData(data);
        mChart.animateY(1000);
    }
}
