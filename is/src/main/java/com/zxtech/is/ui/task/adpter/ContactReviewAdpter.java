package com.zxtech.is.ui.task.adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.s1.ElevatorContact;

import java.util.List;

/**
 * Created by syp600 on 2018/5/4.
 */

public class ContactReviewAdpter extends BaseQuickAdapter<ElevatorContact, BaseViewHolder> {

    public ContactReviewAdpter(int layoutResId, @Nullable List<ElevatorContact> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ElevatorContact item) {
        helper.
                setText(R.id.et_is_contact_name, item.getName()).
                setText(R.id.et_is_contact_telephone, item.getTelephone()).
                setText(R.id.et_is_contact_post, item.getPost());
    }

}
