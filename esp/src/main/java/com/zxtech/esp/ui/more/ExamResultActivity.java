package com.zxtech.esp.ui.more;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zxtech.common.util.SPCache;
import com.zxtech.common.view.RoundProgressBar;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.ExamResultVO;
import com.zxtech.esp.data.bean.ExamVO;
import com.zxtech.esp.data.bean.QuestionsVO;
import com.zxtech.esp.util.GsonCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/7/28.
 */

public class ExamResultActivity extends AppCompatActivity {

    private TextView tv_error;
    private RoundProgressBar mRoundProgressBar1;
    private List<QuestionsVO.Data> list;
    private ExamVO.ExamInfo examInfo;
    private SPCache sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = SPCache.getNewInstance(this, C.EXAM_SP);
        setContentView(R.layout.activity_exam_result);
        mRoundProgressBar1 = (RoundProgressBar) findViewById(R.id.roundProgressBar1);
        step();
    }

    private void step() {

        Intent intent = this.getIntent();
        list = (List<QuestionsVO.Data>) intent.getSerializableExtra(C.DATA);
        examInfo = (ExamVO.ExamInfo) getIntent().getSerializableExtra("examInfo");

        TextView tv_close = (TextView) findViewById(R.id.tv_close);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tv_resolve = (TextView) findViewById(R.id.tv_resolve);
        tv_resolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ExamAnswerActivity.class);
                intent.putExtra(C.DATA, examInfo);
                intent.putExtra("visitor", "resolve");
                intent.putExtra("reviewStatus", 1);
                startActivity(intent);
            }
        });

        TextView tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setText(examInfo.getContinuetime() + "");

        tv_error = (TextView) findViewById(R.id.tv_error);

        /**考试名称**/
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(examInfo.getExamname());
        /**考试类型**/
        TextView tv_type = (TextView) findViewById(R.id.tv_type);
        tv_type.setText(examInfo.getExamkhzl());

        mRoundProgressBar1.setMax(examInfo.getExamscore());

        for (QuestionsVO.Data data : list) {

            String typeId = data.getTypeId();
            //用户答案
            String userAnswer = "";
            if ("T00000000000002".equals(typeId)) {
                userAnswer = data.getUserAnswer();
            } else if ("T00000000000003".equals(typeId)) {
                Map<String, Boolean> chooseMap = data.getChooseMap();
                for (Map.Entry<String, Boolean> entry : chooseMap.entrySet()) {
                    if (entry.getValue()) {
                        userAnswer += entry.getKey() + ",";
                    }
                }
                if (!"".equals(userAnswer) && userAnswer.contains(",")) {
                    userAnswer = userAnswer.substring(0, userAnswer.length() - 1);
                }
            }
            data.setUserAnswer(userAnswer);

            /*if(answer.equals(userAnswer)){
                total += data.getQuestionScore();
            }else{
                error++;
            }*/
        }
        uploadResult(list);
        cleanExamCache();
    }

    private void uploadResult(List<QuestionsVO.Data> datas) {

        List<Map<String, String>> resultList = new ArrayList<>();
        if (datas != null) {
            for (QuestionsVO.Data d : datas) {
                Map<String, String> map = new HashMap<>();
                map.put("questionId", d.getQuestionId());
                map.put("answer", d.getAnswer());
                map.put("userAnswer", d.getUserAnswer());
                map.put("typeId", d.getTypeId());
                map.put("questionScore", d.getQuestionScore() + "");
                map.put("problemState", d.getProblemState() + "");
                resultList.add(map);
            }
        }

        GsonCallBack<ExamResultVO> callBack = new GsonCallBack<ExamResultVO>(this) {
            @Override
            public void onStart() {
                showLoading(ExamResultActivity.this);
            }

            @Override
            protected void onDoSuccess(ExamResultVO vo) {
                dismissLoading();
                ExamResultVO.Data data = vo.getData();
                tv_error.setText(getResources().getString(R.string.errors) + data.getErrorNum());
                drawProgress(data.getTotal());
            }
        };
        Gson gson = new Gson();
        String result = gson.toJson(resultList);
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("jsonStr", result);
        params.addBodyParameter("userId", MyApp.getUserId());
        params.addBodyParameter("examId", examInfo.getExamid());
        params.addBodyParameter("paperId", examInfo.getPaperid());
        params.addBodyParameter("continue_time", examInfo.getContinuetime() + "");
        String url = URL.getInstance().handExamPaper();
        GRequestData requestData = new GRequestData(url, params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    private void drawProgress(int total) {
        new AsyncTask<Integer, Integer, Void>() {
            @Override
            protected Void doInBackground(Integer... params) {
                int progress = 0;
                while (progress < params[0]) {
                    progress += 1;
                    publishProgress(progress);
                    SystemClock.sleep(200);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                mRoundProgressBar1.setProgress(values[0]);
            }
        }.execute(total);
    }

    private void cleanExamCache() {
        sp.clear();
    }
}
