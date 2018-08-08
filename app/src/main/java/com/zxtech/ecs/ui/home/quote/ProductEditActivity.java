package com.zxtech.ecs.ui.home.quote;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.event.EventStandardParam;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.escscheme.EscDecorationFragment;
import com.zxtech.ecs.ui.home.escscheme.EscParametersFragment;
import com.zxtech.ecs.ui.home.scheme.CheckTipsDialogFragment;
import com.zxtech.ecs.ui.home.scheme.PackageManager;
import com.zxtech.ecs.ui.home.scheme.detail.BaseSchemeDetailFragment;
import com.zxtech.ecs.ui.home.scheme.detail.DecorationFragment;
import com.zxtech.ecs.ui.home.scheme.detail.FunctionFragment;
import com.zxtech.ecs.ui.home.scheme.detail.ParametersFragment;
import com.zxtech.ecs.ui.home.scheme.detail.RequireFragment;
import com.zxtech.ecs.util.FileUtil;
import com.zxtech.ecs.util.Util;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

import static com.zxtech.ecs.common.Constants.CODE_SPECIALNONSTANDARD;

/**
 * 报价-产品编辑
 * Created by syp523 on 2018/5/23.
 */

public class ProductEditActivity extends BaseActivity implements BaseSchemeDetailFragment.CallBackValue,CheckTipsDialogFragment.CheckTipsCallBack {


    private BaseFragment[] mFragments = new BaseFragment[4];

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;
    private static final int FIVE = 4;
    @BindView(R.id.tab_layout)
    LinearLayout tab_layout;
    @BindViews({R.id.params_tv, R.id.decoration_tv, R.id.function_tv, R.id.require_tv})
    TextView[] tabTexts;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public Map<String, String> paramMap = null;

    public Map<String, ScriptReturnParam> scriptReturnParamMap = new HashMap<>();

    public static final String QUOTE_ACTIVITY = "quote";
    public static final String CONTRACT_CHANGE_ACTIVITY = "change";

    private String sourceActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quote_edit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.product_edit));
        paramMap = (Map<String, String>) getIntent().getSerializableExtra("datas");
        sourceActivity = getIntent().getStringExtra("activity");

        initData(savedInstanceState);
        showTab(FIRST);
    }

    private void initData(final Bundle savedInstanceState) {
        if (paramMap == null) {
            return;
        }
        String allTag = null;
        final String typeId = paramMap.get("TypeId");
        if (Constants.ELEVATOR.equals(typeId)) {
            allTag = Constants.ELE_ALL_PARAMETERS;
        } else {
            allTag = Constants.ESC_ALL_PARAMETERS;
        }

        baseResponseObservable = HttpFactory.getApiService().getParams(typeId, paramMap.get("ElevatorType"), paramMap.get(Constants.CODE_ELEVATORPRODUCT), allTag, null);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<Parameters>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Parameters>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Parameters>> response) {
                        //拆分出所有参数  和土建参数
                        if (response.getData() != null) {
                            List<Parameters> paramstersList = new ArrayList<>(); //土建参数
                            List<Parameters> decorationList = new ArrayList<>(); //装潢参数
                            List<Parameters> functionList = new ArrayList<>(); //功能参数
                            for (int i = 0; i < response.getData().size(); i++) {
                                Parameters param = response.getData().get(i);
                                //默认值
                                if (TextUtils.isEmpty(paramMap.get(param.getProECode())) && !TextUtils.isEmpty(param.getDefaultValue())) {
                                    paramMap.put(param.getProECode(),param.getDefaultValue());
                                }
                                if (Constants.ESCALATOR.equals(typeId) && (param.getPosition().contains(Constants.ESC_BASIC_INFO) || param.getPosition().contains(Constants.ESC_BASIC_PARAMETERS))) {
                                    paramstersList.add(param);
                                }
                                if (Constants.ESCALATOR.equals(typeId) && param.getPosition().contains(Constants.ESC_DECORATION_PARAMETERS)) {
                                    decorationList.add(param);
                                }
                                if (Constants.ESCALATOR.equals(typeId) && param.getPosition().contains(Constants.ESC_FUNCTION_PARAMETERS)) {
                                    functionList.add(param);
                                }
                                if (Constants.ELEVATOR.equals(typeId) && (param.getPosition().contains(Constants.BASIC_PARAMETERS) || param.getPosition().contains(Constants.CONSTRUCTION_PARAMETERS))) {
                                    paramstersList.add(param);
                                }
                                if (Constants.ELEVATOR.equals(typeId) && param.getPosition().contains(Constants.DECORATION_PARAMETERS)) {
                                    decorationList.add(param);
                                }
                                if (Constants.ELEVATOR.equals(typeId) && param.getPosition().contains(Constants.FUNCTION_PARAMETERS)) {
                                    functionList.add(param);
                                }

                            }


                            if (savedInstanceState == null) {
                                mFragments[FIRST] = Constants.ELEVATOR.equals(paramMap.get("TypeId")) ? ParametersFragment.newInstance(paramstersList) : EscParametersFragment.newInstance(paramstersList);
                                mFragments[SECOND] = Constants.ELEVATOR.equals(paramMap.get("TypeId")) ? DecorationFragment.newInstance(decorationList) : EscDecorationFragment.newInstance(null);
                                mFragments[THIRD] = FunctionFragment.newInstance(functionList);
                                mFragments[FOURTH] = RequireFragment.newInstance();

                                loadMultipleRootFragment(R.id.fl_container, FIRST,
                                        mFragments[FIRST],
                                        mFragments[SECOND],
                                        mFragments[THIRD],
                                        mFragments[FOURTH]
                                );
                            } else {
                                mFragments[FIRST] = findFragment(ParametersFragment.class);
                                mFragments[SECOND] = findFragment(DecorationFragment.class);
                                mFragments[THIRD] = findFragment(FunctionFragment.class);
                                mFragments[FOURTH] = findFragment(RequireFragment.class);
                            }

                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quote_edit, menu);
        MenuItem item = menu.findItem(R.id.item_standard_param);
        if (CONTRACT_CHANGE_ACTIVITY.equals(sourceActivity)) { //合同变更产品编辑没有提取标准参数
            item.setVisible(false);
        }
        MenuItem item1 = menu.findItem(R.id.item_save);
        item1.setTitle("参数校验");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_save) { //询价
            makeInquiry("0");

        } else if (item.getItemId() == R.id.item_standard_param) {
            baseResponseObservable = HttpFactory.getApiService().getStandardParam(new Gson().toJson(paramMap));
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<JsonObject>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<JsonObject>>(this, true) {
                        @Override
                        public void onSuccess(BaseResponse<JsonObject> response) {
                            Map<String, String> map = new Gson().fromJson(response.getData(), new TypeToken<Map<String, String>>() {
                            }.getType());
                            paramMap.putAll(map);
                            //发送事件
                            EventBus.getDefault().post(new EventStandardParam(map));
                        }
                        @Override
                        public void onMessage(String message) {
                            CheckTipsDialogFragment dialogFragment = CheckTipsDialogFragment.newInstance();
                            dialogFragment.setData(message);
                            dialogFragment.show(getFragmentManager(), "tips");
                        }
                    });
        }
        return super.onOptionsItemSelected(item);
    }


    private void makeInquiry(String s) {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(this.paramMap);
        map.put(CODE_SPECIALNONSTANDARD, ((RequireFragment) mFragments[FOURTH]).getSelectParams());//非标需求参数
        map.put(Constants.CODE_SYSTEM_LANG, Util.convertEcsLanguage(language));
        map.put("ProductGuid", getIntent().getStringExtra("productGuid"));
        map.put("PLACEOFDELIVERY", getIntent().getStringExtra("place"));
        if (QUOTE_ACTIVITY.equals(sourceActivity)) {
            map.put("OperateType", "submitParam"); //保存并提交
            map.put("ContractChangeGuid", null);
            map.put("ProjectGuid", null);
        }else{
            map.put("OperateType", "change");
            map.put("ContractChangeGuid", getIntent().getStringExtra("contractChangeGuid"));
            map.put("ProjectGuid", getIntent().getStringExtra("projectGuid"));
        }

        map.put("IsCompulsion", s);
        map.put("RoleNo",getRoleNo());
        map.put("UserNo",getUserNo());
        map.put("UserName",getUserName());
        baseResponseObservable = HttpFactory.getApiService().saveParameter(new Gson().toJson(map));
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<Programme>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Programme>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<Programme> response) {
                        setResult(1010);
                        finish();
                    }
                    @Override
                    public void onMessage(String message) {
                        CheckTipsDialogFragment dialogFragment = CheckTipsDialogFragment.newInstance();
                        dialogFragment.setData(message);
                        dialogFragment.callBack = ProductEditActivity.this;
                        dialogFragment.setNonstandard(true);
                        dialogFragment.show(getFragmentManager(), "tips");
                    }
                });
    }

    private void showTab(int position) {
        for (int i = 0; i < tabTexts.length; i++) {
            if (i == position) {
                tabTexts[position].setTextColor(tabColor(position));
            } else {
                tabTexts[i].setTextColor(tabColor(5));
            }
        }
    }

    private int tabColor(int position) {
        int[] titleColors = new int[]{getResources().getColor(R.color.dark_red), getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.grass_green), getResources().getColor(R.color.green),
                getResources().getColor(R.color.blue), getResources().getColor(R.color.default_text_color)};
        return titleColors[position];
    }

    @OnClick(R.id.params_tv)
    public void paramtersClick() {
        showHideFragment(mFragments[FIRST]);
        showTab(FIRST);
    }


    @OnClick(R.id.decoration_tv)
    public void decorationClick() {
        downVRModelRes();
    }

    private void downVRModelRes() {
        final String fileDirs = APPConfig.ECS_MODEL_RESOURCES;
        if (FileUtil.existsFile(fileDirs + PackageManager.checkFile)) {
            showHideFragment(mFragments[SECOND]);
            showTab(SECOND);
        } else {
            FileUtil.createDirs(fileDirs);
            final String resName = "res.zip";
            showProgress();
            progressDialog.setTint(getString(R.string.downloading));
            FileDownloader.getImpl().create(APPConfig.BASE_URL.split("mobileapi")[0] + "app/" + resName)
                    .setPath(fileDirs + resName)
                    .setListener(new FileDownloadSampleListener() {

                        @Override
                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            progressDialog.setProgress(soFarBytes * 100 / totalBytes + "%");
                        }


                        @Override
                        protected void completed(BaseDownloadTask task) {
                            PackageManager.UpzipResourceDataFile(mContext, fileDirs + resName, fileDirs);
                            dismissProgress();
                            showHideFragment(mFragments[SECOND]);
                            showTab(SECOND);
                        }


                        @Override
                        protected void error(BaseDownloadTask task, Throwable e) {
                            dismissProgress();
                        }

                        @Override
                        protected void warn(BaseDownloadTask task) {
                            dismissProgress();
                        }
                    }).start();
        }

    }


    @OnClick(R.id.function_tv)
    public void functionClick() {
        showHideFragment(mFragments[THIRD]);
        showTab(THIRD);
    }


    @OnClick(R.id.require_tv)
    public void requireClick() {
        showHideFragment(mFragments[FOURTH]);
        showTab(FOURTH);
    }


    @Override
    public void sendMessageValue(String key, String strValue) {
        if (paramMap != null) {
            paramMap.put(key, strValue);
        }
    }

    @Override
    public void nonStandardConfirm() {
        makeInquiry("1");
    }

}
