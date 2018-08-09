package com.zxtech.is.ui.task.adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.s1.ElevatorContact;

import java.util.List;

/**
 * Created by syp600 on 2018/4/27.
 */

public class ContactAdpter extends BaseQuickAdapter<ElevatorContact, BaseViewHolder> {

    public ContactAdpter(int layoutResId, @Nullable List<ElevatorContact> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ElevatorContact item) {
        helper.
                setChecked(R.id.cb_is_checkbox, item.isCheck()).
                setText(R.id.et_is_contact_name, item.getName()).
                setText(R.id.et_is_contact_telephone, item.getTelephone()).
                setText(R.id.et_is_contact_post, item.getPost());
    }

}
