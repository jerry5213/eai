package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.ui.home.contract.ApplyFragment;
import com.zxtech.gks.common.SPUtils;
import com.zxtech.gks.model.vo.contract.ContractData;

import java.util.List;

/**
 * Created by SYP521 on 2017/12/13.
 */

public class ContractApplyAdapter extends BaseQuickAdapter<ContractData,BaseViewHolder> {

    public ContractApplyAdapter(int layoutResId, @Nullable List<ContractData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ContractData item) {
        holder.setText(R.id.process_tv,item.getProcess());
        holder.setText(R.id.project_no,item.getProjectNo());
        holder.setText(R.id.project_name,item.getProjectName());
        holder.setText(R.id.salesman_name,item.getSalesmanUserName());
        holder.setText(R.id.customer_name,item.getCustomerName());
        holder.setText(R.id.create_date,item.getCreateTime());


        ImageView file_download_iv = holder.getView(R.id.file_download_iv);
        if (item.showDownload()) {
            file_download_iv.setVisibility(View.VISIBLE);
            if (existsFile(item.getContractGuid())) {
                file_download_iv.setImageResource(R.drawable.file_downloaded);
            }else{
                file_download_iv.setImageResource(R.drawable.file_download);
            }
            holder.addOnClickListener(R.id.file_download_iv);
        }else {
            file_download_iv.setVisibility(View.GONE);
        }
        holder.addOnClickListener(R.id.review_iv).addOnClickListener(R.id.sync_iv);
    }

    private boolean existsFile(String fileId){
        return (boolean) SPUtils.get(mContext, ApplyFragment.FILE_FLAG+fileId+"1",false);
    }
}
