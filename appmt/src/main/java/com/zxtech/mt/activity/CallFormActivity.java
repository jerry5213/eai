package com.zxtech.mt.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.entity.CalCallFix;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.widget.PopMenu;
import com.zxtech.mt.widget.SimpleDialog;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 修理反馈单
 * Created by Chw on 2016/7/3.
 */
public class CallFormActivity extends BaseActivity{
    private RelativeLayout number_layout,method_layout,stop_reason_layout,project_layout;

    private TextView media_textview,caller_textview,callerphone_textview,number_textview,station_textview
            ,speed_textview,load_textview,repairdate_textview,method_textview,product_textview,project_textview,amve_textview
            ,caltype_textview,angle_textview,brand_textview,reg_textview,width_textview,high_textview;

    private CalCallFix calCallFix;

    private EditText desc_edittext,errcode_edittext,result_edittext,stop_reason_edittext,trapnumber_edittext,traptime_edittext;

    private boolean isRequest = false;

    private LinearLayout trap_layout;


    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_callform1, null);
        main_layout.addView(view);

        setBottomLayoutHide();
        set_textview.setBackgroundResource(R.drawable.setting);


    }

    @Override
    protected void findView() {
        number_layout= (RelativeLayout) findViewById(R.id.number_layout);
        stop_reason_layout = (RelativeLayout) findViewById(R.id.stop_reason_layout);
        project_layout =  (RelativeLayout) findViewById(R.id.project_layout);
        number_textview = (TextView) findViewById(R.id.number_textview);
        station_textview = (TextView) findViewById(R.id.station_textview);
        speed_textview = (TextView) findViewById(R.id.speed_textview);
        load_textview = (TextView) findViewById(R.id.load_textview);
        angle_textview = (TextView) findViewById(R.id.angle_textview);
        repairdate_textview = (TextView) findViewById(R.id.repairdate_textview);
        desc_edittext = (EditText) findViewById(R.id.desc_edittext);
        errcode_edittext = (EditText) findViewById(R.id.errcode_edittext);
        result_edittext = (EditText) findViewById(R.id.result_edittext);
        trapnumber_edittext = (EditText) findViewById(R.id.trapnumber_edittext);
        traptime_edittext = (EditText) findViewById(R.id.traptime_edittext);
        method_layout = (RelativeLayout) findViewById(R.id.method_layout);
        method_textview = (TextView) findViewById(R.id.method_textview);
        product_textview = (TextView) findViewById(R.id.product_textview);
        reg_textview = (TextView) findViewById(R.id.reg_textview);
        project_textview = (TextView) findViewById(R.id.project_textview);
        amve_textview = (TextView) findViewById(R.id.amve_textview);
        caltype_textview = (TextView) findViewById(R.id.caltype_textview);
        stop_reason_edittext = (EditText) findViewById(R.id.stop_reason_edittext);

        trap_layout = (LinearLayout) findViewById(R.id.trap_layout);
        brand_textview = (TextView) findViewById(R.id.brand_textview);
        width_textview = (TextView) findViewById(R.id.width_textview);
        high_textview = (TextView) findViewById(R.id.high_textview);

    }

    @Override
    protected void setListener() {
//        method_layout.setOnClickListener(this);
     //   number_layout.setOnClickListener(this);
      //  stop_layout.setOnClickListener(this);
     //   project_layout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        calCallFix = (CalCallFix) intent.getSerializableExtra("bean");
        if ("1".equals(calCallFix.getTrap_flag())) {
            trap_layout.setVisibility(View.VISIBLE);
            title_textview.setText(getString(R.string.closing_report));
        }else{
            trap_layout.setVisibility(View.GONE);
            title_textview.setText(getString(R.string.repair_feedback));
        }

        brand_textview.setText(calCallFix.getElevator_brand());
        repairdate_textview.setText(calCallFix.getCall_time());
        project_textview.setText(calCallFix.getProj_name());
        amve_textview.setText(calCallFix.getStart_work_time());
        caltype_textview.setText(calCallFix.getCall_source_type_name());
        angle_textview.setText(calCallFix.getElevator_angle());
        number_textview.setText(calCallFix.getElevator_code());
        width_textview.setText(calCallFix.getElevator_step_width());
        high_textview.setText(calCallFix.getElevator_high());
        if (!TextUtils.isEmpty(calCallFix.getElevator_speed())){
            speed_textview.setText(calCallFix.getElevator_speed()+" m/s");
        }
        if (!TextUtils.isEmpty(calCallFix.getElevator_load())) {
            load_textview.setText(calCallFix.getElevator_load()+" kg");
        }


        station_textview.setText((calCallFix.getElevator_level()==null?"N/A":calCallFix.getElevator_level())+"/"+(calCallFix.getElevator_station()==null?"N/A":calCallFix.getElevator_station())+"/"+(calCallFix.getElevator_door()==null?"N/A":calCallFix.getElevator_door()));
        product_textview.setText(calCallFix.getElevator_type());
        reg_textview.setText(calCallFix.getElevator_reg_number());
        desc_edittext.setText(calCallFix.getError_description());

        trapnumber_edittext.setText(calCallFix.getRemark());
        if (calCallFix.getTrap_long() != null) {
            traptime_edittext.setText(calCallFix.getTrap_long()+"");
        }
        errcode_edittext.setText(calCallFix.getError_code());
        result_edittext.setText(calCallFix.getFix_result());
        if ("1".equals(calCallFix.getStop_flag())) {
            stop_reason_layout.setVisibility(View.VISIBLE);
            stop_reason_edittext.setText(calCallFix.getRemark());
            stop_reason_edittext.setSelection(stop_reason_edittext.getText().length());
        }
//        if ("2".equals(calCallFix.getDeal_status())){ //被驳回
//            desc_edittext.setText(calCallFix.getError_description());
//            desc_edittext.setSelection(desc_edittext.getText().length());
//            reason_edittext.setText(calCallFix.getError_reason());
//            result_edittext.setText(calCallFix.getFix_result());
//            errcode_textview.setText(calCallFix.getError_code());
//        }


//        method_textview.setText(calCallFix.getContract_type_name());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.set_textview) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int width = (int) (displayMetrics.widthPixels * 0.4);
            int height = (int) (displayMetrics.heightPixels * 0.4);

            final PopMenu pm = new PopMenu(this, width, height);

            List<Map<String, Object>> resources = new ArrayList<Map<String, Object>>();
            Map<String, Object> maps1 = new HashMap<String, Object>();
            maps1.put("text", getString(R.string.submit));
            resources.add(maps1);
            if (!Constants.CAL_TASK_STATUS_STOP.equals(calCallFix.getStatus())) {
                Map<String, Object> maps2 = new HashMap<String, Object>();
                maps2.put("text", getString(R.string.stop_elevator));
                resources.add(maps2);
            }

            pm.addItems(resources);
            pm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            submitForm();
                            pm.dismiss();
                            break;
                        case 1:
                            pm.dismiss();
                            if (TextUtils.isEmpty(stop_reason_edittext.getText())) {
                                ToastUtil.showLong(mContext, getString(R.string.msg_70));
                                return;
                            }
                            dialog = SimpleDialog.createDialog(mContext, getString(R.string.hint), getString(R.string.msg_71), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialog.dismiss();
                                    handleStop();
                                }
                            }, false);
                            dialog.show();


                            break;

                    }
                }
            });
            pm.showAsDropDown(set_textview);


        }
    }


    private void submitForm() {

        if (TextUtils.isEmpty(desc_edittext.getText().toString())) {
            ToastUtil.showLong(mContext,getString(R.string.msg_57));
            return;
        }
        if (TextUtils.isEmpty(result_edittext.getText().toString())) {
            ToastUtil.showLong(mContext,getString(R.string.msg_59));
            return;
        }

        progressbar.show();

        calCallFix.setError_description(desc_edittext.getText().toString());
        calCallFix.setError_code(errcode_edittext.getText().toString());
        calCallFix.setFix_result(result_edittext.getText().toString());
       // calCallFix.setRemark(trapnumber_edittext.getText().toString());
        if (!TextUtils.isEmpty(traptime_edittext.getText())) {
            //被困时长
            calCallFix.setTrap_long(Integer.parseInt(traptime_edittext.getText().toString()));
        }


        Map<String, String> param = new HashMap<>();
        param = gson.fromJson(gson.toJson(calCallFix),new TypeToken<Map<String, String>>(){}.getType());
        param.put("status",Constants.CAL_TASK_STATUS_SUBMIT);
        HttpUtil.getInstance(mContext).request("/mtmo/updatecallfix.mo", param, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                calCallFix.setStatus(Constants.CAL_TASK_STATUS_SUBMIT);
                ToastUtil.showLong(mContext,getString(R.string.submit_success));
                isRequest = false;
                if (getIntent().getIntExtra("activity",0) == 1){
                    setResult(CallActivity.FRESH_CALL);
                }else{
                    setResult(CallActivity.FRESH_STOP);
                }
                progressbar.dismiss();
                finish();
            }

            @Override
            public void onFail(String msg) {
                progressbar.dismiss();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==330&&resultCode==110) {
        }

    }


    private void handleStop(){

        progressbar.show();

        calCallFix.setError_description(desc_edittext.getText().toString());
        calCallFix.setError_code(errcode_edittext.getText().toString());
        calCallFix.setFix_result(result_edittext.getText().toString());
        calCallFix.setRemark(stop_reason_edittext.getText().toString());//停梯原因
        if (!TextUtils.isEmpty(traptime_edittext.getText())) {
            //被困时长
            calCallFix.setTrap_long(Integer.parseInt(traptime_edittext.getText().toString()));
        }

        Map<String, String> param = new HashMap<>();
        param = gson.fromJson(gson.toJson(calCallFix),new TypeToken<Map<String, String>>(){}.getType());
        param.put("status",Constants.CAL_TASK_STATUS_STOP);
        HttpUtil.getInstance(mContext).request("/mtmo/updatecallfix.mo", param, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                calCallFix.setStatus(Constants.CAL_TASK_STATUS_STOP);
                ToastUtil.showLong(mContext,getString(R.string.submit_success));
                isRequest = false;
                if (getIntent().getIntExtra("activity",0) == 1){
                    setResult(CallActivity.FRESH_CALL);
                }else{
                    setResult(CallActivity.FRESH_STOP);
                }
                progressbar.dismiss();
                finish();
            }

            @Override
            public void onFail(String msg) {
                progressbar.dismiss();
            }
        });

    }


}
