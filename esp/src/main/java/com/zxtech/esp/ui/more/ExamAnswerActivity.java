package com.zxtech.esp.ui.more;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxtech.common.util.SPCache;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.ExamVO;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.data.bean.QuestionsVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import java.util.ArrayList;
import java.util.List;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/7/27.
 */

public class ExamAnswerActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private ImageView iv_flag;
    private QuestionViewPagerAdapter adapter;
    private TextView tv_time;
    private MyCountDownTimer mc;

    private FragmentManager fm;
    private ExamVO.ExamInfo examInfo;
    private List<QuestionsVO.Data> datas; //试题
    private String visitor; //判断是考试进来的还是解析进来的
    private int reviewStatus;

    private SPCache sp;

    public List<QuestionsVO.Data> getDatas() {
        return datas;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = SPCache.getNewInstance(this,C.EXAM_SP);
        Intent intent = getIntent();
        examInfo = (ExamVO.ExamInfo)intent.getSerializableExtra(C.DATA);
        visitor = intent.getStringExtra("visitor");
        reviewStatus = intent.getIntExtra("reviewStatus",0);
        setContentView(R.layout.activity_exam_answer);
        step();
    }

    public int getReviewStatus() {
        return reviewStatus;
    }

    public String getVisitor() {
        return visitor;
    }

    private void step() {

        /**考试名称**/
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(examInfo.getExamname());
        /**考试类型**/
        TextView tv_type = (TextView) findViewById(R.id.tv_type);
        tv_type.setText(getResources().getString(R.string.Sort)+(TextUtils.isEmpty(examInfo.getExamkhzl())?"":examInfo.getExamkhzl()));

        tv_time = (TextView) findViewById(R.id.tv_time);
        iv_flag = (ImageView) findViewById(R.id.iv_flag);

        if(!"resolve".equals(visitor)){
            int time = examInfo.getExamtime();

            String key = examInfo.getExamid()+ MyApp.getUserId()+"_exam";
            String examInfoStr = sp.getString(key);
            if(!TextUtils.isEmpty(examInfoStr)){
                Gson gson = new Gson();
                ExamVO.ExamInfo examInfoCache = gson.fromJson(examInfoStr, ExamVO.ExamInfo.class);
                time = time - examInfoCache.getContinuetime();
            }
            mc = new MyCountDownTimer(time*60*1000, 1000*60);
            mc.start();
        }else{
            tv_time.setText(examInfo.getContinuetime()+"");
        }

        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        BizUtil.setIconFont(tv_back, "\ue620");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 打开考题列表
         */
        fm = getSupportFragmentManager();
        findViewById(R.id.topic_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamListFragment fragment = new ExamListFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.fragment_container_child, fragment, fragment.getClass().getName());
                ft.commit();
            }
        });

        /**
         * 点击交卷
         */
        TextView tv_commit = (TextView) findViewById(R.id.tv_commit);
        TextView tv_before = (TextView) findViewById(R.id.tv_before);
        if(reviewStatus == 0){

            //考试进来提交一条考试记录
            saveExamDetails();

            tv_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ExamResultActivity.class);
                    intent.putExtra(C.DATA,new ArrayList(datas));//发送数据
                    intent.putExtra("examInfo",examInfo);//发送数据
                    setResult(Activity.RESULT_OK);
                    startActivity(intent);
                    finish();
                }
            });

            /**
             * 点击标注
             */
            iv_flag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() != null && v.getTag() instanceof QuestionsVO.Data) {
                        QuestionsVO.Data data = (QuestionsVO.Data) v.getTag();
                        if(data.getProblemState() == 1){
                            data.setProblemState(0);
                        }else{
                            data.setProblemState(1);
                        }
                        iv_flag.setTag(data);
                        iv_flag.setImageResource(data.getProblemState() == 1?R.mipmap.ic_tanhao_blue:R.mipmap.ic_tanhao);
                        saveExamProblemState(data);
                    }
                }
            });

        }else if(reviewStatus == 1){
            tv_commit.setText(getResources().getString(R.string.exit));
            tv_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            iv_flag.setClickable(false);
            tv_before.setText(getResources().getString(R.string.used_time));
        }

        viewpager = (ViewPager) this.findViewById(R.id.viewpager);
        adapter = new QuestionViewPagerAdapter(this);
        viewpager.setAdapter(adapter);
        /**
         * 页面切换
         */
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                QuestionsVO.Data data = adapter.getItem(position);
                iv_flag.setTag(data);
                iv_flag.setImageResource(data.getProblemState() == 1?R.mipmap.ic_tanhao_blue:R.mipmap.ic_tanhao);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        /**加载试题**/
        loadQuestions();
    }

    public void turnPage(QuestionsVO.Data data){
        int position = adapter.getDatas().indexOf(data);
        viewpager.setCurrentItem(position,true);
    }

    private void loadQuestions() {

        if(reviewStatus == 0){

            String key = examInfo.getExamid()+ MyApp.getUserId()+"_answer";
            String answerStr = sp.getString(key);
            if(!TextUtils.isEmpty(answerStr)){
                Gson gson = new Gson();
                datas = gson.fromJson(answerStr,new TypeToken<List<QuestionsVO.Data>>() {}.getType());
                adapter.setDatas(datas);
                adapter.setShowPoint(examInfo.getShowpoint());
                adapter.notifyDataSetChanged();
                if (adapter.hasDatas())
                    iv_flag.setTag(adapter.getItem(0));
                if(null!=datas && datas.size()>0){
                    iv_flag.setImageResource(datas.get(0).getProblemState() == 1?R.mipmap.ic_tanhao_blue:R.mipmap.ic_tanhao);
                }
            }else{
                loadQuestionsChild();
            }
        }else{
            loadQuestionsChild();
        }
    }

    private void loadQuestionsChild() {

        GsonCallBack<QuestionsVO> callBack = new GsonCallBack<QuestionsVO>(this){
            @Override
            public void onStart() {
                showLoading(ExamAnswerActivity.this);
            }

            @Override
            protected void onDoSuccess(QuestionsVO bean) {
                dismissLoading();
                datas = bean.getData();
                adapter.setDatas(datas);
                adapter.setShowPoint(examInfo.getShowpoint());
                adapter.notifyDataSetChanged();
                if (adapter.hasDatas())
                    iv_flag.setTag(adapter.getItem(0));
                if(null!=datas && datas.size()>0){
                    iv_flag.setImageResource(datas.get(0).getProblemState() == 1?R.mipmap.ic_tanhao_blue:R.mipmap.ic_tanhao);
                }
            }
        };
        String url = URL.getInstance().getPaperOptions();
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("loginUserId", MyApp.getUserId());
        params.addBodyParameter("examid",examInfo.getExamid());
        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    private void saveExamDetails(){

        GsonCallBack<JsonElementVO> callBack = new GsonCallBack<JsonElementVO>(this){

            @Override
            protected void onDoSuccess(JsonElementVO bean) {
            }
        };
        String url = URL.getInstance().saveExamDetails();
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("examid",examInfo.getExamid());
        params.addBodyParameter("paperid",examInfo.getPaperid());
        params.addBodyParameter("userid", MyApp.getUserId());
        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    private void saveExamProblemState(QuestionsVO.Data data){

        GsonCallBack<JsonElementVO> callBack = new GsonCallBack<JsonElementVO>(this){

            @Override
            protected void onDoSuccess(JsonElementVO bean) {
            }
        };
        String url = URL.getInstance().saveExamProblemState();
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("examid",examInfo.getExamid());
        params.addBodyParameter("paperid",examInfo.getPaperid());
        params.addBodyParameter("userid", MyApp.getUserId());

        params.addBodyParameter("questionid",data.getQuestionId());
        params.addBodyParameter("typeid",data.getTypeId());
        params.addBodyParameter("problemstate",data.getProblemState()+"");
        params.addBodyParameter("continuetime",examInfo.getContinuetime()+"");

        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    public void closeFragment() {

        Fragment fragment = fm.findFragmentByTag(ExamListFragment.class.getName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_time.setText(millisUntilFinished / 60000 + "");
            examInfo.setContinuetime(examInfo.getContinuetime()+1);
        }

        @Override
        public void onFinish() {

            tv_time.setText("0");
            AlertDialog.Builder builder = new AlertDialog.Builder(ExamAnswerActivity.this);
            builder.setTitle("提示");
            builder.setMessage("考试结束！");
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    Intent intent = new Intent(ExamAnswerActivity.this, ExamResultActivity.class);
                    intent.putExtra(C.DATA,new ArrayList(datas));//发送数据
                    intent.putExtra("examInfo",examInfo);//发送数据
                    setResult(Activity.RESULT_OK);
                    startActivity(intent);
                    finish();
                }
            });
            dialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(reviewStatus == 0){

            Gson gson = new Gson();
            String key = examInfo.getExamid()+ MyApp.getUserId()+"_exam";
            sp.setString(key,gson.toJson(examInfo));
            String key2 = examInfo.getExamid()+ MyApp.getUserId()+"_answer";
            sp.setString(key2,gson.toJson(datas));
        }
    }
}
