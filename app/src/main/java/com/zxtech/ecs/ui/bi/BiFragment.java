package com.zxtech.ecs.ui.bi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.BiMenuAdapter;
import com.zxtech.ecs.ui.home.follow.FollowActivity;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/3/22.
 */

public class BiFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bi_rv)
    RecyclerView bi_rv;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    private BiMenuAdapter mAdapter;

    public static BiFragment newInstance() {
        Bundle args = new Bundle();
        BiFragment fragment = new BiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bi;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        toolbar_title.setText(getString(R.string.nav_bi));
        toolbar.setNavigationIcon(null);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 4);
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        bi_rv.setLayoutManager(linearLayoutManager);
        List<String> mDatas = new ArrayList<>();
        mDatas.add(getString(R.string.sales_channel_management));
        mDatas.add(getString(R.string.capacity_management));
        mDatas.add(getString(R.string.promotion_event));
        mDatas.add(getString(R.string.sales_projection));
        mAdapter = new BiMenuAdapter(R.layout.item_bi, mDatas);
        bi_rv.addItemDecoration(new MyItemDecoration());
        bi_rv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        Intent intent = new Intent(mContext,ChannelActivity.class);
//        startActivity(intent);
//        ToastUtil.showLong(mContext.getString(R.string.msg7));
        Class[] classes = new Class[]{ChannelActivity.class, SalesCapacityActivity.class, SalesActivitiesActivity.class, ForecastActivity.class};
        Intent intent = new Intent(mContext, classes[position]);
        startActivity(intent);
    }
}

