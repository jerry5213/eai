package com.zxtech.ecs.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ChatMessage;

/**
 * Created by syp523 on 2018/1/2.
 */

public class AnswerItemDelegate implements ItemViewDelegate<ChatMessage> {
	private Context mContext;
	private MultiItemTypeAdapter mMultiItemTypeAdapter;


	public void setMultiItemTypeAdapter(MultiItemTypeAdapter multiItemTypeAdapter) {
		mMultiItemTypeAdapter = multiItemTypeAdapter;
	}

	@Override
	public int getItemViewLayoutId() {
		return R.layout.item_qms_ask;
	}

	@Override
	public boolean isForViewType(ChatMessage item, int position) {
		return item.getType() == ChatMessage.ANSWER;
	}

	@Override
	public void convert(ViewHolder holder, ChatMessage ask, int position) {
		holder.setText(R.id.content_tv, ask.getContent());
	}
}
