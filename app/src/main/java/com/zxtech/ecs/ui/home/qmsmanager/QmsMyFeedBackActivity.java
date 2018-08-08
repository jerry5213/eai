package com.zxtech.ecs.ui.home.qmsmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.QmsMyFeedBackAdapter;
import com.zxtech.ecs.model.JsonFeedbackListEntity;
import com.zxtech.ecs.model.QmsMyFeedBackEntity;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.GsonUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

import static com.zxtech.ecs.common.Constants.REQ_EVALUATE;
import static com.zxtech.ecs.util.Util.convertQmsLanguage;

/**
 * @auth: hexl
 * @date: 2018/2/28
 * @desc: 我的反馈
 */

public class QmsMyFeedBackActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener,
		BaseQuickAdapter.OnItemClickListener,TabLayout.OnTabSelectedListener {

	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.recycler_view_qms)
	RecyclerView mRecyclerViewQms;
	@BindView(R.id.srl_refresh)
	BGARefreshLayout rl_refresh;
	@BindView(R.id.tab_layout)
	TabLayout tabLayout;

	private QmsMyFeedBackAdapter mAdapter;
	private List<QmsMyFeedBackEntity.FeedbackListBean> mData = new ArrayList<>();
	private int page = 1 ;
	private int totalCount = 0;
	private String taskStatus = "";

	@Override
	protected int getLayoutId() {
		return R.layout.activity_qms_my_feedback2;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {

		initTitleBar(mToolbar);

		mAdapter = new QmsMyFeedBackAdapter(R.layout.item_qms_feedback2, mData);
		mAdapter.bindToRecyclerView(mRecyclerViewQms);
		mRecyclerViewQms.setLayoutManager(new LinearLayoutManager(mContext));
		mRecyclerViewQms.addItemDecoration(new MyItemDecoration());
		mRecyclerViewQms.setAdapter(mAdapter);

		//评价
		mAdapter.setOnItemChildClickListener(this);
		//详情
		mAdapter.setOnItemClickListener(this);

		BGARefreshViewHolder rv1 = new BGANormalRefreshViewHolder(this, true);
		rl_refresh.setRefreshViewHolder(rv1);

		rl_refresh.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
			@Override
			public void onBGARefreshLayoutBeginRefreshing(final BGARefreshLayout refreshLayout) {
				refresh();
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

		tabLayout.addTab(tabLayout.newTab().setText("全部"));
		tabLayout.addTab(tabLayout.newTab().setText("待反馈"));
		tabLayout.addTab(tabLayout.newTab().setText("已反馈"));
		tabLayout.addTab(tabLayout.newTab().setText("待评价"));
		tabLayout.addOnTabSelectedListener(this);

		rl_refresh.beginRefreshing();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQ_EVALUATE && resultCode == RESULT_OK) {
			rl_refresh.beginRefreshing();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
		Intent intent = new Intent();
		switch (view.getId()) {
			case R.id.tv_qms_logistics://物流
				intent.setClass(mContext, QmsTaskTrackingActivity.class);
				String jobNumber2 = mData.get(position).getJobNumber();
				intent.putExtra("JobNumber", jobNumber2);
				startActivity(intent);
				break;
			case R.id.tv_qms_evaluate://评价
				intent.setClass(mContext, QmsEvaluateActivity.class);
				String AutoGuid = mData.get(position).getAutoGuid();
				intent.putExtra("AutoGuid", AutoGuid);
				startActivityForResult(intent, REQ_EVALUATE);
				break;
			case R.id.tv_qms_look://查看
				intent.setClass(mContext, QmsMyFeedBackDetailActivity.class);
				String jobNumber1 = mData.get(position).getJobNumber();
				intent.putExtra("JobNumber", jobNumber1);
				startActivity(intent);
				break;
		}

	}

	@Override
	public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

		if(mData.get(position).getTaskStatus() == 0){
			Intent intent = new Intent(mContext, QmsMyWantFeedBackActivity.class);
			String autoGuid = mData.get(position).getAutoGuid();
			intent.putExtra("autoGuid", autoGuid);
			intent.putExtra("action", "modify");
			startActivity(intent);
		}else {
			Intent intent = new Intent(mContext, QmsMyFeedBackDetailActivity.class);
			String autoGuid = mData.get(position).getAutoGuid();
			intent.putExtra("autoGuid", autoGuid);
			startActivity(intent);
		}
	}

	public void loadMore() {

		JsonFeedbackListEntity feedbackList =
				new JsonFeedbackListEntity(getUserNo(), page+1+"", "10",taskStatus,"Desc", language);
		String params = GsonUtils.toJson(feedbackList, false);
		HttpFactory.getApiService()
				.getAPPFeedbackList(params)
				.compose(RxHelper.<BaseResponse<QmsMyFeedBackEntity>>rxSchedulerHelper())
				.subscribe(new DefaultObserver<BaseResponse<QmsMyFeedBackEntity>>(this, false) {
					@Override
					public void onSuccess(BaseResponse<QmsMyFeedBackEntity> response) {

						mData.addAll(response.getData().getFeedbackList());
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

	public void refresh() {

		JsonFeedbackListEntity feedbackList =
				new JsonFeedbackListEntity(getUserNo(), "1", "10",taskStatus,"Desc",convertQmsLanguage(language));
		String params = GsonUtils.toJson(feedbackList, false);
		HttpFactory.getApiService()
				.getAPPFeedbackList(params)
				.compose(RxHelper.<BaseResponse<QmsMyFeedBackEntity>>rxSchedulerHelper())
				.subscribe(new DefaultObserver<BaseResponse<QmsMyFeedBackEntity>>(this, false) {
					@Override
					public void onSuccess(BaseResponse<QmsMyFeedBackEntity> response) {

						mData.clear();
						mData.addAll(response.getData().getFeedbackList());
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

	@Override
	public void onTabSelected(TabLayout.Tab tab) {

		switch (tab.getPosition()){

			case 0:
				taskStatus = "";
				break;
			case 1:
				taskStatus = "0";
				break;
			case 2:
				taskStatus = "1";
				break;
			case 3:
				taskStatus = "4";
				break;
			default:
				break;
		}
		rl_refresh.beginRefreshing();
	}

	@Override
	public void onTabUnselected(TabLayout.Tab tab) {}

	@Override
	public void onTabReselected(TabLayout.Tab tab) {}
}
