package com.zxtech.esp.ui.msg;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.BbsPostReplyVO;
import com.zxtech.esp.data.bean.ForumVO;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import java.util.List;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;
import g.api.http.volley.Request;

/**
 * Created by Administrator on 2017/7/19.
 */

public class ForumReplyActivity extends AppCompatActivity implements TextWatcher{

    private ForumVO.Data data;
    private BbsReplyAdapter adapter;
    private ListView lv;
    private PopupWindow popupWindow;
    private ForumReplyHeaderView headerView;
    private TextView tv_send;
    private String replyContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (ForumVO.Data)getIntent().getSerializableExtra(C.DATA);
        setContentView(R.layout.activity_forum_reply);
        step();
        initData();
    }

    private void step() {

        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        BizUtil.setIconFont(tv_back,"\ue620");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lv = (ListView) findViewById(R.id.lv);
        headerView = new ForumReplyHeaderView(this);
        headerView.setShowText(data);
        lv.addHeaderView(headerView, null, false);
        adapter = new BbsReplyAdapter();
        lv.setAdapter(adapter);
    }

    void initData(){

        final GsonCallBack<BbsPostReplyVO> callBack = new GsonCallBack<BbsPostReplyVO>(this){
            @Override
            public void onStart() {
                showLoading(ForumReplyActivity.this);
            }
            @Override
            protected void onDoSuccess(BbsPostReplyVO bean) {

                dismissLoading();
                List<BbsPostReplyVO.Data> data = bean.getData();
                adapter.setDatas(data);
                adapter.notifyDataSetChanged();
                lv.post(new Runnable() {
                    @Override
                    public void run() {
                        lv.setSelection(0);
                    }
                });
            }
        };
        String url = URL.getInstance().getPostCommentUrl(data.getId());
        GRequestData requestData = new GRequestData(Request.Method.GET,url,null);
        MyApp.getInstance().getHttp().send(requestData,callBack);
    }

    void upReply(String text,String targetId,String parentId){

        GsonCallBack<JsonElementVO> callBack = new GsonCallBack<JsonElementVO>(this){
            @Override
            public void onStart() {
                showLoading(ForumReplyActivity.this);
            }
            @Override
            protected void onDoSuccess(JsonElementVO bean) {
                dismissLoading();
                initData();
            }
        };
        String url = URL.getInstance().replyBbsUrl();
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("targetUserId",targetId);
        params.addBodyParameter("ownerUserId", MyApp.getUserId());
        params.addBodyParameter("content",text);
        params.addBodyParameter("postId",data.getId());
        params.addBodyParameter("parentId",parentId);
        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    void initPopupWindow(final BbsPostReplyVO.Data data){

        View view = LayoutInflater.from(this).inflate(R.layout.view_pop_reply, null);
        popupWindow = new PopupWindow(view,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setFocusable(true);
        // 设置点击其他地方就消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(findViewById(R.id.tv_pen), Gravity.BOTTOM
                | Gravity.CENTER_VERTICAL, 0, 0);

        EditText et_reply = (EditText) view.findViewById(R.id.et_reply);
        et_reply.addTextChangedListener(this);
        tv_send = (TextView) view.findViewById(R.id.tv_send);
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upReply(replyContent,data.getOwner_user_id(),data.getId());
                popupWindow.dismiss();
            }
        });

        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        replyContent = s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {

        if(TextUtils.isEmpty(replyContent)){
            tv_send.setClickable(false);
            tv_send.setEnabled(false);
            tv_send.setTextColor(getResources().getColor(R.color.text_color_gray_light));
        }else{
            tv_send.setClickable(true);
            tv_send.setEnabled(true);
            tv_send.setTextColor(getResources().getColor(R.color.text_color_black));
        }
    }
}
