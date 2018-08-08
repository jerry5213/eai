package com.zxtech.ecs.ui.home.bid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.BidAttachmentAdapter;
import com.zxtech.ecs.model.BidAttachment;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.company.activity.ShowBigImageSimpleActivity;
import com.zxtech.ecs.util.FileUtil;
import com.zxtech.ecs.util.OfficePoiUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ShapeUtil;
import com.zxtech.ecs.util.StringUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.ConfirmDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by syp523 on 2018/7/24.
 */

public class BidAttachmentDialogFragment extends BaseDialogFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.attachment_rv)
    RecyclerView attachment_rv;
    @BindView(R.id.upload_photo_tv)
    TextView upload_photo_tv;

    private BidAttachmentAdapter mAdater;
    private List<BidAttachment> mDatas = new ArrayList<>();
    public static final String FILE_FLAG = "BidDocument#";
    public BidInfoFragment bidInfoFragment;

    public static BidAttachmentDialogFragment newInstance() {
        return new BidAttachmentDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_bid_attachment;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        setwScale(0.9f);
        sethScale(0.5f);

        attachment_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdater = new BidAttachmentAdapter(R.layout.item_bid_attachment, mDatas,bidInfoFragment.isEdit());
        attachment_rv.setAdapter(mAdater);
        mAdater.setOnItemChildClickListener(this);

        upload_photo_tv.setVisibility(bidInfoFragment.isEdit() ? View.VISIBLE : View.GONE);
        upload_photo_tv.setBackground(ShapeUtil.getFillRoundBG(1,8,getResources().getColor(R.color.main)));
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }

    public void setmDatas(List<BidAttachment> mDatas) {
        this.mDatas = mDatas;
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
        if (view.getId() == R.id.download_tv) {
            BidAttachment bidAttachment = mDatas.get(position);
            String guid = bidAttachment.getGuid();
            String fileId = FILE_FLAG + guid + bidAttachment.getFileName();
            if (FileUtil.fileType(bidAttachment.getFileName()) == FileUtil.PICTURE) {
                Intent intent = new Intent(mContext, ShowBigImageSimpleActivity.class);
                intent.putExtra("url", bidAttachment.getPath());
                startActivity(intent);
            } else {
                if ((boolean) com.zxtech.gks.common.SPUtils.get(mContext, fileId, false)) {
                    File file = new File(mContext.getFilesDir(), fileId);
                    OfficePoiUtil.openFile(mContext, file);
                } else {
                    downloadDoc(bidAttachment.getPath(), fileId, 0);
                }
            }
        }else{
            ConfirmDialog.newInstance().setBuider(mContext, "删除附件", "确认删除投标附件吗？", new ConfirmDialog.DialogConfirmCallBack() {
                @Override
                public void confirm() {
                    deleteAttach(position);
                }
            }).show();
        }

    }

    @OnClick({R.id.upload_photo_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_photo_tv:
                selectMode();
                break;
        }
    }

    @Override
    public void handleImage(String filePath) {
        super.handleImage(filePath);
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("fileType", RequestBody.create(MediaType.parse("text/plain"), "Project_BidFile"));
        bodyMap.put("projectGuid", RequestBody.create(MediaType.parse("text/plain"), bidInfoFragment.projectBid.getProjectGuid()==null?"0":bidInfoFragment.projectBid.getProjectGuid()));
        bodyMap.put("userId", RequestBody.create(MediaType.parse("text/plain"), getUserId()));
        bodyMap.put("files\";filename=\"attach.png", RequestBody.create(MediaType.parse("image/png"), new File(filePath)));
        baseResponseObservable = HttpFactory.getApiService().addBidFile(bodyMap);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<BidAttachment>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<BidAttachment>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<BidAttachment>> response) {
                        if (response.getData().size() > 0) {
                            mDatas.add(response.getData().get(0));
                            mAdater.notifyDataSetChanged();
                        }
                    }
                });
    }


    private void deleteAttach(final int position) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(mDatas.get(position).getGuid());
        jsonObject.add("GuidList", jsonArray);
        baseResponseObservable = HttpFactory.getApiService().delNonStandardFile(jsonObject.toString());
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mDatas.remove(position);
                        mAdater.notifyDataSetChanged();
                        ToastUtil.showLong("删除成功");
                    }
                });
    }

    private void downloadDoc(String url, final String fileId, final int position) {
        showProgress();
        progressDialog.setTint(getString(R.string.downloading));
        final File file = new File(mContext.getFilesDir(), fileId);
        FileDownloader.getImpl().create(url)
                .setPath(file.getAbsolutePath())
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        progressDialog.setProgress(soFarBytes * 100 / totalBytes + "%");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        dismissProgress();
                        SPUtils.put(mContext, fileId, true);
                        OfficePoiUtil.openFile(mContext, file);
                    }


                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        dismissProgress();
                    }

                }).start();
    }
}
