package com.zxtech.ecs.ui.home.scheme.detail;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.RequireAdapter;
import com.zxtech.ecs.adapter.RequireAttachAdapter;
import com.zxtech.ecs.model.Require;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.BitmapUtil;
import com.zxtech.ecs.util.FileUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.MyItemDecoration;
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

import static com.zxtech.ecs.common.Constants.CODE_SPECIALNONSTANDARD;
import static com.zxtech.ecs.common.Constants.REQ_ALBUM;
import static com.zxtech.ecs.common.Constants.REQ_TAKE_PHOTO;
import static com.zxtech.ecs.common.Constants.REQ_ZOOM;

/**
 * 非标需求
 * Created by syp523 on 2018/1/22.
 */

public class RequireFragment extends BaseSchemeDetailFragment implements RequireAttachAdapter.RequireAttachAdapterCallBack {
    @BindView(R.id.content_rv)
    RecyclerView content_rv;
    @BindView(R.id.image_rv)
    RecyclerView image_rv;
    private RequireAdapter adapter = null;
    private List<Require> datas = new ArrayList<>();
    public List<String> attachDatas = new ArrayList<>();

    private RequireAttachAdapter attachAdapter = null;
    private Uri outputUri;
    private String imgPath = APPConfig.DOWN_LOAD_PATH + "temp.png";
    private String scaleImgPath;
    //附件url，guid
    private HashMap<String, String> attachMap = new HashMap<>();

    private boolean isEdit;


    public static RequireFragment newInstance() {
        Bundle args = new Bundle();
        RequireFragment fragment = new RequireFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_require;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        attachDatas.add(null);
        //回显附件
        String specialNonStandardPaths = getParamsValue("SpecialNonStandardPath");
        if (!TextUtils.isEmpty(specialNonStandardPaths)) {
            String[] split = specialNonStandardPaths.split("\\^");
            for (int i = 0; i < split.length; i++) {
                String[] split1 = split[i].split(",");
                if (split1.length > 1) {
                    isEdit = true;
                    attachMap.put(split1[1], split1[0]);
                    attachDatas.add(split1[1]);
                }
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        content_rv.setLayoutManager(layoutManager);

        adapter = new RequireAdapter(getActivity(), R.layout.item_require, datas);
        content_rv.addItemDecoration(new MyItemDecoration(1));
        content_rv.setAdapter(adapter);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        image_rv.setLayoutManager(layoutManager1);
        attachAdapter = new RequireAttachAdapter(getActivity(), R.layout.item_require_image, attachDatas);
        attachAdapter.setCallBack(this);
        image_rv.setAdapter(attachAdapter);

        attachAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position == 0) {
                    if (attachDatas.size() < 6) {
                        selectMode();
                    } else {
                        ToastUtil.showLong(getString(R.string.msg1));
                    }
                }
            }


            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void removeAttach(final int position) {
        if (isEdit) {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(attachMap.get(attachDatas.get(position)));
            jsonObject.add("GuidList", jsonArray);
            baseResponseObservable = HttpFactory.getApiService().delNonStandardFile(jsonObject.toString());
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            attachDatas.remove(position);
                            attachAdapter.notifyDataSetChanged();

                        }
                    });
        } else {
            attachDatas.remove(position);
            attachAdapter.notifyDataSetChanged();
        }
    }

    private void addAttach(String imgPath) {
        if (isEdit) {
            Map<String, RequestBody> bodyMap = new HashMap<>();
            bodyMap.put("guid", RequestBody.create(MediaType.parse("text/plain"), getParamsValue("Guid")));
            bodyMap.put("userGuid", RequestBody.create(MediaType.parse("text/plain"), getUserId()));
            bodyMap.put("files\";filename=\"attach.png", RequestBody.create(MediaType.parse("image/png"), new File(imgPath)));
            baseResponseObservable = HttpFactory.getApiService().addNonStandardFile(bodyMap);
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            attachDatas.add(scaleImgPath);
                            attachAdapter.notifyDataSetChanged();

                        }
                    });
        } else {
            attachDatas.add(scaleImgPath);
            attachAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (!TextUtils.isEmpty(getParamsValue(CODE_SPECIALNONSTANDARD))) {
            String nonStandard = getParamsValue(CODE_SPECIALNONSTANDARD);
            String[] split = nonStandard.split("\\^");
            for (String sp : split) {
                datas.add(new Require(sp));
            }
            adapter.notifyDataSetChanged();
        }

    }

    private void selectMode() {
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

    @OnClick(R.id.add_require_btn)
    public void addRequire() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialog_edit = inflater.inflate(R.layout.dialog_edit, null);
        builder.setView(dialog_edit).setTitle(R.string.requirement)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_userName = (EditText) dialog_edit.findViewById(R.id.content_et);
                        if (!TextUtils.isEmpty(et_userName.getText())) {
                            datas.add(new Require("#" + et_userName.getText().toString()));
                            adapter.notifyDataSetChanged();
                        } else {

                        }

                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
//        Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        button.setTextColor(getResources().getColor(R.color.black_28));
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
            imgUri = FileProvider.getUriForFile(getContext(), Util.getPackageName() + ".fileprovider", imgFile);
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
//                                temFile = new File(imgPath);
//                                if (temFile.exists()) {
//                                    temFile.delete();
//                                }
                                scaleImgPath = FileUtil.saveBitmapByQuality(bm, 80);//复制并压缩到自己的目录并压缩
                                addAttach(scaleImgPath);

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

    public List<String> getAttachDatas() {
        return attachDatas;
    }


    public String getSelectParams() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < datas.size(); i++) {
            stringBuffer.append(datas.get(i).getContent());
            if (i != datas.size() - 1) {
                stringBuffer.append("^");
            }
        }
        return stringBuffer.toString();
    }

    @Override
    public void scriptReturnParam(List<ScriptReturnParam> returnParams) {

    }

    public void putParams(String key, String value) {
        ((SchemeDetailActivity) getActivity()).paramMap.put(key, value);
    }

}
