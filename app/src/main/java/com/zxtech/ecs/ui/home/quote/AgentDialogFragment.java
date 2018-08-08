package com.zxtech.ecs.ui.home.quote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.Agent;
import com.zxtech.ecs.model.AgentOrg;
import com.zxtech.ecs.model.ProjectQuote;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.DropDownWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp523 on 2018/6/25.
 */

public class AgentDialogFragment extends BaseDialogFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.agent_rv)
    RecyclerView agent_rv;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;
    @BindView(R.id.agent_name_et)
    EditText agent_name_et;
    @BindView(R.id.org_tv)
    TextView org_tv;

    private AgentAdapter mAdapter;
    private List<Agent> mDatas = new ArrayList<>();
    private List<AgentOrg> dropdowns = new ArrayList<>();
    private int currentPageIndex = 1;
    private String orgNo = "";
    private String orgName = "";

    public AgentCallBack callBack;

    public static AgentDialogFragment newInstance() {
        return new AgentDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_agent;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        sethScale(0.65f);
        setwScale(0.95f);

        BGARefreshViewHolder rv1 = new BGANormalRefreshViewHolder(getActivity(), true);
        refresh_layout.setRefreshViewHolder(rv1);
        refresh_layout.setDelegate(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        agent_rv.setLayoutManager(linearLayoutManager);

        mAdapter = new AgentAdapter(R.layout.item_quote_agent, mDatas);
        mAdapter.setOnItemChildClickListener(this);
        agent_rv.setAdapter(mAdapter);
        initDropdown();
        refresh_layout.beginRefreshing();
    }

    private void initDropdown() {
        org_tv.setText(orgName);
        baseResponseObservable = HttpFactory.getApiService().getAgentOrgList();
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<AgentOrg>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<AgentOrg>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<AgentOrg>> response) {
                        dropdowns = response.getData();
                    }
                });

    }

    @Override
    public boolean isBottomShow() {
        return false;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refresh(false);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        refresh(true);
        return false;
    }


    private void refresh(final boolean isMore) {
        if (refresh_layout.isActivated()) {
            return;
        }
        baseResponseObservable = HttpFactory.getApiService().getAgentList(agent_name_et.getText().toString(), orgNo, (isMore ? currentPageIndex : 1), APPConfig.PAGE_SIZE);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<Agent>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Agent>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<Agent>> response) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            if (isMore) {
                                currentPageIndex++;
                                mDatas.addAll(response.getData());
                                refresh_layout.endLoadingMore();
                            } else {
                                currentPageIndex = 1;
                                mDatas.clear();
                                mDatas.addAll(response.getData());
                                refresh_layout.endRefreshing();
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        if (isMore) {
                            refresh_layout.endLoadingMore();
                        } else {
                            refresh_layout.endRefreshing();
                        }
                    }
                });
    }

    @OnClick({R.id.search_tv, R.id.org_tv, R.id.save_tv})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.search_tv:
                refresh(false);
                break;
            case R.id.org_tv:
                new DropDownWindow(getActivity(), view, (TextView) view, dropdowns, view.getWidth(), -2) {
                    @Override
                    public void initEvents(int p) {
                        orgNo = dropdowns.get(p).getDeptNo();
                        ((TextView) view).setText(dropdowns.get(p).getDeptName());
                    }
                };
                break;
            case R.id.save_tv:
                if (mAdapter.selectedPosition == -1 || callBack == null) {
                    return;
                }
                Agent agent = mDatas.get(mAdapter.selectedPosition);
                callBack.agentSave(agent.getPartnerName(),agent.getContactPerson(),agent.getPartnerNumber());
                dismiss();
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mAdapter.selectedPosition = position;
        mAdapter.notifyDataSetChanged();

    }


    class AgentAdapter extends BaseQuickAdapter<Agent, BaseViewHolder> {

        public int selectedPosition = -1;

        public AgentAdapter(int layoutResId, @Nullable List<Agent> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Agent item) {
            helper.setText(R.id.name_tv, item.getPartnerName());
            helper.setText(R.id.contact_person_tv, item.getContactPerson());
            helper.setText(R.id.address_tv, item.getAddress());
            if (selectedPosition == helper.getAdapterPosition()) {
                helper.setImageResource(R.id.check_iv, R.drawable.match_check);
            } else {
                helper.setImageResource(R.id.check_iv, R.drawable.match);
            }
            helper.addOnClickListener(R.id.check_iv);
        }
    }


    public interface AgentCallBack {
        void agentSave(String name, String contact, String number);
    }

}
