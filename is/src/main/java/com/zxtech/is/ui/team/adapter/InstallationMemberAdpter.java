package com.zxtech.is.ui.team.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.project.Project;
import com.zxtech.is.model.team.IsSlInstallationmember;
import com.zxtech.is.ui.task.activity.S1TaskListActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 692 on 2018/4/4.
 */

public class InstallationMemberAdpter extends BaseQuickAdapter<IsSlInstallationmember, BaseViewHolder> {
    private Map<String, Boolean> checkMap = new HashMap<>();

    public InstallationMemberAdpter(int layoutResId, @Nullable List<IsSlInstallationmember> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final IsSlInstallationmember item) {
        helper.setText(R.id.is_item_team_2, item.getName()).setText(R.id.is_item_team_3, item.getUnitName()).setChecked(R.id.is_item_team_1, item.isCheck());
        final CheckBox cb_is_checkbox = helper.getView(R.id.is_item_team_1);
        //如果是leader 则必须选中
        if (item.isLeader()) {
            cb_is_checkbox.setChecked(true);
            cb_is_checkbox.setClickable(false);
            item.setCheck(true);
        }
        //行选中
        cb_is_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    item.setCheck(true);
                } else {
                    item.setCheck(false);
                }


            }
        });
    }
}
