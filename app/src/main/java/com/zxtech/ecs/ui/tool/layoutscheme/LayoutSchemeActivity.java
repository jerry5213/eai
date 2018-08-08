package com.zxtech.ecs.ui.tool.layoutscheme;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.LayoutSchemeAdapter;
import com.zxtech.ecs.adapter.LayoutSelectAdapter;
import com.zxtech.ecs.model.DropDownBean;
import com.zxtech.ecs.model.LayoutSelect;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.FileProvider7;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/4/4.
 */

public class LayoutSchemeActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_rv)
    RecyclerView content_rv;
    @BindView(R.id.select_rv)
    RecyclerView select_rv;


    private LayoutSchemeAdapter mAdapter;
    private List<String> mDatas = new ArrayList<>();

    private LayoutSelectAdapter layoutSelectAdapter;
    private List<LayoutSelect> selectDatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_layout_scheme;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        content_rv.setLayoutManager(linearLayoutManager);

        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("0");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mAdapter = new LayoutSchemeAdapter(R.layout.item_layout_scheme, mDatas);
        content_rv.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        select_rv.addItemDecoration(new MyItemDecoration(DensityUtil.dip2px(mContext, 10)));
        select_rv.setLayoutManager(gridLayoutManager);

        selectDatas.add(new LayoutSelect("Ele_M", "电梯型号", ""));
        selectDatas.add(new LayoutSelect("ENTER", "入口", ""));
        selectDatas.add(new LayoutSelect("Load", "载重(kg)", ""));
        selectDatas.add(new LayoutSelect("V", "速度(m/s)", ""));
        selectDatas.add(new LayoutSelect("CAR_SIZE", "轿厢尺寸(mm)", ""));
        selectDatas.add(new LayoutSelect("CH", "轿厢高度(mm)", ""));
        selectDatas.add(new LayoutSelect("OPT", "开门方式", ""));
        selectDatas.add(new LayoutSelect("DOOR_SIZE", "轿门尺寸(mm)", ""));
        selectDatas.add(new LayoutSelect("A_G1_Jamb_Type", "厅门门套", ""));
        selectDatas.add(new LayoutSelect("TT018", "顶层高度(mm)", ""));

        layoutSelectAdapter = new LayoutSelectAdapter(R.layout.item_layout_select, selectDatas);
        select_rv.setAdapter(layoutSelectAdapter);

        layoutSelectAdapter.setOnItemChildClickListener(this);
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, final View view, final int position) {
        if (view.getId() == R.id.value_tv) {
            LayoutSelect layoutSelect = selectDatas.get(position);
            if (TextUtils.isEmpty(layoutSelect.getCode())) {
                return;
            }
//            baseResponseObservable = HttpFactory.getApiService().getOptions(layoutSelect.getCode());
//            baseResponseObservable
//                    .compose(RxHelper.<BaseResponse<List<DropDownBean>>>rxSchedulerHelper())
//                    .subscribe(new DefaultObserver<BaseResponse<List<DropDownBean>>>(this, true) {
//                        @Override
//                        public void onSuccess(BaseResponse<List<DropDownBean>> response) {
//                            showPopwindow(response.getData(), (TextView) view, position);
//                        }
//                    });
        } else if (view.getId() == R.id.status_iv) {
            final String apkPath = getExternalFilesDir("").getAbsolutePath() + "/ecs.apk";
            showProgress();
            FileDownloader.getImpl().create("http://47.94.99.203:2603/app/release.apk")
                    .setPath(apkPath)
                    .setListener(new FileDownloadSampleListener() {

                        @Override
                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            progressDialog.setProgress(soFarBytes*100/totalBytes+"%");
                        }


                        @Override
                        protected void completed(BaseDownloadTask task) {
                            dismissProgress();
                            installApk(apkPath);
                        }


                        @Override
                        protected void error(BaseDownloadTask task, Throwable e) {
                            Log.d(TAG, "error: "+e.getMessage());
                            dismissProgress();
                        }

                        @Override
                        protected void warn(BaseDownloadTask task) {
                            Log.d(TAG, "warn: ");
                        }
                    }).start();
        }
    }


    private void showPopwindow(final List<DropDownBean> dropDownBeans, final TextView view, final int position) {
        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        view.setCompoundDrawables(null, null, image, null);
        DropDownWindow mWindow = new DropDownWindow(mContext, view, dropDownBeans, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {
                view.setText(dropDownBeans.get(p).getValue());
                selectDatas.get(position).setValue(dropDownBeans.get(p).getKey());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                view.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    /*
  * 安装下载的apk文件
  */
    private void installApk(String apkPath) {
        File file = new File(apkPath);
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        FileProvider7.setIntentDataAndType(getActivity(),
                intent, "application/vnd.android.package-archive", file, true);
        getActivity().startActivity(intent);
    }
}
