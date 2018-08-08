package com.zxtech.ecs.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.util.ColorTemplate;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.SpiderWeb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syp523 on 2018/1/22.
 */

public class CompareAdapter extends CommonAdapter<Programme> {
    Point screenSize = null;
    int height = 0;
    private CompareCallBack callBack;
    private List<Integer> selectedPositions = new ArrayList<>();
    private List<Integer> isCollections = new ArrayList<>();

    public CompareAdapter(Context context, int layoutId, List<Programme> datas) {
        super(context, layoutId, datas);
        screenSize = DensityUtil.getScreenSize(context);
        height = (screenSize.y - DensityUtil.dip2px(mContext, 126)) / 3;
    }

    @Override
    protected void convert(ViewHolder holder, Programme programme, final int position) {
        RelativeLayout group_layout = holder.getView(R.id.group_layout);
        group_layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
        holder.setText(R.id.price_tv, mContext.getString(R.string.price) + Util.numberFormat(programme.getPrice()));
        final TextView scheme_plan_tv = holder.getView(R.id.scheme_plan_tv);
        scheme_plan_tv.setText(mContext.getString(R.string.proposal) + " " + (position + 1));

        SpiderWeb ssw = holder.getView(R.id.ssw);
        ssw.setLayoutParams(new LinearLayout.LayoutParams(height, height));
        int[] parms = new int[]{programme.getDimensionValue(Constants.DIMEN_SSD) / 2, programme.getDimensionValue(Constants.DIMEN_JNDJ) / 2, programme.getDimensionValue(Constants.DIMEN_MGD) / 2, programme.getDimensionValue(Constants.DIMEN_CZFW) / 2, programme.getDimensionValue(Constants.DIMEN_AQDJ) / 2};
        ssw.setAreaLineColor(ColorTemplate.DIMENSION_COLORS[position]);

        ssw.setData(5, parms, mContext.getResources().getStringArray(R.array.dimension_abb));


        final ImageView selected_btn = holder.getView(R.id.selected_btn);
        if (selectedPositions.contains(position)) {
            selected_btn.setImageResource(R.drawable.multiselect_check);
        } else {
            selected_btn.setImageResource(R.drawable.multiselect);
        }

        scheme_plan_tv.setBackground(ColorTemplate.getDimensionBg(position, true));
        scheme_plan_tv.setTextColor(mContext.getResources().getColor(R.color.white));


        ImageView collection_btn = holder.getView(R.id.collection_btn);
        if (isCollections.contains(position)) {
            collection_btn.setVisibility(View.VISIBLE);
        } else {
            collection_btn.setVisibility(View.INVISIBLE);
        }



        TextView dimen1_tv = holder.getView(R.id.dimen1_tv);
        TextView dimen2_tv = holder.getView(R.id.dimen2_tv);
        TextView dimen3_tv = holder.getView(R.id.dimen3_tv);
        TextView dimen4_tv = holder.getView(R.id.dimen4_tv);
        TextView dimen5_tv = holder.getView(R.id.dimen5_tv);
        if (programme.getDimensionValue(Constants.DIMEN_SSD) >= 8) {
            dimen1_tv.setBackground(ColorTemplate.getDimensionBg(position, true));
            dimen1_tv.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            dimen1_tv.setBackground(ColorTemplate.getDimensionBg(position, false));
            dimen1_tv.setTextColor(mContext.getResources().getColor(R.color.default_text_black_color));
        }
        if (programme.getDimensionValue(Constants.DIMEN_JNDJ) >= 8) {
            dimen2_tv.setBackground(ColorTemplate.getDimensionBg(position, true));
            dimen2_tv.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            dimen2_tv.setBackground(ColorTemplate.getDimensionBg(position, false));
            dimen2_tv.setTextColor(mContext.getResources().getColor(R.color.default_text_black_color));
        }
        if (programme.getDimensionValue(Constants.DIMEN_MGD) >= 8) {
            dimen3_tv.setBackground(ColorTemplate.getDimensionBg(position, true));
            dimen3_tv.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            dimen3_tv.setBackground(ColorTemplate.getDimensionBg(position, false));
            dimen3_tv.setTextColor(mContext.getResources().getColor(R.color.default_text_black_color));
        }
        if (programme.getDimensionValue(Constants.DIMEN_CZFW) >= 8) {
            dimen4_tv.setBackground(ColorTemplate.getDimensionBg(position, true));
            dimen4_tv.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            dimen4_tv.setBackground(ColorTemplate.getDimensionBg(position, false));
            dimen4_tv.setTextColor(mContext.getResources().getColor(R.color.default_text_black_color));
        }
        if (programme.getDimensionValue(Constants.DIMEN_AQDJ) >= 8) {
            dimen5_tv.setBackground(ColorTemplate.getDimensionBg(position, true));
            dimen5_tv.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            dimen5_tv.setBackground(ColorTemplate.getDimensionBg(position, false));
            dimen5_tv.setTextColor(mContext.getResources().getColor(R.color.default_text_black_color));
        }
    }


    public List<Integer> getSelectedPositions() {
        return selectedPositions;
    }

    public List<Integer> getIsCollections() {
        return isCollections;
    }

    public void setIsCollections(List<Integer> isCollections) {
        this.isCollections = isCollections;
    }

    public void setListener(CompareCallBack callBack) {
        this.callBack = callBack;
    }


    public interface CompareCallBack {
        void collection(int position);

        void compare(int position);
    }
}
