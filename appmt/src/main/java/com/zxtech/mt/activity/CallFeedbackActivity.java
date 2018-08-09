package com.zxtech.mt.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.zxtech.mt.adapter.MtFeedbackAdapter;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.entity.CalCallFix;
import com.zxtech.mt.entity.CalSurveyResult;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mtos.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评价查看
 *
 * Created by Chw on 2016/7/27.
 */
public class CallFeedbackActivity extends BaseActivity{
    private ListView feedback_listview;
    private ImageView sign_imageview;
    private CalCallFix call;

    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_callfeedback, null);
        main_layout.addView(view);
        setBottomLayoutHide();
        title_textview.setText(getString(R.string.customer_satisfaction));

    }

    @Override
    protected void findView() {
        feedback_listview = (ListView) findViewById(R.id.feedback_listview);
        sign_imageview = (ImageView) findViewById(R.id.sign_imageview);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        call = (CalCallFix) intent.getSerializableExtra("bean");

        Map<String, String> param = new HashMap<>();
        param.put("plan_id", call.getId());
        HttpUtil.getInstance(mContext).request("/mtmo/getsurveyresultlist.mo", param, new HttpCallBack<List<CalSurveyResult>>() {
            @Override
            public void onSuccess(List<CalSurveyResult> list) {
                MtFeedbackAdapter mtFeedbackAdapter = new MtFeedbackAdapter(mContext,list,R.layout.item_mtfeedback);
                feedback_listview.setAdapter(mtFeedbackAdapter);

            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showLong(mContext, getString(R.string.msg_3));

            }
        });

    }

    @Override
    protected void initView() {
        Glide.with(mContext).load(SPUtils.get(mContext,"RESOURCE_URL","")+call.getProperty_sign_url()).into(sign_imageview);
    }

}
