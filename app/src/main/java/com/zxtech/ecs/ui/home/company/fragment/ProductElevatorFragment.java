package com.zxtech.ecs.ui.home.company.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ProductElevatorAdapter;
import com.zxtech.ecs.model.CompanyEntity;
import com.zxtech.ecs.model.CompanySubTitleEntity;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.company.activity.ShowBigImageActivity;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.OfficePoiUtil;
import com.zxtech.ecs.util.PermissionUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyLeftItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zxtech.ecs.APPConfig.DOWN_LOAD_PATH;

/**
 * @date: 2018/2/2
 * @desc: 客梯 货梯 等fragment
 */

public class ProductElevatorFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    private CompanySubTitleEntity.ResultInfoDicBean mTitles;
    private ProductElevatorAdapter mAdapter;
    private List<CompanyEntity.ResultInfoBean> mBeans = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    public static Fragment getInstance(CompanySubTitleEntity.ResultInfoDicBean title) {
        ProductElevatorFragment fragment = new ProductElevatorFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTitles = (CompanySubTitleEntity.ResultInfoDicBean) bundle.getSerializable("key");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_elevator;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mAdapter = new ProductElevatorAdapter(R.layout.item_product_elevator, mBeans);
        mAdapter.setContext(mContext);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.addItemDecoration(new MyLeftItemDecoration(DensityUtil.dip2px(mContext, 10)));
        mRecyclerView.setAdapter(mAdapter);
        mSrlRefresh.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);


        switchTitle();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void switchTitle() {
//        switch (mTitles) {
//            case "客梯":
//                requestNet("2", "ComTag2", "ComTag2A1");
//                break;
//            case "货梯":
//                requestNet("2", "ComTag2", "ComTag2B1");
//                break;
//            case "杂物梯":
//                requestNet("2", "ComTag2", "ComTag2C1");
//                break;
//            case "扶梯":
//                requestNet("2", "ComTag2", "ComTag2D1");
//                break;
//            case "人行道":
//                requestNet("2", "ComTag2", "ComTag2E1");
//                break;
//            case "配饰":
//                requestNet("2", "ComTag2", "ComTag2F1");
//                break;
//        }
        requestNet("2", "ComTag2", mTitles.getSeatId());
        if (mSrlRefresh.isRefreshing()) {
            mSrlRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PermissionUtils.checkAndRequestPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, 0);
        Intent intent = new Intent();
        List<CompanyEntity.ResultInfoBean.FileInfoListBean> fileInfoList = mBeans.get(position).getFileInfoList();
        switch (mBeans.get(position).getFileType()) {
            case "pdf":
                File file = new File(DOWN_LOAD_PATH, fileInfoList.get(0).getFileName());
                if (file.exists()) {//如果已存在,直接打开
                    OfficePoiUtil.openFile(mContext, file);
                } else {//文件不存在,下载后打开
                    downloadFile(fileInfoList, file);
                }
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

    private void downloadFile(List<CompanyEntity.ResultInfoBean.FileInfoListBean> fileInfoList, final File file) {
        showProgress();
        progressDialog.setTint(getString(R.string.downloading));
        final String fileUrlStr = DOWN_LOAD_PATH + fileInfoList.get(0).getFileName();
        FileDownloader.getImpl().create(fileInfoList.get(0).getPath())
                .setPath(fileUrlStr)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        progressDialog.setProgress(soFarBytes * 100 / totalBytes + "%");
                    }


                    @Override
                    protected void completed(BaseDownloadTask task) {
                        dismissProgress();
                        ToastUtil.showLong(mContext.getResources().getString(R.string.msg42));
                        OfficePoiUtil.openFile(mContext, file);
                    }


                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        dismissProgress();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        dismissProgress();
                    }
                }).start();
    }

    @Override
    public void onRefresh() {
        switchTitle();
    }

    private void requestNet(String type, String seatId, String subSeatId) {
        HttpFactory.getApiService()
                .getProductIntroductiono(type, seatId, subSeatId)
                .compose(RxHelper.<BaseResponse<CompanyEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<CompanyEntity>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<CompanyEntity> response) {
                        mBeans = response.getData().getResultInfo();
                        if (mBeans.size() == 0) {//没有数据的情况显示emptyView
                            Log.d("ProductElevatorFragment", "这是emptyView");
                        } else {
                            mAdapter.setNewData(mBeans);
                        }
                    }
                });
    }
}
