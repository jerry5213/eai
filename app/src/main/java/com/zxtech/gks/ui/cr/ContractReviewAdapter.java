package com.zxtech.gks.ui.cr;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ContractReview;
import com.zxtech.gks.common.Constants;

import java.util.List;

/**
 * Created by SYP521 on 2017/12/13.
 */

public class ContractReviewAdapter extends BaseQuickAdapter<ContractReview,BaseViewHolder> {

    public ContractReviewAdapter(int layoutResId, @Nullable List<ContractReview> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ContractReview item) {
        holder.setText(R.id.project_no,item.getProjectNo());
        holder.setText(R.id.project_name,item.getProjectName());
        holder.setText(R.id.salesman_name,item.getSalesmanUserName());
        holder.setText(R.id.branch_name,item.getBranchName());
        holder.setText(R.id.create_date,item.getCreateTime());

        TextView process_tv = holder.getView(R.id.process_tv);
        String type = item.getProjectType();
        if(Constants.PROJ_TYPE_XSDXM.equals(type)){
            process_tv.setText("大项目");
        }else if(Constants.PROJ_TYPE_PT.equals(type)){
            process_tv.setText(mContext.getString(R.string.standard_project));
        }else {
            process_tv.setText("其它");
        }

    }

}
