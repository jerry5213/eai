package com.zxtech.ecs.ui.bi;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haibin.calendarview.BaseView;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.QuotedListAdapter;
import com.zxtech.ecs.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/7/5.
 */

public class SalesActivitiesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.whole_recyclerview)
    RecyclerView whole_recyclerview;
    @BindView(R.id.area_tv)
    TextView area_tv;
    @BindView(R.id.area_layout)
    LinearLayout area_layout;
    @BindView(R.id.whole_layout)
    LinearLayout whole_layout;

    private MyAdapter mAdapter;

    private List<Data> mDatas = new ArrayList<>();
    private Drawable goUp = null;
    private Drawable goDown = null;
    private Drawable equal = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sales_activities;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.promotion_event));

        goUp = mContext.getResources().getDrawable(R.drawable.go_up);
        goDown = mContext.getResources().getDrawable(R.drawable.go_down);
        equal = mContext.getResources().getDrawable(R.drawable.equal);
        goUp.setBounds(0, 0, goUp.getMinimumWidth(), goUp.getMinimumHeight());
        goDown.setBounds(0, 0, goDown.getMinimumWidth(), goDown.getMinimumHeight());
        equal.setBounds(0, 0, equal.getMinimumWidth(), equal.getMinimumHeight());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(linearLayoutManager);

        mAdapter = new MyAdapter(R.layout.item_bi_sales_activities, mDatas);
        recycler_view.setAdapter(mAdapter);

        whole_recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        initWholeData();

        whole_layout.setVisibility(View.VISIBLE);
        area_layout.setVisibility(View.GONE);
    }

    private void initWholeData() {
        List<Whole> datas = new ArrayList<>();
        datas.add(new Whole(28796, 18089, 0.63, 884, 0.049d, 0.031d, 15, getString(R.string.northeast), 19338, 14870, 0.77, 808, 0.054, 0.042, 13, -0.09, "哈尔滨、长春、大连"));
        datas.add(new Whole(108638, 31223, 0.29, 2153, 0.069d, 0.02d, 25, getString(R.string.north), 78672, 22923, 0.29, 1827, 0.08, 0.023, 23, -0.15, "太原、兰州、西安、青岛"));
        datas.add(new Whole(64456, 9196, 0.14, 647, 0.07d, 0.01d, 11, getString(R.string.central), 47219, 5598, 0.12, 334, 0.06, 0.007, 13, -0.48, "成都、重庆、昆明"));
        datas.add(new Whole(147893, 29146, 0.2, 1160, 0.04d, 0.008d, 26, getString(R.string.east), 103835, 19919, 0.19, 843, 0.042, 0.008, 17, -0.27, "苏州、福建、广东"));
        datas.add(new Whole(76309, 18484, 0.24, 1275, 0.069d, 0.017d, 25, getString(R.string.southwest), 56927, 15929, 0.28, 916, 0.058, 0.016, 16, -0.28, "武汉、南昌"));
        datas.add(new Whole(426092, 106138, 0.25, 6119, 0.058d, 0.014d, 102, getString(R.string.nationwide), 305992, 79239, 0.26, 4728, 0.06, 0.015, 82, -0.23, ""));
        WholeAdapter wholeAdapter = new WholeAdapter(R.layout.item_bi_sales_activities_whole, datas);
        whole_recyclerview.setAdapter(wholeAdapter);
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
                whole_layout.setVisibility(View.VISIBLE);
                area_layout.setVisibility(View.GONE);
                break;
            case R.id.northeast:
                swicthData(1);
                break;
            case R.id.north:
                swicthData(2);
                break;
            case R.id.centre:
                swicthData(3);
                break;
            case R.id.east:
                swicthData(4);
                break;
            case R.id.southwest:
                swicthData(5);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void swicthData(int dataIndex) {
        whole_layout.setVisibility(View.GONE);
        area_layout.setVisibility(View.VISIBLE);
        mDatas.clear();
        switch (dataIndex) {

            case 1:
                mDatas.add(new Data(4179, "63%", "5.9%", 4, "哈尔滨", 4268, "104%", "4.5%", 4, -0.22));
                mDatas.add(new Data(6118, "72%", "7.0%", 4, "长春", 3723, "57%", "8.0%", 3, -0.31));
                mDatas.add(new Data(4790, "56%", "3.0%", 5, "沈阳", 4391, "80%", "7.1%", 4, 1.12));
                mDatas.add(new Data(3002, "59%", "2.1%", 2, "大连", 2488, "78%", "0.4%", 2, -0.84));
                area_tv.setText(getString(R.string.northeast));
                area_tv.setBackgroundColor(Color.parseColor("#00B050"));
                break;
            case 2:
                mDatas.add(new Data(16160, "41%", "2.3%", 7, "京津翼", 9016, "33%", "4.2%", 6, 0.03));
                mDatas.add(new Data(4646, "29%", "17.2%", 4, "青岛", 4546, "34%", "14.7%", 3, -0.17));
                mDatas.add(new Data(3516, "26%", "10.1%", 3, "济南", 4824, "44%", "8.1%", 4, 0.1));
                mDatas.add(new Data(3188, "25%", "6.2%", 4, "太原", 2164, "26%", "4.4%", 3, -0.51));
                mDatas.add(new Data(2642, "14%", "10.4%", 5, "西安", 1763, "13%", "12.6%", 4, -0.2));
                mDatas.add(new Data(1071, "13%", "14.8%", 2, "兰州", 610, "11%", "11.8%", 3, -0.54));
                area_tv.setText(getString(R.string.north));
                area_tv.setBackgroundColor(Color.parseColor("#92D050"));
                break;

            case 3:
                mDatas.add(new Data(6021, "38%", "7.8%", 6, "合肥", 5943, "44%", "7.8%", 4, -0.1));
                mDatas.add(new Data(4100, "14%", "12.9%", 8, "武汉", 4234, "19%", "4.6%", 5, -0.63));
                mDatas.add(new Data(5359, "48%", "2.7%", 4, "南昌", 2189, "28%", "4.2%", 2, -0.37));
                mDatas.add(new Data(3004, "15%", "4.3%", 7, "郑州", 3563, "26%", "4.6%", 5, 0.28));
                area_tv.setText(getString(R.string.central));
                area_tv.setBackgroundColor(Color.parseColor("#FFFF00"));
                break;
            case 4:
                mDatas.add(new Data(6680, "31%", "4.7%", 5, "南京", 7113, "47%", "1.2%", 4, -0.05));
                mDatas.add(new Data(6374, "17%", "6.7%", 8, "苏州", 2919, "11%", "8.7%", 5, -0.41));
                mDatas.add(new Data(1937, "6%", "5.8%", 3, "杭州", 3795, "17%", "2.9%", 1, -0.03));
                mDatas.add(new Data(9563, "60%", "2.2%", 5, "福建", 4718, "43%", "2.5%", 4, -0.44));
                mDatas.add(new Data(4592, "11%", "2.0%", 5, "广东", 1374, "5%", "4.7%", 3, -0.31));
                area_tv.setText(getString(R.string.east));
                area_tv.setBackgroundColor(Color.parseColor("#FFC000"));
                break;
            case 5:
                mDatas.add(new Data(1941, "9%", "8.2%", 1, "成都", 1181, "7%", "3.3%", 2, -0.76));
                mDatas.add(new Data(862, "7%", "8.9%", 2, "重庆", 744, "9%", "3.6%", 2, -0.65));
                mDatas.add(new Data(4244, "50%", "6.5%", 4, "昆明", 2350, "34%", "3.6%", 4, -0.69));
                mDatas.add(new Data(2149, "10%", "6.3%", 4, "贵阳/南宁", 1323, "13.9%", "13.9%", 5, 0.35));
                area_tv.setText(getString(R.string.southwest));
                area_tv.setBackgroundColor(Color.parseColor("#FF0000"));
                break;
        }
        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.north, R.id.northeast, R.id.central, R.id.east, R.id.southwest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.northeast:
                swicthData(1);
                break;
            case R.id.north:
                swicthData(2);
                break;
            case R.id.central:
                swicthData(3);
                break;
            case R.id.east:
                swicthData(4);
                break;
            case R.id.southwest:
                swicthData(5);
                break;
        }
    }

    class WholeAdapter extends BaseQuickAdapter<Whole, BaseViewHolder> {

        public WholeAdapter(int layoutResId, @Nullable List<Whole> data) {
            super(layoutResId, data);

        }

        @Override
        protected void convert(BaseViewHolder helper, Whole item) {
            helper.setText(R.id.last_ms_tv, Util.numberFormat(item.getLastMS()));
            helper.setText(R.id.last_pro_tv, Util.numberFormat(item.getLastPro()));
            helper.setText(R.id.last_cov_tv, (int) (item.getLastCov() * 100) + "%");
            helper.setText(R.id.last_booking_tv, Util.numberFormat(item.getLastBooking()));
            helper.setText(R.id.last_hit_tv, new BigDecimal(item.getLastHit() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "%");
            helper.setText(R.id.last_sos_tv, new BigDecimal(item.getLastSOS() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "%");
            helper.setText(R.id.last_hc_tv, Util.numberFormat(item.getLastHC()));
            helper.setText(R.id.area_tv, item.getArea());
            helper.setText(R.id.next_ms_tv, Util.numberFormat(item.getNextMS()));
            helper.setText(R.id.next_pro_tv, Util.numberFormat(item.getNextPro()));
            TextView next_cov_tv = helper.getView(R.id.next_cov_tv);
            if (item.getNextCov() > item.getLastCov()) {
                next_cov_tv.setCompoundDrawables(null, null, goUp, null);
            } else if (item.getNextCov() == item.getLastCov()) {
                next_cov_tv.setCompoundDrawables(null, null, equal, null);
            } else {
                next_cov_tv.setCompoundDrawables(null, null, goDown, null);
            }
            helper.setText(R.id.next_cov_tv, (int) (item.getNextCov() * 100) + "%");
            helper.setText(R.id.next_booking_tv, Util.numberFormat(item.getNextBooking()));

            TextView next_hit_tv = helper.getView(R.id.next_hit_tv);
            if (item.getNextHit() > item.getLastHit()) {
                next_hit_tv.setCompoundDrawables(null, null, goUp, null);
            } else {
                next_hit_tv.setCompoundDrawables(null, null, goDown, null);
            }
            helper.setText(R.id.next_hit_tv, new BigDecimal(item.getNextHit() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "%");

            TextView next_sos_tv = helper.getView(R.id.next_sos_tv);
            if (item.getNextSOS() > item.getLastSOS()) {
                next_sos_tv.setCompoundDrawables(null, null, goUp, null);
            } else if (item.getNextSOS() == item.getLastSOS()) {
                next_sos_tv.setCompoundDrawables(null, null, equal, null);
            } else {
                next_sos_tv.setCompoundDrawables(null, null, goDown, null);
            }
            helper.setText(R.id.next_sos_tv, new BigDecimal(item.getNextSOS() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "%");

            TextView next_hc_tv = helper.getView(R.id.next_hc_tv);
            if (item.getNextHC() > item.getLastHC()) {
                next_hc_tv.setCompoundDrawables(null, null, goUp, null);
            } else {
                next_hc_tv.setCompoundDrawables(null, null, goDown, null);
            }
            helper.setText(R.id.next_hc_tv, Util.numberFormat(item.getNextHC()));

            TextView next_vpy = helper.getView(R.id.next_vpy);
            if (item.getVpy() > 0) {
                next_vpy.setCompoundDrawables(null, null, goUp, null);
            } else {
                next_vpy.setCompoundDrawables(null, null, goDown, null);
            }
            helper.setText(R.id.next_vpy, (int) (item.getVpy() * 100) + "%");
            helper.setText(R.id.follow_tv, item.getFollow());

            if (getItemCount() == (helper.getAdapterPosition() + 1)) {
                helper.itemView.setBackgroundColor(Color.parseColor("#B4D07D"));
            } else {
                if (helper.getAdapterPosition() % 2 == 0) {
                    helper.itemView.setBackgroundColor(getResources().getColor(R.color.main_grey));
                } else {
                    helper.itemView.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        }
    }


    class MyAdapter extends BaseQuickAdapter<Data, BaseViewHolder> {

        public MyAdapter(int layoutResId, @Nullable List<Data> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Data item) {
            helper.setText(R.id.column1, Util.numberFormat(item.getLastPro()));
            helper.setText(R.id.column2, item.getLastCoverageRate());
            helper.setText(R.id.column3, item.getLastHit());
            helper.setText(R.id.column4, item.getLastSalesPersonNum() + "");
            helper.setText(R.id.column5, item.getArea());
            helper.setText(R.id.column6, Util.numberFormat(item.getNextPro()));
            helper.setText(R.id.column7, item.getNextCoverageRate());
            helper.setText(R.id.column8, item.getNextHit());
            helper.setText(R.id.column9, item.getNextSalesPersonNum() + "");
            helper.setText(R.id.column10, (int) (item.getVpy() * 100) + "%");

            if (item.getVpy() > 0) {
                helper.setTextColor(R.id.column10, getResources().getColor(R.color.default_text_black_color));
            } else {
                helper.setTextColor(R.id.column10, getResources().getColor(R.color.main));
            }

            if (helper.getAdapterPosition() % 2 == 0) {
                helper.itemView.setBackgroundColor(getResources().getColor(R.color.main_grey));
            } else {
                helper.itemView.setBackgroundColor(getResources().getColor(R.color.white));
            }

        }
    }


    class Whole {
        private int lastMS;
        private int lastPro;
        private double lastCov;
        private int lastBooking;
        private double lastHit;
        private double lastSOS;
        private int lastHC;
        private String area;

        private int nextMS;
        private int nextPro;
        private double nextCov;
        private int nextBooking;
        private double nextHit;
        private double nextSOS;
        private int nextHC;

        private double vpy;

        private String follow;

        public int getLastMS() {
            return lastMS;
        }

        public void setLastMS(int lastMS) {
            this.lastMS = lastMS;
        }

        public int getLastPro() {
            return lastPro;
        }

        public void setLastPro(int lastPro) {
            this.lastPro = lastPro;
        }

        public double getLastCov() {
            return lastCov;
        }

        public void setLastCov(double lastCov) {
            this.lastCov = lastCov;
        }

        public int getLastBooking() {
            return lastBooking;
        }

        public void setLastBooking(int lastBooking) {
            this.lastBooking = lastBooking;
        }

        public double getLastHit() {
            return lastHit;
        }

        public void setLastHit(double lastHit) {
            this.lastHit = lastHit;
        }

        public double getLastSOS() {
            return lastSOS;
        }

        public void setLastSOS(double lastSOS) {
            this.lastSOS = lastSOS;
        }

        public int getLastHC() {
            return lastHC;
        }

        public void setLastHC(int lastHC) {
            this.lastHC = lastHC;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getNextMS() {
            return nextMS;
        }

        public void setNextMS(int nextMS) {
            this.nextMS = nextMS;
        }

        public int getNextPro() {
            return nextPro;
        }

        public void setNextPro(int nextPro) {
            this.nextPro = nextPro;
        }

        public double getNextCov() {
            return nextCov;
        }

        public void setNextCov(double nextCov) {
            this.nextCov = nextCov;
        }

        public int getNextBooking() {
            return nextBooking;
        }

        public void setNextBooking(int nextBooking) {
            this.nextBooking = nextBooking;
        }

        public double getNextHit() {
            return nextHit;
        }

        public void setNextHit(double nextHit) {
            this.nextHit = nextHit;
        }

        public double getNextSOS() {
            return nextSOS;
        }

        public void setNextSOS(double nextSOS) {
            this.nextSOS = nextSOS;
        }

        public int getNextHC() {
            return nextHC;
        }

        public void setNextHC(int nextHC) {
            this.nextHC = nextHC;
        }

        public double getVpy() {
            return vpy;
        }

        public void setVpy(double vpy) {
            this.vpy = vpy;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }

        public Whole(int lastMS, int lastPro, double lastCov, int lastBooking, double lastHit, double lastSOS, int lastHC, String area, int nextMS, int nextPro, double nextCov, int nextBooking, double nextHit, double nextSOS, int nextHC, double vpy, String follow) {
            this.lastMS = lastMS;
            this.lastPro = lastPro;
            this.lastCov = lastCov;
            this.lastBooking = lastBooking;
            this.lastHit = lastHit;
            this.lastSOS = lastSOS;
            this.lastHC = lastHC;
            this.area = area;
            this.nextMS = nextMS;
            this.nextPro = nextPro;
            this.nextCov = nextCov;
            this.nextBooking = nextBooking;
            this.nextHit = nextHit;
            this.nextSOS = nextSOS;
            this.nextHC = nextHC;
            this.vpy = vpy;
            this.follow = follow;
        }
    }


    class Data {
        private int lastPro;
        private String lastCoverageRate;
        private String lastHit;
        private int lastSalesPersonNum;
        private String area;
        private int nextPro;
        private String nextCoverageRate;
        private String nextHit;
        private int nextSalesPersonNum;
        private double vpy;

        public int getLastPro() {
            return lastPro;
        }

        public void setLastPro(int lastPro) {
            this.lastPro = lastPro;
        }

        public String getLastCoverageRate() {
            return lastCoverageRate;
        }

        public void setLastCoverageRate(String lastCoverageRate) {
            this.lastCoverageRate = lastCoverageRate;
        }

        public String getLastHit() {
            return lastHit;
        }

        public void setLastHit(String lastHit) {
            this.lastHit = lastHit;
        }

        public int getLastSalesPersonNum() {
            return lastSalesPersonNum;
        }

        public void setLastSalesPersonNum(int lastSalesPersonNum) {
            this.lastSalesPersonNum = lastSalesPersonNum;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getNextPro() {
            return nextPro;
        }

        public void setNextPro(int nextPro) {
            this.nextPro = nextPro;
        }

        public String getNextCoverageRate() {
            return nextCoverageRate;
        }

        public void setNextCoverageRate(String nextCoverageRate) {
            this.nextCoverageRate = nextCoverageRate;
        }

        public String getNextHit() {
            return nextHit;
        }

        public void setNextHit(String nextHit) {
            this.nextHit = nextHit;
        }

        public int getNextSalesPersonNum() {
            return nextSalesPersonNum;
        }

        public void setNextSalesPersonNum(int nextSalesPersonNum) {
            this.nextSalesPersonNum = nextSalesPersonNum;
        }

        public double getVpy() {
            return vpy;
        }

        public void setVpy(double vpy) {
            this.vpy = vpy;
        }

        public Data(int lastPro, String lastCoverageRate, String lastHit, int lastSalesPersonNum, String area, int nextPro, String nextCoverageRate, String nextHit, int nextSalesPersonNum, double vpy) {
            this.lastPro = lastPro;
            this.lastCoverageRate = lastCoverageRate;
            this.lastHit = lastHit;
            this.lastSalesPersonNum = lastSalesPersonNum;
            this.area = area;
            this.nextPro = nextPro;
            this.nextCoverageRate = nextCoverageRate;
            this.nextHit = nextHit;
            this.nextSalesPersonNum = nextSalesPersonNum;
            this.vpy = vpy;
        }
    }
}
