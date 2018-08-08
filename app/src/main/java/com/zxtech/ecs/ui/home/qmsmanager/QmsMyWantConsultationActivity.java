package com.zxtech.ecs.ui.home.qmsmanager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.AnswerItemDelegate;
import com.zxtech.ecs.adapter.KnowledgeDelegate;
import com.zxtech.ecs.adapter.MyFeedbackDelegate;
import com.zxtech.ecs.adapter.PictureDelegate;
import com.zxtech.ecs.adapter.QuestionItemDelegate;
import com.zxtech.ecs.adapter.RecommendDelegate;
import com.zxtech.ecs.adapter.TrackingDelegate;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.ChatMessage;
import com.zxtech.ecs.model.ChatMessage2;
import com.zxtech.ecs.model.JsonChatMsgVoice;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.company.activity.ShowBigImageSimpleActivity;
import com.zxtech.ecs.ui.home.scheme.bdspeech.CommonRecogParams;
import com.zxtech.ecs.ui.home.scheme.bdspeech.MessageStatusRecogListener;
import com.zxtech.ecs.ui.home.scheme.bdspeech.MyRecognizer;
import com.zxtech.ecs.ui.home.scheme.bdspeech.OfflineRecogParams;
import com.zxtech.ecs.ui.home.scheme.bdspeech.OnlineRecogParams;
import com.zxtech.ecs.ui.home.scheme.bdspeech.StatusRecogListener;
import com.zxtech.ecs.ui.home.scheme.bdtts.MySyntherizer;
import com.zxtech.ecs.util.GsonUtils;
import com.zxtech.ecs.util.OfficePoiUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.SoftHideKeyBoardUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.util.VibratorUtil;
import com.zxtech.ecs.widget.VoiceDialogManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static com.zxtech.ecs.APPConfig.DOWN_LOAD_PATH;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_FINISHED;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_NONE;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_READY;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_RECOGNITION;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_SPEAKING;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_STOPPED;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_WAITING_READY;

/**
 * @author syp521
 * @date:2018/2/28
 * @desc:我要咨询
 */

public class QmsMyWantConsultationActivity extends BaseActivity implements View.OnTouchListener,
        KnowledgeDelegate.KnowledgeSelectedListener, MyFeedbackDelegate.MyFeedbackSelectedListener,
        TrackingDelegate.TrackingSelectedListener {

    private final int DATA1 = 1001;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.send_content_et)
    EditText mSendContentEt;
    @BindView(R.id.send_tv)
    TextView mSendTv;
    @BindView(R.id.bottom_layout)
    LinearLayout mBottomLayout;
    @BindView(R.id.main_layout)
    LinearLayout mMainLayout;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_layout)
    LinearLayout mCommonLayout;
    @BindView(R.id.voice_btn)
    Button mVoiceBtn;

    private List<ChatMessage> mDatas = new ArrayList<>();
    private MultiItemTypeAdapter adapter;
    private JsonChatMsgVoice mVoice;
    private VoiceDialogManager voiceDialogManager;

    /**
     * 识别控制器，使用MyRecognizer控制识别的流程
     */
    protected MyRecognizer myRecognizer;
    protected CommonRecogParams apiParams;
    /**
     * 控制UI按钮的状态
     */
    private int status;
    /*
     * 本Activity中是否需要调用离线语法功能。根据此参数，判断是否需要调用SDK的ASR_KWS_LOAD_ENGINE事件
     */
    protected boolean enableOffline = false;

    protected Handler handler;

    protected MySyntherizer synthesizer;

    private boolean isVoice = false;
    private String lastAnswerItem = null;
    private AnswerItemDelegate answerItemDelegate;
    private QuestionItemDelegate questionItemDelegate;
    private KnowledgeDelegate knowledgeDelegate;
    private MyFeedbackDelegate myFeedbackDelegate;
    private TrackingDelegate trackingDelegate;
    private RecommendDelegate mRecommendDelegate;
    private PictureDelegate pictureDelegate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qms_robot;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(mToolbar);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    initData();
                } else {
                    ToastUtil.showLong("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consulation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_new) {
            Intent intent = getIntent();
            String base_url = SPUtils.get(this, "IP_ADDRESS", "").toString();
            String url = base_url + "/qms/qms_consulation.html";
            intent.putExtra("url", url);
            startActivity(HtmlActivity.class, intent);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void initData() {

        String helloStr = "Hi,我是小梯！电梯智能问答小助手。\n您可以这样使用\n\n“询问反馈单的情况”\n“使用操作指令直达相关界面”\n“查看电梯维护安装等手册”\n" +
                "“咨询技术干货和政策相关的问题”";

        SoftHideKeyBoardUtil.assistActivity(this);
        mDatas.add(new ChatMessage(helloStr, 2));
        mVoice = new JsonChatMsgVoice();

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager2);
        adapter = new MultiItemTypeAdapter(this, mDatas);
        answerItemDelegate = new AnswerItemDelegate();
        questionItemDelegate = new QuestionItemDelegate();
        adapter.addItemViewDelegate(answerItemDelegate);
        adapter.addItemViewDelegate(questionItemDelegate);
        mRecommendDelegate = new RecommendDelegate(mContext);
        adapter.addItemViewDelegate(mRecommendDelegate);
        pictureDelegate = new PictureDelegate(mContext);
        adapter.addItemViewDelegate(pictureDelegate);
        knowledgeDelegate = new KnowledgeDelegate(mContext);
        knowledgeDelegate.setListener(this);
        adapter.addItemViewDelegate(knowledgeDelegate);
        myFeedbackDelegate = new MyFeedbackDelegate(mContext);
        myFeedbackDelegate.setListener(this);
        adapter.addItemViewDelegate(myFeedbackDelegate);
        trackingDelegate = new TrackingDelegate(mContext);
        trackingDelegate.setListener(this);
        adapter.addItemViewDelegate(trackingDelegate);

        mRecommendDelegate.setListener(new RecommendDelegate.SelectedListener() {
            @Override
            public void getText(final ChatMessage.Options recommend) {

                if ("2".equals(recommend.getType())) {

                    Intent it = new Intent(QmsMyWantConsultationActivity.this, ShowBigImageSimpleActivity.class);
                    it.putExtra("url", recommend.getUrl());
                    startActivity(it);
                    //ToastUtil.showLong(recommend.getUrl(11));
                    return;
                } else if ("3".equals(recommend.getType())) {

                    String filename = recommend.getUrl().substring(recommend.getUrl().lastIndexOf("/") + 1);
                    final String fileUrlStr = DOWN_LOAD_PATH + filename;
                    showProgress();
                    progressDialog.setTint(getString(R.string.downloading));
                    BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(recommend.getUrl())
                            .setPath(fileUrlStr)
                            .setListener(new FileDownloadSampleListener() {


                                @Override
                                protected void completed(BaseDownloadTask task) {
                                    ToastUtil.showLong("下载完成");
                                    OfficePoiUtil.openFile(mContext, new File(fileUrlStr));
                                }


                                @Override
                                protected void error(BaseDownloadTask task, Throwable e) {
                                    dismissProgress();
                                }

                            });
                    baseDownloadTask.start();


                    return;
                }

//				ChatMessage chatMessage1 = new ChatMessage(recommend.getText(), 1);
                HttpFactory.getApiService()
                        .actionRequest(recommend.getId())
                        .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                        .subscribe(new DefaultObserver<BaseResponse<String>>(QmsMyWantConsultationActivity.this) {
                            @Override
                            public void onSuccess(BaseResponse<String> response) {
                                ChatMessage result = new Gson().fromJson(response.getData(), ChatMessage.class);
                                assert result != null;
                                if (result.getId() == null) {
                                    return;
                                }
                                ChatMessage chatMessage2 = new ChatMessage(recommend.getText(), 1);
                                ChatMessage chatMessage4 = new ChatMessage("", 4);
                                ChatMessage chatMessage1 = new ChatMessage(result.getMessage(), 2);
                                //ChatMessage chatMessage5 = new ChatMessage(result.getContent(), 5);
                                mDatas.add(chatMessage2);
                                mDatas.add(chatMessage1);
                                if (result.getOptions().size() != 0) {
                                    for (ChatMessage.Options options : result.getOptions()) {
                                        chatMessage4.getOptions().add(options);
                                    }
                                    mDatas.add(chatMessage4);
                                }
                                adapter.notifyDataSetChanged();
                                mRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                            }
                        });
            }
        });
        mRecyclerView.setAdapter(adapter);
        mVoiceBtn.setOnTouchListener(this);

        initBDSpeech();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //initRecog();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (myRecognizer != null)
            myRecognizer.release();
        if (synthesizer != null)
            synthesizer.release();
    }

    @OnClick({R.id.send_tv, R.id.voice_btn})
    public void onClick(View view) {
        if (view.getId() == R.id.send_tv) {
            if (TextUtils.isEmpty(mSendContentEt.getText())) {
                ToastUtil.showLong("请输入你要的问题");
                return;
            }
//			isVoice = false;
            speak(mSendContentEt.getText().toString());
            mSendContentEt.setText("");
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                stopRecognizer();
                isTouch = false;
                break;

            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.requestEach(Manifest.permission.RECORD_AUDIO)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                if (permission.granted) {
                                    // 用户已经同意该权限
                                    startRecognizer();
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                    ToastUtil.showLong( "用户拒绝了该权限");
                                } else {
                                    // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                    ToastUtil.showLong("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                                }
                            }
                        });
                //  speak("介绍一下滚动导靴吧");

                break;
        }
        return true;
    }

	/*@Override
    public void getText(String text) {
		mDatas.add(new ChatMessage(text, 2));
		adapter.notifyDataSetChanged();
		mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
	}*/

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void speak(String speak) {
//		if (isVoice) {
//			stopRecognizer();
//		}
        if (!speak.contains("错误")) {
            mDatas.add(new ChatMessage(speak.replace("\n", ""), 1));
            adapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
        } else {
            return;
        }

        mVoice.setApp(Constants.BOT_APP_ID_QMS);
        mVoice.setLanguage("CN");
        mVoice.setUserId("2800055");
        mVoice.setUtterance(speak.replace("\n", ""));
        mVoice.setAnswerItem(lastAnswerItem);

        //TODO 问完之后提供多种选择
        if (speak.contains("门")) {

            HttpFactory.getApiService()
                    .actionRequest("ID_1")
                    .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<String>>(QmsMyWantConsultationActivity.this) {
                        @Override
                        public void onSuccess(BaseResponse<String> response) {

                            ChatMessage result = new Gson().fromJson(response.getData(), ChatMessage.class);
                            assert result != null;
                            ChatMessage chatMessage4 = new ChatMessage("", 4);
                            ChatMessage chatMessage1 = new ChatMessage(result.getMessage(), 2);
                            for (ChatMessage.Options options : result.getOptions()) {
                                chatMessage4.getOptions().add(options);
                            }

                            mDatas.add(chatMessage1);
                            mDatas.add(chatMessage4);
                            adapter.notifyDataSetChanged();
                            mRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);

                        }
                    });
        } else {

            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("app", "qms");
            paramsMap.put("userId", getUserId());
            paramsMap.put("utterance", speak.replace("\n", ""));
            //paramsMap.put("utterance", "打开拍照识别装箱单");
            paramsMap.put("answerItem", "");
            paramsMap.put("language", Util.convertBotLanguage(language));
            String params = GsonUtils.toJson(paramsMap, false);

            HttpFactory.getApiService()
                    .qmsVoiceRequest(params)
                    .compose(RxHelper.<BaseResponse<ChatMessage2<Map<String, String>>>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<ChatMessage2<Map<String, String>>>>(QmsMyWantConsultationActivity.this, true) {
                        @Override
                        public void onSuccess(BaseResponse<ChatMessage2<Map<String, String>>> response) {

                            List<Map<String, String>> chatMessageKnowledgeMap = response.getData().getData();
                            String type = response.getData().getActionType();

                            if ("JumpWeb".equals(type)) {
                                ChatMessage chatMessage3 = new ChatMessage("", 3);
                                chatMessage3.setKnowledges(chatMessageKnowledgeMap);
                                mDatas.add(chatMessage3);
                                adapter.notifyDataSetChanged();
                                mRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                            } else if ("Move2FeedbackInfo".equals(type)) {
                                ChatMessage chatMessage6 = new ChatMessage("", 6);
                                chatMessage6.setKnowledges(chatMessageKnowledgeMap);
                                mDatas.add(chatMessage6);
                                adapter.notifyDataSetChanged();
                                mRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                            } else if ("Move2LogisticsInfo".equals(type)) {
                                ChatMessage chatMessage7 = new ChatMessage("", 7);
                                chatMessage7.setDataMap(chatMessageKnowledgeMap.get(0));
                                mDatas.add(chatMessage7);
                                adapter.notifyDataSetChanged();
                                mRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                            } else if ("Move2BarCode".equals(type)) {

                                if (myRecognizer != null)
                                    myRecognizer.release();
                                if (synthesizer != null)
                                    synthesizer.release();

                                Intent intent = getIntent();
                                intent.setClass(mContext, QmsMyWantFeedBackActivity.class);
                                intent.putExtra("action", "Move2BarCode");
                                startActivityForResult(intent, DATA1);

                            } else if ("Move2Photo".equals(type)) {

                                if (myRecognizer != null)
                                    myRecognizer.release();
                                if (synthesizer != null)
                                    synthesizer.release();

                                Intent intent = getIntent();
                                intent.setClass(mContext, QmsMyWantFeedBackActivity.class);
                                intent.putExtra("action", "Move2Photo");
                                startActivityForResult(intent, DATA1);
                            } else if ("no data".equals(type) || "show message".equals(type)) {
                                String message = response.getData().getData().get(0).get("text");
                                ChatMessage chatMessage2 = new ChatMessage(message, 2);
                                mDatas.add(chatMessage2);
                                adapter.notifyDataSetChanged();
                                mRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DATA1) {
            initRecog();
        }
    }

    @SuppressLint("HandlerLeak")
    private void initBDSpeech() {

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleMsg(msg);
            }
        };
        initRecog();
    }


    protected void initRecog() {
        StatusRecogListener listener = new MessageStatusRecogListener(handler);
        myRecognizer = new MyRecognizer(this, listener);
        apiParams = new OnlineRecogParams(this);
        status = STATUS_NONE;
        if (enableOffline) {
            myRecognizer.loadOfflineEngine(OfflineRecogParams.fetchOfflineParams());
        }
    }


    protected void handleMsg(Message msg) {
        switch (msg.what) { // 处理MessageStatusRecogListener中的状态回调
            case STATUS_FINISHED:
                if (msg.arg2 == 1) {
                    speak(msg.obj.toString());
//					ToastUtil.showLong(msg.obj.toString());
                }

                //故意不写break
            case STATUS_NONE:
            case STATUS_READY:
            case STATUS_SPEAKING:
            case STATUS_RECOGNITION:
                status = msg.what;
                updateBtnTextByStatus();
                break;

        }

    }

    private void updateBtnTextByStatus() {
        switch (status) {
            case STATUS_NONE:
                // btn.setText("开始录音");
                mVoiceBtn.setEnabled(true);
                break;
            case STATUS_WAITING_READY:
            case STATUS_READY:
            case STATUS_SPEAKING:
            case STATUS_RECOGNITION:
                mVoiceBtn.setEnabled(true);
                break;

            case STATUS_STOPPED:
                mVoiceBtn.setEnabled(true);
                break;
        }
    }

    private boolean isTouch;

    /**
     * 开始录音，点击“开始”按钮后调用。
     */
    protected void startRecognizer() {
        if (isTouch) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            Map<String, Object> params = apiParams.fetch(sp, language);
            //mVoiceBtn.setImageResource(R.drawable.voice_blue);
            myRecognizer.start(params);
            VibratorUtil.Vibrate(this, 70);   //震动70ms
            voiceDialogManager = new VoiceDialogManager(this);
            voiceDialogManager.show();
        }
    }

    private void stopRecognizer() {
        //mVoiceBtn.setImageResource(R.drawable.voice_grey);
        myRecognizer.stop();
        voiceDialogManager.dismiss();
    }

    @Override
    public void getText(Map<String, String> text) {

        Intent intent = getIntent();
        intent.putExtra("url", text.get("link"));
        startActivity(HtmlActivity.class, intent);
    }

    @Override
    public void getMyFeedback(Map<String, String> text) {

        Intent intent = getIntent();
        intent.putExtra("autoGuid", text.get("AutoGuid"));
        startActivity(QmsMyFeedBackDetailActivity.class, intent);
    }

    @Override
    public void getTrackingSelect(String JobNumber) {

        Intent intent = getIntent();
        intent.putExtra("JobNumber", JobNumber);
        startActivity(QmsTaskTrackingActivity.class, intent);
    }
}