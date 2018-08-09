package com.zxtech.is.ui.task.adpter;

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

public class TeamTaskAdpter extends BaseQuickAdapter<Project, BaseViewHolder> {

    public TeamTaskAdpter(int layoutResId, @Nullable List<Project> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Project item) {
        helper.setText(R.id.tv_project_name, item.getProjectName()).setText(R.id.tv_address, item.getAddress());
    }
}
