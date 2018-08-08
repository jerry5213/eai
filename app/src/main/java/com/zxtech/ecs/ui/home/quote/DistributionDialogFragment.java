package com.zxtech.ecs.ui.home.quote;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.DistributionLocationAdapter;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.ScreenUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by syp523 on 2018/3/31.
 */

public class DistributionDialogFragment extends DialogFragment implements MultiItemTypeAdapter.OnItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Unbinder bind;

    private List<String> mDatas = new ArrayList<>();
    private DistributionLocationAdapter mAdapter;

    public DistributionDialogCallBack callBack;

    public static DistributionDialogFragment newInstance() {
        DistributionDialogFragment fragment = new DistributionDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加这句话去掉自带的标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_distribution, null);
        bind = ButterKnife.bind(this, view);


        LinearLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration(DensityUtil.dip2px(getActivity(), 2)));
        mDatas.add("A");
        mDatas.add("B");
        mDatas.add("C");
        mDatas.add("D");
        mDatas.add("E");
        mDatas.add("F");
        mDatas.add("G");
        mDatas.add("H");
        mAdapter = new DistributionLocationAdapter(getActivity(), R.layout.item_distribution_dialog, mDatas);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(getActivity()) * 0.7);
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != null)
            bind.unbind();
    }


    @OnClick({R.id.cancel_btn, R.id.confirm_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_btn:
                dismiss();
                break;
            case R.id.confirm_btn:
                if (mAdapter.getSelectedPostion() == -1) {
                    ToastUtil.showLong("请选择其中一组");
                    return;
                }
                callBack.confirm(mDatas.get(mAdapter.getSelectedPostion()));
                dismiss();
                break;
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        mAdapter.setSelectedPostion(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }


    public interface DistributionDialogCallBack {
        void confirm(String group);
    }
}
