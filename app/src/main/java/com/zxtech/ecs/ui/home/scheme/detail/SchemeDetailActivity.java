package com.zxtech.ecs.ui.home.scheme.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.engineering.FloorStationFragment;
import com.zxtech.ecs.ui.home.escscheme.EscDecorationFragment;
import com.zxtech.ecs.ui.home.scheme.CheckTipsDialogFragment;
import com.zxtech.ecs.ui.home.scheme.PackageManager;
import com.zxtech.ecs.util.FileUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import okhttp3.RequestBody;

import static com.zxtech.ecs.common.Constants.CODE_SPECIALNONSTANDARD;

/**
 * 直梯方案详情
 * Created by syp523 on 2018/1/22.
 */

public class SchemeDetailActivity extends BaseActivity implements BaseSchemeDetailFragment.CallBackValue {


    private BaseFragment[] mFragments = new BaseFragment[5];

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;
    private static final int FIVE = 4;
    @BindView(R.id.tab_layout)
    LinearLayout tab_layout;
    @BindViews({R.id.params_tv, R.id.floorstation_tv, R.id.decoration_tv, R.id.function_tv, R.id.require_tv})
    TextView[] tabTexts;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public Map<String, String> paramMap = null;

    protected Map<String, ScriptReturnParam> scriptReturnParamMap = new HashMap<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_scheme_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.scheme_details));
        paramMap = (Map<String, String>) getIntent().getSerializableExtra("datas");
        initData(savedInstanceState);
        showTab(FIRST);
    }


    private void initData(final Bundle savedInstanceState) {
        if (paramMap == null) {
            return;
        }
        String allTag = Constants.ELE_ALL_PARAMETERS;
        final String typeId = Constants.ELEVATOR;
//        if (Constants.ELEVATOR.equals(typeId)) {
//            allTag = Constants.ELE_ALL_PARAMETERS;
//        }else {
//            allTag = Constants.ESC_ALL_PARAMETERS;
//        }

        baseResponseObservable = HttpFactory.getApiService().getParams(typeId, paramMap.get(Constants.CODE_ELEVATORTYPE), paramMap.get(Constants.CODE_ELEVATORPRODUCT), allTag, null);
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
                                    paramMap.put(param.getProECode(), param.getDefaultValue());
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
                                mFragments[FIRST] = ParametersFragment.newInstance(paramstersList);
                                mFragments[SECOND] = FloorStationFragment.newInstance();
                                mFragments[THIRD] = DecorationFragment.newInstance(decorationList);
                                mFragments[FOURTH] = FunctionFragment.newInstance(functionList);
                                mFragments[FIVE] = RequireFragment.newInstance();

                                loadMultipleRootFragment(R.id.fl_container, FIRST,
                                        mFragments[FIRST],
                                        mFragments[SECOND],
                                        mFragments[THIRD],
                                        mFragments[FOURTH],
                                        mFragments[FIVE]
                                );
                            } else {
                                mFragments[FIRST] = findFragment(ParametersFragment.class);
                                mFragments[SECOND] = findFragment(FloorStationFragment.class);
                                mFragments[THIRD] = findFragment(DecorationFragment.class);
                                mFragments[FOURTH] = findFragment(FunctionFragment.class);
                                mFragments[FIVE] = findFragment(RequireFragment.class);
                            }


                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_params, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_save) {
            Map<String, RequestBody> bodyMap = new HashMap<>();
            // paramMap.putAll(((FunctionFragment) mFragments[FOURTH]).getSelectParams()); //选配参数
            paramMap.put(CODE_SPECIALNONSTANDARD, ((RequireFragment) mFragments[FIVE]).getSelectParams());//非标需求参数
            paramMap.put("userGuid", getUserId());
            paramMap.put("TypeId", Constants.ELEVATOR);
            paramMap.put(Constants.CODE_SYSTEM_LANG, Util.convertEcsLanguage(language));
            final List<String> attachDatas = ((RequireFragment) mFragments[FIVE]).getAttachDatas();
            baseResponseObservable = HttpFactory.getApiService().dimensionByParameter(paramMap);
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<Programme>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<Programme>>(this, true) {
                        @Override
                        public void onSuccess(BaseResponse<Programme> response) {
                            if (paramMap.get(CODE_SPECIALNONSTANDARD) != null) {
                                response.getData().setSpecialNonStandard(paramMap.get(CODE_SPECIALNONSTANDARD));
                            }

                            Intent intent = getIntent();
                            intent.putExtra("data", response.getData());
                            intent.putExtra("attachDatas", (Serializable) attachDatas);
                            setResult(2, intent);
                            finish();
                        }

                        @Override
                        public void onMessage(String message) {
                            CheckTipsDialogFragment dialogFragment = CheckTipsDialogFragment.newInstance();
                            dialogFragment.setData(message);
                            dialogFragment.show(getFragmentManager(), "tips");
                        }
                    });

        } else if (item.getItemId() == R.id.apply_engineering) {

            if (TextUtils.isEmpty(getUserEmail())) {
                ToastUtil.showLong(getString(R.string.msg30));
                return true;
            }
            applyEngineering(1);
        } else if (item.getItemId() == R.id.apply_double_engineering) {
            applyEngineering(2);
        } else if (item.getItemId() == R.id.apply_design) {
            if (TextUtils.isEmpty(getUserEmail())) {
                ToastUtil.showLong(getString(R.string.msg30));
                return true;
            }
            HashMap<String, String> map = new HashMap<>();
            map.putAll(this.paramMap);
            map.put("UserEmail", getUserEmail());
            map.put("UserGuid", getUserId());
            map.put("TypeId", "Elevator");
            baseResponseObservable = HttpFactory.getApiService().applyDecorationDesign(new Gson().toJson(map));
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {
                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            ToastUtil.showLong(getString(R.string.msg31));
                        }
                    });
        }

        return super.onOptionsItemSelected(item);
    }


    private void applyEngineering(int elevatorCount) {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(this.paramMap);
        map.put(CODE_SPECIALNONSTANDARD, ((RequireFragment) mFragments[FIVE]).getSelectParams());//非标需求参数
        if (elevatorCount == 2) {
            for (Map.Entry<String,String> entry : this.paramMap.entrySet()) {
                map.put("B_"+entry.getKey(),entry.getValue());
            }
            map.put("B_"+CODE_SPECIALNONSTANDARD, ((RequireFragment) mFragments[FIVE]).getSelectParams());//非标需求参数
        }
        map.put("0", ((FloorStationFragment) mFragments[SECOND]).getFloorStation().toString());
        map.put("UserEmail", getUserEmail());
        map.put("UserGuid", getUserId());
        map.put(Constants.CODE_SYSTEM_LANG, Util.convertEcsLanguage(language));
        baseResponseObservable = HttpFactory.getApiService().applyDrawing(map);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        ToastUtil.showLong(getString(R.string.msg32));
                    }

                    @Override
                    public void onMessage(String message) {
                        CheckTipsDialogFragment dialogFragment = CheckTipsDialogFragment.newInstance();
                        dialogFragment.setData(message);
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
            showHideFragment(mFragments[THIRD]);
            showTab(THIRD);
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
                            showHideFragment(mFragments[THIRD]);
                            showTab(THIRD);
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
        showHideFragment(mFragments[FOURTH]);
        showTab(FOURTH);
    }


    @OnClick(R.id.require_tv)
    public void requireClick() {
        showHideFragment(mFragments[FIVE]);
        showTab(FIVE);
    }

    @OnClick(R.id.floorstation_tv)
    public void floorClick() {
        showHideFragment(mFragments[SECOND]);
        showTab(SECOND);
    }


    @Override
    public void sendMessageValue(String key, String strValue) {
        if (paramMap != null) {
            paramMap.put(key, strValue);
        }
    }


}
