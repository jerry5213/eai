package com.zxtech.gks.ui.pa.sale;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zxtech.ecs.R;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.vo.PrProduct;

import java.util.List;

/**
 * Created by syp521 on 2017/10/26.
 */

public class WaitPPAListAdapter extends CommonAdapter<PrProduct> {

    private Context mContext;

    public WaitPPAListAdapter(Context context, int layoutId, List<PrProduct> datas) {
        super(context, layoutId, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, PrProduct prProduct, final int position) {

        holder.setText(R.id.project_no,prProduct.getProjectNo());
        holder.setText(R.id.project_name,prProduct.getProjectName());
        holder.setText(R.id.salesman_name,prProduct.getSalesmanUserName());
        holder.setText(R.id.branch_name,prProduct.getBranchName());
        holder.setText(R.id.create_date,prProduct.getCreateDate());

        TextView tv_project_type = holder.getView(R.id.tv_project_type);
        ImageView submit = holder.getView(R.id.submit);
        LinearLayout list_item = holder.getView(R.id.list_item);

        String type = prProduct.getProjectType();
        GradientDrawable myGrad = (GradientDrawable)tv_project_type.getBackground();
        if(Constants.PROJ_TYPE_XSDXM.equals(type)){
            myGrad.setColor(Color.parseColor("#ff33b5e5"));
            tv_project_type.setText("大项目");
        }else if(Constants.PROJ_TYPE_PT.equals(type)){
            myGrad.setColor(Color.parseColor("#ff99cc00"));
            tv_project_type.setText(mContext.getString(R.string.standard_project));
        }else {
            myGrad.setColor(Color.parseColor("#33CCCCCC"));
            tv_project_type.setText("其它");
        }

        if(position % 2 == 0){
            list_item.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            list_item.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WaitPPAListActivity)mContext).savePriceReview(position);
            }
        });
    }
}
