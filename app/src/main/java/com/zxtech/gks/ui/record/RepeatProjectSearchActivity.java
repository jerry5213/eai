package com.zxtech.gks.ui.record;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.DropDownBean;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.gks.model.vo.PageParamBean;
import com.zxtech.gks.model.vo.RecordApproval;

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
 * Created by syp523 on 2017/12/4.
 */

public class RepeatProjectSearchActivity extends BaseActivity implements CloseListener {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rl_refresh)
    BGARefreshLayout rl_refresh;
    @BindView(R.id.customer_et)
    EditText customer_et;
    @BindView(R.id.proj_name_et)
    EditText proj_name_et;
    @BindView(R.id.search_type_tv)
    TextView search_type_tv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RepeatProjectAdapter mAdapter;
    private List<RecordApproval> mData = null;
    private int page = 1;
    private int totalCount = 0;
    private String projectName;
    private String projectId;
    private String search_type = "and";

    private DropDownWindow mWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repeat_project;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.find_duplicate_items));
        Intent intent = getIntent();
        projectName = intent.getStringExtra("projectName");
        projectId = intent.getStringExtra("projectId");
        proj_name_et.setText(projectName);
        proj_name_et.setSelection(proj_name_et.getText().length());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(new MyItemDecoration(15));

        BGARefreshViewHolder rv1 = new BGANormalRefreshViewHolder(this, true);
        rl_refresh.setRefreshViewHolder(rv1);
        mData = new ArrayList<>();
        mAdapter = new RepeatProjectAdapter(mContext, mData, R.layout.item_repeat_project);
        mAdapter.setListener(this);

        rv.setAdapter(mAdapter);
        rl_refresh.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(final BGARefreshLayout refreshLayout) {
                refesh();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(final BGARefreshLayout refreshLayout) {

                return false;
            }
        });
        rl_refresh.beginRefreshing();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_search){
            rl_refresh.beginRefreshing();
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void loadMore() {

        baseResponseObservable = HttpFactory.getApiService().
                getRepeatProjectByPage(proj_name_et.getText().toString(),customer_et.getText().toString(),search_type,getUserDeptNo());
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<PageParamBean<RecordApproval>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<RecordApproval>>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<RecordApproval>> response) {

                        page++;
                        mData.addAll(response.getData().getData());
                        mAdapter.notifyDataSetChanged();
                        rl_refresh.endLoadingMore();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        rl_refresh.endRefreshing();
                    }
                });

    }

    private void refesh() {

        baseResponseObservable = HttpFactory.getApiService().
                getRepeatProjectByPage(proj_name_et.getText().toString(),customer_et.getText().toString(),search_type,getUserDeptNo());
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<PageParamBean<RecordApproval>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<RecordApproval>>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<RecordApproval>> response) {

                        page = 1;
                        mData.clear();
                        mData.addAll(response.getData().getData());
                        mAdapter.notifyDataSetChanged();
                        rl_refresh.endRefreshing();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        rl_refresh.endRefreshing();
                    }
                });
    }

    private Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("PageSize", APPConfig.PAGE_SIZE+"");
        params.put("ProjectName", projectName);
        params.put("DeptNo", getUserDeptNo());
        params.put("CustomerName", customer_et.getText().toString());
        params.put("RelationType", search_type);
        return params;
    }

    @OnClick({R.id.search_type_tv,R.id.tv_search})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.search_type_tv:
                final List<DropDownBean> list = new ArrayList<>();
                if (search_type_tv.getText().equals("与")){
                    list.add(new DropDownBean("or", "或"));
                }else{
                    list.add(new DropDownBean("and ", "与"));
                }

                mWindow = new DropDownWindow(mContext, search_type_tv, list, search_type_tv.getWidth(), -2) {

                    @Override
                    public void initEvents(int p) {
                        search_type = list.get(p).getKey();
                        search_type_tv.setText(list.get(p).getValue());
                        mWindow.dismiss();
                    }

                    @Override
                    public void dismissEvents() {

                    }
                };
                break;
            case R.id.tv_search:
                rl_refresh.beginRefreshing();
                break;
        }
    }


    @Override
    public void closed(final RecordApproval recordApproval, final int position) {
        if (!recordApproval.isCloseFlag()) { //true 可以关闭，false 相反
            ToastUtil.showLong("该项目无权限操作");
            return;
        }
        if (recordApproval.isSAPClose()) { //false 可以关闭，true 相反
            ToastUtil.showLong("该项目无权限操作");
            return;
        }

        baseResponseObservable = HttpFactory.getApiService().
                closeProject(recordApproval.getProjectGuid(),getUserId());
        baseResponseObservable
                .compose(RxHelper.<BasicResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {

                        ToastUtil.showLong("操作成功");
                        if (projectId != null && projectId.equals(recordApproval.getProjectGuid())) {
                            setResult(10086);
                            finish();
                        }else{
                            mData.remove(position);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }
}
