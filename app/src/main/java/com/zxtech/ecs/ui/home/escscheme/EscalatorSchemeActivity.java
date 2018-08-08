package com.zxtech.ecs.ui.home.escscheme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.engineering.FloorStationFragment;
import com.zxtech.ecs.ui.home.scheme.CheckTipsDialogFragment;
import com.zxtech.ecs.ui.home.scheme.SchemeCompareActivity;
import com.zxtech.ecs.ui.home.scheme.detail.BaseSchemeDetailFragment;
import com.zxtech.ecs.ui.home.scheme.detail.DecorationFragment;
import com.zxtech.ecs.ui.home.scheme.detail.FunctionFragment;
import com.zxtech.ecs.ui.home.scheme.detail.ParametersFragment;
import com.zxtech.ecs.ui.home.scheme.detail.RequireFragment;
import com.zxtech.ecs.util.ConvertUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.zxtech.ecs.common.Constants.CODE_SPECIALNONSTANDARD;

/**
 * 扶梯方案详情
 * Created by syp523 on 2018/4/6.
 */

public class EscalatorSchemeActivity extends BaseActivity implements BaseSchemeDetailFragment.CallBackValue {
    private BaseFragment[] mFragments = new BaseFragment[4];
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    LinearLayout tab_layout;
    @BindViews({R.id.params_tv, R.id.decoration_tv, R.id.function_tv, R.id.require_tv})
    TextView[] tabTexts;
    public Map<String, String> paramMap = new HashMap<>();

    private String elevatorProduct = "自动扶梯";
    private String elevatorType = "BF";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_esc_scheme;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.escalator_scheme));
        if (getIntent().hasExtra("data")) {
            paramMap = (Map<String, String>) getIntent().getSerializableExtra("data");
        }
        paramMap.put(Constants.CODE_ELEVATORPRODUCT, elevatorProduct);
        paramMap.put(Constants.CODE_ELEVATORTYPE, elevatorType);

        showTab(FIRST);
        initData(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_esc_scheme, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_save) {
            if (getIntent().hasExtra("data")) {
                saveCollection(null);
                return true;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

            LayoutInflater inflater = getLayoutInflater();
            final View dialog_edit = inflater.inflate(R.layout.dialog_edit, null);
            builder.setView(dialog_edit).setTitle(getString(R.string.favorites_name))
                    .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText et_userName = (EditText) dialog_edit.findViewById(R.id.content_et);
                            if (TextUtils.isEmpty(et_userName.getText())) {
                                ToastUtil.showLong(getString(R.string.msg29));
                            } else {
                                saveCollection(et_userName.getText().toString());
                            }

                        }
                    }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        } else if (item.getItemId() == R.id.apply_engineering) {

            if (TextUtils.isEmpty(getUserEmail())) {
                ToastUtil.showLong(getString(R.string.msg30));
                return true;
            }
            HashMap<String, String> map = new HashMap<>();
            map.putAll(this.paramMap);
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

        return super.onOptionsItemSelected(item);
    }

    private void saveCollection(String collectionName) {
        if (!TextUtils.isEmpty(collectionName)) {
            paramMap.put("CollectionName", collectionName);
        }
        paramMap.put("ISVERIFY", "1");
        paramMap.put("Guid", paramMap.get("Guid") == null ? "" : paramMap.get("Guid"));
        paramMap.put("TypeId", Constants.ESCALATOR);
        paramMap.put("userGuid", getUserId());
        paramMap.put(Constants.CODE_SYSTEM_LANG, Util.convertEcsLanguage(language));
        paramMap.put(CODE_SPECIALNONSTANDARD, ((RequireFragment) mFragments[FOURTH]).getSelectParams());//非标需求参数
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("params", RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(paramMap)));

        if (!getIntent().hasExtra("data")) {
            List<String> attachDatas = ((RequireFragment) mFragments[FOURTH]).getAttachDatas();
            if (attachDatas != null) {
                for (int i = 0; i < attachDatas.size(); i++) {
                    if (!TextUtils.isEmpty(attachDatas.get(i))) {
                        bodyMap.put("files\";filename=\"attach"+i+".png", RequestBody.create(MediaType.parse("image/png"), new File(attachDatas.get(i))));
                    }
                }
            }

        }
        baseResponseObservable = HttpFactory.getApiService().addOrUpdateCollection(bodyMap);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(EscalatorSchemeActivity.this, true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        finish();
                    }

                    @Override
                    public void onMessage(String message) {
                        CheckTipsDialogFragment dialogFragment = CheckTipsDialogFragment.newInstance();
                        dialogFragment.setData(message);
                        dialogFragment.show(getFragmentManager(), "tips");
                    }
                });
    }

    private void initData(final Bundle savedInstanceState) {

        baseResponseObservable = HttpFactory.getApiService().getParams(Constants.ESCALATOR, elevatorType, elevatorProduct, Constants.ESC_ALL_PARAMETERS, null);
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
                                if (param.getPosition().contains(Constants.ESC_BASIC_INFO) || param.getPosition().contains(Constants.ESC_BASIC_PARAMETERS)) {
                                    paramstersList.add(param);
                                }
                                if (param.getPosition().contains(Constants.ESC_DECORATION_PARAMETERS)) {
                                    decorationList.add(param);
                                }
                                if (param.getPosition().contains(Constants.ESC_FUNCTION_PARAMETERS)) {
                                    functionList.add(param);
                                }
                                if (!getIntent().hasExtra("data")) { //新建
                                    paramMap.put(param.getProECode(), param.getDefaultValue());
                                    paramMap.put(Constants.CODE_LAYOUT_LANG, language.equals(Constants.LANGUAGE_ZH) ? "中文" : "英文");
                                }
                            }


                            if (savedInstanceState == null) {
                                mFragments[FIRST] = EscParametersFragment.newInstance(paramstersList);
                                mFragments[SECOND] = EscDecorationFragment.newInstance(decorationList);
                                mFragments[THIRD] = FunctionFragment.newInstance(functionList);
                                mFragments[FOURTH] = RequireFragment.newInstance();

                                loadMultipleRootFragment(R.id.fl_container, FIRST,
                                        mFragments[FIRST],
                                        mFragments[SECOND],
                                        mFragments[THIRD],
                                        mFragments[FOURTH]
                                );
                            } else {
                                mFragments[FIRST] = findFragment(EscParametersFragment.class);
                                mFragments[SECOND] = findFragment(EscDecorationFragment.class);
                                mFragments[THIRD] = findFragment(FunctionFragment.class);
                                mFragments[FOURTH] = findFragment(RequireFragment.class);
                            }
                        }
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

    @OnClick({R.id.params_tv, R.id.decoration_tv, R.id.function_tv, R.id.require_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.params_tv:
                showHideFragment(mFragments[FIRST]);
                showTab(FIRST);
                break;
            case R.id.decoration_tv:
                showHideFragment(mFragments[SECOND]);
                showTab(SECOND);
                break;
            case R.id.function_tv:
                showHideFragment(mFragments[THIRD]);
                showTab(THIRD);
                break;
            case R.id.require_tv:
                showHideFragment(mFragments[FOURTH]);
                showTab(FOURTH);
                break;
        }
    }

    @Override
    public void sendMessageValue(String key, String strValue) {
        if (paramMap != null) {
            paramMap.put(key, strValue);
        }
    }
}
