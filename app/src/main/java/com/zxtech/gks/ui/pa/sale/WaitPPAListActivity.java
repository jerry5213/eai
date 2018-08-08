package com.zxtech.gks.ui.pa.sale;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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
import com.zxtech.gks.model.bean.SaveResult;
import com.zxtech.gks.model.vo.PageParamBean;
import com.zxtech.gks.model.vo.PrProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by SYP521 on 2017/10/24.
 */

public class WaitPPAListActivity extends BaseActivity implements IActivity, BGARefreshLayout.BGARefreshLayoutDelegate{

    private WaitPPAListAdapter adapter;
    private int page = 1;
    private int totalCount = 0;
    private List<PrProduct> mDatas = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sale_price_approve_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar);

        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());

        adapter = new WaitPPAListAdapter(this,R.layout.item_sale_ppa_list,mDatas);
        recyclerView.setAdapter(adapter);

        mRefreshLayout.beginRefreshing();
    }

    private Map getParams(){

        Map params = new HashMap();
        params.put("TransactUserNo",getUserNo());
        params.put("PageSize", APPConfig.PAGE_SIZE);
        return params;
    }

    private void loadMore(){

        Map params = getParams();
        params.put("PageIndex",page+1+"");

        baseResponseObservable = HttpFactory.getApiService().
                getPRIndexNotSubmitByPage(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<PageParamBean<PrProduct>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<PrProduct>>>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<PrProduct>> response) {

                        if(response.getData().getData() != null){
                            mDatas.addAll(response.getData().getData());
                            adapter.notifyDataSetChanged();
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

    private void refesh(){

        Map params = getParams();
        params.put("PageIndex","1");

        baseResponseObservable = HttpFactory.getApiService().
                getPRIndexNotSubmitByPage(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<PageParamBean<PrProduct>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<PrProduct>>>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<PrProduct>> response) {

                        mDatas.clear();
                        if(response.getData().getData() != null){
                            mDatas.addAll(response.getData().getData());
                            adapter.notifyDataSetChanged();
                        }
                        mRefreshLayout.endRefreshing();
                        page=1;
                        totalCount = response.getData().getTotalCount();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        mRefreshLayout.endRefreshing();
                    }
                });
    }

    public void savePriceReview(final int position){

        PrProduct dataBean = mDatas.get(position);

        Map params = new HashMap();
        params.put("projectGuid",dataBean.getProjectGuid());
        params.put("userId",getUserId());
        params.put("userNo",getUserNo());
        params.put("userName",getUserName());

        baseResponseObservable = HttpFactory.getApiService().
                savePriceReview(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<SaveResult>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<SaveResult>>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse<SaveResult> response) {

                        if(response.getData().isResults()){
                            ToastUtil.showLong("提交成功");
                            adapter.getDatas().remove(position);
                            adapter.notifyDataSetChanged();
                        }else{
                            String info = response.getData().getPostInfo();
                            info = info.replace("u003c/bru003e","");
                            showDialog(info);
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    private void showDialog(String msg){
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确定",null);
        builder.show();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refesh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        if(adapter.getDatas().size()>=totalCount){
            mRefreshLayout.endLoadingMore();
            ToastUtil.showLong(getString(R.string.toast3));
            return false;
        }
        loadMore();
        return true;
    }
}
