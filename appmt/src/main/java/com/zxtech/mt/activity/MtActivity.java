package com.zxtech.mt.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zxtech.mt.adapter.MtAdapter;
import com.zxtech.mt.adapter.MtFinishAdapter;
import com.zxtech.mt.adapter.MyPagerAdapter;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.entity.MtWorkPlan;
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
 * 维保
 * Created by Chw on 2016/6/22.
 */
@Route(path = "/mt/mt")
public class MtActivity extends BaseActivity {

    private ViewPager main_viewpager;
    private List<View> pagerViews = new ArrayList<>();

    private ListView unfinish_listview;
    private ListView finish_listview;
    private ListView today_listview;
    private ListView week_listview;
    private List<ListView> listViews = new ArrayList<>();
    private BGARefreshLayout mRefreshLayout1, mRefreshLayout2, mRefreshLayout3 ,mRefreshLayout4;
    private List<BGARefreshLayout> refreshLayouts = new ArrayList<>();

    private TextView title1_textview, title2_textview, title3_textview, title4_textview;

    private List<MtWorkPlan> unfinish_list;
    private List<MtWorkPlan> finish_list;
    private List<MtWorkPlan> today_list;
    private List<MtWorkPlan> week_list;

    private MtAdapter todayAdapter;
    private MtAdapter weekAdapter;
    private MtFinishAdapter finishAdapter;
    private MtAdapter unFinishAdapter;

    private int unfinish_page = 1, today_page = 1, week_page = 1, finish_page = 1;

    private LinearLayout top_title_layout;

    private static final int TODAY_REQUEST_CODE = 1001;

    private static final int WEEK_REQUEST_CODE = 1002;

    private static final int UNFINISH_REQUEST_CODE = 1003;


    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_maintenance, null);
        main_layout.addView(view);

        //开启定位服务
//        setBackHide();
        title_textview.setText(getString(R.string.maintenance));
        menu2.setTextColor(getResources().getColor(R.color.bg_blue));
        menu2_bg.setText("\ue73a");
        menu2_bg.setTextColor(mContext.getResources().getColor(R.color.c_theme));
    }

    @Override
    protected void initView() {

        initTodayList();
        initWeekList();
        initUnfinishList();
        initFinishList();
        mRefreshLayout1.beginRefreshing();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void findView() {

        main_viewpager = findViewById(R.id.main_viewpager);
        top_title_layout = findViewById(R.id.top_title_layout);
        title1_textview = findViewById(R.id.title1_textview);
        title2_textview = findViewById(R.id.title2_textview);
        title3_textview = findViewById(R.id.title3_textview);
        title4_textview = findViewById(R.id.title4_textview);

        View pager1 = mInfalter.inflate(R.layout.viewpager_1, null);
        View pager2 = mInfalter.inflate(R.layout.viewpager_1, null);
        View pager3 = mInfalter.inflate(R.layout.viewpager_1, null);
        View pager4 = mInfalter.inflate(R.layout.viewpager_1, null);

        today_listview = pager1.findViewById(R.id.listView);
        week_listview = pager2.findViewById(R.id.listView);
        unfinish_listview = pager3.findViewById(R.id.listView);
        finish_listview = pager4.findViewById(R.id.listView);

        listViews.add(today_listview);
        listViews.add(week_listview);
        listViews.add(unfinish_listview);
        listViews.add(finish_listview);

        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, false);
        mRefreshLayout1 = pager1.findViewById(R.id.rl_refresh);
        mRefreshLayout1.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout1.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate(){

            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

                today_page = 1;

                Map<String, String> param = new HashMap<>();
                param.put("search_type", "1");
                param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
                param.put("page", String.valueOf(today_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getmtplanlist.mo", param, new HttpCallBack<List<MtWorkPlan>>() {
                    @Override
                    public void onSuccess(List<MtWorkPlan> list) {
                        if (list != null) {
                            today_list.clear();
                            today_list.addAll(list);
                        }
                        todayAdapter.notifyDataSetChanged();
                        mRefreshLayout1.endRefreshing();
                    }

                    @Override
                    public void onFail(String msg) {
                        mRefreshLayout1.endRefreshing();
                    }
                });
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

                today_page++;
                Map<String, String> param = new HashMap<>();
                param.put("search_type", "1");
                param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
                param.put("page", String.valueOf(today_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getmtplanlist.mo", param, new HttpCallBack<List<MtWorkPlan>>() {
                    @Override
                    public void onSuccess(List<MtWorkPlan> list) {
                        if (list != null) {
                            today_list.addAll(Util.removeDistinct(today_list, list));
                        }

                        todayAdapter.notifyDataSetChanged();
                        mRefreshLayout1.endLoadingMore();
                    }

                    @Override
                    public void onFail(String msg) {
                        mRefreshLayout1.endLoadingMore();
                    }
                });
                return false;
            }
        });

        BGARefreshViewHolder refreshViewHolder2 = new BGANormalRefreshViewHolder(this, false);
        mRefreshLayout2 = pager2.findViewById(R.id.rl_refresh);
        mRefreshLayout2.setRefreshViewHolder(refreshViewHolder2);
        mRefreshLayout2.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate(){

            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

                week_page = 1;

                Map<String, String> param = new HashMap<>();
                param.put("search_type", "2");
                param.put("emp_id", SPUtils.get(mContext,"emp_id","").toString());
                param.put("page",String.valueOf(week_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getmtplanlist.mo", param, new HttpCallBack<List<MtWorkPlan>>() {
                    @Override
                    public void onSuccess(List<MtWorkPlan> list) {
                        if (list != null) {
                            week_list.clear();
                            week_list.addAll(list);
                        }

                        weekAdapter.notifyDataSetChanged();
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

                week_page++;
                Map<String, String> param = new HashMap<>();
                param.put("search_type", "2");
                param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
                param.put("page", String.valueOf(week_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getmtplanlist.mo", param, new HttpCallBack<List<MtWorkPlan>>() {
                    @Override
                    public void onSuccess(List<MtWorkPlan> list) {
                        if (list != null) {
                            week_list.addAll(Util.removeDistinct(week_list, list));
                        }

                        weekAdapter.notifyDataSetChanged();
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

        BGARefreshViewHolder refreshViewHolder3 = new BGANormalRefreshViewHolder(this, false);
        mRefreshLayout3 = pager3.findViewById(R.id.rl_refresh);
        mRefreshLayout3.setRefreshViewHolder(refreshViewHolder3);
        mRefreshLayout3.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate(){

            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

                finish_page = 1;

                Map<String, String> param = new HashMap<>();
                param.put("search_type", "4");
                param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
                param.put("page", String.valueOf(finish_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getmtplanlist.mo", param, new HttpCallBack<List<MtWorkPlan>>() {
                    @Override
                    public void onSuccess(List<MtWorkPlan> list) {
                        if (list != null) {
                            finish_list.clear();
                            finish_list.addAll(list);
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
                param.put("search_type", "4");
                param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
                param.put("page", String.valueOf(finish_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getmtplanlist.mo", param, new HttpCallBack<List<MtWorkPlan>>() {
                    @Override
                    public void onSuccess(List<MtWorkPlan> list) {
                        if (list != null) {
                            finish_list.addAll(Util.removeDistinct(finish_list, list));
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

        BGARefreshViewHolder refreshViewHolder4 = new BGANormalRefreshViewHolder(this, false);
        mRefreshLayout4 = pager4.findViewById(R.id.rl_refresh);
        mRefreshLayout4.setRefreshViewHolder(refreshViewHolder4);
        mRefreshLayout4.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate(){

            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

                unfinish_page = 1;

                Map<String, String> param = new HashMap<>();
                param.put("search_type", "3");
                param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
                param.put("page", String.valueOf(unfinish_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getmtplanlist.mo", param, new HttpCallBack<List<MtWorkPlan>>() {
                    @Override
                    public void onSuccess(List<MtWorkPlan> list) {
                        if (list != null) {
                            unfinish_list.clear();
                            unfinish_list.addAll(list);
                        }

                        unFinishAdapter.notifyDataSetChanged();
                        mRefreshLayout4.endRefreshing();
                    }

                    @Override
                    public void onFail(String msg) {
                        mRefreshLayout4.endRefreshing();
                    }
                });
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

                unfinish_page++;
                Map<String, String> param = new HashMap<>();
                param.put("search_type", "3");
                param.put("emp_id", SPUtils.get(mContext, "emp_id", "328CD4E29E52423C8F58F51885709678").toString());
                param.put("page", String.valueOf(unfinish_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getmtplanlist.mo", param, new HttpCallBack<List<MtWorkPlan>>() {
                    @Override
                    public void onSuccess(List<MtWorkPlan> list) {
                        if (list != null) {
                            unfinish_list.addAll(Util.removeDistinct(unfinish_list, list));
                        }

                        unFinishAdapter.notifyDataSetChanged();
                        mRefreshLayout4.endLoadingMore();
                    }

                    @Override
                    public void onFail(String msg) {
                        mRefreshLayout4.endLoadingMore();
                    }
                });
                return true;
            }
        });

        refreshLayouts.add(mRefreshLayout1);
        refreshLayouts.add(mRefreshLayout2);
        refreshLayouts.add(mRefreshLayout3);
        refreshLayouts.add(mRefreshLayout4);

        pagerViews.add(pager1);
        pagerViews.add(pager2);
        pagerViews.add(pager3);
        pagerViews.add(pager4);

        main_viewpager.setAdapter(new MyPagerAdapter(pagerViews));
        main_viewpager.setCurrentItem(0);
        main_viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        unfinish_list = new ArrayList<>();
        finish_list = new ArrayList<>();
        today_list = new ArrayList<>();
        week_list = new ArrayList<>();
    }

    @Override
    protected void setListener() {
        title1_textview.setOnClickListener(this);
        title2_textview.setOnClickListener(this);
        title3_textview.setOnClickListener(this);
        title4_textview.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // PushManager.listTags(getApplicationContext());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

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
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TODAY_REQUEST_CODE:
                today_list.clear();
                mRefreshLayout1.beginRefreshing();
                break;
            case WEEK_REQUEST_CODE:
                week_list.clear();
                mRefreshLayout2.beginRefreshing();
                break;
            case UNFINISH_REQUEST_CODE:
                unfinish_list.clear();
                mRefreshLayout4.beginRefreshing();
                break;
        }
    }

    private void clickMtItem(MtWorkPlan mtWorkPlan, int requestCode) {
        Intent it = new Intent(mContext, MtTaskActivity.class);
        it.putExtra("bean", mtWorkPlan);
        startActivityForResult(it, requestCode);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }


    private void initTodayList() {

        todayAdapter = new MtAdapter(mContext, today_list, R.layout.item_name);
        today_listview.setAdapter(todayAdapter);

        today_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final MtWorkPlan mtWorkPlan = today_list.get(position);
                clickMtItem(mtWorkPlan, TODAY_REQUEST_CODE);
            }
        });
    }

    private void initWeekList() {

        weekAdapter = new MtAdapter(mContext, week_list, R.layout.item_name);
        week_listview.setAdapter(weekAdapter);
        week_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final MtWorkPlan mtWorkPlan = week_list.get(position);
                //提示 是否确认提前进行保养？
                String now = DateUtil.getCurrentDate();
                if (mtWorkPlan.getPlan_date() != null && mtWorkPlan.getPlan_date().compareTo(now) > 0 && mtWorkPlan.getStatus().equals(Constants.MT_WORK_STATUS_WAIT)) {

                    dialog = SimpleDialog.createDialog(mContext, getString(R.string.hint), getString(R.string.msg_56), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            clickMtItem(mtWorkPlan, WEEK_REQUEST_CODE);
                            dialog.dismiss();
                        }
                    }, false);
                    dialog.show();

                } else {
                    clickMtItem(mtWorkPlan, WEEK_REQUEST_CODE);
                }


            }
        });

    }


    private void initUnfinishList() {

        unFinishAdapter = new MtAdapter(mContext, unfinish_list, R.layout.item_name);
        unfinish_listview.setAdapter(unFinishAdapter);
        unfinish_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final MtWorkPlan mtWorkPlan = unfinish_list.get(position);
                clickMtItem(mtWorkPlan, UNFINISH_REQUEST_CODE);

            }
        });


    }


    private void initFinishList() {

        finishAdapter = new MtFinishAdapter(mContext, finish_list, R.layout.item_mt_finish);
        finish_listview.setAdapter(finishAdapter);
        finish_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MtWorkPlan mtWorkPlan = finish_list.get(position);
                if (Constants.MT_WORK_STATUS_SUBMIT.equals(mtWorkPlan.getStatus())) {
                    ToastUtil.showLong(mContext, getString(R.string.wait_appraise_sign));
                } else {
                    Intent it = new Intent(mContext, MtFeedbackActivity.class);
                    it.putExtra("data", mtWorkPlan);
                    startActivity(it);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            }
        });
    }


}
