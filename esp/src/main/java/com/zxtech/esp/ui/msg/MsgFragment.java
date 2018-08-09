package com.zxtech.esp.ui.msg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxtech.common.util.T;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.AppConfig;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.ForumVO;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import java.util.List;

import g.api.adapter.GTagUtil;
import g.api.http.GRequestData;
import g.api.http.volley.Request;

/**
 * Created by SYP521 on 2017/6/29.
 */

public class MsgFragment extends Fragment {

    private View rootView;
    private ImageView iv_header;
    private ListView lv;
    private SwipeRefreshLayout mSwipeLayout;
    private MsgAdapter adapter;
    private DisplayImageOptions opts;
    private TextView tv_nickname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_msg, container, false);
            setup(rootView);
        }
        return T.getNoParentView(rootView);
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

        iv_header = (ImageView) view.findViewById(R.id.iv_head);
        opts = BizUtil.getDefaultImageOPTSBuilder(T.dip2px(getContext(), 34.0f)).showImageOnFail(R.mipmap.avatar_default)
                .showImageForEmptyUri(R.mipmap.avatar_default)
                .build();

        tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
        TextView tv_create = (TextView) view.findViewById(R.id.tv_create);
        tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ForumCreateActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        lv = (ListView) view.findViewById(R.id.lv);
        adapter = new MsgAdapter();
        lv.setAdapter(adapter);
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
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ForumVO.Data data = adapter.getItem(position);
                lookPost(data.getId());
                Intent intent = new Intent(view.getContext(), ForumReplyActivity.class);
                intent.putExtra(C.DATA, data);
                startActivity(intent);
            }
        });
        initData();
    }

    void initData() {

        GsonCallBack<ForumVO> callBack = new GsonCallBack<ForumVO>(getActivity()) {
            @Override
            public void onStart() {
                if (!mSwipeLayout.isRefreshing())
                    mSwipeLayout.setRefreshing(true);
            }

            @Override
            protected void onDoSuccess(ForumVO bean) {
                List<ForumVO.Data> data = bean.getData();
                adapter.setDatas(data);
                adapter.notifyDataSetChanged();
                mSwipeLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String info) {
                super.onFailure(info);
                mSwipeLayout.setRefreshing(false);
            }
        };
        String url = URL.getInstance().getBbsPostUrl();
        GRequestData requestData = new GRequestData(Request.Method.GET, url, null);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    /**
     * 浏览次数
     **/
    void lookPost(String post_id) {

        GsonCallBack<JsonElementVO> callBack = new GsonCallBack<JsonElementVO>(getActivity()) {
            @Override
            protected void onDoSuccess(JsonElementVO bean) {
            }
        };
        String url = URL.getInstance().lookPostUrl(post_id);
        GRequestData requestData = new GRequestData(url, null);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            initData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (GTagUtil.isDifferentTag(MyApp.getPhoto_url(), iv_header)) {
            String url = AppConfig.getFileHost() + MyApp.getPhoto_url();
            ImageLoader.getInstance().displayImage(url, iv_header, opts);
        }
        tv_nickname.setText(MyApp.getNick_name());
    }
}
