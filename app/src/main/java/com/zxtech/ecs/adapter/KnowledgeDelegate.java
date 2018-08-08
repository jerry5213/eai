package com.zxtech.ecs.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ChatMessage;

import java.util.Map;

/**
 * @author syp521
 *         知识问答 返回列表 点击跳转网页查看
 */

public class KnowledgeDelegate implements ItemViewDelegate<ChatMessage> {

    private Context mContext;
    private KnowledgeSelectedListener listener;

    public KnowledgeDelegate(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_qms_category;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        return item.getType() == ChatMessage.KNOWLEDGE;
    }

    @Override
    public void convert(ViewHolder holder, final ChatMessage chatMessage, int position) {

        RecyclerView recycleView = holder.getView(R.id.category_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(mContext,R.drawable.divider_line_gray));
        recycleView.addItemDecoration(divider);

        CommonAdapter<Map<String,String>> adapter = new CommonAdapter<Map<String,String>>(mContext, R.layout.item_qms_knowledge, chatMessage.getKnowledges()) {
            @Override
            protected void convert(ViewHolder holder, Map<String,String> knowledge, int position) {
                holder.setText(R.id.tv_qms_question_desc, knowledge.get("title"));
                holder.setText(R.id.tv_qms_answer, knowledge.get("content"));
            }
        };
        recycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(listener!=null){
                    listener.getText(chatMessage.getKnowledges().get(position));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    public void setListener(KnowledgeSelectedListener listener) {
        this.listener = listener;
    }

    public interface KnowledgeSelectedListener {
        void getText(Map<String,String> text);
    }

}


