package com.zxtech.is.ui.person.activity;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxtech.is.widget.SelectDialog;
import com.zxtech.is.BuildConfig;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxtech.is.util.ImageUtil;

import com.bumptech.glide.Glide;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.ToastUtil;

import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.activity.BasePhotoActivity;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.person.PersonMember;
import com.zxtech.is.service.person.PersonCheckService;
import com.zxtech.is.ui.team.activity.AutographDialogFragment;
import com.zxtech.is.ui.team.activity.ElaTeamAddBlacklistDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by syp692 on 2018/5/3.
 */

public class PersonCheckDetailedActivity extends BasePhotoActivity implements View.OnClickListener, AutographDialogFragment.signSuccess, PersonCheckProjectDialog.PROJECT {


    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.team_check_person_name)
    TextView personName;
    @BindView(R2.id.team_check_person_phone)
    TextView personPhone;
    @BindView(R2.id.team_check_person_id)
    TextView personId;
    @BindView(R2.id.team_check_person_5)
    TextView license;
    @BindView(R2.id.team_check_person_6)
    TextView gklicense;
    @BindView(R2.id.team_check_person_c)
    TextView unitName;
    @BindView(R2.id.team_check_person_d)
    TextView deptname;
    @BindView(R2.id.check_person_iv)
    ImageView photo;
    @BindView(R2.id.check_photo_1)
    Button makePhoto;
    @BindView(R2.id.check_photo_2)
    Button savePhoto;
    @BindView(R2.id.check_person_content_h)
    ImageView checkPersonName;
    @BindView(R2.id.team_check_person_cancel)
    Button teamcheckpersoncancel;


    private Bitmap bitmapTemp;
    private Bitmap photoName;
    private String guid;
    private PersonMember personMember = new PersonMember();
    private List<PersonMember> personMemberList = new ArrayList<PersonMember>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_check_detailed;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(mToolbar);
        initdata();


    }


    private void initdata() {
        String guid = getIntent().getStringExtra("guid");
        PersonCheckService personCheckService = HttpFactory.getService(PersonCheckService.class);
        personCheckService.selectPersonCheckDetail(guid).compose(RxHelper.<PageResponse<List<PersonMember>>>rxSchedulerHelper()).subscribe(new DefaultObserver<PageResponse<List<PersonMember>>>(getActivity(), false) {


            @Override
            public void onSuccess(PageResponse<List<PersonMember>> response) {


                if (response.getData().size() == 0) {
                    ToastUtil.showLong(getResources().getString(R.string.is_no_retrieved_data));
                    return;
                }
                personMember = response.getData().get(0);
                personName.setText(personMember.getName());
                personPhone.setText(personMember.getTelephone());
                personId.setText(personMember.getIdcard());

                if ("1".equals(personMember.getLicense())) {
                    license.setText(getResources().getString(R.string.mr));
                } else {
                    license.setText(getResources().getString(R.string.mrl));
                }
                if ("1".equals(personMember.getGkLicense())) {
                    gklicense.setText(getResources().getString(R.string.mr));
                } else {
                    gklicense.setText(getResources().getString(R.string.mrl));
                }
                unitName.setText(personMember.getUnitname());
                deptname.setText(personMember.getDeptName());

                //加载头像
                String guid = personMember.getAttachguid();
                String url = BuildConfig.BASE_URL + "s1/downloadAttach?guid=" + guid;
                Glide.with(mContext).load(url).error(R.drawable.is_contact_grey).into(photo);

                //加载签名
                String AutographGuid = personMember.getAutographGuid();
                String Autographurl = BuildConfig.BASE_URL + "s1/downloadAttach?guid=" + AutographGuid;
                Glide.with(mContext).load(Autographurl).into(checkPersonName);

            }


            @Override
            public void onFail() {
                super.onFail();

            }

        });


    }




    @OnClick({R2.id.check_photo_1, R2.id.check_photo_2, R2.id.check_person_content_h, R2.id.team_check_person_create, R2.id.team_check_person_cancel, R2.id.team_check_person_cupdate, R2.id.check_person_iv})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.check_person_iv) {
            addAttach();

            //增加签名
        } else if (id == R.id.check_person_content_h) {
            AutographDialogFragment mDialogSign = AutographDialogFragment.newInstance();
            mDialogSign.setSignsuccess(this);
            mDialogSign.show(((Activity) mContext).getFragmentManager(), "");

            //通过认证
        } else if (id == R.id.team_check_person_create) {
            if (check()) {
                PersonCheckProjectDialog mDialogProject = PersonCheckProjectDialog.newInstance(mContext);
                mDialogProject.setProject(this);
                mDialogProject.params.put("memberGuid", personMember.getGuid());
                mDialogProject.params.put("flag", "1");
                mDialogProject.show(((Activity) mContext).getFragmentManager(), "");
            }


            //加入黑名单
        } else if (id == R.id.team_check_person_cancel) {
            ElaTeamAddBlacklistDialog mDialog = ElaTeamAddBlacklistDialog.newInstance();
            mDialog.setMemberGuid(personMember.getGuid());
            mDialog.show(((Activity) mContext).getFragmentManager(), "");

            //信息更新
        } else if (id == R.id.team_check_person_cupdate) {
            if (check()) {
                PersonCheckProjectDialog rDialogProject = PersonCheckProjectDialog.newInstance(mContext);
                rDialogProject.setProject(this);
                rDialogProject.params.put("memberGuid", personMember.getGuid());
                rDialogProject.params.put("flag", "2");
                rDialogProject.show(((Activity) mContext).getFragmentManager(), "");
            }


        }
    }


    private  boolean check(){
        String Attachguid = personMember.getAttachguid();
        //如果照片为空：后台返回的照片id为空并且相机返回值为空
        if((bitmapTemp==null||"".equals(bitmapTemp))&&(Attachguid==null||"".equals(Attachguid)))
        {
            ToastUtil.showLong(getResources().getString(R.string.is_team_add_personj));
            return false;
        }
       //没有新签名的话，不能通过验证，不能信息待更新
        if(photoName==null||"".equals(photoName))
        {
            ToastUtil.showLong(getResources().getString(R.string.is_team_add_personk));
            return false;
        }

        return true;
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


    public void savePic() {

        photo.getDrawingCache();
        if (bitmapTemp == null || "".equals(bitmapTemp)) {
            ToastUtil.showLong("请上传照片");
            return;
        }
        String mPhotoPath = ImageUtil.saveImageToGallery(mContext, bitmapTemp);
        if (mPhotoPath == null || "".equals(mPhotoPath)) {
            ToastUtil.showLong(getResources().getString(R.string.is_first_photo));
            return;
        }
        File uploadFile = new File(mPhotoPath);

        //转换
        RequestBody photo = RequestBody.create(MediaType.parse("application/octet-stream"), uploadFile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", uploadFile.getName(), photo);
        String guid = getIntent().getStringExtra("guid");
        PersonCheckService personCheckService = HttpFactory.getService(PersonCheckService.class);
        personCheckService.savePersonCheckPhoto(guid, filePart).compose(RxHelper.<BaseResponse<Integer>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<Integer>>(getActivity(), false) {


            @Override
            public void onSuccess(BaseResponse<Integer> response) {


            }


            @Override
            public void onFail() {
                super.onFail();
                ToastUtil.showLong(getResources().getString(R.string.is_fail_upload));
            }

        });


    }

    @Override
    public void mPhoto(Bitmap photoSign) {
        checkPersonName.setImageBitmap(photoSign);
        photoName = photoSign;
        saveCheckPersonName();


    }

    //相机回调
    @Override
    protected void picSuccess(Bitmap bitmap) {

        String url = ImageUtil.saveImageToGallery(mContext, bitmap);
        Glide.with(mContext).load(url).into(photo);
//        photo.setImageBitmap(bitmap);
        bitmapTemp = bitmap;
        savePic();

    }
    public void saveCheckPersonName() {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        photoName.compress(Bitmap.CompressFormat.JPEG, 100, out);
//        RequestBody requestBodySign =  RequestBody.create(MediaType.parse(""),out.toByteArray());
//        MultipartBody.Part fileSign = MultipartBody.Part.createFormData("sign", String.valueOf(new Date().getTime()) + ".png", requestBodySign);
//
        savePic();

        Bitmap image = ((BitmapDrawable) checkPersonName.getDrawable()).getBitmap();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, out);
        RequestBody requestBodySign = RequestBody.create(MediaType.parse("application/octet-stream"), out.toByteArray());
        MultipartBody.Part fileSign = MultipartBody.Part.createFormData("sign", String.valueOf(new Date().getTime()) + ".png", requestBodySign);


        String guid = getIntent().getStringExtra("guid");
        PersonCheckService personCheckService = HttpFactory.getService(PersonCheckService.class);
        personCheckService.savePersonCheckName(guid, fileSign).compose(RxHelper.<BaseResponse<Integer>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<Integer>>(getActivity(), false) {


            @Override
            public void onSuccess(BaseResponse<Integer> response) {

                if (response.getMessage().equals("1"))
                    ToastUtil.showLong(getResources().getString(R.string.is_successfully_signature));

            }


            @Override
            public void onFail() {
                super.onFail();

            }

        });


    }

    @Override
    public void Projectinfo(String projectId, String projectName) {

    }
}
