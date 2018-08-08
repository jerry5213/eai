package com.zxtech.gks.ui.cr;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zxtech.ecs.R;
import com.zxtech.gks.model.vo.contract.WorkFlowNode;

import java.util.List;

/**
 * Created by SYP521 on 2017/10/30.
 */

public class ContractWorkFlowNodeListAdapter extends CommonAdapter<WorkFlowNode> {

    public ContractWorkFlowNodeListAdapter(Context context, int layoutId, List<WorkFlowNode> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, WorkFlowNode workFlowNode, int position) {

        holder.setText(R.id.TransactUserName,workFlowNode.getTransactUserName());
        holder.setText(R.id.SubmitResult,workFlowNode.getSubmitResult());
        holder.setText(R.id.tv_idea,workFlowNode.getSubmitDescription());
    }
}
