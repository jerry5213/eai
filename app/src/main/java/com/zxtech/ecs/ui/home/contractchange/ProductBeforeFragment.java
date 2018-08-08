package com.zxtech.ecs.ui.home.contractchange;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ProductInfoAdapter;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.event.EventFloorStation;
import com.zxtech.ecs.model.ProductInfo;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/6/27.
 */

public class ProductBeforeFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener, ChangeElevatorDialogFragment.ChangeElevatorCallBack {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private ProductInfoAdapter mAdapter;
    private List<ProductInfo> lists = new ArrayList<>();

    private String projectNo;
    private String newProductNo;
    private String productGuid;


    public static ProductBeforeFragment newInstance(String projectGuidBefore, String projectGuidAfter, String projectNo, String contractNo, String contractChangeGuid, boolean isEdit, int operation) {
        Bundle args = new Bundle();
        ProductBeforeFragment fragment = new ProductBeforeFragment();
        args.putString("projectGuidBefore", projectGuidBefore);
        args.putString("projectGuidAfter", projectGuidAfter);
        args.putString("projectNo", projectNo);
        args.putString("contractNo", contractNo);
        args.putString("contractChangeGuid", contractChangeGuid);
        args.putBoolean("isEdit", isEdit);
        //0合同变更 1合同取消
        args.putInt("operation", operation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_before;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mAdapter = new ProductInfoAdapter(getContext(), R.layout.item_product_info, lists, false,getArguments().getBoolean("isEdit"));
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_view.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData();
    }


    private void initData() {


        projectNo = getArguments().getString("projectNo");

        baseResponseObservable = HttpFactory.getApiService().getProductList(getArguments().getString("projectGuidAfter"), "", getArguments().getString("contractChangeGuid"), "-3", "0");
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<ProductInfo>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ProductInfo>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<ProductInfo>> response) {

                        lists.clear();
                        lists.addAll(response.getData());
                        mAdapter.notifyDataSetChanged();
                    }

                });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (!getArguments().getBoolean("isEdit")) {
            return;
        }
        if (view.getId() == R.id.column7) {
            ProductInfo productInfo = lists.get(position);
            if (TextUtils.isEmpty(productInfo.getCutStringElevatorNo())) {
                return;
            }
            this.productGuid = productInfo.getEQS_Guid();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("EQS_ProductNo", productInfo.getProductNo());
            jsonObject.addProperty("ProjectGuid", getArguments().getString("projectGuidAfter"));
            jsonObject.addProperty("EqsProductGuid", productInfo.getEQS_Guid());
            jsonObject.addProperty("CCVersion", productInfo.getVersionNum());
            jsonObject.addProperty("oldProjectGuid", getArguments().getString("projectGuidBefore"));
            jsonObject.addProperty("contractNo", getArguments().getString("contractNo"));

            baseResponseObservable = HttpFactory.getApiService().getEQSProductVersion(jsonObject.toString());
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<Map<String, Object>>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<Map<String, Object>>>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse<Map<String, Object>> response) {
                            List<String> elevatorList = (List<String>) response.getData().get("elevatorList");
                            if (elevatorList != null && elevatorList.size() >0) {
                                ChangeElevatorDialogFragment changeElevatorDialogFragment = ChangeElevatorDialogFragment.newInstance();
                                changeElevatorDialogFragment.setmDatas(elevatorList);
                                newProductNo = (String) response.getData().get("newProductNo");
                                changeElevatorDialogFragment.setNumber(newProductNo);
                                changeElevatorDialogFragment.callBack = ProductBeforeFragment.this;
                                changeElevatorDialogFragment.show(getActivity().getFragmentManager(), "change_elevator");
                            }else{
                                ToastUtil.showLong(getString(R.string.msg47));
                            }
                        }

                    });

        }
    }

    @Override
    public void confirm(String elevatorNo) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("conChangeGuid", getArguments().getString("contractChangeGuid"));
        jsonObject.addProperty("ProductGuid", productGuid);
        jsonObject.addProperty("elevatorNo", elevatorNo);
        jsonObject.addProperty("newProductNo", newProductNo);
        jsonObject.addProperty("userId", getUserId());
        baseResponseObservable = HttpFactory.getApiService().saveChangeElevator(jsonObject.toString(),getArguments().getInt("operation"));
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        EventBus.getDefault().post(new EventAction(EventAction.PRODUCT_CHANGE));
                        initData();
                        ToastUtil.showLong(getString(R.string.msg48));

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
        if (event.getCode() == EventAction.CONTRACT_CHANGE_DELETE) {
            initData();
        }
    }


}
