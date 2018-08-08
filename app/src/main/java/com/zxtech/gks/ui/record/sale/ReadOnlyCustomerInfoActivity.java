package com.zxtech.gks.ui.record.sale;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.vo.Customer;

import butterknife.BindView;

/**
 * Created by SYP521 on 2017/12/28.
 */

public class ReadOnlyCustomerInfoActivity extends BaseActivity implements IActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.customer_name)
    TextView customer_name;
    @BindView(R.id.link_name)
    TextView link_name;
    @BindView(R.id.customer_phone)
    TextView customer_phone;
    @BindView(R.id.fixed_line_tel)
    TextView fixed_line_tel;
    @BindView(R.id.customer_bank)
    TextView customer_bank;
    @BindView(R.id.bank_account)
    TextView bank_account;
    @BindView(R.id.bank_location)
    TextView bank_location;
    @BindView(R.id.duty_no)
    TextView duty_no;
    @BindView(R.id.post_code)
    TextView post_code;
    @BindView(R.id.customer_property)
    TextView customer_property;
    @BindView(R.id.customerAddress)
    TextView customerAddress;
    @BindView(R.id.customerRelationship)
    TextView customerRelationship;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_readonly_customer_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar);
        initData();
    }

    public void initData() {

        Customer customer = (Customer)getIntent().getSerializableExtra(Constants.DATA1);
        customer_name.setText(customer.getCustomerName());
        link_name.setText(customer.getCustomerContact());
        customer_phone.setText(customer.getPhoneNumber());
        fixed_line_tel.setText(customer.getTEL());
        customer_bank.setText(customer.getBankName());
        bank_account.setText(customer.getBankAccount());
        bank_location.setText(customer.getBankOnCity());
        duty_no.setText(customer.getTaxNumber());
        post_code.setText(customer.getPostalCode());
        customer_property.setText(customer.getCustomerProperty());
        customerAddress.setText(customer.getCustomerAdd());
        customerRelationship.setText(customer.getCustomerRelationship());
    }

}
