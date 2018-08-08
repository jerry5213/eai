package com.zxtech.ecs.ui.home.quote;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.DistributionAdapter;
import com.zxtech.ecs.model.DistributionElevator;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/3/31.
 */

public class DistributionActivity extends BaseActivity implements MultiItemTypeAdapter.OnItemClickListener, DistributionDialogFragment.DistributionDialogCallBack {
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.show_tv)
    TextView show_tv;
    private DistributionAdapter mAdapter;
    private List<DistributionElevator> mDatas = new ArrayList<>();
    private List<DistributionElevator> distributionDatas = new ArrayList<>();
    private String productIds;
    private String projectGuid;
    private String[] shafts = new String[]{"A", "B", "C", "D", "E", "F"};
    private boolean hasElevator = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_distribution;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.layout_drawing));

        productIds = getIntent().getStringExtra("data");
        projectGuid = getIntent().getStringExtra("projectGuid");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 10);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration(DensityUtil.dip2px(mContext, 2)));
        mAdapter = new DistributionAdapter(mContext, R.layout.item_distribution, mDatas);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        initData();
    }

    private void initData() {
        baseResponseObservable = HttpFactory.getApiService().getUndistributedElevatorNoList(productIds);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<Map<String, String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, String>>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, String>> response) {
                        if (response.getData() != null) {
                            if (response.getData().get("result") != null && response.getData().get("result").equals("False")) {
                                ToastUtil.showLong(response.getData().get("message"));
                                return;
                            } else {
                                hasElevator = true;
                                for (Map.Entry<String, String> entry : response.getData().entrySet()) {
                                    if ("result".equals(entry.getKey())) continue;
                                    mDatas.add(new DistributionElevator(entry.getKey(), entry.getValue()));
                                }
                                Collections.sort(mDatas, new Comparator<DistributionElevator>() {
                                    @Override
                                    public int compare(DistributionElevator o1, DistributionElevator o2) {
                                        return o1.getElevator().compareTo(o2.getElevator());
                                    }
                                });
                                mAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                });
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        DistributionElevator distributionElevator = mDatas.get(position);
        if (distributionElevator.isDistribution()) {
            distributionElevator.setDistribution(false);
        } else {
            distributionElevator.setDistribution(true);
        }
        mAdapter.notifyItemChanged(position, false);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @OnClick({R.id.distribution_btn, R.id.save_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.distribution_btn:
                if (getSelectedCount() == 0) {
                    ToastUtil.showLong("请选择一台电梯进行分配");
                    return;
                }
                DistributionDialogFragment dialog = DistributionDialogFragment.newInstance();
                dialog.callBack = this;
                dialog.show(getFragmentManager(), "distribution");
                break;
            case R.id.save_btn:
                if (!hasElevator) {
                    return;
                }
                if (mDatas.size() > 0) {
                    ToastUtil.showLong("请把剩余电梯分配完进行保存");
                } else {
                    saveAction();
                }
                break;
        }
    }

    private int getSelectedCount(){
        int count = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).isDistribution()) {
                count++;
            }
        }
        return count;
    }

    private void saveAction() {
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < shafts.length; i++) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Position", shafts[i]);
            jsonObject.addProperty("ProjectGuid", this.projectGuid);
            jsonObject.addProperty("UserGuid", getUserId());
            jsonObject.addProperty("UserNo", getUserNo());
            jsonObject.addProperty("UserName", getUserName());
            JsonArray elevatorArray = new JsonArray();
            boolean isHas = false;
            for (int j = 0; j < distributionDatas.size(); j++) {
                if (shafts[i].equals(distributionDatas.get(j).getPosition())) {
                    isHas = true;
                    JsonObject elevatorJson = new JsonObject();
                    elevatorJson.addProperty("EQSProductGuid", distributionDatas.get(j).getProductId());
                    elevatorJson.addProperty("ElevatorNo", distributionDatas.get(j).getElevator());
                    elevatorArray.add(elevatorJson);
                }
            }
            if (isHas) {
                jsonObject.add("ElevatorNoInfos", elevatorArray);
                jsonArray.add(jsonObject);
            }
        }

        baseResponseObservable = HttpFactory.getApiService().saveElevatorAssignInfo(jsonArray.toString());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        ToastUtil.showLong("保存成功");
                        finish();
                    }
                });
    }

    @Override
    public void confirm(String group) {

        List<DistributionElevator> unDistributionDatas = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {

            DistributionElevator distributionElevator = mDatas.get(i);
            if (distributionElevator.isDistribution()) {
                distributionElevator.setPosition(group);
                distributionDatas.add(distributionElevator);
            }else {
                unDistributionDatas.add(distributionElevator);
            }

        }

        mDatas.clear();
        mDatas.addAll(unDistributionDatas);
        mAdapter.notifyDataSetChanged();

        StringBuffer showBuffer = new StringBuffer();
        for (int i = 0; i < shafts.length; i++) {
            List<String> elevators = new ArrayList<>();
            for (int j = 0; j < distributionDatas.size(); j++) {
                if (shafts[i].equals(distributionDatas.get(j).getPosition())) {
                    elevators.add(distributionDatas.get(j).getElevator());
                }
            }

            for (int j = 0; j < elevators.size(); j++) {
                if (j == 0) {
                    showBuffer.append(" " + shafts[i] + ":");
                }
                showBuffer.append(elevators.get(j));
                if (j != (elevators.size() - 1)) {
                    showBuffer.append(",");
                }
            }
        }
        show_tv.setText(showBuffer);
    }
}
