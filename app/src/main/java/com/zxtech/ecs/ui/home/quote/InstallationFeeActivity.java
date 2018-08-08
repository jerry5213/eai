package com.zxtech.ecs.ui.home.quote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/5/15.
 */

public class InstallationFeeActivity extends BaseActivity {
    @BindView(R.id.content_recycleview)
    RecyclerView content_recycleview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private List<Detail> mDatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_installationfee_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.details_of_installation_fee));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        content_recycleview.setLayoutManager(linearLayoutManager);

        MyAdapter mAdapter = new MyAdapter(R.layout.item_quote_installationfee, mDatas);
        content_recycleview.setAdapter(mAdapter);

        initData();
    }

    private void initData() {
        mDatas.add(new Detail("17483",getString(R.string.installation_management_fee),getString(R.string.basic_price)));
        mDatas.add(new Detail("17484",getString(R.string.installation_commission_fee),"3100"));
        mDatas.add(new Detail("17485",getString(R.string.fees_for_unloading_and_hoisting),"3105"));
        mDatas.add(new Detail("17486",getString(R.string.fees_for_scaffolding),"3200"));
        String guid = getIntent().getStringExtra("EQSProductGuid");
        baseResponseObservable = HttpFactory.getApiService().getInstallation(guid);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<Map<String,String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String,String>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String,String>> response) {
                        Map<String, String> responseData = response.getData();
                        mDatas.get(0).setSalePrice(responseData.get("ManagementCostForSale_Inst"));
                        mDatas.get(0).setBasicPrice(responseData.get("ManagementCost_Inst"));
                        mDatas.get(1).setSalePrice(responseData.get("EntrustCostForSale_Inst"));
                        mDatas.get(1).setBasicPrice(responseData.get("EntrustCost_Inst"));
                        mDatas.get(2).setSalePrice(responseData.get("HoistingCostForSale"));
                        mDatas.get(2).setBasicPrice(responseData.get("HoistingCost"));
                        mDatas.get(3).setSalePrice(responseData.get("ScaffoldCostForSale"));
                        mDatas.get(3).setBasicPrice(responseData.get("ScaffoldCost"));
                        content_recycleview.getAdapter().notifyDataSetChanged();
                    }

                });
    }


    class MyAdapter extends BaseQuickAdapter<Detail, BaseViewHolder> {


        public MyAdapter(int layoutResId, @Nullable List<Detail> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Detail item) {
            helper.setText(R.id.id_tv, item.getId());
            helper.setText(R.id.name_tv, item.getName());
            helper.setText(R.id.alias_tv, item.getAlias());
            helper.setText(R.id.sale_price_tv, item.getSalePrice());
            helper.setText(R.id.basic_price_tv, item.getBasicPrice());
        }
    }

    class Detail {
        private String id;
        private String name;
        private String alias;
        private String salePrice;
        private String basicPrice;

        public Detail() {
        }

        public Detail(String id, String name, String alias) {
            this.id = id;
            this.name = name;
            this.alias = alias;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
        }

        public String getBasicPrice() {
            return basicPrice;
        }

        public void setBasicPrice(String basicPrice) {
            this.basicPrice = basicPrice;
        }
    }
}
