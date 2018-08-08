package com.zxtech.ecs.ui.home.contractchange;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.DistributionElevator;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/27.
 */

public class ChangeElevatorDialogFragment extends BaseDialogFragment implements MultiItemTypeAdapter.OnItemClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.number_tv)
    TextView number_tv;

    public ChangeElevatorCallBack callBack;

    private MyAdapter mAdapter;
    private String number;
    private List<String> mDatas = new ArrayList<>();

    public static ChangeElevatorDialogFragment newInstance() {
        return new ChangeElevatorDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_contract_change_elevator;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        setwScale(0.8f);
        sethScale(0.4f);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 8);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(gridLayoutManager);
        recycler_view.addItemDecoration(new MyItemDecoration(DensityUtil.dip2px(getActivity(), 4)));
        mAdapter = new MyAdapter(getActivity(), R.layout.item_distribution, mDatas);
        recycler_view.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        number_tv.setText(number);
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }

    @OnClick({R.id.confirm_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_tv:
                List<Integer> positions = mAdapter.getPositions();
                if (positions.size() == 0) {
                    ToastUtil.showLong("请选择要变更的电梯");
                    return;
                }
                if (callBack != null) {
                    String elevatorNo = "";
                    for (int i = 0; i < positions.size(); i++) {
                        String ele = mDatas.get(positions.get(i));
                        if ("All".equals(ele)) continue; //过滤掉all
                        elevatorNo += ele;
                        elevatorNo += ",";
                    }
                    if (elevatorNo.endsWith(",")){
                        elevatorNo = elevatorNo.substring(0,elevatorNo.length()-1);
                    }
                    callBack.confirm(elevatorNo);
                }
                dismiss();
                break;
        }
    }

    public void setmDatas(List<String> mDatas) {
        this.mDatas = mDatas;
        this.mDatas.add("All");//全选
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        int lastPosition = this.mDatas.size()-1;
        if (position == (lastPosition)) { //判断点击全选
            if (mAdapter.getPositions().contains(lastPosition) ) {
                mAdapter.getPositions().clear();
            }else{
                for (int i = 0; i <= lastPosition; i++) {
                    if (!mAdapter.getPositions().contains(Integer.valueOf(i))) {
                        mAdapter.getPositions().add(Integer.valueOf(i));
                    }
                }
            }

        }else{
            if (mAdapter.getPositions().contains(position)) {
                mAdapter.getPositions().remove(Integer.valueOf(position));
                mAdapter.getPositions().remove(Integer.valueOf(lastPosition));
            } else {
                mAdapter.getPositions().add(Integer.valueOf(position));
                if ( mAdapter.getPositions().size() == (lastPosition)) {
                    mAdapter.getPositions().add(Integer.valueOf(lastPosition));
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    class MyAdapter extends CommonAdapter<String> {
        private List<Integer> positions = new ArrayList<>();

        public MyAdapter(Context context, int layoutId, List<String> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, String item, int position) {
            TextView content_tv = holder.getView(R.id.content_tv);
            content_tv.setText(item);
            if (positions.contains(position)) {
                content_tv.setTextColor(mContext.getResources().getColor(R.color.main));
                content_tv.setBackground(getSelectBorder());
            } else {
                content_tv.setTextColor(mContext.getResources().getColor(R.color.default_text_grey_color));
                content_tv.setBackground(getBorder());

            }
        }

        public List<Integer> getPositions() {
            return positions;
        }

        private GradientDrawable getBorder() {
            int strokeWidth = 1; // 3px not dp
            int roundRadius = 8; // 8px not dp
            int strokeColor = mContext.getResources().getColor(R.color.main_grey);
            int fill = mContext.getResources().getColor(R.color.main_grey);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(roundRadius);
            gd.setStroke(strokeWidth, strokeColor);
            gd.setColor(fill);
            return gd;
        }
        private GradientDrawable getSelectBorder() {
            int strokeWidth = 1; // 3px not dp
            int roundRadius = 8; // 8px not dp
            int strokeColor = mContext.getResources().getColor(R.color.main);
            int fill = mContext.getResources().getColor(R.color.white);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(roundRadius);
            gd.setStroke(strokeWidth, strokeColor);
            gd.setColor(fill);
            return gd;
        }

    }


    public interface ChangeElevatorCallBack {
        void confirm(String elevatorNo);
    }
}
