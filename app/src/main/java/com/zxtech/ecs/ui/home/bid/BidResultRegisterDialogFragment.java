package com.zxtech.ecs.ui.home.bid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.zxtech.ecs.model.DropDown;
import com.zxtech.ecs.model.DropDownVo;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.company.activity.ShowBigImageSimpleActivity;
import com.zxtech.ecs.util.FileUtil;
import com.zxtech.ecs.util.OfficePoiUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ShapeUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.ItemDivider;
import com.zxtech.gks.model.bean.SaveResult;

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
 * Created by syp523 on 2018/7/25.
 */

public class BidResultRegisterDialogFragment extends BaseDialogFragment implements BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.is_bidding_tv)
    TextView is_bidding_tv;
    @BindView(R.id.id_update_tv)
    TextView id_update_tv;
    @BindView(R.id.bid_remark_et)
    EditText bid_remark_et;
    @BindView(R.id.save_tv)
    TextView save_tv;
    @BindView(R.id.is_update_layout)
    LinearLayout is_update_layout;
    @BindView(R.id.attachment_layout)
    LinearLayout attachment_layout;
    @BindView(R.id.attachment_rv)
    RecyclerView attachment_rv;
    @BindView(R.id.upload_photo_tv)
    TextView upload_photo_tv;

    private String projectGuid;
    private boolean isEdit;
    private List<BidAttachment> mDatas = new ArrayList<>();
    private BidAttachmentAdapter mAdater;
    public static final String FILE_FLAG = "BidDocument#";

    public BidResultRegisterDialogFragmentCallBack callBack;

    public static BidResultRegisterDialogFragment newInstance() {
        return new BidResultRegisterDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_bid_result_register;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        setwScale(0.9f);
        sethScale(0.6f);
        if (isEdit) {
            save_tv.setVisibility(View.VISIBLE);
            is_bidding_tv.setEnabled(true);
            attachment_layout.setVisibility(View.INVISIBLE);
        } else {
            save_tv.setVisibility(View.GONE);
            is_bidding_tv.setEnabled(false);
            is_bidding_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            id_update_tv.setEnabled(false);
            bid_remark_et.setEnabled(false);
            bid_remark_et.setBackgroundResource(R.drawable.bg_round_border_disable);
            is_update_layout.setVisibility(View.GONE);
            upload_photo_tv.setVisibility(View.GONE);

        }
        initData();
        attachment_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdater = new BidAttachmentAdapter(R.layout.item_bid_attachment, mDatas,false);
        attachment_rv.addItemDecoration(new ItemDivider().setDividerWith(1).setDividerColor(R.color.line));
        attachment_rv.setAdapter(mAdater);
        mAdater.setOnItemChildClickListener(this);

        upload_photo_tv.setBackground(ShapeUtil.getFillRoundBG(1,8,getResources().getColor(R.color.main)));

    }

    private void initData() {
        baseResponseObservable = HttpFactory.getApiService().getProjectBidResultInfo(projectGuid);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<BidResultInfo>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<BidResultInfo>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<BidResultInfo> response) {
                        is_bidding_tv.setText(response.getData().isBidding() ? "是" : "否");
                        bid_remark_et.setText(response.getData().getBidRemark());
                        attachment_layout.setVisibility(response.getData().isBidding() ? View.VISIBLE : View.INVISIBLE);
                        mDatas.clear();
                        mDatas.addAll(response.getData().getBidResultFiles());
                        mAdater.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    @OnClick({R.id.is_bidding_tv, R.id.id_update_tv, R.id.save_tv, R.id.upload_photo_tv})
    public void onClick(final View view) {
        final List<DropDownVo> list = new ArrayList<>();
        list.add(new DropDownVo("true", "是"));
        list.add(new DropDownVo("false", "否"));

        switch (view.getId()) {
            case R.id.is_bidding_tv:
                new DropDownWindow(getActivity(), view, (TextView) view, list, view.getWidth(), -2) {
                    @Override
                    public void initEvents(int p) {
                        DropDownVo dropDownVo = list.get(p);
                        is_bidding_tv.setText(dropDownVo.getText());
                        is_bidding_tv.setTag(dropDownVo.getValue());
                        if (dropDownVo.getValue().equals("true")) {
                            is_update_layout.setVisibility(View.VISIBLE);
                            attachment_layout.setVisibility(View.VISIBLE);
                        } else {
                            is_update_layout.setVisibility(View.GONE);
                            attachment_layout.setVisibility(View.INVISIBLE);
                        }

                    }
                };
                break;
            case R.id.id_update_tv:
                new DropDownWindow(getActivity(), view, (TextView) view, list, view.getWidth(), -2) {
                    @Override
                    public void initEvents(int p) {
                        DropDownVo dropDownVo = list.get(p);
                        id_update_tv.setText(dropDownVo.getText());
                        id_update_tv.setTag(dropDownVo.getValue());

                    }
                };
                break;
            case R.id.save_tv:
                if (is_bidding_tv.getTag() == null) {
                    ToastUtil.showLong("请选择是否中标");
                    return;
                }
                JsonObject jsonObject = new JsonObject();
                JsonObject jsonInfo = new JsonObject();
                jsonInfo.addProperty("ProjectGuid", this.projectGuid);
                jsonInfo.addProperty("UserId", getUserId());
                jsonInfo.addProperty("IsBidding", is_bidding_tv.getTag().toString());
                jsonInfo.addProperty("IsUpdatePrice", id_update_tv.getTag() == null ? "" : id_update_tv.getTag().toString());
                jsonInfo.addProperty("BidRemark", bid_remark_et.getText().toString());
                jsonObject.add("DicInfo", jsonInfo);

                JsonObject jsonListInfo = new JsonObject();
                JsonArray jsonArray = new JsonArray();
                for (int i = 0; i < mDatas.size(); i++) {
                    jsonArray.add(mDatas.get(i).getGuid());
                }
                jsonListInfo.add("BidResultFile", jsonArray);
                jsonObject.add("DicListInfo", jsonListInfo);
                save(jsonObject.toString());
                break;
            case R.id.upload_photo_tv:
                selectMode();
                break;
        }
    }

    private void save(String parmas) {
        baseResponseObservable = HttpFactory.getApiService().saveProjectBidInfo(parmas);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        callBack.registerResultSave(is_bidding_tv.getTag().toString());
                        dismiss();
                    }
                });
    }


    @Override
    public void handleImage(String filePath) {
        super.handleImage(filePath);
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("fileType", RequestBody.create(MediaType.parse("text/plain"), "Project_BidResultFile"));
        bodyMap.put("projectGuid", RequestBody.create(MediaType.parse("text/plain"), this.projectGuid));
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

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
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

    }

    private void downloadDoc(String url, final String fileId, final int position) {
        showProgress();
        progressDialog.setTint(getString(R.string.downloading));
        final File file = new File(mContext.getFilesDir(), fileId);
        BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(url)
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

                });
        baseDownloadTask.start();
    }


    public interface BidResultRegisterDialogFragmentCallBack {
        void registerResultSave(String isBidding);
    }


}
