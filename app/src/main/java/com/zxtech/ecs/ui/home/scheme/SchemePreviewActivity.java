package com.zxtech.ecs.ui.home.scheme;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ExpandableItemAdapter;
import com.zxtech.ecs.adapter.SchemeDimensionAdapter;
import com.zxtech.ecs.adapter.SchemeTitleExpandableAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.DropDownBean;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.model.Scheme;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.model.Voice;
import com.zxtech.ecs.model.VoiceResult;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.scheme.bdspeech.CommonRecogParams;
import com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus;
import com.zxtech.ecs.ui.home.scheme.bdspeech.MessageStatusRecogListener;
import com.zxtech.ecs.ui.home.scheme.bdspeech.MyRecognizer;
import com.zxtech.ecs.ui.home.scheme.bdspeech.OfflineRecogParams;
import com.zxtech.ecs.ui.home.scheme.bdspeech.OnlineRecogParams;
import com.zxtech.ecs.ui.home.scheme.bdspeech.StatusRecogListener;
import com.zxtech.ecs.ui.home.scheme.bdtts.InitConfig;
import com.zxtech.ecs.ui.home.scheme.bdtts.MainHandlerConstant;
import com.zxtech.ecs.ui.home.scheme.bdtts.MySyntherizer;
import com.zxtech.ecs.ui.home.scheme.bdtts.NonBlockSyntherizer;
import com.zxtech.ecs.ui.home.scheme.bdtts.UiMessageListener;
import com.zxtech.ecs.ui.home.scheme.detail.SchemeDetailActivity;
import com.zxtech.ecs.util.ConvertUtil;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.GsonUtils;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.util.VibratorUtil;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.ecs.widget.MyLeftItemDecoration;
import com.zxtech.ecs.widget.VoiceDialogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;
import io.reactivex.functions.Consumer;

import static com.zxtech.ecs.adapter.ExpandableItemAdapter.TYPE_LEVEL_0;
import static com.zxtech.ecs.adapter.ExpandableItemAdapter.TYPE_LEVEL_1;
import static com.zxtech.ecs.model.ScriptReturnParam.GONE;
import static com.zxtech.ecs.model.ScriptReturnParam.SET;

/**
 * 方案对比详情
 * Created by syp523 on 2018/3/7.
 */

public class SchemePreviewActivity extends BaseActivity implements IStatus, View.OnTouchListener, ReduceConfigDialog.OnDialogListener, MainHandlerConstant {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dimension_rv)
    RecyclerView dimension_rv;
    @BindView(R.id.param_title_rv)
    RecyclerView param_title_rv;
    @BindView(R.id.mian_layout)
    LinearLayout mian_layout;
    @BindView(R.id.voice_btn)
    FloatingActionButton voice_btn;
    @BindView(R.id.announcer)
    ImageView announcer;
    @BindView(R.id.announcer_layout)
    LinearLayout announcer_layout;


    private SchemeTitleExpandableAdapter schemeParamsTitileAdapter;
    private SchemeDimensionAdapter dimensionAdapter;


    private List<Programme> mData = new ArrayList<>();

    private int itemWidth = 0;
    private final static int INTENT_DIMENS = 001;
    private int selectedPostion = 0;
    private String lastAnswerItem = null;
    private int lastCount = 0;
    private boolean isTouch;
    private String moveTo;
    private ShowContentDialog dialog;
    //编码 位置对照关系map
    private HashMap<String, Integer> codePositionMap = new HashMap<>();
    //方案对照编号
    List<String> schemeNum = null;


    /**
     * 识别控制器，使用MyRecognizer控制识别的流程
     */
    protected MyRecognizer myRecognizer;
    protected CommonRecogParams apiParams;
    /**
     * 控制UI按钮的状态
     */
    private int status;
    protected boolean enableOffline = false;
    protected Handler handler;

    //语音合成参数

    protected Handler ttsHandler;

    protected String appId = "10817203";

    protected String appKey = "WCRlvizXdMEBqapvKRUwfCnx";

    protected String secretKey = "5ea316651520c7f8b75636e8c1a3a725";

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.ONLINE;
    protected MySyntherizer synthesizer;
    private VoiceDialogManager voiceDialogManager;
    private String budget;
    private final static int INTENT_DETAIL = 0x001;

    private ExpandableItemAdapter paramAdapter;
    private RecyclerView paramRecyclerView;
    //联动预处理map
    private Map<String, ScriptReturnParam> scriptReturnParamMap = new HashMap<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_scheme_two;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.scheme_compare));
        budget = getIntent().getStringExtra("budget");
        mData = (List<Programme>) getIntent().getSerializableExtra("datas");
        schemeNum = (List<String>) getIntent().getSerializableExtra("schemeNum");
        //计算方案宽度
        Point screenSize = DensityUtil.getScreenSize(mContext);
        itemWidth = (screenSize.x - DensityUtil.dip2px(mContext, 80)) / 2;

        //参数区
        ArrayList<MultiItemEntity> multiItemEntities = generateData(mData.get(0));
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        param_title_rv.setLayoutParams(new LinearLayout.LayoutParams(itemWidth * mData.size(), LinearLayout.LayoutParams.WRAP_CONTENT));
        param_title_rv.setLayoutManager(linearLayoutManager1);
        schemeParamsTitileAdapter = new SchemeTitleExpandableAdapter(multiItemEntities, language);
        param_title_rv.addItemDecoration(new MyItemDecoration(1));
        param_title_rv.setAdapter(schemeParamsTitileAdapter);
        schemeParamsTitileAdapter.expandAll();


        paramRecyclerView = new RecyclerView(mContext);
        paramRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(itemWidth * mData.size() + 3, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        paramRecyclerView.setLayoutManager(linearLayoutManager);
        paramRecyclerView.setBackgroundColor(getResources().getColor(R.color.line));

        paramAdapter = new ExpandableItemAdapter(generateData());
        paramAdapter.setLanguage(language);
        paramRecyclerView.addItemDecoration(new MyItemDecoration(1));
        paramRecyclerView.setAdapter(paramAdapter);

        mian_layout.addView(paramRecyclerView);
        paramAdapter.expandAll();

        voice_btn.setOnTouchListener(this);
        //维度价格区
        LinearLayoutManager dimensionlinearLayoutManager = new LinearLayoutManager(this);
        dimensionlinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dimension_rv.setLayoutManager(dimensionlinearLayoutManager);

        dimensionAdapter = new SchemeDimensionAdapter(R.layout.item_scheme_dimensions, mData, itemWidth, schemeNum);
        dimension_rv.addItemDecoration(new MyLeftItemDecoration(1));
        dimension_rv.setAdapter(dimensionAdapter);

        dimensionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                selectedPostion = position;
                final Programme programme = mData.get(position);
                if (view.getId() == R.id.reduce_layout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(R.string.price_change).setMessage(getString(R.string.msg5) + Util.numberFormat(programme.getPrice()) + "," + getString(R.string.msg6))
                            .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setNegativeButton(mContext.getString(R.string.reduction), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reduceConfig(position);
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else if (view.getId() == R.id.look_tv) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, SchemeDetailActivity.class);
                    intent.putExtra("datas", convertParam(position));
                    if (getIntent().hasExtra("edit_operation")) { //新建
                        intent.putExtra("edit_operation", true);
                    }
                    startActivityForResult(intent, INTENT_DETAIL);
                } else {

                    Intent it = new Intent(mContext, SchemeRedarActivity.class);
                    HashMap<String, String> paramMap = convertParam(position);
                    it.putExtra("data", paramMap);
                    it.putExtra("budget", budget);
                    startActivityForResult(it, INTENT_DIMENS);
                }
            }
        });


        schemeParamsTitileAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (adapter.getItemViewType(position)) {
                    case TYPE_LEVEL_1:
                        Programme.DimensionsBean.ParamsBean paramsBean = (Programme.DimensionsBean.ParamsBean) adapter.getItem(position);
                        if (paramsBean.isHasDescription()) {
                            VoiceResult.ResultBean.MessagesBean message = new VoiceResult.ResultBean.MessagesBean();
                            message.setMsg_text(paramsBean.getDescription());
                            message.setTitle(paramsBean.getName());
                            if (TextUtils.isEmpty(paramsBean.getVideoPath())) {
                                message.setConfig(1);
                            } else {
                                message.setConfig(4);
                            }
                            message.getMsg_video().add(paramsBean.getImagePath());
                            message.getMsg_video().add(paramsBean.getVideoPath());

                            synthesizer.speak(paramsBean.getDescription());
                            showDialog(message, false);
                        }
                        break;
                    case TYPE_LEVEL_0:
                        Programme.DimensionsBean testParent = (Programme.DimensionsBean) adapter.getItem(position);
                        if (testParent.isExpanded()) {
                            //合并
                            paramAdapter.collapse(position, false);
                            schemeParamsTitileAdapter.collapse(position, false);
                            testParent.setExpanded(false);

                        } else {
                            //展开
                            paramAdapter.expand(position, false);
                            schemeParamsTitileAdapter.expand(position, false);
                            testParent.setExpanded(true);

                        }
                        break;

                }

            }
        });
        initBDTTS();
        initBDSpeech();
        syncScroll();
        paramCick();

    }


    private void paramCick() {

        paramAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (adapter.getItemViewType(position)) {
                    case TYPE_LEVEL_1:
                        Scheme.ParamsBeans paramsBeans = (Scheme.ParamsBeans) adapter.getItem(position);
                        switch (view.getId()) {
                            case R.id.title1:
                                showWindow((TextView) view, paramsBeans.getParamsBeans().get(0), 0, position);
                                break;
                            case R.id.title2:
                                showWindow((TextView) view, paramsBeans.getParamsBeans().get(1), 1, position);
                                break;
                            case R.id.title3:
                                showWindow((TextView) view, paramsBeans.getParamsBeans().get(2), 2, position);
                                break;
                        }
                        break;
                }
            }
        });
    }

    private void showWindow(TextView view, Programme.DimensionsBean.ParamsBean paramsBean, int number, int position) {
        if (Constants.WIDGET_TYPE_INPUT.equals(paramsBean.getDataType())) {
            showEditwindow(view, paramsBean, number, position);
        } else if (Constants.WIDGET_TYPE_SELECT.equals(paramsBean.getDataType())) {
            requestOption( view, paramsBean, number, position);
        }
    }


    private void requestOption(final TextView tv, final Programme.DimensionsBean.ParamsBean paramsBean, final int count, final int position) {
        baseResponseObservable = HttpFactory.getApiService().getOptions(paramsBean.getProECode(), mData.get(count).getElevatorType(), mData.get(count).getElevatorProduct());
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<Parameters.Option>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Parameters.Option>>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Parameters.Option>> response) {
                        showPopwindow(tv, response.getData(), paramsBean, count, position);
                    }
                });
    }

    private void showEditwindow(final TextView tv, final Programme.DimensionsBean.ParamsBean paramsBean, final int count, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = getLayoutInflater();
        final View dialog_edit = inflater.inflate(R.layout.dialog_edit, null);
        final EditText et_userName = dialog_edit.findViewById(R.id.content_et);
        et_userName.setHint(paramsBean.getText());
        et_userName.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(dialog_edit).setTitle(paramsBean.getName(language))
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = et_userName.getText().toString();
                        if (TextUtils.isEmpty(text)) {
                            return;
                        }
                        paramsBean.setValue(text);
                        paramsBean.setText(text);
                        paramsBean.setText_En(text);
                        tv.setText(text);
                        String code = paramsBean.getProECode();
                        lastCount = count;
                        if (paramsBean.getScriptInfo() == null || paramsBean.getScriptInfo().size() == 0) {
                            process(code, count, position);
                        } else {
                            scriptHandle(paramsBean, count, position);
                        }

                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void showPopwindow(final TextView tv, final List<Parameters.Option> data, final Programme.DimensionsBean.ParamsBean paramsBean, final int count, final int position) {


        if (scriptReturnParamMap.get(paramsBean.getProECode()) != null) {
            ScriptReturnParam scriptReturnParam = scriptReturnParamMap.get(paramsBean.getProECode());
            if (scriptReturnParam.getOperation() == ScriptReturnParam.DISABLE) { //禁用
                return;
            } else if (scriptReturnParam.getOperation() == ScriptReturnParam.RESET) {
                if (data != null) {
                    data.clear();
                    data.addAll(scriptReturnParam.getResetOptions());
                }
            }
        }

        if (data.size() == 0) {
            ToastUtil.showLong(getString(R.string.msg8));
            return;
        }

        DropDownWindow mWindow = new DropDownWindow(this, tv, data, itemWidth, -2) {

            @Override
            public void initEvents(final int p) {
                this.dismiss();

                final String key = data.get(p).getValue();
                String text = data.get(p).getText();
                paramsBean.setValue(key);
                paramsBean.setText(text);
                paramsBean.setText_En(text);
                tv.setText(text);
                lastCount = count;
                String code = paramsBean.getProECode();
                //判断是否有联动
                if (paramsBean.getScriptInfo() == null || paramsBean.getScriptInfo().size() == 0) {
                    process(code, count, position);
                } else {
                    scriptHandle(paramsBean, count, position);
                }


            }

            @Override
            public void dismissEvents() {

            }
        };

    }


    private void reduceConfig(int number) {

        ReduceConfigDialog reduceConfigDialog = ReduceConfigDialog.newInstance();
        HashMap<String, String> paramMap = convertParam(number);
        HashMap<String, String> paramTextMap = convertParamText(number);
        reduceConfigDialog.setRawMap(paramMap);
        reduceConfigDialog.setRawTextMap(paramTextMap);
        reduceConfigDialog.setBudget(this.budget);
        reduceConfigDialog.setOnDialogListener(this);
        reduceConfigDialog.show(getFragmentManager(), "reduce_config");

    }


    private void syncScroll() {

        param_title_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    paramRecyclerView.scrollBy(dx, dy);
                }
            }
        });

        paramRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    param_title_rv.scrollBy(dx, dy);
                }
            }
        });


    }

    private void initBDTTS() {
        ttsHandler = new Handler() {
            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handle(msg);
            }

        };
        SpeechSynthesizerListener listener = new UiMessageListener(ttsHandler);

        Map<String, String> params = getParams();


        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        InitConfig initConfig = new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);


        synthesizer = new NonBlockSyntherizer(this, initConfig, ttsHandler); // 此处可以改为MySyntherizer 了解调用过程
    }

    //语音合成回调
    protected void handle(Message msg) {
        int what = msg.what;
        switch (what) {
            case INIT_SPEECH_SUCCESS:
                // ToastUtil.showLong("语音播放已启动");
                break;
            case SPEECHFINISH:
                if (dialog != null) {
                    dialog.stopAnnouncer();
                }
                break;
        }
    }

    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_VOLUME, "8");
        // 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");

        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        return params;
    }


    private void initBDSpeech() {

        handler = new Handler() {

            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleMsg(msg);
            }

        };
        initRecog();
    }

    protected void handleMsg(Message msg) {
        switch (msg.what) { // 处理MessageStatusRecogListener中的状态回调
            case STATUS_FINISHED:
                if (msg.arg2 == 1) {
                    if (voiceDialogManager != null) {
                        voiceDialogManager.dismiss();
                    }
                    if (!msg.obj.toString().startsWith("识别错误")) {
                        String speakMsg = msg.obj.toString().replace("\n", "");
                        speak(speakMsg);
                        ToastUtil.showLong(speakMsg);
                    } else {
                        ToastUtil.showLong(getString(R.string.msg33));
                    }

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
                voice_btn.setEnabled(true);
                break;
            case STATUS_WAITING_READY:
            case STATUS_READY:
                VibratorUtil.Vibrate(this, 70);   //震动70ms
                voiceDialogManager = new VoiceDialogManager(this);
                voiceDialogManager.show();
                break;
            case STATUS_SPEAKING:
            case STATUS_RECOGNITION:
                voice_btn.setEnabled(true);
                break;

            case STATUS_STOPPED:
                voice_btn.setEnabled(true);
                break;
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_DIMENS && resultCode == 100) {
            Programme programme = (Programme) data.getSerializableExtra("data");
            this.mData.set(selectedPostion, programme);
            HashMap<String, Programme.DimensionsBean.ParamsBean> paramMap = ConvertUtil.convertParamMap(programme);

            for (int i = 0; i < paramAdapter.getData().size(); i++) {

                if (paramAdapter.getItemViewType(i) == TYPE_LEVEL_0) {
                    Scheme scheme = (Scheme) paramAdapter.getItem(i);
                    List<Scheme.ParamsBeans> subItems = scheme.getSubItems();
                    for (int j = 0; j < subItems.size(); j++) {
                        Programme.DimensionsBean.ParamsBean paramsBean = subItems.get(j).getParamsBeans().get(selectedPostion);
                        Programme.DimensionsBean.ParamsBean newParamsBean = paramMap.get(paramsBean.getProECode());
                        paramsBean.setText(newParamsBean.getText());
                        paramsBean.setText_En(newParamsBean.getText_En());
                        paramsBean.setValue(newParamsBean.getValue());
                    }
                }
            }

            paramAdapter.notifyDataSetChanged();
            dimensionAdapter.notifyDataSetChanged();

        } else if (requestCode == INTENT_DETAIL && resultCode == 2) {
            Programme bean = (Programme) data.getSerializableExtra("data");
            Intent intent = getIntent();
            intent.putExtra("data", bean);
            if (schemeNum != null) {
                String changePosition = schemeNum.get(selectedPostion);
                intent.putExtra("changePosition", Integer.valueOf(changePosition));
            }
            setResult(3, intent);
            finish();
        }

    }

    private ArrayList<MultiItemEntity> generateData(Programme programme) {


        ArrayList<MultiItemEntity> res = new ArrayList<>();
        List<Programme.DimensionsBean> dimensions = programme.getDimensions();
        int position = 0;
        for (int i = 0; i < dimensions.size(); i++) {
            Programme.DimensionsBean dimensionsBean = dimensions.get(i);
            codePositionMap.put(dimensionsBean.getProECode(), position);
            position++;
            for (int j = 0; j < dimensionsBean.getParams().size(); j++) {
                Programme.DimensionsBean.ParamsBean paramsBean = dimensionsBean.getParams().get(j);
                dimensionsBean.addSubItem(paramsBean);
                codePositionMap.put(paramsBean.getProECode(), position);
                position++;
            }
            res.add(dimensionsBean);
        }
        return res;
    }

    private ArrayList<MultiItemEntity> generateData() {
        //转成listmap
        List<HashMap<String, Programme.DimensionsBean.ParamsBean>> paramMapList = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            HashMap<String, Programme.DimensionsBean.ParamsBean> paramMap = ConvertUtil.convertParamBean(mData.get(i));
            paramMapList.add(paramMap);
        }
        //取出标题
        Scheme scheme = null;
        ArrayList<MultiItemEntity> res = new ArrayList<>();

        List<Programme.DimensionsBean> dimensions = mData.get(0).getDimensions();
        for (int i = 0; i < dimensions.size(); i++) {
            Programme.DimensionsBean dimensionsBean = dimensions.get(i);
            scheme = new Scheme();
            for (int j = 0; j < dimensionsBean.getParams().size(); j++) {
                Programme.DimensionsBean.ParamsBean paramsBean = dimensionsBean.getParams().get(j);
                Scheme.ParamsBeans scParamsBeans = new Scheme.ParamsBeans();
                for (int k = 0; k < paramMapList.size(); k++) {
                    HashMap<String, Programme.DimensionsBean.ParamsBean> stringParamsBeanHashMap = paramMapList.get(k);
                    if (paramsBean.getProECode() != null) {
                        scParamsBeans.getParamsBeans().add(stringParamsBeanHashMap.get(paramsBean.getProECode()));
                    } else {
                        scParamsBeans.getParamsBeans().add(new Programme.DimensionsBean.ParamsBean());
                    }
                }
                scheme.addSubItem(scParamsBeans);
            }

            res.add(scheme);
        }

        return res;
    }


    private void speak(String speak) {
        Voice voice = new Voice();
        voice.setApp(Constants.BOT_APP_ID);
        voice.setLanguage(Util.convertBotLanguage(language));
        voice.setUserId(getUserId());
        voice.setBusiId("elevator");
        voice.setAnswerItem(lastAnswerItem);
        voice.setUtterance(speak.replace("\n", ""));
        HashMap<String, String> paramMap = convertParam(lastCount);
        voice.getBean().getElevatorParameter().putAll(paramMap);
        baseResponseObservable = HttpFactory.getApiService().voiceRequest(GsonUtils.createSerializeNullsGson().toJson(voice));
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        Gson gson = new Gson();
                        JsonObject jsonData = gson.fromJson(response.getData(), JsonObject.class);
                        Map<String, String> schemeResult = gson.fromJson(jsonData.getAsJsonObject("schemeResult"), Map.class);
                        VoiceResult voiceResult = gson.fromJson(jsonData.getAsJsonObject("voiceResult"), VoiceResult.class);
                        actionHandle(voiceResult, schemeResult, lastCount);
                    }
                });
    }


    private HashMap<String, String> convertParamText(int number) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < paramAdapter.getData().size(); i++) {

            if (paramAdapter.getItemViewType(i) == TYPE_LEVEL_0) {
                Scheme scheme = (Scheme) paramAdapter.getItem(i);
                List<Scheme.ParamsBeans> subItems = scheme.getSubItems();
                for (int j = 0; j < subItems.size(); j++) {
                    Programme.DimensionsBean.ParamsBean paramsBean = subItems.get(j).getParamsBeans().get(number);
                    map.put(paramsBean.getProECode(), paramsBean.getText(language));
                }
            }
        }
        return map;
    }

    private HashMap<String, String> convertParam(int number) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < paramAdapter.getData().size(); i++) {

            if (paramAdapter.getItemViewType(i) == TYPE_LEVEL_0) {
                Scheme scheme = (Scheme) paramAdapter.getItem(i);
                List<Scheme.ParamsBeans> subItems = scheme.getSubItems();
                for (int j = 0; j < subItems.size(); j++) {
                    Programme.DimensionsBean.ParamsBean paramsBean = subItems.get(j).getParamsBeans().get(number);
                    map.put(paramsBean.getProECode(), paramsBean.getValue());
                }
            }
        }

        for (Programme.DimensionsBean dimensionsBean : mData.get(number).getDimensions()) {
            map.put(dimensionsBean.getProECode(), dimensionsBean.getValue());
        }
        map.put(Constants.CODE_PRICE, String.valueOf(mData.get(number).getPrice()));
        map.put(Constants.CODE_ELEVATORTYPE, String.valueOf(mData.get(number).getElevatorType()));
        map.put(Constants.CODE_ELEVATORPRODUCT, String.valueOf(mData.get(number).getElevatorProduct()));
        map.put("PriceDrawingNo", String.valueOf(mData.get(number).getPriceDrawingNo()));
        map.put("SalesParameterDrawingNo", String.valueOf(mData.get(number).getSalesParameterDrawingNo()));
        return map;
    }


    private void process(String triggerCode, final int number, final int position) {

        paramAdapter.notifyItemChanged(position); //处理不同

        Gson gson = new Gson();
        Voice voice = new Voice();
        voice.setApp(Constants.BOT_APP_ID);
        voice.setLanguage(Util.convertBotLanguage(language));
        voice.setUserId(getUserId());
        voice.setBusiId("elevator");
        voice.setAnswerItem(lastAnswerItem);
        voice.setTrigger(triggerCode);
        HashMap<String, String> paramMap = convertParam(number);
        voice.getBean().getElevatorParameter().putAll(paramMap);

        baseResponseObservable = HttpFactory.getApiService().processRequest(gson.toJson(voice));
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        // actionHandle(response.getData(), position);
                        Gson gson = new Gson();
                        JsonObject jsonData = gson.fromJson(response.getData(), JsonObject.class);
                        Map<String, String> schemeResult = gson.fromJson(jsonData.getAsJsonObject("schemeResult"), Map.class);
                        VoiceResult voiceResult = gson.fromJson(jsonData.getAsJsonObject("voiceResult"), VoiceResult.class);
                        actionHandle(voiceResult, schemeResult, number);
                    }
                });
    }


    //语音 或 操作后续处理
    private void actionHandle(VoiceResult voiceResult, Map<String, String> schemeResult, int number) {
        VoiceResult.ResultBean result = voiceResult.getResult();
        for (Programme.DimensionsBean dimensionsBean : mData.get(number).getDimensions()) {
            String value = schemeResult.get(dimensionsBean.getProECode());
            dimensionsBean.setValue(value);
        }
        if (schemeResult.get(Constants.CODE_PRICE) != null) {
            mData.get(number).setPrice(Integer.parseInt(schemeResult.get(Constants.CODE_PRICE)));
            dimensionAdapter.notifyDataSetChanged();
        }

        moveTo = result.getMoveTo();
        speakCompleteHandle();

        if (!TextUtils.isEmpty(result.getAnswerItem())) {
            lastAnswerItem = result.getAnswerItem();
        }
        String state = result.getState();
        if (Constants.BOT_STATE_PROCESS.equals(state)) {
            //
        } else {
            if (Constants.VOICE_RESULT_ACTION_MESSAGE.equals(result.getType())) { //弹出消失
                if (result.getMessages() != null && result.getMessages().size() > 0) {
                    VoiceResult.ResultBean.MessagesBean messagesBean = result.getMessages().get(0);
                    showDialog(messagesBean, true);
                    synthesizer.speak(messagesBean.getMsg_text());
                }
            } else if (Constants.VOICE_RESULT_ACTION_ACTION.equals(result.getType())) { //修改方案值
                if (result.getActions() != null) {
                    for (Map<String, String> map : result.getActions()) {
                        String key = map.get("key");
                        String value = map.get("value");
                        int pos = codePositionMap.get(key);
                        MultiItemEntity item = paramAdapter.getItem(pos);
                        Scheme.ParamsBeans paramsBeans = (Scheme.ParamsBeans) item;
                        paramsBeans.getParamsBeans().get(number).setValue(value);
                        paramAdapter.notifyItemChanged(pos);
                    }
                }
            } else if (Constants.VOICE_RESULT_ACTION_AM.equals(result.getType())) {
                if (result.getActions() != null) {
                    for (Map<String, String> map : result.getActions()) {
                        String key = map.get("key");
                        String value = map.get("value");
                        int pos = codePositionMap.get(key);
                        MultiItemEntity item = paramAdapter.getItem(pos);
                        Scheme.ParamsBeans paramsBeans = (Scheme.ParamsBeans) item;
                        paramsBeans.getParamsBeans().get(number).setValue(value);
                        paramAdapter.notifyItemChanged(pos);
                    }
                }
                if (result.getMessages() != null && result.getMessages().size() > 0) {
                    VoiceResult.ResultBean.MessagesBean messagesBean = result.getMessages().get(0);
                    showDialog(messagesBean, true);
                    synthesizer.speak(messagesBean.getMsg_text());
                }
            }
        }
    }

    private void showDialog(VoiceResult.ResultBean.MessagesBean message, final boolean isRun) {
        dialog = new ShowContentDialog(mContext, message) {
            @Override
            public void dismiss() {
                super.dismiss();
//                if (isRun) {
//                    announcer.setVisibility(View.VISIBLE);
//                    Glide.with(mContext).load(R.drawable.male_announcer_stop).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(announcer);
//                    speakCompleteHandle();
//                } else {
//                    announcer.setVisibility(View.GONE);
//                }
                synthesizer.stop();
            }
        };
        dialog.show();
    }

    private void speakCompleteHandle() {

        if (!TextUtils.isEmpty(moveTo)) {
            announcer.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(R.drawable.male_announcer_stop).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(announcer);
            Integer moveToPosition = codePositionMap.get(moveTo);
            if (moveToPosition != null) {
                ((LinearLayoutManager) param_title_rv.getLayoutManager()).scrollToPositionWithOffset(moveToPosition, 0);
                ((LinearLayoutManager) paramRecyclerView.getLayoutManager()).scrollToPositionWithOffset(moveToPosition, 0);
                int[] startLocation = new int[2];
                int[] endLocation = new int[2];
                announcer.getLocationInWindow(startLocation);
                param_title_rv.getLocationInWindow(endLocation);
                runAnim(endLocation[0] - startLocation[0], endLocation[1] - startLocation[1] - announcer.getHeight());
            }

        }
    }


    private void runAnim(final int atX, final int atY) {

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, atY);
        AnimationSet set = new AnimationSet(false);
        set.setFillBefore(true);
        set.setDuration(800);// 动画的执行时间
        set.addAnimation(translateAnimationY);
        announcer_layout.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0 + announcer_layout.getLeft(), atY + announcer_layout.getTop(), 0, 0);
                announcer_layout.setLayoutParams(layoutParams);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
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
                                    ToastUtil.showLong(getString(R.string.msg12));
                                } else {
                                    // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                    ToastUtil.showLong(getString(R.string.msg13));
                                }
                            }
                        });


                break;
        }
        return true;
    }

    /**
     * 开始录音，点击“开始”按钮后调用。
     */
    protected void startRecognizer() {
        if (isTouch) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            Map<String, Object> params = apiParams.fetch(sp, language);
            voice_btn.setImageResource(R.drawable.voice_blue);
            myRecognizer.start(params);
        }
    }

    private void stopRecognizer() {
        voice_btn.setImageResource(R.drawable.voice_grey);
        myRecognizer.stop();

    }

    @Override
    protected void onDestroy() {
        if (myRecognizer != null)
            myRecognizer.release();
        if (synthesizer != null)
            synthesizer.release();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    //减配回调
    @Override
    public void onDialogClick(Programme programme) {
        this.mData.set(selectedPostion, programme);
        HashMap<String, Programme.DimensionsBean.ParamsBean> paramMap = ConvertUtil.convertParamMap(programme);
        for (int i = 0; i < paramAdapter.getData().size(); i++) {

            if (paramAdapter.getItemViewType(i) == TYPE_LEVEL_0) {
                Scheme scheme = (Scheme) paramAdapter.getItem(i);
                List<Scheme.ParamsBeans> subItems = scheme.getSubItems();
                for (int j = 0; j < subItems.size(); j++) {
                    Programme.DimensionsBean.ParamsBean paramsBean = subItems.get(j).getParamsBeans().get(selectedPostion);
                    Programme.DimensionsBean.ParamsBean newParamsBean = paramMap.get(paramsBean.getProECode());
                    paramsBean.setText(newParamsBean.getText());
                    paramsBean.setText_En(newParamsBean.getText_En());
                    paramsBean.setValue(newParamsBean.getValue());
                }
            }
        }
        paramAdapter.notifyDataSetChanged();
        dimensionAdapter.notifyDataSetChanged();
        // compareData();
    }


    //联动请求
    private void scriptHandle(final Programme.DimensionsBean.ParamsBean parameters, final int count, final int position) {
        HashMap<String, String> paramMap = convertParam(count);
        JsonArray array = new JsonArray();
        for (Programme.DimensionsBean.ScriptInfo info : parameters.getScriptInfo()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("ScriptName", info.getScriptName());
            jsonObject.addProperty(Constants.CODE_ELEVATORPRODUCT, paramMap.get(Constants.CODE_ELEVATORPRODUCT));
            jsonObject.addProperty(Constants.CODE_ELEVATORTYPE, paramMap.get(Constants.CODE_ELEVATORTYPE));
            jsonObject.addProperty("ControlParamList", info.getCustomScripts());
            String[] sptScripts = info.getCustomScripts().split(",");
            for (int i = 0; i < sptScripts.length; i++) {
                String code = sptScripts[i];
                String value = paramMap.get(code);
                jsonObject.addProperty(code, value);
            }
            array.add(jsonObject);
        }


        baseResponseObservable = HttpFactory.getApiService().scriptParamsLink(array.toString());
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<ScriptReturnParam>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ScriptReturnParam>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<ScriptReturnParam>> response) {
                        List<ScriptReturnParam> data = response.getData();
                        //联动返回结果
                        if (data.size() == 0) {
                            process(parameters.getProECode(), count, position);
                            return;
                        }

                        for (ScriptReturnParam returnParam : data) {
                            if (GONE == returnParam.getOperation()) continue;//不处理
                            if (SET == returnParam.getOperation()) { //处理赋值情况
                                int pos = codePositionMap.get(returnParam.getParamCode());
                                MultiItemEntity item = paramAdapter.getItem(pos);
                                Scheme.ParamsBeans paramsBeans = (Scheme.ParamsBeans) item;
                                Programme.DimensionsBean.ParamsBean paramsBean = paramsBeans.getParamsBeans().get(count);
                                paramsBean.setValue(returnParam.getFirstParamValue());
                                paramAdapter.notifyItemChanged(pos);
                            } else if (returnParam.getParamCode() != null) {
                                scriptReturnParamMap.put(returnParam.getParamCode(), returnParam);
                            }
                        }
                        process(parameters.getProECode(), count, position);
                    }
                });
    }
}
