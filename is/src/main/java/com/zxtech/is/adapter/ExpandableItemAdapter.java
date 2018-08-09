package com.zxtech.is.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.Programme;
import com.zxtech.is.model.Scheme;

import java.util.List;

/**
 * Created by syp523 on 2018/3/9.
 */

public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_scheme_dimension);
        addItemType(TYPE_LEVEL_1, R.layout.item_scheme_params);
    }


    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:

//                final TestParent lv0 = (TestParent) item;
//                holder.setText(R.id.title, lv0.name);

                break;
            case TYPE_LEVEL_1:
                final Scheme.ParamsBeans lv1 = (Scheme.ParamsBeans) item;
                List<Programme.DimensionsBean.ParamsBean> paramsBeans = lv1.getParamsBeans();
                holder.setText(R.id.title1, paramsBeans.get(0).getValue()).addOnClickListener(R.id.title1);
                holder.setText(R.id.title2, paramsBeans.get(1).getValue()).addOnClickListener(R.id.title2);
                if (paramsBeans.size() > 2) {
                    holder.setVisible(R.id.title3, true);
                    holder.setText(R.id.title3, paramsBeans.get(2).getValue()).addOnClickListener(R.id.title3);
                } else {
                    holder.setGone(R.id.title3, false);
                    holder.setText(R.id.title3, "");
                }

                if (lv1.isDifferent()) {
                    holder.setBackgroundColor(R.id.title1, Color.parseColor("#fcefed"));
                    holder.setBackgroundColor(R.id.title2, Color.parseColor("#fcefed"));
                    holder.setBackgroundColor(R.id.title3, Color.parseColor("#fcefed"));
                } else {
                    holder.setBackgroundColor(R.id.title1, Color.parseColor("#ffffff"));
                    holder.setBackgroundColor(R.id.title2, Color.parseColor("#ffffff"));
                    holder.setBackgroundColor(R.id.title3, Color.parseColor("#ffffff"));
                }

//                holder.setText(R.id.title, lv1.getValue()).addOnClickListener(R.id.title);
//                if (lv1.isDifferent()) {
//                    holder.setBackgroundColor(R.id.title, Color.parseColor("#fcefed"));
//                }else{
//                    holder.setBackgroundColor(R.id.title, Color.parseColor("#ffffff"));
//                }
                // holder.itemView.setBackgroundResource(R.drawable.main_border);
                break;

        }
    }
}
