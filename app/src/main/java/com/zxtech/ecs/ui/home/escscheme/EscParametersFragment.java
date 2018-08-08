package com.zxtech.ecs.ui.home.escscheme;

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
import com.zxtech.ecs.model.DropDownBean;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.scheme.detail.BaseSchemeDetailFragment;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.StringUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.EditDialog;

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
 * 扶梯土建参数
 * Created by syp523 on 2018/1/22.
 */

public class EscParametersFragment extends BaseSchemeDetailFragment {
    @BindView(R.id.basic_params_layout)
    LinearLayout basic_params_layout;
    @BindView(R.id.well_params_layout)
    LinearLayout well_params_layout;
    @BindView(R.id.image_layout)
    RelativeLayout image_layout;
    @BindView(R.id.basic_expand_iv)
    ImageView basic_expand_iv;
    @BindView(R.id.well_expand_iv)
    ImageView well_expand_iv;
    @BindView(R.id.escalator_iv)
    ImageView escalator_iv;
    @BindView(R.id.price_tv)
    TextView price_tv;

    private int textMaxWidth = 75;
    private int textSize = 11;

    private List<Parameters> parameters = new ArrayList<>();

    private Map<String, View> views = new HashMap<>();
    //底部图对应的参数
    private Map<String, TextView> flagTextViews = new HashMap<>();

    private int[] escalatorSize = new int[]{958, 557};

    public static EscParametersFragment newInstance(List<Parameters> parameters) {
        Bundle args = new Bundle();
        EscParametersFragment fragment = new EscParametersFragment();
        args.putSerializable("data", (Serializable) parameters);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_esc_parameters;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        String price = getParamsValue("Price");
        price_tv.setText(getString(R.string.esc_price) + (Util.isNumeric(price) ? Util.numberFormat(Integer.valueOf(price)) : "0"));
    }

    private void createLayout(String seatTag, List<Parameters> parameters) {
        if (parameters.size() % 2 == 1) { //补全奇数情况
            parameters.add(new Parameters(""));
        }
        LinearLayout linearLayout = null;
        for (int i = 0; i < parameters.size(); i++) {
            Parameters parameter = parameters.get(i);

            if (flagTextViews.get(parameter.getProECode()) != null) {
                flagTextViews.get(parameter.getProECode()).setText(getParamsValue(parameter.getProECode()));
            }
            if (i % 2 == 0) {
                linearLayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext, 32));
                layoutParams.setMargins(0, 15, 0, 15);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                if (Constants.ESC_BASIC_INFO.equals(seatTag)) {
                    basic_params_layout.addView(linearLayout);
                } else if (Constants.ESC_BASIC_PARAMETERS.equals(seatTag)) {
                    well_params_layout.addView(linearLayout);
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


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        createEscImageParamLayout();
        this.parameters = (List<Parameters>) getArguments().getSerializable("data");
        createLayout(Constants.ESC_BASIC_INFO, splitParameters(Constants.ESC_BASIC_INFO, parameters));
        createLayout(Constants.ESC_BASIC_PARAMETERS, splitParameters(Constants.ESC_BASIC_PARAMETERS, parameters));
    }

    private List<Parameters> splitParameters(String seatTag, List<Parameters> parameters) {
        List<Parameters> parametersList = new ArrayList<>();
        for (Parameters param : parameters) {
            if (param.getPosition().contains(seatTag)) {
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

    @OnClick(R.id.calculate_tv)
    public void calculatePrice() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(getAllParams());
        map.put("UserEmail", getUserEmail());
        map.put("UserGuid", getUserId());
        baseResponseObservable = HttpFactory.getApiService().calculateEscPrice(map);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        String replaceStr = response.getData().replace("\"", "");
                        price_tv.setText(getString(R.string.esc_price) + (Util.isNumeric(replaceStr) ? Util.numberFormat(Integer.valueOf(replaceStr)) : "0"));
                    }
                });
    }


    //更新底部标注参数值
    private void updateBottomParam(String proECode, String value) {
        TextView textView = flagTextViews.get(proECode);
        if (textView != null) {
            textView.setText(value);
        }
    }

    private void showPopwindow(final Parameters paramsBean, final TextView view) {
        final List<Parameters.Option> options = paramsBean.getOption();
        if (options == null || options.size() == 0) {
            return;
        }
        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        view.setCompoundDrawables(image, null, null, null);
        DropDownWindow mWindow = new DropDownWindow(mContext, view, options, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {
                view.setText(options.get(p).getText());
                updateBottomParam(paramsBean.getProECode(), options.get(p).getValue());
                putParams(paramsBean.getProECode(), options.get(p).getValue());
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
                scriptHandle(paramsBean);
            }
        }).show();
    }

    private void createEscImageParamLayout() {
        int top = escalator_iv.getTop();
        TextView tv = new TextView(mContext);
        flagTextViews.put(Constants.CODE_ES_BBS, tv);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv.setTextColor(getResources().getColor(R.color.default_text_black_color));
        int tvleft = escalator_iv.getWidth() * 220 / escalatorSize[0];
        int tvtop = escalator_iv.getHeight() * 32 / escalatorSize[1] + top;
        tv.setPadding(tvleft, tvtop, 0, 0);
        image_layout.addView(tv);

        TextView tv1 = new TextView(mContext);
        flagTextViews.put(Constants.CODE_ES_TBS, tv1);
        tv1.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        int tv1left = escalator_iv.getWidth() * 660 / escalatorSize[0];
        int tv1top = escalator_iv.getHeight() * 32 / escalatorSize[1] + top;
        tv1.setPadding(tv1left, tv1top, 0, 0);
        image_layout.addView(tv1);

        TextView tv2 = new TextView(mContext);
        flagTextViews.put(Constants.CODE_ES_TH, tv2);
        tv2.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        int tv2left = escalator_iv.getWidth() * 800 / escalatorSize[0];
        int tv2top = escalator_iv.getHeight() * 180 / escalatorSize[1] + top;
        tv2.setPadding(tv2left, tv2top, 0, 0);
        image_layout.addView(tv2);

        TextView tv3 = new TextView(mContext);
        flagTextViews.put(Constants.CODE_ES_HD, tv3);
        tv3.setTextColor(getResources().getColor(R.color.default_text_black_color));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        int tv3left = escalator_iv.getWidth() * 430 / escalatorSize[0];
        int tv3top = escalator_iv.getHeight() * 475 / escalatorSize[1] + top;
        tv3.setPadding(tv3left, tv3top, 0, 0);
        image_layout.addView(tv3);
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
            } else if (RESET == returnParam.getOperation()) {  //重构下拉选项
                Parameters parameters = (Parameters) views.get(returnParam.getParamCode()).getTag();
                parameters.getOption().clear();
                parameters.getOption().addAll(returnParam.getResetOptions());

            }
        }
    }
}
