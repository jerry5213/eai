package com.zxtech.is.ui.team.activity;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ImageUtil;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.DropDownWindow;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.activity.BasePhotoActivity;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.common.IsMstOptions;
import com.zxtech.is.model.team.IsSlInstallationmember;
import com.zxtech.is.model.team.UsrDeptName;
import com.zxtech.is.service.common.IsMstOptionService;
import com.zxtech.is.service.team.ElaTeamAddPersonService;

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

import static com.zxtech.is.util.IDCard.IDCardValidate;

/**
 * Created by syp660 on 2018/4/25.
 */

public class ElaTeamAddPersonActivity extends BasePhotoActivity implements SlinstallationunitDialog.Unit, AutographDialogFragment.signSuccess{

    private List<String> list = new ArrayList<>(); //是否的下拉列表

    private List<UsrDeptName> usrDeptNames = new ArrayList<>();

    private IsSlInstallationmember isSlInstallationmember = new IsSlInstallationmember();

    private String mPhotoPath; //拍照后本机地址


    private List<IsMstOptions> cbList = new ArrayList<>();

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.team_add_person_a)
    TextView mTextViewA;
    @BindView(R2.id.team_add_person_b)
    TextView mTextViewB;
    @BindView(R2.id.team_add_person_c)
    TextView mTextViewC;
    @BindView(R2.id.team_add_person_d)
    TextView mTextViewD;
    @BindView(R2.id.team_add_person_name)
    EditText personName;
    @BindView(R2.id.team_add_person_phone)
    EditText personPhone;
    @BindView(R2.id.team_add_person_id)
    EditText personId;
//    @BindView(R.id.team_add_person_address)
//    EditText personAddress;

    @BindView(R2.id.team_add_person_iv)
    ImageView personPhoto;
    @BindView(R2.id.team_add_person_sign)
    ImageView personSign;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_elevator_team_addperson;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        list.add(getResources().getString(R.string.mr));
        list.add(getResources().getString(R.string.mrl));
        requestGetUsrDeptName();
        initTitleBar(mToolbar);
    }

    @OnClick({R2.id.team_add_person_sign,R2.id.team_add_person_blacklist, R2.id.team_add_person_iv, R2.id.team_add_person_create, R2.id.team_add_person_member, R2.id.team_add_person_a, R2.id.team_add_person_b, R2.id.team_add_person_c, R2.id.team_add_person_d})
    void OnClick(View view){
        int id = view.getId();
        if (id == R.id.team_add_person_create) {
            if (!check()) {
                return;
            }
            if (personSign.getDrawable() == null) {
                ToastUtil.showLong(getResources().getString(R.string.is_team_add_personk));
                return;
            }
            savePerson();

        } else if (id == R.id.team_add_person_a) {
            dropDownSolve(mTextViewA);

        } else if (id == R.id.team_add_person_b) {
            dropDownSolve(mTextViewB);

        } else if (id == R.id.team_add_person_c) {
            SlinstallationunitDialog updateDialog = SlinstallationunitDialog.newInstance();
            updateDialog.setUnit(this);
            updateDialog.show(((Activity) mContext).getFragmentManager(), "");

        } else if (id == R.id.team_add_person_d) {
            dropDownSolve(mTextViewD);

        } else if (id == R.id.team_add_person_member) {
            if (!check()) {
               return;
            }
            if (personSign.getDrawable() == null) {
                ToastUtil.showLong(getResources().getString(R.string.is_team_add_personk));
                return;
            }
            ElaTeamAddTeamMemberDialog addTeamMemberDialog = ElaTeamAddTeamMemberDialog.newInstance();
            addTeamMemberDialog.setIsSlInstallationmember(isSlInstallationmember);
            addTeamMemberDialog.setPhotoPath(mPhotoPath);
            addTeamMemberDialog.setBitmap(((BitmapDrawable) personSign.getDrawable()).getBitmap());
            addTeamMemberDialog.show(getActivity().getFragmentManager(), "");

        } else if (id == R.id.team_add_person_iv) {
            takePic();

        } else if (id == R.id.team_add_person_sign) {
            AutographDialogFragment mDialogSign = AutographDialogFragment.newInstance();
            mDialogSign.setSignsuccess(this);
            mDialogSign.show(((Activity) mContext).getFragmentManager(), "");

        } else if (id == R.id.team_add_person_blacklist) {
            if (check()) {
                ElaTeamAddBlacklistDialog mDialog = ElaTeamAddBlacklistDialog.newInstance();
                mDialog.setIsSlInstallationmember(isSlInstallationmember);
                mDialog.setPhotoPath(mPhotoPath);
                mDialog.show(((Activity) mContext).getFragmentManager(), "");
            }

        } else {
        }
    }

    @Override
    public void INSTALLATIONUNIT(String guid, String name) {
        mTextViewC.setText(name);
        mTextViewC.setTag(guid);
    }

    protected void dropDownSolve (TextView view) {
        List<Object> result = new ArrayList<>();
        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        view.setCompoundDrawables(null, null, image, null);
        int id = view.getId();
        if (id == R.id.team_add_person_d) {
            if (usrDeptNames.size() == 0) {
                ToastUtil.showLong(getResources().getString(R.string.is_no_data));
            }
            new DropDownWindow(mContext, view, usrDeptNames, view.getWidth(), -2) {
                @Override
                public void initEvents(final int p) {
                    mTextViewD.setText(usrDeptNames.get(p).getDeptName());
                    mTextViewD.setTag(usrDeptNames.get(p).getDeptId());
                    this.dismiss();
                }

                @Override
                public void dismissEvents() {
                    Drawable image = getResources().getDrawable(R.drawable.down);
                    image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                    mTextViewD.setCompoundDrawables(null, null, image, null);
                }
            };

        } else if (id == R.id.team_add_person_a) {
            if (usrDeptNames.size() == 0) {
                ToastUtil.showLong(getResources().getString(R.string.is_no_data));
            }
            new DropDownWindow(mContext, view, list, view.getWidth(), -2) {
                @Override
                public void initEvents(final int p) {
                    mTextViewA.setText(list.get(p));
                    if (getResources().getString(R.string.mr).equals(list.get(p))) {
                        mTextViewA.setTag("1");
                    } else {
                        mTextViewA.setTag("0");
                    }
                    this.dismiss();
                }

                @Override
                public void dismissEvents() {
                    Drawable image = getResources().getDrawable(R.drawable.down);
                    image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                    mTextViewA.setCompoundDrawables(null, null, image, null);
                }
            };

        } else if (id == R.id.team_add_person_b) {
            if (usrDeptNames.size() == 0) {
                ToastUtil.showLong(getResources().getString(R.string.is_no_data));
            }
            new DropDownWindow(mContext, view, list, view.getWidth(), -2) {
                @Override
                public void initEvents(final int p) {
                    mTextViewB.setText(list.get(p));
                    if (getResources().getString(R.string.mr).equals(list.get(p))) {
                        mTextViewB.setTag("1");
                    } else {
                        mTextViewB.setTag("0");
                    }
                    this.dismiss();
                }

                @Override
                public void dismissEvents() {
                    Drawable image = getResources().getDrawable(R.drawable.down);
                    image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                    mTextViewB.setCompoundDrawables(null, null, image, null);
                }
            };

        } else {
        }
    }
    private void requestGetUsrDeptName() {
        ElaTeamAddPersonService elaTeamAddPersonService = HttpFactory.getService(ElaTeamAddPersonService.class);
        elaTeamAddPersonService.getUsrDeptName()
                .compose(RxHelper.<BaseResponse<List<UsrDeptName>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<UsrDeptName>>>(getActivity(),true) {
                    @Override
                    public void onSuccess(BaseResponse<List<UsrDeptName>> response) {
                        usrDeptNames.clear();
                        usrDeptNames.addAll(response.getData());
                    }
                });
    }

    private void savePerson() {
        //签名图片
        Bitmap image = ((BitmapDrawable)personSign.getDrawable()).getBitmap();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, out);
        RequestBody requestBodySign =  RequestBody.create(MediaType.parse("application/octet-stream"),out.toByteArray());
        MultipartBody.Part fileSign = MultipartBody.Part.createFormData("sign", String.valueOf(new Date().getTime()) + ".png", requestBodySign);
       //本地图片路径
        File uploadFile = new File(mPhotoPath);
        //转换
        RequestBody photo = RequestBody.create(MediaType.parse("application/octet-stream"), uploadFile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", uploadFile.getName(), photo);
        String param = GsonUtils.toJson(isSlInstallationmember, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        ElaTeamAddPersonService elaTeamAddPersonService = HttpFactory.getService(ElaTeamAddPersonService.class);
        elaTeamAddPersonService.addPerson(requestBody,filePart,fileSign)
                .compose(RxHelper.<BaseResponse<Integer>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Integer>>(getActivity(),true) {
                    @Override
                    public void onSuccess(BaseResponse<Integer> response) {
                         if(response.getData() == 1)
                         {
                             ToastUtil.showLong(getResources().getString(R.string.is_person_black_no_add));

                         }else if(response.getData() == 2){

                             ToastUtil.showLong(getResources().getString(R.string.is_person_exist_no_add));

                         }
                         else if(response.getData() == 3){

                             ToastUtil.showLong(getResources().getString(R.string.is_add_person_success));

                             finish();
                         }
                       else {
                             ToastUtil.showLong(getResources().getString(R.string.is_add_person_success));
                         }
                    }
                });
    }
    private boolean check ()  {

        if (TextUtils.isEmpty(personName.getText())) {
            ToastUtil.showLong(getResources().getString(R.string.is_team_add_persona));
            return false;
        }
        if (TextUtils.isEmpty(personPhone.getText())) {
            ToastUtil.showLong(getResources().getString(R.string.is_team_add_personb));
            return false;
        } else {
            if(!isMobile(String.valueOf(personPhone.getText()))) {
                ToastUtil.showLong(getResources().getString(R.string.is_team_add_personc));
                return false;
            }
        }
        if (TextUtils.isEmpty(personId.getText())) {
            ToastUtil.showLong(getResources().getString(R.string.is_team_add_persond));
            return false;
        } else {
            if(!IDCardValidate(String.valueOf(personId.getText()))) {
                ToastUtil.showLong(getResources().getString(R.string.is_team_add_persone));
                return false;
            }
        }
//        if (TextUtils.isEmpty(personAddress.getText())) {
//            ToastUtil.showLong("身份证地址不能为空");
//            return false;
//        }
        if (TextUtils.isEmpty(mTextViewA.getText())) {
            ToastUtil.showLong(getResources().getString(R.string.is_team_add_personf));
            return false;
        }
        if (TextUtils.isEmpty(mTextViewB.getText())) {
            ToastUtil.showLong(getResources().getString(R.string.is_team_add_personh));
            return false;
        }
        if (TextUtils.isEmpty(mTextViewC.getText())) {
            ToastUtil.showLong(getResources().getString(R.string.is_choose_unit));
            return false;
        }
        if (TextUtils.isEmpty(mTextViewD.getText())) {
            ToastUtil.showLong(getResources().getString(R.string.is_team_add_personi));
            return false;
        }
        if (personPhoto.getDrawable() == null){
            ToastUtil.showLong(getResources().getString(R.string.is_team_add_personj));
            return false;
        }
        isSlInstallationmember.setName(String.valueOf(personName.getText()));
        isSlInstallationmember.setTelephone(String.valueOf(personPhone.getText()));
        isSlInstallationmember.setIdcard(String.valueOf(personId.getText()));
        isSlInstallationmember.setLicense(String.valueOf(mTextViewA.getTag()));
        isSlInstallationmember.setGkLicense(String.valueOf(mTextViewB.getTag()));
        isSlInstallationmember.setSlGuid(String.valueOf(mTextViewC.getTag()));
        isSlInstallationmember.setBranchGuid(String.valueOf(mTextViewD.getTag()));
        return true;
    }
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }
    //拍照成功回调
    @Override
    protected void picSuccess(Bitmap bitmap) {
        personPhoto.setImageBitmap(bitmap);
        mPhotoPath = ImageUtil.saveImageToGallery(mContext,bitmap);
    }

    private void takePic(){
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
    }

    @Override
    public void mPhoto(Bitmap photoSign) {
        personSign.setImageBitmap(photoSign);

    }
}
