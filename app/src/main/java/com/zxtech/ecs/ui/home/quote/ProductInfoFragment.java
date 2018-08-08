package com.zxtech.ecs.ui.home.quote;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ProductInfoAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.model.ProductInfo;
import com.zxtech.ecs.model.ToolBean;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.ItemDivider;
import com.zxtech.ecs.widget.SelectDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp521 on 2018/3/31.
 */

public class ProductInfoFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recycleView_content)
    RecyclerView recycleViewContent;
    @BindView(R.id.recycleView_tool)
    RecyclerView recycleViewTool;

    private ProductInfoAdapter mAdapter;
    private ToolAdapter toolAdapter;
    private List<ProductInfo> lists = new ArrayList<>();
    private List<ToolBean> toolBeanLists = new ArrayList<>();
    //private String[] toolText = {"新建", "编辑", "修改", "复制", "升级", "召回", "土建", "装潢", "价格"};
    private String[] toolText;

    private String projectGuid;
    private String projectNo;

    public static ProductInfoFragment newInstance(String projectGuid, String projectNo) {
        Bundle args = new Bundle();
        ProductInfoFragment fragment = new ProductInfoFragment();
        args.putString("projectGuid", projectGuid);
        args.putString("projectNo", projectNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_info;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        toolText = new String[]{
                getString(R.string.new_),
                getString(R.string.edit),
                getString(R.string.modify),
                getString(R.string.copy),
                getString(R.string.upgrade),
                getString(R.string.callback),
                getString(R.string.layout_drawing),
                getString(R.string.rendering),
                getString(R.string.price_)
        };

        int[] colorfulArr = getResources().getIntArray(R.array.colorful);
        for (int a = 0; a < toolText.length; a++) {
            ToolBean bean = new ToolBean(toolText[a], colorfulArr[a % 6]);
            toolBeanLists.add(bean);
        }

        mAdapter = new ProductInfoAdapter(getContext(),R.layout.item_product_info, lists,true,false);
        recycleViewContent.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleViewContent.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);


        toolAdapter = new ToolAdapter(R.layout.item_round_textview, toolBeanLists);
        recycleViewTool.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleViewTool.addItemDecoration(new ItemDivider().setDividerWith(20).setDividerColor(Color.TRANSPARENT));
        recycleViewTool.setAdapter(toolAdapter);

        toolAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData();
    }

    private void initData() {

        if (isAdded()) {

            projectGuid = getArguments().getString("projectGuid");
            projectNo = getArguments().getString("projectNo");

            baseResponseObservable = HttpFactory.getApiService().getProductList(projectGuid, "","","0","");
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
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final Intent intent;
        List<Integer> selectList = mAdapter.getSelectList();
        ProductInfo productInfo = null;
        switch (position) {

            case 0: //新建
                intent = new Intent(getActivity(), NewProductActivity.class);
                intent.putExtra("projectGuid", projectGuid);
                intent.putExtra("projectNo", projectNo);

                List<String> names = new ArrayList<>();
                names.add(getString(R.string.elevator_));
                names.add(getString(R.string.escalator_));
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                intent.putExtra("elevatorType", Constants.ELEVATOR);
                                startActivityForResult(intent, 1001);
                                break;
                            case 1:
                                intent.putExtra("elevatorType", Constants.ESCALATOR);
                                startActivityForResult(intent, 1001);
                                break;
                        }

                    }
                }, names);

                break;
            case 1: //编辑

                if (selectList.size() != 1) {
                    ToastUtil.showLong(getString(R.string.toast1));
                    return;
                }
                productInfo = lists.get(selectList.get(0));
                if (productInfo.getIsConfirmVersion()) {
                    ToastUtil.showLong(getString(R.string.toast12));
                    return;
                }

                final HashMap<String, String> map1 = new HashMap<>();
                map1.put(Constants.CODE_ELEVATORTYPE, productInfo.getElevatorType());
                map1.put(Constants.CODE_ELEVATORPRODUCT, productInfo.getElevatorProduct());
                map1.put("TypeId", productInfo.getTypeId());
                map1.put(Constants.CODE_MACHINEROOM, productInfo.getIsMR());

                editProduct(map1, productInfo.getEQS_Guid());
                break;
            case 2://修改
                if (selectList.size() != 1) {
                    ToastUtil.showLong(getString(R.string.toast1));
                    return;
                }
                productInfo = lists.get(selectList.get(0));

                if (!"未询价".equals(productInfo.getNonState())) {
                    ToastUtil.showLong("产品已询价");
                    return;
                }

                intent = new Intent(getActivity(), NewProductActivity.class);
                intent.putExtra("projectGuid", projectGuid);
                intent.putExtra("projectNo", projectNo);

                intent.putExtra("elevatorType", productInfo.getTypeId());
                intent.putExtra("productInfo",productInfo);
                startActivityForResult(intent, 1001);
                break;
            case 3://复制
                if (selectList.size() != 1) {
                    ToastUtil.showLong(getString(R.string.toast1));
                    return;
                }
                productInfo = lists.get(selectList.get(0));

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                LayoutInflater inflater = getLayoutInflater();
                final View dialog_edit = inflater.inflate(R.layout.dialog_edit, null);
                final EditText et_userName =  dialog_edit.findViewById(R.id.content_et);
                et_userName.setInputType(InputType.TYPE_CLASS_NUMBER);
                final ProductInfo finalProductInfo = productInfo;
                builder.setView(dialog_edit).setTitle("复制产品个数")
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (TextUtils.isEmpty(et_userName.getText())) {
                                    ToastUtil.showLong(getString(R.string.msg29));
                                    return;
                                }

                                copyProduct(finalProductInfo.getEQS_Guid(), et_userName.getText().toString());
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

                break;
            case 4://升级
                if (selectList.size() != 1) {
                    ToastUtil.showLong(getString(R.string.toast1));
                    return;
                }
                productInfo = lists.get(selectList.get(0));

                upgradeProduct(productInfo.getEQS_Guid());
                break;
            case 5://召回
                if (selectList.size() != 1) {
                    ToastUtil.showLong(getString(R.string.toast1));
                    return;
                }
                productInfo = lists.get(selectList.get(0));
                if (TextUtils.isEmpty(productInfo.getInstanceNodeName())) {
                    ToastUtil.showLong("任务未发起流程无法召回");
                    return;
                }
                if ("流程结束".equals(productInfo.getInstanceNodeName())) {
                    ToastUtil.showLong("任务流程已结束无法召回");
                    return;
                }

                recallProduct(productInfo.getEQS_Guid());
                break;
            case 6://土建
                String s = "";
                if (selectList.size() == 0) {
                    ToastUtil.showLong(getString(R.string.toast1));
                    return;
                }
                for (int i = 0; i < selectList.size(); i++) {
                    s += lists.get(selectList.get(i)).getEQS_Guid();
                    if (i != (selectList.size() - 1)) {
                        s += ",";
                    }
                }
                intent = new Intent(getActivity(), DistributionActivity.class);
                intent.putExtra("data", s);
                intent.putExtra("projectGuid", getArguments().getString("projectGuid"));
                startActivity(intent);
                break;
            case 7://装潢
                if (selectList.size() != 1) {
                    ToastUtil.showLong(getString(R.string.toast1));
                    return;
                }
                final ProductInfo productInfo1 = lists.get(selectList.get(0));
                if (!productInfo1.getIsConfirmVersion()) {
                    ToastUtil.showLong("版本未确认，请确认版本");
                    return;
                }
                final HashMap<String, String> params = new HashMap<>();
                params.put("UserNo", getUserNo());
                params.put("UserName", getUserName());
                params.put("UserId", getUserId());
                params.put("EqsProductGuid", productInfo1.getEQS_Guid());

                createDesignDraw(new Gson().toJson(params));
                break;
            case 8://价格
                intent = new Intent(getActivity(), PriceListActivity.class);
                intent.putExtra("projectGuid", getArguments().getString("projectGuid"));
                startActivity(intent);
                break;
        }
    }

    private void copyProduct(String EQSGuid, String count) {
        baseResponseObservable = HttpFactory.getApiService().copyChildProduct(EQSGuid, count, this.projectGuid, this.projectNo, getUserId());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        initData();
                    }
                });
    }

    private void recallProduct(String EQSGuid) {
        baseResponseObservable = HttpFactory.getApiService().removeWorkFlow(EQSGuid, getUserNo(), getUserName());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        initData();
                    }
                });
    }

    private void editProduct(final HashMap<String, String> otherParam, final String EQSGuid) {
        baseResponseObservable = HttpFactory.getApiService().getProductParams(EQSGuid);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<Map<String, String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, String>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, String>> response) {
                        otherParam.putAll(response.getData());
                        Intent intent = new Intent(mContext, ProductEditActivity.class);
                        intent.putExtra("datas", otherParam);
                        intent.putExtra("productGuid", EQSGuid);
                        intent.putExtra("activity", ProductEditActivity.QUOTE_ACTIVITY);
                        startActivityForResult(intent, 1001);
                    }
                });
    }

    private void createDesignDraw(String params) {
        baseResponseObservable = HttpFactory.getApiService().applyDesignDraw(params);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        ToastUtil.showLong("申请成功");
                    }
                });
    }

    private void upgradeProduct(String productGuid) {
        baseResponseObservable = HttpFactory.getApiService().updatePriceVersionNum(productGuid, getUserId());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        initData();
                    }
                });
    }


    @OnClick({R.id.submit_btn})
    public void onClick(View view) {
        EventBus.getDefault().post(new EventAction<String>(EventAction.QUOTE_SUBMIT,projectGuid));

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1010) {
            initData();
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
        switch (view.getId()) {
            case R.id.column9:
                if (lists.get(position).getIsConfirmVersion()) {
                    ToastUtil.showLong("版本已确认");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("确认版本").setMessage(getString(R.string.toast11))
                            .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    confirmVersion(position);
                                }
                            }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }

                break;
        }
    }

    private void confirmVersion(final int position) {
        ProductInfo productInfo = lists.get(position);
        baseResponseObservable = HttpFactory.getApiService().confirmVersion(productInfo.getEQS_Guid());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        lists.get(position).setIsConfirmVersion("True");
                        mAdapter.notifyItemChanged(position);
                    }

                });
    }



     class ToolAdapter extends BaseQuickAdapter<ToolBean, BaseViewHolder> {

        public ToolAdapter(int layoutResId, @Nullable List<ToolBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ToolBean item) {
            helper.setText(R.id.text, item.getText());
            helper.setTextColor(R.id.text, item.getColor());
            GradientDrawable gd = new GradientDrawable();//创建drawable
            gd.setStroke(2, item.getColor());
            gd.setShape(GradientDrawable.OVAL);
            helper.getView(R.id.text).setBackgroundDrawable(gd);
        }
    }

}
