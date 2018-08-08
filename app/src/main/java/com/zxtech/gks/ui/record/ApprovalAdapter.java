package com.zxtech.gks.ui.record;

import android.content.Context;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.vo.RecordApproval;

import java.util.List;

/**
 * Created by syp523 on 2017/11/30.
 */

public class ApprovalAdapter extends CommonAdapter<RecordApproval> {

    public ApprovalAdapter(Context context, List<RecordApproval> datas, int layoutId) {
        super(context,layoutId,datas);
    }

    @Override
    public void convert(ViewHolder holder, RecordApproval recordApproval, int position) {
        holder.setText(R.id.project_no,recordApproval.getProjectNo());
        holder.setText(R.id.project_name,recordApproval.getProjectName());
        holder.setText(R.id.salesman_name,recordApproval.getSalesmanUserName());
        holder.setText(R.id.branch_name,recordApproval.getBranchName());
        holder.setText(R.id.create_date,recordApproval.getCreateTime());

        String type = recordApproval.getProjectType();
        TextView tv_project_type = holder.getView(R.id.tv_project_type);
        //GradientDrawable myGrad = (GradientDrawable)tv_project_type.getBackground();
        if(Constants.PROJ_TYPE_XSDXM.equals(type)){
            //myGrad.setColor(Color.parseColor("#ff33b5e5"));
            tv_project_type.setText("大项目");
        }else if(Constants.PROJ_TYPE_PT.equals(type)){
            //myGrad.setColor(Color.parseColor("#ff99cc00"));
            tv_project_type.setText(mContext.getString(R.string.standard_project));
        }else {
            //myGrad.setColor(Color.parseColor("#33CCCCCC"));
            tv_project_type.setText("其它");
        }

        /*if(position % 2 == 0){
            holder.getView(R.id.list_item).setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            holder.getView(R.id.list_item).setBackgroundColor(Color.parseColor("#F5F5F5"));
        }*/
    }
}
