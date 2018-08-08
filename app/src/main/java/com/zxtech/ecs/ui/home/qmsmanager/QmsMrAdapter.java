package com.zxtech.ecs.ui.home.qmsmanager;

import android.content.Context;
import android.view.View;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.QmsMaterialRequirement;

import java.util.List;

/**
 * Created by syp521 on 2018/3/29.
 */

public class QmsMrAdapter extends CommonAdapter<QmsMaterialRequirement>{

    public QmsMrAdapter(Context context, int layoutId, List<QmsMaterialRequirement> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, QmsMaterialRequirement qmsMr, final int position) {

        holder.setText(R.id.et_qms_piece_no,qmsMr.getM_JH());
        holder.setText(R.id.et_qms_name,qmsMr.getM_MC());
        holder.setText(R.id.tv_qms_question_code,qmsMr.getM_WTDMValue());
        String dw = qmsMr.getM_DWValue();
        holder.setText(R.id.et_qms_number,qmsMr.getM_SL()+dw);

        final SwipeMenuLayout layout = holder.getView(R.id.layout);

        holder.setOnClickListener(R.id.delete_btn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.quickClose();
                mDatas.remove(position);
                notifyDataSetChanged();
            }
        });
    }
}
