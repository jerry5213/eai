package com.zxtech.ecs.ui.home.contractchange;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ProductCancelAdapter;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.model.DropDownVo;
import com.zxtech.ecs.model.ProductCancel;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.ConfirmDialog;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.EditDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/7/30.
 */

public class ProductCancelFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.save_tv)
    TextView save_tv;
    @BindView(R.id.submit_tv)
    TextView submit_tv;

    private ProductCancelAdapter mAdapter;
    private List<ProductCancel> mDatas = new ArrayList<>();
    private boolean isEdit;

    public static ProductCancelFragment newInstance(String projectGuidBefore, String projectGuidAfter, String projectNo, String contractNo, String contractChangeGuid, boolean isEdit) {
        Bundle args = new Bundle();
        ProductCancelFragment fragment = new ProductCancelFragment();
        args.putString("projectGuidBefore", projectGuidBefore);
        args.putString("projectGuidAfter", projectGuidAfter);
        args.putString("projectNo", projectNo);
        args.putString("contractNo", contractNo);
        args.putString("contractChangeGuid", contractChangeGuid);
        args.putBoolean("isEdit", isEdit);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_cancel;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        isEdit = getArguments().getBoolean("isEdit");
        mAdapter = new ProductCancelAdapter(getContext(), R.layout.item_product_cancel, mDatas, isEdit);
        mAdapter.setOnItemChildClickListener(this);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_view.setAdapter(mAdapter);

        save_tv.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        submit_tv.setVisibility(isEdit ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData(true);
    }


    private void initData(boolean init) {

        Bundle arguments = getArguments();

        baseResponseObservable = HttpFactory.getApiService().getCancelContractInfoList(arguments.getString("contractChangeGuid"));
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<ProductCancel>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ProductCancel>>>(getActivity(), init) {
                    @Override
                    public void onSuccess(BaseResponse<List<ProductCancel>> response) {

                        mDatas.clear();
                        mDatas.addAll(response.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, final View view, final int position) {
        if (!isEdit) {
            return;
        }
        final List<DropDownVo> data = new ArrayList<>();
        switch (view.getId()) {
            case R.id.no_tv:

                ConfirmDialog.newInstance().setBuider(mContext, "删除产品", "确认删除取消变更产品吗？", new ConfirmDialog.DialogConfirmCallBack() {
                    @Override
                    public void confirm() {
                        deleteCancelProduct(position);
                    }
                }).show();

                break;
            case R.id.project_state_tv:
                data.add(new DropDownVo("已排产", "已排产"));
                data.add(new DropDownVo("未排产", "未排产"));
                new DropDownWindow(mContext, view, (TextView) view, data, view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDownVo dropDownVo = data.get(p);
                        ((TextView) view).setText(dropDownVo.getText());
                        mDatas.get(position).setCancelStateId(dropDownVo.getValue());
                        mDatas.get(position).setCancelStateValue(dropDownVo.getText());
                    }
                };
                break;
            case R.id.payment_tv:
                data.add(new DropDownVo("已付款", "已付款"));
                data.add(new DropDownVo("未付款", "未付款"));
                new DropDownWindow(mContext, view, (TextView) view, data, view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDownVo dropDownVo = data.get(p);
                        ((TextView) view).setText(dropDownVo.getText());
                        mDatas.get(position).setPaymentId(dropDownVo.getValue());
                        mDatas.get(position).setPaymentValue(dropDownVo.getText());
                    }
                };
                break;
            case R.id.reason_tv:
                EditDialog.newInstance().setBuider(mContext, "取消原因", ((TextView) view).getText().toString(), new EditDialog.DialogConfirmCallBack() {
                    @Override
                    public void confirm(String value) {
                        if (TextUtils.isEmpty(value)) {
                            return;
                        }
                        mDatas.get(position).setCancelReason(value);
                        ((TextView) view).setText(value);

                    }
                }).show();
                break;
            case R.id.solution_tv:
                EditDialog.newInstance().setBuider(mContext, "处理办法", ((TextView) view).getText().toString(), new EditDialog.DialogConfirmCallBack() {
                    @Override
                    public void confirm(String value) {
                        if (TextUtils.isEmpty(value)) {
                            return;
                        }
                        mDatas.get(position).setCancelMoneyDealRemark(value);
                        ((TextView) view).setText(value);
                    }
                }).show();
                break;
        }
    }


    @OnClick({R.id.save_tv, R.id.submit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_tv:
                save();
                break;
            case R.id.submit_tv:
                submit();
                break;
        }
    }

    private void submit() {
        baseResponseObservable = HttpFactory.getApiService().submitCanelContractInfo(getDataParams(),getArguments().getString("contractChangeGuid"),getUserNo(),getUserName());
        baseResponseObservable
                .compose(this.bindToLifecycle())
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

    private void save() {

        baseResponseObservable = HttpFactory.getApiService().saveCanelContractInfo(getDataParams());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                      ToastUtil.showLong("保存成功");
                    }
                });

    }

    private String getDataParams(){
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = null;
        for (ProductCancel productCancel:mDatas) {
            jsonObject = new JsonObject();
            jsonObject.addProperty("Guid",productCancel.getGuid());
            jsonObject.addProperty("CancelStateId",productCancel.getCancelStateId());
            jsonObject.addProperty("CancelStateValue",productCancel.getCancelStateValue());
            jsonObject.addProperty("PaymentId",productCancel.getPaymentId());
            jsonObject.addProperty("PaymentValue",productCancel.getPaymentValue());
            jsonObject.addProperty("CancelReason",productCancel.getCancelReason());
            jsonObject.addProperty("CancelMoneyDealRemark",productCancel.getCancelMoneyDealRemark());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }

    private void deleteCancelProduct(final int position) {
        ProductCancel productCancel = mDatas.get(position);
        baseResponseObservable = HttpFactory.getApiService().deleteCancelContractInfo(productCancel.getGuid());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        mDatas.remove(position);
                        mAdapter.notifyDataSetChanged();
                        ToastUtil.showLong("删除成功");
                        EventBus.getDefault().post(new EventAction(EventAction.CONTRACT_CHANGE_DELETE));

                    }
                });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventAction event) {
        if (event.getCode() == EventAction.PRODUCT_CHANGE) {
            initData(false);
        }
    }
}
