package com.zxtech.gks.ui.cr;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.gks.model.vo.contract.FileData;

/**
 * Created by SYP521 on 2017/7/25.
 */

public class AttachmentAdapter extends BaseQuickAdapter<FileData,BaseViewHolder> {


    public AttachmentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileData item) {

        helper.setImageResource(R.id.iv,item.getDrawableId());
        helper.setText(R.id.tv,item.getFileName());
        helper.addOnClickListener(R.id.iv);
    }
}
