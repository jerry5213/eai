package com.zxtech.ecs.ui.home.follow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by chw on 2017/10/24.
 */

public class FollowActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate,BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.no_item)
    TextView no_item;


    public static final int REQUEST_SEARCH = 0X02;
    private String search_proj_name = "", search_proj_no = "", search_customer = "", search_status = "";
    private FollowListAdapter adapter;
    private int page = 1;
    private int totalCount = 0;
    private List<PrProduct> mDatas = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.project_tracking));

        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mContext, true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.addItemDecoration(new MyItemDecoration(15));
        adapter = new FollowListAdapter(R.layout.item_follow_list, mDatas);

        recycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }



    private void initData() {
        mRefreshLayout.beginRefreshing();
    }

    private Map getParams() {

        String deptNo = getUserDeptNo();
        String hasGkDeptNo = "GKLN";//演示写死
//        if (!TextUtils.isEmpty(deptNo)) {
//            if (!deptNo.contains(",")) {
//                hasGkDeptNo = deptNo;
//            } else {
//                String[] arr = deptNo.split(",");
//                for (String s : arr) {
//                    if (s.contains("GK")) {
//                        hasGkDeptNo = s;
//                    }
//                }
//            }
//        }

        Map params = new HashMap();
        params.put("UserNo", getUserNo());
        params.put("RoleNo", getRoleNo());
        params.put("DeptNo", hasGkDeptNo);
        params.put("ProjectNo", search_proj_no);
        params.put("ProjectName", search_proj_name);
        params.put("CustomerName", search_customer);
        params.put("WorkFlowNodeName", search_status);
        params.put("PageSize", APPConfig.PAGE_SIZE);
        return params;
    }

    private void loadMore() {

        Map params = getParams();
        params.put("PageIndex", page + 1 + "");


        baseResponseObservable = HttpFactory.getApiService().
                getAllProjectListByPage(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<PageParamBean<PrProduct>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<PrProduct>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<PrProduct>> response) {
                        adapter.getData().addAll(response.getData().getData());
                        adapter.notifyDataSetChanged();
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

    private void refesh() {

        Map params = getParams();
        params.put("PageIndex", "1");

        baseResponseObservable = HttpFactory.getApiService().
                getAllProjectListByPage(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<PageParamBean<PrProduct>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<PrProduct>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<PrProduct>> response) {
                        adapter.getData().clear();
                        adapter.getData().addAll(response.getData().getData());
                        adapter.notifyDataSetChanged();
                        mRefreshLayout.endRefreshing();
                        page = 1;
                        totalCount = response.getData().getTotalCount();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        mRefreshLayout.endLoadingMore();
                    }
                });

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refesh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (adapter.getData().size() >= totalCount) {
            mRefreshLayout.endLoadingMore();
            ToastUtil.showLong(getString(R.string.toast3));
            return false;
        }
        loadMore();
        return true;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.menu_search2, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_search) {
//
//            Intent intent = new Intent(mContext, FollowSearchActivity.class);
//            intent.putExtra("search_proj_no", search_proj_no);
//            intent.putExtra("search_proj_name", search_proj_name);
//            intent.putExtra("search_customer", search_customer);
//            intent.putExtra("search_status", search_status);
//            startActivityForResult(intent, REQUEST_SEARCH);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SEARCH && resultCode == 1) {
            this.search_proj_no = data.getStringExtra("search_proj_no");
            this.search_proj_name = data.getStringExtra("search_proj_name");
            this.search_customer = data.getStringExtra("search_customer");
            this.search_status = data.getStringExtra("search_status");
            mRefreshLayout.beginRefreshing();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String id = this.adapter.getData().get(position).getProjectInstanceGuid();
        Intent intent = new Intent(mContext, WorkNodeListActivity.class);
        intent.putExtra("taskId", id);
        startActivity(intent);
    }
}
