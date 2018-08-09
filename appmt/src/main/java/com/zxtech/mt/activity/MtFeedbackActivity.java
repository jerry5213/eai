package com.zxtech.mt.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.zxtech.mt.adapter.MtCheckPhotoAdapter;
import com.zxtech.mt.adapter.MtFeedbackAdapter;
import com.zxtech.mt.adapter.MtFormPhotoAdapter;
import com.zxtech.mt.adapter.MyPagerAdapter;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.common.MultipartRequestUpload;
import com.zxtech.mt.entity.CalSurveyResult;
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
 * 评价查看
 *
 * Created by Chw on 2016/7/27.
 */
public class MtFeedbackActivity extends BaseActivity implements ViewPager.OnPageChangeListener, MtCheckPhotoAdapter.PhotoCallBack{
    private ListView feedback_listview;
    private MtWorkPlan work;
    private ImageView sign_imageview;
    private ViewPager main_viewpager;
    private List<View> views = new ArrayList<>();
    private TextView project_textview,address_textview,type_textview,plan_date_textview,workers_textview,elevator_code_textview,
            contract_code_textview,contract_type_textview,contract_begin_textview,elevator_brand_textview,elevator_reg_textview,elevator_type_textview,
            load_textview,speed_textview,door_textview,high_textview,angle_textview,width_textview,relation_person_textview,relation_phone_textview,
            next_check_textview;
    private ListView checkphoto_listview;
    private MtCheckPhotoAdapter mAdapter = null;
    private GridView gridView;

    private int selectedPosition = 0;
    private MtFormPhotoAdapter formPhotoAdapter = null;

    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_mt_finish, null);
        main_layout.addView(view);
        setBottomLayoutHide();
        title_textview.setText(getString(R.string.customer_satisfaction));

    }

    @Override
    protected void findView() {
        feedback_listview = (ListView) findViewById(R.id.feedback_listview);
        sign_imageview = (ImageView) findViewById(R.id.sign_imageview);
        main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
        View pager1 = mInfalter.inflate(R.layout.activity_mtfeedback, null);
        View pager2 = mInfalter.inflate(R.layout.viewpager_mtinfo, null);
        View pager3 = mInfalter.inflate(R.layout.viewpager_checkphoto, null);
        View pager4 = mInfalter.inflate(R.layout.viewpager_mtphoto, null);
        views.add(pager1);
        views.add(pager2);
        views.add(pager3);
        views.add(pager4);
        feedback_listview = (ListView) pager1.findViewById(R.id.feedback_listview);
        sign_imageview = (ImageView) pager1.findViewById(R.id.sign_imageview);

        project_textview = (TextView)pager2. findViewById(R.id.project_textview);
        address_textview = (TextView)pager2. findViewById(R.id.address_textview);
        type_textview = (TextView)pager2. findViewById(R.id.type_textview);
        plan_date_textview = (TextView)pager2. findViewById(R.id.plan_date_textview);

        workers_textview = (TextView)pager2. findViewById(R.id.workers_textview);
        elevator_code_textview = (TextView)pager2. findViewById(R.id.elevator_code_textview);
        contract_code_textview = (TextView)pager2. findViewById(R.id.contract_code_textview);
        contract_type_textview = (TextView)pager2. findViewById(R.id.contract_type_textview);
        contract_begin_textview = (TextView)pager2. findViewById(R.id.contract_begin_textview);
        elevator_brand_textview = (TextView)pager2. findViewById(R.id.elevator_brand_textview);
        elevator_reg_textview = (TextView)pager2. findViewById(R.id.elevator_reg_textview);
        elevator_type_textview = (TextView)pager2. findViewById(R.id.elevator_type_textview);
        load_textview = (TextView)pager2. findViewById(R.id.load_textview);
        speed_textview = (TextView)pager2. findViewById(R.id.speed_textview);
        door_textview = (TextView)pager2. findViewById(R.id.door_textview);
        high_textview = (TextView)pager2. findViewById(R.id.high_textview);
        angle_textview = (TextView)pager2. findViewById(R.id.angle_textview);
        width_textview = (TextView)pager2. findViewById(R.id.width_textview);
        relation_person_textview = (TextView)pager2. findViewById(R.id.relation_person_textview);
        relation_phone_textview = (TextView)pager2. findViewById(R.id.relation_phone_textview);
        next_check_textview = (TextView)pager2. findViewById(R.id.next_check_textview);

        checkphoto_listview = (ListView) pager3.findViewById(R.id.listview);
        gridView = (GridView) pager4.findViewById(R.id.gridView);
        final List<String>  mDataList = new ArrayList<>();
        mDataList.add("评价");
        mDataList.add("维保详情");
        mDataList.add("现场照片");
        mDataList.add("纸质维保");
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        work = (MtWorkPlan) intent.getSerializableExtra("data");

        Map<String, String> param = new HashMap<>();
        param.put("plan_id", work.getId());
        HttpUtil.getInstance(mContext).request("/mtmo/getsurveyresultlist.mo", param, new HttpCallBack<List<CalSurveyResult>>() {
            @Override
            public void onSuccess(List<CalSurveyResult> list) {
                MtFeedbackAdapter mtFeedbackAdapter = new MtFeedbackAdapter(mContext,list,R.layout.item_mtfeedback);
                feedback_listview.setAdapter(mtFeedbackAdapter);
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showLong(mContext, getString(R.string.msg_3));
            }
        });

        HttpUtil.getInstance(mContext).request("/mtmo/getmtplanaddtion.mo", param, new HttpCallBack<List<MtWorkPlanAddtion>>() {
            @Override
            public void onSuccess(List<MtWorkPlanAddtion> list) {
                mAdapter = new MtCheckPhotoAdapter(mContext,list,R.layout.item_check_photo);
                mAdapter.setListener(MtFeedbackActivity.this);
                checkphoto_listview.setAdapter(mAdapter);
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showLong(mContext, getString(R.string.msg_3));
            }
        });

        param.put("addition_type","1");
        HttpUtil.getInstance(mContext).request("/mtmo/getmtformaddtion.mo", param, new HttpCallBack<List<MtWorkPlanAddtion>>() {
            @Override
            public void onSuccess(List<MtWorkPlanAddtion> list) {
                if (list != null) {
                    list.add(new MtWorkPlanAddtion());
                    formPhotoAdapter = new MtFormPhotoAdapter(mContext,list,R.layout.item_formphoto);
                    gridView.setAdapter(formPhotoAdapter);
                }

            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showLong(mContext, getString(R.string.msg_3));
            }
        });



    }

    @Override
    protected void initView() {


        project_textview.setText(work.getProj_name());
        address_textview.setText(work.getProj_address());
        type_textview.setText(work.getWork_type_name());
        plan_date_textview.setText(work.getPlan_date());

        workers_textview.setText(work.getWorkers());
        elevator_code_textview.setText(work.getElevator_code());
        contract_code_textview.setText(work.getContract_code());
        contract_type_textview.setText(work.getContract_type());
        contract_begin_textview.setText(work.getElevator_effect_date());
        elevator_brand_textview.setText(work.getElevator_brand());
        elevator_reg_textview.setText(work.getElevator_reg_number());
        elevator_type_textview.setText(work.getElevator_type());
        load_textview.setText(work.getElevator_load());
        speed_textview.setText(work.getElevator_speed());
        door_textview.setText(work.getElevator_level()+"/"+work.getElevator_station()+"/"+work.getElevator_door());
        high_textview.setText(work.getElevator_high());
        angle_textview.setText(work.getElevator_angle());
        width_textview.setText(work.getElevator_step_width());
        relation_person_textview.setText(work.getElevator_relation_person());
        relation_phone_textview.setText(work.getElevator_relation_phone());
        next_check_textview.setText(work.getNext_check_date());

        main_viewpager.setAdapter(new MyPagerAdapter(views));
        main_viewpager.addOnPageChangeListener(this);
        main_viewpager.setCurrentItem(0);
        Glide.with(mContext).load(SPUtils.get(mContext,"RESOURCE_URL","")+work.getProperty_sign_url()).into(sign_imageview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getAdapter().getCount()-1 == i ){
                    Intent intent = new Intent(mContext, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                    startActivityForResult(intent, 10020);
                }
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        //magicIndicator.onPageSelected(position);
        if (position == 2) {

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //magicIndicator.onPageScrollStateChanged(state);
    }

    @Override
    public void selectedPhoto(int position) {
        this.selectedPosition = position;

        Intent intent = new Intent(mContext, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
        startActivityForResult(intent, 10010);
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
                    MultipartRequestUpload upload = new MultipartRequestUpload((String)SPUtils.get(getApplicationContext(),"IP","") + "/mtmo/loadmtcheckphoto.mo?_token="+ SPUtils.get(MyApplication.getContext(),"token","").toString(), new Response.Listener<String>() {
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

            if (data != null && requestCode == 10020) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    Bitmap bitmap= BitmapFactory.decodeFile(images.get(0).path);
                    bitmap = BitmapUtil.addTimeFlag(bitmap);
                    String s = BitmapUtil.saveImageToGallery(mContext, bitmap);
                    bitmap.recycle();
                    Map<String, String> params = new HashMap<>();
                    params.put("plan_id",work.getId());
                    params.put("type_id","1");
                    params.put("type","纸质检查单照片");
                    MultipartRequestUpload upload = new MultipartRequestUpload((String)SPUtils.get(getApplicationContext(),"IP","") + "/mtmo/loadmtcheckphoto.mo?_token="+ SPUtils.get(MyApplication.getContext(),"token","").toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            HttpResult result = gson.fromJson(s,new TypeToken<HttpResult>(){}.getType());
                            if (Constants.SUCCESS.equals(result.getStatus())) {
                                if (result.getData() != null) {
                                    MtWorkPlanAddtion addtion = new MtWorkPlanAddtion();
                                    addtion.setAddition_url(result.getData().toString());
                                    formPhotoAdapter.addItem(addtion);
                                }
                                ToastUtil.showLong(mContext, "上传成功");
                            }
                        }
                    },"file",new File(s),params);
                    upload.setRetryPolicy(new DefaultRetryPolicy(500 * 1000, 1, 1.0f));
                    mQueue.add(upload);

                }
            }
        }
    }
}
