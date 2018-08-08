package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.BidAttachment;
import com.zxtech.ecs.util.FileUtil;

import java.util.List;

/**
 * Created by syp523 on 2018/7/24.
 */

public class BidAttachmentAdapter extends BaseQuickAdapter<BidAttachment, BaseViewHolder> {
    private boolean isEdit;
    public BidAttachmentAdapter(int layoutResId, @Nullable List<BidAttachment> data,boolean isEdit) {
        super(layoutResId, data);
        this.isEdit = isEdit;
    }

    @Override
    protected void convert(BaseViewHolder helper, BidAttachment item) {
        helper.setText(R.id.filename_tv,item.getFileName());
        helper.setText(R.id.user_tv,item.getCreateUserName());
        helper.setText(R.id.time_tv,item.getCreateTime());
        helper.setText(R.id.download_tv, FileUtil.fileType(item.getFileName())==FileUtil.PICTURE ? "查看图片":"下载附件");
        helper.setGone(R.id.delete_tv,isEdit);
        helper.addOnClickListener(R.id.download_tv).addOnClickListener(R.id.delete_tv);
    }
}
