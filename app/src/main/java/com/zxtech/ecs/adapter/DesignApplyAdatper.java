package com.zxtech.ecs.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.DesignApply;
import com.zxtech.ecs.model.Engineering;
import com.zxtech.ecs.ui.home.designapply.DesignApplyActivity;
import com.zxtech.ecs.ui.home.engineering.EngineeringActivity;
import com.zxtech.gks.common.SPUtils;

import java.util.List;

/**
 * Created by syp523 on 2018/3/31.
 */

public class DesignApplyAdatper extends BaseQuickAdapter<DesignApply,BaseViewHolder> {
    public DesignApplyAdatper(int layoutResId, @Nullable List<DesignApply> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DesignApply item) {
        helper.setText(R.id.process_tv, item.getInstanceNodeName());
        helper.setText(R.id.project_tv, item.getProjectName());
        helper.setText(R.id.spec_tv, item.getElevatorProduct());
        helper.setText(R.id.create_date_tv, item.getCreateDate());
        helper.setText(R.id.draw_no_tv, item.getDS_ProductNo());
        helper.setText(R.id.sale_user_tv, item.getCreateUserName());
        helper.setText(R.id.compant_tv, item.getBranchName());
        helper.setText(R.id.elevator_tv, item.getElevatorNo());
        helper.setText(R.id.product_no_tv, item.getStrEqsProductNo());
        helper.setText(R.id.elevator_type_tv, item.getElevatorType());

        ImageView file_download_iv = helper.getView(R.id.file_download_iv);
        if (item.showDownload()) {
            file_download_iv.setVisibility(View.VISIBLE);
            helper.addOnClickListener(R.id.file_download_iv);
        }else {
            file_download_iv.setVisibility(View.GONE);
        }
    }

    private boolean existsFile(String fileId){
        return (boolean) SPUtils.get(mContext, DesignApplyActivity.FILE_FLAG+fileId,false);
    }

}
