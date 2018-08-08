package com.zxtech.ecs.ui.home.scheme.detail;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.event.EventFloorStation;
import com.zxtech.ecs.event.EventStandardParam;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.EditDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zxtech.ecs.model.ScriptReturnParam.DISABLE;
import static com.zxtech.ecs.model.ScriptReturnParam.ENABLE;
import static com.zxtech.ecs.model.ScriptReturnParam.RESET;
import static com.zxtech.ecs.model.ScriptReturnParam.SET;

/**
 * 土建参数
 * Created by syp523 on 2018/1/22.
 */

public class ParametersFragment extends BaseSchemeDetailFragment {
    @BindView(R.id.basic_params_layout)
    LinearLayout basic_params_layout;
    @BindView(R.id.well_params_layout)
    LinearLayout well_params_layout;
    @BindView(R.id.room_params_layout)
    LinearLayout room_params_layout;
    @BindView(R.id.location_params_layout)
    LinearLayout location_params_layout;
    @BindView(R.id.plane)
    ImageView plane;
    @BindView(R.id.solid)
    ImageView solid;
    @BindView(R.id.door)
    ImageView door;
    @BindView(R.id.left_layout)
    RelativeLayout left_layout;
    @BindView(R.id.right_layout)
    RelativeLayout right_layout;
    @BindView(R.id.basic_expand_iv)
    ImageView basic_expand_iv;
    @BindView(R.id.well_expand_iv)
    ImageView well_expand_iv;
    @BindView(R.id.room_expand_iv)
    ImageView room_expand_iv;
    @BindView(R.id.location_expand_iv)
    ImageView location_expand_iv;
    @BindView(R.id.room_area_layout)
    RelativeLayout room_area_layout;
    @BindView(R.id.room_area_line)
    View room_area_line;

    private int textMaxWidth = 75;
    private int textSize = 11;

    private List<Parameters> parameters = new ArrayList<>();

    private Map<String, View> views = new HashMap<>();
    //底部图对应的参数
    private Map<String, TextView> planFlagMap = new HashMap<>();
    private Map<String, TextView> solidFlagMap = new HashMap<>();
    private Map<String, TextView> doorFlagMap = new HashMap<>();


    private int[] planeSize = new int[]{508, 450};
    private int[] solidSize = new int[]{300, 1568};
    private int[] doorSize = new int[]{364, 470};

    public static ParametersFragment newInstance(List<Parameters> parameters) {
        Bundle args = new Bundle();
        ParametersFragment fragment = new ParametersFragment();
        args.putSerializable("data", (Serializable) parameters);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_parameters;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        if (!getMR()) {
            solid.setImageResource(R.drawable.mrl_solid);
            room_params_layout.setVisibility(View.GONE);
            room_area_layout.setVisibility(View.GONE);
            room_area_line.setVisibility(View.GONE);
        }
        if (isHouseElevator()) {
            door.setImageResource(R.drawable.homelift_door);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            TextView floorsTv = (TextView) views.get(Constants.CODE_QTY_NUMBER_OF_FLOORS);
            if (floorsTv != null) {
                floorsTv.setText(getParamsValue(Constants.CODE_QTY_NUMBER_OF_FLOORS));
            }
            TextView ohTv = (TextView) views.get(Constants.CODE_DIM_HEADROOM_SH);
            if (ohTv != null) {
                ohTv.setText(getParamsValue(Constants.CODE_DIM_HEADROOM_SH));
            }
            TextView pdTv = (TextView) views.get(Constants.CODE_DIM_PIT_HEIGHT_PH);
            if (pdTv != null) {
                pdTv.setText(getParamsValue(Constants.CODE_DIM_PIT_HEIGHT_PH));
            }
            TextView thTv = (TextView) views.get(Constants.CODE_TH);
            if (thTv != null) {
                thTv.setText(getParamsValue(Constants.CODE_TH));
            }
        }
    }

    private void createLayout(String seatTag, List<Parameters> parameters) {
        if (parameters.size() % 2 == 1) { //补全奇数情况
            parameters.add(new Parameters(""));
        }
        LinearLayout linearLayout = null;
        for (int i = 0; i < parameters.size(); i++) {
            Parameters parameter = parameters.get(i);

            updateBottomParam(parameter.getProECode(), getParamsValue(parameter.getProECode()));
            if (i % 2 == 0) {
                linearLayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext, 32));
                layoutParams.setMargins(0, 15, 0, 15);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                if (Constants.BASIC_SUB_PARAMETERS.equals(seatTag)) {
                    basic_params_layout.addView(linearLayout);
                } else if (Constants.BASIC_WELL_PARAMETERS.equals(seatTag)) {
                    well_params_layout.addView(linearLayout);
                } else if (Constants.BASIC_ROOM_PARAMETERS.equals(seatTag)) {
                    room_params_layout.addView(linearLayout);
                } else if (Constants.BASIC_LOCATION_PARAMETERS.equals(seatTag)) {
                    location_params_layout.addView(linearLayout);
                }
                //第一列
                LinearLayout oneLayout = new LinearLayout(mContext);
                oneLayout.setGravity(Gravity.CENTER_VERTICAL);
                LinearLayout.LayoutParams oneParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                oneParams.weight = 1;
                oneParams.width = 0;
                oneLayout.setLayoutParams(oneParams);
                oneLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(oneLayout);

                TextView textView = new TextView(mContext);
                textView.setText(parameter.getName());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                textView.setMaxWidth(DensityUtil.dip2px(mContext, textMaxWidth));
                textView.setMaxLines(2);
                LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, textMaxWidth), LinearLayout.LayoutParams.WRAP_CONTENT);
                titleParams.leftMargin = 20;
                textView.setLayoutParams(titleParams);
                oneLayout.addView(textView);

                LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                contentParams.setMargins(20, 0, 40, 0);
                if (parameter.getDataType().equals("input")) {
                    LinearLayout inputWidget = createInputWidget(parameter);
                    inputWidget.setLayoutParams(contentParams);
                    oneLayout.addView(inputWidget);
                } else if (parameter.getDataType().equals("select")) {
                    TextView selectWidget = createSelectWidget(parameter);
                    selectWidget.setLayoutParams(contentParams);
                    oneLayout.addView(selectWidget);
                }
            } else {
                //第二列
                LinearLayout twoLayout = new LinearLayout(mContext);
                twoLayout.setGravity(Gravity.CENTER_VERTICAL);
                LinearLayout.LayoutParams oneParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                oneParams.weight = 1;
                oneParams.width = 0;
                oneParams.gravity = Gravity.CENTER_VERTICAL;
                twoLayout.setLayoutParams(oneParams);
                twoLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(twoLayout);

                TextView textView = new TextView(mContext);
                textView.setText(parameter.getName());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                textView.setMaxWidth(DensityUtil.dip2px(mContext, textMaxWidth));
                textView.setMaxLines(2);
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, textMaxWidth), LinearLayout.LayoutParams.WRAP_CONTENT);
                textParams.leftMargin = 40;
                textView.setLayoutParams(textParams);
                twoLayout.addView(textView);

                LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                contentParams.gravity = Gravity.CENTER_VERTICAL;
                contentParams.setMargins(20, 0, 20, 0);
                if (parameter.getDataType().equals("select")) {
                    TextView selectWidget = createSelectWidget(parameter);
                    selectWidget.setLayoutParams(contentParams);
                    twoLayout.addView(selectWidget);
                } else if (parameter.getDataType().equals("input")) {
                    LinearLayout inputWidget = createInputWidget(parameter);
                    inputWidget.setLayoutParams(contentParams);
                    twoLayout.addView(inputWidget);
                }
            }
        }
    }

    private LinearLayout createInputWidget(final Parameters param) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(mContext);
        textView.setTag(param);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textParams.weight = 1;
        textParams.height = 0;
        textView.setLayoutParams(textParams);
        views.put(param.getProECode(), textView);
        textView.setOnClickListener(this);

        textView.setGravity(Gravity.CENTER);
        textView.setText(getParamsValue(param.getProECode()));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setTextColor(mContext.getResources().getColorStateList(R.color.default_text_grey_color));
        textView.setPadding(15, 0, 0, 0);
        linearLayout.addView(textView);

        View view = new View(mContext);
        view.setBackgroundColor(getResources().getColor(R.color.line));
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        linearLayout.addView(view);
        // textView.setBackground(getResources().getDrawable(R.drawable.button_grey_radius));
        return linearLayout;
    }

    private TextView createSelectWidget(Parameters param) {
        TextView textView = new TextView(mContext);
        textView.setOnClickListener(this);
        textView.setFocusable(true);
        textView.setClickable(true);
        //解决 当edittext获取焦点时，需点击两次触发onclick问题
        textView.setFocusableInTouchMode(false);
        textView.setTag(param);
        views.put(param.getProECode(), textView);
        textView.setGravity(Gravity.CENTER);
        textView.setText(param.getOptionText(getParamsValue(param.getProECode())));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setPadding(10, 0, 10, 0);
        textView.setTextColor(mContext.getResources().getColorStateList(R.color.default_text_grey_color));
        textView.setBackground(getResources().getDrawable(R.drawable.button_grey_radius));
        Drawable image = getResources().getDrawable(R.drawable.down);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        textView.setCompoundDrawablePadding(4);
        textView.setCompoundDrawables(image, null, null, null);
        return textView;
    }

    private void createPlanImageParamLayout() {
        TextView tv = new TextView(mContext);
        planFlagMap.put(Constants.CODE_DIM_DOOR_LL_A, tv);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv.setText("");
        int tvleft = plane.getWidth() * 10 / planeSize[0];
        int tvtop = plane.getHeight() * 265 / planeSize[1];
        tv.setPadding(tvleft, tvtop, 0, 0);
        left_layout.addView(tv);

        TextView tv1 = new TextView(mContext);
        planFlagMap.put(Constants.CODE_DIM_CAR_SHELL_WIDTH_BB, tv1);
        tv1.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv1.setText("");
        int tv1left = plane.getWidth() * 285 / planeSize[0];
        int tv1top = plane.getHeight() * 265 / planeSize[1];
        tv1.setPadding(tv1left, tv1top, 0, 0);
        left_layout.addView(tv1);

        TextView tv2 = new TextView(mContext);
        planFlagMap.put(Constants.CODE_DIM_SHAFT_WIDTH_WW, tv2);
        tv2.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv2.setText("");
        int tv2left = plane.getWidth() * 437 / planeSize[0];
        int tv2top = plane.getHeight() * 275 / planeSize[1];
        tv2.setPadding(tv2left, tv2top, 0, 0);
        left_layout.addView(tv2);

        TextView tv3 = new TextView(mContext);
        planFlagMap.put(Constants.CODE_DIM_CAR_SHELL_DEPTH_DD, tv3);
        tv3.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv3.setText("");
        int tv3left = plane.getWidth() * 205 / planeSize[0];
        int tv3top = plane.getHeight() * 425 / planeSize[1];
        tv3.setPadding(tv3left, tv3top, 0, 0);
        left_layout.addView(tv3);

        TextView tv4 = new TextView(mContext);
        planFlagMap.put(Constants.CODE_DIM_SHAFT_DEPTH_WD, tv4);
        tv4.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv4.setText("");
        int tv4left = plane.getWidth() * 210 / planeSize[0];
        int tv4top = plane.getHeight() * 495 / planeSize[1];
        tv4.setPadding(tv4left, tv4top, 0, 0);
        left_layout.addView(tv4);
    }

    private void createSolidImageParamLayout() {
        TextView tv = new TextView(mContext);
        solidFlagMap.put(Constants.CODE_DIM_HEADROOM_SH, tv);
        tv.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv.setText("");
        int tvleft = solid.getWidth() * 30 / solidSize[0];
        int tvtop = solid.getHeight() * 540 / solidSize[1];
        tv.setPadding(tvleft, tvtop, 0, 0);
        right_layout.addView(tv);

        TextView tv1 = new TextView(mContext);
        solidFlagMap.put(Constants.CODE_DIM_CAR_HEIGHT_CH, tv1);
        tv1.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv1.setText("");
        int tv1left = solid.getWidth() * 200 / solidSize[0];
        int tv1top = solid.getHeight() * 644 / solidSize[1];
        tv1.setPadding(tv1left, tv1top, 0, 0);
        right_layout.addView(tv1);

        TextView tv2 = new TextView(mContext);
        solidFlagMap.put(Constants.CODE_DIM_CDO_HH_A, tv2);
        tv2.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv2.setText("");
        int tv2left = solid.getWidth() * 30 / solidSize[0];
        int tv2top = solid.getHeight() * 1002 / solidSize[1];
        tv2.setPadding(tv2left, tv2top, 0, 0);
        right_layout.addView(tv2);

        TextView tv3 = new TextView(mContext);
        solidFlagMap.put(Constants.CODE_DIM_PIT_HEIGHT_PH, tv3);
        tv3.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv3.setText("");
        int tv3left = solid.getWidth() * 30 / solidSize[0];
        int tv3top = solid.getHeight() * 1545 / solidSize[1];
        tv3.setPadding(tv3left, tv3top, 0, 0);
        right_layout.addView(tv3);
    }


    private void createDoorImageParamLayout() {
        int top = door.getTop();
        TextView tv = new TextView(mContext);
        doorFlagMap.put(Constants.CODE_OPH, tv);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv.setText("");
        int tvleft = door.getWidth() * 48 / doorSize[0];
        int tvtop = door.getHeight() * 190 / doorSize[1] + top;
        tv.setPadding(tvleft, tvtop, 0, 0);
        left_layout.addView(tv);

        TextView tv1 = new TextView(mContext);
        doorFlagMap.put(Constants.CODE_OPW, tv1);
        tv1.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv1.setText("");
        int tv1left = door.getWidth() * 170 / doorSize[0];
        int tv1top = door.getHeight() * 435 / doorSize[1] + top;
        tv1.setPadding(tv1left, tv1top, 0, 0);
        left_layout.addView(tv1);

        if (!isHouseElevator()) {
            TextView tv2 = new TextView(mContext);
            //flagTextViews.put(Constants.CODE_DIM_SHAFT_WIDTH_WW, tv2);
            tv2.setTextColor(getResources().getColor(R.color.default_text_black_color));
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            tv2.setText("515");
            int tv2left = door.getWidth() * 272 / doorSize[0];
            int tv2top = door.getHeight() * 90 / doorSize[1] + top;
            tv2.setPadding(tv2left, tv2top, 0, 0);
            left_layout.addView(tv2);
        }

        TextView tv3 = new TextView(mContext);
        //flagTextViews.put(Constants.CODE_DIM_CAR_SHELL_DEPTH_DD, tv3);
        tv3.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv3.setText("1325");
        int tv3left = door.getWidth() * 268 / doorSize[0];
        int tv3top = door.getHeight() * 245 / doorSize[1] + top;
        tv3.setPadding(tv3left, tv3top, 0, 0);
        left_layout.addView(tv3);
    }

    /**
     * @param value1 对重位置值
     * @param value2 贯通门值
     */
    private void switchPlaneImage(String value1, String value2) {
        if ("CB".equals(value1)) {
            plane.setImageResource(R.drawable.back_plane);
        } else if ("B".equals(value1) && "YES".equals(value2)) { //左置贯通门
            plane.setImageResource(R.drawable.left_thr_entr_plane);
        } else if ("B".equals(value1) && "NO".equals(value2)) { //左
            plane.setImageResource(R.drawable.left_plane);
        } else if ("D".equals(value1) && "YES".equals(value2)) { //右置贯通门
            plane.setImageResource(R.drawable.right_thr_entr_plane);
        } else {
            plane.setImageResource(R.drawable.right_plane);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        parameters = (List<Parameters>) getArguments().getSerializable("data");
        createPlanImageParamLayout();
        createSolidImageParamLayout();
        createDoorImageParamLayout();
        createLayout(Constants.BASIC_SUB_PARAMETERS, splitParameters(Constants.BASIC_SUB_PARAMETERS, parameters));
        createLayout(Constants.BASIC_WELL_PARAMETERS, splitParameters(Constants.BASIC_WELL_PARAMETERS, parameters));
        createLayout(Constants.BASIC_ROOM_PARAMETERS, splitParameters(Constants.BASIC_ROOM_PARAMETERS, parameters));
        createLayout(Constants.BASIC_LOCATION_PARAMETERS, splitParameters(Constants.BASIC_LOCATION_PARAMETERS, parameters));
//        baseResponseObservable = HttpFactory.getApiService().getParams(Constants.ELEVATOR, getElevatorType(), getElevatorProduct(), Constants.BASIC_CONSTRUCTION_PARAMETERS, null);
//        baseResponseObservable
//                .compose(RxHelper.<BaseResponse<List<Parameters>>>rxSchedulerHelper())
//                .subscribe(new DefaultObserver<BaseResponse<List<Parameters>>>(getActivity(), true) {
//                    @Override
//                    public void onSuccess(BaseResponse<List<Parameters>> response) {
//
//                    }
//                });
    }

    private List<Parameters> splitParameters(String seatTag, List<Parameters> parameters) {
        List<Parameters> parametersList = new ArrayList<>();
        for (Parameters param : parameters) {
            if (param.getPosition().contains(seatTag)) {
                parametersList.add(param);
            } else if (Constants.BASIC_WELL_PARAMETERS.equals(seatTag) && param.getPosition().contains(Constants.BASIC_CAR_PARAMETERS)) { //特殊处理井道与轿厢信息
                parametersList.add(param);
            }
        }

        return parametersList;
    }


    @Override
    public void onClick(final View view) {
        super.onClick(view);
        final Parameters paramBean = (Parameters) view.getTag();
        switch (paramBean.getDataType()) {
            case Constants.WIDGET_TYPE_INPUT:
                showEditwindow(paramBean, (TextView) view);
                break;
            case Constants.WIDGET_TYPE_SELECT:
                showPopwindow(paramBean, (TextView) view);
                break;

        }
    }

    @OnClick(R.id.basic_area_layout)
    public void basicAreaAction() {
        if (basic_params_layout.getVisibility() == View.GONE) {
            basic_params_layout.setVisibility(View.VISIBLE);
            basic_expand_iv.setImageResource(R.drawable.close);
        } else {
            basic_params_layout.setVisibility(View.GONE);
            basic_expand_iv.setImageResource(R.drawable.open);
        }
    }

    @OnClick(R.id.well_area_layout)
    public void wellAreaAction() {
        if (well_params_layout.getVisibility() == View.GONE) {
            well_params_layout.setVisibility(View.VISIBLE);
            well_expand_iv.setImageResource(R.drawable.close);
        } else {
            well_params_layout.setVisibility(View.GONE);
            well_expand_iv.setImageResource(R.drawable.open);
        }
    }

    @OnClick(R.id.room_area_layout)
    public void roomAreaAction() {
        if (room_params_layout.getVisibility() == View.GONE) {
            room_params_layout.setVisibility(View.VISIBLE);
            room_expand_iv.setImageResource(R.drawable.close);
        } else {
            room_params_layout.setVisibility(View.GONE);
            room_expand_iv.setImageResource(R.drawable.open);
        }
    }

    @OnClick(R.id.location_area_layout)
    public void locationAreaAction() {
        if (location_params_layout.getVisibility() == View.GONE) {
            location_params_layout.setVisibility(View.VISIBLE);
            location_expand_iv.setImageResource(R.drawable.close);
        } else {
            location_params_layout.setVisibility(View.GONE);
            location_expand_iv.setImageResource(R.drawable.open);
        }
    }

    //更新底部标注参数值
    private void updateBottomParam(String proECode, String value) {
        if (planFlagMap.get(proECode) != null) {
            planFlagMap.get(proECode).setText(value);
        }
        if (solidFlagMap.get(proECode) != null) {
            solidFlagMap.get(proECode).setText(value);
        }
        if (doorFlagMap.get(proECode) != null) {
            if (Constants.CODE_OPH.equals(proECode) && Util.isNumeric(value)) { //+100
                value = String.valueOf((Integer.valueOf(value) + 100));
            } else if (Constants.CODE_OPW.equals(proECode) && Util.isNumeric(value)) { // +200
                value = String.valueOf((Integer.valueOf(value) + 200));
            }
            doorFlagMap.get(proECode).setText(value);
        }
        if (Constants.CODE_POS_OF_CWT.equals(proECode) || Constants.CODE_THR_ENTR.equals(proECode)) {
            switchPlaneImage(getParamsValue(Constants.CODE_POS_OF_CWT), getParamsValue(Constants.CODE_THR_ENTR));
            TextView textView = planFlagMap.get(Constants.CODE_DIM_CAR_SHELL_DEPTH_DD);
            if (textView != null) {
                if ("D".equals(value)) { //右置
                    int tv3left = plane.getWidth() * 205 / planeSize[0];
                    int tv3top = plane.getHeight() * 106 / planeSize[1];
                    textView.setPadding(tv3left, tv3top, 0, 0);
                } else {
                    int tv3left = plane.getWidth() * 205 / planeSize[0];
                    int tv3top = plane.getHeight() * 425 / planeSize[1];
                    textView.setPadding(tv3left, tv3top, 0, 0);
                }
            }
        }
    }

    private void showPopwindow(final Parameters paramsBean, final TextView view) {
        if (paramsBean.getOption().size() == 0) {
            return;
        }
        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        view.setCompoundDrawables(image, null, null, null);
        DropDownWindow mWindow = new DropDownWindow(mContext, view, paramsBean.getOption(), view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {
                view.setText(paramsBean.getOption().get(p).getText());
                updateBottomParam(paramsBean.getProECode(), paramsBean.getOption().get(p).getValue());
                putParams(paramsBean.getProECode(), paramsBean.getOption().get(p).getValue());
                updateFloorStation(paramsBean.getProECode());
                scriptHandle(paramsBean);

                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                view.setCompoundDrawables(image, null, null, null);
            }
        };
    }


    private void showEditwindow(final Parameters paramsBean, final TextView view) {
        String title = paramsBean.getName();
        String textHint = getParamsValue(paramsBean.getProECode());

        EditDialog.newInstance().setBuider(mContext, title, textHint, InputType.TYPE_CLASS_NUMBER, new EditDialog.DialogConfirmCallBack() {
            @Override
            public void confirm(String value) {
                if (TextUtils.isEmpty(value)) {
                    return;
                }
                view.setText(value);
                updateBottomParam(paramsBean.getProECode(), value);
                putParams(paramsBean.getProECode(), value);
                updateFloorStation(paramsBean.getProECode());
                scriptHandle(paramsBean);
            }
        }).show();
    }

    //联动层站参数fragment 楼层数 顶层高度  底坑深度 贯通门
    private void updateFloorStation(String pCode) {
        if (Constants.CODE_QTY_NUMBER_OF_FLOORS.equals(pCode) || Constants.CODE_DIM_HEADROOM_SH.equals(pCode)
                || Constants.CODE_DIM_PIT_HEIGHT_PH.equals(pCode) || Constants.CODE_TH.equals(pCode)
                || Constants.CODE_THR_ENTR.equals(pCode)) {
            EventBus.getDefault().post(new EventFloorStation());
        }
    }


    @Override
    public void scriptReturnParam(List<ScriptReturnParam> returnParams) {
        if (returnParams == null) {
            return;
        }

        for (int i = 0; i < returnParams.size(); i++) {

            ScriptReturnParam returnParam = returnParams.get(i);
            if (views.get(returnParam.getParamCode()) == null) continue;
            if (DISABLE == returnParam.getOperation()) { //设置对应元素禁用
                views.get(returnParam.getParamCode()).setEnabled(false);
            } else if (ENABLE == returnParam.getOperation()) { //设置对应元素可用
                views.get(returnParam.getParamCode()).setEnabled(true);
            } else if (SET == returnParam.getOperation()) { //改变值（不是文本 ）
                Parameters parameters = (Parameters) views.get(returnParam.getParamCode()).getTag();
                ((TextView) views.get(returnParam.getParamCode())).setText(parameters.getOptionText(returnParam.getFirstParamValue()));
                updateBottomParam(returnParam.getParamCode(), returnParam.getFirstParamValue());
                updateFloorStation(returnParam.getParamCode());
            } else if (RESET == returnParam.getOperation()) {  //重构下拉选项
                Parameters parameters = (Parameters) views.get(returnParam.getParamCode()).getTag();
                parameters.getOption().clear();
                parameters.getOption().addAll(returnParam.getResetOptions());

            }
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void nodifyStandParam(EventStandardParam event) {
        if (event != null) {
            for (Map.Entry<String, String> entry : event.getParam().entrySet()) {
                TextView textView = (TextView) views.get(entry.getKey());
                if (textView != null && textView.getTag() != null) {
                    Parameters parameters = (Parameters) textView.getTag();
                    textView.setText(parameters.getOptionText(entry.getValue()));
                    updateBottomParam(parameters.getProECode(),entry.getValue());
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
