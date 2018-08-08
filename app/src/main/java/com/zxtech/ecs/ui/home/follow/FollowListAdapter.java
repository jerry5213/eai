package com.zxtech.ecs.ui.home.follow;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;

import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.vo.PrProduct;

import java.util.List;

/**
 * Created by syp521 on 2017/10/26.
 */

public class FollowListAdapter extends BaseQuickAdapter<PrProduct, BaseViewHolder> {

    public FollowListAdapter(int layoutResId, @Nullable List<PrProduct> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, PrProduct item) {
        helper.setText(R.id.project_no, item.getProjectNo());
        helper.setText(R.id.project_name, item.getProjectName());
        helper.setText(R.id.salesman_name, item.getSalesmanUserName());
        helper.setText(R.id.branch_name, item.getBranchName());
        helper.setText(R.id.create_date, item.getCreateTime());
        helper.setText(R.id.work_flow, item.getGetWorkFlowNodeNameStr());
        helper.setText(R.id.tv_contact_status, item.getContactState());

        String type = item.getProjectType();
        TextView tv_project_type = helper.getView(R.id.tv_project_type);
        GradientDrawable myGrad = (GradientDrawable) tv_project_type.getBackground();
        if (Constants.PROJ_TYPE_XSDXM.equals(type) || Constants.PROJ_TYPE_DXM.equals(type)) {
            myGrad.setColor(Color.parseColor("#ff33b5e5"));
            tv_project_type.setText("大项目");
        } else if (Constants.PROJ_TYPE_PT.equals(type)) {
            myGrad.setColor(Color.parseColor("#ff99cc00"));
            tv_project_type.setText(mContext.getString(R.string.standard_project));
        } else {
            myGrad.setColor(Color.parseColor("#33CCCCCC"));
            tv_project_type.setText("其它");
        }

        LinearLayout list_item = helper.getView(R.id.list_item);
        if (helper.getAdapterPosition() % 2 == 0) {
            list_item.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            list_item.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }
    }


}
