package com.zxtech.gks.ui.pa;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.bean.SpecialNonStandard;
import com.zxtech.gks.model.vo.FbParamBean;

import java.util.List;

import butterknife.BindView;

/**
 * Created by syp521 on 2018/2/24.
 */

public class FbReviewWayActivity extends BaseActivity implements IActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.column1)
    TextView column1;
    @BindView(R.id.column2)
    TextView column2;
    @BindView(R.id.no_item)
    TextView no_item;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MyAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fb_review_way;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.non_standard_review));
        initData();
    }

    public void initData() {

        String guid = getIntent().getStringExtra(Constants.DATA1);
        baseResponseObservable = HttpFactory.getApiService().
                getSpecialNonStandardList(guid);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<FbParamBean<SpecialNonStandard>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<FbParamBean<SpecialNonStandard>>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<FbParamBean<SpecialNonStandard>> response) {

                        List<SpecialNonStandard> datas = response.getData().getData();
                        if(datas == null || datas.size() == 0){
                            no_item.setVisibility(View.VISIBLE);
                        }
                        adapter = new MyAdapter(FbReviewWayActivity.this,R.layout.item_fb_review_way,datas);
                        rv.setAdapter(adapter);

                        String s1 = response.getData().getSumDesignCycle();
                        String s2 = response.getData().getSumProductValidateCycle();
                        column1.setText(s1);
                        column2.setText(s2);
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    protected class MyAdapter extends CommonAdapter<SpecialNonStandard> {

        public MyAdapter(Context context, int layoutId, List<SpecialNonStandard> datas) {
            super(context, layoutId, datas);
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, SpecialNonStandard specialNonStandard, int position) {

            holder.setText(R.id.column1,specialNonStandard.getNonStandardPoint());
            holder.setText(R.id.column2,specialNonStandard.isIsFeasibility()==true?"是":"否");
            holder.setText(R.id.column3,specialNonStandard.getProgram4Sales());
        }
    }
}
