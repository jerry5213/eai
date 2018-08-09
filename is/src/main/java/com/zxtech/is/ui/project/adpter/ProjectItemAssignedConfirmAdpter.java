package com.zxtech.is.ui.project.adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.project.Project;

import java.util.List;

/**
 * Created by duomi on 2018/4/4.
 */

public class ProjectItemAssignedConfirmAdpter extends BaseQuickAdapter<Project, BaseViewHolder> {

    public ProjectItemAssignedConfirmAdpter(int layoutResId, @Nullable List<Project> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Project item) {
        helper.setText(R.id.project_name, item.getProjectName()).setText(R.id.project_address, item.getAddress()).setText(R.id.customer_tv, item.getCustomerName());
    }
}
