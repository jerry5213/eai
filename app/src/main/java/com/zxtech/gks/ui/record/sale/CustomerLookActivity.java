package com.zxtech.gks.ui.record.sale;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.vo.Customer;
import com.zxtech.gks.model.vo.PageParamBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by SYP521 on 2017/12/28.
 */

public class CustomerLookActivity extends BaseActivity implements IActivity, BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemChildClickListener {

    private int page = 1;
    private int totalCount = 0;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.new_btn)
    TextView new_btn;
    @BindView(R.id.edit_btn)
    TextView edit_btn;
    @BindView(R.id.delete_btn)
    TextView delete_btn;
    @BindView(R.id.edit_tv)
    TextView edit_tv;
    @BindView(R.id.delete_tv)
    TextView delete_tv;


    private List<Customer> mDatas = new ArrayList<>();
    private CustomerListAdapter mAdapter;
    private String customerName = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_look;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.search_customer));

        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration(1));

        mAdapter = new CustomerListAdapter(R.layout.item_customer_list, mDatas);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(this);

        mRefreshLayout.beginRefreshing();
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        if (mAdapter.getData().size() >= totalCount) {
            mRefreshLayout.endLoadingMore();
            ToastUtil.showLong(getString(R.string.toast3));
            return false;
        }
        loadMore();
        return true;
    }

    private Map getParams() {

        Map params = new HashMap();
        params.put("createUserId", getUserId());
        params.put("pageSize", APPConfig.PAGE_SIZE);
        params.put("customerName", customerName);
        return params;
    }

    private void refresh() {

        Map params = getParams();
        params.put("pageIndex", "1");

        baseResponseObservable = HttpFactory.getApiService().
                getCustomerByPage(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<PageParamBean<Customer>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<Customer>>>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<Customer>> response) {

                        mDatas.clear();
                        if (response.getData().getData2() != null) {
                            mDatas.addAll(response.getData().getData2());
                            mAdapter.notifyDataSetChanged();
                        }
                        mRefreshLayout.endRefreshing();
                        page = 1;
                        totalCount = response.getData().getTotalCount2();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        mRefreshLayout.endRefreshing();
                    }
                });
    }

    private void loadMore() {

        Map params = getParams();
        params.put("pageIndex", page + 1 + "");

        baseResponseObservable = HttpFactory.getApiService().
                getCustomerByPage(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<PageParamBean<Customer>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<Customer>>>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<Customer>> response) {

                        if (response.getData().getData2() != null) {
                            mDatas.addAll(response.getData().getData2());
                            mAdapter.notifyDataSetChanged();
                        }
                        mRefreshLayout.endLoadingMore();
                        page++;
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        mRefreshLayout.endLoadingMore();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            mRefreshLayout.beginRefreshing();
        }
    }


    @OnClick({R.id.new_btn, R.id.edit_btn, R.id.delete_btn, R.id.save_btn})
    public void onClick(View view) {
        Intent intent = null;
        Customer beanData = null;
        switch (view.getId()) {
            case R.id.new_btn:
                intent = new Intent(this, EditCustomerActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.edit_btn:
                beanData = mAdapter.getData().get(mAdapter.getSelectedPosition());
                intent = getIntent();
                intent.setClass(this, EditCustomerActivity.class);
                intent.putExtra("action", "modify");
                intent.putExtra(Constants.DATA1, beanData);
                startActivityForResult(intent, 1);
                break;
            case R.id.delete_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("确定删除吗？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doDelete(mAdapter.getSelectedPosition());
                    }
                });
                builder.show();
                break;
            case R.id.save_btn:
                if (mAdapter.getSelectedPosition() == -1) {
                    return;
                }
                beanData = mAdapter.getData().get(mAdapter.getSelectedPosition());
                intent = getIntent();
                intent.putExtra(Constants.DATA1, beanData);
                setResult(1, intent);
                finish();
                break;
        }
    }

    protected void doDelete(final int position) {

        Customer beanData = mAdapter.getData().get(position);
        baseResponseObservable = HttpFactory.getApiService().
                deleteCustomer(beanData.getGuid());
        baseResponseObservable
                .compose(RxHelper.<BasicResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse response) {

                        ToastUtil.showLong("删除成功");
                        mAdapter.getData().remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mAdapter.setSelectedPosition(position);
        edit_btn.setEnabled(true);
        edit_btn.setTextColor(getResources().getColor(R.color.yellow));
        edit_tv.setTextColor(getResources().getColor(R.color.yellow));
        delete_btn.setEnabled(true);
        delete_btn.setTextColor(getResources().getColor(R.color.main));
        delete_tv.setTextColor(getResources().getColor(R.color.main));
    }

}
