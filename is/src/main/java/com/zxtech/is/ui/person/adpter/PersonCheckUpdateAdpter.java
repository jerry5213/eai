package com.zxtech.is.ui.person.adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.model.common.IsMstOptions;

import java.util.List;

/**
 * Created by duomi on 2018/4/4.
 */

public class PersonCheckUpdateAdpter extends BaseQuickAdapter<IsMstOptions, BaseViewHolder> {

    public PersonCheckUpdateAdpter(int layoutResId, @Nullable List<IsMstOptions> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final IsMstOptions item) {
        helper.setText(R.id.is_item_team_2, item.getText());
        helper.addOnClickListener(R.id.is_item_team_1);


    }




}
