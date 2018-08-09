package com.zxtech.mt.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.zxtech.mt.adapter.MtCheckPhotoAdapter;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.common.MultipartRequestUpload;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.entity.HttpResult;
import com.zxtech.mt.entity.MtWorkPlan;
import com.zxtech.mt.entity.MtWorkPlanAddtion;
import com.zxtech.mt.imagepicker.ImageGridActivity;
import com.zxtech.mt.imagepicker.ImageItem;
import com.zxtech.mt.imagepicker.ImagePicker;
import com.zxtech.mt.utils.BitmapUtil;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mtos.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syp523 on 2017/7/27.
 */

public class MtCheckPhotoActivity extends BaseActivity implements MtCheckPhotoAdapter.PhotoCallBack {

    private Button photo;

    private static int CAMERA_REQUEST = 0x001;
    private static int PHOTO_REQUEST_GALLERY = 0x002;
    private static  int PHOTO_REQUEST_CUT = 0x003;
    private String photoPath = "";
    private static final int RESULT_PHOTO = 1031;

    private ListView listview;


    private MtCheckPhotoAdapter mAdapter = null;

    private MtWorkPlan work = null;

    @Override
    protected void onCreate() {

        View view = mInfalter.inflate(R.layout.activity_checkphoto, null);
        main_layout.addView(view);
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lps);
        title_textview.setText(getString(R.string.check_photo));
        setBottomLayoutHide();
    }

    @Override
    protected void findView() {
        listview = (ListView) findViewById(R.id.listview);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {
        work = (MtWorkPlan) getIntent().getSerializableExtra("bean");
    }

    @Override
    protected void initView() {




        Map<String, String> param = new HashMap<>();
        param.put("plan_id", work.getId());
        HttpUtil.getInstance(mContext).request("/mtmo/getmtplanaddtion.mo", param, new HttpCallBack<List<MtWorkPlanAddtion>>() {
            @Override
            public void onSuccess(List<MtWorkPlanAddtion> list) {
                mAdapter = new MtCheckPhotoAdapter(mContext,list,R.layout.item_check_photo);
                mAdapter.setListener(MtCheckPhotoActivity.this);
                listview.setAdapter(mAdapter);
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showLong(mContext, getString(R.string.msg_3));
            }
        });

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
//            case R.id.photo:
//
//
//                break;

        }
    }

    private List<MtWorkPlanAddtion> handleAddtion(List<MtWorkPlanAddtion> addtions){
        List<MtWorkPlanAddtion> list = new ArrayList<>();
        for (MtWorkPlanAddtion addtion:addtions) {

        }
        return list;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == 10010) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    Bitmap bitmap= BitmapFactory.decodeFile(images.get(0).path);
                    bitmap = BitmapUtil.addTimeFlag(bitmap);
                    String s = BitmapUtil.saveImageToGallery(mContext, bitmap);
                    bitmap.recycle();
                    Map<String, String> params = new HashMap<>();
                    params.put("plan_id",work.getId());
                    params.put("type",mAdapter.getItem(selectedPosition).getDict_name());
                    params.put("type_id","0");

                    MultipartRequestUpload upload = new MultipartRequestUpload((String)SPUtils.get(getApplicationContext(),"IP","") + "/mtmo/loadmtcheckphoto.mo?_token="+SPUtils.get(getApplicationContext(),"token","").toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            HttpResult result = gson.fromJson(s,new TypeToken<HttpResult>(){}.getType());
                            if (Constants.SUCCESS.equals(result.getStatus())) {
                                if (result.getData() != null) {
                                    mAdapter.getItem(selectedPosition).getImages().add(result.getData().toString());
                                }
                                mAdapter.notifyDataSetChanged();
                                ToastUtil.showLong(mContext, "上传成功");
                            }
                        }
                    },"file",new File(s),params);
                    upload.setRetryPolicy(new DefaultRetryPolicy(500 * 1000, 1, 1.0f));
                    mQueue.add(upload);

                }
            }
        }
        if (requestCode==103) {
            Bitmap bitmap= BitmapFactory.decodeFile(UIController.SIGN_FILE_PATH);


        }
    }

    private void uploadPhoto(){

    }

    private int selectedPosition = 0;
    @Override
    public void selectedPhoto(int position) {
        this.selectedPosition = position;
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        photoPath = UIController.SD_DIR_PATH + "/"+System.currentTimeMillis()+".png";
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photoPath)));
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);

        Intent intent = new Intent(mContext, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
        startActivityForResult(intent, 10010);
    }
}
