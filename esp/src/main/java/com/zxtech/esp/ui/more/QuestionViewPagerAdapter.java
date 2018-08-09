package com.zxtech.esp.ui.more;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zxtech.esp.R;
import com.zxtech.esp.data.bean.QuestionsVO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import g.api.adapter.GPagerAdapter;
import g.api.adapter.GViewHolder;

/**
 * Created by SYP521 on 2017/7/31.
 */

public class QuestionViewPagerAdapter extends GPagerAdapter<QuestionsVO.Data> {

    private int showPoint;
    private int reviewStatus;

    public void setShowPoint(int showPoint) {
        this.showPoint = showPoint;
    }

    public QuestionViewPagerAdapter(Context context) {
        super(context);
        reviewStatus = ((ExamAnswerActivity)context).getReviewStatus();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder(parent.getContext());
        convertView = viewHolder.getConvertView();
        viewHolder.showData(getItem(position), position, getCount());
        return convertView;
    }

    @Override
    public List<QuestionsVO.Data> getDatas() {
        return super.getDatas();
    }

    private class ViewHolder extends GViewHolder {

        private int num = 9;
        private TextView question;
        private RadioButton[] rbs;
        private RadioGroup radioGroup;
        private LinearLayout check_group;
        private CheckBox[] cbs;
        private TextView tv_answer;
        private TextView tv_remarks;
        private LinearLayout fl_resolve;

        public ViewHolder(Context context) {
            super(context);
            question = findView(R.id.question);
            radioGroup = findView(R.id.radioGroup);
            check_group = findView(R.id.check_group);
            rbs = new RadioButton[num];
            cbs = new CheckBox[8];
            for (int i = 0; i < num; i++) {
                int itemViewId = context.getResources().getIdentifier("radio_" + i, "id", context.getPackageName());
                rbs[i] = findView(itemViewId);
            }
            for (int i = 0; i < 8; i++) {
                int itemViewId = context.getResources().getIdentifier("check_" + i, "id", context.getPackageName());
                cbs[i] = findView(itemViewId);
            }
            tv_answer = findView(R.id.tv_answer);
            tv_remarks = findView(R.id.tv_remarks);
            fl_resolve = findView(R.id.fl_resolve);
            tv_remarks.setMovementMethod(ScrollingMovementMethod.getInstance());
        }

        @Override
        protected int onSetConvertViewResId() {
            return R.layout.adapter_question;
        }

        public void showData(final QuestionsVO.Data data, int position, int count) {

            question.setText(data.getQuestionNum()+". "+data.getQuestionContent()+"("+data.getTypeName()+")");
            tv_answer.setText(data.getShowAnswer());
            tv_remarks.setText(data.getRemarks());
            /**是否显示解析**/
            if(showPoint == 0 || reviewStatus == 0){
                fl_resolve.setVisibility(View.GONE);
            }else {
                fl_resolve.setVisibility(View.VISIBLE);
            }
            /**单选题**/
            if("T00000000000002".equals(data.getTypeId()) || "T00000000000004".equals(data.getTypeId())){
                check_group.setVisibility(View.GONE);
                radioGroup.setVisibility(View.VISIBLE);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        RadioButton rb = findView(checkedId);
                        if (rb.isChecked()) {
                            List<RadioButton> list = Arrays.asList(rbs);
                            int pos = list.indexOf(rb);
                            data.setChoose(pos);
                            data.setUserAnswer(rb.getTag().toString());
                            //T.showToast(group.getContext(), pos + "");
                        }
                    }
                });

                for (int i = 0; i < num; i++) {
                    if (i < data.getData().size()) {
                        QuestionsVO.Question question = data.getData().get(i);
                        rbs[i].setVisibility(View.VISIBLE);
                        rbs[i].setText("  " + question.getOptionKey()+"、"+question.getOptionContent());
                        rbs[i].setTag(question.getOptionId());
                        boolean check = isCheck(question.getOptionId(), data.getUserAnswer());
                        rbs[i].setChecked(check);
                        if(reviewStatus == 0){
                            rbs[i].setClickable(true);
                            /*if (i == data.getChoose()) {
                                rbs[i].setChecked(true);
                            } else {
                                rbs[i].setChecked(false);
                            }*/
                        }else {
                            rbs[i].setClickable(false);
                        }
                    } else {
                        rbs[i].setVisibility(View.GONE);
                    }
                }
            }
            /**多选题**/
            else if("T00000000000003".equals(data.getTypeId())){
                check_group.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.GONE);
                for (int i = 0; i < 8; i++) {
                    if (i < data.getData().size()) {
                        QuestionsVO.Question question = data.getData().get(i);
                        cbs[i].setVisibility(View.VISIBLE);
                        cbs[i].setText("  " + question.getOptionKey()+"、"+question.getOptionContent());
                        cbs[i].setTag(question.getOptionId());
                        cbs[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                String userAnswer = "";
                                Map<String, Boolean> chooseMap = data.getChooseMap();
                                chooseMap.put(buttonView.getTag().toString(),isChecked);
                                for (Map.Entry<String, Boolean> entry : chooseMap.entrySet()) {
                                    if(entry.getValue()){
                                        userAnswer += entry.getKey()+",";
                                    }
                                }
                                if(!"".equals(userAnswer) && userAnswer.contains(",")){
                                    userAnswer = userAnswer.substring(0,userAnswer.length()-1);
                                }
                                data.setUserAnswer(userAnswer);
                            }
                        });

                        boolean check2 = isCheck(question.getOptionId(), data.getUserAnswer());
                        cbs[i].setChecked(check2);
                        if(reviewStatus == 0){
                            cbs[i].setClickable(true);
                            /*Boolean check = data.getChooseMap().get(question.getOptionId());
                            cbs[i].setChecked(check==null?false:check);*/
                        }else {
                            cbs[i].setClickable(false);
                        }
                    } else {
                        cbs[i].setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    private boolean isCheck(String optionId,String userAnswer){

        if(TextUtils.isEmpty(userAnswer)){
            return false;
        }else{
            String[] split = userAnswer.split(",");
            List<String> answerList = Arrays.asList(split);
            if(answerList.contains(optionId)){
                return true;
            }else{
                return false;
            }
        }
    }
}
