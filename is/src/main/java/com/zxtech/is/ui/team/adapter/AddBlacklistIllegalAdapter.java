package com.zxtech.is.ui.team.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.common.IsMstOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by duomi on 2018/4/4.
 */

public class AddBlacklistIllegalAdapter extends BaseQuickAdapter<IsMstOptions, BaseViewHolder> {

    private Map<String, Boolean> checkMap = new HashMap<>();

    public AddBlacklistIllegalAdapter(int layoutResId, @Nullable List<IsMstOptions> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final IsMstOptions item) {
        helper.setText(R.id.illegal_item_tv, item.getText());

        CheckBox cb_is_checkbox = helper.getView(R.id.illegal_item_cb);
        //行选中
        cb_is_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    item.setCheck(true);
                } else {
                    item.setCheck(false);
                }
                checkMap.put(item.getCode(), item.isCheck());
            }
        });

    }

    public int getSelectCount() {
        int i = 0;
        for (boolean value : checkMap.values()) {
            if (value) {
                i++;
            }
        }
        return i;
    }

}
