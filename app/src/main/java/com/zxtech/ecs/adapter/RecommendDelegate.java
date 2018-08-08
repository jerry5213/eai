package com.zxtech.ecs.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ChatMessage;

/**
 * Created by syp523 on 2018/1/3.
 */

public class RecommendDelegate implements ItemViewDelegate<ChatMessage> {
    private Context mContext;
    private SelectedListener listener;

    public RecommendDelegate(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_qms_recommend;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        return item.getType() == ChatMessage.RECOMMEND;
    }

    @Override
    public void convert(ViewHolder holder, final ChatMessage ask, int position) {
        RecyclerView ask_rv = holder.getView(R.id.category_rv);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        ask_rv.setLayoutManager(linearLayoutManager2);
        CommonAdapter<ChatMessage.Options> adapter = new CommonAdapter<ChatMessage.Options>(mContext, R.layout.item_recommend_item,ask.getOptions()) {
            @Override
            protected void convert(ViewHolder holder, ChatMessage.Options recommend, int position) {
                holder.setText(R.id.price_tv,recommend.getText());
//                Glide.with(mContext).load(recommend.getDrawable()).into((ImageView) holder.getView(R.id.imageview));
            }
        };
        ask_rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                listener.getText(ask.getOptions().get(position));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    public void setListener(SelectedListener listener) {
        this.listener = listener;
    }

    public interface SelectedListener {
        void getText(ChatMessage.Options recommend);
    }

}


