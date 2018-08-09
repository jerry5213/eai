package com.zxtech.is.ui.person.adpter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.model.project.Project;

import java.util.List;

/**
 * Created by duomi on 2018/4/4.
 */

public class PersonCheckProjectAdpter extends BaseQuickAdapter<Project, BaseViewHolder> {

    public PersonCheckProjectAdpter(int layoutResId, @Nullable List<Project> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Project item) {
        helper.setText(R.id.is_item_team_2, item.getProjectName());
        helper.addOnClickListener(R.id.is_item_team_1);


    }




}
