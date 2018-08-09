package com.zxtech.mt.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.necer.ncalendar.calendar.MWCalendar;
import com.necer.ncalendar.listener.OnCalendarChangeListener;
import com.zxtech.mt.entity.PltEmpPosition;
import com.zxtech.mt.utils.DateUtil;
import com.zxtech.mt.utils.DensityUtil;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mtos.R;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 考勤打卡
 * Created by Chw on 2016/6/28.
 */
@Route(path = "/mt/workcheck")
public class WorkCheckActivity extends BaseActivity  {

    private TextView start_work_address,end_work_address,last_address_textview,calendar_title,today_textview,week_textview;

    private TextView clock1_textview,go_work_textview,up_work_textview,go_icon,up_icon;

    //    /** 0上班打卡 1下班打卡*/
    private int clockMode = 0;


    private MsgReceiver msgReceiver;

    private String address,longitude,latitude;


    private String selectedDate = null;
    private MWCalendar mwCalendar = null;
    private RelativeLayout calendar_layout;

    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            last_address_textview.setText(intent.getStringExtra("info"));
            address = intent.getStringExtra("address");
            longitude = intent.getStringExtra("longitude");
            latitude = intent.getStringExtra("latitude");
        }

    }

    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_work_check, null);
        main_layout.addView(view);
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lps);
        title_textview.setText(getString(R.string.attendance_working));
        setBottomLayoutHide();

    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(msgReceiver);
        super.onDestroy();
    }

    @Override
    protected void findView() {
       calendar_layout = (RelativeLayout) findViewById(R.id.calendar_layout);
        clock1_textview = (TextView) findViewById(R.id.clock1_textview);
        start_work_address = (TextView) findViewById(R.id.start_work_address);
        last_address_textview = (TextView) findViewById(R.id.last_address_textview);
        end_work_address = (TextView) findViewById(R.id.end_work_address);
        go_work_textview = (TextView) findViewById(R.id.go_work_textview);
        up_work_textview = (TextView) findViewById(R.id.up_work_textview);
        calendar_title = (TextView) findViewById(R.id.calendar_title);
        go_icon = (TextView) findViewById(R.id.go_icon);
        today_textview = (TextView) findViewById(R.id.today_textview);
        week_textview = (TextView) findViewById(R.id.week_textview);
        up_icon = (TextView) findViewById(R.id.up_icon);
         mwCalendar = (MWCalendar) findViewById(R.id.mWCalendar);
        mwCalendar.setOnClickCalendarListener(new OnCalendarChangeListener() {
            @Override
            public void onClickCalendar(DateTime dateTime) {
                calendar_title.setText(dateTime.getYear() + "年" + dateTime.getMonthOfYear() + "月");
                start_work_address.setText(null);
                go_work_textview.setText(null);
                end_work_address.setText(null);
                up_work_textview.setText(null);
                selectedDate = DateUtil.formatDate(new Date( dateTime.getMillis()));
                a(DateUtil.formatDate(dateTime.getMillis()));
            }

            @Override
            public void onCalendarPageChanged(DateTime dateTime) {
                calendar_title.setText(dateTime.getYear() + "年" + dateTime.getMonthOfYear() + "月");
            }
        });
        //mwCalendar.close();
        calendar_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150));

//        calendarView.setSelectedDate(new Date());
//        calendarView.setOnDateChangedListener(this);
    }



    @Override
    protected void setListener() {
        clock1_textview.setOnClickListener(this);
        today_textview.setOnClickListener(this);
        week_textview.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
//        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
//                PushUtil.getMetaValue(this, "api_key"));

        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.mt.receiver");
        registerReceiver(msgReceiver, intentFilter);


        selectedDate = DateUtil.getCurrentDate();
        calendar_title.setText(selectedDate);
        a(DateUtil.getCurrentDate());
    }



    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.clock1_textview) {
            clock();

        } else if (i == R.id.today_textview) {
            DateTime dateTime = new DateTime();
            mwCalendar.setDate(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
            a(DateUtil.getCurrentDate());

        } else if (i == R.id.week_textview) {
            if (getString(R.string.week_).equals(week_textview.getText().toString())) {
                mwCalendar.open();
                calendar_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext, 250f)));
                week_textview.setText(getString(R.string.month_));
            } else {
                mwCalendar.close();
                calendar_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150));
                week_textview.setText(getString(R.string.week_));
            }


        }
    }


    private void a (final String date){
        progressbar.show();

        Map<String, String> param = new HashMap<>();
        param.put("date", date);
        param.put("emp_id", SPUtils.get(mContext,"emp_id","admin").toString());
        HttpUtil.getInstance(mContext).request("/mtmo/getworkbyemp.mo", param, new HttpCallBack<List<PltEmpPosition>>() {
            @Override
            public void onSuccess(List<PltEmpPosition> list) {

                if (list != null && list.size() > 0) {
                        if (DateUtil.getCurrentDate().equals(date)) {
                            clockMode = 1;
                        }
                        for (PltEmpPosition emp:list) {
                            if ("3".equals(emp.getWork_type())) {
                                if (TextUtils.isEmpty(emp.getAddress())) {
                                    start_work_address.setText(getString(R.string.unknown_address));
                                }else{
                                    start_work_address.setText(emp.getAddress());
                                }

                                go_work_textview.setText(emp.getRecord_timestamp().replaceAll("-",":"));

                            }else{
                                if (TextUtils.isEmpty(emp.getAddress())) {
                                    end_work_address.setText(getString(R.string.unknown_address));
                                }else{
                                    end_work_address.setText(emp.getAddress());
                                }
                                up_work_textview.setText(emp.getRecord_timestamp().replaceAll("-",":"));
                                up_icon.setBackgroundResource(R.drawable.blue_circle);
                            }

                        }
                }else{
                    up_icon.setBackgroundResource(R.drawable.grey_circle);
                }
                progressbar.dismiss();
            }

            @Override
            public void onFail(String msg) {
                progressbar.dismiss();
            }
        });
    }


    private void clock(){
        progressbar.show();

        Map<String, String> param = new HashMap<>();
        if (!TextUtils.isEmpty(address)) {
            param.put("address",address);
        }
        if (!TextUtils.isEmpty(longitude)) {
            param.put("longitude",longitude);
        }
        if (!TextUtils.isEmpty(latitude)) {
            param.put("latitude",latitude);
        }
        param.put("emp_id", SPUtils.get(mContext,"emp_id","").toString());
        if (clockMode == 0) {
            param.put("work_type","3");
        }else{
            param.put("work_type","4");
        }

        HttpUtil.getInstance(mContext).request("/mtmo/workclock.mo", param, new HttpCallBack<List<PltEmpPosition>>() {
            @Override
            public void onSuccess(List<PltEmpPosition> list) {

                if (DateUtil.getCurrentDate().equals(selectedDate)) { //只在今天界面更新数据

                    String now = DateUtil.getCurrentDate2();
                    if (clockMode == 0) {
                        go_work_textview.setText(now);
                        if (TextUtils.isEmpty(address)) {
                            start_work_address.setText(getString(R.string.unknown_address));
                        }else{
                            start_work_address.setText(address);
                        }

                        clockMode = 1;
                    }else{
                        up_work_textview.setText(now);
                        up_icon.setBackgroundResource(R.drawable.blue_circle);
                        if (TextUtils.isEmpty(address)) {
                            end_work_address.setText(getString(R.string.unknown_address));
                        }else{
                            end_work_address.setText(address);
                        }
                        clockMode = 1;
                    }
                    clock1_textview.setText(getString(R.string.clock)+"\n"+now);
                }
                ToastUtil.showLong(mContext,getString(R.string.clock_in_success));
                progressbar.dismiss();
            }

            @Override
            public void onFail(String msg) {
                progressbar.dismiss();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent d) {

    }




}




