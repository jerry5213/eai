package com.zxtech.is.ui.team.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.team.Slinstallationunit;

import java.util.List;

/**
 * Created by duomi on 2018/4/4.
 */

public class SlinstallationunitAdpter extends BaseQuickAdapter<Slinstallationunit, BaseViewHolder> {


    public SlinstallationunitAdpter(int layoutResId, @Nullable List<Slinstallationunit> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Slinstallationunit item) {
        helper.setText(R.id.is_item_team_2, item.getName());
    }


}
