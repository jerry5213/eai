package com.zxtech.mt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.zxtech.mt.adapter.MtPhotoAdapter;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.common.MultipartRequestUpload;
import com.zxtech.mt.entity.HttpResult;
import com.zxtech.mt.entity.MtWorkPlan;
import com.zxtech.mt.imagepicker.ImageGridActivity;
import com.zxtech.mt.imagepicker.ImageItem;
import com.zxtech.mt.imagepicker.ImagePicker;
import com.zxtech.mt.utils.DialogUtil;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.utils.Util;
import com.zxtech.mt.widget.SelectDialog;
import com.zxtech.mtos.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp523 on 2017/1/20.
 */
public class MtPhotoActivity extends BaseActivity {

    private ListView listview;
    private BGARefreshLayout mRefreshLayout;
    private static final int RESULT_PHOTO = 1031;

    private String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();

    private String IMAGES_FOLDER = SDCARD_PATH + File.separator + "mt" + File.separator + "images" + File.separator;
    private boolean isRequest = false;
    private MtWorkPlan work = null;
    private int current_page = 1;
    private MtPhotoAdapter adapter;
    private List<MtWorkPlan> workPlans = new ArrayList<>();

    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_mt_photo, null);
        main_layout.addView(view);
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lps);
        setBottomLayoutHide();
    }

    @Override
    protected void findView() {

        listview = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.rl_refresh);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, false);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {

            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

                if (adapter == null) {
                    return false;
                }
                current_page++;
                Map<String, String> param = new HashMap<>();
                param.put("search_type", "5");
                param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
                param.put("page", String.valueOf(current_page));
                HttpUtil.getInstance(mContext).request("/mtmo/getmtplanlist.mo", param, new HttpCallBack<List<MtWorkPlan>>() {
                    @Override
                    public void onSuccess(List<MtWorkPlan> list) {
                        workPlans.addAll(Util.removeDistinct(workPlans, list));
                        adapter.notifyDataSetChanged();
                        mRefreshLayout.endLoadingMore();
                    }

                    @Override
                    public void onFail(String msg) {
                        mRefreshLayout.endLoadingMore();
                    }
                });
                return true;
            }
        });
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {

        Map<String, String> param = new HashMap<>();
        param.put("search_type", "5");
        param.put("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
        param.put("page", String.valueOf(current_page));
        HttpUtil.getInstance(mContext).request("/mtmo/getmtplanlist.mo", param, new HttpCallBack<List<MtWorkPlan>>() {
            @Override
            public void onSuccess(List<MtWorkPlan> list) {
                adapter = new MtPhotoAdapter(mContext, list, R.layout.item_mt_photo);
                listview.setAdapter(adapter);
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showLong(mContext, getString(R.string.msg_3));
                title_textview.setText(getString(R.string.check_info));
            }
        });

    }

    @Override
    protected void initView() {

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                work = (MtWorkPlan) parent.getAdapter().getItem(position);
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                DialogUtil.showDialog((Activity) mContext, new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                Intent intent = new Intent(mContext, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                ((Activity) mContext).startActivityForResult(intent, 10032);
                                break;
                            case 1:
                                Intent intent1 = new Intent(mContext, ImageGridActivity.class);
                                ((Activity) mContext).startActivityForResult(intent1, 10032);
                                break;
                        }
                    }
                }, names);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == 10032) {
                Map<String, String> params = new HashMap<>();
                params.put("plan_id", work.getId());
                params.put("type_id", "1");
                params.put("type", "纸质检查单照片");
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    MultipartRequestUpload upload = new MultipartRequestUpload((String) SPUtils.get(getApplicationContext(), "IP", "") + "/mtmo/loadmtcheckphoto.mo?_token=" + SPUtils.get(MyApplication.getContext(), "token", "").toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            HttpResult result = gson.fromJson(s, new TypeToken<HttpResult>() {
                            }.getType());
                            if (Constants.SUCCESS.equals(result.getStatus())) {
                                ToastUtil.showLong(mContext, "上传成功");
                                work.setMt_photo_url(result.getData().toString());
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }, "file", new File(images.get(0).path), params);
                    upload.setRetryPolicy(new DefaultRetryPolicy(500 * 1000, 1, 1.0f));
                    mQueue.add(upload);
                }
            }
        }
    }
}
