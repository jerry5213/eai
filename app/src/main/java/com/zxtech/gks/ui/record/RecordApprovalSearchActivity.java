package com.zxtech.gks.ui.record;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;

import butterknife.BindView;

/**
 * Created by syp523 on 2017/12/4.
 */

public class RecordApprovalSearchActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @BindView(R.id.proj_no_et)
    EditText proj_no_et;
    @BindView(R.id.proj_name_et)
    EditText proj_name_et;
    @BindView(R.id.customer_et)
    EditText customer_et;
    @BindView(R.id.overdue_cb)
    CheckBox overdue_cb;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_approval_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String search_proj_no = intent.getStringExtra("search_proj_no");
        proj_no_et.setText(search_proj_no);
        String search_proj_name = intent.getStringExtra("search_proj_name");
        proj_name_et.setText(search_proj_name);
        String search_customer = intent.getStringExtra("search_customer");
        customer_et.setText(search_customer);
        boolean search_overdue=  intent.getBooleanExtra("search_overdue",false);
        overdue_cb.setChecked(search_overdue);
        setListener();
        proj_no_et.setOnEditorActionListener(this);
        proj_name_et.setOnEditorActionListener(this);
        customer_et.setOnEditorActionListener(this);
    }

    private void setListener() {
        //setting_tv.setOnClickListener(this);
        //showSetting("保存");
    }

    private void save(){
        Intent intent = getIntent();
        intent.putExtra("search_proj_no",proj_no_et.getText().toString());
        intent.putExtra("search_proj_name",proj_name_et.getText().toString());
        intent.putExtra("search_customer",customer_et.getText().toString());
        intent.putExtra("search_overdue",overdue_cb.isChecked());
        setResult(1,intent);
        finish();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            save();
        }
        return false;
    }
}
