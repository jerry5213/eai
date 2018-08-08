package com.zxtech.ecs.ui.home.qmsmanager;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.QmsMaterialRequirement;

import java.util.List;

/**
 * Created by syp521 on 2018/3/29.
 */

public class QmsNotMrAdapter extends CommonAdapter<QmsMaterialRequirement> {

    public QmsNotMrAdapter(Context context, int layoutId, List<QmsMaterialRequirement> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, QmsMaterialRequirement qmsMr, final int position) {

        holder.setText(R.id.et_qms_piece_no, qmsMr.getM_JH());
        holder.setText(R.id.et_qms_name, qmsMr.getM_MC());
        holder.setText(R.id.tv_qms_question_code, qmsMr.getM_WTDM());
        String dw = qmsMr.getM_DW();
        holder.setText(R.id.et_qms_number, qmsMr.getM_SL() + dw);
        if (getDatas().size() - 1 == position) {
            holder.setVisible(R.id.tv_line, false);
        } else {
            holder.setVisible(R.id.tv_line, true);
        }
    }
}
