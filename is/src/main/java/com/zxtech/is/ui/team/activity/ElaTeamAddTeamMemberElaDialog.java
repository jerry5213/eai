package com.zxtech.is.ui.team.activity;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.MyItemDecoration;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.team.TeamAddMemberEla;
import com.zxtech.is.service.team.ElaTeamAddPersonService;
import com.zxtech.is.ui.team.adapter.AddTeamMemberElaAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by syp660 on 2018/5/5.
 */

public class ElaTeamAddTeamMemberElaDialog extends DialogFragment {

    private AddTeamMemberElaAdapter mAdapter;

    private List<TeamAddMemberEla> list = new ArrayList<>();

    private String prjGuid;

    public String getPrjGuid() {
        return prjGuid;
    }

    public void setPrjGuid(String prjGuid) {
        this.prjGuid = prjGuid;
    }

    public interface selectEla{

        void elaList(String elaName, String teamGuid);
    }

    private selectEla selectEla;

    public ElaTeamAddTeamMemberElaDialog.selectEla getSelectEla() {
        return selectEla;
    }

    public void setSelectEla(ElaTeamAddTeamMemberElaDialog.selectEla selectEla) {
        this.selectEla = selectEla;
    }

    @BindView(R2.id.ela_team_add_member_rv)
    RecyclerView mRecyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_elevator_tem, null);
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
        requesNet();
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        mAdapter = new AddTeamMemberElaAdapter(R.layout.item_elevator_team, list);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        WindowManager m = getActivity().getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //params.gravity = Gravity.BOTTOM;
        params.width = (int) (d.getWidth() * 0.8);
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @OnClick({R2.id.ela_team_add_member_submit,R2.id.ela_team_add_member_cancel})
    public void onClick(View view){
        int i = view.getId();
        if (i == R.id.ela_team_add_member_submit) {
            if (check()) {
                result();
                this.dismiss();
            }

        } else if (i == R.id.ela_team_add_member_cancel) {
            this.dismiss();

        } else {
        }
    }

    private void requesNet(){
        ElaTeamAddPersonService elaTeamAddPersonService = HttpFactory.getService(ElaTeamAddPersonService.class);
        elaTeamAddPersonService.selectTeamMemberEla(prjGuid)
                .compose(RxHelper.<BaseResponse<List<TeamAddMemberEla>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<TeamAddMemberEla>>>(getActivity(),true) {
                    @Override
                    public void onSuccess(BaseResponse<List<TeamAddMemberEla>> response) {
                        list.clear();
                        list.addAll(response.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
    public static ElaTeamAddTeamMemberElaDialog newInstance() {
        ElaTeamAddTeamMemberElaDialog fragment = new ElaTeamAddTeamMemberElaDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    private boolean check(){
        if(mAdapter.getSelectCount() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_one_elevator));
            return false;
        }
        return true;
    }
    private void result(){
        String elaName = "";
        String teamGuid = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isCheck()){
                if (elaName.equals("")) {
                    elaName += list.get(i).getElaName();
                    teamGuid += list.get(i).getTeamGuid();
                } else {
                    elaName += "," + list.get(i).getElaName();
                    teamGuid += "," + list.get(i).getTeamGuid();
                }
            }
        }
        selectEla.elaList(elaName,teamGuid);
    }

}
