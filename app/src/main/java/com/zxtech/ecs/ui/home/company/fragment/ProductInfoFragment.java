package com.zxtech.ecs.ui.home.company.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ProductTitleAdapter;
import com.zxtech.ecs.model.CompanyProduct;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.OfficePoiUtil;
import com.zxtech.ecs.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zxtech.ecs.APPConfig.DOWN_LOAD_PATH;

/**
 * @date: 2018/2/1
 * @desc: 产品介绍
 */

public class ProductInfoFragment extends BaseFragment implements ProductTitleAdapter.ProductTitleAdapterCallBack {

    @BindView(R.id.product_rv)
    RecyclerView product_rv;
    private ProductTitleAdapter mAdapter;
    private List<CompanyProduct.ResultInfoBean> mData = new ArrayList<>();

    public static ProductInfoFragment newInstance() {
        return new ProductInfoFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_company_product;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        product_rv.setLayoutManager(linearLayoutManager);
        mAdapter = new ProductTitleAdapter(getContext(), R.layout.item_company_product, mData);
        mAdapter.setCallBack(this);
        product_rv.setAdapter(mAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        requestNet();
    }


    private void requestNet() {
        HttpFactory.getApiService()
                .getCompanyProductInfo("ComTag2")
                .compose(RxHelper.<BaseResponse<CompanyProduct>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<CompanyProduct>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<CompanyProduct> response) {
                        mData.addAll(response.getData().getResultInfo());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void downloadFile(final String fileName, final String path) {
        showProgress();
        progressDialog.setTint(getString(R.string.downloading));
        final String fileUrlStr = DOWN_LOAD_PATH + fileName;
        FileDownloader.getImpl().create(path)
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
                        File file = new File(DOWN_LOAD_PATH, fileName);
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
    public void download(String fileName, String path) {
        downloadFile(fileName, path);
    }
}
