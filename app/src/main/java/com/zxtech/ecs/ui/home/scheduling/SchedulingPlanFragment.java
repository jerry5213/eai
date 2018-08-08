package com.zxtech.ecs.ui.home.scheduling;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.GsonBuilder;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.SchedulingAdapter;
import com.zxtech.ecs.event.EventDeliveryTime;
import com.zxtech.ecs.model.ContractChange;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by SYP521 on 2018/7/30.
 */

public class SchedulingPlanFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate
        , SchedulingAdapter.OnclickCallBack {

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.top_tool_layout)
    LinearLayout top_tool_layout;

    private int pageIndex = 1;
    private List<ContractChange> mDatas = new ArrayList<>();
    private SchedulingAdapter mAdapter;
    private String contractProdType;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_scheduling_plan;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new MyItemDecoration(15));

        EventBus.getDefault().register(this);
        refreshLayout.setDelegate(this);

        contractProdType = getArguments().getString("state");
        int state = "NotSubmit".equals(contractProdType) ? 1 : 0;
        mAdapter = new SchedulingAdapter(R.layout.item_scheduling_plan, mDatas, state);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }

    public static SchedulingPlanFragment newInstance(String state) {
        Bundle bundle = new Bundle();
        bundle.putString("state", state);
        SchedulingPlanFragment fragment = new SchedulingPlanFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //loadingMore();
        return false;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        refreshLayout.beginRefreshing();
    }

    public void refresh() {

        baseResponseObservable = HttpFactory.getApiService().getContractProdList(contractProdType, getUserNo());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<ContractChange>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ContractChange>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<ContractChange>> response) {

                        mDatas.clear();
                        if (response.getData() != null && response.getData().size() > 0) {
                            pageIndex = 1;
                            mDatas.addAll(response.getData());
                        } else {
                            ToastUtil.showLong(getString(R.string.msg8));
                        }
                        mAdapter.notifyDataSetChanged();
                        refreshLayout.endRefreshing();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        refreshLayout.endRefreshing();
                    }
                });
    }

    private void loadingMore() {
        baseResponseObservable = HttpFactory.getApiService().getContractProdList(contractProdType, getUserNo());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<ContractChange>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ContractChange>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<ContractChange>> response) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            pageIndex++;
                            mDatas.addAll(response.getData());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showLong(getString(R.string.msg8));
                        }
                        refreshLayout.endLoadingMore();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        refreshLayout.endLoadingMore();
                    }
                });
    }

    @Override
    public void getSelectSize(int size) {

        if (size > 0) {
            top_tool_layout.setVisibility(View.VISIBLE);
        } else {
            top_tool_layout.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn1:
                if (mAdapter.getSelectedPositions().size() == 0) {
                    ToastUtil.showLong("请选择要修改的记录！");
                    return;
                }
                SchedulingDialogFragment fragment = SchedulingDialogFragment.newInstance();
                fragment.sethScale(0.3f);
                fragment.show(getActivity().getFragmentManager(), "scheduling");
                break;
            case R.id.btn2:
                submit();
                break;
        }
    }

    public void submit() {

        //拼接需要修改的id
        List<Map> mapList = new ArrayList<>();
        for (int position : mAdapter.getSelectedPositions()) {

            if(TextUtils.isEmpty(mDatas.get(position).getExpectedTime())){
                ToastUtil.showLong("请填写期望交期！");
                return;
            }
            Map contractProdListMap = new HashMap();
            contractProdListMap.put("Guid",mDatas.get(position).getGuid());
            contractProdListMap.put("ExpectedTime",mDatas.get(position).getExpectedTime());
            mapList.add(contractProdListMap);
        }

        Map paramMap = new HashMap();
        paramMap.put("ContractProdList", mapList);
        //paramMap.put("BranchNo", contractChange.getBranchNo());
        paramMap.put("UserNo", getUserNo());
        paramMap.put("UserName", getUserName());
        paramMap.put("OperateType", "submit");

        String json = new GsonBuilder().serializeNulls().create().toJson(paramMap);

        baseResponseObservable = HttpFactory.getApiService().saveContractProd(json);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        refresh();
                        mAdapter.clearSelect();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(EventDeliveryTime deliveryTime) {
        if (deliveryTime != null && "NotSubmit".equals(contractProdType)) {

            int size = mAdapter.getSelectedPositions().size();
            for (int position : mAdapter.getSelectedPositions()) {
                if (!TextUtils.isEmpty(deliveryTime.getExpectedTime()))
                    mDatas.get(position).setExpectedTime(deliveryTime.getExpectedTime());
                if (!TextUtils.isEmpty(deliveryTime.getPlannedTime()))
                    mDatas.get(position).setPlannedTime(deliveryTime.getPlannedTime());
                if (!TextUtils.isEmpty(deliveryTime.getRealTime()))
                    mDatas.get(position).setRealTime(deliveryTime.getRealTime());
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
