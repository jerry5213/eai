package com.zxtech.is.ui.task.adpter;

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

public class ElevatorAttachAdpter extends BaseQuickAdapter<Attach, BaseViewHolder> {

    public ElevatorAttachAdpter(int layoutResId, @Nullable List<Attach> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Attach item) {
        helper.addOnClickListener(R.id.common_img_close);
        String url = item.getFilepath();
        if (item.getAttachguid() != null) {
            url = BuildConfig.BASE_URL + "s1/downloadAttach?guid=" + item.getAttachguid();
        }
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
    }
}
