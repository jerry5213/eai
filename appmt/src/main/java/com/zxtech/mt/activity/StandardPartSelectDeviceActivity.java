package com.zxtech.mt.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxtech.mt.adapter.CommonAdapter;
import com.zxtech.mt.adapter.ViewHolder;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.common.VolleySingleton;
import com.zxtech.mt.entity.BaseElevator;
import com.zxtech.mt.entity.MtProject;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/6.
 */
public class StandardPartSelectDeviceActivity extends BaseActivity implements View.OnClickListener {
    private InsideListAdapter adapter;
    private ListView lv_list;
    private EditText et_search;
    private List<BaseElevator> allDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = mInfalter.inflate(R.layout.activity_standard_part_select_device, null);
        main_layout.addView(view);

        setBottomLayoutHide();
        setup();
        refresh();
    }

    @Override
    protected void onCreate() {

    }

    @Override
    protected void findView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    private void refresh() {

        Map<String, String> param = new HashMap<>();
        param.put("proj_id", getIntent().getStringExtra("DATA"));

        HttpUtil.getInstance(mContext).request("/mtmo/getmtelevatorlist.mo", param, new HttpCallBack<List<BaseElevator>>() {
            @Override
            public void onSuccess(List<BaseElevator> list) {
                allDatas.addAll(list);
                adapter.setDatas(allDatas);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
            }
        });

    }

    private void setup() {
        lv_list = (ListView) findViewById(R.id.lv_list);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BaseElevator data = adapter.getItem(i);
                Intent intent = new Intent();
                intent.putExtra("DATA", data);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        adapter = new InsideListAdapter(StandardPartSelectDeviceActivity.this, null);
        lv_list.setAdapter(adapter);
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (adapter != null) {
                    List<BaseElevator> newDatas = new ArrayList<BaseElevator>();
                    for (BaseElevator data : allDatas) {
                        if (data.getElevator_code() != null && (data.getElevator_code().contains(text))) {
                            newDatas.add(data);
                        }
                    }
                    adapter.setDatas(newDatas);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }



    private static class InsideListAdapter extends CommonAdapter<BaseElevator> {

        public InsideListAdapter(Context context, List<BaseElevator> datas) {
            super(context, datas == null ? new ArrayList<BaseElevator>() : datas, R.layout.item_listview_standard_part_device_select);
        }

        public List<BaseElevator> getDatas() {
            return mDatas;
        }

        public void setDatas(List<BaseElevator> datas) {
            this.mDatas.clear();
            this.mDatas.addAll(datas);
        }

        @Override
        public void convert(ViewHolder holder, final BaseElevator data, int position) {
            StringBuilder sb = new StringBuilder();
            sb.append(mContext.getString(R.string.elevator_code)+" ");
            sb.append(TextUtils.isEmpty(data.getElevator_code()) ? "" : data.getElevator_code());
            holder.setText(R.id.tv_name, sb.toString());
        }

    }

}
