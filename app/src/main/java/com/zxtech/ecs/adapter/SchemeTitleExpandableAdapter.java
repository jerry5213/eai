package com.zxtech.ecs.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.util.SPUtils;

import java.util.List;
import java.util.Locale;

/**
 * Created by syp523 on 2018/3/9.
 */

public class SchemeTitleExpandableAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = SchemeTitleExpandableAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private String language = "zh";
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SchemeTitleExpandableAdapter(List<MultiItemEntity> data,String language) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_scheme_title_dimension);
        addItemType(TYPE_LEVEL_1, R.layout.item_scheme_title_params);
        this.language = language;
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
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int pos = holder.getAdapterPosition();
//                        Log.d(TAG, "Level 0 item pos: " + pos);
//                        if (lv0.isExpanded()) {
//                            collapse(pos);
//                        } else {
////                            if (pos % 3 == 0) {
////                                expandAll(pos, false);
////                            } else {
//                            expand(pos);
////                            }
//                        }
//                    }
//                });
                break;
            case TYPE_LEVEL_1:
                final Programme.DimensionsBean.ParamsBean paramsBean = (Programme.DimensionsBean.ParamsBean) item;
                holder.setText(R.id.title, paramsBean.getName(language));
                if (paramsBean.isHasDescription()){
                    holder.setImageResource(R.id.tip_iv,R.drawable.upper_right_corner);
                }else{
                    holder.setImageResource(R.id.tip_iv,0);
                }
                break;

        }
    }
}
