package com.zxtech.is.ui.taskquestion.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.BuildConfig;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.taskquestion.TaskQuestionPic;

import java.util.List;

/**
 * Created by hsz on 2018/5/2.
 */

public class TaskQuestionAssignAdapter extends BaseQuickAdapter<TaskQuestionPic, BaseViewHolder> {

    public TaskQuestionAssignAdapter(int layoutResId, @Nullable List<TaskQuestionPic> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskQuestionPic item) {
        helper.addOnClickListener(R.id.bg_img_id).addOnClickListener(R.id.close_img_id);
        String url = "";
        url = item.getFilePath();
        if (item.getPicguid() != null
                && !item.getPicguid().equals("")) {
            url = BuildConfig.BASE_URL + "s1/downloadAttach?guid=" + item.getPicguid();
        }
        Glide.with(mContext).load(url).into(((ImageView) helper.getView(R.id.bg_img_id)));
        if (item.getOpenMode() != null
                && !item.getOpenMode().equals("1")
                && !item.getOpenMode().equals("2")) {
            helper.getView(R.id.close_img_id).setVisibility(View.GONE);
        }
    }
}
