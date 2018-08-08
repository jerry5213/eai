package com.zxtech.ecs.ui.bi;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.PercentBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/7/3.
 */

public class SalesCapacityActivity extends BaseActivity {
    @BindView(R.id.left)
    PercentBar left;
    @BindView(R.id.right)
    PercentBar right;
    @BindView(R.id.last_tv)
    TextView last_tv;
    @BindView(R.id.next_tv)
    TextView next_tv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.area_tv)
    TextView area_tv;
    @BindView(R.id.up_down_iv)
    ImageView up_down_iv;

    private List<Data> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sales_capacity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.capacity_management));
        initData();
        refreshView(0);
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


    private void setView(Sale lastYear, Sale nextYear, boolean upOrDown) {
        left.setArrays(lastYear.sale);
        right.setArrays(nextYear.sale);
        left.invalidate();
        right.invalidate();

        last_tv.setText(getString(R.string.salesman$) + lastYear.salesmenNum + "\n" + getString(R.string.per_capita_units) + lastYear.perCapitaNum);
        next_tv.setText(getString(R.string.salesman$) + nextYear.salesmenNum + "\n" + getString(R.string.per_capita_units) + nextYear.perCapitaNum);
        if (upOrDown) {
            up_down_iv.setImageResource(R.drawable.go_up);
        }else{
            up_down_iv.setImageResource(R.drawable.go_down);
        }
    }

    private void initData() {
        Data data = new Data(getString(R.string.nationwide), new Sale(102, 60, new int[]{17, 23, 37, 23}), new Sale(82, 58, new int[]{12, 22, 37, 16, 13}), false);
        Data data1 = new Data(getString(R.string.northeast), new Sale(15, 59, new int[]{20, 20, 40, 20}), new Sale(13, 62, new int[]{15, 23, 46, 8, 8}), true);
        Data data2 = new Data(getString(R.string.north), new Sale(25, 86, new int[]{21, 14, 36, 29}), new Sale(26, 79, new int[]{17, 26, 22, 22, 13}), false);
        Data data3 = new Data(getString(R.string.central), new Sale(25, 51, new int[]{24, 8, 36, 32}), new Sale(16, 57, new int[]{6, 25, 38, 19, 13}), true);
        Data data4 = new Data(getString(R.string.east), new Sale(26, 45, new int[]{5, 43, 43, 10}), new Sale(17, 50, new int[]{18, 24, 35, 6, 18}), true);
        Data data5 = new Data(getString(R.string.southwest), new Sale(11, 59, new int[]{25, 38, 25, 13, 0}), new Sale(13, 26, new int[]{0, 8, 54, 23, 15}), false);


        list.add(data);
        list.add(data1);
        list.add(data2);
        list.add(data3);
        list.add(data4);
        list.add(data5);
    }


    private void refreshView(int dataIndex) {
        Data data = list.get(dataIndex);
        area_tv.setText(data.area);
        setView(data.lastYear, data.nextYear, data.isUpDown);
    }


    class Data {
        public String area;
        public Sale lastYear;
        public Sale nextYear;
        public boolean isUpDown;

        public Data(String area, Sale lastYear, Sale nextYear, boolean isUpDown) {
            this.area = area;
            this.lastYear = lastYear;
            this.nextYear = nextYear;
            this.isUpDown = isUpDown;
        }
    }

    public static class Sale {
        public int salesmenNum;
        public int perCapitaNum;
        private int[] sale = null;

        public Sale(int salesmenNum, int perCapitaNum, int[] sale) {
            this.salesmenNum = salesmenNum;
            this.perCapitaNum = perCapitaNum;
            this.sale = sale;
        }
    }


}
