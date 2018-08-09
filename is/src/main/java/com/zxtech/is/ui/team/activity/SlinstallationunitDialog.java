package com.zxtech.is.ui.team.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.project.Project;
import com.zxtech.is.model.team.Slinstallationunit;
import com.zxtech.is.service.project.ProjectManageService;
import com.zxtech.is.service.team.SlinstallationunitService;
import com.zxtech.is.ui.team.adapter.SlinstallationunitAdpter;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by syp692 on 2018/4/23.
 */

public class SlinstallationunitDialog extends DialogFragment implements View.OnClickListener,BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener ,SearchView.OnQueryTextListener {

    RecyclerView rvRecyclerView ;
    SearchView  mSearchView ;
    BGARefreshLayout mRefreshLayout ;
    ImageView close;
    SlinstallationunitAdpter slinstallationunitAdpter;

    public String getDeptGuid() {
        return deptGuid;
    }

    public void setDeptGuid(String deptGuid) {
        this.deptGuid = deptGuid;
    }

    public  String deptGuid;
    private String projectSearch;

    private List<com.zxtech.is.model.team.Slinstallationunit> slinstallationunitList  = new ArrayList<com.zxtech.is.model.team.Slinstallationunit>();
    private int totalCount = 0;
    private int page = 1;
    private int pageSize = 10;

    SlinstallationunitService slinstallationunitService = HttpFactory.getService(SlinstallationunitService.class);


    @Override
    @OnClick({R2.id.Unit_1})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rv_1) {
            this.dismiss();

        } else {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加这句话去掉自带的标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        View view = inflater.inflate(R.layout.dialog_sl__installationunit, null);
        rvRecyclerView = view.findViewById(R.id.is_team_5);
        mSearchView = view.findViewById(R.id.searchView);
        mRefreshLayout = view.findViewById(R.id.rl_refresh);

         close = view.findViewById(R.id.rv_1);


        initView();
        return view;
    }
    public static SlinstallationunitDialog newInstance() {
        SlinstallationunitDialog fragment = new SlinstallationunitDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    private void initView() {
       BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), true);

        //设置该SearchView默认是否自动缩小为图标
        mSearchView.setIconifiedByDefault(false);
        //为该SearchView组件设置事件监听器
        mSearchView.setOnQueryTextListener(this);
        //设置该SearchView显示搜索按钮
        mSearchView.setSubmitButtonEnabled(false);
        //给close设置监听事件
        close.setOnClickListener(this);

        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setDelegate(this);
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);

        android.widget.LinearLayout.LayoutParams textLayoutParams = (android.widget.LinearLayout.LayoutParams) textView.getLayoutParams();

        //发现textLayoutParams中的高度是固定108的，而图标的布局文件的高度是-2也就是WrapContent，将文本的高度也改成WrapContent就可以了
        textLayoutParams.height = textLayoutParams.WRAP_CONTENT;
        textView.setLayoutParams(textLayoutParams);
//        textView.setTextSize(13);//字体、提示字体大小
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRecyclerView.setLayoutManager(linearLayoutManager);
        slinstallationunitAdpter = new SlinstallationunitAdpter(R.layout.item_sl_installationunit, slinstallationunitList);
        slinstallationunitAdpter.bindToRecyclerView(rvRecyclerView);
        rvRecyclerView.setAdapter(slinstallationunitAdpter);
        slinstallationunitAdpter.setOnItemClickListener(this);
        mRefreshLayout.beginRefreshing();
}


    @Override
    public void onStart() {
        super.onStart();
        WindowManager m = getActivity().getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //params.gravity = Gravity.BOTTOM;
        params.width = (int) (d.getWidth() * 0.7);
        params.height = (int) (d.getHeight() * 0.7);
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    @Override
    public boolean onQueryTextSubmit(String query) {

        if(!TextUtils.isEmpty(query))
        {
            projectSearch= query;
            refesh();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        projectSearch= newText;

        return false;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refesh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        if (slinstallationunitAdpter.getData().size() >= totalCount) {
            mRefreshLayout.endLoadingMore();
            ToastUtil.showLong(getResources().getString(R.string.is_no_more_data));
        }
        else{
            loadMore();
            mRefreshLayout.endLoadingMore();
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


        unit.INSTALLATIONUNIT(slinstallationunitList.get(position).getGuid(),slinstallationunitList.get(position).getName());
        dismiss();

    }


    private void refesh() {
        page = 1;


        slinstallationunitService.selectSlInstailationUnit(projectSearch,deptGuid,page,pageSize).compose(RxHelper.<PageResponse<List<Slinstallationunit>>>rxSchedulerHelper()).subscribe(new DefaultObserver<PageResponse<List<Slinstallationunit>>>(getActivity(), false) {


            @Override
                public void onSuccess(PageResponse<List<Slinstallationunit>> response) {
                    slinstallationunitList.clear();
                    slinstallationunitList.addAll(response.getData());
                    slinstallationunitAdpter.notifyDataSetChanged();
                    mRefreshLayout.endRefreshing();
                    totalCount = response.getRowCount();
                    if (totalCount==0)
                {
                    ToastUtil.showLong(getResources().getString(R.string.is_no_retrieved_data) );
                }
            }
            @Override
            public void onFail() {
                super.onFail();
                mRefreshLayout.endRefreshing();
            }

        });


    }
    @Override
    public void dismiss() {
        super.dismiss();

    }

    private void loadMore() {

        slinstallationunitService.selectSlInstailationUnit(projectSearch,deptGuid,page+1,pageSize).compose(RxHelper.<PageResponse<List<Slinstallationunit>>>rxSchedulerHelper()).subscribe(new DefaultObserver<PageResponse<List<Slinstallationunit>>>(getActivity(), false) {


            @Override
            public void onSuccess(PageResponse<List<Slinstallationunit>> response) {
                slinstallationunitList.addAll(response.getData());
                slinstallationunitAdpter.notifyDataSetChanged();
                mRefreshLayout.endLoadingMore();
                page ++;
                totalCount = response.getRowCount();

            }



            @Override
            public void onFail() {
                super.onFail();
                mRefreshLayout.endRefreshing();
            }

        });





    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    private Unit unit;


    public interface Unit{

        void INSTALLATIONUNIT(String guid,String name);
    }
}
