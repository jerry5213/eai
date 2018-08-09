package com.zxtech.mt.adapter;

import android.content.Context;
import android.widget.RatingBar;

import com.zxtech.mt.entity.CalSurveyResult;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by Chw on 2016/7/27.
 */
public class MtFeedbackAdapter extends CommonAdapter<CalSurveyResult>{

    public MtFeedbackAdapter(Context context, List<CalSurveyResult> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CalSurveyResult calSurveyResult, int position) {
        holder.setText(R.id.content_textview,calSurveyResult.getQuestion_content());
        RatingBar level_ratingbar = holder.getView(R.id.level_ratingbar);
        level_ratingbar.setRating(calSurveyResult.getQuestion_score()/2);
    }
}
