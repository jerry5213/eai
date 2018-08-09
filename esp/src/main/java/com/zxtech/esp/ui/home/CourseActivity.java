package com.zxtech.esp.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.BaseVO;
import com.zxtech.esp.data.bean.CourseVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import java.util.List;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/7/4.
 */

public class CourseActivity extends AppCompatActivity{

    private ListView lv;
    private CourseAdapter adapter;
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
        lv.setEmptyView(no_item);
        adapter = new CourseAdapter();
        initData();
    }

    public void initData(){

        GsonCallBack<CourseVO> callBack = new GsonCallBack<CourseVO>(this){
            @Override
            public void onStart() {
                no_item.setVisibility(View.GONE);
                showLoading(CourseActivity.this);
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
        String url = URL.getInstance().getCourseUrl(typeId);
        GRequestData requestData = new GRequestData(url,null);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    public void notifyDataSetChanged(){
        adapter.notifyDataSetChanged();
    }

    /**上传成绩**/
    public void uploadScore(String courseId){

        GsonCallBack<BaseVO> callBack = new GsonCallBack<BaseVO>(this){

            @Override
            protected void onDoSuccess(BaseVO bean) {
            }

            @Override
            public void onFailure(String info) {
                super.onFailure(info);
            }
        };
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("courseid",courseId);
        params.addBodyParameter("userid", MyApp.getUserId());
        params.addBodyParameter("credits","0");
        params.addBodyParameter("learningtime","0");
        String url = URL.getInstance().setUserLearnCourseInfoUrl();
        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }
}
