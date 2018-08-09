package com.zxtech.mt.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.entity.AccessoryGoods;
import com.zxtech.mt.entity.Icon;
import com.zxtech.mt.widget.FontView;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by syp523 on 2017/8/15.
 */

public class HomeIconAdapter extends BaseQuickAdapter<Icon, BaseViewHolder> {
    public HomeIconAdapter(List<Icon> list) {
        super(R.layout.item_home_icon, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Icon item) {
        helper.setText(R.id.name_textview, item.getText());
        helper.setTextColor(R.id.icon_home, mContext.getResources().getColor(item.getColor()));
        FontView icon = helper.getView(R.id.icon_home);

        icon.setText(item.getIcon());
    }


}
