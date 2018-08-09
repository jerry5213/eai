package com.zxtech.is.ui.smt.adpter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.project.ProductInformation;
import com.zxtech.is.ui.project.activity.ProjectItemAssignedActivity;
import com.zxtech.is.ui.smt.activity.ProjectInstaWayRecordsApprovalActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syp661 on 2018/4/19.
 * <p>
 * 安装方式报备
 */

public class ProjectInstaWayRecordsApprovalAdapter extends BaseQuickAdapter<ProductInformation, BaseViewHolder> {

    private Map<Integer, Boolean> checkMap = new HashMap<>();
    private String checked;

    public ProjectInstaWayRecordsApprovalAdapter(int layoutResId
            , @Nullable List<ProductInformation> productInformation) {
        super(layoutResId, productInformation);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(final BaseViewHolder helper, final ProductInformation item) {
        String str = mContext.getString(R.string.is_insta_way) + ":" + item.getSmtinstallType() + "; "
                + mContext.getString(R.string.is_scaffolding) + ":";
        if ("0".equals(item.getScaffold())) {
            str += "  无";

        } else {
            str += "  有";
        }
        str += " \n" + mContext.getString(R.string.is_report_time) + ":   " + item.getSmtCreatTime()
                + "\n" + mContext.getString(R.string.is_report_name) + ":  " + item.getSmtCreatName();
        helper.setText(R.id.is_elevator_id, item.getArktx())
                .setText(R.id.is_insta_way, str);
        final CheckBox is_checkbox = helper.getView(R.id.is_checkbox);
        is_checkbox.setChecked(checkMap.get(helper.getAdapterPosition()));
        //行选中
        is_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkMap.put(helper.getAdapterPosition(), is_checkbox.isChecked());
                if (getSelectCount() == getData().size()) {
                    //多选框选中状态
                    ((ProjectInstaWayRecordsApprovalActivity) mContext).set_is_checkall(true);
                } else {
                    ((ProjectInstaWayRecordsApprovalActivity) mContext).set_is_checkall(false);
                }
            }
        });
    }

    public void selectAll(boolean flag) {
        for (int a = 0; a < getData().size(); a++) {
            checkMap.put(a, flag);
        }
    }

    //获取选中条数
    public int getSelectCount() {
        int i = 0;
        for (boolean value : checkMap.values()) {
            if (value) {
                i++;
            }
        }
        return i;
    }

    public Map<Integer, Boolean> getCheckMap() {
        return checkMap;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}