package com.zxtech.ecs.ui.home.quote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ProjectOfferAdapter;
import com.zxtech.ecs.model.ProjectQuote;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp523 on 2018/5/31.
 */

public class QuotationFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener{

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private ProjectOfferAdapter mAdapter;
    private List<ProjectQuote> mDatas = new ArrayList<>();

    public static QuotationFragment newInstance() {
        Bundle args = new Bundle();
        QuotationFragment fragment = new QuotationFragment();
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

        mAdapter = new ProjectOfferAdapter(R.layout.item_offer, mDatas);
        recycleView.setAdapter(mAdapter);
        refreshLayout.beginRefreshing();

        mAdapter.setOnItemClickListener(this);
    }

    private void refresh() {
        baseResponseObservable = HttpFactory.getApiService().getProjectOfferList(getUserNo());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<ProjectQuote>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ProjectQuote>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<ProjectQuote>> response) {
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
        if (requestCode == 1001 && resultCode == 1002) {
            refresh();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ProjectQuote projectQuote = mDatas.get(position);
        Intent intent = new Intent(mContext, ProjectQuoteEditActivity.class);
        intent.putExtra("projectQuote", projectQuote);
        startActivityForResult(intent,1001);
    }
}
