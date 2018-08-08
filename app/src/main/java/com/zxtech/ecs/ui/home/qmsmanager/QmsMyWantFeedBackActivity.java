package com.zxtech.ecs.ui.home.qmsmanager;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.PhotoAdapter;
import com.zxtech.ecs.model.ContractInfo;
import com.zxtech.ecs.model.FileList;
import com.zxtech.ecs.model.GenerateAnswer;
import com.zxtech.ecs.model.JsonEntity;
import com.zxtech.ecs.model.JsonSaveFeedBackEntity;
import com.zxtech.ecs.model.JsonSubSystemEntity;
import com.zxtech.ecs.model.JsonSystemCodeSXMSEntity;
import com.zxtech.ecs.model.OcrResult;
import com.zxtech.ecs.model.QmsAddressList;
import com.zxtech.ecs.model.QmsFeedbackInfoEntity;
import com.zxtech.ecs.model.QmsMaterialRequirement;
import com.zxtech.ecs.model.SaveAPPFeedbackInfoEntity;
import com.zxtech.ecs.model.SystemCodeListBean;
import com.zxtech.ecs.model.SystemCodeListEntity;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.qmsmanager.baidu.ocr.RecognizeService;
import com.zxtech.ecs.ui.home.scheme.bdspeech.CommonRecogParams;
import com.zxtech.ecs.ui.home.scheme.bdspeech.MessageStatusRecogListener;
import com.zxtech.ecs.ui.home.scheme.bdspeech.MyRecognizer;
import com.zxtech.ecs.ui.home.scheme.bdspeech.OfflineRecogParams;
import com.zxtech.ecs.ui.home.scheme.bdspeech.OnlineRecogParams;
import com.zxtech.ecs.ui.home.scheme.bdspeech.StatusRecogListener;
import com.zxtech.ecs.ui.home.scheme.bdtts.InitConfig;
import com.zxtech.ecs.ui.home.scheme.bdtts.MySyntherizer;
import com.zxtech.ecs.ui.home.scheme.bdtts.NonBlockSyntherizer;
import com.zxtech.ecs.ui.home.scheme.bdtts.UiMessageListener;
import com.zxtech.ecs.util.DateUtil;
import com.zxtech.ecs.util.FileUtil;
import com.zxtech.ecs.util.GsonUtils;
import com.zxtech.ecs.util.ImageUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.util.VibratorUtil;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.SelectDialog;
import com.zxtech.ecs.widget.VoiceDialogManager;
import com.zxtech.gks.common.ToolUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.zxtech.ecs.common.Constants.CAMERA_REQUEST;
import static com.zxtech.ecs.common.Constants.ELEVATOR;
import static com.zxtech.ecs.common.Constants.PHOTO_REQUEST_CUT;
import static com.zxtech.ecs.common.Constants.PHOTO_REQUEST_GALLERY;
import static com.zxtech.ecs.common.Constants.PHOTO_REQUEST_GALLERY_CODE3;
import static com.zxtech.ecs.common.Constants.REQUEST_VIDEO_CODE;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_FINISHED;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_NONE;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_READY;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_RECOGNITION;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_SPEAKING;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_STOPPED;
import static com.zxtech.ecs.ui.home.scheme.bdspeech.IStatus.STATUS_WAITING_READY;
import static com.zxtech.ecs.ui.home.scheme.bdtts.MainHandlerConstant.PRINT;
import static com.zxtech.ecs.ui.home.scheme.bdtts.MainHandlerConstant.UI_CHANGE_INPUT_TEXT_SELECTION;
import static com.zxtech.ecs.ui.home.scheme.bdtts.MainHandlerConstant.UI_CHANGE_SYNTHES_TEXT_SELECTION;
import static com.zxtech.ecs.util.Util.convertBotLanguage;
import static com.zxtech.ecs.util.Util.convertQmsLanguage;
import static com.zxtech.ecs.util.Util.hideSoftInput;

/**
 * @auth: hexl
 * @date: 2018/2/27
 * @desc: 我要反馈
 */

public class QmsMyWantFeedBackActivity extends BaseActivity implements View.OnTouchListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_qms_submit)
    TextView mTvQmsSubmit;
    @BindView(R.id.et_qms_contract_no)
    EditText mEtQmsContractNo;
    @BindView(R.id.et_qms_elevator_source)
    EditText mEtQmsElevatorSource;
    @BindView(R.id.tv_qms_service_status)
    TextView mTvQmsServiceStatus;
    @BindView(R.id.tv_qms_open_box_time)
    TextView mTvQmsOpenBoxTime;
    @BindView(R.id.et_qms_product_no)
    EditText mEtQmsProductNo;
    @BindView(R.id.tv_qms_assembly)
    TextView mTvQmsAssembly;
    @BindView(R.id.tv_qms_parts)
    TextView mTvQmsParts;
    @BindView(R.id.tv_qms_spare_parts)
    TextView mTvQmsSpareParts;
    @BindView(R.id.tv_qms_invalid_mode)
    TextView mTvQmsInvalidMode;
    @BindView(R.id.et_qms_question_desc)
    EditText mEtQmsQuestionDesc;
    @BindView(R.id.tv_qms_sub_system)
    TextView mTvQmsSubSystem;
    @BindView(R.id.recycler_view_qms)
    RecyclerView mRecyclerViewQms;
    @BindView(R.id.iv_qms_add_img)
    ImageView mIvQmsAddImg;
    @BindView(R.id.voice_btn)
    FloatingActionButton voice_btn;
    @BindView(R.id.ll_un_materiel)
    LinearLayout mLlUnMateriel;
    @BindView(R.id.ll_materiel)
    RelativeLayout mLlMateriel;
    @BindView(R.id.tv_edit)
    TextView tv_edit;
    @BindView(R.id.recycleView_mr)
    RecyclerView recycleView;
    @BindView(R.id.rl_change_address)
    RelativeLayout rl_change_address;
    @BindView(R.id.project_name)
    EditText project_name;

    //非物料需求
    //补件信息
    @BindView(R.id.tv_qms_reason)
    TextView tv_qms_reason;
    @BindView(R.id.tv_qms_frequency)
    TextView tv_qms_frequency;
    @BindView(R.id.tv_qms_severity)
    TextView tv_qms_severity;

    //补件信息
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.customer_phone)
    TextView customer_phone;
    @BindView(R.id.company_tv)
    TextView company_tv;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_label)
    TextView tv_label;
    @BindView(R.id.tv_default)
    TextView tv_default;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.iv_qms_add_img_system)
    ImageView iv_qms_add_img_system;
    @BindView(R.id.iv_qms_img_code)
    ImageView iv_qms_img_code;

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;

    private String mPhotoPath;
    private List<SystemCodeListBean> mCodeLists;
    private List<SystemCodeListBean> mServiceStatusLists;
    private List<SystemCodeListBean> mSubSystemCodeLists;
    private List<SystemCodeListBean> mAssemblyCodeLists;
    private List<SystemCodeListBean> mPartsCodeLists;
    private List<SystemCodeListBean> mSparePartsCodeLists;
    private List<SystemCodeListBean> mInvalidModeCodeLists;

    private List<SystemCodeListBean> mReasonCodeLists = new ArrayList<>();
    private List<SystemCodeListBean> mFrequencyLists = new ArrayList<>();
    private List<SystemCodeListBean> mSeverityLists = new ArrayList<>();

    private List<QmsMaterialRequirement> qmsMrs = new ArrayList<>();
    //附件适配器
    private PhotoAdapter mPhotoAdapter;
    private QmsMrAdapter qmsMrAdapter;
    private List<FileList> mFileLists = new ArrayList<>();
    private List<FileList> localFileLists = new ArrayList<>();
    private List<Map<String, Integer>> deleteFiles = new ArrayList<>();

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

    protected Handler ttsHandler;

    protected String appId = "10817203";

    protected String appKey = "WCRlvizXdMEBqapvKRUwfCnx";

    protected String secretKey = "5ea316651520c7f8b75636e8c1a3a725";

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.ONLINE;
    protected MySyntherizer synthesizer;

    private boolean hasGotToken = false;
    private AlertDialog.Builder alertDialog;

    private String supportType;
    private String qmsLan;

    private QmsAddressList.QmsAddress qmsAddress;

    private VoiceDialogManager voiceDialogManager;

    private String AutoGuid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qms_my_want_feedback;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(mToolbar);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {

                } else {
                    ToastUtil.showLong("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                }
            }
        });

        qmsLan = convertQmsLanguage(language);
        //隐藏软键盘
        hideSoftInput(this);
        alertDialog = new AlertDialog.Builder(this);

        mPhotoAdapter = new PhotoAdapter(R.layout.item_qms_img, mFileLists);
        mRecyclerViewQms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewQms.setAdapter(mPhotoAdapter);

        mPhotoAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {

                Snackbar snackbar = Snackbar.make(coordinator, "删除操作", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(getResources().getColor(R.color.white))
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Map<String, Integer> deleteFileMap = new HashMap<>();
                                deleteFileMap.put("FileId", mFileLists.get(position).getFileId());
                                deleteFiles.add(deleteFileMap);
                                mFileLists.remove(position);
                                mPhotoAdapter.notifyDataSetChanged();
                                //ToastUtil.showLong("delete");

                            }
                        });

                setSnackbarColor(snackbar, getResources().getColor(R.color.white), getResources().getColor(R.color.main_aa));
                snackbar.show();
                return true;
            }
        });

        //物料反馈
        qmsMrAdapter = new QmsMrAdapter(this, R.layout.item_qms_mr, qmsMrs);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleView.setAdapter(qmsMrAdapter);

        initBDSpeech();
        initBDTTS();
        //初始化子系统
        initSubSystem();
        //初始化服务状态
        initServiceStatus();

        initAccessTokenWithAkSk();

        voice_btn.setOnTouchListener(this);

        initTab();
        getDefaultAddress();
        //设置分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.tab_divider)); //设置分割线的样式
        linearLayout.setDividerPadding(dip2px(10)); //设置分割线间隔

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String code = tab.getTag().toString();
                supportType = code;
                if (code.equals("1")) {
                    mLlMateriel.setVisibility(View.VISIBLE);
                    mLlUnMateriel.setVisibility(View.GONE);
                } else {
                    mLlUnMateriel.setVisibility(View.VISIBLE);
                    mLlMateriel.setVisibility(View.GONE);
                    initFWL();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mEtQmsContractNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    String aa = mEtQmsContractNo.getText().toString();
                    gContractInfo(aa);
                }
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("action")) {
            String action = intent.getStringExtra("action");
            if ("modify".equals(action)) {
                String autoGuid = intent.getStringExtra("autoGuid");
                requestNet(autoGuid);
            }
        }
    }

    public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();//获取Snackbar的view
        if (view != null) {
            view.setBackgroundColor(backgroundColor);//修改view的背景色
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);//获取Snackbar的message控件，修改字体颜色
        }
    }

    protected void initFWL() {
        initReason();
        initFrequency();
        initSeverity();
    }

    private void initReason() {

        //将参数转为json格式
        JsonEntity jsonEntity = new JsonEntity("FKYYCode", convertQmsLanguage(language), "", "", ELEVATOR);
        String params = GsonUtils.toJson(jsonEntity, false);

        HttpFactory.getApiService()
                .getSystemCode(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mReasonCodeLists = response.getData().getSystemCodeList();
                        }
                    }
                });
    }

    protected void dropDownReasonCode(View view) {

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        tv_qms_reason.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, mReasonCodeLists, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                try {
                    tv_qms_reason.setText(mReasonCodeLists.get(p).getValue());
                    tv_qms_reason.setTag(mReasonCodeLists.get(p).getCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                tv_qms_reason.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    private void initFrequency() {

        //将参数转为json格式
        JsonEntity jsonEntity = new JsonEntity("FSPLCode", convertQmsLanguage(language), "", "", ELEVATOR);
        String params = GsonUtils.toJson(jsonEntity, false);

        HttpFactory.getApiService()
                .getSystemCode(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mFrequencyLists = response.getData().getSystemCodeList();
                        }
                    }
                });
    }

    protected void dropDownFrequency(View view) {

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        tv_qms_frequency.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, mFrequencyLists, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                try {
                    tv_qms_frequency.setText(mFrequencyLists.get(p).getValue());
                    tv_qms_frequency.setTag(mFrequencyLists.get(p).getCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                tv_qms_frequency.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    private void initSeverity() {

        //将参数转为json格式
        JsonEntity jsonEntity = new JsonEntity("YZDCode", convertQmsLanguage(language), "", "", ELEVATOR);
        String params = GsonUtils.toJson(jsonEntity, false);

        HttpFactory.getApiService()
                .getSystemCode(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mSeverityLists = response.getData().getSystemCodeList();
                        }
                    }
                });
    }

    protected void dropDownSeverity(View view) {

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        tv_qms_frequency.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, mSeverityLists, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                try {
                    tv_qms_severity.setText(mSeverityLists.get(p).getValue());
                    tv_qms_severity.setTag(mSeverityLists.get(p).getCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                tv_qms_severity.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //像素单位转换
    public int dip2px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    //查询子系统
    protected void initTab() {

        //将参数转为json格式
        JsonEntity jsonEntity = new JsonEntity("ZCLXCode", qmsLan, "", "", ELEVATOR);
        String params = GsonUtils.toJson(jsonEntity, false);

        HttpFactory.getApiService()
                .getSystemCode(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mCodeLists = response.getData().getSystemCodeList();
                            for (SystemCodeListBean bean : mCodeLists) {
                                tabLayout.addTab(tabLayout.newTab().setText(bean.getValue()).setTag(bean.getCode()));
                            }
                            String code = mCodeLists.get(0).getCode();
                            supportType = code;
                            if (code.equals("1")) {
                                mLlMateriel.setVisibility(View.VISIBLE);
                                mLlUnMateriel.setVisibility(View.GONE);
                            } else {
                                mLlUnMateriel.setVisibility(View.VISIBLE);
                                mLlMateriel.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    @OnClick({R.id.tv_qms_save, R.id.tv_qms_submit, R.id.iv_qms_add_img, R.id.tv_qms_open_box_time,
            R.id.tv_qms_service_status, R.id.tv_qms_sub_system, R.id.rl_change_address,
            R.id.tv_qms_assembly, R.id.tv_qms_parts, R.id.tv_qms_spare_parts, R.id.tv_qms_invalid_mode,
            R.id.iv_qms_add_img_system, R.id.iv_qms_img_code, R.id.tv_edit, R.id.iv_generateAnswer,
            R.id.tv_qms_reason, R.id.tv_qms_frequency, R.id.tv_qms_severity})
    void onClick(View v) {

        Intent intent = getIntent();
        switch (v.getId()) {
            case R.id.tv_qms_save://保存
                submitInfo("True", "0");
                break;
            case R.id.tv_qms_submit://提交
                submitInfo("False", "1");
                break;
            case R.id.iv_qms_add_img://添加图片
                addAttach();
                break;
            case R.id.tv_qms_open_box_time://开箱时间
                openTime();
                break;
            case R.id.tv_qms_service_status://服务状态
                dropDownServiceStatus(v);
                break;
            case R.id.tv_qms_sub_system://子系统
                dropDownSubSystem(v);
                break;
            case R.id.tv_qms_assembly://组件
                if (TextUtils.isEmpty(mTvQmsSubSystem.getText())) {
                    ToastUtil.showLong("请先选择子系统");
                    return;
                }
                if (mAssemblyCodeLists == null || mAssemblyCodeLists.size() <= 0) {
                    return;
                }
                dropDownAssembly(v);
                break;
            case R.id.tv_qms_parts://部件
                if (TextUtils.isEmpty(mTvQmsAssembly.getText())) {
                    ToastUtil.showLong("请先选择组件");
                    return;
                }
                if (mPartsCodeLists == null || mPartsCodeLists.size() <= 0) {
                    return;
                }
                dropDownParts(v);
                break;
            case R.id.tv_qms_spare_parts://零件
                if (TextUtils.isEmpty(mTvQmsParts.getText())) {
                    ToastUtil.showLong("请先选择部件");
                    return;
                }
                if (mSparePartsCodeLists == null || mSparePartsCodeLists.size() <= 0) {
                    return;
                }
                dropDownSpareParts(v);
                break;
            case R.id.tv_qms_invalid_mode://失效模式
                if (mInvalidModeCodeLists == null || mInvalidModeCodeLists.size() <= 0) {
                    return;
                }
                dropDownInvalidMode(v);
                break;
            case R.id.iv_qms_add_img_system://子系统拍照
                addAttachZXT();
                break;
            case R.id.iv_qms_img_code://合同号二维码
                QRCode();
                break;
            case R.id.tv_edit:
                intent.setClass(this, QmsMREditActivity.class);
                startActivityForResult(intent, 10010);
                break;
            case R.id.rl_change_address:
                intent.setClass(this, QmsAddressManageActivity.class);
                startActivityForResult(intent, 10012);
                break;
            case R.id.iv_generateAnswer:
                String desc = mEtQmsQuestionDesc.getText().toString();
                getGenerateAnswer(desc);
                break;
            case R.id.tv_qms_reason:
                dropDownReasonCode(v);
                break;
            case R.id.tv_qms_frequency:
                dropDownFrequency(v);
                break;
            case R.id.tv_qms_severity:
                dropDownSeverity(v);
                break;
        }
    }

    private void getGenerateAnswer(String desc) {

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("question", desc);
        paramsMap.put("app", "qms");
        paramsMap.put("language", convertBotLanguage(language));
        paramsMap.put("top", "5");
        String params = GsonUtils.toJson(paramsMap, false);
        HttpFactory.getApiService()
                .generateAnswer(params)
                .compose(RxHelper.<BaseResponse<GenerateAnswer>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<GenerateAnswer>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<GenerateAnswer> response) {

                        List<GenerateAnswer.DataBean> data = response.getData().getData();
                        QmsGenerateAnswerDialog dialog = new QmsGenerateAnswerDialog(QmsMyWantFeedBackActivity.this, data);
                        dialog.show();
                    }
                });
    }

    private void getDefaultAddress() {

        //将参数转为json格式
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("FKR", getUserNo());
        paramsMap.put("IsMR", "True");
        String params = GsonUtils.toJson(paramsMap, false);

        HttpFactory.getApiService()
                .getAppAddressList(params)
                .compose(RxHelper.<BaseResponse<QmsAddressList>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<QmsAddressList>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<QmsAddressList> response) {
                        List<QmsAddressList.QmsAddress> list = response.getData().getAddressList();
                        if (list != null && list.size() > 0) {
                            for (QmsAddressList.QmsAddress qmsAddress : list) {
                                if (qmsAddress.getIsMR()) {
                                    name_tv.setText(qmsAddress.getLXR());
                                    customer_phone.setText(qmsAddress.getLXDH());
                                    company_tv.setText(qmsAddress.getLXDZ());
                                    tv_address.setVisibility(View.GONE);
                                    company_tv.setText(qmsAddress.getLXDZ());
                                    tv_label.setText(qmsAddress.getSX());
                                    if (qmsAddress.getIsMR()) {
                                        tv_default.setVisibility(View.VISIBLE);
                                    } else {
                                        tv_default.setVisibility(View.GONE);
                                    }
                                    break;
                                } else {
                                    tv_address.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            tv_address.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    protected void initServiceStatus() {

        //将参数转为json格式
        JsonEntity jsonEntity = new JsonEntity("FWZTCode", qmsLan, "", "", ELEVATOR);
        String params = GsonUtils.toJson(jsonEntity, false);

        HttpFactory.getApiService()
                .getSystemCode(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mServiceStatusLists = response.getData().getSystemCodeList();
                        }
                    }
                });
    }

    protected void dropDownServiceStatus(View view) {

        if (mServiceStatusLists == null || mServiceStatusLists.size() == 0) {
            ToastUtil.showLong("无数据");
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mTvQmsServiceStatus.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, mServiceStatusLists, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                mTvQmsServiceStatus.setText(mServiceStatusLists.get(p).getValue());
                mTvQmsServiceStatus.setTag(mServiceStatusLists.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mTvQmsServiceStatus.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    protected void initSubSystem() {

        //将参数转为json格式
        JsonEntity jsonEntity = new JsonEntity("ZXTCode", qmsLan, "", "", ELEVATOR);
        String params = GsonUtils.toJson(jsonEntity, false);

        HttpFactory.getApiService()
                .getSystemCode(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mSubSystemCodeLists = response.getData().getSystemCodeList();
                        }
                    }
                });
    }

    protected void dropDownSubSystem(View view) {

        if (mSubSystemCodeLists == null || mSubSystemCodeLists.size() == 0) {
            ToastUtil.showLong("无数据");
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mTvQmsSubSystem.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, mSubSystemCodeLists, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                String sub = mTvQmsSubSystem.getText().toString();
                String selectValue = mSubSystemCodeLists.get(p).getValue();
                if (sub.equals(selectValue)) {
                    return;
                }
                mTvQmsSubSystem.setText(selectValue);
                mTvQmsSubSystem.setTag(mSubSystemCodeLists.get(p).getCode());
                mTvQmsAssembly.setText("");
                mTvQmsParts.setText("");
                mTvQmsSpareParts.setText("");
                mTvQmsInvalidMode.setText("");
                findAssembly();
                findInvalidMode();
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mTvQmsSubSystem.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    protected void findAssembly() {

        String parentCode = mTvQmsSubSystem.getTag().toString();
        String params = GsonUtils.toJson(new JsonSubSystemEntity(parentCode, "ZXTCode", "ZJCode", ELEVATOR, qmsLan), false);
        HttpFactory.getApiService()
                .getSystemCodeSub(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mAssemblyCodeLists = response.getData().getSystemCodeList();
                        }
                    }
                });
    }

    protected void dropDownAssembly(View view) {

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mTvQmsAssembly.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, mAssemblyCodeLists, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                String sub = mTvQmsAssembly.getText().toString();
                String selectValue = mAssemblyCodeLists.get(p).getValue();
                if (sub.equals(selectValue)) {
                    this.dismiss();
                    return;
                }
                mTvQmsAssembly.setText(selectValue);
                mTvQmsAssembly.setTag(mAssemblyCodeLists.get(p).getCode());
                mTvQmsParts.setText("");
                mTvQmsSpareParts.setText("");
                mTvQmsInvalidMode.setText("");
                findParts();
                findInvalidMode();
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mTvQmsAssembly.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    protected void findParts() {

        String parentCode = mTvQmsAssembly.getTag().toString();
        String params = GsonUtils.toJson(new JsonSubSystemEntity(parentCode, "ZJCode", "BJCode", ELEVATOR, qmsLan), false);
        HttpFactory.getApiService()
                .getSystemCodeSub(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mPartsCodeLists = response.getData().getSystemCodeList();
                        }
                    }
                });
    }

    protected void dropDownParts(View view) {

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mTvQmsParts.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, mPartsCodeLists, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                String sub = mTvQmsParts.getText().toString();
                String selectValue = mPartsCodeLists.get(p).getValue();
                if (sub.equals(selectValue)) {
                    this.dismiss();
                    return;
                }
                mTvQmsParts.setText(selectValue);
                mTvQmsParts.setTag(mPartsCodeLists.get(p).getCode());
                mTvQmsSpareParts.setText("");
                mTvQmsInvalidMode.setText("");
                findSpareParts();
                findInvalidMode();
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mTvQmsParts.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    protected void findSpareParts() {

        String parentCode = mTvQmsParts.getTag().toString();
        String params = GsonUtils.toJson(new JsonSubSystemEntity(parentCode, "BJCode", "LJCode", ELEVATOR, qmsLan), false);
        HttpFactory.getApiService()
                .getSystemCodeSub(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mSparePartsCodeLists = response.getData().getSystemCodeList();
                        }
                    }
                });
    }

    protected void dropDownSpareParts(View view) {

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mTvQmsSpareParts.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, mSparePartsCodeLists, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                String sub = mTvQmsSpareParts.getText().toString();
                String selectValue = mSparePartsCodeLists.get(p).getValue();
                if (sub.equals(selectValue)) {
                    this.dismiss();
                    return;
                }
                mTvQmsSpareParts.setText(selectValue);
                mTvQmsSpareParts.setTag(mSparePartsCodeLists.get(p).getCode());
                mTvQmsInvalidMode.setText("");
                findInvalidMode();
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mTvQmsSpareParts.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    protected void findInvalidMode() {

        String zxt = mTvQmsSubSystem.getTag() == null ? "" : mTvQmsSubSystem.getTag().toString();
        String zj = mTvQmsAssembly.getTag() == null ? "" : mTvQmsAssembly.getTag().toString();
        String bj = mTvQmsParts.getTag() == null ? "" : mTvQmsParts.getTag().toString();
        String lj = mTvQmsSpareParts.getTag() == null ? "" : mTvQmsSpareParts.getTag().toString();
        String params = GsonUtils.toJson(new JsonSystemCodeSXMSEntity(zxt, zj, bj, lj, ELEVATOR, qmsLan), false);
        HttpFactory.getApiService()
                .getAppSystemCodeSXMS(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mInvalidModeCodeLists = response.getData().getSystemCodeList();
                        }
                    }
                });
    }

    protected void dropDownInvalidMode(View view) {

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mTvQmsInvalidMode.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, mInvalidModeCodeLists, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                mTvQmsInvalidMode.setText(mInvalidModeCodeLists.get(p).getValue());
                mTvQmsInvalidMode.setTag(mInvalidModeCodeLists.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mTvQmsInvalidMode.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    @Override
    protected void onDestroy() {
        clear();
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance().release();
    }

    protected void clear() {

        if (myRecognizer != null) {
            myRecognizer.release();
            myRecognizer = null;
        }
        if (synthesizer != null) {
            synthesizer.release();
            synthesizer = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FileList fileList = new FileList();
        fileList.setFileId(521);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                mPhotoPath = ImageUtil.saveImageToGallery(QmsMyWantFeedBackActivity.this, bitmap);
                Intent intent = getIntent();
                intent.putExtra("photoPath", mPhotoPath);
                fileList.setFileUrl(mPhotoPath);
                localFileLists.add(fileList);
                mFileLists.add(fileList);
                mPhotoAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == PHOTO_REQUEST_GALLERY && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                ImageUtil.crop(this, uri);
            }
        } else if (requestCode == PHOTO_REQUEST_GALLERY_CODE3 && resultCode == RESULT_OK) {
            if (data != null) {
                //更新UI
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedVideo,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                String realName = imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.lastIndexOf("."));

                getAppFailureModeInfo(realName);
            }
        } else if (requestCode == PHOTO_REQUEST_CUT && resultCode == RESULT_OK) {

            try {
                //更新UI
                Bitmap bitmap = data.getParcelableExtra("data");
                mPhotoPath = ImageUtil.saveImageToGallery(QmsMyWantFeedBackActivity.this, bitmap);
                fileList.setFileUrl(mPhotoPath);
                localFileLists.add(fileList);
                mFileLists.add(fileList);
                mPhotoAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_VIDEO_CODE && resultCode == RESULT_OK) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

            //ContentResolver cr = this.getContentResolver();
            // 缩略图ID:MediaStore.Audio.Media._ID
            //int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
            // 方法一 Thumbnails 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
            // 第一个参数为 ContentResolver，第二个参数为视频缩略图ID， 第三个参数kind有两种为：MICRO_KIND和MINI_KIND 字面意思理解为微型和迷你两种缩略模式，前者分辨率更低一些。
            //Bitmap bitmap1 = MediaStore.Video.Thumbnails.getThumbnail(cr, imageId, MediaStore.Video.Thumbnails.MICRO_KIND, null);

            // 方法二 ThumbnailUtils 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
            // 第一个参数为 视频/缩略图的位置，第二个依旧是分辨率相关的kind
            Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail(imagePath, MediaStore.Video.Thumbnails.MICRO_KIND);

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String videoPath = cursor.getString(columnIndex);

            mPhotoPath = ImageUtil.saveImageToGallery(QmsMyWantFeedBackActivity.this, bitmap2);
            fileList.setFileUrl(mPhotoPath);
            fileList.setFileContent(videoPath);
            localFileLists.add(fileList);
            mFileLists.add(fileList);
            mPhotoAdapter.notifyDataSetChanged();
            cursor.close();
        } else if (requestCode == 10010 && resultCode == 1) {

            QmsMaterialRequirement qmsMr = (QmsMaterialRequirement) data.getSerializableExtra("qmsMr");
            qmsMrs.add(qmsMr);
            qmsMrAdapter.notifyDataSetChanged();

        } else if (requestCode == REQUEST_CODE_GENERAL_BASIC && resultCode == Activity.RESULT_OK) {
            // 识别成功回调，通用文字识别
            RecognizeService.recGeneralBasic(FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            OcrResult ocrResult = new Gson().fromJson(result, OcrResult.class);
                            if (ocrResult.getWords_result_num() == 1) {
                                String aa = ocrResult.getWords_result().get(0).getWords();
                                mEtQmsContractNo.setText(aa);
                                gContractInfo(aa);
                            } else {
                                ToastUtil.showLong("请确定证号为连续数字");
                            }
                        }
                    });
        } else if (requestCode == 10012 && resultCode == 55) {

            qmsAddress = (QmsAddressList.QmsAddress) data.getSerializableExtra("address");
            tv_address.setVisibility(View.GONE);
            name_tv.setText(qmsAddress.getLXR());
            customer_phone.setText(qmsAddress.getLXDH());
            company_tv.setText(qmsAddress.getLXDZ());
            tv_label.setText(qmsAddress.getSX());
            if (qmsAddress.getIsMR()) {
                tv_default.setVisibility(View.VISIBLE);
            } else {
                tv_default.setVisibility(View.GONE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void getAppFailureModeInfo(String UniqueCode) {

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("UniqueCode", UniqueCode);
        paramsMap.put("Language", convertQmsLanguage(language));
        String params = GsonUtils.toJson(paramsMap, false);
        HttpFactory.getApiService()
                .getAppFailureModeInfo(params)
                .compose(RxHelper.<BaseResponse<Map<String, String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, String>>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, String>> response) {

                        try {
                            Map<String, String> resultMap = response.getData();
                            String F_ZXT = resultMap.get("F_ZXT");
                            String F_ZXTValue = resultMap.get("F_ZXTValue");
                            if (!TextUtils.isEmpty(F_ZXT)) {
                                mTvQmsSubSystem.setText(F_ZXTValue);
                                mTvQmsSubSystem.setTag(F_ZXT);

                            }
                            String F_ZJ = resultMap.get("F_ZJ");
                            String F_ZJValue = resultMap.get("F_ZJValue");
                            if (!TextUtils.isEmpty(F_ZJ)) {
                                mTvQmsAssembly.setText(F_ZJValue);
                                mTvQmsAssembly.setTag(F_ZJ);
                                findAssembly();

                            }
                            String F_BJ = resultMap.get("F_BJ");
                            String F_BJValue = resultMap.get("F_BJValue");
                            if (!TextUtils.isEmpty(F_BJ)) {
                                mTvQmsParts.setText(F_BJValue);
                                mTvQmsParts.setTag(F_BJ);
                                findParts();
                            }
                            String F_LJ = resultMap.get("F_LJ");
                            String F_LJValue = resultMap.get("F_LJValue");
                            if (!TextUtils.isEmpty(F_LJ)) {
                                mTvQmsSpareParts.setText(F_LJValue);
                                mTvQmsSpareParts.setTag(F_LJ);
                                findSpareParts();

                            }
                            String F_FailureMode = resultMap.get("F_FailureMode");
                            String F_FailureModeValue = resultMap.get("F_FailureModeValue");
                            if (!TextUtils.isEmpty(F_FailureMode)) {
                                mTvQmsInvalidMode.setText(F_FailureModeValue);
                                mTvQmsInvalidMode.setTag(F_FailureMode);
                                findInvalidMode();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void gContractInfo(String no) {

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("UniqueCode", no);
        String params = GsonUtils.toJson(paramsMap, false);
        HttpFactory.getApiService()
                .getAppContractInfo(params)
                .compose(RxHelper.<BaseResponse<ContractInfo>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<ContractInfo>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<ContractInfo> response) {

                        try {
                            ContractInfo contractInfo = response.getData();
                            project_name.setText(contractInfo.getC_XMMC());
                            mEtQmsElevatorSource.setText(contractInfo.getC_DTLY());
                            if (mServiceStatusLists != null && mServiceStatusLists.size() > 0) {
                                for (SystemCodeListBean bean : mServiceStatusLists) {
                                    if (bean.getCode().equals(contractInfo.getC_FWZT())) {
                                        mTvQmsServiceStatus.setText(bean.getValue());
                                        mTvQmsServiceStatus.setTag(bean.getCode());
                                    }
                                }
                            }
                            mTvQmsOpenBoxTime.setText(contractInfo.getC_KXSJ());
                            mEtQmsProductNo.setText(contractInfo.getC_CPXH());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void openTime() {
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DATE);
        @SuppressWarnings("ResourceType")
        DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker dp, int year, int month, int day) {
                String date = DateUtil.formatChange(year, month, day, "yyyy-MM-dd");
                mTvQmsOpenBoxTime.setText(date);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void submitInfo(final String isCG, String taskStatus) {

        try {
            String contractNo = mEtQmsContractNo.getText().toString();
            String c_dtly = mEtQmsElevatorSource.getText().toString();
            String c_fwzt = mTvQmsServiceStatus.getTag() == null ? "" : mTvQmsServiceStatus.getTag().toString();
            String c_kxsj = mTvQmsOpenBoxTime.getText().toString();
            String c_cpxh = mEtQmsProductNo.getText().toString();
            String f_zxt = mTvQmsSubSystem.getTag() == null ? "" : mTvQmsSubSystem.getTag().toString();
            String f_zj = mTvQmsAssembly.getTag() == null ? "" : mTvQmsAssembly.getTag().toString();
            String f_bj = mTvQmsParts.getTag() == null ? "" : mTvQmsParts.getTag().toString();
            String f_lj = mTvQmsSpareParts.getTag() == null ? "" : mTvQmsSpareParts.getTag().toString();
            String f_failuremode = mTvQmsInvalidMode.getTag() == null ? "" : mTvQmsInvalidMode.getTag().toString();
            String questionDesc = mEtQmsQuestionDesc.getText().toString();
            if (TextUtils.isEmpty(contractNo)) {
                ToastUtil.showLong("请输入合同号");
                return;
            }

            JsonSaveFeedBackEntity saveFeedBackEntity = new JsonSaveFeedBackEntity();
            if (!TextUtils.isEmpty(AutoGuid)) {
                saveFeedBackEntity.setAutoGuid(AutoGuid);
            }
            saveFeedBackEntity.setFKR(getUserNo());
            saveFeedBackEntity.setFKRName(TextUtils.isEmpty(getUserName()) ? "LS" : getUserName());
            saveFeedBackEntity.setEntity(getUserDeptName());
            saveFeedBackEntity.setIsCG(isCG);//是否草稿
            saveFeedBackEntity.setIsWL("1".equals(supportType) ? "True" : "False");//是否物料
            //saveFeedBackEntity.setIsPJ("N(默认False)");//是否评价
            saveFeedBackEntity.setTaskStatus(taskStatus);//任务状态
            saveFeedBackEntity.setW_WTMS(questionDesc);
            saveFeedBackEntity.setW_ZCLX(supportType);
            saveFeedBackEntity.setC_ContractNo(contractNo);
            saveFeedBackEntity.setC_XMMC(project_name.getText().toString());
            saveFeedBackEntity.setC_DTLY(c_dtly);
            saveFeedBackEntity.setC_FWZT(c_fwzt);
            saveFeedBackEntity.setC_KXSJ(c_kxsj);
            saveFeedBackEntity.setC_CPXH(c_cpxh);
            saveFeedBackEntity.setF_ZXT(f_zxt);
            saveFeedBackEntity.setF_ZJ(f_zj);
            saveFeedBackEntity.setF_BJ(f_bj);
            saveFeedBackEntity.setF_LJ(f_lj);
            saveFeedBackEntity.setF_FailureMode(f_failuremode);

            String params2 = "";
            if ("1".equals(supportType)) {
                params2 = GsonUtils.toJson(qmsMrs, false);
            } else {
                saveFeedBackEntity.setNM_FKYY(tv_qms_reason.getText().toString());//非物料需求-反馈原因
                saveFeedBackEntity.setNM_FSPL(tv_qms_frequency.getText().toString()); //非物料需求-发生频率
                saveFeedBackEntity.setNM_YZD(tv_qms_severity.getText().toString());//非物料需求-严重度
            }

            saveFeedBackEntity.setP_BJDZ(company_tv.getText().toString()); //补件地址
            saveFeedBackEntity.setP_BJLXDH(customer_phone.getText().toString());//补件联系电话
            saveFeedBackEntity.setP_BJLXR(name_tv.getText().toString());//补件联系人

            Map<String, RequestBody> bodyMap = new HashMap<>();

            for (FileList bean : localFileLists) {
                if (!TextUtils.isEmpty(bean.getFileUrl())) {

                    String filename = ToolUtils.getNameFromUrl(bean.getFileUrl());
                    String videoname = "";
                    if (!TextUtils.isEmpty(bean.getFileContent())) {
                        videoname = ToolUtils.getNameFromUrl(bean.getFileContent());
                    }
                    if (TextUtils.isEmpty(videoname)) {
                        bodyMap.put(filename + "\";filename=\"" + filename, RequestBody.create(MediaType.parse("image/png"), new File(bean.getFileUrl())));
                    } else {
                        bodyMap.put(videoname + "\";filename=\"" + videoname, RequestBody.create(MediaType.parse("multipart/form-data"), new File(bean.getFileContent())));
                    }
                }
            }

            String tempPath = APPConfig.DOWN_LOAD_PATH + "temp.png";
            File fileFile = new File(tempPath);
            if (!fileFile.exists()) {
                try {
                    fileFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            bodyMap.put("temp\";filename=\"temp.png", RequestBody.create(MediaType.parse("image/png"), fileFile));

            String params = GsonUtils.toJson(saveFeedBackEntity, false);
            String deleteFileParams = GsonUtils.toJson(deleteFiles, false);

            HttpFactory.getApiService()
                    .SaveAPPFeedbackInfo(params, params2, deleteFileParams, bodyMap)
                    .compose(RxHelper.<BaseResponse<SaveAPPFeedbackInfoEntity>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<SaveAPPFeedbackInfoEntity>>(this, true) {
                        @Override
                        public void onSuccess(BaseResponse<SaveAPPFeedbackInfoEntity> response) {
                            if (response.getData().getResult().equals("1")) {

                                String message;
                                if ("True".equals(isCG)) {
                                    message = "保存成功";
                                } else {
                                    message = "提交成功";
                                }
                                ToastUtil.showLong(message);
                                clear();
                                finish();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addAttach() {
        List<String> names = new ArrayList<>();
        names.add("拍照");
        names.add("相册");
        names.add("视频");
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        RxPermissions rxPermissions = new RxPermissions(getActivity());
                        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE)
                                .subscribe(new Consumer<Permission>() {
                                    @Override
                                    public void accept(Permission permission) throws Exception {
                                        if (permission.granted) {
                                            // 用户已经同意该权限
                                            takePhoto();
                                        } else if (permission.shouldShowRequestPermissionRationale) {
                                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                            ToastUtil.showLong("用户拒绝了该权限");
                                        } else {
                                            // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                            ToastUtil.showLong("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                                        }
                                    }
                                });

                        break;
                    case 1:
                        takeImage();
                        break;
                    case 2:
                        takeVideo();
                        break;
                }

            }
        }, names);
    }


    private void addAttachZXT() {
        List<String> names = new ArrayList<>();
        names.add("AR识别");
        names.add("相册");
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getAppFailureModeInfo("yeyinji");
                        PackageManager packageManager = getPackageManager();
                        //String packageName = "com.alibaba.android.rimet";//要打开应用的包名,以钉钉为例
                        String packageName = "com.RecognitionYYJ.Product";//要打开应用的包名,以微信为例
                        Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(packageName);
                        if (launchIntentForPackage != null)
                            startActivityForResult(launchIntentForPackage, 163);
                        else
                            ToastUtil.showLong("手机未安装该应用");
                        break;
                    case 1:
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, PHOTO_REQUEST_GALLERY_CODE3);
                        break;
                }

            }
        }, names);
    }

    private void takePhoto() {

        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST);
        } catch (SecurityException e) {
            ToastUtil.showLong("Permission Denial");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takeImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    private void takeVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_VIDEO_CODE);
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


    protected void initRecog() {
        StatusRecogListener listener = new MessageStatusRecogListener(handler);
        myRecognizer = new MyRecognizer(this, listener);
        apiParams = new OnlineRecogParams(this);
        status = STATUS_NONE;
        if (enableOffline) {
            myRecognizer.loadOfflineEngine(OfflineRecogParams.fetchOfflineParams());
        }
    }

    protected void handle(Message msg) {
        int what = msg.what;
        switch (what) {
            case PRINT:
//                print(msg);
                break;
            case UI_CHANGE_INPUT_TEXT_SELECTION:
//                if (msg.arg1 <= mInput.getText().length()) {
//                    mInput.setSelection(0, msg.arg1);
//                }
                break;
            case UI_CHANGE_SYNTHES_TEXT_SELECTION:
//                SpannableString colorfulText = new SpannableString(mInput.getText().toString());
//                if (msg.arg1 <= colorfulText.toString().length()) {
//                    colorfulText.setSpan(new ForegroundColorSpan(Color.GRAY), 0, msg.arg1, Spannable
//                            .SPAN_EXCLUSIVE_EXCLUSIVE);
//                    mInput.setText(colorfulText);
//                }
                break;
            default:
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
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

//        // 离线资源文件
//        OfflineResource offlineResource = createOfflineResource(offlineVoice);
//        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
//        params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
//        params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
//                offlineResource.getModelFilename());
        return params;
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

//        // 如果您集成中出错，请将下面一段代码放在和demo中相同的位置，并复制InitConfig 和 AutoCheck到您的项目中
//        // 上线时请删除AutoCheck的调用
//        AutoCheck.getInstance(getApplicationContext()).check(initConfig, new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == 100) {
//                    AutoCheck autoCheck = (AutoCheck) msg.obj;
//                    synchronized (autoCheck) {
//                        String message = autoCheck.obtainDebugMessage();
//                        toPrint(message); // 可以用下面一行替代，在logcat中查看代码
//                        // Log.w("AutoCheckMessage", message);
//                    }
//                }
//            }
//
//        });
        synthesizer = new NonBlockSyntherizer(this, initConfig, ttsHandler); // 此处可以改为MySyntherizer 了解调用过程
    }

    protected void handleMsg(Message msg) {
        switch (msg.what) { // 处理MessageStatusRecogListener中的状态回调
            case STATUS_FINISHED:
                if (msg.arg2 == 1) {
                    String message = msg.obj.toString();
                    if (!message.contains("7")) {
                        speak(msg.obj.toString());
                    }
                    //ToastUtil.showLong(msg.obj.toString());
                } else {
                    //ToastUtil.showLong(msg.obj.toString());
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

    private void speak(String speak) {
        String front = mEtQmsQuestionDesc.getText().toString();
        mEtQmsQuestionDesc.setText(front + speak.replace("\n", ""));
    }

    private void updateBtnTextByStatus() {
        switch (status) {
            case STATUS_NONE:
                // btn.setText("开始录音");
                voice_btn.setEnabled(true);
                break;
            case STATUS_WAITING_READY:
            case STATUS_READY:
            case STATUS_SPEAKING:
            case STATUS_RECOGNITION:
                voice_btn.setEnabled(true);
                break;

            case STATUS_STOPPED:
                voice_btn.setEnabled(true);
                break;
        }
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
            VibratorUtil.Vibrate(this, 70);   //震动70ms
            voiceDialogManager = new VoiceDialogManager(this);
            voiceDialogManager.show();
        }
    }

    private void stopRecognizer() {
        voice_btn.setImageResource(R.drawable.voice_grey);
        myRecognizer.stop();
        voiceDialogManager.dismiss();
    }

    private boolean isTouch;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
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
                                    ToastUtil.showLong("用户拒绝了该权限");
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

    private static final int REQUEST_CODE_GENERAL_BASIC = 106;

    private void QRCode() {

        if (!checkTokenStatus()) {
            return;
        }
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                CameraActivity.CONTENT_TYPE_GENERAL);
        startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    private void initAccessToken() {
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("licence方式获取token失败", error.getMessage());
            }
        }, getApplicationContext());
    }

    private void initAccessTokenWithAkSk() {

        String ak = getResources().getString(R.string.ak);
        String sk = getResources().getString(R.string.sk);
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hasGotToken = true;
                doSometing();
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, getApplicationContext(), ak, sk);
    }

    private void doSometing() {

        if (getIntent().hasExtra("action")) {
            String action = getIntent().getStringExtra("action");

            if ("Move2BarCode".equals(action)) {
                //iv_qms_img_code.performClick();
                QRCode();
            } else if ("Move2Photo".equals(action)) {
                //iv_qms_add_img_system.performClick();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY_CODE3);
            }
        }
    }

    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    private void infoPopText(final String result) {
        alertText("", result);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initAccessToken();
        } else {
            Toast.makeText(getApplicationContext(), "需要android.permission.READ_PHONE_STATE", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        clear();
    }

    private void requestNet(String id) {

        AutoGuid = id;
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("AutoGuid", id);
        paramsMap.put("Language", Util.convertQmsLanguage(language));
        String params = GsonUtils.toJson(paramsMap, false);
        HttpFactory.getApiService()
                .getAPPFeedbackInfo(params)
                .compose(RxHelper.<BaseResponse<QmsFeedbackInfoEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<QmsFeedbackInfoEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<QmsFeedbackInfoEntity> response) {
                        QmsFeedbackInfoEntity.FeedbackInfoBean bean = response.getData().getFeedbackInfo();
                        List<QmsMaterialRequirement> qmsMaterialRequirements = response.getData().getFeedbackPartList();
                        mFileLists = response.getData().getFileList();
                        if (mFileLists != null && mFileLists.size() > 0) {
                            for (FileList fileList : mFileLists) {
                                if ("temp.png".equals(fileList.getFileName())) {
                                    mFileLists.remove(fileList);
                                    break;
                                }
                            }
                        }

                        try {

                            if (bean.getW_ZCLX().equals("1")) {//物料
                                mLlUnMateriel.setVisibility(View.GONE);
                                mLlMateriel.setVisibility(View.VISIBLE);
                                qmsMrs.clear();
                                qmsMrs.addAll(qmsMaterialRequirements);
                                qmsMrAdapter.notifyDataSetChanged();
                            } else {//非物料
                                mLlUnMateriel.setVisibility(View.VISIBLE);
                                mLlMateriel.setVisibility(View.GONE);
                                tv_qms_reason.setText(bean.getNM_FKYY());
                                tv_qms_frequency.setText(bean.getNM_YZD());
                                tv_qms_severity.setText(bean.getNM_FSPL());
                            }

                            mPhotoAdapter.setNewData(mFileLists);

                            mEtQmsQuestionDesc.setText(bean.getW_WTMS());
                            //mTvQmsSupportType.setText(bean.getW_ZCLXValue());
                            String zclx = bean.getW_ZCLX();

                            if (mCodeLists != null && mCodeLists.size() > 0) {
                                int index = 0;
                                for (SystemCodeListBean bean1 : mCodeLists) {
                                    if (bean1.getCode().equals(zclx)) {
                                        tabLayout.getTabAt(index).select();
                                    }
                                    index++;
                                }
                            }

                            mEtQmsContractNo.setText(bean.getC_ContractNo());
                            project_name.setText(bean.getC_XMMC());
                            mEtQmsElevatorSource.setText(bean.getC_DTLY());
                            mTvQmsServiceStatus.setText(bean.getC_FWZTValue());
                            mTvQmsServiceStatus.setTag(bean.getC_FWZT());
                            mTvQmsOpenBoxTime.setText(TextUtils.isEmpty(bean.getC_KXSJ()) ? "" : bean.getC_KXSJ());
                            mEtQmsProductNo.setText(bean.getC_CPXH());

                            String F_ZXT = bean.getF_ZXT();
                            String F_ZXTValue = bean.getF_ZXTValue();
                            if (!TextUtils.isEmpty(F_ZXT)) {
                                mTvQmsSubSystem.setText(F_ZXTValue);
                                mTvQmsSubSystem.setTag(F_ZXT);

                            }
                            String F_ZJ = bean.getF_ZJ();
                            String F_ZJValue = bean.getF_ZJValue();
                            if (!TextUtils.isEmpty(F_ZJ)) {
                                mTvQmsAssembly.setText(F_ZJValue);
                                mTvQmsAssembly.setTag(F_ZJ);
                                findAssembly();

                            }
                            String F_BJ = bean.getF_BJ();
                            String F_BJValue = bean.getF_BJValue();
                            if (!TextUtils.isEmpty(F_BJ)) {
                                mTvQmsParts.setText(F_BJValue);
                                mTvQmsParts.setTag(F_BJ);
                                findParts();
                            }
                            String F_LJ = bean.getF_LJ();
                            String F_LJValue = bean.getF_LJValue();
                            if (!TextUtils.isEmpty(F_LJ)) {
                                mTvQmsSpareParts.setText(F_LJValue);
                                mTvQmsSpareParts.setTag(F_LJ);
                                findSpareParts();

                            }
                            String F_FailureMode = bean.getF_FailureMode();
                            String F_FailureModeValue = bean.getF_FailureModeValue();
                            if (!TextUtils.isEmpty(F_FailureMode)) {
                                mTvQmsInvalidMode.setText(F_FailureModeValue);
                                mTvQmsInvalidMode.setTag(F_FailureMode);
                                findInvalidMode();

                            }

                            company_tv.setText(bean.getP_BJDZ());
                            name_tv.setText(bean.getP_BJLXR());
                            customer_phone.setText(bean.getP_BJLXDH());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
