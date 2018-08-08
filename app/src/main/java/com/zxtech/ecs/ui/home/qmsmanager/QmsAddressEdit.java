package com.zxtech.ecs.ui.home.qmsmanager;

import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.QmsAddressList;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.GsonUtils;
import com.zxtech.ecs.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by syp521 on 2018/4/10.
 */

public class QmsAddressEdit extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.customer_phone)
    TextView customer_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.isMr)
    SwitchCompat isMr;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    private String AutoId = "0";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qms_address_edit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar);

        String action = getIntent().getStringExtra("action");

        if("0".equals(action)){

        }else if("1".equals(action)){
            QmsAddressList.QmsAddress qmsAddress = (QmsAddressList.QmsAddress)getIntent().getSerializableExtra("address");
            name_tv.setText(qmsAddress.getLXR());
            customer_phone.setText(qmsAddress.getLXDH());
            tv_address.setText(qmsAddress.getLXDZ());
            AutoId = qmsAddress.getAutoId()+"";
        }

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    private void submit() {

        if(TextUtils.isEmpty(name_tv.getText().toString()) || TextUtils.isEmpty(customer_phone.getText().toString())
                || TextUtils.isEmpty(tv_address.getText().toString())){

            ToastUtil.showLong("输入项不能空！");
            return;
        }

        String defaultAddress;
        if(isMr.isChecked()){
            defaultAddress = "True";
        }else{
            defaultAddress = "False";
        }

        String label = "工地";
        for(int i = 0 ;i < radioGroup.getChildCount();i++){
            AppCompatRadioButton rb = (AppCompatRadioButton)radioGroup.getChildAt(i);
            if(rb.isChecked()){
                label = rb.getText().toString();
                break;
            }
        }

        //将参数转为json格式
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("AutoId",AutoId);
        paramsMap.put("FKR",getUserNo());
        paramsMap.put("LXR",name_tv.getText().toString());
        paramsMap.put("LXDH",customer_phone.getText().toString());
        paramsMap.put("LXDZ",tv_address.getText().toString());
        paramsMap.put("IsMR",defaultAddress);
        paramsMap.put("SX",label);
        String params = GsonUtils.toJson(paramsMap, false);

        HttpFactory.getApiService()
                .saveAppAddress(params)
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        Log.i(QmsAddressEdit.class.getSimpleName(),"保存收货地址成功");
                        finish();
                    }
                });
    }
}
