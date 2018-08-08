package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.QmsMyFeedBackEntity;

import java.util.List;

/**
 * @auth: hexl
 * @date: 2018/2/28
 * @desc:
 */

public class QmsMyFeedBackAdapter extends BaseQuickAdapter<QmsMyFeedBackEntity.FeedbackListBean, BaseViewHolder> {


    public QmsMyFeedBackAdapter(int layoutResId, @Nullable List<QmsMyFeedBackEntity.FeedbackListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QmsMyFeedBackEntity.FeedbackListBean item) {

        helper.setText(R.id.tv_qms_order_no, item.getJobNumber())
                .setText(R.id.tv_qms_content, item.getW_WTMS())
                .setText(R.id.time, item.getCreateDate())
                .setText(R.id.tv_qms_contract_no, item.getC_ContractNo())
                .setText(R.id.tv_qms_project_name, item.getC_XMMC())
                .setText(R.id.tv_qms_support_type, item.getW_ZCLX())
                .setText(R.id.status, convertStatus(item.getTaskStatus()))
                .addOnClickListener(R.id.tv_qms_evaluate)
                .addOnClickListener(R.id.tv_qms_logistics)
                .addOnClickListener(R.id.tv_qms_look);

        if(TextUtils.isEmpty(item.getJobNumber())){
            helper.setVisible(R.id.tv_qms_logistics, false);
        }else{
            helper.setVisible(R.id.tv_qms_logistics, true);
        }

        if(item.getTaskStatus()>=4 && !item.isPJ()){
            helper.setVisible(R.id.tv_qms_evaluate, true);
        }else {
            helper.setVisible(R.id.tv_qms_evaluate, false);
        }

    }

    private String convertStatus(int status) {

        switch (status) {
            case 0:
                return "待反馈";
            case 1:
                return "已反馈";
            case 2:
                return "处理反馈";
            case 3:
                return "经理审批";
            case 4:
                return "物料发运";
            case 5:
                return "完成";
            default:
                return "";
        }
    }
}
