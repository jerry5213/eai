package com.zxtech.is.ui.project.activity;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
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
import com.zxtech.is.util.ScreenUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.project.Project;
import com.zxtech.is.service.project.ProjectManageService;
import com.zxtech.is.ui.project.adpter.ProjectManageAdpter;
import com.zxtech.is.ui.team.activity.SlinstallationunitDialog;
import com.zxtech.is.ui.team.adapter.SlinstallationunitAdpter;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp692 on 2018/4/17.
 */
//View.OnClickListener,BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener ,SearchView.OnQueryTextListener
public class ProjectManageDialog extends DialogFragment implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener ,SearchView.OnQueryTextListener{

    private ProjectManageAdpter projectManageAdpter;
    private List<Project> projectList = new ArrayList<Project>();
    private int totalCount = 0;
    private int page = 1;
    private int pageSize = 10;
    private String projectSearch;
    Toolbar mToolbar;
    BGARefreshLayout mRefreshLayout;
    RecyclerView rvProject;
    SearchView mSearchView;
    ImageView close;

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

        refesh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        if (projectManageAdpter.getData().size() >= totalCount) {
            mRefreshLayout.endLoadingMore();
            ToastUtil.showLong(getResources().getString(R.string.is_no_more_data)  );
        }
        else{
            loadMore();
            mRefreshLayout.endLoadingMore();
            return true;
        }


        return false;

    }

    public static ProjectManageDialog newInstance() {
        ProjectManageDialog fragment = new ProjectManageDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.dialog_project_manage, null);
        //循环绑定
        rvProject = view.findViewById(R.id.rvProject);
        mSearchView = view.findViewById(R.id.searchView);
        mRefreshLayout = view.findViewById(R.id.rl_refresh);

        close = view.findViewById(R.id.rv_1);


        initView();
        return view;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {



        project.Projectinfo(projectList.get(position).getProjectId() ,projectList.get(position).getProjectName());
        dismiss();

    }


    protected void initView() {
    BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), true);

        //设置该SearchView默认是否自动缩小为图标
        mSearchView.setIconifiedByDefault(false);
        //为该SearchView组件设置事件监听器
        mSearchView.setOnQueryTextListener(this);
        //设置该SearchView显示搜索按钮
        mSearchView.setSubmitButtonEnabled(false);

        close.setOnClickListener(this);
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);

        android.widget.LinearLayout.LayoutParams textLayoutParams = (android.widget.LinearLayout.LayoutParams) textView.getLayoutParams();

        //发现textLayoutParams中的高度是固定108的，而图标的布局文件的高度是-2也就是WrapContent，将文本的高度也改成WrapContent就可以了
        textLayoutParams.height = textLayoutParams.WRAP_CONTENT;
        textView.setLayoutParams(textLayoutParams);
//        textView.setTextSize(13);//字体、提示字体大小
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

//        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mContext, true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setDelegate(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProject.setLayoutManager(linearLayoutManager);
        rvProject.addItemDecoration(new MyItemDecoration());
        projectManageAdpter = new ProjectManageAdpter(R.layout.item_dialog_project_manage, projectList);
        projectManageAdpter.bindToRecyclerView(rvProject);
        rvProject.setAdapter(projectManageAdpter);
        projectManageAdpter.setOnItemClickListener(this);

        mRefreshLayout.beginRefreshing();

    }



    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();


        params.width = (int) (ScreenUtils.getScreenWidth(getActivity()) * 0.8);
        params.height = (int) (ScreenUtils.getScreenHeight(getActivity()) * 0.7);
        window.setAttributes(params);
    }


    private void loadMore() {
        ProjectManageService projectManageService = HttpFactory.getService(ProjectManageService.class);

        projectManageService.ProjectManageList(projectSearch,page+1,pageSize).compose(RxHelper.<PageResponse<List<Project>>>rxSchedulerHelper()).subscribe(new DefaultObserver<PageResponse<List<Project>>>(getActivity(), false) {


            @Override
            public void onSuccess(PageResponse<List<Project>> response) {
                projectList.addAll(response.getData());
                projectManageAdpter.notifyDataSetChanged();
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

    private void refesh() {
        page = 1;
        ProjectManageService projectManageService = HttpFactory.getService(ProjectManageService.class);

        projectManageService.ProjectManageList(projectSearch,page,pageSize).compose(RxHelper.<PageResponse<List<Project>>>rxSchedulerHelper()).subscribe(new DefaultObserver<PageResponse<List<Project>>>(getActivity(), false) {


            @Override
            public void onSuccess(PageResponse<List<Project>> response) {
                projectList.clear();
                projectList.addAll(response.getData());
                projectManageAdpter.notifyDataSetChanged();
                mRefreshLayout.endRefreshing();

                totalCount = response.getRowCount();
                if (totalCount==0)
                {
                    ToastUtil.showLong(getResources().getString(R.string.is_no_retrieved_data));
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
        if(TextUtils.isEmpty(newText))
        {
            refesh();
            return true;

        }
        return false;
    }

    @Override
 public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rv_1) {
            this.dismiss();

        } else {
        }

    }


    public PROJECT getProject() {
        return project;
    }

    public void setProject(PROJECT project) {
        this.project = project;
    }

    private ProjectManageDialog.PROJECT project;


    public interface PROJECT{

        void Projectinfo(String projectId,String projectName);
    }


}
