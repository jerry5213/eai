package com.zxtech.esp.ui.more;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxtech.common.util.T;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.UserInfoVO;
import com.zxtech.esp.util.BizUtil;

/**
 * Created by SYP521 on 2017/8/28.
 */

public class MoreHeaderView extends FrameLayout {

    private ImageView owner_photo;
    private TextView owner_nickname;
    private TextView tv_rank;
    private TextView tv_exam;
    private DisplayImageOptions opts;
    private MoreFragment fragment;

    public MoreHeaderView(Context context) {
        super(context);
        setup(context);
    }

    public MoreHeaderView(Context context, AttributeSet attrs){
        super(context, attrs);
        setup(context);
    }

    public void setFragment(MoreFragment fragment) {
        this.fragment = fragment;
    }

    private void setup(Context context) {

        opts = BizUtil.getDefaultImageOPTSBuilder(T.dip2px(getContext(),40.0f)).showImageOnFail(R.mipmap.avatar_default).build();
        LayoutInflater.from(context).inflate(R.layout.view_more_header, this);
        owner_photo = (ImageView)findViewById(R.id.owner_photo);
        owner_nickname = (TextView)findViewById(R.id.owner_nickname);
        owner_nickname.setText(MyApp.getNick_name());
        tv_rank = (TextView)findViewById(R.id.tv_rank);
        tv_exam = (TextView)findViewById(R.id.tv_exam);

        LinearLayout ll_ranking = (LinearLayout)findViewById(R.id.ll_ranking);
        ll_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),MyRankingActivity.class);
                intent.putExtra(C.DATA,fragment.getUser());
                fragment.startActivity(intent);
            }
        });

        LinearLayout ll_exam = (LinearLayout)findViewById(R.id.ll_exam);
        ll_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MyExamActivity.class);
                fragment.startActivity(intent);
            }
        });

        ImageView tv_pen = (ImageView)findViewById(R.id.iv_pen);
        tv_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),UserInfoActivity.class);
                intent.putExtra(C.DATA,fragment.getUser());
                fragment.getActivity().startActivityForResult(intent,1);
            }
        });

    }

    public void setShowText(UserInfoVO.Data user){

        owner_nickname.setText(user.getName());
        MyApp.setPhoto_url(user.getPhoto_url());
        ImageLoader.getInstance().displayImage(URL.getInstance().getFullUrl(user.getPhoto_url()),owner_photo,opts);
        tv_rank.setText(TextUtils.isEmpty(user.getRank())?getResources().getString(R.string.nothing):user.getRank());
        tv_exam.setText(user.getExam());

    }
}
