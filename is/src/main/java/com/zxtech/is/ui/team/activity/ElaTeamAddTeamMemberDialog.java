package com.zxtech.is.ui.team.activity;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ScreenUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.team.IsSlInstallationmember;
import com.zxtech.is.service.team.ElaTeamAddPersonService;
import com.zxtech.is.ui.project.activity.ProjectManageDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by syp692 on 2018/4/23.
 */

public class ElaTeamAddTeamMemberDialog extends DialogFragment implements ProjectManageDialog.PROJECT,View.OnClickListener,ElaTeamAddTeamMemberElaDialog.selectEla{


    ///////////////////////////////////////父页面传参/////////////////////////////////////////////////////////
    private IsSlInstallationmember isSlInstallationmember; //新增人员页面数据

    public IsSlInstallationmember getIsSlInstallationmember() {
        return isSlInstallationmember;
    }

    public void setIsSlInstallationmember(IsSlInstallationmember isSlInstallationmember) {
        this.isSlInstallationmember = isSlInstallationmember;
    }

    private String photoPath; //新增人员页面图片

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    private String memberGuid;//人员Guid

    private Bitmap bitmap; //签名图片

    public String getMemberGuid() {
        return memberGuid;
    }

    public void setMemberGuid(String memberGuid) {
        this.memberGuid = memberGuid;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    @BindView(R2.id.team_add_member_prj)
    TextView memberPrj;
    @BindView(R2.id.team_add_member_ela)
    TextView memberEla;

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
        View view = inflater.inflate(R.layout.dialog_elevator_team_add_member, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void Projectinfo(String projectId, String projectName) {
        if (!memberPrj.getText().equals(projectName)){
            memberPrj.setText(projectName);
            memberPrj.setTag(projectId);
            memberEla.setTag("");
            memberEla.setText("");
        }
    }

    @OnClick({R2.id.team_add_member_prj,R2.id.team_add_member_ela,R2.id.team_add_member_cancel,R2.id.team_add_member_submit})
    public void onClick(View view){
        int i = view.getId();
        if (i == R.id.team_add_member_cancel) {
            this.dismiss();

        } else if (i == R.id.team_add_member_prj) {
            ProjectManageDialog projectDialog = ProjectManageDialog.newInstance();

            projectDialog.setProject(this);

            projectDialog.show((getActivity()).getFragmentManager(), "");

        } else if (i == R.id.team_add_member_ela) {
            if (TextUtils.isEmpty(memberPrj.getText())) {
                ToastUtil.showLong(getResources().getString(R.string.is_please_select_project));
            }
            String prjId = String.valueOf(memberPrj.getTag());
            ElaTeamAddTeamMemberElaDialog mDialog = ElaTeamAddTeamMemberElaDialog.newInstance();
            mDialog.setPrjGuid(prjId);
            mDialog.setSelectEla(this);
            mDialog.show((getActivity()).getFragmentManager(), "");

        } else if (i == R.id.team_add_member_submit) {
            if (check()) {
                addPersonTeamMember();
            }

        } else {
        }
    }
    public static ElaTeamAddTeamMemberDialog newInstance() {
        ElaTeamAddTeamMemberDialog fragment = new ElaTeamAddTeamMemberDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(getActivity()) * 0.8);
        window.setAttributes(params);
    }

    @Override
    public void elaList(String elaName, String teamGuid) {
        memberEla.setText(elaName);
        memberEla.setTag(teamGuid);
    }
    private boolean check(){
        if(TextUtils.isEmpty(memberPrj.getText())) {
            ToastUtil.showLong(getResources().getString(R.string.is_please_select_project));
            return false;
        }
        if(TextUtils.isEmpty(memberEla.getText())) {
            ToastUtil.showLong(getResources().getString(R.string.is_team_add_personn));
            return false;
        }
        return true;
    }

    private void  addPersonTeamMember(){
        //团队guid
        String teamGuid = String.valueOf(memberEla.getTag());
//        //测试用
//        teamGuid = "19FC9AC8-D147-49C3-8620-8699C846DEE3";

        if (memberGuid == null || memberGuid == "") {
//---------------------------------------父页面传参---------------------------------------------------
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            RequestBody requestBodySign =  RequestBody.create(MediaType.parse("application/octet-stream"),out.toByteArray());
            MultipartBody.Part fileSign = MultipartBody.Part.createFormData("sign", String.valueOf(new Date().getTime()) + ".png", requestBodySign);

            String param = GsonUtils.toJson(isSlInstallationmember, false);
            RequestBody requestBodyA = RequestBody.create(MediaType.parse("text/plain"), param);

            File uploadFile = new File(photoPath);
            //转换
            RequestBody photo = RequestBody.create(MediaType.parse("application/octet-stream"), uploadFile);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", uploadFile.getName(), photo);
//---------------------------------------父页面传参-----------------------------------------------------
            ElaTeamAddPersonService elaTeamAddPersonService = HttpFactory.getService(ElaTeamAddPersonService.class);
            elaTeamAddPersonService.addTeamMember(teamGuid,requestBodyA,filePart,fileSign)
                    .compose(RxHelper.<BaseResponse<Integer>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<Integer>>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse<Integer> response) {
                            ToastUtil.showLong(response.getMessage());
                            if(response.getData() == 1)
                            {
                                ToastUtil.showLong(getResources().getString(R.string.is_person_black_no_add));

                            }else if(response.getData() == 2){

                                ToastUtil.showLong(getResources().getString(R.string.is_person_exist_no_add));

                            }else if(response.getData() == 4){
                                ToastUtil.showLong(getResources().getString(R.string.is_add_team_success));
                            }
                            else{
                                ToastUtil.showLong(getResources().getString(R.string.is_add_team_fail));

                            }
                       }
                    });
        } else {
            ElaTeamAddPersonService elaTeamAddPersonService = HttpFactory.getService(ElaTeamAddPersonService.class);
            elaTeamAddPersonService.moveToTeamMember(teamGuid,memberGuid)
                    .compose(RxHelper.<BaseResponse<Integer>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<Integer>>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse<Integer> response) {
                            ToastUtil.showLong(response.getMessage());
                            if (response.getData() == 4) {
                                getActivity().finish();
                            }
                        }
                    });
        }

    }
}
