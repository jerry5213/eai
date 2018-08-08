package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.Engineering;
import com.zxtech.ecs.ui.home.engineering.EngineeringActivity;
import com.zxtech.gks.common.SPUtils;

import java.util.List;

/**
 * Created by syp523 on 2018/3/31.
 */

public class EngineeringAdatper extends BaseQuickAdapter<Engineering,BaseViewHolder> {
    public EngineeringAdatper(int layoutResId, @Nullable List<Engineering> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Engineering item) {
        helper.setText(R.id.process_tv, item.getInstanceNodeName());
        helper.setText(R.id.project_tv, item.getProjectName());
        helper.setText(R.id.spec_tv, item.getStrSpecification());
        helper.setText(R.id.create_date_tv, item.getCreateDate());
        helper.setText(R.id.draw_no_tv, item.getDrawingNo());
        helper.setText(R.id.sale_user_tv, item.getSalesmanUserName());
        helper.setText(R.id.compant_tv, item.getBranchName());
        helper.setText(R.id.elevator_tv, item.getElevatorNoList());
        helper.setText(R.id.elevator_number_tv, item.getElevatorNumber());
        helper.setText(R.id.draw_state_tv, item.getDrawingState());

        ImageView file_download_iv = helper.getView(R.id.file_download_iv);
        if (item.showDownload()) {
            file_download_iv.setVisibility(View.VISIBLE);
            if (existsFile(item.getGuid())) {
                file_download_iv.setImageResource(R.drawable.file_downloaded);
            }else{
                file_download_iv.setImageResource(R.drawable.file_download);
            }
            helper.addOnClickListener(R.id.file_download_iv);
        }else {
            file_download_iv.setVisibility(View.GONE);
        }
    }

    private boolean existsFile(String fileId){
        return (boolean) SPUtils.get(mContext, EngineeringActivity.FILE_FLAG+fileId,false);
    }

}
