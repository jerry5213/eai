package com.zxtech.is.common.dialog;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;

import io.reactivex.functions.Consumer;

import static com.zxtech.is.common.Constants.CAMERA_REQUEST;
import static com.zxtech.is.common.Constants.PHOTO_REQUEST_CUT;
import static com.zxtech.is.common.Constants.PHOTO_REQUEST_GALLERY;

/**
 * Created by duomi on 2018/5/1.
 */

public abstract class BasePhotoDialogFragment extends DialogFragment {

    private String fileprovider = BuildConfig.APPLICATION_ID + ".fileprovider";

    public void takePhoto() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    //当所有权限都允许之后，返回true
                    Uri fileUri = null;
                    File file = new File(Environment.getExternalStorageDirectory(), "temp1.jpg");
                    if (Build.VERSION.SDK_INT >= 24) {

                        fileUri = FileProvider.getUriForFile(getActivity(), fileprovider, file);
                    } else {
                        fileUri = Uri.fromFile(file);
                    }

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else {
                    //只要有一个权限禁止，返回false，
                    //下一次申请只申请没通过申请的权限
                    ToastUtil.showLong("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                }
            }
        });
    }

    public void takeImage() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    //当所有权限都允许之后，返回true
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
//
//        Uri mUriSave=Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"temp1.jpg"));//裁剪后图片保存路径
//
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//        intent.setType("image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 2);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 600);
//        intent.putExtra("outputY", 600);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", true);//截小图这里true，不用设置MediaStore.EXTRA_OUTPUT
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriSave);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true);
//        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                } else {
                    //只要有一个权限禁止，返回false，
                    //下一次申请只申请没通过申请的权限
                    ToastUtil.showLong("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                }
            }
        });
    }

    public void cropImageUri(Uri fileUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(fileUri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        Uri fileUri1 = null;
        File file1 = new File(Environment.getExternalStorageDirectory(), "crop.jpg");
//        if (Build.VERSION.SDK_INT >= 24) {
//            fileUri1 = FileProvider.getUriForFile(this, "com.zxtech.is.fileProvider", file1);
//        } else {
//            fileUri1 = Uri.fromFile(file1);
//        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file1));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            Uri fileUri = null;
            File file = new File(Environment.getExternalStorageDirectory(), "temp1.jpg");
            if (Build.VERSION.SDK_INT >= 24) {
                fileUri = FileProvider.getUriForFile(getActivity(), fileprovider, file);
            } else {
                fileUri = Uri.fromFile(file);
            }
            cropImageUri(fileUri);
        } else if (requestCode == PHOTO_REQUEST_GALLERY && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                cropImageUri(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            //更新UI

            Bitmap bitmap = null;
            Uri fileUri1 = null;
            File file1 = new File(Environment.getExternalStorageDirectory(), "crop.jpg");
            if (Build.VERSION.SDK_INT >= 24) {
                String fileprovider = BuildConfig.APPLICATION_ID + ".fileprovider";
                fileUri1 = FileProvider.getUriForFile(getActivity(), fileprovider, file1);
            } else {
                fileUri1 = Uri.fromFile(file1);
            }
            try {
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(fileUri1));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            picSuccess(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪后的图片，子类具体业务处理
     *
     * @param bitmap
     */
    protected abstract void picSuccess(Bitmap bitmap);
}
