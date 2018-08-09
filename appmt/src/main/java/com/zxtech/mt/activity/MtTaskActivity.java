package com.zxtech.mt.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.zxtech.mt.adapter.MtInfoAdapter;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.common.VolleySingleton;
import com.zxtech.mt.entity.JsonData;
import com.zxtech.mt.entity.MtWorkPlan;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxtech.mtos.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 维保详情
 * Created by Chw on 2016/7/6.
 */
public class MtTaskActivity extends BaseActivity{

    private TextView project_textview,address_textview,type_textview,plan_date_textview,workers_textview,elevator_code_textview,
    contract_code_textview,contract_type_textview,contract_begin_textview,elevator_brand_textview,elevator_reg_textview,elevator_type_textview,
            load_textview,speed_textview,door_textview,high_textview,angle_textview,width_textview,relation_person_textview,relation_phone_textview,
            next_check_textview,check_textview;


    private MtWorkPlan work;

    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_maintenance_info, null);
        main_layout.addView(view);
        title_textview.setText(getString(R.string.mt_check));
        setBottomLayoutHide();
    }

    @Override
    protected void findView() {
        project_textview = (TextView) findViewById(R.id.project_textview);
        address_textview = (TextView) findViewById(R.id.address_textview);
        type_textview = (TextView) findViewById(R.id.type_textview);
        plan_date_textview = (TextView) findViewById(R.id.plan_date_textview);

        workers_textview = (TextView) findViewById(R.id.workers_textview);
        elevator_code_textview = (TextView) findViewById(R.id.elevator_code_textview);
        contract_code_textview = (TextView) findViewById(R.id.contract_code_textview);
        contract_type_textview = (TextView) findViewById(R.id.contract_type_textview);
        contract_begin_textview = (TextView) findViewById(R.id.contract_begin_textview);
        elevator_brand_textview = (TextView) findViewById(R.id.elevator_brand_textview);
        elevator_reg_textview = (TextView) findViewById(R.id.elevator_reg_textview);
        elevator_type_textview = (TextView) findViewById(R.id.elevator_type_textview);
        load_textview = (TextView) findViewById(R.id.load_textview);
        speed_textview = (TextView) findViewById(R.id.speed_textview);
        door_textview = (TextView) findViewById(R.id.door_textview);
        high_textview = (TextView) findViewById(R.id.high_textview);
        angle_textview = (TextView) findViewById(R.id.angle_textview);
        width_textview = (TextView) findViewById(R.id.width_textview);
        relation_person_textview = (TextView) findViewById(R.id.relation_person_textview);
        relation_phone_textview = (TextView) findViewById(R.id.relation_phone_textview);
        next_check_textview = (TextView) findViewById(R.id.next_check_textview);
        check_textview = (TextView) findViewById(R.id.check_textview);


    }

    @Override
    protected void setListener() {
        check_textview.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        work = (MtWorkPlan) intent.getSerializableExtra("bean");
        project_textview.setText(work.getProj_name());
        address_textview.setText(work.getProj_address());
        type_textview.setText(work.getWork_type_name());
        plan_date_textview.setText(work.getPlan_date());

        workers_textview.setText(work.getWorkers());
        elevator_code_textview.setText(work.getElevator_code());
        contract_code_textview.setText(work.getContract_code());
        contract_type_textview.setText(work.getContract_type());
        contract_begin_textview.setText(work.getElevator_effect_date());
        elevator_brand_textview.setText(work.getElevator_brand());
        elevator_reg_textview.setText(work.getElevator_reg_number());
        elevator_type_textview.setText(work.getElevator_type());
        load_textview.setText(work.getElevator_load());
        speed_textview.setText(work.getElevator_speed());
        door_textview.setText(work.getElevator_level()+"/"+work.getElevator_station()+"/"+work.getElevator_door());
        high_textview.setText(work.getElevator_high());
        angle_textview.setText(work.getElevator_angle());
        width_textview.setText(work.getElevator_step_width());
        relation_person_textview.setText(work.getElevator_relation_person());
        relation_phone_textview.setText(work.getElevator_relation_phone());
        next_check_textview.setText(work.getNext_check_date());

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId()==check_textview.getId()){
            startWork();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (requestCode==11){
           finish();
       }
    }

    private void intentActivity(){
        Intent it = new Intent(mContext,MtCheckItemActivity.class);
        it.putExtra("bean", work);
        startActivityForResult(it,11);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
    }


    private void startWork(){
        //维保负责人是否为自己
        if ("1".equals(work.getOrder_pincipal()) && (Constants.MT_WORK_STATUS_PAUSE.equals(work.getStatus()) || Constants.MT_WORK_STATUS_RENEW.equals(work.getStatus()))) {
            intentActivity();
        }else{

            Map<String, String> param = new HashMap<>();
            param.put("status", Constants.MT_WORK_STATUS_IN);
            param.put("last_update_user", SPUtils.get(mContext,"user","admin").toString());
            param.put("id", work.getId());
            param.put("plan_id", work.getId());
            param.put("emp_id", SPUtils.get(mContext,"emp_id","").toString());
            HttpUtil.getInstance(mContext).request("/mtmo/updateworkplan.mo", param, new HttpCallBack<String>() {
                @Override
                public void onSuccess(String result) {
                    if ("repeat".equals(result)) {
                        ToastUtil.showShort(mContext,getString(R.string.msg_8));
                    }else{
                        intentActivity();
                    }

                }

                @Override
                public void onFail(String msg) {
                    ToastUtil.showLong(mContext,getString(R.string.msg_3));
                }
            });
        }

    }



}
