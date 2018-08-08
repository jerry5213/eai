package com.zxtech.ecs.ui.home.scheme.detail;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.decorationvr.DecorationVrWrapper;
import com.zxtech.decorationvr.ElevatorSpec;
import com.zxtech.decorationvr.IVrChangeCompleted;
import com.zxtech.decorationvr.PartType;
import com.zxtech.decorationvr.VrView;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.PartAdapter;
import com.zxtech.ecs.adapter.PartConentAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.scheme.DecorationWaitDialogFragment;
import com.zxtech.ecs.util.ScreenUtils;
import com.zxtech.ecs.util.StatusBarUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zxtech.ecs.common.Constants.TAG;
import static com.zxtech.ecs.model.ScriptReturnParam.DISABLE;
import static com.zxtech.ecs.model.ScriptReturnParam.RESET;

/**
 * 装潢参数
 * Created by syp523 on 2018/1/22.
 */

public class DecorationFragment extends BaseSchemeDetailFragment implements SmartSearchDialogFragment.OnDialogListener, IVrChangeCompleted {


    @BindView(R.id.part_rv)
    RecyclerView part_rv;
    @BindView(R.id.part_content_rv)
    RecyclerView part_content_rv;
    @BindView(R.id.vr_layout)
    RelativeLayout vr_layout;
    @BindView(R.id.part_expand_btn)
    ImageView part_expand_btn;


    private PartAdapter adapter = null;
    private PartConentAdapter optionsAdapter = null;
    private List<Parameters> parts = new ArrayList<>();
    private List<Parameters> carParts = new ArrayList<>();
    private List<Parameters> hallParts = new ArrayList<>();
    private List<Parameters.Option> options = new ArrayList<>();
    private Parameters selectedParameters = new Parameters();
    private Parameters selectedFirstParameters = new Parameters();

    private static final int CAR_MODE = 0;
    private static final int HALL_MODE = 1;
    private int currentMode = 0;
    //已选择的商品
    private boolean scriptHasChild = false;
    private boolean isFirst = true;
    private boolean isItemExpand;
    private String elevatorSizeId;


    public static DecorationFragment newInstance(List<Parameters> parameters) {
        Bundle args = new Bundle();
        DecorationFragment fragment = new DecorationFragment();
        args.putSerializable("data", (Serializable) parameters);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_decoration;
    }


    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new PartAdapter(getActivity(), R.layout.item_part, parts, language);
        part_rv.setLayoutManager(layoutManager);
        part_rv.setAdapter(adapter);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        part_content_rv.setLayoutManager(layoutManager1);
        optionsAdapter = new PartConentAdapter(getActivity(), R.layout.item_part_content, options);
        part_content_rv.setAdapter(optionsAdapter);


        waitDialogFragment = new DecorationWaitDialogFragment();
        //初始化viewPager
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                if (parts.get(position).getOption() != null) {
                    isItemExpand = true;
                    part_expand_btn.setImageResource(R.drawable.item_down);
                    part_content_rv.setVisibility(View.VISIBLE);
                    options.clear();
                    ScriptReturnParam scriptReturnParam = getScriptReturnParam(parts.get(position).getProECode());
                    if (scriptReturnParam != null) {
                        if (DISABLE == scriptReturnParam.getOperation()) { //禁用控件
                            ToastUtil.showLong(getString(R.string.msg36));
                            return;
                        } else if (RESET == scriptReturnParam.getOperation()) { //联动重新赋值
                            options.addAll(scriptReturnParam.getResetOptions());
                        } else {
                            options.addAll(parts.get(position).getOption());
                        }
                    } else { //没有联动关系
                        options.addAll(parts.get(position).getOption());
                    }
                    selectedParameters = parts.get(position);
                    selectedFirstParameters = parts.get(position);
                    optionsAdapter.setSelectedValue(getParamsValue(selectedParameters.getProECode()));//设置选中值
                    //    switchBottomStyle();
                    optionsAdapter.notifyDataSetChanged();
                    //选中变色
                    adapter.setSelectedPosition(position);
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showLong(getString(R.string.msg8));
                }


            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        optionsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String selectValue = options.get(position).getValue();
                if (selectValue == null || selectValue.equals(optionsAdapter.getSelectedValue())) {
                    ToastUtil.showLong(getString(R.string.msg2));
                    return; //已选择的商品不再选择
                }
                optionsAdapter.setSelectedValue(selectValue); //设置选择项
                optionsAdapter.notifyDataSetChanged();
                putParams(selectedParameters.getProECode(), selectValue);
                //  List<Parameters> parametersList = adapter.getDatas();

                //没有联动
                if (selectedParameters.getScriptInfo() == null || selectedParameters.getScriptInfo().size() == 0) {
                    if (selectedParameters.isHasChildren()) { //有二级 选择完二级替换装潢
                        showSecondLevelWindow();
                    } else { //没有二级 直接替换装潢
                        requestConvertScript();
                    }
                } else { //有联动 需先加载联动 再弹出二级

                    if (selectedParameters.isHasChildren()) { //有二级
                        scriptHasChild = true;
                        scriptHandle(selectedParameters);
                    } else {
                        scriptHasChild = false;
                        scriptHandle(selectedParameters);
                    }
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initVRDir();
        createVrView();
        List<Parameters> parameters = (List<Parameters>) getArguments().getSerializable("data");
        if (parameters != null && parameters.size() > 0) {
            for (Parameters param : parameters) {
                if (param.getPosition().contains(Constants.FUNCTION_PARAMETERS_CAR)) {
                    carParts.add(param);
                } else if (param.getPosition().contains(Constants.FUNCTION_PARAMETERS_HALL)) {
                    hallParts.add(param);
                }
            }
            switchPart();

            requestConvertScript();

            isFirst = false;
        }

//        baseResponseObservable = HttpFactory.getApiService().getParams(Constants.ELEVATOR, getElevatorType(), getElevatorProduct(), Constants.DECORATION_PARAMETERS, null);
//        baseResponseObservable
//                .compose(RxHelper.<BaseResponse<List<Parameters>>>rxSchedulerHelper())
//                .subscribe(new DefaultObserver<BaseResponse<List<Parameters>>>(getActivity(), true) {
//                    @Override
//                    public void onSuccess(BaseResponse<List<Parameters>> response) {
//
//                    }
//                });


    }


    private VrView vrView;


    private void initVRDir() {
        String dir = APPConfig.ECS_MODEL_RESOURCES;
        DecorationVrWrapper.SetDownloadServerURL("http://edp.5000m.com:28753", "http://edp.5000m.com:9050/");
        DecorationVrWrapper.SetAppDirectory(dir, dir);
    }

    @Override
    public void onPause() {
        if (vrView != null) {
            vrView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (vrView != null) {
            vrView.onResume();
        }
    }


    public void createVrView() {
        if (vrView == null) {
            vrView = new VrView(mContext);
            ElevatorSpec es = new ElevatorSpec();
            es.size_id = 1;
            es.car_width = 160;
            es.car_height = 240;
            es.door_height = 210;
            es.car_depth = 150;
            es.door_width = 90;
            vrView.VrInit(es);
            vr_layout.addView(vrView);
        }

//        DecorationVrWrapper.SetPart(100000, 2, true);
//        DecorationVrWrapper.SetPartByGoods(PartType.PartTypeCarConfig, 15409, true);
        vrView.SetVrCompletedCallBack(this);
    }

    public void deleteVrView() {
        if (vrView != null) {
            vrView.onPause();
            vrView.VrRelease();
            vr_layout.removeView(vrView);
            vrView = null;
        }
    }


    @OnClick(R.id.search_btn)
    public void searchAction() {
        searchMore();
    }

    @OnClick(R.id.part_expand_btn)
    public void expandPartAction() {
        switchExpand();
    }


    private void switchExpand() {
        if (isItemExpand) {
            isItemExpand = false;
            part_expand_btn.setImageResource(R.drawable.item_up);
            part_content_rv.setVisibility(View.GONE);
        } else {
            isItemExpand = true;
            part_expand_btn.setImageResource(R.drawable.item_down);
            part_content_rv.setVisibility(View.VISIBLE);
        }
    }

    private void searchMore() {
        if (selectedParameters == null || TextUtils.isEmpty(selectedParameters.getProECode())) {
            ToastUtil.showLong(getString(R.string.msg10));
            return;
        }
        if (TextUtils.isEmpty(selectedParameters.getPartType())) {
            ToastUtil.showLong(getString(R.string.msg9));
            return;
        }
        if (!Util.isNumeric(selectedParameters.getPartType())) {
            return;
        } else {
            int partTypeId = Integer.parseInt(this.selectedParameters.getPartType());
            if (partTypeId == 0) {
                ToastUtil.showLong(getString(R.string.msg9));
                return;
            }
            requestGoods(partTypeId);
        }
    }


    @OnClick(R.id.switch_btn)
    public void switchAction() {
        if (currentMode == CAR_MODE) {
            currentMode = HALL_MODE;
            vrView.queueEvent(new Runnable() {
                @Override
                public void run() {
                    DecorationVrWrapper.SendVrMessage("msg_gotohall", 0);
                }
            });

        } else {
            currentMode = CAR_MODE;
            vrView.queueEvent(new Runnable() {
                @Override
                public void run() {
                    DecorationVrWrapper.SendVrMessage("msg_enter360", 0);
                }
            });
        }
        switchPart();

    }

    //效果图
    @OnClick(R.id.cad_btn)
    public void cadAction() {
        if (TextUtils.isEmpty(getUserEmail())) {
            ToastUtil.showLong(getString(R.string.msg30));
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.putAll(getAllParams());
        map.put("UserEmail", getUserEmail());
        map.put("UserGuid", getUserId());
        map.put("TypeId", "Elevator");
        baseResponseObservable = HttpFactory.getApiService().applyDecorationDesign(new Gson().toJson(map));
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        ToastUtil.showLong(getString(R.string.msg31));
                    }
                });
    }


    private void switchPart() {

        adapter.setSelectedPosition(0);//恢复选中第一个
        parts.clear();
        if (currentMode == CAR_MODE) {
            parts.addAll(carParts);
        } else {
            parts.addAll(hallParts);
        }
        options.clear();
        if (parts.size() > 0) {
            selectedParameters = parts.get(0);
            options.addAll(parts.get(0).getOption());
            if (isFirst) {
                part_expand_btn.setImageResource(R.drawable.item_down);
                isItemExpand = true;
            }
            optionsAdapter.setSelectedValue(getParamsValue(selectedParameters.getProECode())); //设置选择项
        } else {
            selectedParameters = new Parameters();
        }

        adapter.notifyDataSetChanged();
        optionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        deleteVrView();
        super.onDestroyView();
        mHandler.removeCallbacksAndMessages(null);
    }


    //
    private void requestGoods(final int partId) {
        baseResponseObservable = HttpFactory.getApiService().getGoods(partId, elevatorSizeId, 0, APPConfig.PAGE_SIZE);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<Map<String, List<Map<String, String>>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, List<Map<String, String>>>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, List<Map<String, String>>>> response) {

                        SmartSearchDialogFragment dialogFragment = SmartSearchDialogFragment.newInstance(partId);
                        dialogFragment.mlistener = DecorationFragment.this;
                        dialogFragment.title = selectedParameters.getName();
                        dialogFragment.setElevatorSizeId(elevatorSizeId);
                        if (response.getData().get("goods") != null) {
                            dialogFragment.getGoods().addAll(response.getData().get("goods"));
                        }
                        if (response.getData().get("styles") != null) {
                            dialogFragment.getStyleConditionDatas().addAll(response.getData().get("styles"));
                        }
                        if (response.getData().get("colors") != null) {
                            dialogFragment.getColorConditionDatas().addAll(response.getData().get("colors"));
                        }
                        if (response.getData().get("materialTypes") != null) {
                            dialogFragment.getMaterialTypeDatas().addAll(response.getData().get("materialTypes"));
                        }
                        dialogFragment.show(((Activity) mContext).getFragmentManager(), "Smart_Search");
                    }
                });
    }


    @Override
    public void onDialogClick(final int partTypeId, final int goodsId) {
        showAnimWaitDialog();
        vrView.queueEvent(new Runnable() {
            @Override
            public void run() {
                if (partTypeId == PartType.PartTypeMaterial) {
                    //更换材质 需要所属部件partType
                    if (selectedFirstParameters.getPartType() != null && Util.isNumeric(selectedFirstParameters.getPartType()))
                        DecorationVrWrapper.SetMaterialByGoods(Integer.parseInt(selectedFirstParameters.getPartType()), goodsId, true);
                } else {
                    DecorationVrWrapper.SetPartByGoods(partTypeId, goodsId, true);
                }
            }
        });
    }

    private void showSecondLevelWindow() {
        if (selectedParameters.isPosition()) { //二级位置
            PlaceDialog placeDialog = new PlaceDialog(mContext, selectedParameters.getProECode(), getAllParams(), getScriptReturnParams()) {
                @Override
                public void selected(String code, String value) {
                    if (code != null) {
                        putParams(code, value);
                    }
                    requestConvertScript();
                }
            };
            placeDialog.show();
        } else { //二级材质
            MaterialDialog dialog = new MaterialDialog(mContext, selectedParameters.getProECode(), getAllParams(), getScriptReturnParams()) {
                @Override
                public void selected(String code, String value) {
                    if (code != null) {
                        putParams(code, value);
                    }
                    requestConvertScript();
                }

                @Override
                public void search(Parameters secondLevelParameters) {
                    selectedParameters = secondLevelParameters;
                    searchMore();
                }
            };
            dialog.show();
        }
    }

    private int[] calculateDecPadding() {
        int[] decLocation = new int[2];
        int[] location = new int[2];
        vr_layout.getLocationInWindow(location);
        int y = location[1];
        int h = vr_layout.getHeight();

        int s_h = ScreenUtils.getScreenHeight(mContext);
        int bar_h = StatusBarUtils.getStatusBarHeight(mContext);
        decLocation[0] = y;
        decLocation[1] = s_h - y - h;
        return decLocation;
    }


    @Override
    public void scriptReturnParam(List<ScriptReturnParam> returnParam) {
        if (returnParam != null) {
            if (scriptHasChild) { //有二级需弹出二级界面
                showSecondLevelWindow();
            } else {
                requestConvertScript();
            }
        }
    }

    private void requestConvertScript() {
        showAnimWaitDialog();
        putParams("Ele_M", getElevatorType());
        baseResponseObservable = HttpFactory.getApiService().customScripts(new Gson().toJson(getAllParams()));
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                    @Override
                    public void onSuccess(final BaseResponse<String> response) {
                        if (vrView == null) {
                            return;
                        }
                        final String content = response.getData().replace("\"", "");
                        Log.d(TAG, content);
                        String[] split = content.replace("'", "").split(",");
                        int index = -1;
                        if (split != null) {
                            for (int i = 0; i < split.length; i++) {
                                if ("ElevatorSizeId".equals(split[i])) {
                                    index = i + 1;
                                }
                                if (index == i) {
                                    elevatorSizeId = split[i];
                                    break;
                                }

                            }
                        }

                        vrView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                DecorationVrWrapper.SetContentString(content);
                            }
                        });

                    }
                });
    }

    private DecorationWaitDialogFragment waitDialogFragment;
    private boolean isLoading = false;

    private void showAnimWaitDialog() {
        if (!isLoading) {
            isLoading = true;
            waitDialogFragment.setTopBottomPadding(calculateDecPadding());
            waitDialogFragment.show(getActivity().getFragmentManager(), "DecorationWait");
        }
    }

    private final Handler mHandler = new MyHandler(this);

    @Override
    public void VrChangeCompletedCallBack(int wpara, int lpara, String content) {
        Message msg = new Message();
        msg.arg1 = wpara;
        msg.obj = content;
        mHandler.sendMessage(msg);

    }


    public void vrCallBackMessage(Message msg) {
        if (msg.arg1 == -1) {
            ToastUtil.showLong("参数错误：" + msg.obj);
        }
        if (waitDialogFragment != null) {
            waitDialogFragment.dismiss();
            isLoading = false;
        }
    }


    private static class MyHandler extends Handler {
        private final WeakReference<DecorationFragment> mActivity;

        public MyHandler(DecorationFragment activity) {
            mActivity = new WeakReference<DecorationFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
            mActivity.get().vrCallBackMessage(msg);
        }
    }

}
