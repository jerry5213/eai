package com.zxtech.is.ui.mt.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.project.Project;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.service.mt.AppMtService;
import com.zxtech.is.ui.mt.adapter.MtProjectProcessAdapter;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by syp692 on 2018/4/17.
 */

public class MtProjectProcessItemActivity extends BaseActivity {

    private List<Map<String, String>> mBeans = new ArrayList<>();
    private MtProjectProcessAdapter mAdapter;
    @BindView(R2.id.civil_review_rv)
    RecyclerView mRecyclerView;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    /**
     * projectNo 项目编号
     */
    private String projectNo;
    /**
     * taskFlag 项目类型
     */
    private String taskFlag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_process_item;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);

        projectNo = String.valueOf(getIntent().getSerializableExtra("projectNo"));
        taskFlag = String.valueOf(getIntent().getSerializableExtra("taskFlag"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        mAdapter = new MtProjectProcessAdapter(R.layout.item_task_me_civilreview, mBeans);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        requestNet();

    }

    private void requestNet() {
        Project prj = new Project();
        prj.setProjectno(projectNo);
        prj.setTaskFlag(taskFlag);
        String param = GsonUtils.toJson(prj, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        AppMtService appMtService = HttpFactory.getService(AppMtService.class);
        appMtService.getProjectElevatorProgressInfo(requestBody)
                .compose(RxHelper.<BaseResponse<List<Map<String, String>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Map<String, String>>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Map<String, String>>> response) {
                        mBeans.addAll(response.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


}
