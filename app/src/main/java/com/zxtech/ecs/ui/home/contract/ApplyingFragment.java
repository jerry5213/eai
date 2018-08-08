package com.zxtech.ecs.ui.home.contract;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ContractApplyingAdapter;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.gks.model.vo.contract.ContractData;
import com.zxtech.gks.model.vo.contract.ContractDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * 合同申请-申请中
 * Created by syp523 on 2018/5/31.
 */

public class ApplyingFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private ContractApplyingAdapter mAdapter;
    private List<ContractData> mDatas = new ArrayList<>();

    public static ApplyingFragment newInstance() {
        Bundle args = new Bundle();
        ApplyingFragment fragment = new ApplyingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_quotation;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        BGARefreshViewHolder rv1 = new BGANormalRefreshViewHolder(getContext(), false);
        refreshLayout.setRefreshViewHolder(rv1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.addItemDecoration(new MyItemDecoration(15));

        refreshLayout.setDelegate(this);

        mAdapter = new ContractApplyingAdapter(R.layout.item_contract_applying, mDatas);
        recycleView.setAdapter(mAdapter);
        refreshLayout.beginRefreshing();

        mAdapter.setOnItemClickListener(this);
    }

    private void refresh() {
        baseResponseObservable = HttpFactory.getApiService().getContractByApplying(getUserNo());
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<ContractData>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ContractData>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<ContractData>> response) {
                        mDatas.clear();
                        if (response.getData() != null && response.getData().size() > 0) {
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
        if (resultCode == 10032) {
            refresh();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(mContext, ContractDetailActivity.class);
        intent.putExtra("contractGuid",mDatas.get(position).getContractGuid());
        intent.putExtra("PRProductGuid",mDatas.get(position).getPR_ProductGuid());
        startActivityForResult(intent,10031);

    }
}
