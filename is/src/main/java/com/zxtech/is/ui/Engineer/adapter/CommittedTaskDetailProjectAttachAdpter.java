package com.zxtech.is.ui.Engineer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.BuildConfig;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.attach.Attach;

import java.util.List;

/**
 * Created by syp600 on 2018/4/20.
 */

public class CommittedTaskDetailProjectAttachAdpter extends BaseQuickAdapter<Attach, BaseViewHolder> {

    public CommittedTaskDetailProjectAttachAdpter(int layoutResId, @Nullable List<Attach> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Attach item) {
        String guid = item.getAttachguid();
        String url = BuildConfig.BASE_URL + "s1/downloadAttach?guid=" + guid;
        String fileType = item.getFiletype();
        switch (fileType) {
            case "doc":
                break;
            case "xlsx":
                break;
            case "txt":
                break;
            case "pdf":
                break;
            case "jpg":
            case "png":
                Glide.with(mContext).load(url).into(((ImageView) helper.getView(R.id.common_img_iv)));
                break;
        }
//        ImageView common_img_close = helper.getView(R.id.common_img_close);
//        common_img_close.setOnClickListener();
    }
}
