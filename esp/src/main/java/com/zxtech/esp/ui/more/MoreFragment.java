package com.zxtech.esp.ui.more;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zxtech.common.util.SPCache;
import com.zxtech.common.util.T;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.AppConfig;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.CourseTypeVO;
import com.zxtech.esp.data.bean.UserInfoVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import java.util.List;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/6/29.
 */

public class MoreFragment extends Fragment {

    private static final int HEADER_SET = 1;
    private View rootView;
    private MoreAdapter mAdapter;
    private ListView mListView;

    private UserInfoVO.Data user;
    private MoreHeaderView headerView;
    private SwipeRefreshLayout mSwipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_more, container, false);
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

        mAdapter = new MoreAdapter();
        mListView = (ListView) view.findViewById(R.id.lv);
        headerView = new MoreHeaderView(getContext());
        headerView.setFragment(this);
        mListView.addHeaderView(headerView, null, false);
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
                        initPersonInfo();
                    }
                }, 1000);
            }
        });
        initData();
        initPersonInfo();
    }

    public void initData() {

        GsonCallBack<CourseTypeVO> callBack = new GsonCallBack<CourseTypeVO>(getActivity()) {
            @Override
            public void onStart() {
            }

            @Override
            protected void onDoSuccess(CourseTypeVO bean) {
                List<CourseTypeVO.Data> data = bean.getData();
                mAdapter.setDatas(data);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String info) {
                super.onFailure(info);
            }
        };
        String url = URL.getInstance().getLearningCourseTypeUrl();
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("userId", SPCache.getInstance(getActivity()).getString(C.USER_ID));
        String departId = SPCache.getInstance(getActivity()).getString(C.DepartId);
        params.addBodyParameter("departid", departId);
        if(!"all".equals(AppConfig.LAN)){
            params.addBodyParameter("languageflag", AppConfig.LAN);
        }
        GRequestData requestData = new GRequestData(url, params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    public void initPersonInfo() {

        GsonCallBack<UserInfoVO> callBack = new GsonCallBack<UserInfoVO>(getActivity()) {

            @Override
            public void onStart() {
                super.onStart();
                if (!mSwipeLayout.isRefreshing())
                    mSwipeLayout.setRefreshing(true);
            }

            @Override
            protected void onDoSuccess(UserInfoVO bean) {
                user = bean.getData();
                headerView.setShowText(user);
                mSwipeLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String info) {
                super.onFailure(info);
                mSwipeLayout.setRefreshing(false);
            }
        };
        String url = URL.getInstance().getUserInfoUrl(SPCache.getInstance(getActivity()).getString(C.USER_ID));
        GRequestData requestData = new GRequestData(url, null);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case HEADER_SET:
                initPersonInfo();
                break;
            default:
                break;
        }
    }*/

    public UserInfoVO.Data getUser() {
        return user;
    }
}
