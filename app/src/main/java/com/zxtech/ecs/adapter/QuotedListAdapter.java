package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.PriceApproval;
import com.zxtech.ecs.ui.home.quote.QuotedFragment;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.common.SPUtils;
import com.zxtech.gks.ui.cr.ContractReviewActivity;

import java.util.List;

/**
 * Created by syp521 on 2017/10/26.
 */

public class QuotedListAdapter extends BaseQuickAdapter<PriceApproval, BaseViewHolder> {


    public QuotedListAdapter(int layoutResId, @Nullable List<PriceApproval> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, PriceApproval item) {
        holder.setText(R.id.process_tv, item.getProcess());
        holder.setText(R.id.project_no_tv, item.getProjectNo());
        holder.setText(R.id.project_tv, item.getProjectName());
        holder.setText(R.id.sale_user_tv, item.getSalesmanUserName());
        holder.setText(R.id.customer_tv, item.getCustomerName());
        holder.setText(R.id.create_date_tv, item.getCreateDate());
        holder.setText(R.id.company_tv, item.getBranchName());



        ImageView file_download_iv = holder.getView(R.id.file_download_iv);
        if (item.showDownload()) {
            file_download_iv.setVisibility(View.VISIBLE);
            if (existsFile(item.getGuid())) {
                file_download_iv.setImageResource(R.drawable.file_downloaded);
            } else {
                file_download_iv.setImageResource(R.drawable.file_download);
            }
            holder.addOnClickListener(R.id.file_download_iv);
        } else {
            file_download_iv.setVisibility(View.GONE);
        }
    }

    private boolean existsFile(String fileId) {
        return (boolean) SPUtils.get(mContext, QuotedFragment.FILE_FLAG + fileId, false);
    }


}
