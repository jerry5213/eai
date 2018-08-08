package com.zxtech.ecs.ui.home.engineering;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.FloorStationAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.event.EventFloorStation;
import com.zxtech.ecs.model.DropDownBean;
import com.zxtech.ecs.model.FloorStationParam;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.ui.home.scheme.detail.BaseSchemeDetailFragment;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.DropDownWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnFocusChange;

/**
 * 土建-层站参数
 * Created by syp523 on 2018/4/2.
 */

public class FloorStationFragment extends BaseSchemeDetailFragment implements BaseQuickAdapter.OnItemChildClickListener, View.OnTouchListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.other_layout)
    RelativeLayout other_layout;
    @BindView(R.id.start_floor_et)
    EditText start_floor_et;
    @BindView(R.id.end_floor_et)
    EditText end_floor_et;
    @BindView(R.id.floor_height_et)
    EditText floor_height_et;

    private int defaultStart = 1;
    private int defaultEnd = 1;
    private int defaultFloorHeight = 3200;

    private List<FloorStationParam> mDatas = new ArrayList<>();
    private FloorStationAdapter mAdapter;

    public static FloorStationFragment newInstance() {
        Bundle args = new Bundle();
        FloorStationFragment fragment = new FloorStationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_floor_station_param;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new FloorStationAdapter(R.layout.item_floorstation, mDatas);
        recyclerView.setAdapter(mAdapter);

        EventBus.getDefault().register(this);

        mAdapter.setOnItemChildClickListener(this);
        other_layout.setOnTouchListener(this);
        initParam();
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

    private void initParam() {
        if (getParamsValue(Constants.CODE_QTY_NUMBER_OF_FLOORS) != null && Util.isNumeric(getParamsValue(Constants.CODE_QTY_NUMBER_OF_FLOORS))) {
            defaultEnd = Integer.valueOf(getParamsValue(Constants.CODE_QTY_NUMBER_OF_FLOORS));
            if (getParamsValue(Constants.CODE_TH) != null && Util.isNumeric(getParamsValue(Constants.CODE_TH))) {
                defaultFloorHeight = Integer.valueOf(getParamsValue(Constants.CODE_TH)) / (Integer.valueOf(getParamsValue(Constants.CODE_QTY_NUMBER_OF_FLOORS)) - 1);
            }
        }
        start_floor_et.setText(String.valueOf(defaultStart));
        end_floor_et.setText(String.valueOf(defaultEnd));
        floor_height_et.setText(String.valueOf(defaultFloorHeight));
        updateFloorList();
    }

    private void updateFloorList() {
        mDatas.clear();
        //底坑层
        mDatas.add(new FloorStationParam(getParamsValue(Constants.CODE_DIM_PIT_HEIGHT_PH), "-", "-", "-", "-", "-", "-"));
        int h = 0;
        for (int i = defaultStart; i <= defaultEnd; i++) {
            if (defaultStart < 0 && i == 0) {
                continue;
            }
            String floorHeight;
            String serviceA;
            String serviceACode;
            String serviceC = "-";
            String serviceCCode = "-";
            if (i == defaultEnd) { //顶层
                floorHeight = getParamsValue(Constants.CODE_DIM_HEADROOM_SH);
            } else {
                floorHeight = String.valueOf(defaultFloorHeight);
            }

            String thrEntr = getParamsValue(Constants.CODE_THR_ENTR);
            if (i == defaultStart) {
                serviceA = getString(R.string.base_station);
                serviceACode = "M";
            } else {
                serviceA = getString(R.string.normal);
                serviceACode = "X";
            }

            if (thrEntr != null && "YES".equals(thrEntr)) { //贯通门联动
                serviceC = serviceA;
                serviceCCode = serviceACode;
            }
            h++;
            mDatas.add(new FloorStationParam(floorHeight, String.valueOf(h), String.valueOf(i), serviceA, serviceACode, serviceC, serviceCCode));

        }
        putParams(Constants.CODE_QTY_NUMBER_OF_FLOORS, String.valueOf(mDatas.size() - 1));
        mAdapter.notifyDataSetChanged();
        linkTH();
    }

    @OnFocusChange({R.id.start_floor_et, R.id.end_floor_et, R.id.floor_height_et})
    public void onEditChange(View view, boolean hasFocus) {
        if (hasFocus) return;
        if (TextUtils.isEmpty(end_floor_et.getText()) || TextUtils.isEmpty(start_floor_et.getText()) || TextUtils.isEmpty(floor_height_et.getText())) {
            return;
        }
        if (Integer.valueOf(start_floor_et.getText().toString()) == 0) {
            return;
        }
        switch (view.getId()) {
            case R.id.start_floor_et:
                defaultStart = Integer.valueOf(start_floor_et.getText().toString());
                break;
            case R.id.end_floor_et:
                defaultEnd = Integer.valueOf(end_floor_et.getText().toString());
                break;

            case R.id.floor_height_et:
                defaultFloorHeight = Integer.valueOf(floor_height_et.getText().toString());
                break;

        }
        updateFloorList();
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        switch (view.getId()) {
            case R.id.floor_height_tv:
                if (position == 0 || position == (mDatas.size() - 1)) {
                    return;
                }
                showEditwindow((TextView) view, getString(R.string.hegiht), position);
                break;
            case R.id.identifying_floor_tv:
                showEditwindow((TextView) view, getString(R.string.identify_floor), position);
                break;
            case R.id.service_a_tv:
                showPopwindow((TextView) view, position);
                break;
            case R.id.service_c_tv:
                String thrEntr = getParamsValue(Constants.CODE_THR_ENTR);
                if (thrEntr != null && "YES".equals(thrEntr)) { //贯通门联动
                    showPopwindow((TextView) view, position);
                }
                break;

        }
    }


    private void showPopwindow(final TextView view, final int position) {
        final List<DropDownBean> fitterDropBean = new ArrayList<>();
        fitterDropBean.add(new DropDownBean("M", getString(R.string.base_station)));
        fitterDropBean.add(new DropDownBean("N", getString(R.string.non_stop)));
        fitterDropBean.add(new DropDownBean("E", getString(R.string.safety_door)));
        fitterDropBean.add(new DropDownBean("X", getString(R.string.normal)));
        fitterDropBean.add(new DropDownBean("-", "-"));
        DropDownWindow mWindow = new DropDownWindow(mContext, view, fitterDropBean, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {
                view.setText(fitterDropBean.get(p).getValue());
                if (view.getId() == R.id.service_a_tv) {
                    mDatas.get(position).setServiceA(fitterDropBean.get(p).getValue());
                    mDatas.get(position).setServiceACode(fitterDropBean.get(p).getKey());
                }else{
                    mDatas.get(position).setServiceC(fitterDropBean.get(p).getValue());
                    mDatas.get(position).setServiceCCode(fitterDropBean.get(p).getKey());
                }

                this.dismiss();
            }

        };
    }

    private void showEditwindow(final TextView view, String title, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = getLayoutInflater();
        final View dialog_edit = inflater.inflate(R.layout.dialog_edit, null);
        final EditText et_userName = dialog_edit.findViewById(R.id.content_et);
        et_userName.setHint(view.getText());
        if (view.getId() == R.id.identifying_floor_tv) {
            et_userName.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            et_userName.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        builder.setView(dialog_edit).setTitle(title)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = et_userName.getText().toString();
                        if (TextUtils.isEmpty(value)) {
                            return;
                        }
                        view.setText(value);
                        if (view.getId() == R.id.floor_height_tv) {
                            mDatas.get(position).setHeight(value);
                            linkTH();
                        } else if (view.getId() == R.id.identifying_floor_tv) {
                            mDatas.get(position).setIdentifyingFloor(value);
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

    private void linkTH() {
        int totalHeight = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            if (i == 0 || i == (mDatas.size() - 1)) {
                continue;
            }
            totalHeight += Integer.valueOf(mDatas.get(i).getHeight());
        }
        putParams(Constants.CODE_TH, String.valueOf(totalHeight));
    }

    public JsonObject getFloorStation() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("StartFloor", String.valueOf(defaultStart));
        jsonObject.addProperty("EndFloor", String.valueOf(defaultEnd));
        JsonArray array = new JsonArray();
        JsonObject eleJsonObject = null;
        for (int i = 0; i < mDatas.size(); i++) {
            if (i == 0) continue;
            FloorStationParam floorStationParam = mDatas.get(i);
            eleJsonObject = new JsonObject();
            eleJsonObject.addProperty("AFLOOROD", floorStationParam.getServiceACode());
            eleJsonObject.addProperty("CFLOOROD", floorStationParam.getServiceCCode());
            eleJsonObject.addProperty("FLOORHEIGHT", floorStationParam.getHeight());
            eleJsonObject.addProperty("FLOORNAME", floorStationParam.getEngineeringFloor());
            eleJsonObject.addProperty("FLOOR_MARKS", floorStationParam.getIdentifyingFloor());
            array.add(eleJsonObject);
        }
        jsonObject.add("EleFloors", array);
        return jsonObject;
    }


    @Override
    public void scriptReturnParam(List<ScriptReturnParam> returnParams) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventResetFloor(EventFloorStation event) {
        initParam();
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        other_layout.setFocusable(true);
        other_layout.setFocusableInTouchMode(true);
        other_layout.requestFocus();
        return false;
    }
}
