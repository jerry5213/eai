package com.zxtech.mt.adapter;

import android.content.Context;
import android.widget.RatingBar;

import com.zxtech.mt.entity.CalSurveyQuestion;
import com.zxtech.mtos.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Chw on 2016/8/17.
 */
public class SignAdapter extends  CommonAdapter<CalSurveyQuestion> {
    private HashMap<String,Integer> map = new HashMap<>();
    public SignAdapter(Context context, List<CalSurveyQuestion> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final CalSurveyQuestion calSurveyQuestion, int position) {
        holder.setText(R.id.question_textview, calSurveyQuestion.getQuestion_content());
        RatingBar rate_ratingbar = holder.getView(R.id.rate_ratingbar);

        rate_ratingbar.setRating(map.get(calSurveyQuestion.getId())/2);
        rate_ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                map.put(calSurveyQuestion.getId(),(int)rating*2);
            }
        });
    }

    public HashMap<String, Integer> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Integer> map) {
        this.map = map;
    }
}
