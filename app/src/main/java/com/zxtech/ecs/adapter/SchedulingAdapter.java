package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ContractChange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syp523 on 2018/3/31.
 */

public class SchedulingAdapter extends BaseQuickAdapter<ContractChange, BaseViewHolder> {

    private List<Integer> selectedPositions = new ArrayList<>();
    private int state;
    private OnclickCallBack callBack;

    public SchedulingAdapter(int layoutResId, @Nullable List<ContractChange> data, int state) {
        super(layoutResId, data);
        this.state = state;
    }

    @Override
    protected void convert(final BaseViewHolder holder, ContractChange item) {

        holder.setGone(R.id.check_iv,state == 1);
        if("end".equals(item.getTaskRunState())){
            holder.setText(R.id.process_tv, "流程结束");
        }else{
            holder.setText(R.id.process_tv, item.getInstanceNodeName());
        }
        holder.setText(R.id.contract_no_tv, item.getProjectName());
        holder.setText(R.id.elevator_number_tv, item.getElevatorNo());
        holder.setText(R.id.plannedTime_tv, item.getExpectedTime());
        holder.setText(R.id.orderCompleteTime_tv, item.getPlannedTime());
        holder.setText(R.id.completeTime_tv, item.getRealTime());

        ImageView check_iv = holder.getView(R.id.check_iv);
        holder.setGone(R.id.check_iv,state == 1);
        if (selectedPositions.contains(holder.getAdapterPosition())) {
            check_iv.setImageResource(R.drawable.match_check);
        }else{
            check_iv.setImageResource(R.drawable.match);
        }

        check_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPositions.contains((Integer)holder.getAdapterPosition())) {
                    selectedPositions.remove((Integer)holder.getAdapterPosition());
                }else{
                    selectedPositions.add((Integer) holder.getAdapterPosition());
                }
                callBack.getSelectSize(selectedPositions.size());
            }
        });
    }

    public List<Integer> getSelectedPositions() {
        return selectedPositions;
    }

    public void clearSelect() {
        selectedPositions.clear();
        callBack.getSelectSize(selectedPositions.size());
    }

    public void setListener(OnclickCallBack callBack) {
        this.callBack = callBack;
    }

    public interface OnclickCallBack {
        void getSelectSize(int size);
    }
}
