package com.zxtech.ecs.ui.me.feedback;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.RequireAttachAdapter;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.scheme.SchemeActivity;
import com.zxtech.ecs.util.BitmapUtil;
import com.zxtech.ecs.util.FileUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.SelectDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.zxtech.ecs.common.Constants.REQ_ALBUM;
import static com.zxtech.ecs.common.Constants.REQ_TAKE_PHOTO;
import static com.zxtech.ecs.common.Constants.REQ_ZOOM;

/**
 * 反馈
 * Created by syp523 on 2018/4/16.
 */

public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.image_rv)
    RecyclerView image_rv;
    @BindView(R.id.suggestion_et)
    EditText suggestion_et;
    @BindView(R.id.contact_phone_et)
    EditText contact_phone_et;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.submit_btn)
    Button submit_btn;

    public List<String> attachDatas = new ArrayList<>();
    private RequireAttachAdapter attachAdapter = null;
    private Uri outputUri;
    private String imgPath = APPConfig.DOWN_LOAD_PATH + "temp.png";
    private String scaleImgPath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.feedback));

        attachDatas.add(null);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        image_rv.setLayoutManager(layoutManager1);
        attachAdapter = new RequireAttachAdapter(getActivity(), R.layout.item_require_image, attachDatas);
        image_rv.setAdapter(attachAdapter);

        attachAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position == attachDatas.size() - 1) {
                    if (attachDatas.size() < 6) {
                        addAttach();
                    } else {
                        ToastUtil.showLong(getString(R.string.msg1));
                    }
                } else {
                    attachDatas.remove(position);
                    attachAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    @OnClick({R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                submitAction();
                break;
        }
    }

    private void submitAction() {
        if (TextUtils.isEmpty(suggestion_et.getText())) {
            ToastUtil.showLong(getString(R.string.msg16));
            return;
        }
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if (!TextUtils.isEmpty(contact_phone_et.getText())) {
            bodyMap.put("contact_phone", RequestBody.create(MediaType.parse("text/plain"), contact_phone_et.getText().toString()));
        }
        if (!TextUtils.isEmpty(getUserId())) {
            bodyMap.put("user_id", RequestBody.create(MediaType.parse("text/plain"), getUserId()));
        }
        if (!TextUtils.isEmpty(getUserId())) {
            bodyMap.put("create_user", RequestBody.create(MediaType.parse("text/plain"), getUserId()));
        }
        bodyMap.put("suggestion", RequestBody.create(MediaType.parse("text/plain"), suggestion_et.getText().toString()));
        for (int i = 0; i < attachDatas.size(); i++) {
            if (!TextUtils.isEmpty(attachDatas.get(i))) {
                bodyMap.put("files\";filename=\"suggestion"+i+".png", RequestBody.create(MediaType.parse("image/png"), new File(attachDatas.get(i))));
            }
        }

        baseResponseObservable = HttpFactory.getApiService().submitFeedback(bodyMap);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<Map<String, String>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Map<String, String>>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Map<String, String>>> response) {
                        ToastUtil.showLong(getString(R.string.msg17));
                        finish();
                    }
                });
    }

    private void addAttach() {
        List<String> names = new ArrayList<>();
        names.add(getString(R.string.shoot));
        names.add(getString(R.string.album));
        showDialog(new SelectDialog.SelectDialogListener() {
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

    /**
     * 开启摄像机
     */
    private void openCamera(String imgPath) {
        // 指定调用相机拍照后照片的储存路径
        File imgFile = new File(imgPath);
        Uri imgUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            //如果是7.0或以上
            imgUri = FileProvider.getUriForFile(mContext, Util.getPackageName() + ".fileprovider", imgFile);
        } else {
            imgUri = Uri.fromFile(imgFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(intent, REQ_TAKE_PHOTO);
    }

    /**
     * 打开相册
     */
    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, REQ_ALBUM);
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
                                temFile = new File(imgPath);
                                if (temFile.exists()) {
                                    temFile.delete();
                                }
                                scaleImgPath = FileUtil.saveBitmapByQuality(bm, 80);//复制并压缩到自己的目录并压缩
                                attachDatas.add(0, scaleImgPath);
                                attachAdapter.notifyDataSetChanged();
                                //进行上传，上传成功后显示新图片,这里不演示上传的逻辑，上传只需将scaleImgPath路径下的文件上传即可。
                                // custom_iv.setImageBitmap(bm);//显示在iv上
                            }
                        } else {
                            ToastUtil.showLong("选择图片发生错误，图片可能已经移位或删除");
                        }
                        break;
                }
                break;
        }
        if (data == null) {
            return;
        }

    }

    /**
     * 发起剪裁图片的请求
     *
     * @param activity    上下文
     * @param srcFile     原文件的File
     * @param output      输出文件的File
     * @param requestCode 请求码
     */
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
}
