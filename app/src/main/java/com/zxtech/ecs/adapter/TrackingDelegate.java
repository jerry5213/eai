package com.zxtech.ecs.adapter;

import android.content.Context;
import android.view.View;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ChatMessage;

/**
 * @author syp521
 *       点击我的反馈详情界面
 */

public class TrackingDelegate implements ItemViewDelegate<ChatMessage> {

    private Context mContext;
    private TrackingSelectedListener listener;

    public TrackingDelegate(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.delegate_qms_tracking;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        return item.getType() == ChatMessage.TRACKING;
    }

    @Override
    public void convert(ViewHolder holder, final ChatMessage chatMessage, int position) {

        String number = "反馈单"+chatMessage.getDataMap().get("JobNumber")+"的物流情况如下：";
        holder.setText(R.id.tv_feedback_util, number);
        holder.setText(R.id.tv_number, chatMessage.getDataMap().get("L_YSDH"));
        holder.setText(R.id.tv_company, chatMessage.getDataMap().get("L_YSDW"));
        holder.setOnClickListener(R.id.tv_link, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.getTrackingSelect(chatMessage.getDataMap().get("JobNumber"));
                }
            }
        });
    }

    public void setListener(TrackingSelectedListener listener) {
        this.listener = listener;
    }

    public interface TrackingSelectedListener {
        void getTrackingSelect(String JobNumber);
    }
}


