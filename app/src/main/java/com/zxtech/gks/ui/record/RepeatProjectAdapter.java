package com.zxtech.gks.ui.record;

import android.content.Context;
import android.view.View;
import android.widget.Button;
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

public class RepeatProjectAdapter extends CommonAdapter<RecordApproval> {
    private CloseListener listener;

    public RepeatProjectAdapter(Context context, List<RecordApproval> datas, int layoutId) {
        super(context,layoutId,datas);
    }

    @Override
    public void convert(ViewHolder holder, final RecordApproval recordApproval, final int position) {
        holder.setText(R.id.project_no,recordApproval.getProjectNo());
        holder.setText(R.id.project_name,recordApproval.getProjectName());
        holder.setText(R.id.customer_tv,recordApproval.getCustomerName());
        holder.setText(R.id.salesman_tv,recordApproval.getSalesmanUserName());
        holder.setText(R.id.branch_tv,recordApproval.getBranchName());

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

        Button close_btn = holder.getView(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.closed(recordApproval,position);
            }
        });
    }


    public void setListener(CloseListener listener){
        this.listener = listener;
    }
}

interface CloseListener{
    void closed(RecordApproval recordApproval, int position);
}
