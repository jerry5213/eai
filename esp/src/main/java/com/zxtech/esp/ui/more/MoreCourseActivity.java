package com.zxtech.esp.ui.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.zxtech.common.util.SPCache;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.CourseVO;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import java.util.List;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/7/4.
 */

public class MoreCourseActivity extends AppCompatActivity{

    private ListView lv;
    private MoreCourseAdapter adapter;
    private TextView no_item;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        BizUtil.setIconFont(tv_back,"\ue620");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        no_item = (TextView) findViewById(R.id.tv_no_item);
        lv = (ListView) findViewById(R.id.lv_course);
        lv.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(),true,true));
        lv.setEmptyView(findViewById(R.id.tv_no_item));
        adapter = new MoreCourseAdapter(this);
        initData();
    }

    public void initData(){

        GsonCallBack<CourseVO> callBack = new GsonCallBack<CourseVO>(this){
            @Override
            public void onStart() {
                showLoading(MoreCourseActivity.this);
                no_item.setVisibility(View.GONE);
            }

            @Override
            protected void onDoSuccess(CourseVO bean) {
                dismissLoading();
                List<CourseVO.Data> data = bean.getData();
                adapter.setDatas(data);
                lv.setAdapter(adapter);
                if (null == data || data.size() <= 0){
                    no_item.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String info) {
                super.onFailure(info);
                no_item.setVisibility(View.VISIBLE);
            }
        };
        String typeId = getIntent().getStringExtra("typeId");
        String url = URL.getInstance().getCourseUrl(typeId, SPCache.getInstance(this).getString(C.USER_ID));
        GRequestData requestData = new GRequestData(url,null);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    void praiseCourse(String courseId){

        GsonCallBack<JsonElementVO> callBack = new GsonCallBack<JsonElementVO>(this){
            @Override
            public void onStart() {
            }
            @Override
            protected void onDoSuccess(JsonElementVO bean) {
                initData();
            }
        };
        String url = URL.getInstance().praiseCourseUrl();
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("userId", SPCache.getInstance(this).getString(C.USER_ID));
        params.addBodyParameter("courseId",courseId);
        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }
}
