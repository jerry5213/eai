package com.zxtech.gks.ui.pa;

import android.content.Context;
import android.support.annotation.Nullable;
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

public class ProjectPriceApprovalListAdapter extends BaseQuickAdapter<PrProduct,BaseViewHolder> {

    private String path = "";

    public ProjectPriceApprovalListAdapter(Context context,int layoutResId, @Nullable List<PrProduct> data) {
        super(layoutResId, data);
        path = context.getFilesDir().getAbsolutePath();
    }

        @Override
        protected void convert(BaseViewHolder holder, PrProduct prProduct) {
            holder.setText(R.id.process_tv,prProduct.getProcess());
            holder.setText(R.id.project_no,prProduct.getProjectNo());
            holder.setText(R.id.project_name,prProduct.getProjectName());
            holder.setText(R.id.salesman_name,prProduct.getSalesmanUserName());
            holder.setText(R.id.branch_name,prProduct.getBranchName());
            holder.setText(R.id.create_date,prProduct.getCreateDate());


            TextView tv_project_type = holder.getView(R.id.tv_project_type);

            String type = prProduct.getProjectType();
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

    }




}
