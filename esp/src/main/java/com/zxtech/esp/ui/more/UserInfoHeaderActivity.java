package com.zxtech.esp.ui.more;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxtech.common.util.F;
import com.zxtech.common.util.SPCache;
import com.zxtech.common.util.T;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.data.bean.UserInfoVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import java.io.File;
import java.io.IOException;

import g.api.http.GHttp;
import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/7/18.
 */

public class UserInfoHeaderActivity extends AppCompatActivity {

    private static final String TAG = UserInfoHeaderActivity.class.getSimpleName();
    private String state;
    private Uri imageUri;
    private String realFilePath;
    private int flag = 0;
    private static final int TAKE_PHOTO = 1;//从相机选择
    private static final int PICTURE_CAPTURE = 2;//从相册选择
    private static final int ZOOM_AFTER_TAKE_PHOTO = 3;//从相机选择后截图
    private static final int ZOOM_AFTER_PICTURE_CAPTURE = 4;//从照片选择后截图

    private UserInfoVO.Data data;
    private ImageView owner_photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (UserInfoVO.Data) getIntent().getSerializableExtra(C.DATA);
        setContentView(R.layout.activity_user_info_header);
        //存储介质
        state = Environment.getExternalStorageState();
        step();
    }

    private void step() {

        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        BizUtil.setIconFont(tv_back, "\ue620");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.choose_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });

        findViewById(R.id.take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(null == imageUri || flag == 0){
                    //T.showToast(v.getContext(),"头像无改变");
                }else{
                    GRequestParams param = new GRequestParams();
                    realFilePath = F.getRealFilePath(v.getContext(), imageUri);
                    String url = URL.getInstance().uploadHeaderPhotoUrl();
                    param.addBodyParameter(GHttp.FILE_PREFIX + "file",realFilePath);
                    param.addBodyParameter("userId", MyApp.getUserId());
                    MyApp.getInstance().getHttp().sendFile(new GRequestData(url, param), new GsonCallBack<JsonElementVO>(v.getContext()){
                        @Override
                        protected void onDoSuccess(JsonElementVO bean) {
                            String file_url = bean.getData().getAsJsonObject().get("file_url").toString();
                            file_url = file_url.replace("\"","");
                            data.setPhoto_url(file_url);

                            Intent intent = new Intent();
                            intent.putExtra("backInfoHeader",file_url);
                            setResult(1,intent);
                            finish();
                        }
                    }, true);
                }
            }
        });

        owner_photo = (ImageView) findViewById(R.id.owner_photo);
        showMyHeaderImage(URL.getInstance().getFullUrl(data.getPhoto_url()));
    }

    private void showMyHeaderImage(String uri) {
        DisplayImageOptions opts = BizUtil.getDefaultImageOPTSBuilder(T.dip2px(this, 75.0f)).showImageOnFail(R.mipmap.avatar_default).build();
        ImageLoader.getInstance().displayImage(uri, owner_photo, opts);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult requestCode:" + requestCode + " resultCode:" + resultCode + " data:" + data);
        String filepath;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    filepath = SPCache.getInstance(this).getString("filepath");
                    imageUri = Uri.fromFile(new File(filepath));
                    if (imageUri != null) {
                        startPhotoZoom(imageUri, ZOOM_AFTER_TAKE_PHOTO);
                    }
                    break;
                case PICTURE_CAPTURE:
                    imageUri = data.getData();
                    String url = imageUri.toString();
                    if (imageUri != null) {
                        startPhotoZoom(imageUri, ZOOM_AFTER_PICTURE_CAPTURE);
                    }
                    break;
                case ZOOM_AFTER_TAKE_PHOTO:
                    // 拿到照相截取后的剪切数据
                    filepath = SPCache.getInstance(this).getString("filepath");
                    imageUri = Uri.fromFile(new File(filepath));
                    if (imageUri != null) {
                        showMyHeaderImage(imageUri.toString());
                    }
                    flag = 1;
                    break;
                case ZOOM_AFTER_PICTURE_CAPTURE:
                    // 拿到从相册选择截取后的剪切数据
                    if (imageUri != null) {
                        showMyHeaderImage(imageUri.toString());
                    }
                    flag = 2;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 选择图片
     */
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, PICTURE_CAPTURE);
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        //创建File对象，用于存储拍照后的图片
        //将此图片存储于SD卡的根目录下
        File outputImage = new File(Environment.getExternalStorageDirectory(), "img_"+System.currentTimeMillis()+".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将File对象转换成Uri对象
        //Uri表标识着图片的地址
        imageUri = Uri.fromFile(outputImage);
        SPCache.getInstance(this).setString("filepath", outputImage.getAbsolutePath());
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(getImageByCamera, TAKE_PHOTO);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    /*
     * 图片裁剪
     */
    private void startPhotoZoom(Uri uri, int i) {
        Log.d(TAG, "startPhotoZoom uri:" + uri);
        if (uri == null) {
            Toast.makeText(getApplicationContext(), "选择图片出错！", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        //如果为true,则通过 Bitmap bmap = data.getParcelableExtra("data")取出数据
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, i);
    }

}
