package com.zxtech.ecs.ui.home.scheme.detail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.PlaceAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by syp523 on 2018/2/1.
 */

public abstract class PlaceDialog extends Dialog {

    private Context context;

    private Observable baseResponseObservable = null;
    private String parentCode;

    private RecyclerView recycleView;
    private List<Parameters.Option> options = new ArrayList<>();
    private PlaceAdapter placeAdapter = null;
    private Parameters currentParameter;
    private Map<String, String> allParams;
    private Map<String, ScriptReturnParam> scriptReturnParamMap;

    public PlaceDialog(@NonNull Context context, String code, Map<String, String> allParams, Map<String, ScriptReturnParam> scriptReturnParamMap) {
        super(context);
        this.context = context;
        this.parentCode = code;
        this.allParams = allParams;
        this.scriptReturnParamMap = scriptReturnParamMap;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_place);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        initDialogSize();
        initView();

    }

    private void initView() {
        recycleView = findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(layoutManager);
        placeAdapter = new PlaceAdapter(R.layout.item_place_image, options);
        recycleView.setAdapter(placeAdapter);
        initData();
        placeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (currentParameter != null) {
                    if (placeAdapter.getSelectedOptionValue() != null && placeAdapter.getSelectedOptionValue().equals(options.get(position).getValue())) {
                        ToastUtil.showLong(context.getString(R.string.msg2));
                        return;
                    }
                    Parameters.Option option = options.get(position);
                    placeAdapter.setSelectedOptionValue(option.getValue());
                    placeAdapter.notifyDataSetChanged();
                    selected(currentParameter.getProECode(), option.getValue());
                }

            }
        });
    }

    public abstract void selected(String code, String value);

    private void initData() {
        baseResponseObservable = HttpFactory.getApiService().getParams(Constants.ELEVATOR, allParams.get("ElevatorType"), allParams.get("ElevatorProduct"), Constants.DECORATION_PARAMETERS, parentCode);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<Parameters>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Parameters>>>((Activity) context, true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Parameters>> response) {
                        if (response.getData().size() > 0) {
                            currentParameter = response.getData().get(0);
                            if (scriptReturnParamMap.get(currentParameter.getProECode()) != null) {
                                ScriptReturnParam scriptReturnParam = scriptReturnParamMap.get(currentParameter.getProECode());
                                if (scriptReturnParam.getOperation() == ScriptReturnParam.RESET) { //重构
                                    currentParameter.getOption().clear();
                                    currentParameter.getOption().addAll(scriptReturnParam.getResetOptions());
                                }
                            }
                            if (currentParameter.getOption().size() > 0) {
                                options.addAll(currentParameter.getOption());
                                placeAdapter.setSelectedOptionValue(allParams.get(currentParameter.getProECode()));
                                placeAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtil.showLong(context.getString(R.string.msg8));
                            }
                        }

                    }
                });

    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (baseResponseObservable != null) {
            baseResponseObservable.unsubscribeOn(Schedulers.io());
        }
        selected(null, null);
    }


    private void initDialogSize() {
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值

        p.height = DensityUtil.dip2px(context, 190);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.85);    //宽度设置为屏幕的0.9
        getWindow().setAttributes(p);     //设置生效
        getWindow().setGravity(Gravity.BOTTOM);
    }
}
