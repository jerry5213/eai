package com.zxtech.ecs.ui.home.quote;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Collection;
import com.zxtech.ecs.model.DropDown;
import com.zxtech.ecs.model.NewProductDropDown;
import com.zxtech.ecs.model.ProductInfo;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.DropDownWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chw on 2018/06/21.
 */

public class NewProductActivity extends BaseActivity implements CollectionSelectionDialogFragment.CollectionSelectionDialogFragmentCallBack {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.param1_tv)
    TextView param1_tv;//载重下拉列表
    @BindView(R.id.param2_tv)
    TextView param2_tv;//速度下拉列表
    @BindView(R.id.product_tv)
    TextView product_tv;//产品名称
    @BindView(R.id.model_tv)
    TextView model_tv;//产品型号
    @BindView(R.id.delivery_date_tv)
    TextView delivery_date_tv;//预计交货期
    @BindView(R.id.tv_number)
    TextView tv_number;//批次数量
    @BindView(R.id.et_begin_num)
    EditText et_begin_num;//批次数量
    @BindView(R.id.tv_pl)
    EditText tv_pl;//批次
    @BindView(R.id.guarantee_date_et)
    EditText guarantee_date_et;//保质期
    @BindView(R.id.freeinsurance_date_et)
    EditText freeinsurance_date_et;//保质期
    @BindView(R.id.param1_title)
    TextView param1_title;
    @BindView(R.id.param2_title)
    TextView param2_title;


    private String projectGuid;
    private String projectNo;
    private String programmeGuid;
    private String type;
    private ProductInfo productInfo;
    private List<DropDown> param1List = new ArrayList<>();
    private List<DropDown> param2List = new ArrayList<>();
    private List<String> productList = new ArrayList<>();
    private List<String> typeList = new ArrayList<>();
    private List<DropDown> deliveryList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_product;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.new_product));
        Intent intent = getIntent();
        projectGuid = intent.getStringExtra("projectGuid");
        projectNo = intent.getStringExtra("projectNo");
        type = intent.getStringExtra("elevatorType");
        productInfo = (ProductInfo) intent.getSerializableExtra("productInfo");

        if (Constants.ELEVATOR.equals(type)) {
            param1_title.setText(getString(R.string.load_capacity$));
            param2_title.setText(getString(R.string.speed$));
        } else {
            param1_title.setText(getString(R.string.pedal_width));
            param2_title.setText(getString(R.string.angle_of_tilt));
        }

        if (productInfo != null) {
            guarantee_date_et.setText(productInfo.getGuaranteeDate());
            freeinsurance_date_et.setText(productInfo.getFreeInsuranceDate());
            product_tv.setText(productInfo.getElevatorProduct());
            model_tv.setText(productInfo.getElevatorType());
            //区分直梯扶梯
            param1_tv.setText(Constants.ELEVATOR.equals(type) ? productInfo.getElevatorLoad() : productInfo.getRungsWidth());
            param1_tv.setTag(Constants.ELEVATOR.equals(type) ? productInfo.getElevatorLoad() : productInfo.getRungsWidth());
            param2_tv.setText(Constants.ELEVATOR.equals(type) ? productInfo.getSpeed() : productInfo.getAngle());
            param2_tv.setTag(Constants.ELEVATOR.equals(type) ? productInfo.getSpeed() : productInfo.getAngle());
        }
        initDropDown();
    }

    @OnClick({R.id.param1_tv, R.id.param2_tv, R.id.product_tv, R.id.model_tv, R.id.delivery_date_tv, R.id.tv_ok, R.id.tv_make, R.id.clear_tv, R.id.selection_collection_tv})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.param1_tv://额定载重
                dropDown(view, param1List);
                break;
            case R.id.param2_tv://额定速度
                dropDown(view, param2List);
                break;
            case R.id.product_tv://产品名称
                dropDown2(view, productList);
                break;
            case R.id.model_tv://产品型号
                dropDown2(view, typeList);
                break;
            case R.id.delivery_date_tv://预计交货期
                dropDown(view, deliveryList);
                break;
            case R.id.tv_ok:
                submit();
                break;
            case R.id.tv_make:
                makeL();
                break;
            case R.id.clear_tv:
                clearL();
                break;
            case R.id.selection_collection_tv:
                initCollectionSelectionData();

                break;
        }
    }

    private void initCollectionSelectionData() {
        baseResponseObservable = HttpFactory.getApiService().getCollectionList(getUserId(), type);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<Collection>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Collection>>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Collection>> response) {
                        CollectionSelectionDialogFragment dialogFragment = CollectionSelectionDialogFragment.newInstance();
                        dialogFragment.setData(response.getData());
                        dialogFragment.callBack = NewProductActivity.this;
                        dialogFragment.setType(type);
                        dialogFragment.show(getFragmentManager(), "selection");
                    }
                });
    }

    public void initDropDown() {

        baseResponseObservable = HttpFactory.getApiService().getProductDropDownList(type);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<NewProductDropDown>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<NewProductDropDown>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<NewProductDropDown> response) {
                        NewProductDropDown data = response.getData();
                        productList.addAll(data.getElevatorProductList());
                        deliveryList.addAll(data.getDeliveryDateList());
                        if (productList.size() > 0 ) { //新建赋值
                            if (productInfo == null) {
                                product_tv.setText(productList.get(0));
                            }
                            getProductType(productList.get(0));
                        }

                        if (deliveryList.size() > 0 && productInfo != null) {
                            for (int i = 0; i < deliveryList.size(); i++) {
                                if (productInfo.getDeliveryDate().equals(deliveryList.get(i).getValue())) {
                                    delivery_date_tv.setText(deliveryList.get(i).getText());
                                    delivery_date_tv.setTag(deliveryList.get(i).getValue());
                                    break;
                                }
                            }
                        }
                    }
                });

    }

    private void clearL() {
        tv_pl.setText(null);
    }

    public void makeL() {

        int a = 1;
        int b = 1;
        String str1 = tv_number.getText().toString();
        String str2 = et_begin_num.getText().toString();
        if (!TextUtils.isEmpty(str1)) {
            a = Integer.parseInt(str1);
        }
        if (!TextUtils.isEmpty(str2)) {
            b = Integer.parseInt(str2);
        }

        String PL = "";
        for (int i = 0; i < a; i++) {
            PL += "L" + (b + i) + ((a - 1) == i ? "" : ",");
        }
        tv_pl.setText(PL);
    }

    public void submit() {
        if (delivery_date_tv.getTag() == null || delivery_date_tv.getTag().toString().equals("请选择")) {
            ToastUtil.showLong(getString(R.string.toast5));
            return;
        }

        if (TextUtils.isEmpty(tv_pl.getText())) {
            ToastUtil.showLong(getString(R.string.toast6));
            return;
        }

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("Guid", productInfo == null?"":productInfo.getEQS_Guid());
        paramsMap.put(Constants.CODE_ELEVATORPRODUCT, product_tv.getText().toString());
        paramsMap.put(Constants.CODE_ELEVATORTYPE, model_tv.getText().toString());
        paramsMap.put("ProjectGuid", projectGuid);
        paramsMap.put("ProjectNo", projectNo);
        if (Constants.ELEVATOR.equals(type)) {
            paramsMap.put("ElevatorLoad", param1_tv.getTag() == null ? "" : param1_tv.getTag().toString());
            paramsMap.put("Speed", param2_tv.getTag() == null ? "" : param2_tv.getTag().toString());
        } else {
            paramsMap.put("HiddenRungsWidth", param1_tv.getTag() == null ? "" : param1_tv.getTag().toString());//踏板宽度（扶梯）
            paramsMap.put("HiddenAngle", param2_tv.getTag() == null ? "" : param2_tv.getTag().toString());//倾斜角度（扶梯）
        }
        paramsMap.put("DeliveryDate", delivery_date_tv.getTag().toString());//预计交货期
        paramsMap.put("GuaranteeDate", guarantee_date_et.getText().toString());//质保期
        paramsMap.put("FreeInsuranceDate", freeinsurance_date_et.getText().toString());//免保期
        paramsMap.put("ElevatorNo", tv_pl.getText().toString());//批次数量，如L1,L2,L3
        paramsMap.put("ElevatorCount", tv_number.getText().toString());//批次数量
        paramsMap.put("UserId", getUserId());
        paramsMap.put("State", "0");//0代表普通任务，3代表变更任务
        paramsMap.put("ProgrammeGuid", programmeGuid == null ? "" : programmeGuid);

        String json = new Gson().toJson(paramsMap);
        baseResponseObservable = HttpFactory.getApiService().saveChildProduct(json);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (response.getData() != null) {
                            ToastUtil.showLong(response.getData().toString());
                        } else {
                            setResult(1010);
                            finish();
                        }
                    }
                });
    }

    public void dropDown(View view, final List<DropDown> list) {

        if (list == null) {
            return;
        }

        final TextView textView = (TextView) view;
        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        textView.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, list, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                String sub = textView.getText().toString();
                String selectText = list.get(p).getText();
                if (sub.equals(selectText)) {
                    return;
                }
                textView.setText(selectText);
                textView.setTag(list.get(p).getValue());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                textView.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    public void dropDown2(final View view, final List<String> list) {

        if (list == null) {
            return;
        }

        final TextView textView = (TextView) view;
        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        textView.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, list, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                String sub = textView.getText().toString();
                String selectValue = list.get(p);
                if (sub.equals(selectValue)) {
                    return;
                }

                textView.setText(selectValue);
                if (view.getId() == R.id.product_tv) {
                    getProductType(selectValue);
                } else {
                    getParam(product_tv.getText().toString(), selectValue);
                }
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                textView.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //获取产品型号下拉值
    private void getProductType(final String elevatorProduct) {
        baseResponseObservable = HttpFactory.getApiService().getElevatorTypeDropDownList(elevatorProduct);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<NewProductDropDown>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<NewProductDropDown>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<NewProductDropDown> response) {
                        NewProductDropDown data = response.getData();
                        typeList.clear();
                        typeList.addAll(data.getElevatorTypeList());
                        //产品型号默认选中第一项
                        if (typeList.size() > 0) {
                            if (productInfo == null) {
                                model_tv.setText(typeList.get(0));
                            }
                            getParam(elevatorProduct, typeList.get(0));
                        }
                    }
                });
    }

    //获取其余参数下拉值
    private void getParam(String elevatorProduct, String elevatorType) {
        baseResponseObservable = HttpFactory.getApiService().getTypeIdAndOptionList(elevatorProduct, elevatorType);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<NewProductDropDown>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<NewProductDropDown>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<NewProductDropDown> response) {
                        NewProductDropDown data = response.getData();
                        param1List.clear();
                        param2List.clear();
                        param1List.addAll((Constants.ELEVATOR.equals(type) ? data.getElevatorLoadList() : data.getRungsWidthList()));
                        param2List.addAll((Constants.ELEVATOR.equals(type) ? data.getSpeedList() : data.getAngleList()));

                        if (param1List.size() > 0) {
                            if (productInfo == null) {
                                param1_tv.setText(param1List.get(0).getText());
                                param1_tv.setTag(param1List.get(0).getValue());
                            }
                        }

                        if (param2List.size() > 0) {
                            if (productInfo == null) {
                                param2_tv.setText(param2List.get(0).getText());
                                param2_tv.setTag(param2List.get(0).getValue());
                            }
                        }

                    }
                });
    }

    @Override
    public void confirm(Collection collection) {

        this.programmeGuid = collection.getGuid();
        if (Constants.ELEVATOR.equals(type)) {
            param1_tv.setText(collection.getEl_Load());
            param1_tv.setTag(collection.getEl_Load());
            param2_tv.setText(collection.getEl_V());
            param2_tv.setTag(collection.getEl_V());
            product_tv.setText(collection.getElevatorProduct());
            model_tv.setText(collection.getElevatorType());

        } else {
            param1_tv.setText(collection.getEs_SW());
            param1_tv.setTag(collection.getEs_SW());
            param2_tv.setText(collection.getEs_Angle());
            param2_tv.setTag(collection.getEs_Angle());
            product_tv.setText(collection.getElevatorProduct());
            model_tv.setText(collection.getElevatorType());
        }
    }
}
