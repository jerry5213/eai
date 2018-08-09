package com.zxtech.mt.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxtech.mt.adapter.MtInfoAdapter;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.entity.MtWorkItem;
import com.zxtech.mt.entity.MtWorkItemResultRel;
import com.zxtech.mt.entity.MtWorkPlan;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.utils.Util;
import com.zxtech.mt.widget.PopMenu;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 维保检查项
 * Created by Chw on 2016/8/8.
 */
public class MtCheckItemActivity extends BaseActivity {

    private ListView item_listview;

    private MtInfoAdapter mMtInfoAdapter;


    private MtWorkPlan work;
    private EditText result_edittext;



    private boolean isRequest = true;

    private static final int RESULT_PHOTO = 10081;

    private String resultPhotoPath = "";

    private List<MtWorkItem> mtWorkItems = new ArrayList<>();

    private ProgressBar progress_bar;


    public ProgressBar getProgress_bar() {
        return progress_bar;
    }

    @Override
    protected void onCreate() {

        View view = mInfalter.inflate(R.layout.activity_check_item, null);
        main_layout.addView(view);
        title_textview.setText(getString(R.string.check_info));
        set_textview.setBackgroundResource(R.drawable.setting);
        progress_bar = view.findViewById(R.id.progress_bar);

        setBottomLayoutHide();
        initWakeUp();
    }

    private void initWakeUp() {

    }

    @Override
    protected void findView() {
        item_listview = (ListView) findViewById(R.id.item_listview);
        result_edittext = (EditText) findViewById(R.id.result_edittext);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        work = (MtWorkPlan) intent.getSerializableExtra("bean");

        progressbar.show();
        Map<String, String> param = new HashMap<>();
        param.put("category", work.getElevator_category());
        param.put("plan_id", work.getId());
        param.put("work_type", work.getWork_type());
        HttpUtil.getInstance(mContext).request("/mtmo/getmtplanitemlist.mo", param, new HttpCallBack<List<MtWorkItem>>() {
            @Override
            public void onSuccess(List<MtWorkItem> list) {

                if (list != null && list.size() > 0) {
                    mtWorkItems.addAll(list);

                    for (MtWorkItem item : mtWorkItems) {
                        if (TextUtils.isEmpty(item.getResult())) {
                            item.setResult("1");
                        }
                    }
                    mMtInfoAdapter = new MtInfoAdapter(mContext, mtWorkItems, R.layout.item_mt_info);
                    item_listview.setAdapter(mMtInfoAdapter);
                }
                progressbar.dismiss();
            }

            @Override
            public void onFail(String msg) {
                progressbar.dismiss();
            }
        });
    }

    @Override
    protected void initView() {

        result_edittext.setText(work.getWork_result());
        result_edittext.setSelection(result_edittext.getText().length());

        item_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Constants.MT_WORK_STATUS_PAUSE.equals(work.getStatus())) {
                    ToastUtil.showLong(mContext, getString(R.string.msg_9));
                    return;
                }
                TextView yes_or_no_textview = (TextView) view.findViewById(R.id.yes_or_no_textview);
                MtWorkItem mtWorkItem = mtWorkItems.get(position);
                if ("1".equals(mtWorkItem.getResult())) {
                    mtWorkItem.setResult("2");
                    yes_or_no_textview.setBackgroundResource(R.drawable.false_selected);
                } else if ("2".equals(mtWorkItem.getResult())) {
                    mtWorkItem.setResult("3");
                    yes_or_no_textview.setBackgroundResource(R.drawable.mt_status1);
                } else if ("3".equals(mtWorkItem.getResult())) {
                    mtWorkItem.setResult("4");
                    yes_or_no_textview.setBackgroundResource(R.drawable.mt_status2);
                } else   {
                    mtWorkItem.setResult("1");
                    yes_or_no_textview.setBackgroundResource(R.drawable.true_selected);
                }
            }
        });


    }




    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == set_textview.getId()) {

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int width = (int) (displayMetrics.widthPixels * 0.4);
            int height = (int) (displayMetrics.heightPixels * 0.4);

            final PopMenu pm = new PopMenu(this, width, height);

            List<Map<String, Object>> resources = new ArrayList<Map<String, Object>>();
            Map<String, Object> maps1 = new HashMap<String, Object>();
            maps1.put("text", getString(R.string.submit));
            Map<String, Object> maps2 = new HashMap<String, Object>();
            if (Constants.MT_WORK_STATUS_PAUSE.equals(work.getStatus())) {
                maps2.put("text", getString(R.string.start));
            } else {
                maps2.put("text", getString(R.string.pause));
            }
            Map<String, Object> maps3 = new HashMap<String, Object>();
            maps3.put("text", getString(R.string.check_photo));
            resources.add(maps1);
            resources.add(maps2);
            resources.add(maps3);
            pm.addItems(resources);
            pm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            handleSubmit();
                            pm.dismiss();
                            break;
                        case 1:
                            if (Constants.MT_WORK_STATUS_PAUSE.equals(work.getStatus())) {
                                handleStart();
                            } else {
                                handlePause();
                            }
                            pm.dismiss();
                            break;
                        case 2:

                            Intent it = new Intent(mContext, MtCheckPhotoActivity.class);
                            it.putExtra("bean", work);
                            startActivityForResult(it, RESULT_PHOTO);
                            overridePendingTransition(R.anim.right_in,R.anim.left_out);
                            pm.dismiss();
                            break;

                    }
                }
            });
            pm.showAsDropDown(set_textview);


        }

    }



    private void handleSubmit() {
        if (Constants.MT_WORK_STATUS_PAUSE.equals(work.getStatus())) {
            ToastUtil.showLong(mContext, getString(R.string.msg_9));
            return;
        }
         submit();
    }


    private void submit() {

        progressbar.show();
        String mtTaskId = work.getId();
        List<MtWorkItemResultRel> list = new ArrayList<>();
        String version_id = "";
        for (MtWorkItem item :mtWorkItems) {
            if (!"1".equals(item.getResult()) ) {
                version_id = item.getVersion_id();
                list.add(new MtWorkItemResultRel(Util.getGUID(), mtTaskId, item.getItem_code(), item.getResult()));
            }

        }


        Map<String, String> param = new HashMap<>();
        param.put("status", Constants.MT_WORK_STATUS_SUBMIT);
        param.put("plan_id", work.getId());
        param.put("emp_id",SPUtils.get(mContext,"emp_id","328CD4E29E52423C8F58F51885709678").toString());
        param.put("item_results",gson.toJson(list));
        param.put("work_result",result_edittext.getText().toString());
        param.put("version_id",version_id);

        HttpUtil.getInstance(mContext).request("/mtmo/updateworkplan.mo", param, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                progressbar.dismiss();
                finish();
            }

            @Override
            public void onFail(String msg) {
                progressbar.dismiss();
            }
        });

    }


    private void handlePause() {



        progressbar.show();
        if (!TextUtils.isEmpty(result_edittext.getText())) {
            work.setWork_result(result_edittext.getText().toString());
        }

        String mtTaskId = work.getId();
        String version_id = "";
        List<MtWorkItemResultRel> list = new ArrayList<>();
        for (MtWorkItem item :mtWorkItems) {
            if (!"1".equals(item.getResult()) ) {
                version_id = item.getVersion_id();
                list.add(new MtWorkItemResultRel(Util.getGUID(), mtTaskId, item.getItem_code(), item.getResult()));
            }
        }

        Map<String, String> param = new HashMap<>();
        param.put("status", Constants.MT_WORK_STATUS_PAUSE);
        param.put("plan_id", work.getId());
        param.put("emp_id",SPUtils.get(mContext,"emp_id","").toString());
        param.put("item_results",gson.toJson(list));
        param.put("work_result",result_edittext.getText().toString());
        param.put("last_update_user", SPUtils.get(mContext,"user","admin").toString());
        param.put("version_id",version_id);

        HttpUtil.getInstance(mContext).request("/mtmo/updateworkplan.mo", param, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                work.setStatus(Constants.MT_WORK_STATUS_PAUSE);
                ToastUtil.showLong(mContext, getString(R.string.msg_9));

                progressbar.dismiss();
                finish();
            }

            @Override
            public void onFail(String msg) {
                progressbar.dismiss();
            }
        });

    }


    private void handleStart() {

        progressbar.show();
        Map<String, String> param = new HashMap<>();
        param.put("status", Constants.MT_WORK_STATUS_RENEW);
        param.put("plan_id", work.getId());
        param.put("emp_id",SPUtils.get(mContext,"emp_id","").toString());
        param.put("last_update_user", SPUtils.get(mContext,"user","admin").toString());

        HttpUtil.getInstance(mContext).request("/mtmo/updateworkplan.mo", param, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                work.setStatus(Constants.MT_WORK_STATUS_RENEW);
                ToastUtil.showLong(mContext, getString(R.string.msg_11));
                progressbar.dismiss();
            }

            @Override
            public void onFail(String msg) {
                progressbar.dismiss();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_PHOTO&& resultCode == RESULT_OK) {
            String path = data.getStringExtra("photoPath");
            if (!TextUtils.isEmpty(path)){
            }
        }
        if (requestCode == RESULT_PHOTO&& resultCode == 3) {

        }

    }








}
