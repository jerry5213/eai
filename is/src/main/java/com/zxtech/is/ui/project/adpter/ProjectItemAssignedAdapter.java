package com.zxtech.is.ui.project.adpter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.project.ProductInformation;
import com.zxtech.is.ui.project.activity.ProjectItemAssignedActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syp661 on 2018/4/19.
 * <p>
 * 开工确认
 */

public class ProjectItemAssignedAdapter extends BaseQuickAdapter<ProductInformation, BaseViewHolder> {
    // 权限控制测试 begin
//    private Map<String, Object> securityMap = new HashMap<>();
//
//    public void setSecurityMapFun (Map<String, Object> map){
//        securityMap = map;
//    }
    // 权限控制测试 end

    private Map<Integer, Boolean> checkMap = new HashMap<>();

    private String checked;

    public ProjectItemAssignedAdapter(int layoutResId
            , @Nullable List<ProductInformation> productInformation) {
        super(layoutResId, productInformation);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ProductInformation item) {
        // 权限控制测试 begin
//        System.out.println("securityMap=" + securityMap);
//        if(securityMap.get("colFLCMIIRate").equals("1")){
//            helper.getView(R.id.lable1).setVisibility(View.GONE);
//        }
        // 权限控制测试 end

        helper.setText(R.id.is_elevator_id, item.getArktx())
                .addOnClickListener(R.id.lable1)
                .addOnClickListener(R.id.lable2)
                .addOnClickListener(R.id.lable3);
        if ("1".equals(checked)) {//任务口
            helper.setVisible(R.id.lable2, false)
                    .setVisible(R.id.lable3, false)
                    .setImageResource(R.id.image1_src, R.drawable.is_team_confirm_gray)//团队分配图
                    .setText(R.id.image1_text, R.string.is_unconfirmed)
                    .setTextColor(R.id.image1_text,
                            mContext.getResources().getColor(R.color.default_text_grey_color));
        } else if ("2".equals(checked)) {//项目口进
            //未开启流程
            if ("3".equals(item.getTaskCheck())) {//团队分配 未开启流程
                helper.setText(R.id.image1_text, R.string.is_unopened_process)
                        .setImageResource(R.id.image1_src, R.drawable.is_unopened_process)
                        .setTextColor(R.id.image1_text,
                                mContext.getResources().getColor(R.color.default_text_grey_color));
            } else if ("2".equals(item.getTaskCheck())) { //团队分配 已确认
                helper.setImageResource(R.id.image1_src, R.drawable.is_team_confirm_blue)
                        .setText(R.id.image1_text, R.string.is_confirmed)
                        .setTextColor(R.id.image1_text,
                                mContext.getResources().getColor(R.color.light_blue));
            } else if ("1".equals(item.getTaskCheck())) {//团队分配 未确认
                helper.setImageResource(R.id.image1_src, R.drawable.is_team_confirm_gray)
                        .setText(R.id.image1_text, R.string.is_unconfirmed)
                        .setTextColor(R.id.image1_text,
                                mContext.getResources().getColor(R.color.default_text_grey_color));
            }

            //安装方式报备 判断
            if (item.getSmtType() == 0) {
                helper.setImageResource(R.id.image3_src, R.drawable.is_iwr_grey)
                        .setText(R.id.image3_text, R.string.is_not_reported)
                        .setTextColor(R.id.image3_text,
                                mContext.getResources().getColor(R.color.default_text_grey_color));
            } else if (item.getSmtType() == 1) {
                helper.setImageResource(R.id.image3_src, R.drawable.is_iwr_yellow)
                        .setText(R.id.image3_text, R.string.is_application)
                        .setTextColor(R.id.image3_text,
                                mContext.getResources().getColor(R.color.yellow));
            } else if (item.getSmtType() == 2) {
                helper.setImageResource(R.id.image3_src, R.drawable.is_iwr_yellow)
                        .setText(R.id.image3_text, R.string.is_reported)
                        .setTextColor(R.id.image3_text,
                                mContext.getResources().getColor(R.color.yellow));
            } else if (item.getSmtType() == 3) {
                helper.setImageResource(R.id.image3_src, R.drawable.is_iwr_grey)
                        .setText(R.id.image3_text, R.string.is_not_pass)
                        .setTextColor(R.id.image3_text,
                                mContext.getResources().getColor(R.color.default_text_grey_color));
            } else if (item.getSmtType() == 4) {
                helper.setImageResource(R.id.image3_src, R.drawable.is_iwr_grey)
                        .setText(R.id.image3_text, R.string.is_obsolete)
                        .setTextColor(R.id.image3_text,
                                mContext.getResources().getColor(R.color.default_text_grey_color));
            }

            //班组分配 判断
            if (item.isSlTeam()) {//已分配
                helper.setImageResource(R.id.image2_src, R.drawable.is_citem_assigned_green)
                        .setText(R.id.image2_text, R.string.is_allocated)
                        .setTextColor(R.id.image2_text,
                                mContext.getResources().getColor(R.color.grass_green));
            } else {//未分配
                helper.setImageResource(R.id.image2_src, R.drawable.is_citem_assigned_grey)
                        .setText(R.id.image2_text, R.string.is_unallocated)
                        .setTextColor(R.id.image2_text,
                                mContext.getResources().getColor(R.color.default_text_grey_color));
            }
        }

        //checkBox 点击事件监听
        final CheckBox is_checkbox = helper.getView(R.id.is_checkbox);
        is_checkbox.setChecked(checkMap.get(helper.getAdapterPosition()));
        //行选中
        is_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkMap.put(helper.getAdapterPosition(), is_checkbox.isChecked());
                if (getSelectCount() == getData().size()) {
                    //多选框选中状态
                    ((ProjectItemAssignedActivity) mContext).set_is_checkall(true);
                } else {
                    ((ProjectItemAssignedActivity) mContext).set_is_checkall(false);
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