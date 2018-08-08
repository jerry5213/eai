package com.zxtech.ecs.ui.home.follow;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.gks.model.vo.PrProductDetail.WorkFlowNodeListBean;
import com.zxtech.gks.ui.pa.WorkFlowNodeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by syp521 on 2018/2/8.
 */

public class WorkNodeListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private WorkFlowNodeListAdapter workFlowNodeListAdapter;
    private List<WorkFlowNodeListBean> mDatas = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_node_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.process_tracking));
        String taskId = getIntent().getStringExtra("taskId");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);


        workFlowNodeListAdapter = new WorkFlowNodeListAdapter(mContext,R.layout.item_work_flow_node_list,mDatas);
        recycleView.setAdapter(workFlowNodeListAdapter);

//        GsonCallBack<JsonElementVO> callBack = new GsonCallBack<JsonElementVO>(this) {
//
//            @Override
//            protected void onDoSuccess(JsonElementVO vo) {
//
//                if(vo.getData().isJsonArray()){
//                    List<WorkFlowNodeListBean> list = new Gson().fromJson(vo.getData(),new TypeToken<List<WorkFlowNodeListBean>>(){}.getType());
//                    workFlowNodeListAdapter.setDatas(list);
//                    workFlowNodeListAdapter.notifyDataSetChanged();
//                }
//            }
//        };
//        String url = AppConfig.getHOST() + "getWorkFlowList.do";
//        GRequestParams params = new GRequestParams();
//        params.addBodyParameter("taskId",taskId);
//        GRequestData requestData = new GRequestData(Request.Method.POST, url, params);
//        App.getInstance().getHttp().send(requestData, callBack);

        initData(taskId);
    }

    private void initData(String taskId) {
        baseResponseObservable = HttpFactory.getApiService().
                getWorkFlowList(taskId);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<List<WorkFlowNodeListBean>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<List<WorkFlowNodeListBean>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<List<WorkFlowNodeListBean>> response) {
                        workFlowNodeListAdapter.getDatas().addAll(response.getData());
                        workFlowNodeListAdapter.notifyDataSetChanged();
                    }

                });
    }
}
