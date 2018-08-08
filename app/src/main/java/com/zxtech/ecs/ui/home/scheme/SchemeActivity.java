package com.zxtech.ecs.ui.home.scheme;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.warkiz.widget.IndicatorSeekBar;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.DropDownBean;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.SeekSpiderWeb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 方案推荐
 * Created by syp523 on 2017/9/29.
 */

public class SchemeActivity extends BaseActivity implements View.OnFocusChangeListener, IndicatorSeekBar.OnSeekBarChangeListener, SeekSpiderWeb.TouchUpListener {
    @BindView(R.id.spw)
    SeekSpiderWeb spw;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.width_et)
    EditText width_et;
    @BindView(R.id.depth_et)
    EditText depth_et;
    @BindView(R.id.layer_et)
    EditText layer_et;
    @BindView(R.id.price_et)
    EditText price_et;
    @BindView(R.id.price_sb)
    IndicatorSeekBar price_sb;
    @BindView(R.id.layer_sb)
    IndicatorSeekBar layer_sb;
    @BindView(R.id.depth_sb)
    IndicatorSeekBar depth_sb;
    @BindView(R.id.width_sb)
    IndicatorSeekBar width_sb;
    @BindView(R.id.room_tv)
    TextView room_tv;
    @BindView(R.id.room_layout)
    LinearLayout room_layout;
    @BindView(R.id.category_tv)
    TextView category_tv;
    @BindView(R.id.category_layout)
    LinearLayout category_layout;

    private Gson gson = new Gson();

    private HashMap<String, String> dimens = new HashMap<>();
    private List<String> category_list = new ArrayList();
    private List<String> room_list = new ArrayList();
    private String defaultCategory = "乘客电梯";
    private String defaultRoom = "YES";
    private int minLayer = 8;
    private int maxLayer = 40;
    private int minPrice = 100000;
    private int maxPrice = 450000;
    int minWidth = 1500;
    int maxWidth = 2950;
    int widthDefault = 2100;
    int minDepth = 1550;
    int maxDepth = 3000;
    int depthDefault = 2200;
    int floorDefault = 10;
    int priceDefault = 150000;
    Set<String> permissionSet = new HashSet<>();
    private static final int stepWidth = 10;
    private static final int stepDepth = 10;
    private static final int stepPrice = 100;

    private HashMap<String, String> covertMap = new HashMap<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_scheme;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.find_solution));
        setListener();
        initData();

        int[] parms = new int[]{Integer.parseInt(this.dimens.get(Constants.DIMEN_SSD)) / 2, Integer.parseInt(this.dimens.get(Constants.DIMEN_JNDJ)) / 2, Integer.parseInt(this.dimens.get(Constants.DIMEN_MGD)) / 2, Integer.parseInt(this.dimens.get(Constants.DIMEN_CZFW)) / 2, Integer.parseInt(this.dimens.get(Constants.DIMEN_AQDJ)) / 2};
        spw.setData(5, parms, getResources().getStringArray(R.array.dimension_full));
        spw.setAreaLineColor(getResources().getColor(R.color.main));
        spw.setShowThumbDrawable(true);
        spw.setListener(this);

        linkAction();
    }

    private void initData() {


        //获取权限
        String permission = (String) SPUtils.get(mContext, "permission", "");
        String[] split = permission.split(",");
        for (int i = 0; i < split.length; i++) {
            permissionSet.add(split[i]);
        }
        String category;

        //家用梯只有 无机房，通用电梯只有有机房，乘客电梯都有
        if (permissionSet.contains("Generalpassengerlift")) {
            category = getString(R.string.generalpassenger_elevator);
            category_list.add(category);
            category_tv.setText(category);
            defaultCategory = category;
            covertMap.put(defaultCategory, "通用客梯");
        }
        if (permissionSet.contains("HomeLift")) {//家用梯
            category = getString(R.string.house_elevator);
            category_list.add(category);
            category_tv.setText(category);
            defaultCategory = category;
            covertMap.put(defaultCategory, "家用电梯");
        }
        if (permissionSet.contains("PassengerLift")) {
            category = getString(R.string.passenger_elevator);
            category_list.add(category);
            category_tv.setText(category);
            defaultCategory = category;
            covertMap.put(defaultCategory, "乘客电梯");
        }


        String room;
        if (permissionSet.contains("MachineRoomless")) {
            room = getString(R.string.mrl);
            room_list.add(room);
            room_tv.setText(room);
            defaultRoom = room;
            covertMap.put(room, "NO");
        }

        if (permissionSet.contains("MachineRoom")) {
            room = getString(R.string.mr);
            room_list.add(room);
            room_tv.setText(room);
            defaultRoom = room;
            covertMap.put(room, "YES");
        }


        switchDefaultValue(defaultCategory);
        dimens.put(Constants.DIMEN_SSD, "8");
        dimens.put(Constants.DIMEN_JNDJ, "6");
        dimens.put(Constants.DIMEN_MGD, "4");
        dimens.put(Constants.DIMEN_CZFW, "4");
        dimens.put(Constants.DIMEN_AQDJ, "6");
    }

    private void setListener() {

        width_sb.setOnSeekChangeListener(this);
        depth_sb.setOnSeekChangeListener(this);
        layer_sb.setOnSeekChangeListener(this);
        width_et.setOnFocusChangeListener(this);
        price_sb.setOnSeekChangeListener(this);
        depth_et.setOnFocusChangeListener(this);
        layer_et.setOnFocusChangeListener(this);
        price_et.setOnFocusChangeListener(this);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int progress = 0;
        if (!hasFocus) {
            switch (v.getId()) {
                case R.id.width_et:
                    progress = Integer.valueOf(width_et.getText().toString());
                    if (progress < minWidth) {
                        progress = minWidth;
                        width_et.setText(progress + "");
                    } else if (progress > maxWidth) {
                        progress = maxWidth;
                        width_et.setText(progress + "");
                    }
                    width_sb.setProgress(progress / stepWidth);
                    linkAction();
                    break;
                case R.id.depth_et:
                    progress = Integer.valueOf(depth_et.getText().toString());
                    if (progress < minDepth) {
                        progress = minDepth;
                        depth_et.setText(progress + "");
                    } else if (progress > maxDepth) {
                        progress = maxDepth;
                        depth_et.setText(progress + "");
                    }
                    depth_sb.setProgress(progress / stepDepth);
                    linkAction();

                    break;
                case R.id.layer_et:
                    progress = Integer.valueOf(layer_et.getText().toString());
                    if (progress < minLayer) {
                        progress = minLayer;
                        layer_et.setText(progress + "");
                    } else if (progress > maxLayer) {
                        progress = maxLayer;
                        layer_et.setText(progress + "");
                    }
                    layer_sb.setProgress(progress);
                    break;
                case R.id.price_et:
                    progress = Integer.valueOf(price_et.getText().toString());
                    if (progress < minPrice) {
                        progress = minPrice;
                        price_et.setText(progress + "");
                    } else if (progress > maxPrice) {
                        progress = maxPrice;
                        price_et.setText(progress + "");
                    }
                    price_sb.setProgress(progress / stepPrice);
                    break;
            }
        }
    }

    private boolean seekBarTouch = false;

    @Override
    public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
        if (seekBarTouch) {
            switch (seekBar.getId()) {
                case R.id.width_sb:
                    width_et.setText(progress * stepWidth + "");
                    break;
                case R.id.depth_sb:
                    depth_et.setText(progress * stepDepth + "");
                    break;
                case R.id.layer_sb:
                    layer_et.setText(progress + "");
                    break;
                case R.id.price_sb:
                    price_et.setText(progress * stepPrice + "");
                    break;
            }
        }

    }

    @Override
    public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {
    }

    @Override
    public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {
        seekBarTouch = true;
    }

    @Override
    public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
        seekBarTouch = false;
        if (seekBar.getId() == depth_sb.getId() || seekBar.getId() == width_sb.getId()) {
            linkAction();
        }
    }

    @OnClick(R.id.room_tv)
    public void roomAction(final TextView tv) {
        final List<DropDownBean> fitterDropBean = new ArrayList<>();
        for (String ca : room_list) {
            if (ca.equals(tv.getText().toString())) continue;
            fitterDropBean.add(new DropDownBean(ca, ca));
        }
        if (fitterDropBean.size() == 0) {
            return;
        }
        new DropDownWindow(mContext, room_layout, tv, fitterDropBean, room_layout.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {
                tv.setText(fitterDropBean.get(p).getValue());
                defaultRoom = fitterDropBean.get(p).getValue();
                linkAction();
                this.dismiss();
            }

        };
    }

    @OnClick(R.id.category_tv)
    public void categoryAction(final TextView tv) {
        final List<DropDownBean> fitterDropBean = new ArrayList<>();
        for (String ca : category_list) {
            if (ca.equals(tv.getText().toString())) continue;
            fitterDropBean.add(new DropDownBean(ca, ca));
        }
        if (fitterDropBean.size() == 0) {
            return;
        }

        DropDownWindow mWindow = new DropDownWindow(mContext, category_layout, tv, fitterDropBean, category_layout.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {
                tv.setText(fitterDropBean.get(p).getValue());
                defaultCategory = fitterDropBean.get(p).getValue();
                room_list.clear();
                if (fitterDropBean.get(p).getValue().equals(getString(R.string.house_elevator))) { //无机房
                    room_list.add(getString(R.string.mrl));
                }  else {
                    if (permissionSet.contains("MachineRoom") && permissionSet.contains("MachineRoomless")) {
                        room_list.add(getString(R.string.mrl));
                        room_list.add(getString(R.string.mr));
                        room_tv.setText(getString(R.string.mr));
                        defaultRoom = getString(R.string.mr);
                    } else if (permissionSet.contains("MachineRoom")) {
                        room_list.add(getString(R.string.mr));
                        room_tv.setText(getString(R.string.mr));
                        defaultRoom = getString(R.string.mr);
                    } else if (permissionSet.contains("MachineRoomless")) {
                        room_list.add(getString(R.string.mrl));
                        room_tv.setText(getString(R.string.mrl));
                        defaultRoom = getString(R.string.mrl);
                    }
                }
                switchDefaultValue(defaultCategory);
                linkAction();
                this.dismiss();
            }
        };
    }


    private void switchDefaultValue(String elevatorCategory) {


        if (getString(R.string.house_elevator).equals(elevatorCategory) && Constants.AGENT_BF.equals(getString(R.string.agent))) { //bf家用梯
            minWidth = 1500;
            maxWidth = 2100;
            widthDefault = 1600;
            minDepth = 1500;
            maxDepth = 1900;
            depthDefault = 1500;
            floorDefault = 3;
            priceDefault = 120000;
            room_tv.setText(getString(R.string.mrl));
            defaultRoom = getString(R.string.mrl);
        } else if (getString(R.string.passenger_elevator).equals(elevatorCategory) && Constants.AGENT_BF.equals(getString(R.string.agent))) {
            minWidth = 1500;
            maxWidth = 2950;
            widthDefault = 2100;
            minDepth = 1550;
            maxDepth = 3000;
            depthDefault = 2200;
            floorDefault = 10;
            priceDefault = 150000;

        } else if (getString(R.string.generalpassenger_elevator).equals(elevatorCategory)) {
            minWidth = 1500;
            maxWidth = 2950;
            widthDefault = 2100;
            minDepth = 1550;
            maxDepth = 3000;
            depthDefault = 2200;
            floorDefault = 10;
            priceDefault = 150000;
            room_tv.setText(getString(R.string.mr));
            defaultRoom = getString(R.string.mr);
        } else if (getString(R.string.house_elevator).equals(elevatorCategory) && Constants.AGENT_MNK.equals(getString(R.string.agent))) {//mnk 家用梯
            minWidth = 1650;
            maxWidth = 2000;
            widthDefault = 1650;
            minDepth = 1550;
            maxDepth = 2000;
            depthDefault = 1550;
            floorDefault = 3;
            priceDefault = 120000;
            room_tv.setText(getString(R.string.mrl));
            defaultRoom = getString(R.string.mrl);
        }

        width_sb.getBuilder().setLeftEndText(String.valueOf(minWidth)).setRightEndText(String.valueOf(maxWidth)).setMin(minWidth / stepWidth).setMax(maxWidth / stepWidth).apply();
        width_sb.setProgress(widthDefault / stepWidth);
        width_et.setText(String.valueOf(widthDefault));

        depth_sb.getBuilder().setLeftEndText(String.valueOf(minDepth)).setRightEndText(String.valueOf(maxDepth)).setMin(minDepth / stepDepth).setMax(maxDepth / stepDepth).apply();
        depth_sb.setProgress(depthDefault / stepDepth);
        depth_et.setText(String.valueOf(depthDefault));

        layer_sb.setProgress(floorDefault);
        layer_et.setText(String.valueOf(floorDefault));

        price_sb.setProgress(priceDefault / stepPrice);
        price_et.setText(String.valueOf(priceDefault));
    }


    @OnClick(R.id.confirm_btn)
    public void confirmAction() {
        final HashMap<String, String> param = new HashMap<>();
        param.putAll(dimens);
        param.put("budget", price_et.getText().toString());
        param.put(Constants.CODE_DIM_SHAFT_WIDTH_WW, width_et.getText().toString());
        param.put(Constants.CODE_DIM_SHAFT_DEPTH_WD, depth_et.getText().toString());
        param.put(Constants.CODE_ELE_TYPE, covertMap.get(defaultCategory));
        param.put(Constants.CODE_MACHINEROOM, covertMap.get(defaultRoom));
        param.put(Constants.CODE_QTY_NUMBER_OF_FLOORS, layer_et.getText().toString());

        baseResponseObservable = HttpFactory.getApiService().recommendScheme(param);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<Programme>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Programme>>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Programme>> response) {

                        Intent it = new Intent();
                        it.putExtra("datas", (Serializable) response.getData());
                        it.putExtra("budget", price_et.getText().toString());
                        startActivity(SchemeCompareActivity.class, it);
                    }
                });

    }

    private void linkAction() {

        HashMap<String, String> param = new HashMap<>();
        param.put(Constants.CODE_DIM_SHAFT_WIDTH_WW, width_et.getText().toString());
        param.put(Constants.CODE_DIM_SHAFT_DEPTH_WD, depth_et.getText().toString());
        param.put(Constants.CODE_ELE_TYPE, covertMap.get(defaultCategory));
        param.put(Constants.CODE_MACHINEROOM, covertMap.get(defaultRoom));
        baseResponseObservable = HttpFactory.getApiService().recommendSchemeScript(gson.toJson(param));
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<Map<String, String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, String>>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, String>> response) {
                        updateScope(response.getData());
                    }
                });
    }

    private void updateScope(Map<String, String> map) {
        if (Util.isNumeric(map.get("FloorMin")) && Util.isNumeric(map.get("FloorMax"))) {
            int floorMin = Integer.valueOf(map.get("FloorMin"));
            int floorMax = Integer.valueOf(map.get("FloorMax"));

            layer_sb.getBuilder().setLeftEndText(map.get("FloorMin")).setRightEndText(map.get("FloorMax")).setMin(floorMin).setMax(floorMax).apply();
            this.minLayer = floorMin;
            this.maxLayer = floorMax;
        }
        if (Util.isNumeric(map.get("PriceMin")) && Util.isNumeric(map.get("PriceMax"))) {
            int priceMin = Integer.valueOf(map.get("PriceMin"));
            int priceMax = Integer.valueOf(map.get("PriceMax"));
            price_sb.getBuilder().setLeftEndText(priceMin + "").setRightEndText(priceMax + "").setMin(priceMin / stepPrice).setMax(priceMax / stepPrice).apply();
            this.minPrice = priceMin;
            this.maxPrice = priceMax;
        }

        int floorDefault = 10;
        int priceDefault = 150000;
        if (getString(R.string.house_elevator).equals(defaultCategory)) {
            floorDefault = 3;
            priceDefault = 120000;
        }

        layer_sb.setProgress(floorDefault);
        layer_et.setText(String.valueOf(floorDefault));

        price_sb.setProgress(priceDefault / stepPrice);
        price_et.setText(String.valueOf(priceDefault));
    }


    @Override
    public void onProgressValue(int ability_id, int val) {
        String progressValue = String.valueOf((val + 1) * 2);
        switch (ability_id) {
            case 0:
                dimens.put(Constants.DIMEN_SSD, progressValue);
                break;
            case 1:
                dimens.put(Constants.DIMEN_JNDJ, progressValue);
                break;
            case 2:
                dimens.put(Constants.DIMEN_MGD, progressValue);
                break;
            case 3:
                dimens.put(Constants.DIMEN_CZFW, progressValue);
                break;
            case 4:
                dimens.put(Constants.DIMEN_AQDJ, progressValue);
                break;

        }
    }
}
