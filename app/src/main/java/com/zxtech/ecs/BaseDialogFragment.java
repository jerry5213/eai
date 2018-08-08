package com.zxtech.ecs;

import android.*;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;


import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxtech.ecs.model.UserManager;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.BitmapUtil;
import com.zxtech.ecs.util.FileUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.SelectDialog;
import com.zxtech.module.common.widget.ProgressDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static com.zxtech.ecs.common.Constants.REQ_ALBUM;
import static com.zxtech.ecs.common.Constants.REQ_TAKE_PHOTO;
import static com.zxtech.ecs.common.Constants.REQ_ZOOM;

/**
 * Created by syp523 on 2018/2/7.
 */

public abstract class BaseDialogFragment extends DialogFragment implements View.OnClickListener{

    private Unbinder binder;

    protected Observable baseResponseObservable = null;

    protected ProgressDialog progressDialog = null;

    private float wScale = 0.85f;

    private float hScale = 0.5f;

    private boolean isCanceledOnTouchOutside = true;

    protected Context mContext;

    private String imgPath = APPConfig.DOWN_LOAD_PATH + "temp.png";
    private Uri outputUri;
    private String scaleImgPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBottomShow()) {
            setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(getLayoutId(), null);
        binder = ButterKnife.bind(this, view);
        initUI(view, savedInstanceState);

        getDialog().setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        getDialog().setCancelable(isCanceledOnTouchOutside);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();  //获取对话框当前的参数值
        Window window = getDialog().getWindow();

        if (isBottomShow()) {
            p.gravity = Gravity.BOTTOM;
            p.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(p);
            //设置背景透明
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }else {
            DisplayMetrics d = getResources().getDisplayMetrics();
            p.height = (int) (d.heightPixels * hScale);
            p.width = (int) (d.widthPixels * wScale);
            window.setAttributes(p);

            int roundRadius = 20; //
            int strokeColor = getActivity().getResources().getColor(R.color.white);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(roundRadius);
            gd.setColor(strokeColor);
            window.setBackgroundDrawable(gd);//设置生效
        }

    }

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    public abstract boolean isBottomShow();

    //宽度比例
    public void setwScale(float wScale) {
        this.wScale = wScale;
    }

    //高度比例
    public void sethScale(float hScale) {
        this.hScale = hScale;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        isCanceledOnTouchOutside = canceledOnTouchOutside;
    }

    protected void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    protected void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void dismiss() {
        super.dismiss();
        dismissProgress();
        if (binder != null)
            binder.unbind();
        if (baseResponseObservable != null) {
            baseResponseObservable.unsubscribeOn(Schedulers.io());
        }
    }

    protected String getUserId() {
        return UserManager.getInstance().getUserId();
    }

    protected String getUserNo() {
        return UserManager.getInstance().getUserNo();
    }

    protected String getUserName() {
        return UserManager.getInstance().getUserName();
    }

    @Override
    public void onClick(View v) {

    }

    protected SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(getActivity(), R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!getActivity().isFinishing()) {
            dialog.show();
        }
        return dialog;
    }


    protected void selectMode() {
        List<String> names = new ArrayList<>();
        names.add(getString(R.string.shoot));
        names.add(getString(R.string.album));
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        RxPermissions rxPermissions = new RxPermissions(getActivity());
                        rxPermissions.requestEach(android.Manifest.permission.CAMERA)
                                .subscribe(new Consumer<Permission>() {
                                    @Override
                                    public void accept(Permission permission) throws Exception {
                                        if (permission.granted) {
                                            // 用户已经同意该权限
                                            openCamera(imgPath);
                                        } else if (permission.shouldShowRequestPermissionRationale) {
                                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                            ToastUtil.showLong(getString(R.string.msg12));
                                        } else {
                                            // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                            ToastUtil.showLong(getString(R.string.msg13));
                                        }
                                    }
                                });

                        break;
                    case 1:
                        openAlbum();
                        break;
                }

            }
        }, names);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK://调用图片选择处理成功
                String zoomImgPath = "";
                Bitmap bm = null;
                File temFile = null;
                File srcFile = null;
                File outPutFile = null;
                switch (requestCode) {
                    case REQ_TAKE_PHOTO:// 拍照后在这里回调
                        srcFile = new File(imgPath);
                        outPutFile = new File(FileUtil.generateImgePathInStoragePath());
                        outputUri = Uri.fromFile(outPutFile);
                        startPhotoZoom(getActivity(), srcFile, outPutFile, REQ_ZOOM);// 发起裁剪请求
                        break;

                    case REQ_ALBUM:// 选择相册中的图片
                        if (data != null) {
                            Uri sourceUri = data.getData();
                            String[] proj = {MediaStore.Images.Media.DATA};

                            // 好像是android多媒体数据库的封装接口，具体的看Android文档
                            Cursor cursor = getActivity().managedQuery(sourceUri, proj, null, null, null);

                            // 按我个人理解 这个是获得用户选择的图片的索引值
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                            cursor.moveToFirst();
                            // 最后根据索引值获取图片路径
                            imgPath = cursor.getString(column_index);

                            srcFile = new File(imgPath);
                            outPutFile = new File(FileUtil.generateImgePathInStoragePath());
                            outputUri = Uri.fromFile(outPutFile);
                            startPhotoZoom(getActivity(), srcFile, outPutFile, REQ_ZOOM);// 发起裁剪请求
                        }
                        break;

                    case REQ_ZOOM://裁剪后回调
                        //  Bundle extras = data.getExtras();
                        if (data != null) {
                            //  bm = extras.getParcelable("data");
                            if (outputUri != null) {
                                bm = BitmapUtil.decodeUriAsBitmap(outputUri);
                                //如果是拍照的,删除临时文件
//                                temFile = new File(imgPath);
//                                if (temFile.exists()) {
//                                    temFile.delete();
//                                }
                                scaleImgPath = FileUtil.saveBitmapByQuality(bm, 80);//复制并压缩到自己的目录并压缩
                                handleImage(scaleImgPath);

                                //进行上传，上传成功后显示新图片,这里不演示上传的逻辑，上传只需将scaleImgPath路径下的文件上传即可。
                                // custom_iv.setImageBitmap(bm);//显示在iv上
                            }
                        } else {
                            ToastUtil.showLong(getString(R.string.msg35));
                        }
                        break;
                }
                break;
        }
        if (data == null) {
            return;
        }

    }

    public void handleImage(String filePath) {

    }

    public void startPhotoZoom(Activity activity, File srcFile, File output, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(FileUtil.getImageContentUri(activity, srcFile), "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        if (android.os.Build.MANUFACTURER.contains("HUAWEI")) {// 华为特殊处理 不然会显示圆
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        } else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        // intent.putExtra("return-data", false);

        //        intent.putExtra(MediaStore.EXTRA_OUTPUT,
        //                Uri.fromFile(new File(FileUtils.picPath)));

        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection

        startActivityForResult(intent, requestCode);
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, REQ_ALBUM);
    }


    private void openCamera(String imgPath) {
        // 指定调用相机拍照后照片的储存路径
        File imgFile = new File(imgPath);
        Uri imgUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            //如果是7.0或以上
            imgUri = FileProvider.getUriForFile(getContext(), Util.getPackageName() + ".fileprovider", imgFile);
        } else {
            imgUri = Uri.fromFile(imgFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(intent, REQ_TAKE_PHOTO);

    }
}
