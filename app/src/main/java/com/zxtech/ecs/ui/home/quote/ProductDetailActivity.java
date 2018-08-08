package com.zxtech.ecs.ui.home.quote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ProductDetail;
import com.zxtech.ecs.model.ProductInfo;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.StringUtils;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/5/15.
 */

public class ProductDetailActivity extends BaseActivity {
    @BindView(R.id.content_recycleview)
    RecyclerView content_recycleview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private List<ProductDetail.SysEQSListBean> mDatas = new ArrayList<>();
    private MyAdapter mAdapter;
    private TextView total_tv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        content_recycleview.setLayoutManager(linearLayoutManager);
        mAdapter = new MyAdapter(R.layout.item_quote_product_detail, mDatas);
        View footerView = LayoutInflater.from(mContext).inflate(R.layout.footer_product_detail, null);
        total_tv = footerView.findViewById(R.id.total_tv);
        mAdapter.addFooterView(footerView);
        content_recycleview.setAdapter(mAdapter);

        initData();
    }

    private void initData() {
        String guid = getIntent().getStringExtra("guid");
        baseResponseObservable = HttpFactory.getApiService().getEquipmentPriceList(guid);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<ProductDetail>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<ProductDetail>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<ProductDetail> response) {
                        mDatas.addAll(response.getData().getSysEQSList());
                        mAdapter.notifyDataSetChanged();
                        calculateTotal();
                    }

                });
    }


    private void calculateTotal() {
        int total = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            int salePrice = mDatas.get(i).getSalePrice();
            total += salePrice;
        }
        total_tv.setText(Util.numberFormat(total));
    }


    class MyAdapter extends BaseQuickAdapter<ProductDetail.SysEQSListBean, BaseViewHolder> {


        public MyAdapter(int layoutResId, @Nullable List<ProductDetail.SysEQSListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProductDetail.SysEQSListBean item) {
            helper.setText(R.id.title_tv, "系统报价");
            helper.setText(R.id.name_tv, item.getPriceName());
            helper.setText(R.id.price_tv, Util.numberFormat(item.getSalePrice()));

        }
    }
}
