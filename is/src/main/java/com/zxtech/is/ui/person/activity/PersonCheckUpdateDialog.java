package com.zxtech.is.ui.person.activity;

import android.app.Activity;
import android.app.DialogFragment;
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
import android.widget.SearchView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ScreenUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.common.IsMstOptions;
import com.zxtech.is.model.person.PersonCheckUpdate;
import com.zxtech.is.model.project.Project;
import com.zxtech.is.service.common.IsMstOptionService;
import com.zxtech.is.service.person.PersonCheckService;
import com.zxtech.is.service.project.ProjectManageService;
import com.zxtech.is.ui.person.adpter.PersonCheckProjectAdpter;
import com.zxtech.is.ui.person.adpter.PersonCheckUpdateAdpter;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by syp692 on 2018/5/8.
 */
//View.OnClickListener,BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener ,SearchView.OnQueryTextListener
public class PersonCheckUpdateDialog extends DialogFragment implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private PersonCheckUpdateAdpter personCheckUpdateAdpter;

    private List<IsMstOptions> isMstOptionsList = new ArrayList<IsMstOptions>();
    private List<IsMstOptions> IsMstOptionsFinalList = new ArrayList<IsMstOptions>();

    //传入dialog的参数
    public Map<String,Object> params = new HashMap();


    RecyclerView rvProject;
    Button close;
    Button affim;
    TextView questionText;



//    }

    public static PersonCheckUpdateDialog newInstance() {
        PersonCheckUpdateDialog fragment = new PersonCheckUpdateDialog();
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
        View view = inflater.inflate(R.layout.dialog_person__check_info_update, null);
        //循环绑定
        rvProject = view.findViewById(R.id.is_team_5);
        questionText = view.findViewById(R.id.is_item_team_3);

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


        close.setOnClickListener(this);
        affim.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProject.setLayoutManager(linearLayoutManager);
        rvProject.addItemDecoration(new MyItemDecoration());
        personCheckUpdateAdpter = new PersonCheckUpdateAdpter(R.layout.item_project_check_update, isMstOptionsList);
        personCheckUpdateAdpter.bindToRecyclerView(rvProject);
        rvProject.setAdapter(personCheckUpdateAdpter);
        personCheckUpdateAdpter.setOnItemChildClickListener(this);

        refesh();
    }







    private void refesh() {

        IsMstOptionService isMstOptionService = HttpFactory.getService(IsMstOptionService.class);

        isMstOptionService.selectParents("GK0012","").compose(RxHelper.<BaseResponse<List<IsMstOptions>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<IsMstOptions>>>(getActivity(), false) {


            @Override
            public void onSuccess(BaseResponse<List<IsMstOptions>> response) {
                isMstOptionsList.clear();
                isMstOptionsList.addAll(response.getData());
                personCheckUpdateAdpter.notifyDataSetChanged();


            }



            @Override
            public void onFail() {
                super.onFail();

            }

        });


    }



    @Override
 public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rv_return_11) {
            this.dismiss();

        } else if (id == R.id.rv_Affirm_10) {
            Affirm();
            getActivity().finish();


        } else {
        }

    }

        private void Affirm()
    {
       if(isMstOptionsList.size()>0)
       {
           for(int i=0; i <isMstOptionsList.size();i++)
           {
              if(isMstOptionsList.get(i).isCheck()){
                  IsMstOptionsFinalList.add(isMstOptionsList.get(i));

              }
           }
       }

        PersonCheckUpdate  personCheckUpdate = new PersonCheckUpdate();
        personCheckUpdate.setMemberGuid(params.get("memberGuid").toString());
        personCheckUpdate.setProjectGuid(params.get("projectGuid").toString());
        personCheckUpdate.setIsMstOptions(IsMstOptionsFinalList);
        personCheckUpdate.setQuestionText(questionText.getText().toString());


        String param = GsonUtils.toJson(personCheckUpdate, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);

        PersonCheckService personCheckService = HttpFactory.getService(PersonCheckService.class);

           personCheckService.savePersonCheckUpdate(requestBody).compose(RxHelper.<BaseResponse<Integer>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<Integer>>(getActivity(), false) {


               @Override
               public void onSuccess(BaseResponse<Integer> response) {
                   if("1".equals(response.getMessage()))
                   {
                       ToastUtil.showLong( getResources().getString(R.string.is_info_upload_success));


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

    private PersonCheckUpdateDialog.PROJECT project;

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            isMstOptionsList.get(position).setCheck(true);


    }


    public interface PROJECT{

        void Projectinfo(String projectId, String projectName);
    }


}
