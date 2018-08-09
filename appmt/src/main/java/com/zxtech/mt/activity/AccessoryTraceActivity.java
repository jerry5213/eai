package com.zxtech.mt.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.zxtech.mt.adapter.AccessoryTraceAdapter;
import com.zxtech.mt.entity.MtAccessoryInquiry;
import com.zxtech.mt.entity.MtAccessoryTraceBatch;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.widget.MyItemDecoration;
import com.zxtech.mt.widget.RecycleViewDivider;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 备件溯源信息
 * Created by syp523 on 2017/8/28.
 */

public class AccessoryTraceActivity extends BaseActivity{

    private RecyclerView recyview;

    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_accessory_trace, null);
        main_layout.addView(view);
        title_textview.setText(getString(R.string.menu_project_origin));
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lps);
        setBottomLayoutHide();

    }

    @Override
    protected void findView() {
        recyview = (RecyclerView) findViewById(R.id.recyview);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        Map<String, String> param = new HashMap<>();
        param.put("id", getIntent().getStringExtra("id"));

        HttpUtil.getInstance(mContext).request("/mtmo/getaccessorytracebatch.mo", param, new HttpCallBack<MtAccessoryTraceBatch>() {
            @Override
            public void onSuccess(MtAccessoryTraceBatch trace) {
                RecyclerView.LayoutManager mgr = new LinearLayoutManager(mContext);
                recyview.setLayoutManager(mgr);
                List<HashMap<String,String>> listMap = new ArrayList<>();
                HashMap<String,String> map = null;
                for (int i=0;i<7;i++) {
                    map = new HashMap<>();
                    switch (i) {
                        case 0:
                            map.put("title","备件件号：");
                            map.put("content",trace.getAcc_code());
                           break;
                        case 1:
                            map.put("title","生产厂商：");
                            map.put("content",trace.getAcc_factory());
                            break;
                        case 2:
                            map.put("title","厂商编号：");
                            map.put("content",trace.getAcc_factory_code());
                            break;
                        case 3:
                            map.put("title","备件名称：");
                            map.put("content",trace.getAcc_name());
                            break;
                        case 4:
                            map.put("title","备件规格：");
                            map.put("content",trace.getAcc_spec());
                            break;
                        case 5:
                            map.put("title","生产日期：");
                            map.put("content",trace.getProduct_date());
                            break;
                        case 6:
                            map.put("title","批次编号：");
                            map.put("content",trace.getBatch_code());
                            break;
                    }

                    listMap.add(map);
                }
                AccessoryTraceAdapter adapter = new AccessoryTraceAdapter(listMap);
                recyview.addItemDecoration(new RecycleViewDivider(
                        mContext, LinearLayoutManager.HORIZONTAL));
                recyview.setAdapter(adapter);
            }

            @Override
            public void onFail(String msg) {
            }
        });

    }
}
