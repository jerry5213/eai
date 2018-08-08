package com.zxtech.ecs.ui.home.quote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.PayMethodAdapter;
import com.zxtech.ecs.model.DropDownVo;
import com.zxtech.ecs.model.PayMethod;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.DropDownWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/19.
 */

public class PaymentMethodDialogFragment extends BaseDialogFragment {
    @BindView(R.id.standard_layout)
    LinearLayout standard_layout;
    @BindView(R.id.non_standard_layout)
    LinearLayout non_standard_layout;
    @BindView(R.id.inst_layout)
    LinearLayout inst_layout;
    @BindView(R.id.eq_layout)
    LinearLayout eq_layout;

    @BindView(R.id.method_tv)
    TextView method_tv;
    @BindView(R.id.type_tv)
    TextView type_tv;
    @BindView(R.id.non_standard_et)
    EditText non_standard_et;
    @BindView(R.id.inst_rv)
    RecyclerView inst_rv;
    @BindView(R.id.eq_rv)
    RecyclerView eq_rv;
    @BindView(R.id.save_tv)
    TextView save_tv;

    public static final String EQ_PAYMENT = "0";
    public static final String INSTALL_PAYMENT = "1";

    public static final String STANDARD = "1";
    public static final String NON_STANDARD = "0";

    private PayMethodAdapter instAdapter;
    private PayMethodAdapter eqAdapter;

    private String projectGuid;
    private String payTypeParam;
    //是否安装合同
    private boolean isInstallContract;
    private boolean standardType = true;

    private HashMap<String,String[]> payTypeParamMap = new HashMap<>();

    public PaymentMethodCallBack callBack;

    private boolean isEdit;

    public static PaymentMethodDialogFragment newInstance() {
        return new PaymentMethodDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_payment_method;
    }


    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        sethScale(0.6f);
        setwScale(0.95f);

        if (payTypeParam != null) {
            if (standardType) {
                String[] split = payTypeParam.split(";");
                for (int i = 0; i < split.length; i++) {
                    String[] split1 = split[i].split(",");
                    payTypeParamMap.put(split1[0],split1);
                }
            }else{
                non_standard_et.setText(payTypeParam);
            }
        }
        type_tv.setText(standardType ? "标准":"非标");
        type_tv.setTag(standardType ? STANDARD : NON_STANDARD);
        method_tv.setTag(EQ_PAYMENT);
        switchStandard(type_tv.getTag().toString());
        switchPayment(EQ_PAYMENT);

        if (!isEdit) {
            non_standard_et.setEnabled(false);
            save_tv.setEnabled(false);
        }

        initData();
    }

    private void initData() {
        baseResponseObservable = HttpFactory.getApiService().getPayTypeList(projectGuid);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<PayMethod>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<PayMethod>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<PayMethod>> response) {
                        List<PayMethod> instList = new ArrayList<>();
                        List<PayMethod> eqList = new ArrayList<>();


                        for (int i = 0; i < response.getData().size(); i++) {
                            PayMethod payMethod = response.getData().get(i);
                            if (payTypeParam != null) { //回显赋值
                                if (payTypeParamMap.get(payMethod.getGuid()) != null) {
                                    String[] stringArray = payTypeParamMap.get(payMethod.getGuid());
                                    payMethod.setIsChecked("True");
                                    payMethod.setDays(stringArray[1]);
                                    payMethod.setPercent(stringArray[2]);
                                    payMethod.setContractProperty(stringArray[3]);
                                }else{
                                    payMethod.setIsChecked("False");
                                    payMethod.setDays("0");
                                    payMethod.setPercent("0");
                                }
                            }
                            if ("安装付款".equals(payMethod.getProperty())) {
                                instList.add(payMethod);
                            } else if ("设备付款".equals(payMethod.getProperty())) {
                                eqList.add(payMethod);
                            }
                        }

                        instAdapter = new PayMethodAdapter(R.layout.item_paymethod, instList,isEdit);
                        inst_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        inst_rv.setAdapter(instAdapter);

                        eqAdapter = new PayMethodAdapter(R.layout.item_paymethod, eqList,isEdit);
                        eq_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        eq_rv.setAdapter(eqAdapter);
                    }
                });
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }

    @OnClick({R.id.type_tv, R.id.method_tv, R.id.save_tv})
    public void onClick(final View view) {
        DropDownWindow dropDownWindow = null;
        final List<DropDownVo> mDatas = new ArrayList<>();
        switch (view.getId()) {
            case R.id.type_tv:
                mDatas.add(new DropDownVo(STANDARD, "标准"));
                mDatas.add(new DropDownVo(NON_STANDARD, "非标"));
                dropDownWindow = new DropDownWindow(getActivity(), view, (TextView) view, mDatas, view.getWidth(), -2) {
                    @Override
                    public void initEvents(int p) {
                        view.setTag(mDatas.get(p).getValue());
                        ((TextView) view).setText(mDatas.get(p).getText());
                        switchStandard(mDatas.get(p).getValue());
                    }
                };
                break;
            case R.id.method_tv:
                mDatas.add(new DropDownVo(EQ_PAYMENT, "设备付款"));
                if (isInstallContract) {
                    mDatas.add(new DropDownVo(INSTALL_PAYMENT, "安装付款"));
                }
                dropDownWindow = new DropDownWindow(getActivity(), view, (TextView) view, mDatas, view.getWidth(), -2) {
                    @Override
                    public void initEvents(int p) {
                        view.setTag(mDatas.get(p).getValue());
                        ((TextView) view).setText(mDatas.get(p).getText());
                        switchPayment(mDatas.get(p).getValue());

                    }
                };
                break;
            case R.id.save_tv:
                onSave();
                break;

        }
    }

    private void switchStandard(String tag) {
        if (STANDARD.equals(tag)) {
            standard_layout.setVisibility(View.VISIBLE);
            non_standard_layout.setVisibility(View.GONE);
        } else {
            standard_layout.setVisibility(View.GONE);
            non_standard_layout.setVisibility(View.VISIBLE);
        }
    }

    private void switchPayment(String tag) {
        if (EQ_PAYMENT.equals(tag)) {
            eq_layout.setVisibility(View.VISIBLE);
            inst_layout.setVisibility(View.GONE);
        } else {
            eq_layout.setVisibility(View.GONE);
            inst_layout.setVisibility(View.VISIBLE);
        }
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public void setInstallContract(boolean installContract) {
        isInstallContract = installContract;
    }

    public void setStandardType(boolean standardType) {
        this.standardType = standardType;
    }

    public void setPayTypeParam(String payTypeParam) {
        this.payTypeParam = payTypeParam;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public void onSave() {
        //格式 Guid,day,percent,contractProperty;
        StringBuffer payTypeParam = new StringBuffer();
        if (STANDARD.equals(type_tv.getTag().toString())) {
            List<PayMethod> eqAdapterData = eqAdapter.getData();
            List<PayMethod> instAdapterData = instAdapter.getData();

            int sumEQ = 0;
            for (int i = 0; i < eqAdapterData.size(); i++) {
                PayMethod payMethod = eqAdapterData.get(i);
                String ratio = payMethod.getPercent();

                if ( payMethod.isCheck() && !TextUtils.isEmpty(ratio)) {
                    sumEQ += Integer.valueOf(ratio);
                    payTypeParam.append(payMethod.getGuid());
                    payTypeParam.append(",");
                    payTypeParam.append(payMethod.getDays());
                    payTypeParam.append(",");
                    payTypeParam.append(payMethod.getPercent());
                    payTypeParam.append(",");
                    payTypeParam.append(payMethod.getContractProperty());
                    payTypeParam.append(";");
                }
            }

            if (sumEQ != 100) { //标准时，设备付款比例总和必须等于100%
                ToastUtil.showLong("设备付款比例总和必须等于100%");
                return;
            }

            if (isInstallContract) { //如果是安装合同，安装付款比例总和必须等于100%
                int sumInst = 0;
                for (int i = 0; i < instAdapterData.size(); i++) {
                    PayMethod payMethod = instAdapterData.get(i);
                    String ratio = payMethod.getPercent();
                    if (payMethod.isCheck() && !TextUtils.isEmpty(ratio)) {
                        sumInst += Integer.valueOf(ratio);
                        payTypeParam.append(payMethod.getGuid());
                        payTypeParam.append(",");
                        payTypeParam.append(payMethod.getDays());
                        payTypeParam.append(",");
                        payTypeParam.append(payMethod.getPercent());
                        payTypeParam.append(",");
                        payTypeParam.append(payMethod.getContractProperty());
                        payTypeParam.append(";");
                    }
                }

                if (sumInst != 100) {
                    ToastUtil.showLong("安装付款比例总和必须等于100%");
                    return;
                }
            }
            //取出尾部;
            if (payTypeParam.toString().endsWith(";")) {
                String substring = payTypeParam.toString().substring(0, payTypeParam.toString().length() - 1);
                payTypeParam = new StringBuffer(substring);
            }

        }else{
            if (TextUtils.isEmpty(non_standard_et.getText())) {
                ToastUtil.showLong("非标付款内容为空，请填写");
                return;
            }
            payTypeParam.append(non_standard_et.getText().toString());
        }

        if (callBack != null) {
            callBack.paymentMethodSave(STANDARD.equals(type_tv.getTag().toString()),payTypeParam.toString());
        }
        dismiss();
    }


    public interface PaymentMethodCallBack {
        void paymentMethodSave(boolean standardType, String payTypeParam);
    }
}
