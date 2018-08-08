package com.zxtech.ecs.adapter;

import android.graphics.Point;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.util.ColorTemplate;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.SpiderWeb;

import java.util.List;

/**
 * Created by syp523 on 2018/3/14.
 */

public class SchemeDimensionAdapter extends BaseQuickAdapter<Programme, BaseViewHolder> {
    private int itemWidth = 0;
    private List<String> schemeNum;

    public SchemeDimensionAdapter(int layoutResId, @Nullable List<Programme> data, int itemWidth, List<String> schemeNum) {
        super(layoutResId, data);
        this.itemWidth = itemWidth;
        this.schemeNum = schemeNum;
    }

    @Override
    protected void convert(BaseViewHolder helper, Programme item) {
        helper.itemView.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
        int realNum = 0;
        if (schemeNum != null) {
            realNum = Integer.valueOf(schemeNum.get(helper.getAdapterPosition()));
        } else {
            realNum = (helper.getAdapterPosition());
        }

        TextView title1 = helper.getView(R.id.title1);
        title1.setText(mContext.getString(R.string.proposal) + " " + (realNum + 1));
        helper.setText(R.id.title2, "¥"+Util.numberFormat(item.getPrice()) + "");
        helper.setText(R.id.title3, mContext.getString(R.string.nine_day));
        int[] params = new int[]{item.getDimensionValue(Constants.DIMEN_SSD) / 2, item.getDimensionValue(Constants.DIMEN_JNDJ) / 2, item.getDimensionValue(Constants.DIMEN_MGD) / 2, item.getDimensionValue(Constants.DIMEN_CZFW) / 2, item.getDimensionValue(Constants.DIMEN_AQDJ) / 2};
        SpiderWeb sw = helper.getView(R.id.sw);
        sw.setAreaLineColor(ColorTemplate.DIMENSION_COLORS[realNum]);
        sw.setData(5, params, mContext.getResources().getStringArray(R.array.dimension_abb));

        helper.addOnClickListener(R.id.sw);
        helper.addOnClickListener(R.id.reduce_layout);
        helper.addOnClickListener(R.id.look_tv);
    }

}
