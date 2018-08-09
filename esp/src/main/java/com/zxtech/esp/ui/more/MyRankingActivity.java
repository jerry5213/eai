package com.zxtech.esp.ui.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxtech.common.util.T;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.RankingVO;
import com.zxtech.esp.data.bean.UserInfoVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import java.util.List;

import g.api.http.GRequestData;

/**
 * Created by SYP521 on 2017/7/13.
 */

public class MyRankingActivity extends AppCompatActivity {

    private MyRankingAdapter adapter;
    private ListView mListView;

    private UserInfoVO.Data data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (UserInfoVO.Data) getIntent().getSerializableExtra(C.DATA);
        setContentView(R.layout.activity_my_ranking);
        step();
    }

    private void step(){

        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        BizUtil.setIconFont(tv_back, "\ue620");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tv_rank = (TextView) findViewById(R.id.tv_rank);
        tv_rank.setText(data.getRank());
        ImageView owner_photo = (ImageView) findViewById(R.id.owner_photo);
        DisplayImageOptions opts = BizUtil.getDefaultImageOPTSBuilder(T.dip2px(this,40.0f)).showImageOnFail(R.mipmap.avatar_default).build();
        ImageLoader.getInstance().displayImage(URL.getInstance().getFullUrl(data.getPhoto_url()),owner_photo,opts);

        mListView = (ListView)findViewById(R.id.lv_ranking);
        adapter = new MyRankingAdapter();
        initData();
    }

    private void initData(){

        GsonCallBack<RankingVO> callBack = new GsonCallBack<RankingVO>(this){
            @Override
            public void onStart() {
                showLoading(MyRankingActivity.this);
            }
            @Override
            protected void onDoSuccess(RankingVO bean) {
                dismissLoading();
                List<RankingVO.Data> data = bean.getData();
                adapter.setDatas(data);
                mListView.setAdapter(adapter);
            }
        };
        String url = URL.getInstance().getRankingAllUrl();
        GRequestData requestData = new GRequestData(url,null);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }
}
