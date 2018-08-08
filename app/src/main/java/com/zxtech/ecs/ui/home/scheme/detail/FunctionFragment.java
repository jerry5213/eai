package com.zxtech.ecs.ui.home.scheme.detail;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.DropDownBean;
import com.zxtech.ecs.model.FunctionHead;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.zxtech.ecs.adapter.ExpandableItemAdapter.TYPE_LEVEL_0;
import static com.zxtech.ecs.model.ScriptReturnParam.DISABLE;
import static com.zxtech.ecs.model.ScriptReturnParam.ENABLE;
import static com.zxtech.ecs.model.ScriptReturnParam.RESET;
import static com.zxtech.ecs.model.ScriptReturnParam.SET;
import static com.zxtech.ecs.ui.home.scheme.detail.FunctionFragment.FunctionExpandableAdapter.TYPE_LEVEL_1;

/**
 * 功能参数
 * Created by syp523 on 2018/1/22.
 */

public class FunctionFragment extends BaseSchemeDetailFragment implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.func_rv)
    RecyclerView func_rv;

    //选配参数列表
    private List<MultiItemEntity> datas = new ArrayList<>();

    private FunctionExpandableAdapter expandableAdapter;

    public static FunctionFragment newInstance(List<Parameters> parameters) {
        Bundle args = new Bundle();
        FunctionFragment fragment = new FunctionFragment();
        args.putSerializable("data", (Serializable) parameters);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_function;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        List<Parameters> parameters = (List<Parameters>) getArguments().getSerializable("data");
        for (int i = 0; i < parameters.size(); i++) {
            Parameters pms = parameters.get(i);
            if (!TextUtils.isEmpty(getParamsValue(pms.getProECode())))
                pms.setSelected(true);
        }
        datas = generateData(parameters);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        func_rv.setLayoutManager(layoutManager);
        expandableAdapter = new FunctionExpandableAdapter(datas);
        expandableAdapter.setOnItemClickListener(FunctionFragment.this);
        // func_rv.addItemDecoration(new MyItemDecoration(1));
        func_rv.setAdapter(expandableAdapter);
        //展开第二组
        expandableAdapter.expand(1);
//        baseResponseObservable = HttpFactory.getApiService().getParams(typeId, getElevatorType(), getElevatorProduct(), getArguments().getString("areaTag"), null);
//        baseResponseObservable
//                .compose(RxHelper.<BaseResponse<List<Parameters>>>rxSchedulerHelper())
//                .subscribe(new DefaultObserver<BaseResponse<List<Parameters>>>(getActivity(), true) {
//                    @Override
//                    public void onSuccess(BaseResponse<List<Parameters>> response) {
//
//                    }
//                });
    }


    private ArrayList<MultiItemEntity> generateData(List<Parameters> parameters) {
        int seatCount = 2;
        String[] headTitles = new String[]{getString(R.string.standard_functions), getString(R.string.optional_parameters)};

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < seatCount; i++) {
            FunctionHead functionHead = new FunctionHead(headTitles[i], i == 0 ? true : false);
            for (int j = 0; j < parameters.size(); j++) {
                Parameters paramter = parameters.get(j);
                if (isPositionType(i, paramter.getPosition())) { //标配
                    paramter.setStandard(i == 0 ? true : false);
                    functionHead.addSubItem(paramter);
                }
            }
            res.add(functionHead);
        }
        return res;
    }

    //type 0标配 1选配
    private boolean isPositionType(int type, String position) {
        boolean isType = false;
        if (position == null) return isType;
      //  String areaTag = getArguments().getString("areaTag");
       // if (Constants.FUNCTION_PARAMETERS.equals(areaTag)) { //直梯
            if (type == 0 && (position.contains(Constants.FUNCTION_PARAMETERS_STANDARD) || position.contains(Constants.ESC_FUNCTION_PARAMETERSD1))) {
                isType = true;
            } else if (type == 1 && (position.contains(Constants.FUNCTION_PARAMETERS_MATCH_A) || position.contains(Constants.FUNCTION_PARAMETERS_MATCH_B) || position.contains(Constants.ESC_FUNCTION_PARAMETERSA1) || position.contains(Constants.ESC_FUNCTION_PARAMETERSB1) || position.contains(Constants.ESC_FUNCTION_PARAMETERSC1))) {
                isType = true;
            }

//        } else if (Constants.ESC_FUNCTION_PARAMETERS.equals(areaTag)) { //扶梯
//            if (type == 0 && position.contains(Constants.ESC_FUNCTION_PARAMETERSD1)) {
//                isType = true;
//            } else if (type == 1 && (position.contains(Constants.ESC_FUNCTION_PARAMETERSA1) || position.contains(Constants.ESC_FUNCTION_PARAMETERSB1) || position.contains(Constants.ESC_FUNCTION_PARAMETERSC1))) {
//                isType = true;
//            }
//        }

        return isType;
    }




    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (adapter.getItemViewType(position)) {
            case TYPE_LEVEL_0:
                FunctionHead parent = (FunctionHead) adapter.getItem(position);
                if (parent.isExpanded()) {
                    //合并
                    adapter.collapse(position, false);
                    parent.setExpanded(false);

                } else {
                    //展开
                    adapter.expand(position, false);
                    parent.setExpanded(true);

                }
                break;

        }
    }

    @Override
    public void scriptReturnParam(List<ScriptReturnParam> returnParams) {
        if (returnParams == null) {
            return;
        }
        //是否当前界面参数
        for (int j = 0; j < returnParams.size(); j++) {
            ScriptReturnParam returnParam = returnParams.get(j);
            for (int i = 0; i < expandableAdapter.getData().size(); i++) {
                MultiItemEntity multiItemEntity = expandableAdapter.getData().get(i);
                int itemViewType = expandableAdapter.getItemViewType(i);
                if (itemViewType == TYPE_LEVEL_1) {
                    Parameters parameters = (Parameters) multiItemEntity;
                    parameters.setReturnOperation(returnParam.getOperation());
                    if (returnParam.getParamCode() != null && returnParam.getParamCode().equals(parameters.getProECode())) {
                        if (SET == returnParam.getOperation()) { //联动重新赋值
                            parameters.setDefaultValue(returnParam.getFirstParamValue());
                            putParams(returnParam.getParamCode(), returnParam.getFirstParamValue());
                        } else if (RESET == returnParam.getOperation()) { //重构下拉框
                            parameters.getOption().clear();
                            parameters.getOption().addAll(returnParam.getResetOptions());
                        }
                        expandableAdapter.notifyDataSetChanged();
                    }
                }
            }
        }


    }


    class FunctionExpandableAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
        private final String TAG = FunctionExpandableAdapter.class.getSimpleName();

        public static final int TYPE_LEVEL_0 = 0;
        public static final int TYPE_LEVEL_1 = 1;

        // private List<Integer> matchList = new ArrayList<>();

        /**
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public FunctionExpandableAdapter(List<MultiItemEntity> data) {
            super(data);
            addItemType(TYPE_LEVEL_0, R.layout.item_function_title);
            addItemType(TYPE_LEVEL_1, R.layout.item_function_match);
        }


        @Override
        protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
            switch (holder.getItemViewType()) {
                case TYPE_LEVEL_0:
                    final FunctionHead lv0 = (FunctionHead) item;
                    holder.setText(R.id.title_tv, lv0.getTitle());
                    holder.setVisible(R.id.remark_tv, lv0.isShowRemark());
                    if (lv0.isExpanded()) {
                        holder.setImageResource(R.id.expand_iv, R.drawable.close);
                    } else {
                        holder.setImageResource(R.id.expand_iv, R.drawable.open);
                    }
                    break;
                case TYPE_LEVEL_1:
                    final Parameters lv1 = (Parameters) item;
                    holder.setText(R.id.desc_tv, lv1.getName());
//                    if (lv1.getPosition().contains(Constants.FUNCTION_PARAMETERS_STANDARD) || lv1.getPosition().contains(Constants.ESC_FUNCTION_PARAMETERSD1)) {
//                        holder.setImageResource(R.id.config_iv, R.drawable.standard);
//                        holder.setVisible(R.id.right_layout, false);
//                        holder.setVisible(R.id.config_iv, true);
//                    } else {
//                        holder.setVisible(R.id.config_iv, false);
//                        holder.setVisible(R.id.right_layout, true);
//                    }
                    holder.setText(R.id.value_tv, lv1.getOptionText(getParamsValue(lv1.getProECode())));
                    if (lv1.isStandard()) {
                        holder.setVisible(R.id.horizontal_line, true);
                        holder.setVisible(R.id.value_tv, false);
                    } else {
                        holder.setVisible(R.id.value_tv, true);
                        holder.setVisible(R.id.horizontal_line, false);
                    }

                    ImageView plus_btn = holder.getView(R.id.plus_btn);
                    ImageView minus_btn = holder.getView(R.id.minus_btn);
                    final TextView value_tv = holder.getView(R.id.value_tv);


                    if ("select".equals(lv1.getDataType())) {
                        plus_btn.setVisibility(View.GONE);
                        minus_btn.setVisibility(View.GONE);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                        layoutParams.setMargins(0, 25, 0, 25);
                        value_tv.setLayoutParams(layoutParams);
                        value_tv.setBackground(getResources().getDrawable(R.drawable.button_grey_radius));
                        value_tv.setPadding(10, 0, 0, 0);
                        Drawable image = getResources().getDrawable(R.drawable.down);
                        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                        value_tv.setCompoundDrawablePadding(4);
                        value_tv.setCompoundDrawables(image, null, null, null);
                    } else {
                        value_tv.setBackground(getResources().getDrawable(R.drawable.button_grey_radius));
                        value_tv.setCompoundDrawables(null, null, null, null);
                        plus_btn.setVisibility(View.VISIBLE);
                        minus_btn.setVisibility(View.VISIBLE);
                    }

                    plus_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (lv1.getReturnOperation() == DISABLE) {
                                ToastUtil.showLong(getString(R.string.msg36));
                                return;
                            }
                            String value = getParamsValue(lv1.getProECode());
                            if (!Util.isNumeric(value)) {
                                return;
                            }
                            String afterValue = String.valueOf(Integer.parseInt(value) + 1);
                            lv1.setDefaultValue(afterValue);
                            putParams(lv1.getProECode(), afterValue);
                            value_tv.setText(afterValue);
                        }
                    });
                    minus_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (lv1.getReturnOperation() == DISABLE) {
                                ToastUtil.showLong(getString(R.string.msg36));
                                return;
                            }
                            String value = getParamsValue(lv1.getProECode());
                            if (!Util.isNumeric(value)) {
                                return;
                            }
                            if (Integer.parseInt(value) > 0) {
                                String afterValue = String.valueOf(Integer.parseInt(value) - 1);
                                putParams(lv1.getProECode(), afterValue);
                                lv1.setDefaultValue(afterValue);
                                value_tv.setText(afterValue);
                            }
                        }
                    });

                    value_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (lv1.isStandard()) {
                                return;
                            }
                            if (lv1.getReturnOperation() == DISABLE) {
                                ToastUtil.showLong(getString(R.string.msg36));
                                return;
                            }

                            if ("input".equals(lv1.getDataType())) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                                LayoutInflater inflater = LayoutInflater.from(mContext);
                                final View dialog_edit = inflater.inflate(R.layout.dialog_edit, null);
                                builder.setView(dialog_edit).setTitle(lv1.getName())
                                        .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                EditText et_userName = (EditText) dialog_edit.findViewById(R.id.content_et);
                                                putParams(lv1.getProECode(), et_userName.getText().toString());
                                                value_tv.setText(et_userName.getText().toString());
                                                scriptHandle(lv1);//联动
                                            }
                                        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                            } else if ("select".equals(lv1.getDataType())) {
                                final List<DropDownBean> fitterDropBean = new ArrayList<>();
                                for (Parameters.Option op : lv1.getOption()) {
                                    if (op.getText().equals(value_tv.getText().toString()))
                                        continue;
                                    fitterDropBean.add(new DropDownBean(op.getValue(), op.getText()));
                                }
                                if (fitterDropBean.size() == 0) {
                                    return;
                                }
                                new DropDownWindow(mContext, v, fitterDropBean, v.getWidth(), -2) {

                                    @Override
                                    public void initEvents(final int p) {
                                        value_tv.setText(lv1.getOptionText(fitterDropBean.get(p).getValue()));
                                        putParams(lv1.getProECode(), fitterDropBean.get(p).getValue());
                                        lv1.setDefaultValue(fitterDropBean.get(p).getKey());
                                        scriptHandle(lv1);//联动
                                        this.dismiss();
                                    }
                                };
                            }
                        }
                    });
                    break;
            }
        }


    }
}