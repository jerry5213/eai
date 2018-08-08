package com.zxtech.ecs.ui.home.bid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.BidReviewAdapter;
import com.zxtech.ecs.model.BidReview;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 投标申请-state 0申请中 1已申请
 * Created by syp523 on 2018/5/31.
 */

public class BidApplyFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener,BidResultRegisterDialogFragment.BidResultRegisterDialogFragmentCallBack {

    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout refreshlayout;
    @BindView(R.id.bidding_result_tv)
    TextView bidding_result_tv;

    private BidReviewAdapter mAdapter;
    private List<BidReview> mDatas = new ArrayList<>();
    private int pageIndex = 1;
    private int state;

    public static BidApplyFragment newInstance(int state, boolean isEdit) {
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        bundle.putBoolean("isEdit", isEdit);
        BidApplyFragment fragment = new BidApplyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bid_applying;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        state = getArguments().getInt("state");
        if (state == 0) {
            bidding_result_tv.setVisibility(View.GONE);
        } else {
            bidding_result_tv.setVisibility(View.VISIBLE);
        }

        refreshlayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
        recycleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycleview.addItemDecoration(new MyItemDecoration(15));

        refreshlayout.setDelegate(this);

        mAdapter = new BidReviewAdapter(R.layout.item_bid_apply, mDatas, state);
        recycleview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        refreshlayout.beginRefreshing();
    }

    private void refresh() {
        baseResponseObservable = HttpFactory.getApiService().getBiddingByPage(1, APPConfig.PAGE_SIZE, state, getUserNo());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<BidReview>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<BidReview>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<BidReview>> response) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            pageIndex = 1;
                            mDatas.clear();
                            mDatas.addAll(response.getData());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showLong(getString(R.string.msg8));
                        }
                        refreshlayout.endRefreshing();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        refreshlayout.endRefreshing();
                    }
                });
    }


    private void loadingMore() {
        baseResponseObservable = HttpFactory.getApiService().getBiddingByPage(pageIndex + 1, APPConfig.PAGE_SIZE, state, getUserNo());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<BidReview>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<BidReview>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<BidReview>> response) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            pageIndex++;
                            mDatas.addAll(response.getData());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showLong(getString(R.string.msg8));
                        }
                        refreshlayout.endLoadingMore();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        refreshlayout.endLoadingMore();
                    }
                });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        loadingMore();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            refresh();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        Intent intent = new Intent(mContext,BidDetailActivity.class);
        BidReview bidReview = mDatas.get(position);
        intent.putExtra("data",bidReview);
        intent.putExtra("isEdit",getArguments().getBoolean("isEdit"));
        startActivityForResult(intent,1);
    }

    @OnClick({R.id.bidding_result_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bidding_result_tv:
                int selectedPosition = mAdapter.getSelectedPosition();
                if (selectedPosition == -1) {
                    return;
                }
                if (!"end".equals(mDatas.get(selectedPosition).getTaskRunState())){
                    return;
                }
                String isBidding = mDatas.get(selectedPosition).getIsBidding();
                BidResultRegisterDialogFragment bidResultRegisterDialogFragment = BidResultRegisterDialogFragment.newInstance();
                bidResultRegisterDialogFragment.setEdit(TextUtils.isEmpty(isBidding));
                bidResultRegisterDialogFragment.setProjectGuid(mDatas.get(selectedPosition).getProjectGuid());
                bidResultRegisterDialogFragment.callBack = this;
                bidResultRegisterDialogFragment.show(getActivity().getFragmentManager(),"result_register");
                break;
        }
    }

    @Override
    public void registerResultSave(String isBidding) {
        mDatas.get(mAdapter.getSelectedPosition()).setIsBidding(isBidding);
        mAdapter.notifyItemChanged(mAdapter.getSelectedPosition());
        ToastUtil.showLong("保存成功");
    }
}