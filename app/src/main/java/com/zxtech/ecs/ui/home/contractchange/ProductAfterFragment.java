package com.zxtech.ecs.ui.home.contractchange;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ProductInfoAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.event.EventFloorStation;
import com.zxtech.ecs.model.ProductInfo;
import com.zxtech.ecs.model.ToolBean;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.quote.PriceListActivity;
import com.zxtech.ecs.ui.home.quote.ProductEditActivity;
import com.zxtech.ecs.ui.home.quote.ProductInfoFragment;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.ItemDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/6/27.
 */

public class ProductAfterFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycleView_tool)
    RecyclerView recycleViewTool;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private ToolAdapter toolAdapter;
    private ProductInfoAdapter mAdapter;

    private List<ToolBean> toolBeanLists = new ArrayList<>();
    private List<ProductInfo> lists = new ArrayList<>();

    private String projectGuid;
    private String place;
    private boolean isEdit;

    private String[] toolText = null;

    public static ProductAfterFragment newInstance(String projectGuid, String projectNo, String contractId, String contractChangeGuid, boolean isEdit) {
        Bundle args = new Bundle();
        ProductAfterFragment fragment = new ProductAfterFragment();
        args.putString("projectGuid", projectGuid);
        args.putString("projectNo", projectNo);
        args.putString("contractId", contractId);
        args.putString("contractChangeGuid", contractChangeGuid);
        args.putBoolean("isEdit", isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_after;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        isEdit = getArguments().getBoolean("isEdit");
        projectGuid = getArguments().getString("projectGuid");

        toolText = new String[]{getString(R.string.edit), getString(R.string.parameters_before), getString(R.string.parameters_after), getString(R.string.service_fee_before), getString(R.string.parameter_specifications), getString(R.string.other_fees)};
        int[] colorfulArr = getResources().getIntArray(R.array.colorful);
        for (int i = 0; i < toolText.length; i++) {
            ToolBean toolBean = new ToolBean(toolText[i], colorfulArr[i % 6]);
            toolBeanLists.add(toolBean);
        }
        toolAdapter = new ToolAdapter(R.layout.item_corner_radius_textview, toolBeanLists);
        recycleViewTool.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleViewTool.addItemDecoration(new ItemDivider().setDividerWith(20).setDividerColor(Color.TRANSPARENT));
        recycleViewTool.setAdapter(toolAdapter);
        toolAdapter.setOnItemClickListener(this);

        mAdapter = new ProductInfoAdapter(getContext(), R.layout.item_product_info, lists, isEdit, false);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_view.setAdapter(mAdapter);


    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData(true);
    }


    private void initData(boolean init) {

        Bundle arguments = getArguments();

        baseResponseObservable = HttpFactory.getApiService().getProductList(projectGuid, "", arguments.getString("contractChangeGuid"), "-3", "1");
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<ProductInfo>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ProductInfo>>>(getActivity(), init) {
                    @Override
                    public void onSuccess(BaseResponse<List<ProductInfo>> response) {

                        lists.clear();
                        lists.addAll(response.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!isEdit) {
            return;
        }
        List<Integer> selectList = mAdapter.getSelectList();
        ProductInfo productInfo = null;
        switch (position) {
            case 0://编辑
                if (selectList.size() != 1) {
                    ToastUtil.showLong(getString(R.string.msg46));
                    return;
                }
                productInfo = lists.get(selectList.get(0));
                final HashMap<String, String> map1 = new HashMap<>();
                map1.put(Constants.CODE_ELEVATORTYPE, productInfo.getElevatorType());
                map1.put(Constants.CODE_ELEVATORPRODUCT, productInfo.getElevatorProduct());
                map1.put("TypeId", productInfo.getTypeId());
                map1.put(Constants.CODE_MACHINEROOM, productInfo.getIsMR());

                editProduct(map1, productInfo.getEQS_Guid());
                break;
        }
    }

    private void editProduct(final HashMap<String, String> otherParam, final String EQSGuid) {
        baseResponseObservable = HttpFactory.getApiService().getProductParams(EQSGuid);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<Map<String, String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, String>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, String>> response) {
                        otherParam.putAll(response.getData());
                        Intent intent = new Intent(mContext, ProductEditActivity.class);
                        intent.putExtra("datas", otherParam);
                        intent.putExtra("productGuid", EQSGuid);
                        intent.putExtra("projectGuid", projectGuid);
                        intent.putExtra("place", place);
                        intent.putExtra("contractChangeGuid", getArguments().getString("contractChangeGuid"));
                        intent.putExtra("activity", ProductEditActivity.CONTRACT_CHANGE_ACTIVITY);
                        startActivityForResult(intent, 1001);
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventAction event) {
        if (event.getCode() == EventAction.PRODUCT_CHANGE) {
            initData(false);
        } else if (event.getCode() == EventAction.CONTRACT_CHANGE_PLACE) {
            place = (String) event.getData();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1010) {
            EventBus.getDefault().post(new EventAction(EventAction.CONTRACT_CHANGE));
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    class ToolAdapter extends BaseQuickAdapter<ToolBean, BaseViewHolder> {
        private Integer selectedPosition;

        public ToolAdapter(int layoutResId, @Nullable List<ToolBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ToolBean item) {
            helper.setText(R.id.text, item.getText());

            GradientDrawable gd = new GradientDrawable();//创建drawable
            gd.setStroke(2, item.getColor());
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setCornerRadius(8);
            if (selectedPosition != null && selectedPosition == helper.getAdapterPosition()) {
                gd.setColor(item.getColor());
                helper.setTextColor(R.id.text, getResources().getColor(R.color.white));
            } else {
                gd.setColor(GradientDrawable.RECTANGLE);
                helper.setTextColor(R.id.text, item.getColor());
            }
            helper.getView(R.id.text).setBackgroundDrawable(gd);
        }

        public Integer getSelectedPosition() {
            return selectedPosition;
        }

        public void clearSelectedPosition() {
            selectedPosition = null;
            this.notifyDataSetChanged();
        }

        public void setSelectedPosition(Integer selectedPosition) {
            this.selectedPosition = selectedPosition;
            this.notifyDataSetChanged();
        }
    }
}
