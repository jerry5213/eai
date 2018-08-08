package com.zxtech.ecs.ui.home.payment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.event.EventAccountPayment;
import com.zxtech.ecs.model.AccountPayment;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/7/10.
 */

public class AccountPaymentDetailActivity extends BaseActivity {

    private BaseFragment[] mFragments = new BaseFragment[2];
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.save_btn)
    Button save_btn;

    private String[] titles;
    private String accountGuid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_payment_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.payment_details));

        accountGuid = getIntent().getStringExtra("accountGuid");
        String orderNumber = getIntent().getStringExtra("orderNumber");
        if (savedInstanceState == null) {
            mFragments[0] = BasicInfoFragment.newInstance(accountGuid);
            mFragments[1] = AllotInfoFragment.newInstance(accountGuid,orderNumber);

            loadMultipleRootFragment(R.id.fl_container, 0,
                    mFragments[0],
                    mFragments[1]
            );
        }


        titles = new String[]{getString(R.string.basic_info), getString(R.string.assignment_info)};

        tabLayout.addTab(tabLayout.newTab().setText(titles[0]));
        tabLayout.addTab(tabLayout.newTab().setText(titles[1]));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showHideFragment(mFragments[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //设置分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.tab_divider)); //设置分割线的样式
        linearLayout.setDividerPadding(DensityUtil.dip2px(mContext, 10)); //设置分割线间隔
    }

    @OnClick(R.id.save_btn)
    public void OnClick(View view){

        switch (view.getId()){

            case R.id.save_btn:

                final AccountPayment paymentBasic = ((BasicInfoFragment)mFragments[0]).getAccountPaymentBasic();
                Map params = new HashMap();

                if(TextUtils.isEmpty(paymentBasic.getInvoiceUnit())){
                    ToastUtil.showLong(getString(R.string.msg50));
                    return;
                }

                Map dicInfoMap = new HashMap();
                dicInfoMap.put("AccountGuid",accountGuid);
                dicInfoMap.put("InvoiceUnit",paymentBasic.getInvoiceUnit());
                dicInfoMap.put("InfoSupporter",paymentBasic.getInfoSupporter());
                dicInfoMap.put("PaymentRemark",paymentBasic.getPaymentRemark()==null?"":paymentBasic.getPaymentRemark());
                String payNode = ((AllotInfoFragment)mFragments[1]).getPayNode();
                dicInfoMap.put("PayNode",payNode == null?"":payNode);
                dicInfoMap.put("UserId",getUserId());

                List<Map> dicInfoList = ((AllotInfoFragment)mFragments[1]).getDicInfoList();
                final String allotMoney = ((AllotInfoFragment)mFragments[1]).getAllotMoney();
                params.put("DicInfo",dicInfoMap);
                params.put("DicInfoList",dicInfoList);

                String json = new Gson().toJson(params);
                baseResponseObservable = HttpFactory.getApiService().saveAccountInfoForPayNode(json);
                baseResponseObservable
                        .compose(this.bindToLifecycle())
                        .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                        .subscribe(new DefaultObserver<BaseResponse>(getActivity(), false) {
                            @Override
                            public void onSuccess(BaseResponse response) {

                                paymentBasic.setInvoiceUnit(paymentBasic.getInvoiceUnit());
                                paymentBasic.setInfoSupporter(paymentBasic.getInfoSupporter());
                                paymentBasic.setAllotMoney(allotMoney);
                                paymentBasic.setOrderNumber(getIntent().getStringExtra("orderNumber"));
                                EventBus.getDefault().post(paymentBasic);
                                finish();
                            }
                        });
                break;
        }
    }
}
