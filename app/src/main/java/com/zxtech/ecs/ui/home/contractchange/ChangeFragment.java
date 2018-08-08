package com.zxtech.ecs.ui.home.contractchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ContractChangeAdapter;
import com.zxtech.ecs.model.ContractChange;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * 合同变更-state 0待变更 1变更中 2已变更
 * Created by syp523 on 2018/5/31.
 */

public class ChangeFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener, ChangeSelectedDialogFragment.ChangeSelectedBack {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;

    private ContractChangeAdapter mAdapter;
    private List<ContractChange> mDatas = new ArrayList<>();
    private int state;

    public static ChangeFragment newInstance(int state, boolean isEdit) {
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        bundle.putBoolean("isEdit", isEdit);
        ChangeFragment fragment = new ChangeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_quotation;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        state = getArguments().getInt("state");

        BGARefreshViewHolder rv1 = new BGANormalRefreshViewHolder(getContext(), false);
        refreshLayout.setRefreshViewHolder(rv1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.addItemDecoration(new MyItemDecoration(15));

        refreshLayout.setDelegate(this);

        mAdapter = new ContractChangeAdapter(R.layout.item_offer, mDatas);
        recycleView.setAdapter(mAdapter);
        refreshLayout.beginRefreshing();

        mAdapter.setOnItemClickListener(this);
    }

    private void refresh() {
        baseResponseObservable = HttpFactory.getApiService().getContractChangeList(getUserNo(), state);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<ContractChange>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ContractChange>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<ContractChange>> response) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            mDatas.clear();
                            mDatas.addAll(response.getData());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showLong(getString(R.string.msg8));
                        }
                        refreshLayout.endRefreshing();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        refreshLayout.endRefreshing();
                    }
                });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1002) {
            refresh();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (state == 0) {
            ChangeSelectedDialogFragment changeSelectedDialogFragment = ChangeSelectedDialogFragment.newInstance(position);
            changeSelectedDialogFragment.callBack = this;
            changeSelectedDialogFragment.show(getActivity().getFragmentManager(), "change_selected");
        } else {

            ContractChange contractChange = mDatas.get(position);
            boolean isEdit = getArguments().getBoolean("isEdit");
            if (isEdit && ("变更申请".equals(contractChange.getInstanceNodeName()) || "确认变更内容是否有疑义".equals(contractChange.getInstanceNodeName()) || "销售员合同变更申请".equals(contractChange.getInstanceNodeName()))) {
                //销售员
                startIntentDetail(contractChange.getProjectNo(), contractChange.getProjectGuid_Before(), contractChange.getProjectGuid_After(), contractChange.getGuid(), contractChange.getTypeId(), contractChange.getContractNo(), isEdit);
            } else if (!isEdit && "分总审批".equals(contractChange.getInstanceNodeName())) {
                //分总
                startIntentDetail(contractChange.getProjectNo(), contractChange.getProjectGuid_Before(), contractChange.getProjectGuid_After(), contractChange.getGuid(), contractChange.getTypeId(), contractChange.getContractNo(), isEdit);
            }
        }

    }

    @Override
    public void confirm(String type, int position) {
        ContractChange contractChange = mDatas.get(position);
        confirm(type, contractChange.getProjectNo(), contractChange.getContractGuid(), contractChange.getContractNo(), position);
    }


    private void confirm(final String type, final String projectNo, String oldContract, final String contractNo, final int position) {
        Map<String, String> params = new HashMap<>();
        params.put("oldContractGuid", oldContract);
        params.put("typeId", type);
        params.put("userId", getUserId());
        params.put("userName", getUserName());
        params.put("userNo", getUserNo());
        baseResponseObservable = HttpFactory.getApiService().createContractChangeInfo(params);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<Map<String, String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, String>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, String>> response) {
                        mDatas.remove(position);
                        mAdapter.notifyDataSetChanged();

                        startIntentDetail(projectNo, response.getData().get("ProjectGuid_Before"), response.getData().get("ProjectGuid_After"), response.getData().get("ContractChangeGuid"), type, contractNo, getArguments().getBoolean("isEdit"));
                    }
                });
    }

    private void startIntentDetail(String projectNo, String projectGuidBefore, String projectGuidAfter, String contractChangeGuid, String contractType, String contractNo, boolean isEdit) {
        Intent intent = new Intent(mContext, ChangeSelectedDialogFragment.CONTRACT_CHANGE_CANCEL_CHANGE.equals(contractType) ? CancelChangeDetailActivity.class : ContractChangeDetailActivity.class);
        intent.putExtra("projectNo", projectNo);
        intent.putExtra("projectGuidBefore", projectGuidBefore);
        intent.putExtra("projectGuidAfter", projectGuidAfter);
        intent.putExtra("contractChangeGuid", contractChangeGuid);
        intent.putExtra("contractType", contractType);
        intent.putExtra("contractNo", contractNo);
        intent.putExtra("isEdit", isEdit);
        startActivityForResult(intent, 1001);
    }
}