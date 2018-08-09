package com.zxtech.is.ui.team.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.model.team.TeamAddMemberEla;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syp660 on 2018/4/19.
 */

public class AddTeamMemberElaAdapter extends BaseQuickAdapter<TeamAddMemberEla, BaseViewHolder> {

    private Map<String, Boolean> checkMap = new HashMap<>();

    public AddTeamMemberElaAdapter(int layoutResId, @Nullable List<TeamAddMemberEla> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, final TeamAddMemberEla item) {
        helper.setText(R.id.ela_team_add_member_content,item.getElaName());//电梯名
        if (item.getLeader() != null) {
            helper.setText(R.id.ela_team_add_member_leader,item.getLeader()); //组别名称
            helper.setVisible(R.id.ela_team_add_member_cb,true);

        } else {
            helper.setGone(R.id.ela_team_add_member_cb,false);

        }
        CheckBox cb_is_checkbox = helper.getView(R.id.ela_team_add_member_cb);
        //行选中
        cb_is_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    item.setCheck(true);
                } else {
                    item.setCheck(false);
                }
                checkMap.put(item.getElaGuid(), item.isCheck());
            }
        });


    }

    public int getSelectCount(){
        int i =0;
        for (boolean value : checkMap.values()) {
            if(value){
                i++;
            }
        }
        return i;
    }

}
