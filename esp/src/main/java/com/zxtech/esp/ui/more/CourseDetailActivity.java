package com.zxtech.esp.ui.more;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxtech.common.download.DownloadUtils;
import com.zxtech.common.download.FileDownloadDialog;
import com.zxtech.common.download.SimpleDialog;
import com.zxtech.common.util.F;
import com.zxtech.common.util.FileProvider7;
import com.zxtech.common.util.SPCache;
import com.zxtech.common.util.T;
import com.zxtech.esp.AppConfig;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.BaseVO;
import com.zxtech.esp.data.bean.CourseCommentVO;
import com.zxtech.esp.data.bean.CourseVO;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;
import com.zxtech.mvp.konepluginp.PlayPluginActivity;

import java.io.File;
import java.util.List;
import java.util.Map;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;
import g.api.http.volley.Request;

/**
 * Created by SYP521 on 2017/7/15.
 */

public class CourseDetailActivity extends AppCompatActivity implements TextWatcher{

    private ListView lv;
    private CourseTalkAdapter mAdapter;
    private CourseVO.Data data;
    private PopupWindow popupWindow;
    private EditText editText;
    private TextView tv_send;
    private TextView tv_publish_time;
    private TextView tv_intro;
    private String replyContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (CourseVO.Data) getIntent().getSerializableExtra(C.DATA);
        setContentView(R.layout.activity_course_detail);
        step();
    }

    private void step(){

        ImageView iv_head = (ImageView) findViewById(R.id.iv_header);
        iv_head.getLayoutParams().height = (int) (getResources().getDisplayMetrics().widthPixels * 144f / 259f);
        ImageLoader.getInstance().displayImage(URL.getInstance().getFullUrl(data.getBig_image_url()), iv_head);

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(data.getCourse_name().toString());
        tv_publish_time  = (TextView) findViewById(R.id.tv_publish_time);
        tv_publish_time.setText(tv_publish_time.getText().toString()+data.getCreate_time());
        tv_intro  = (TextView) findViewById(R.id.tv_intro);
        tv_intro.setText(tv_intro.getText().toString()+(TextUtils.isEmpty(data.getCourse_desc())?"":data.getCourse_desc()));

        TextView tv_pen = (TextView) findViewById(R.id.tv_pen);
        BizUtil.setIconFont(tv_pen, "\ue617");
        TextView tv_up = (TextView) findViewById(R.id.tv_up);
        BizUtil.setIconFont(tv_up, "\ue635");
        editText = (EditText) findViewById(R.id.et_comment);
        tv_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = editText.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    upComment(text);
                }else{
                    T.showToast(v.getContext(),"请输入评论内容");
                }
            }
        });
        /**
         * 返回键
         */
        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        BizUtil.setIconFont(tv_back, "\ue620");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RelativeLayout tv_play = (RelativeLayout) findViewById(R.id.tv_play);
        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(v.getContext(),CoursePlayActivity.class);
                intent.putExtra(C.DATA,data);
                startActivity(intent);*/

//                if(BizUtil.isPkgInstalled(v.getContext(),"com.zxtech.mvp.konepluginp")){
                    playPPT(data);
//                }else{
//                    //T.showToast(v.getContext(),"还未安装播放插件");
//                    downPlugins();
//                }
            }
        });

        lv = (ListView) findViewById(R.id.lv);
        mAdapter = new CourseTalkAdapter();
        lv.setAdapter(mAdapter);
        initData();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1 && data !=null){
            String score = data.getStringExtra("score");
            System.out.println("*********************"+score);
            uploadScore(score);
        }else{
            //T.showToast(this,"出现点小意外没有获得成绩");
        }
    }

    public void playPPT(CourseVO.Data data) {

        Intent intent = new Intent(this,PlayPluginActivity.class);

        String filePath = getExternalCacheDir().getAbsolutePath();
        String fileName = DownloadUtils.getFileName(URL.getInstance().getFullUrl(data.getFile_url()));
        fileName = F.getNoExName(fileName);
        filePath = filePath + "/" + fileName + "/index_lms_html5.html";

        intent.putExtra("filePath", filePath);
        intent.putExtra("course_id", data.getCourse_id());
        //intent.setAction(plugins.get("packageName"));
        //intent.setAction("com.zxtech.mvp.konepluginp");
        startActivityForResult(intent, 1);
    }

    /**上传成绩**/
    private void uploadScore(String score){

        GsonCallBack<JsonElementVO> callBack = new GsonCallBack<JsonElementVO>(this){
            @Override
            protected void onDoSuccess(JsonElementVO vo) {
                //finish();
            }

            @Override
            public void onFailure(String info) {
                super.onFailure(info);
                //finish();
            }
        };
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("courseid",data.getCourse_id());
        params.addBodyParameter("userid", SPCache.getInstance(this).getString(C.USER_ID));
        params.addBodyParameter("credits",score);
        params.addBodyParameter("learningtime","20");
        String url = URL.getInstance().setUserLearnCourseInfoUrl();
        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    void initData(){

        GsonCallBack<CourseCommentVO> callBack = new GsonCallBack<CourseCommentVO>(this){
            @Override
            public void onStart() {
                showLoading(CourseDetailActivity.this);
            }

            @Override
            protected void onDoSuccess(CourseCommentVO bean) {
                dismissLoading();
                List<CourseCommentVO.Data> data = bean.getData();
                mAdapter.setDatas(data);
                mAdapter.notifyDataSetChanged();
            }
        };
        String url = URL.getInstance().getCourseCommentUrl(data.getCourse_id());
        GRequestData requestData = new GRequestData(Request.Method.GET,url,null);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    void upComment(String text){

        GsonCallBack<BaseVO> callBack = new GsonCallBack<BaseVO>(this){
            @Override
            public void onStart() {
                showLoading(CourseDetailActivity.this);
            }
            @Override
            protected void onDoSuccess(BaseVO bean) {
                dismissLoading();
                initData();
                editText.setText("");
            }
        };
        String url = URL.getInstance().getReplyCommentUrl();
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("ownerUserId", SPCache.getInstance(this).getString(C.USER_ID));
        params.addBodyParameter("content",text);
        params.addBodyParameter("courseId",data.getCourse_id());
        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    void upReply(String text,String targetId,String parentId){

        GsonCallBack<BaseVO> callBack = new GsonCallBack<BaseVO>(this){
            @Override
            public void onStart() {
                showLoading(CourseDetailActivity.this);
            }
            @Override
            protected void onDoSuccess(BaseVO bean) {
                dismissLoading();
                initData();
            }
        };
        String url = URL.getInstance().getReplyCommentUrl();
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("targetUserId",targetId);
        params.addBodyParameter("ownerUserId", SPCache.getInstance(this).getString(C.USER_ID));
        params.addBodyParameter("content",text);
        params.addBodyParameter("courseId",data.getCourse_id());
        params.addBodyParameter("parentId",parentId);
        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    @SuppressLint("WrongConstant")
    void initPopupWindow(final CourseCommentVO.Data data){

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

    private void downPlugins(){

        String filePath = getExternalCacheDir().getAbsolutePath()+"/lxwalk-release.apk";
        File downloadFile = new File(filePath);
        if(null != downloadFile && downloadFile.exists()) {
            downloadFile.delete();
        }

        final String url = AppConfig.getScormPluginUrl()+"lxwalk-release.apk";
        AlertDialog dialog = SimpleDialog.createDialog(this, "提示", "您还未安装播放插件，请点击确定安装！", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AlertDialog alertDialog = FileDownloadDialog.createDialog(CourseDetailActivity.this, "正在下载...", (CharSequence) null, new DownloadUtils.DownloadListener() {

                    public void onDownloadComplete(String downloadUrl, String tag) {
                        T.showToast(CourseDetailActivity.this, "下载完成");

                        String url = getExternalCacheDir().getAbsolutePath()+"/lxwalk-release.apk";
                        startInstall(CourseDetailActivity.this,url);
                    }

                    public void onDownloadFailed(String downloadUrl, String tag, int errorCode) {
                        if (errorCode == 1008) {
                            T.showToast(CourseDetailActivity.this, "下载取消");
                        } else {
                            T.showToast(CourseDetailActivity.this, "下载失败");
                        }
                    }
                    public void onProgress(long totalBytes, long downloadedBytes, int progress) {

                    }
                }, url,getExternalCacheDir().getAbsolutePath(),"lxwalk-release.apk",url);
                if (alertDialog != null) {
                    alertDialog.show();
                }
            }
        }, false);
        dialog.show();
    }

    public static void startInstall(Context context, String mUrl) {

        /*Intent install = new Intent(Intent.ACTION_VIEW);
        //install.setDataAndType(uri, "application/vnd.android.package-archive");
        install.setDataAndType(Uri.fromFile(new File(mUrl)),"application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);*/

        File file = new File(mUrl);
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        FileProvider7.setIntentDataAndType(context, intent, "application/vnd.android.package-archive", file, true);
        context.startActivity(intent);
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
