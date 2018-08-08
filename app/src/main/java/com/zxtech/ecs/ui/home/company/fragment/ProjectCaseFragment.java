package com.zxtech.ecs.ui.home.company.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ProjectCaseAdapter;
import com.zxtech.ecs.model.CompanyEntity;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.company.activity.ShowBigImageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @date: 2018/2/1
 * @desc: 项目案例
 */

public class ProjectCaseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

	private ProjectCaseAdapter mAdapter;
	private List<CompanyEntity.ResultInfoBean> mBeans = new ArrayList<>();

	@BindView(R.id.recycler_view)
	RecyclerView mRecyclerView;
	@BindView(R.id.srl_refresh)
	SwipeRefreshLayout mSrlRefresh;

	public static ProjectCaseFragment newInstance() {
		return new ProjectCaseFragment();
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_project_case;
	}

	@Override
	public void initUI(View view, @Nullable Bundle savedInstanceState) {
		mSrlRefresh.setOnRefreshListener(this);

		mAdapter = new ProjectCaseAdapter(R.layout.item_project_case, mBeans);
		mAdapter.setContext(mContext);
		mAdapter.setOnItemClickListener(this);
		mAdapter.bindToRecyclerView(mRecyclerView);
		mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
		mRecyclerView.setAdapter(mAdapter);
	}

	@Override
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {
		super.onLazyInitView(savedInstanceState);
		requestNet();
	}

	@Override
	public void onRefresh() {
		requestNet();
		mSrlRefresh.setRefreshing(false);
	}

	@Override
	public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
		ProgressBar progressBar = view.findViewById(R.id.progress);
		Intent intent = new Intent();
		List<CompanyEntity.ResultInfoBean.FileInfoListBean> fileInfoList = mBeans.get(position).getFileInfoList();
		switch (mBeans.get(position).getFileType()) {//项目实例的类型目前全部为 jpg 、png 这里做健壮性判断避免程序崩溃
			case "pdf":
//				intent.setClass(mContext, PDFActivity.class);
//				intent.putExtra("pdfPath",fileInfoList.get(0).getPath());
//				intent.putExtra("pdfName",fileInfoList.get(0).getFileName());
				break;
			case "jpg":
			case "png":
				intent.setClass(mContext, ShowBigImageActivity.class);
				ArrayList<String> images = new ArrayList<>();
				for (int i = 0; i < fileInfoList.size(); i++) {
					images.add(fileInfoList.get(i).getCoverPath());
				}
				intent.putExtra("images", images);
				startActivity(intent);
				break;
		}
	}


	private void requestNet() {
		HttpFactory.getApiService()
				.getCompanyInfo("2", "ComTag4")
				.compose(RxHelper.<BaseResponse<CompanyEntity>>rxSchedulerHelper())
				.subscribe(new DefaultObserver<BaseResponse<CompanyEntity>>(getActivity(), true) {
					@Override
					public void onSuccess(BaseResponse<CompanyEntity> response) {
						if (response.getData().getResultInfo() != null) {
							mBeans = response.getData().getResultInfo();
							mAdapter.setNewData(mBeans);
						}
					}
				});
	}

}
