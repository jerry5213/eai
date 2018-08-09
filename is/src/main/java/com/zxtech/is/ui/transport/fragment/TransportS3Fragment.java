package com.zxtech.is.ui.transport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.s1.IsPlanS1;
import com.zxtech.is.service.engineer.EngineerService;
import com.zxtech.is.ui.Engineer.activity.CommittedTaskDetailActivity;
import com.zxtech.is.ui.transport.adapter.TransportAdapter;

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
 * Created by syp660 on 2018/4/19.
 */

public class TransportS3Fragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private List<Map<String, Object>> mBeans = new ArrayList<>();
    private TransportAdapter mAdapter;
    private int pageNum = 1;
    private int pageSize = 5;
    private int totalCount = 0;

    @BindView(R2.id.transport__rv)
    RecyclerView mRecyclerView;
    @BindView(R2.id.transport_refresh)
    BGARefreshLayout mRefreshLayout;

    public static TransportS3Fragment newInstance() {
        return new TransportS3Fragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_transport;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        //下拉刷新、上拉加载更多
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mContext, true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setDelegate(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //mRecyclerView.addItemDecoration(new MyItemDecoration());
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //     requestNet();
        mRefreshLayout.beginRefreshing();
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


    private void requestNet() {
        EngineerService engineerService = HttpFactory.getService(EngineerService.class);
        engineerService.getEngineerS3List(pageNum, pageSize).compose(RxHelper.<PageResponse<List<Map<String, Object>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<PageResponse<List<Map<String, Object>>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(PageResponse<List<Map<String, Object>>> response) {
                        if (pageNum == 1) { //如果page等于1时 为下拉刷新方法
                            if (response.getData().size() == 0) {
                                ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_data));
                                mRefreshLayout.endRefreshing();
                            } else {
                                mBeans.clear();
                                mBeans.addAll(response.getData());
                                mAdapter = new TransportAdapter(generateData(mBeans));
                                mAdapter.bindToRecyclerView(mRecyclerView);
                                mRecyclerView.setAdapter(mAdapter);
                                mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        switch (adapter.getItemViewType(position)) {
                                            case TYPE_LEVEL_0:
                                                IsPlanS1.IsPlanPro testParent = (IsPlanS1.IsPlanPro) adapter.getItem(position);
                                                if (testParent.isExpanded()) {
                                                    //合并
                                                    mAdapter.collapse(position, true);
                                                    testParent.setExpanded(false);

                                                } else {
                                                    //展开
                                                    mAdapter.expand(position, true);
                                                    testParent.setExpanded(true);

                                                }
                                                break;
                                            case TYPE_LEVEL_1:
                                                IsPlanS1.IsPlanPro.IsProEle testChild = (IsPlanS1.IsPlanPro.IsProEle) adapter.getItem(position);
                                                Intent intent = new Intent(mContext, CommittedTaskDetailActivity.class);
                                                intent.putExtra("eleGuid", testChild.getEleId());
                                                intent.putExtra("projectId", testChild.getProjectId());
                                                intent.putExtra("procInstId", testChild.getProcInstId());
                                                intent.putExtra("procDefKey", "s3");
                                                startActivity(intent);
                                                break;
                                        }
                                    }
                                });
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

}
