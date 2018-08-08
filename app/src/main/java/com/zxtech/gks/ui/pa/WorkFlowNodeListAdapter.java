package com.zxtech.gks.ui.pa;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zxtech.ecs.R;
import com.zxtech.gks.model.vo.PrProductDetail.WorkFlowNodeListBean;

import java.util.List;

/**
 * Created by SYP521 on 2017/10/30.
 */

public class WorkFlowNodeListAdapter extends CommonAdapter<WorkFlowNodeListBean> {

    public WorkFlowNodeListAdapter(Context context, int layoutId, List<WorkFlowNodeListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, WorkFlowNodeListBean workFlowNodeListBean, int position) {

        holder.setText(R.id.InstanceNodeName,workFlowNodeListBean.getInstanceNodeName());
        holder.setText(R.id.TransactUserName,workFlowNodeListBean.getTransactUserName());
        holder.setText(R.id.tv_date,workFlowNodeListBean.getCreateTime()+"~"+workFlowNodeListBean.getCompleteTime());
        holder.setText(R.id.SubmitResult,workFlowNodeListBean.getSubmitResult());
        holder.setText(R.id.tv_idea,workFlowNodeListBean.getSubmitDescription());
    }
}
