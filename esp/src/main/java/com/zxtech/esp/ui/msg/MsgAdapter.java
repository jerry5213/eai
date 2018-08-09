package com.zxtech.esp.ui.msg;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxtech.esp.R;
import com.zxtech.esp.data.bean.ForumVO;

import java.util.Locale;

import g.api.adapter.GBaseAdapter;
import g.api.adapter.GViewHolder;

/**
 * Created by SYP521 on 2017/7/4.
 */

public class MsgAdapter extends GBaseAdapter<ForumVO.Data> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder(parent.getContext());
            convertView = viewHolder.getConvertView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.showData(getItem(position));

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    static class ViewHolder extends GViewHolder implements View.OnClickListener {

        private TextView tv_name;
        private TextView tv_info;
        private TextView tv_time;
        private TextView tv_type;
        private TextView tv_look;
        private TextView tv_reply;
        private String lan = Locale.getDefault().getLanguage();

        public ViewHolder(Context context) {
            super(context);

            tv_name = findView(R.id.tv_name);
            tv_info = findView(R.id.tv_info);
            tv_time = findView(R.id.tv_time);
            tv_type = findView(R.id.tv_type);
            tv_look = findView(R.id.tv_look);
            tv_reply = findView(R.id.tv_reply);
        }

        @Override
        protected int onSetConvertViewResId() {
            return R.layout.adapter_list_msg;
        }

        public void showData(ForumVO.Data data) {

            tv_name.setText(data.getSubject());
            tv_info.setText(data.getBody());
            tv_time.setText(data.getCreate_date());
            if("en".equals(lan)){
                tv_type.setText(data.getType_name_en());
            }else{
                tv_type.setText(data.getType_name());
            }
            tv_look.setText(data.getLook_num()+"");
            tv_reply.setText(data.getReply_num()+"");
        }

        @Override
        public void onClick(View v) {
        }
    }
}
