package com.zxtech.mt.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zxtech.mt.adapter.CallFinishAdapter;
import com.zxtech.mt.adapter.CallStopAdapter;
import com.zxtech.mt.adapter.MyPagerAdapter;
import com.zxtech.mt.adapter.ReCallAdapter;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.entity.CalCallFix;
import com.zxtech.mt.utils.CacheData;
import com.zxtech.mt.utils.DateUtil;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.utils.Util;
import com.zxtech.mt.widget.SimpleDialog;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * 召修
 * Created by Chw on 2016/6/26.
 */
@Route(path = "/mt/call")
public class CallActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private List<CalCallFix> callList = null;
    private List<CalCallFix> stopList = null;
    private List<CalCallFix> compList = null;

    private Context mContext;
    private LinearLayout view;
    private TextView title1_textview, title2_textview, title3_textview, title4_textview;

    private ViewPager main_viewpager;

    private List<View> views = new ArrayList<>();

    private ListView listview1, listview2, listview3;
    private List<ListView> listViews = new ArrayList<>();
    private BGARefreshLayout mRefreshLayout1, mRefreshLayout2, mRefreshLayout3;
    private List<BGARefreshLayout> refreshLayouts = new ArrayList<>();

    private LinearLayout top_title_layout;

    private ReCallAdapter adapter;
    private CallFinishAdapter finishAdapter;
    private CallStopAdapter stopAdapter;

    private int call_page = 1, stop_page = 1, finish_page = 1;

    public final static int FRESH_CALL = 1;
    public final static int FRESH_STOP = 1;

    @Override
    protected void onCreate() {
        view = (LinearLayout) mInfalter.inflate(R.layout.activity_recall, null);
        main_layout.addView(view);
        mContext = this;
        title_textview.setText(getString(R.string.call));
        //setBackHide();
        menu4.setTextColor(getResources().getColor(R.color.bg_blue));
        menu4_bg.setText("\ue709");
        menu4_bg.setTextColor(mContext.getResources().getColor(R.color.c_theme));
    }

    @Override
    protected void findView() {

        title1_textview = findViewById(R.id.title1_textview);
        title2_textview = findViewById(R.id.title2_textview);
        title3_textview = findViewById(R.id.title3_textview);
        main_viewpager = findViewById(R.id.main_viewpager);
        top_title_layout = findViewById(R.id.top_title_layout);

        View pager1 = mInfalter.inflate(R.layout.viewpager_call, null);
        View pager2 = mInfalter.inflate(R.layout.viewpager_call, null);
        View pager3 = mInfalter.inflate(R.layout.viewpager_call, null);


        listview1 = pager1.findViewById(R.id.listView);
        listview2 = pager2.findViewById(R.id.listView);
        listview3 = pager3.findViewById(R.id.listView);

        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, false);
        mRefreshLayout1 = pager1.findViewById(R.id.rl_refresh);
        mRefreshLayout1.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout1.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate(){

            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                doFresh();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                doLoadMore();
                return false;
            }
        });

        BGARefreshViewHolder refreshViewHolder2 = new BGANormalRefreshViewHolder(this, false);
        mRefreshLayout2 = pager2.findViewById(R.id.rl_refresh);
        mRefreshLayout2.setRefreshViewHolder(refreshViewHolder2);
        mRefreshLayout2.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate(){

            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

                stop_page = 1;
                stopList.clear();
                Map<String, String> param = new HashMap<>();
                param.put("search_type", "2");
                param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
                param.put("page", String.valueOf(stop_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getcallfixlist.mo", param, new HttpCallBack<List<CalCallFix>>() {
                    @Override
                    public void onSuccess(List<CalCallFix> list) {
                        if (list != null) {
                            stopList.addAll(list);
                        }
                        stopAdapter.notifyDataSetChanged();
                        mRefreshLayout2.endRefreshing();
                    }

                    @Override
                    public void onFail(String msg) {
                        mRefreshLayout2.endRefreshing();
                    }
                });
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

                stop_page++;
                Map<String, String> param = new HashMap<>();
                param.put("search_type", "2");
                param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
                param.put("page", String.valueOf(stop_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getcallfixlist.mo", param, new HttpCallBack<List<CalCallFix>>() {
                    @Override
                    public void onSuccess(List<CalCallFix> list) {
                        if (list != null) {
                            stopList.addAll(Util.removeDistinctCall(stopList, list));
                        }
                        stopAdapter.notifyDataSetChanged();
                        mRefreshLayout2.endLoadingMore();
                    }

                    @Override
                    public void onFail(String msg) {
                        mRefreshLayout2.endLoadingMore();
                    }
                });
                return true;
            }
        });

        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalCallFix task = stopList.get(position);
                startActivity(task);
            }
        });

        BGARefreshViewHolder refreshViewHolder3 = new BGANormalRefreshViewHolder(this, false);
        mRefreshLayout3 = pager3.findViewById(R.id.rl_refresh);
        mRefreshLayout3.setRefreshViewHolder(refreshViewHolder3);
        mRefreshLayout3.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate(){

            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

                finish_page = 1;
                compList.clear();
                Map<String, String> param = new HashMap<>();
                param.put("search_type", "3");
                param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
                param.put("page", String.valueOf(finish_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getcallfixlist.mo", param, new HttpCallBack<List<CalCallFix>>() {
                    @Override
                    public void onSuccess(List<CalCallFix> list) {
                        if (list != null) {
                            compList.addAll(list);
                        }

                        finishAdapter.notifyDataSetChanged();
                        mRefreshLayout3.endRefreshing();
                    }

                    @Override
                    public void onFail(String msg) {
                        mRefreshLayout3.endRefreshing();
                    }
                });
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

                finish_page++;
                Map<String, String> param = new HashMap<>();
                param.put("search_type", "3");
                param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
                param.put("page", String.valueOf(finish_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getcallfixlist.mo", param, new HttpCallBack<List<CalCallFix>>() {
                    @Override
                    public void onSuccess(List<CalCallFix> list) {
                        if (list != null) {
                            compList.addAll(Util.removeDistinctCall(compList, list));
                        }

                        finishAdapter.notifyDataSetChanged();
                        mRefreshLayout3.endLoadingMore();
                    }

                    @Override
                    public void onFail(String msg) {
                        mRefreshLayout3.endLoadingMore();
                    }
                });
                return true;
            }
        });

        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CalCallFix task = compList.get(position);
                if (Constants.CAL_TASK_STATUS_COMPLETE.equals(task.getStatus())) {
                    Intent it = new Intent(mContext, CallFeedbackActivity.class);
                    it.putExtra("bean", task);
                    startActivity(it);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                } else {
                    ToastUtil.showLong(mContext, getString(R.string.wait_appraise_sign));
                }
            }
        });

        views.add(pager1);
        views.add(pager2);
        views.add(pager3);

        refreshLayouts.add(mRefreshLayout1);
        refreshLayouts.add(mRefreshLayout2);
        refreshLayouts.add(mRefreshLayout3);

        listViews.add(listview1);
        listViews.add(listview2);
        listViews.add(listview3);
    }

    @Override
    protected void initData() {
        callList = new ArrayList<>();
        stopList = new ArrayList<>();
        compList = new ArrayList<>();
    }


    @Override
    protected void setListener() {
        title1_textview.setOnClickListener(this);
        title2_textview.setOnClickListener(this);
        title3_textview.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        initCurrentList();
        initStopList();
        initFinishList();

        mRefreshLayout1.beginRefreshing();
        main_viewpager.setAdapter(new MyPagerAdapter(views));
        main_viewpager.addOnPageChangeListener(this);
        main_viewpager.setCurrentItem(0);
    }


    private void clickItemAction(final CalCallFix task) {

        if (Constants.CAL_TASK_STATUS_NOTICE.equals(task.getStatus())) {//通知
            task.setStatus(Constants.CAL_TASK_STATUS_ORDER);
            Map<String, String> param = new HashMap<>();
            param.put("status", task.getStatus());
            param.put("id", task.getId());
            HttpUtil.getInstance(mContext).request("/mtmo/updatecallfix.mo", param, new HttpCallBack<String>() {
                @Override
                public void onSuccess(String result) {
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFail(String msg) {

                }
            });
        } else if (Constants.CAL_TASK_STATUS_ORDER.equals(task.getStatus())) {//到达

            dialog = SimpleDialog.createDialog(mContext, getString(R.string.hint), getString(R.string.msg_16), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                    handleArrive(task);
                }
            }, false);
            dialog.show();

        } else if (Constants.CAL_TASK_STATUS_ARRIVE.equals(task.getStatus())) {//工程经理
            startActivity(task);
        }

    }


    private void handleArrive(final CalCallFix task) {
        //提交到达时间
        if (TextUtils.isEmpty(task.getStart_work_time())) {
            task.setStatus(Constants.CAL_TASK_STATUS_ARRIVE);
            Map<String, String> param = new HashMap<>();
            param.put("status", task.getStatus());
            param.put("id", task.getId());
            HttpUtil.getInstance(mContext).request("/mtmo/updatecallfix.mo", param, new HttpCallBack<String>() {
                @Override
                public void onSuccess(String result) {
                    adapter.notifyDataSetChanged();
                    task.setStart_work_time(DateUtil.getCurrentDate1());
                    startActivity(task);
                }

                @Override
                public void onFail(String msg) {

                }
            });
        }
    }

    private void startActivity(CalCallFix task) {
        Intent it = new Intent(this, CallFormActivity.class);
        it.putExtra("bean", task);
        if (Constants.CAL_TASK_STATUS_STOP.equals(task.getStatus())) {
            it.putExtra("activity", 0);
        } else {
            it.putExtra("activity", 1);
        }

        startActivityForResult(it, 1003);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CacheData.temp.size() == 0) return;
        try {
            mRefreshLayout1.beginRefreshing();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == FRESH_CALL) {
            callList.clear();
            mRefreshLayout1.beginRefreshing();
        } else if (resultCode == FRESH_STOP) {
            stopList.clear();
            mRefreshLayout2.beginRefreshing();
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int count = top_title_layout.getChildCount();
        for (int i = 0; i < count; i++) {
            TextView textView = (TextView) top_title_layout.getChildAt(i);
            if (position == 0 && i == 0) { //第一个被选中
                textView.setBackgroundResource(R.drawable.solid_bule_border_left);
                textView.setTextColor(getResources().getColor(R.color.white));
            } else if (position == (count - 1) && (count - 1) == i) { //最后一个被选中
                textView.setBackgroundResource(R.drawable.solid_bule_border_right);
                textView.setTextColor(getResources().getColor(R.color.white));
            } else if (i == 0) {//第一个未被选中
                textView.setBackgroundResource(R.drawable.solid_white_border_left);
                textView.setTextColor(getResources().getColor(R.color.grey));
            } else if (i == count - 1) {
                textView.setBackgroundResource(R.drawable.solid_white_border_right);
                textView.setTextColor(getResources().getColor(R.color.grey));
            } else if (i == position) {
                textView.setBackgroundResource(R.drawable.solid_bule_border_center);
                textView.setTextColor(getResources().getColor(R.color.white));
            } else {
                textView.setBackgroundResource(R.drawable.solid_white_border_center);
                textView.setTextColor(getResources().getColor(R.color.grey));
            }
        }
        refreshLayouts.get(position).beginRefreshing();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.title1_textview) {
            main_viewpager.setCurrentItem(0);

        } else if (i == R.id.title2_textview) {
            main_viewpager.setCurrentItem(1);

        } else if (i == R.id.title3_textview) {
            main_viewpager.setCurrentItem(2);

        } else if (i == R.id.title4_textview) {
            main_viewpager.setCurrentItem(3);

        }
    }

    private void initCurrentList() {

        adapter = new ReCallAdapter(mContext, callList, R.layout.item_recall);
        listview1.setAdapter(adapter);
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalCallFix task = callList.get(position);
                clickItemAction(task);
            }
        });
    }

    private void initStopList() {

        stopAdapter = new CallStopAdapter(mContext, stopList, R.layout.item_stopcall);
        listview2.setAdapter(stopAdapter);
    }

    private void initFinishList() {
        finishAdapter = new CallFinishAdapter(mContext, compList, R.layout.item_finishcall);
        listview3.setAdapter(finishAdapter);
    }

    public void doFresh(){

        call_page = 1;
        callList.clear();
        Map<String, String> param = new HashMap<>();
        param.put("search_type", "1");
        param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
        param.put("page", String.valueOf(call_page));
        HttpUtil.getInstance(mContext).request("/mtmo/getcallfixlist.mo", param, new HttpCallBack<List<CalCallFix>>() {
            @Override
            public void onSuccess(List<CalCallFix> list) {
                if (list != null) {
                    callList.addAll(list);
                }

                adapter.notifyDataSetChanged();
                mRefreshLayout1.endRefreshing();
            }

            @Override
            public void onFail(String msg) {
                mRefreshLayout1.endRefreshing();
            }
        });
    }

    public void doLoadMore(){

        call_page++;
        Map<String, String> param = new HashMap<>();
        param.put("search_type", "1");
        param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
        param.put("page", String.valueOf(call_page));
        HttpUtil.getInstance(mContext).request("/mtmo/getcallfixlist.mo", param, new HttpCallBack<List<CalCallFix>>() {
            @Override
            public void onSuccess(List<CalCallFix> list) {
                if (list != null) {
                    callList.addAll(Util.removeDistinctCall(callList, list));
                }
                adapter.notifyDataSetChanged();
                mRefreshLayout1.endRefreshing();
            }

            @Override
            public void onFail(String msg) {
                mRefreshLayout1.endRefreshing();
            }
        });
    }
}
