package com.zxtech.mt.activity;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.Listener;
import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.common.MultipartRequestUpload;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.entity.HttpResult;
import com.zxtech.mt.imagepicker.ImageGridActivity;
import com.zxtech.mt.imagepicker.ImageItem;
import com.zxtech.mt.imagepicker.ImagePicker;
import com.zxtech.mt.service.LocationService;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.PushUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.widget.SelectDialog;
import com.zxtech.mtos.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 我的
 * Created by Chw on 2017/08/01.
 */
public class MyActivity extends BaseActivity{
    private Context mContext;

    private TextView location_textview,work_check_textview,emp_name_textview,login_name_textview
            ,elevator_info_textview,setting_textview,comp_textview,line_code_textview,last_money_textview,
            line_elevator_textview,this_plan_count_textview,this_act_count_textview,close_end_textview;

//    private RatingBar level_ratingbar;

    private LinearLayout work_check_layout,location_layout,elevator_info_layout,setting_layout,sign_layout;

    private ImageView photo_imageview;

    private final  static int REQUEST_CODE_SELECT = 123;


    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_my, null);
        main_layout.addView(view);
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lps);
        mContext = this;
        setBackHide();
        title_textview.setText(getString(R.string.my_info));
        menu5.setTextColor(getResources().getColor(R.color.bg_blue));
        menu5_bg.setText("\ue7d9");
        menu5_bg.setTextColor(mContext.getResources().getColor(R.color.c_theme));
    }

    @Override
    protected void findView() {
        location_textview = (TextView) findViewById(R.id.location_textview);
        work_check_textview = (TextView) findViewById(R.id.work_check_textview);
//        level_ratingbar = (RatingBar) findViewById(R.id.level_ratingbar);
        emp_name_textview = (TextView) findViewById(R.id.emp_name_textview);
        login_name_textview = (TextView) findViewById(R.id.login_name_textview);
        line_elevator_textview = (TextView) findViewById(R.id.line_elevator_textview);
        this_plan_count_textview = (TextView) findViewById(R.id.this_plan_count_textview);
        this_act_count_textview = (TextView) findViewById(R.id.this_act_count_textview);
        close_end_textview = (TextView) findViewById(R.id.close_end_textview);
        elevator_info_textview = (TextView) findViewById(R.id.elevator_info_textview);
        setting_textview = (TextView) findViewById(R.id.setting_textview);
        comp_textview = (TextView) findViewById(R.id.comp_textview);
        line_code_textview = (TextView) findViewById(R.id.line_code_textview);
        work_check_layout = (LinearLayout) findViewById(R.id.work_check_layout);
        location_layout = (LinearLayout) findViewById(R.id.location_layout);
        elevator_info_layout = (LinearLayout) findViewById(R.id.elevator_info_layout);
        setting_layout = (LinearLayout) findViewById(R.id.setting_layout);
        photo_imageview = (ImageView) findViewById(R.id.photo_imageview);
        sign_layout = (LinearLayout)findViewById(R.id.sign_layout);
       // mt_work_form_layout = (LinearLayout)findViewById(R.id.mt_work_form_layout);
    }

    @Override
    protected void setListener() {
        location_layout.setOnClickListener(this);
        work_check_layout.setOnClickListener(this);
        elevator_info_layout.setOnClickListener(this);
        setting_layout.setOnClickListener(this);
        sign_layout.setOnClickListener(this);
        photo_imageview.setOnClickListener(this);
       // mt_work_form_layout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Intent intent = new Intent(mContext, LocationService.class);
        intent.putExtra("emp_id",SPUtils.get(this,"emp_id","").toString());
        startService(intent);
//        level_ratingbar.setRating(4);
        emp_name_textview.setText(SPUtils.get(mContext,"emp_name","").toString());
        login_name_textview.setText(SPUtils.get(mContext,"username","").toString());
        comp_textview.setText(SPUtils.get(mContext,"comp_name","").toString());
        line_code_textview.setText(SPUtils.get(mContext,"grp_code","").toString());
        //读取本地缓存签名
        Glide.with(mContext).load(SPUtils.get(mContext,"RESOURCE_URL","")+SPUtils.get(mContext,"emp_photo_url","").toString()).placeholder(R.drawable.logo)
                .error(R.drawable.logo).into(photo_imageview);
        pushManager ();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showMoney();

    }


    private void showMoney(){
        Map<String, String> param = new HashMap<>();
        param.put("emp_id", SPUtils.get(mContext,"emp_id","").toString());
        param.put("tenant_code", SPUtils.get(mContext,"tenant_code","").toString());

        HttpUtil.getInstance(mContext).request("/mtmo/statisticsplan.mo", param, new HttpCallBack<Map<String,Object>>() {
            @Override
            public void onSuccess(Map<String,Object> map) {
                if (map.containsKey("last_num")){
                    line_elevator_textview.setText(map.get("last_num")+"台");
                }
                if (map.containsKey("current_plan_num")){
                    this_plan_count_textview.setText(map.get("current_plan_num")+"台");
                }
                if (map.containsKey("current_num")){
                    this_act_count_textview.setText(map.get("current_num")+"台");
                }
                if (map.containsKey("close_end_date")){
                    close_end_textview.setText(map.get("close_end_date")+"");
                }
            }

            @Override
            public void onFail(String msg) {
            }
        });

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.photo_imageview) {
            List<String> names = new ArrayList<>();
            names.add(getString(R.string.photo));
            names.add(getString(R.string.album));
            showDialog(new SelectDialog.SelectDialogListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0: // 直接调起相机
                            /**
                             * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                             *
                             * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                             *
                             * 如果实在有所需要，请直接下载源码引用。
                             */
                            //打开选择,本次允许选择的数量
                            Intent intent = new Intent(mContext, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
                            break;
                        case 1:
                            //打开选择,本次允许选择的数量
//                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
//                                Intent intent1 = new Intent(WxDemoActivity.this, ImageGridActivity.class);
//                                /* 如果需要进入选择的时候显示已经选中的图片，
//                                 * 详情请查看ImagePickerActivity
//                                 * */
////                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
//                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                            Intent intent1 = new Intent(mContext, ImageGridActivity.class);
                            // intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                            //ImagePicker.getInstance().setSelectedImages(images);
                            startActivityForResult(intent1, REQUEST_CODE_SELECT);
                            break;
                    }

                }
            }, names);


        } else if (i == R.id.location_layout) {
            startActivity(new Intent(mContext, LocationSourceActivity.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        } else if (i == R.id.sign_layout) {
            if (!TextUtils.isEmpty(SPUtils.get(mContext, "emp_sign_url", "").toString())) {
                Intent intent = new Intent(mContext, SpaceImageSignActivity.class);
                intent.putExtra("url", SPUtils.get(mContext, "RESOURCE_URL", "") + SPUtils.get(mContext, "emp_sign_url", "").toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", v.getWidth());
                intent.putExtra("height", v.getHeight());
                startActivityForResult(intent, 109);
            } else {
                startActivityForResult(new Intent(mContext, SignatrueActivity.class), 103);
            }


        } else if (i == R.id.work_check_layout) {
            startActivity(new Intent(mContext, WorkCheckActivity.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

//            case R.id.mt_work_form_layout:
//                startActivity(new Intent(mContext,MtPhotoActivity.class));
//                overridePendingTransition(R.anim.right_in,R.anim.left_out);
//                break;
        } else if (i == R.id.elevator_info_layout) {
            startActivity(new Intent(mContext, ElevatorInfoActivity.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        } else if (i == R.id.setting_layout) {
            startActivity(new Intent(mContext, SettingActivity.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                Map<String, String> params = new HashMap<>();
                params.put("id",SPUtils.get(mContext,"emp_id","").toString());
                params.put("emp_photo_url","true");
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size()>0) {
                    MultipartRequestUpload upload = new MultipartRequestUpload((String)SPUtils.get(getApplicationContext(),"IP","") + "/mtmo/updateempinfo.mo?_token="+SPUtils.get(MyApplication.getContext(),"token","").toString(), new Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            HttpResult result = gson.fromJson(s,new TypeToken<HttpResult>(){}.getType());
                            if (Constants.SUCCESS.equals(result.getStatus())) {
                                Bitmap bitmap = BitmapFactory.decodeFile(images.get(0).path);
                                photo_imageview.setImageBitmap(bitmap);
                                ToastUtil.showLong(mContext,"上传成功");
                                SPUtils.put(mContext,"emp_photo_url",result.getData());
                            }

                        }
                    },"file",new File(images.get(0).path),params);
                    upload.setRetryPolicy(new DefaultRetryPolicy(500 * 1000, 1, 1.0f));
                    mQueue.add(upload);
                }
            }
        }
        if (requestCode==103) {
                Bitmap bitmap= BitmapFactory.decodeFile(UIController.SIGN_FILE_PATH);
            }
    }

    private void pushManager (){

        Resources resource = this.getResources();
        String pkgName = this.getPackageName();
        PushManager.startWork(this.getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
                PushUtil.getMetaValue(this, "api_key"));
        CustomPushNotificationBuilder cBuilder_0 = new CustomPushNotificationBuilder(
                resource.getIdentifier(
                        "notification_custom_builder", "layout", pkgName),
                resource.getIdentifier("notification_icon", "id", pkgName),
                resource.getIdentifier("notification_title", "id", pkgName),
                resource.getIdentifier("notification_text", "id", pkgName));
        cBuilder_0.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        cBuilder_0.setNotificationDefaults(Notification.DEFAULT_VIBRATE);
        cBuilder_0.setStatusbarIcon(this.getApplicationInfo().icon);
        cBuilder_0.setLayoutDrawable(resource.getIdentifier(
                "simple_notification_icon", "drawable", pkgName));
        cBuilder_0.setNotificationSound(Uri.parse("android.resource://"+pkgName+"/"+ R.raw.old_ring).toString());

        // 推送高级设置，通知栏样式设置为下面的ID
        PushManager.setNotificationBuilder(this, 0, cBuilder_0);
    }
}
