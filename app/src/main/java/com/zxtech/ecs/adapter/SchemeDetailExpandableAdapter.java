package com.zxtech.ecs.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.Programme;

import java.util.List;

/**
 * Created by syp523 on 2018/3/9.
 */

public class SchemeDetailExpandableAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = SchemeDetailExpandableAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SchemeDetailExpandableAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_scheme_detail_dimension);
        addItemType(TYPE_LEVEL_1, R.layout.item_scheme_detail_params);
    }


    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:

                final Programme.DimensionsBean parent = (Programme.DimensionsBean) item;
                holder.setText(R.id.title, parent.getName());

                if (parent.isExpanded()){
                    holder.setImageResource(R.id.expand_iv, R.drawable.close);
                }else{
                    holder.setImageResource(R.id.expand_iv, R.drawable.open);
                }

                break;
            case TYPE_LEVEL_1:
                final Programme.DimensionsBean.ParamsBean paramsBean = (Programme.DimensionsBean.ParamsBean) item;
                holder.setText(R.id.title, paramsBean.getName());
                holder.setText(R.id.value_tv, paramsBean.getValue());
                break;

        }
    }
}
