package com.zxtech.esp.ui.more;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxtech.esp.R;
import com.zxtech.esp.data.bean.QuestionsVO;

import java.util.Arrays;
import java.util.List;

import g.api.adapter.GRecyclerAdapter;
import g.api.adapter.GRecyclerViewHolder;

/**
 * Created by SYP521 on 2017/7/28.
 */

public class ExamListAdapter extends GRecyclerAdapter<QuestionsVO.Data,ExamListAdapter.ViewHolder> {

    private int reviewStatus;
    private String visitor; //判断是考试进来的还是解析进来的

    public ExamListAdapter(Context context) {
        super(context);
        reviewStatus = ((ExamAnswerActivity) context).getReviewStatus();
        visitor = ((ExamAnswerActivity) context).getVisitor();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_exam, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.setData(getItem(position));
        holder.showData();
    }

    class ViewHolder extends GRecyclerViewHolder<QuestionsVO.Data> {

        TextView tv_topic;
        TextView tv_tag;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_topic = (TextView) itemView.findViewById(R.id.tv_topic);
            tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);
        }

        @Override
        public boolean isOpenItemClick() {
            return true;
        }

        @Override
        protected void onShowData(QuestionsVO.Data data) {
            super.onShowData(data);
            tv_topic.setText(data.getQuestionNum()+"");

            if(reviewStatus == 0 && !"resolve".equals(visitor)){
                if(!TextUtils.isEmpty(data.getUserAnswer()) || data.getChooseMap().size()>0){
                    tv_topic.setBackgroundResource(R.drawable.circle_corner_button_fill_blue);
                }else{
                    tv_topic.setBackgroundResource(R.drawable.circle_corner_button);
                }
            }else{
                //判断答案是否正确
                int num = answerIsRight(data.getUserAnswer(),data.getAnswer());
                if(num == 0){
                    tv_topic.setBackgroundResource(R.drawable.circle_corner_button);
                }else if(num == 1){
                    tv_topic.setBackgroundResource(R.drawable.circle_corner_button_fill_red);
                }else if(num == 2){
                    tv_topic.setBackgroundResource(R.drawable.circle_corner_button_fill_blue);
                }else{
                    tv_topic.setBackgroundResource(R.drawable.circle_corner_button);
                }
            }

            if(data.getProblemState() == 1){
               tv_tag.setVisibility(View.VISIBLE);
            }
            else{
                tv_tag.setVisibility(View.GONE);
            }
        }

        @Override
        public void onItemClick(View v, QuestionsVO.Data data) {
            super.onItemClick(v, data);
            ((ExamAnswerActivity)(v.getContext())).turnPage(data);
            ((ExamAnswerActivity)(v.getContext())).closeFragment();
        }
    }

    private int answerIsRight(String userAnswer,String answer){

        int flag = 0;
        if(null != userAnswer && !"".equals(userAnswer)){

            String[] answers = answer.split(",");
            String[] userAnswers = userAnswer.split(",");
            List<String> answerList = Arrays.asList(answers);

            if(userAnswers.length != answerList.size()){
                flag = 1;
            }else{
                for(String str:userAnswers){
                    if(!answerList.contains(str)){
                        flag = 1;
                        break;
                    }
                    flag = 2;
                }
            }
        }else{
            flag = 0;
        }
        return flag;
    }
}
