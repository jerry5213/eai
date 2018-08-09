package com.zxtech.is.ui.install.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.StringUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.MyItemDecoration;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.service.install.InstallService;
import com.zxtech.is.ui.install.adapter.InstallAdapter;
import com.zxtech.is.ui.taskme.activity.ScheduleManageListActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp688 on 4/30/2018.
 */
@Route(path = "/is/install")
public class InstallActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener, SearchView.OnQueryTextListener {
    private List<Map<String, Object>> mBeans = new ArrayList<>();
    private int pageNum = 1;
    private int pageSize = 10;
    private int totalCount = 0;
    private String taskDefKey;
    private InstallAdapter mAdapter;
    private String projectName;
    @BindView(R2.id.install_review_r_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R2.id.install_review_rv)
    RecyclerView mRecyclerView;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.searchView)
    SearchView mSearchView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_install_delivery;
    }

    public void onResume() {
        super.onResume();
        requestNet();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);
        //下拉刷新、上拉加载更多
        //设置该SearchView默认是否自动缩小为图标
        mSearchView.setIconifiedByDefault(false);
        //为该SearchView组件设置事件监听器
        mSearchView.setOnQueryTextListener(this);
        //设置该SearchView显示搜索按钮
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mContext, true);
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);
        android.widget.LinearLayout.LayoutParams textLayoutParams = (android.widget.LinearLayout.LayoutParams) textView.getLayoutParams();
        textLayoutParams.height = textLayoutParams.WRAP_CONTENT;
        textView.setLayoutParams(textLayoutParams);
//        textView.setTextSize(13);//字体、提示字体大小
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setDelegate(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //mRecyclerView.addItemDecoration(new MyItemDecoration());

        mAdapter = new InstallAdapter(R.layout.item_schedul_civilreview, mBeans);
        mAdapter.bindToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
        //载入调用刷新方法 在懒加载中onLazyInitView 调用请求

        // mRefreshLayout.beginRefreshing();


        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        pageNum = 1;
        requestNet();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (mAdapter.getData().size() >= totalCount) {
            mRefreshLayout.endLoadingMore();
            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_more_data));
        } else {
            pageNum++;
            requestNet();
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(mContext, ScheduleManageListActivity.class);
        Map<String, Object> map = mBeans.get(position);
        String guid = map.get("elevatorguid").toString();
        if (map.get("taskDefKey") == null) {
            taskDefKey = "is_scheduleManage_jf_wc";
        } else {
            taskDefKey = map.get("taskDefKey").toString();
        }
        intent.putExtra("guid", guid);
        intent.putExtra("taskDefKey", taskDefKey);
        intent.putExtra("iden", true);
        startActivity(intent);

    }

    private void requestNet() {
        InstallService installService = HttpFactory.getService(InstallService.class);
        installService.getInstallList(projectName, pageNum, pageSize)//测试以后要改为  scheduleManage
                .compose(RxHelper.<PageResponse<List<Map<String, Object>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<PageResponse<List<Map<String, Object>>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(PageResponse<List<Map<String, Object>>> response) {
                        if (pageNum == 1) { //如果page等于1时 为下拉刷新方法
                            if (response.getData().size() == 0) {
                                mBeans.clear();
                                mAdapter.notifyDataSetChanged();
                                ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_data));
                            } else {
                                mBeans.clear();
                                mBeans.addAll(response.getData());
                                mAdapter.notifyDataSetChanged();
                                mRefreshLayout.endRefreshing(); //停止刷新
                                totalCount = response.getRowCount();
                            }
                        } else {
                            mBeans.addAll(response.getData());
                            mAdapter.notifyDataSetChanged();
                            mRefreshLayout.endLoadingMore();//停止加载更多
                            totalCount = response.getRowCount();
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        mRefreshLayout.endRefreshing();
                    }
                });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        if (!TextUtils.isEmpty(query)) {
            projectName = query;
            requestNet();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        projectName = newText;
        if (TextUtils.isEmpty(newText)) {
            Log.i(TAG, newText);
            requestNet();
        }
        return false;
    }
}
