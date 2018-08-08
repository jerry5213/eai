package com.zxtech.gks.ui.record;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.gks.model.vo.PageParamBean;
import com.zxtech.gks.model.vo.RecordApproval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

public class RecordApprovalActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rl_refresh)
    BGARefreshLayout rl_refresh;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ApprovalAdapter mAdapter;
    private List<RecordApproval> mData = new ArrayList<>();

    public static final int REQUEST_DETAIL = 0X01;
    public static final int REQUEST_SEARCH = 0X02;

    private String search_proj_name = "",search_proj_no = "",search_customer = "";
    private boolean search_overdue;
    private int page = 1 ;
    private int selectedPosition = 0;
    private int totalCount = 0;

   /* protected void showSetting(String text) {

        setting_tv.setText("");
        setting_tv.setVisibility(View.GONE);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_search_strange);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        setting_tv.setCompoundDrawables(null,null,drawable,null);
    }*/

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_approval;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.project_approval));
        BGARefreshViewHolder rv1 = new BGANormalRefreshViewHolder(this, true);
        rl_refresh.setRefreshViewHolder(rv1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(new MyItemDecoration(15));

        mAdapter = new ApprovalAdapter(mContext,mData,R.layout.item_record_appr);
        rv.setAdapter(mAdapter);
        rl_refresh.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(final BGARefreshLayout refreshLayout) {
                refesh();
            }


            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(final BGARefreshLayout refreshLayout) {
                if (mData.size() >= totalCount) {
                    rl_refresh.endLoadingMore();
                    ToastUtil.showLong(getString(R.string.toast3));
                    return false;
                }
                loadMore();
                return true;
            }
        });

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                selectedPosition = position;
                RecordApproval recordApproval = mData.get(position);
                Intent intent = new Intent(mContext,RecordApprovalDetailActivity.class);
                intent.putExtra("id",recordApproval.getProjectGuid());
                startActivityForResult(intent,REQUEST_DETAIL);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rl_refresh.beginRefreshing();
    }

    private Map getParams(){

        Map params = new HashMap();
        params.put("PageSize", APPConfig.PAGE_SIZE);
        params.put("TransactUserNo",getUserNo());
        params.put("ProjectNo",search_proj_no);
        params.put("ProjectName",search_proj_name);
        params.put("CustomerName",search_customer);
        if (search_overdue) {
            params.put("OrderFlag","OutDate");
        }else{
            params.put("OrderFlag","");
        }
        return params;
    }


    private void loadMore(){

        Map params = getParams();
        params.put("PageIndex", String.valueOf(page+1));
        baseResponseObservable = HttpFactory.getApiService().
                getTodoProjectByPage(params);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<PageParamBean<RecordApproval>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<RecordApproval>>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<RecordApproval>> response) {

                        mData.addAll(response.getData().getData());
                        mAdapter.notifyDataSetChanged();
                        rl_refresh.endLoadingMore();
                        page++;
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        rl_refresh.endLoadingMore();
                    }
                });
    }

    private void refesh(){

        Map params = getParams();
        params.put("PageIndex","1");
        baseResponseObservable = HttpFactory.getApiService().
                getTodoProjectByPage(params);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<PageParamBean<RecordApproval>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<RecordApproval>>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<RecordApproval>> response) {

                        mData.clear();
                        mData.addAll(response.getData().getData());
                        mAdapter.notifyDataSetChanged();
                        rl_refresh.endRefreshing();
                        page = 1;
                        totalCount = response.getData().getTotalCount();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        rl_refresh.endRefreshing();
                    }
                });
    }


    /*@Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.setting_tv:
                Intent intent = new Intent(mContext,RecordApprovalSearchActivity.class);
                intent.putExtra("search_proj_no",search_proj_no);
                intent.putExtra("search_proj_name",search_proj_name);
                intent.putExtra("search_customer",search_customer);
                intent.putExtra("search_overdue",search_overdue);
                startActivityForResult(intent,REQUEST_SEARCH);
                break;
        }
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SEARCH && resultCode == 1) {
            this.search_proj_no = data.getStringExtra("search_proj_no");
            this.search_proj_name = data.getStringExtra("search_proj_name");
            this.search_customer = data.getStringExtra("search_customer");
            this.search_overdue=  data.getBooleanExtra("search_overdue",false);
            rl_refresh.beginRefreshing();
        }
        if (resultCode == 1001) {
            mData.remove(selectedPosition);
            mAdapter.notifyDataSetChanged();
        }
    }
}
