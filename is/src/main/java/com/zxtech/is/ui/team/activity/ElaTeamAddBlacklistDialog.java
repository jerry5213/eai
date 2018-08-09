package com.zxtech.is.ui.team.activity;


import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ImageUtil;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.SelectDialog;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.dialog.BasePhotoDialogFragment;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.common.IsMstOptions;
import com.zxtech.is.model.team.IsSlInstallationmember;
import com.zxtech.is.service.common.IsMstOptionService;
import com.zxtech.is.service.team.ElaTeamAddPersonService;
import com.zxtech.is.ui.task.activity.ShowBigImageSimpleActivity;
import com.zxtech.is.ui.team.adapter.AddBlacklistIllegalAdapter;
import com.zxtech.is.ui.team.adapter.AddBlacklistPhotoAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by syp660 on 2018/4/30.
 */

public class ElaTeamAddBlacklistDialog extends BasePhotoDialogFragment implements BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener {

    private Dialog dialog;

    private ImageView mImageView;

    //违规行为
    private AddBlacklistIllegalAdapter addBlacklistIllegalAdapter;
    //违规照片
    private AddBlacklistPhotoAdapter addBlacklistPhotoAdapter;
    //违规照片
    List<String> photoList = new ArrayList<>();
    //违规行为--发送
    List<String> illegalList = new ArrayList<>();

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

    public String getMemberGuid() {
        return memberGuid;
    }

    public void setMemberGuid(String memberGuid) {
        this.memberGuid = memberGuid;
    }

    ///////////////////////////////////////Adapter设置的参数///////////////////////////////

    private List<IsMstOptions> cbList = new ArrayList<>(); //违法行为list

    @BindView(R2.id.add_blacklist_illegal_rv)
    RecyclerView rvBlackList;

    @BindView(R2.id.add_blacklist_photo_rv)
    RecyclerView rVPhoto;

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
        View view = inflater.inflate(R.layout.dialog_elevator_team_add_blacklist, null);
        ButterKnife.bind(this, view);
        getIllegalList();
        initView();
        return view;
    }

    private void initView() {
        //违规行为adapter
        LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(getActivity());
        linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
        rvBlackList.setLayoutManager(linearLayoutManagera);
        addBlacklistIllegalAdapter = new AddBlacklistIllegalAdapter(R.layout.item_elevator_team_illegal, cbList);
        addBlacklistIllegalAdapter.bindToRecyclerView(rvBlackList);
        rvBlackList.setAdapter(addBlacklistIllegalAdapter);

        //违规行为照片adapter//项目附件
        LinearLayoutManager linearLayoutManagerb = new LinearLayoutManager(getActivity());
        linearLayoutManagerb.setOrientation(LinearLayoutManager.HORIZONTAL);
        rVPhoto.setLayoutManager(linearLayoutManagerb);
        addBlacklistPhotoAdapter = new AddBlacklistPhotoAdapter(R.layout.item_img_common, photoList);
        addBlacklistPhotoAdapter.bindToRecyclerView(rVPhoto);
        rVPhoto.setAdapter(addBlacklistPhotoAdapter);
        addBlacklistPhotoAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        int i = view.getId();
        if (i == R.id.common_img_close) {
            photoList.remove(position);
            addBlacklistPhotoAdapter.notifyItemRemoved(position);
            addBlacklistPhotoAdapter.notifyDataSetChanged();

        } else if (i == R.id.common_img_iv) {
            Intent intent = new Intent();
            intent.putExtra("url", photoList.get(position));
            intent.setClass(getActivity(), ShowBigImageSimpleActivity.class);
            startActivity(intent);

        } else {
        }

    }

    public static ElaTeamAddBlacklistDialog newInstance() {
        ElaTeamAddBlacklistDialog fragment = new ElaTeamAddBlacklistDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager m = getActivity().getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = getDialog().getWindow();
        window.setGravity( Gravity.BOTTOM);

        window.setWindowAnimations(R.style.AnimBottom);

        WindowManager.LayoutParams params = window.getAttributes();
        //params.gravity = Gravity.BOTTOM;
        params.width = (int) (d.getWidth() * 1);
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    protected void closeDialog() {
        this.dismiss();
    }

    @OnClick({R2.id.team_add_blacklist_add_iv, R2.id.team_add_blacklist_cancel, R2.id.team_add_blacklist_submit})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.team_add_blacklist_add_iv) {
            addAttach();

        } else if (id == R.id.team_add_blacklist_cancel) {
            closeDialog();

        } else if (id == R.id.team_add_blacklist_submit) {
            if (check()) {
                addPersonBlackList();
            }

        } else {
        }
    }

    private boolean check() {
        if (addBlacklistIllegalAdapter.getSelectCount() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_team_add_personl));
            return false;
        }
        if (photoList.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_team_add_personm));
            return false;
        }
        return true;
    }

    private void addPersonBlackList() {
        Map<String, RequestBody> fileMap = new HashMap<>();
        for (String mPhotoPath : photoList) {
            File uploadFile = new File(mPhotoPath);
            RequestBody photo = RequestBody.create(MediaType.parse("application/octet-stream"), uploadFile);
            fileMap.put("parts\"; filename=\"" + uploadFile.getName() + "", photo);
        }
        illegalList.clear();
        for (int i = 0; i < cbList.size(); i++) {
            if (cbList.get(i).isCheck()) {
                illegalList.add(cbList.get(i).getCode());
            }
        }
        String illegal = GsonUtils.toJson(illegalList, false);
        RequestBody requestBodyB = RequestBody.create(MediaType.parse("text/plain"), illegal);
        if (memberGuid == null || memberGuid == "") {
//---------------------------------------父页面传参---------------------------------------------------
            String param = GsonUtils.toJson(isSlInstallationmember, false);
            RequestBody requestBodyA = RequestBody.create(MediaType.parse("text/plain"), param);

            File uploadFile = new File(photoPath);
            //转换
            RequestBody photo = RequestBody.create(MediaType.parse("application/octet-stream"), uploadFile);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", uploadFile.getName(), photo);
//---------------------------------------父页面传参-----------------------------------------------------
            ElaTeamAddPersonService elaTeamAddPersonService = HttpFactory.getService(ElaTeamAddPersonService.class);
            elaTeamAddPersonService.addBlackList(requestBodyA, filePart, requestBodyB, fileMap)
                    .compose(RxHelper.<BaseResponse<Integer>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<Integer>>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse<Integer> response) {
                            if(response.getData() == 1)
                            {
                                ToastUtil.showLong(getResources().getString(R.string.is_person_black_no_add));

                            }else if(response.getData() == 2){

                                ToastUtil.showLong(getResources().getString(R.string.is_person_exist_no_add));

                            }else if(response.getData() == 4){
                                ToastUtil.showLong(getResources().getString(R.string.is_add_black_success));


                            }
                            else{
                                ToastUtil.showLong(getResources().getString(R.string.is_add_black_fail));

                            }

                        }
                    });
        } else {
            ElaTeamAddPersonService elaTeamAddPersonService = HttpFactory.getService(ElaTeamAddPersonService.class);
            elaTeamAddPersonService.moveToBlackList(memberGuid, requestBodyB, fileMap)
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


    private void addAttach() {
        List<String> names = new ArrayList<>();
        names.add(getResources().getString(R.string.is_take_photo));
        names.add(getResources().getString(R.string.is_take_image));
        showSelectDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        RxPermissions rxPermissions = new RxPermissions(getActivity());
                        rxPermissions.requestEach(Manifest.permission.CAMERA)
                                .subscribe(new Consumer<Permission>() {
                                    @Override
                                    public void accept(Permission permission) throws Exception {
                                        if (permission.granted) {
                                            // 用户已经同意该权限
                                            takePhoto();
                                        } else if (permission.shouldShowRequestPermissionRationale) {
                                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                            ToastUtil.showLong(getResources().getString(R.string.is_user_denial_authority));
                                        } else {
                                            // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                            ToastUtil.showLong(getResources().getString(R.string.is_denial_authority_operation));
                                        }
                                    }
                                });
                        break;
                    case 1:
                        takeImage();
                        break;
                }
            }
        }, names);
    }

    protected SelectDialog showSelectDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(getActivity(), R.style.transparentFrameWindowStyle, listener, names);
        if (!getActivity().isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @Override
    protected void picSuccess(Bitmap bitmap) {
        photoList.add(ImageUtil.saveImageToGallery(getActivity(), bitmap));
        addBlacklistPhotoAdapter.notifyDataSetChanged();
    }

    protected void getIllegalList() {
        IsMstOptionService isMstOptionService = HttpFactory.getService(IsMstOptionService.class);
        isMstOptionService.selectParents("GK0010", null).compose(RxHelper.<BaseResponse<List<IsMstOptions>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<IsMstOptions>>>(getActivity(), false) {
            @Override
            public void onSuccess(BaseResponse<List<IsMstOptions>> response) {
                if (response.getData().size() > 0) {
                    cbList.addAll(response.getData());
                    addBlacklistIllegalAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
