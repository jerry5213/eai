package com.zxtech.is.ui.person.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.ScreenUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.project.Project;
import com.zxtech.is.service.person.PersonCheckService;
import com.zxtech.is.service.project.ProjectManageService;
import com.zxtech.is.ui.person.adpter.PersonCheckProjectAdpter;
import com.zxtech.is.ui.project.adpter.ProjectManageAdpter;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import io.reactivex.internal.operators.flowable.FlowableOnErrorReturn;

/**
 * Created by syp692 on 2018/5/8.
 */
//View.OnClickListener,BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener ,SearchView.OnQueryTextListener
public class PersonCheckProjectDialog extends DialogFragment implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemChildClickListener ,SearchView.OnQueryTextListener{

    private PersonCheckProjectAdpter projectManageAdpter;

    private List<Project> projectList = new ArrayList<Project>();
    private List<Project> projectFinalList = new ArrayList<Project>();

    //传入dialog的参数
    public Map<String,Object> params = new HashMap();

    private int totalCount = 0;
    private int page = 1;
    private int pageSize = 10;
    private String projectSearch;
    Toolbar mToolbar;
    BGARefreshLayout mRefreshLayout;
    RecyclerView rvProject;
    Button close;
    Button affim;


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

        refesh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        if (projectManageAdpter.getData().size() >= totalCount) {
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

    public static PersonCheckProjectDialog newInstance(Context context) {
        PersonCheckProjectDialog fragment = new PersonCheckProjectDialog();
//        personCheckDetailedActivity = (PersonCheckDetailedActivity)context;
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
        View view = inflater.inflate(R.layout.dialog_person__check_select_project, null);
        //循环绑定
        rvProject = view.findViewById(R.id.is_team_5);
        mRefreshLayout = view.findViewById(R.id.rl_refresh);

        close = view.findViewById(R.id.rv_return_11);
        affim = view.findViewById(R.id.rv_Affirm_10);


        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //设置Dialog从窗体底部弹出
        window.setGravity( Gravity.BOTTOM);
        window.setWindowAnimations(R.style.AnimBottom);
        params.width = (int) (ScreenUtils.getScreenWidth(getActivity()) * 1);
        params.height = (int) (ScreenUtils.getScreenHeight(getActivity()) * 0.6);
        window.setAttributes(params);
    }

    protected void initView() {
    BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), true);


        close.setOnClickListener(this);
        affim.setOnClickListener(this);

//        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mContext, true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setDelegate(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProject.setLayoutManager(linearLayoutManager);
        rvProject.addItemDecoration(new MyItemDecoration());
        projectManageAdpter = new PersonCheckProjectAdpter(R.layout.item_project_check_person, projectList);
        projectManageAdpter.bindToRecyclerView(rvProject);
        rvProject.setAdapter(projectManageAdpter);
        projectManageAdpter.setOnItemChildClickListener(this);

        mRefreshLayout.beginRefreshing();

    }






    private void loadMore() {
        ProjectManageService projectManageService = HttpFactory.getService(ProjectManageService.class);

        projectManageService.selectCheckPersonProjectBymemberGuid(params.get("memberGuid").toString(),page+1,pageSize).compose(RxHelper.<PageResponse<List<Project>>>rxSchedulerHelper()).subscribe(new DefaultObserver<PageResponse<List<Project>>>(getActivity(), false) {


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

        projectManageService.selectCheckPersonProjectBymemberGuid(params.get("memberGuid").toString(),page,pageSize).compose(RxHelper.<PageResponse<List<Project>>>rxSchedulerHelper()).subscribe(new DefaultObserver<PageResponse<List<Project>>>(getActivity(), false) {


            @Override
            public void onSuccess(PageResponse<List<Project>> response) {
                projectList.clear();
                projectList.addAll(response.getData());
                projectManageAdpter.notifyDataSetChanged();
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
 public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rv_return_11) {
            this.dismiss();

        } else if (id == R.id.rv_Affirm_10) {
            if (params.get("flag").equals("1"))
            //通过验证
            {

                Affirm();

                getActivity().finish();
            } else
            //信息待更新
            {

                if (projectList.size() > 0) {
                    for (int i = 0; i < projectList.size(); i++) {
                        if (projectList.get(i).isCheck()) {
                            projectFinalList.add(projectList.get(i));

                        }
                    }
                }
                PersonCheckUpdateDialog rDialogProject = PersonCheckUpdateDialog.newInstance();
                rDialogProject.params.put("projectGuid", projectFinalList.get(0).getProjectId().toString());
                rDialogProject.params.put("memberGuid", params.get("memberGuid").toString());
                rDialogProject.show(((Activity) getActivity()).getFragmentManager(), "");
            }

        } else {
        }

    }

        private void Affirm()
    {
       if(projectList.size()>0)
       {
           for(int i=0; i <projectList.size();i++)
           {
              if(projectList.get(i).isCheck()){
                  projectFinalList.add(projectList.get(i));

              }
           }
       }


           page = 1;
           PersonCheckService personCheckService = HttpFactory.getService(PersonCheckService.class);

           personCheckService.savePersonCheckInfo(params.get("memberGuid").toString(),projectFinalList.get(0).getProjectId().toString()).compose(RxHelper.<BaseResponse<Integer>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<Integer>>(getActivity(), false) {


               @Override
               public void onSuccess(BaseResponse<Integer> response) {
                   if("1".equals(response.getMessage()))
                   {
                       ToastUtil.showLong(getResources().getString(R.string.is_successfully_verify));


                   }



               }



               @Override
               public void onFail() {
                   super.onFail();

               }

           });



    }


    public PROJECT getProject() {
        return project;
    }

    public void setProject(PROJECT project) {
        this.project = project;
    }

    private PersonCheckProjectDialog.PROJECT project;

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if(view.getId() == R.id.is_item_team_1){
           for  (int i =0;i<projectList.size();i++)
           {
               CheckBox viewByPosition = (CheckBox)projectManageAdpter.getViewByPosition(i, R.id.is_item_team_1);
               viewByPosition.setChecked(false);


           }
            ((CheckBox) projectManageAdpter.getViewByPosition(position, R.id.is_item_team_1)).setChecked(true);
            projectList.get(position).setCheck(true);

        }
    }


    public interface PROJECT{

        void Projectinfo(String projectId, String projectName);
    }


}
