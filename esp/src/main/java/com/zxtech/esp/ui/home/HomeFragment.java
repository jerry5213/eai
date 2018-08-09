package com.zxtech.esp.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zxtech.common.util.T;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.AppConfig;
import com.zxtech.esp.R;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.CourseTypeVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import java.util.List;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/6/29.
 */

public class HomeFragment extends Fragment {

    private View rootView;
    private HomeHeaderView headerView;

    private ListView mListView;
    private SwipeRefreshLayout mSwipeLayout;
    private HomeAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_etp_home, container, false);
            setup(rootView);
        }
        return T.getNoParentView(rootView);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setup(View view) {
        T.fitSystemWindow19(view.findViewById(R.id.sp_fit_system));

        TextView tv_back = view.findViewById(R.id.tv_back);
        BizUtil.setIconFont(tv_back, "\ue620");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        TextView tv_title = view.findViewById(R.id.tv_stp_title);
        tv_title.setText(getString(R.string.etp_home));

        mListView = view.findViewById(R.id.lv_home2);
        //headerView = new HomeHeaderView(view.getContext());

        //bannerView = new BannerView(view.getContext());

        //mListView.addHeaderView(headerView, null, false);
        mAdapter = new HomeAdapter();
        mListView.setAdapter(mAdapter);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                    }
                }, 1000);
            }
        });
        initData();
    }

    public void initData() {

        GsonCallBack<CourseTypeVO> callBack = new GsonCallBack<CourseTypeVO>(getActivity()){
            @Override
            public void onStart() {
                if (!mSwipeLayout.isRefreshing())
                    mSwipeLayout.setRefreshing(true);
            }

            @Override
            protected void onDoSuccess(CourseTypeVO bean) {
                List<CourseTypeVO.Data> data = bean.getData();
                mAdapter.setDatas(data);
                mAdapter.notifyDataSetChanged();
                mSwipeLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String info) {
                super.onFailure(info);
                mSwipeLayout.setRefreshing(false);
            }
        };
        String url = URL.getInstance().getCourseTypeUrl();
        GRequestParams params = new GRequestParams();
        if(!"all".equals(AppConfig.LAN)){
            params.addBodyParameter("languageflag", AppConfig.LAN);
        }
        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
