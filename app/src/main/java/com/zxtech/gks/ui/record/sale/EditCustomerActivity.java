package com.zxtech.gks.ui.record.sale;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.vo.Customer;
import com.zxtech.gks.model.vo.type.CustomerProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SYP521 on 2017/12/28.
 */

public class EditCustomerActivity extends BaseActivity implements IActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.customer_name)
    EditText customer_name;
    @BindView(R.id.link_name)
    EditText link_name;
    @BindView(R.id.customer_phone)
    EditText customer_phone;
    @BindView(R.id.fixed_line_tel)
    EditText fixed_line_tel;
    @BindView(R.id.customer_bank)
    EditText customer_bank;
    @BindView(R.id.bank_account)
    EditText bank_account;
    @BindView(R.id.bank_location)
    EditText bank_location;
    @BindView(R.id.duty_no)
    EditText duty_no;
    @BindView(R.id.post_code)
    EditText post_code;
    @BindView(R.id.customerAddress)
    EditText customerAddress;
    @BindView(R.id.customerRelationship)
    EditText customerRelationship;

    List<CustomerProperty> customerProperties;
    private ArrayAdapter<CustomerProperty> customerPropertyAdapter;
    private String customerId;
    private Customer customer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_customer;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.new_customer));
        initData();
    }

    public void initData() {

        Intent intent = getIntent();
        String action = intent.getStringExtra("action");
        if ("add".equals(action)) {
            setTitle(getString(R.string.new_customer));
        } else if ("modify".equals(action)) {
            setTitle(getString(R.string.edit_customer));
            customer = (Customer) getIntent().getSerializableExtra(Constants.DATA1);
            customerId = customer.getGuid();
            customer_name.setText(customer.getCustomerName());
            link_name.setText(customer.getCustomerContact());
            customer_phone.setText(customer.getPhoneNumber());
            fixed_line_tel.setText(customer.getTEL());
            customer_bank.setText(customer.getBankName());
            bank_account.setText(customer.getBankAccount());
            bank_location.setText(customer.getBankOnCity());
            duty_no.setText(customer.getTaxNumber());
            post_code.setText(customer.getPostalCode());
            customerAddress.setText(customer.getCustomerAdd());
            customerRelationship.setText(customer.getCustomerRelationship());
        }
        //  initCustomerProperty();
    }


    @OnClick(R.id.save_btn)
    public void onClick(View view) {
        submitCustomerInfo();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定要放弃已修改的内容吗？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    private void initCustomerProperty() {

//        GsonCallBack<JsonElementVO> callback = new GsonCallBack<JsonElementVO>(this) {
//            @Override
//            protected void onDoSuccess(JsonElementVO vo) {
//                if(vo.getData().isJsonArray()){
//                    customerProperties = new Gson().fromJson(vo.getData(),new TypeToken<List<CustomerProperty>>(){}.getType());
//                    if(customerProperties!=null && customerProperties.size()>0){
//                        customer_property_selector.setItems(customerProperties);
//                        if(customer!=null){
//                            customer_property_selector.setText(customer.getCustomerProperty());
//                            customer_property_selector.setTag(customer.getCustomerProperty());
//                        }
//                    }
//                }
//            }
//        };
//        String url = APPConfig.BASE_URL + "getCustomerPropertyList.do";
//        GRequestData requestData = new GRequestData(Request.Method.POST, url, null);
//        AppUtil.getHttp().send(requestData, callback);
    }

    private Map params = new HashMap();

    private void submitCustomerInfo() {

        String c_name = customer_name.getText().toString();
        String address = customerAddress.getText().toString();
        String contact = link_name.getText().toString();
        String tel = fixed_line_tel.getText().toString();
        String phone = customer_phone.getText().toString();
        // String property = customer_property_selector.getText().toString();
        if (isNull(c_name, address, contact, tel, phone)) {
            ToastUtil.showLong(getString(R.string.toast2));
            return;
        }

        getParam("Guid", customerId);
        getParam("CreateUserId", getUserId());
        getParam("CustomerName", c_name);
        getParam("CustomerProperty", "");
        getParam("PhoneNumber", phone);
        getParam("CustomerContact", contact);
        getParam("CustomerAdd", address);
        getParam("TEL", tel);
        getParam("BankName", customer_bank.getText().toString());
        getParam("BankAccount", bank_account.getText().toString());
        getParam("BankOnCity", bank_location.getText().toString());
        getParam("TaxNumber", duty_no.getText().toString());
        getParam("PostalCode", post_code.getText().toString());
        getParam("CustomerRelationship", customerRelationship.getText().toString());

        baseResponseObservable = HttpFactory.getApiService().saveCustomemr(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse>(this, false) {

                    @Override
                    public void onSuccess(BasicResponse response) {
                        ToastUtil.showLong("提交成功");
                        setResult(2);
                        finish();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    private void getParam(String key, String value) {

        if (!TextUtils.isEmpty(value)) {
            params.put(key, value);
        }
    }

    private boolean isNull(String... value) {

        for (String s : value) {
            if (TextUtils.isEmpty(s) || "请选择".equals(s)) {
                return true;
            }
        }
        return false;
    }
}
