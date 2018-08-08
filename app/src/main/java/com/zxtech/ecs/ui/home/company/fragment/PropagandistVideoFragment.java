package com.zxtech.ecs.ui.home.company.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.PropagandistVideoAdapter;
import com.zxtech.ecs.model.CompanyEntity;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;

import java.util.List;

import butterknife.BindView;

/**
 * @date: 2018/2/1
 * @desc: 宣传视频
 */

public class PropagandistVideoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<CompanyEntity.ResultInfoBean> mBeans;
    private PropagandistVideoAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    public static PropagandistVideoFragment newInstance() {
        return new PropagandistVideoFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_propagandist_video;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mSrlRefresh.setOnRefreshListener(this);

        mAdapter = new PropagandistVideoAdapter(R.layout.item_propagandist_video, mBeans);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        requestNet();
    }

    @Override
    public void onRefresh() {
        requestNet();
        mSrlRefresh.setRefreshing(false);
    }

    private void requestNet() {
        HttpFactory.getApiService()
                .getCompanyInfo("2", "ComTag3")
                .compose(RxHelper.<BaseResponse<CompanyEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<CompanyEntity>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<CompanyEntity> response) {
                        if (response.getData().getResultInfo() != null) {
                            mBeans = response.getData().getResultInfo();
                            mAdapter.setNewData(mBeans);
                        }
                    }
                });
    }
}
