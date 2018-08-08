package com.zxtech.ecs.ui.home.payment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.AccountPaymentAdapter;
import com.zxtech.ecs.adapter.ToolAdapter;
import com.zxtech.ecs.event.EventAccountPayment;
import com.zxtech.ecs.model.AccountPayment;
import com.zxtech.ecs.model.ToolBean;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.ItemDivider;
import com.zxtech.ecs.widget.MyItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by syp523 on 2018/7/9.
 */

public class PaymentActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener, AllotTypeSelectedDialogFragment.SelectedCallBack, AccountPaymentModifyDialogFragment.AccountPaymentModifyDialogFragmentCallBack, ProblemDialogFragment.ProblemDialogFragmentCallBack {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleview_tool)
    RecyclerView recycleViewTool;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout refreshlayout;

    private String[] toolText = null;
    private List<ToolBean> toolBeanLists = new ArrayList<>();
    private ToolAdapter toolAdapter;

    private AccountPaymentAdapter accountPaymentAdapter;
    private List<AccountPayment> mDatas = new ArrayList<>();

    private int pageIndex = 1;
    private int UIMode;

    private static final String INTENT_DETAIL = "ChooseDetail";
    private static final String INTENT_OTHER_DETAIL = "PayOther";
    private static final String INTENT_DIALOG = "SelectCopyType";
    private static final String INTENT_PROJECT = "DistributionList";

    private AccountPayment currentSelectedAccountPayment;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_payment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        toolText = new String[]{getString(R.string.assignment_of_payment), getString(R.string.new_assignment), getString(R.string.cancel_assignment), getString(R.string.submit_review), getString(R.string.payment_collection_Issue), getString(R.string.apply_edit), getString(R.string.assignment_history)};
        UIMode = getIntent().getIntExtra("UIMode", 0);
        if (UIMode == 1) {
            initTitleBar(toolbar, getString(R.string.version_history));
            recycleViewTool.setVisibility(View.GONE);
        } else {
            initTitleBar(toolbar, getString(R.string.payment_management));
            recycleViewTool.setVisibility(View.VISIBLE);
        }

        int[] colorfulArr = getResources().getIntArray(R.array.colorful);
        for (int a = 0; a < toolText.length; a++) {
            ToolBean bean = new ToolBean(toolText[a], colorfulArr[a % 6]);
            toolBeanLists.add(bean);
        }

        toolAdapter = new ToolAdapter(R.layout.item_corner_radius_textview, toolBeanLists);
        recycleViewTool.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleViewTool.addItemDecoration(new ItemDivider().setDividerWith(20).setDividerColor(Color.TRANSPARENT));
        recycleViewTool.setAdapter(toolAdapter);
        toolAdapter.setOnItemClickListener(this);

        refreshlayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshlayout.setDelegate(this);
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleview.addItemDecoration(new MyItemDecoration(15));

        accountPaymentAdapter = new AccountPaymentAdapter(R.layout.item_account_payment, mDatas, UIMode == 0);
        recycleview.setAdapter(accountPaymentAdapter);
        refreshlayout.beginRefreshing();
    }


    private void refresh() {
        baseResponseObservable = HttpFactory.getApiService().getAllAccountListByPage(1, APPConfig.PAGE_SIZE, getIntent().getStringExtra("accountGuid"));
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<AccountPayment>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<AccountPayment>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<AccountPayment>> response) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            pageIndex = 1;
                            mDatas.clear();
                            mDatas.addAll(response.getData());
                            accountPaymentAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showLong(getString(R.string.msg8));
                        }
                        refreshlayout.endRefreshing();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        refreshlayout.endRefreshing();
                    }
                });
    }


    private void loadingMore() {
        baseResponseObservable = HttpFactory.getApiService().getAllAccountListByPage(pageIndex + 1, APPConfig.PAGE_SIZE, getIntent().getStringExtra("accountGuid"));
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<AccountPayment>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<AccountPayment>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<AccountPayment>> response) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            pageIndex++;
                            mDatas.addAll(response.getData());
                            accountPaymentAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showLong(getString(R.string.msg8));
                        }
                        refreshlayout.endLoadingMore();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        refreshlayout.endLoadingMore();
                    }
                });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        loadingMore();
        return true;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        int selectedPosition = accountPaymentAdapter.getSelectedPosition();
        if (selectedPosition == -1) {
            return;
        }
        AccountPayment accountPayment = mDatas.get(selectedPosition);
        this.currentSelectedAccountPayment = accountPayment;

        switch (position) {
            case 0://到账分配
                validateAllot(accountPayment.getGuid());
                break;
            case 1://新建拆分
                break;
            case 2://撤销分款
                revokeAllot(accountPayment.getGuid());
                break;
            case 3://提交审核
                submitAllot(accountPayment.getGuid(),selectedPosition);
                break;
            case 4://到款问题
                if (TextUtils.isEmpty(currentSelectedAccountPayment.getPayProblem()) || "已解决".equals(currentSelectedAccountPayment.getPayDealStatus())) {
                    return;
                }
                if (getUserId() != null && !getUserId().equals(currentSelectedAccountPayment.getSaleUserId())) {
                    return;
                }
                ProblemDialogFragment problemDialogFragment = ProblemDialogFragment.newInstance();
                problemDialogFragment.setDesc(currentSelectedAccountPayment.getPayProblem());
                problemDialogFragment.callBack = this;
                problemDialogFragment.show(getFragmentManager(), "problem");
                break;
            case 5://申请修改
                applyUpdate(accountPayment.getGuid());
                break;
            case 6://历史分款
                if ("A".equals(accountPayment.getVersionNo())) {
                    ToastUtil.showLong(getString(R.string.msg57));
                    return;
                }
                Intent intent = new Intent(mContext, PaymentActivity.class);
                intent.putExtra("accountGuid", accountPayment.getGuid());
                intent.putExtra("UIMode", 1);
                startActivity(intent);
                break;

        }
    }

    private void applyUpdate(String guid) {
        baseResponseObservable = HttpFactory.getApiService().validateUpdateApply(guid, getUserId());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        modifyDialog();
                    }
                });
    }

    private void modifyDialog() {
        AccountPaymentModifyDialogFragment allotTypeSelectedDialogFragment = AccountPaymentModifyDialogFragment.newInstance();
        allotTypeSelectedDialogFragment.callBack = this;
        allotTypeSelectedDialogFragment.show(getFragmentManager(), "apply_modify");
    }

    private void submitAllot(String guid, final int position) {
        baseResponseObservable = HttpFactory.getApiService().submitAllot(guid, getUserId(), getUserNo(), getUserName(), getUserDeptId());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        ToastUtil.showLong(getString(R.string.submitted));
                        mDatas.get(position).setStatus(1);
                        accountPaymentAdapter.notifyItemChanged(position);
                    }
                });
    }

    private void revokeAllot(String guid) {
        baseResponseObservable = HttpFactory.getApiService().delSplitMoney(guid, getUserId());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        AccountPayment accountPayment = mDatas.get(accountPaymentAdapter.getSelectedPosition());
                        accountPayment.setInvoiceUnit(null);
                        accountPayment.setInfoSupporter(null);
                        accountPayment.setAllotMoney("0");
                        accountPayment.setOrderNumber(null);
                        accountPayment.setProjectName(null);
                        accountPayment.setContractNo(null);
                        accountPayment.setContractArchivesNo(null);
                        accountPayment.setSaleBranchName(null);
                        accountPayment.setCopyType(null);
                        accountPayment.setPaymentRemark(null);
                        accountPaymentAdapter.notifyItemChanged(accountPaymentAdapter.getSelectedPosition());
                        ToastUtil.showLong(getString(R.string.revocation_of_success));
                    }
                });
    }

    private void allotDialog() {
        AllotTypeSelectedDialogFragment allotTypeSelectedDialogFragment = AllotTypeSelectedDialogFragment.newInstance();
        allotTypeSelectedDialogFragment.callBack = this;
        allotTypeSelectedDialogFragment.show(getFragmentManager(), "allot_type");
    }

    private void validateAllot(String accountGuid) {
        baseResponseObservable = HttpFactory.getApiService().validateAllot(getUserId(), getUserName(), getUserDeptNo(), accountGuid, "allot");
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        validateAllotHandle(response.getData());
                    }
                });
    }

    private void validateAllotHandle(String allotType) {
        Intent intent = null;
        if (INTENT_DIALOG.equals(allotType)) {
            allotDialog();
        } else if ("".equals(allotType)) {
            allotDialog();
        } else if (INTENT_DETAIL.equals(allotType)) {
            intent = new Intent(mContext, AccountPaymentDetailActivity.class);
            intent.putExtra("accountGuid", this.currentSelectedAccountPayment.getGuid());
            intent.putExtra("orderNumber", this.currentSelectedAccountPayment.getOrderNumber());
            startActivity(intent);
        } else if (INTENT_OTHER_DETAIL.equals(allotType)) {
            intent = new Intent(mContext, AccountPaymentOtherDetailActivity.class);
            intent.putExtra("accountPayment",this.currentSelectedAccountPayment);
            startActivityForResult(intent,1);
        } else if (INTENT_PROJECT.equals(allotType)) {
            intent = new Intent(mContext, AccountPaymentProjectActivity.class);
            intent.putExtra("accountGuid", this.currentSelectedAccountPayment.getGuid());
            intent.putExtra("orderNumber", this.currentSelectedAccountPayment.getOrderNumber());
            startActivity(intent);
        }

    }

    @Override
    public void confirm(String type) {
        baseResponseObservable = HttpFactory.getApiService().saveSaleUser(getUserId(), getUserName(), getUserDeptNo(), currentSelectedAccountPayment.getGuid(), type);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        validateAllotHandle(response.getData());
                    }
                });

    }

    @Override
    public void applyModify(String type, String reason) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Guid", this.currentSelectedAccountPayment.getGuid());
        jsonObject.addProperty("UpdateStatus", type);
        jsonObject.addProperty("UpdateReason", reason);
        baseResponseObservable = HttpFactory.getApiService().saveAccountInfo(jsonObject.toString());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        ToastUtil.showLong(getString(R.string.apply_successful));
                    }
                });
    }


    @Override
    public void solveProblem() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Guid", this.currentSelectedAccountPayment.getGuid());
        jsonObject.addProperty("PayDealStatus", "已解决");
        baseResponseObservable = HttpFactory.getApiService().saveAccountInfo(jsonObject.toString());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mDatas.get(accountPaymentAdapter.getSelectedPosition()).setPayDealStatus("已解决");
                        accountPaymentAdapter.notifyItemChanged(accountPaymentAdapter.getSelectedPosition());
                        ToastUtil.showLong(getString(R.string.problem_solved));
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10020) {
            AccountPayment accountPayment = (AccountPayment) data.getSerializableExtra("accountPayment");
            mDatas.set(accountPaymentAdapter.getSelectedPosition(),accountPayment);
            accountPaymentAdapter.notifyItemChanged(accountPaymentAdapter.getSelectedPosition());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AccountPayment data){ //分款界面返回

        AccountPayment accountPayment = mDatas.get(accountPaymentAdapter.getSelectedPosition());
        accountPayment.setInvoiceUnit(data.getInvoiceUnit());
        accountPayment.setInfoSupporter(data.getInfoSupporter());
        accountPayment.setAllotMoney(data.getAllotMoney());
        accountPayment.setOrderNumber(data.getOrderNumber());
        accountPayment.setProjectName(data.getProjectName());
        accountPayment.setContractNo(data.getContractNo());
        accountPayment.setContractArchivesNo(data.getContractArchivesNo());
        accountPayment.setSaleBranchName(data.getSaleBranchName());
        accountPayment.setCopyType("定金/设备款/安装款");
        accountPaymentAdapter.notifyItemChanged(accountPaymentAdapter.getSelectedPosition());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
