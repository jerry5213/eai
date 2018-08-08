package com.zxtech.ecs.adapter;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ChatMessage;

/**
 * Created by syp523 on 2018/1/2.
 */

public class QuestionItemDelegate implements ItemViewDelegate<ChatMessage>{
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_qms_question;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        return item.getType() == ChatMessage.QUESTION;
    }

    @Override
    public void convert(ViewHolder holder, ChatMessage chatMessage, int position) {
        holder.setText(R.id.content_tv,chatMessage.getContent());

    }
}
