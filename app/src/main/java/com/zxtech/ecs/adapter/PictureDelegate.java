package com.zxtech.ecs.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ChatMessage;

/**
 * Created by syp523 on 2018/1/3.
 */

public class PictureDelegate implements ItemViewDelegate<ChatMessage> {
    private Context mContext;
    private SelectedListener listener;

    public PictureDelegate(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_qms_img;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        return item.getType() == ChatMessage.IMAGE;
    }

    @Override
    public void convert(ViewHolder holder, final ChatMessage ask, int position) {

        //holder.setText(R.id.iv_qms_img,ask.getContent());
    }

    public void setListener(SelectedListener listener) {
        this.listener = listener;
    }

    public interface SelectedListener {
        void getText(ChatMessage.Options recommend);
    }

}


