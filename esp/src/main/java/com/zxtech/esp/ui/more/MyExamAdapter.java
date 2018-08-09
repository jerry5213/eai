package com.zxtech.esp.ui.more;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxtech.esp.R;
import com.zxtech.esp.data.bean.ExamVO;

import java.util.Locale;

import g.api.adapter.GBaseAdapter;
import g.api.adapter.GViewHolder;

/**
 * Created by SYP521 on 2017/7/14.
 */

public class MyExamAdapter extends GBaseAdapter<ExamVO.ExamInfo> {

    private static int reviewStatus;

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

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    static class ViewHolder extends GViewHolder{

        TextView tv_score;
        TextView tv_score2;
        TextView tv_name;
        TextView tv_time;
        TextView tv_type;
        TextView tv_fen;

        String minute;
        String exam;
        String wait;
        String miss;
        String lan;
        String sort;

        public ViewHolder(Context context) {
            super(context);
            tv_score = findView(R.id.tv_score);
            tv_score2 = findView(R.id.tv_score2);
            tv_name = findView(R.id.tv_name);
            tv_time = findView(R.id.tv_time);
            tv_type = findView(R.id.tv_type);
            tv_fen = findView(R.id.tv_fen);
            minute = context.getResources().getString(R.string.minute);
            exam = context.getResources().getString(R.string.test);
            wait = context.getResources().getString(R.string.wait);
            miss = context.getResources().getString(R.string.miss);
            sort = context.getResources().getString(R.string.Sort);
            lan = Locale.getDefault().getLanguage();
        }

        @Override
        protected int onSetConvertViewResId() {
            return R.layout.adapter_list_exam;
        }

        public void showData(ExamVO.ExamInfo data) {

            if(reviewStatus == 0){
                tv_score.setVisibility(View.GONE);
                tv_score2.setVisibility(View.VISIBLE);
                String flag = data.getFlag();
                if("now".equals(flag)){
                    tv_score2.setText(exam);
                }else if("after".equals(flag)){
                    tv_score2.setText(miss);
                }else if("before".equals(flag)){
                    tv_score2.setText(wait);
                }
                tv_fen.setVisibility(View.GONE);
            }else{
                tv_score.setVisibility(View.VISIBLE);
                tv_score2.setVisibility(View.GONE);
                tv_score.setText(data.getScore()+"");
                if("en".equals(lan)){
                    tv_fen.setVisibility(View.VISIBLE);
                    tv_fen.setText(" ");
                }else{
                    tv_fen.setVisibility(View.VISIBLE);
                    tv_fen.setText("分");
                }
            }
            tv_name.setText(data.getExamname());
            tv_time.setText(data.getExamtime()+minute);
            tv_type.setText(sort+(TextUtils.isEmpty(data.getExamkhzl())?"":data.getExamkhzl()));
        }
    }
}
