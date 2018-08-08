package com.zxtech.ecs.ui.tool.decoration;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ElevatorModelAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.scheme.detail.BaseSchemeDetailFragment;
import com.zxtech.ecs.ui.home.scheme.detail.DecorationFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/4/5.
 */

public class DecorationMatchingActivity extends BaseActivity implements BaseSchemeDetailFragment.CallBackValue {
    @BindView(R.id.recylerView)
    RecyclerView recylerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private BaseFragment[] mFragments = new BaseFragment[1];

    private static final int FIRST = 0;
    public Map<String, String> paramMap = new HashMap<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_decoration_match;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recylerView.setLayoutManager(linearLayoutManager);
        List<String> s = new ArrayList<>();
        s.add("小机房");
        s.add("无机房");
        s.add("家用梯");
        s.add("扶梯");

        ElevatorModelAdapter adapter = new ElevatorModelAdapter(mContext, R.layout.item_elevator_model, s);
        adapter.setSelectedPosition(0);
        recylerView.setAdapter(adapter);

        initData();
        if (savedInstanceState == null) {
            mFragments[FIRST] = DecorationFragment.newInstance(null);

        } else {
            mFragments[FIRST] = findFragment(DecorationFragment.class);
        }
    }

    private void initData() {
        //参数错误
        baseResponseObservable = HttpFactory.getApiService().getParams(Constants.ELEVATOR, null, null, Constants.ALL_PARAMETERS, null);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<Parameters>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Parameters>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Parameters>> response) {

                        List<Parameters> data = response.getData();
                        for (int i = 0; i < data.size(); i++) {
                            paramMap.put(data.get(i).getProECode(), data.get(i).getDefaultValue());
                        }

                        loadMultipleRootFragment(R.id.fl_container, FIRST,
                                mFragments[FIRST]);
                    }
                });
    }


    @Override
    public void sendMessageValue(String key, String strValue) {
        paramMap.put(key, strValue);
    }
}
