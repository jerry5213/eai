package com.zxtech.ecs.ui.home.qmsmanager;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.QmsLogisticsAdapter;
import com.zxtech.ecs.model.LogisticsInfoEntity;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.GsonUtils;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;

/**
 * @auth: hexl
 * @date: 2018/3/5
 * @desc: 物流
 */

public class QmsTaskTrackingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.jobNumber)
    TextView jobNumber;
    @BindView(R.id.time)
    TextView time;
    @BindViews({R.id.image1,R.id.image2,R.id.image3,R.id.image4,R.id.image5})
    ImageView[] imageViews;
    private QmsLogisticsAdapter mAdapter;
    private List<LogisticsInfoEntity.LogisticsListBean> mEntityList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qms_logistics;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        requestNet();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void requestNet() {

        String JobNumber = getIntent().getStringExtra("JobNumber");
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("JobNumber",JobNumber);
        mEntityList = new ArrayList<>();
        mAdapter = new QmsLogisticsAdapter(R.layout.item_qms_logistics, mEntityList);
        mRecyclerView.setAdapter(mAdapter);
        String params = GsonUtils.toJson(paramsMap, false);
        HttpFactory.getApiService()
                .getLogisticsInfo(params)
                .compose(RxHelper.<BaseResponse<LogisticsInfoEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<LogisticsInfoEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<LogisticsInfoEntity> response) {
                        mAdapter.setNewData(response.getData().getLogisticsList());
                        jobNumber.setText(response.getData().getJobNumber());
                        time.setText(response.getData().getCreateDate());
                        String taskStatus = response.getData().getTaskStatus();
                        initTaskFlow(taskStatus);
                    }
                });
    }

    private void initTaskFlow(String taskStatus){

        if(TextUtils.isEmpty(taskStatus)){
            return;
        }
        int status = Integer.parseInt(taskStatus);
        for(int a = 0;a<status;a++){
            imageViews[a].setImageResource(R.drawable.ic_qms_checked);
        }
    }
}
