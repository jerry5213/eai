package com.zxtech.is.ui.Engineer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.MyItemDecoration;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.s1.IsPlanS1;
import com.zxtech.is.service.engineer.EngineerService;
import com.zxtech.is.ui.Engineer.adapter.EngineerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

import static com.zxtech.is.adapter.ExpandableItemAdapter.TYPE_LEVEL_0;
import static com.zxtech.is.adapter.ExpandableItemAdapter.TYPE_LEVEL_1;

/**
 * Created by syp688 on 5/2/2018.
 */

@Route(path = "/is/engineer")
public class EngineerActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener {
    private List<Map<String, Object>> mBeans = new ArrayList<>();
    private EngineerAdapter engineerAdapter;
    private RecyclerView detail_rv;
    private int pageNum = 1;
    private int pageSize = 5;
    private int totalCount = 0;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.engineering_refresh)
    BGARefreshLayout mRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.civil_engineering_review;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        if (engineerAdapter.getData().size() >= totalCount) {
            mRefreshLayout.endLoadingMore();
            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_more_data));
        } else {
            pageNum++;
            loadMore();
            mRefreshLayout.endLoadingMore();
            return true;
        }
        return false;

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mContext, true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setDelegate(this);
        detail_rv = (RecyclerView) findViewById(R.id.civil_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        detail_rv.setLayoutManager(linearLayoutManager);
        mRefreshLayout.beginRefreshing();
    }

    private void loadData() {
        EngineerService engineerService = HttpFactory.getService(EngineerService.class);
        engineerService.getEngineerList(pageNum, pageSize).compose(RxHelper.<PageResponse<List<Map<String, Object>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<PageResponse<List<Map<String, Object>>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(PageResponse<List<Map<String, Object>>> response) {
                        if (response.getData().size() == 0) {
                            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_data));
                        } else {
                            mBeans.clear();
                            mBeans.addAll(response.getData());
                        }
                        engineerAdapter = new EngineerAdapter(generateData(response.getData()));
                        detail_rv.addItemDecoration(new MyItemDecoration(1));
                        detail_rv.setAdapter(engineerAdapter);
                        engineerAdapter.setOnItemClickListener(EngineerActivity.this);
                        mRefreshLayout.endRefreshing();
                        engineerAdapter.notifyDataSetChanged();
                    }

                });
    }

    private void loadMore() {
        EngineerService engineerService = HttpFactory.getService(EngineerService.class);
        engineerService.getEngineerList(pageNum, pageSize).compose(RxHelper.<PageResponse<List<Map<String, Object>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<PageResponse<List<Map<String, Object>>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(PageResponse<List<Map<String, Object>>> response) {
                        if (response.getData().size() == 0) {
                            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_data));
                        } else {
                            mBeans.clear();
                            mBeans.addAll(response.getData());
                        }
                        engineerAdapter = new EngineerAdapter(generateData(response.getData()));
                        detail_rv.addItemDecoration(new MyItemDecoration(1));
                        detail_rv.setAdapter(engineerAdapter);
                        engineerAdapter.setOnItemClickListener(EngineerActivity.this);
                        engineerAdapter.notifyDataSetChanged();
                        mRefreshLayout.endLoadingMore();
                    }

                });
    }


    private ArrayList<MultiItemEntity> generateData(List<Map<String, Object>> list) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            IsPlanS1.IsPlanPro isPlanPro = new IsPlanS1.IsPlanPro();
            isPlanPro.setPrjId(list.get(i).get("prjId").toString());
            isPlanPro.setPrjName(list.get(i).get("prjName").toString());
            isPlanPro.setParams((List<IsPlanS1.IsPlanPro.IsProEle>) list.get(i).get("eleList"));
            for (int j = 0; j < isPlanPro.getParams().size(); j++) {
                Map<String, Object> objectMap = (Map<String, Object>) isPlanPro.getParams().get(j);
                IsPlanS1.IsPlanPro.IsProEle item = new IsPlanS1.IsPlanPro.IsProEle();
                item.setEleId(objectMap.get("eleId").toString());
                item.setProjectId(objectMap.get("prjId").toString());
                item.setProcInstId(objectMap.get("procinstid").toString());
                if (objectMap.get("eleName") != null) {
                    item.setEleName(objectMap.get("eleName").toString());
                }
                if (objectMap.get("confirmTime") != null) {
                    item.setConfirmTime(objectMap.get("confirmTime").toString());
                }
                if (objectMap.get("confirmUsr") != null) {
                    item.setUserName(objectMap.get("confirmUsr").toString());
                }
                if (objectMap.get("endTime") != null) {
                    item.setStatus(getResources().getString(R.string.is_have_done));
                } else {
                    item.setStatus(getResources().getString(R.string.is_process_done));
                }
                isPlanPro.addSubItem(item);
            }
            res.add(isPlanPro);
        }
        return res;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (adapter.getItemViewType(position)) {
            case TYPE_LEVEL_0:
                IsPlanS1.IsPlanPro testParent = (IsPlanS1.IsPlanPro) adapter.getItem(position);
                if (testParent.isExpanded()) {
                    //合并
                    engineerAdapter.collapse(position, true);
                    testParent.setExpanded(false);

                } else {
                    //展开
                    engineerAdapter.expand(position, true);
                    testParent.setExpanded(true);

                }
                break;
            case TYPE_LEVEL_1:
                IsPlanS1.IsPlanPro.IsProEle testChild = (IsPlanS1.IsPlanPro.IsProEle) adapter.getItem(position);
                Intent intent = new Intent(mContext, CommittedTaskDetailActivity.class);
                intent.putExtra("eleGuid", testChild.getEleId());
                intent.putExtra("projectId", testChild.getProjectId());
                intent.putExtra("procDefKey", "s1");
                intent.putExtra("procInstId", testChild.getProcInstId());
                startActivity(intent);
                break;
        }
    }
}
