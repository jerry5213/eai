package com.zxtech.ecs.ui.home.contractchange;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.BusinessChangeAdapter;
import com.zxtech.ecs.adapter.ProductInfoAdapter;
import com.zxtech.ecs.adapter.SpecChangeAdapter;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.model.ContractChangeSummary;
import com.zxtech.ecs.model.DropDownVo;
import com.zxtech.ecs.model.ProductInfo;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.DateUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.DropDownWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/27.
 */

public class ChangeSummaryFragment extends BaseFragment {

    @BindView(R.id.reply_date_tv)
    TextView reply_date_tv;
    @BindView(R.id.apply_date_tv)
    TextView apply_date_tv;
    @BindView(R.id.reason_tv)
    TextView reason_tv;
    @BindView(R.id.desc_et)
    EditText desc_et;
    @BindView(R.id.business_rv)
    RecyclerView business_rv;
    @BindView(R.id.spec_rv)
    RecyclerView spec_rv;

    private ContractChangeSummary contractChangeSummary = new ContractChangeSummary();
    private boolean isEdit;

    private Calendar cal = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            reply_date_tv.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
        }
    };

    public static ChangeSummaryFragment newInstance(String contractChangeGuid, boolean isEdit) {
        Bundle args = new Bundle();
        ChangeSummaryFragment fragment = new ChangeSummaryFragment();
        args.putString("contractChangeGuid", contractChangeGuid);
        args.putBoolean("isEdit", isEdit); //界面是否允许编辑
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contract_change_summary;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        isEdit = getArguments().getBoolean("isEdit");
        EventBus.getDefault().register(this);
        apply_date_tv.setText(DateUtil.getCurrentDate());

        business_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        spec_rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (!isEdit) {
            reply_date_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            reply_date_tv.setEnabled(false);
            reason_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            reason_tv.setEnabled(false);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData(true);
    }

    private void initData(final boolean init) {


        baseResponseObservable = HttpFactory.getApiService().getChangeInfo(getArguments().getString("contractChangeGuid"));
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<ContractChangeSummary>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<ContractChangeSummary>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<ContractChangeSummary> response) {
                        contractChangeSummary = response.getData();
                        BusinessChangeAdapter busAdapter = new BusinessChangeAdapter(R.layout.item_business_change, contractChangeSummary.getDicInfoList_bus());
                        business_rv.setAdapter(busAdapter);

                        SpecChangeAdapter specAdapter = new SpecChangeAdapter(R.layout.item_spec_change,contractChangeSummary.getDicInfoList_tec());
                        spec_rv.setAdapter(specAdapter);

                        if (init) {
                            reply_date_tv.setText(contractChangeSummary.getRecoveryTime());
                            reason_tv.setText(contractChangeSummary.getChangeReason());
                        }

                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventAction event) {
        if (event.getCode() == EventAction.CONTRACT_CHANGE) {
            initData(false);
        }
    }

    @OnClick({R.id.reply_date_tv, R.id.reason_tv, R.id.submit_btn})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.reply_date_tv:

                new DatePickerDialog(mContext, listener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
            case R.id.reason_tv:
                final List<DropDownVo> mDatas = new ArrayList<>();
                mDatas.add(new DropDownVo("", "客户原因"));
                mDatas.add(new DropDownVo("", "内部原因"));
                new DropDownWindow(getActivity(), view, (TextView) view, mDatas, view.getWidth(), -2) {
                    @Override
                    public void initEvents(int p) {
                        ((TextView) view).setText(mDatas.get(p).getText());
                    }
                };
                break;
            case R.id.submit_btn:
                submit();
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void submit() {

        baseResponseObservable = HttpFactory.getApiService().submitContractChange(getArguments().getString("contractChangeGuid"),"0",desc_et.getText().toString(),getUserNo(),getUserName(),reply_date_tv.getText().toString(),reason_tv.getText().toString());
        baseResponseObservable
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        getActivity().setResult(1002);
                        getActivity().finish();
                        ToastUtil.showLong(getString(R.string.submitted));

                    }
                });
    }
}