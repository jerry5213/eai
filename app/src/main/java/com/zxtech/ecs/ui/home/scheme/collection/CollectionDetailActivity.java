package com.zxtech.ecs.ui.home.scheme.collection;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.SchemeDetailExpandableAdapter;
import com.zxtech.ecs.model.Content;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zxtech.ecs.adapter.ExpandableItemAdapter.TYPE_LEVEL_0;

/**
 * 收藏详情
 * Created by syp523 on 2018/1/3.
 */

public class CollectionDetailActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    private RecyclerView detail_rv;
    private CommonAdapter<Content> mAdapter;
    List<Content> mDatas = new ArrayList<>();

    private SchemeDetailExpandableAdapter schemeParamsTitileAdapter;
    private String guid;
    @BindView(R.id.toolbar)
    Toolbar toolbar;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar);
        guid =  getIntent().getStringExtra("guid");

        detail_rv = (RecyclerView) findViewById(R.id.detail_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        detail_rv.setLayoutManager(linearLayoutManager);


        loadData();
    }



    private void loadData() {
        baseResponseObservable = HttpFactory.getApiService().getCollectionDetail(guid);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<Programme>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Programme>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<Programme> response) {

                        schemeParamsTitileAdapter = new SchemeDetailExpandableAdapter(generateData(response.getData()));
                        detail_rv.addItemDecoration(new MyItemDecoration(1));
                        detail_rv.setAdapter(schemeParamsTitileAdapter);
                        schemeParamsTitileAdapter.expandAll();
                        schemeParamsTitileAdapter.setOnItemClickListener(CollectionDetailActivity.this);
                    }
                });
    }

    private ArrayList<MultiItemEntity> generateData(Programme programme) {


        ArrayList<MultiItemEntity> res = new ArrayList<>();
        List<Programme.DimensionsBean> dimensions = programme.getDimensions();
        for (int i = 0; i < dimensions.size(); i++) {
            Programme.DimensionsBean dimensionsBean = dimensions.get(i);
            //TestParent lv0 = new TestParent("维度");
            for (int j = 0; j < dimensionsBean.getParams().size(); j++) {
                dimensionsBean.addSubItem(dimensionsBean.getParams().get(j));
            }
            res.add(dimensionsBean);
        }
        return res;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (adapter.getItemViewType(position)) {
            case TYPE_LEVEL_0:
                Programme.DimensionsBean testParent = (Programme.DimensionsBean) adapter.getItem(position);
                if (testParent.isExpanded()) {
                    //合并
                    schemeParamsTitileAdapter.collapse(position, true);
                    testParent.setExpanded(false);

                } else {
                    //展开
                    schemeParamsTitileAdapter.expand(position, true);
                    testParent.setExpanded(true);

                }
                break;
        }
    }
}
