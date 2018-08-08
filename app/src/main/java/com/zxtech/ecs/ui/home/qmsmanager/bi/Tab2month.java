package com.zxtech.ecs.ui.home.qmsmanager.bi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.zxtech.ecs.R;

import java.util.ArrayList;

public class Tab2month extends Fragment {
    public Spinner spinner;
    private String[] test= {"2018", "2017", "2016", "2015"};
    HorizontalBarChart Lt2Chart;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xAxis;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);


        //   spinner = (Spinner) getView().findViewById(R.id.spinner);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> mSortAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, test);
        mSortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mSortAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("2015")) {
                    setData(12, 50);
                   // Toast.makeText(getContext(),"yeaft", Toast.LENGTH_LONG).show();
                   // Lt2Chart.animateY(3000);
                }

                if(selectedItem.equals("2016")) {
                    setData(12, 50);
                 //   Toast.makeText(getContext(),"yeaft", Toast.LENGTH_LONG).show();
                    //Lt2Chart.animateY(3000);
                }

                if(selectedItem.equals("2017")) {
                    setData(12, 50);
                 //   Toast.makeText(getContext(),"yeaft", Toast.LENGTH_LONG).show();
                    //Lt2Chart.animateY(3000);
                }

                if(selectedItem.equals("2018")) {
                    setData(12, 50);
                 //   Toast.makeText(getContext(),"yeaft", Toast.LENGTH_LONG).show();
                    //Lt2Chart.animateY(3000);
                }



            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        Lt2Chart = (HorizontalBarChart) rootView.findViewById(R.id.LT2);
        leftAxis = Lt2Chart.getAxisLeft();
        rightAxis = Lt2Chart.getAxisRight();
        xAxis = Lt2Chart.getXAxis();




        return rootView;
    }








    private void setData(int count, int range)
    {
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

        //XAxis xAxis = Lt2Chart.getXAxis(); // 获取X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0); // 此轴显示的最小值；
        xAxis.setGranularityEnabled(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(8);
        xAxis.setLabelCount(xVals.size());
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);

      //  Lt2Chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));
       // Lt2Chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);



        ArrayList<BarEntry> yVals = new ArrayList<>();

        float barWidth = 3f;
        float spaceForBar = 5f;

        for (int i = 0; i<count; i ++)
        {
            float val = (float)(Math.random()*range);
            yVals.add(new BarEntry(i,val,"qadrui sunni"));

        }

        BarDataSet set1;
        set1 = new BarDataSet(yVals, "Data Set");

        BarData data = new BarData(set1);
        set1.setColors(ContextCompat.getColor(getContext(),R.color.main_aa));
        //data.setBarWidth(barWidth);

        Lt2Chart.getXAxis().setDrawGridLines(false);
        Lt2Chart.setDrawValueAboveBar(true);
        YAxis y ;
        


        Lt2Chart.setData(data);
        Lt2Chart.animateY(1000);
        Lt2Chart.invalidate();

    }
}
