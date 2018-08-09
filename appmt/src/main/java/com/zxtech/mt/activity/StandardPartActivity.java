package com.zxtech.mt.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxtech.mt.adapter.ViewHolder;
import com.zxtech.mt.entity.BaseAccessory;
import com.zxtech.mt.entity.BaseElevator;
import com.zxtech.mt.entity.MtAccessoryInquiry;
import com.zxtech.mt.entity.MtProject;
import com.zxtech.mt.entity.PltEmpPosition;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.widget.DropDownWindow;
import com.zxtech.mt.widget.SingleSelectionDialog;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 标准询价
 * Created by Chw on 2016/6/28.
 */
public class StandardPartActivity extends BaseActivity {
    private ImageView select_imageview;

    private RelativeLayout large_relayout, medium_relayout, small_relayout, eq_relayout, acc_code_relayout, proj_relayout;

    private DropDownWindow dropDownWindow;

    private Gson gson = new Gson();

    private TextView large_textview, medium_textview, small_textview, acc_name_textview, acc_code_textview, eq_textview, proj_textview, acc_part_spec_textview, acc_category_textview, acc_size_textview, acc_sale_pric_textview;





    private MtAccessoryInquiry accessoryInquiry = new MtAccessoryInquiry();

    private String currentAccId;



    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_standard_part, null);
        main_layout.addView(view);
        title_textview.setText(getString(R.string.standard_inquiry));
        set_textview.setText(getString(R.string.submit));
        setBottomLayoutHide();
    }

    @Override
    protected void findView() {
        proj_relayout = (RelativeLayout) findViewById(R.id.proj_relayout);
        eq_relayout = (RelativeLayout) findViewById(R.id.eq_relayout);
        large_relayout = (RelativeLayout) findViewById(R.id.large_relayout);
        medium_relayout = (RelativeLayout) findViewById(R.id.medium_relayout);
        small_relayout = (RelativeLayout) findViewById(R.id.small_relayout);
        large_textview = (TextView) findViewById(R.id.large_textview);
        medium_textview = (TextView) findViewById(R.id.medium_textview);
        small_textview = (TextView) findViewById(R.id.small_textview);
        eq_textview = (TextView) findViewById(R.id.eq_textview);
        proj_textview = (TextView) findViewById(R.id.proj_textview);
        acc_code_relayout = (RelativeLayout) findViewById(R.id.acc_code_relayout);
        acc_code_textview = (TextView) findViewById(R.id.acc_code_textview);
        acc_name_textview = (TextView) findViewById(R.id.acc_name_textview);
        acc_part_spec_textview = (TextView) findViewById(R.id.acc_part_spec_textview);
        acc_category_textview = (TextView) findViewById(R.id.acc_category_textview);
        acc_size_textview = (TextView) findViewById(R.id.acc_size_textview);
        acc_sale_pric_textview = (TextView) findViewById(R.id.acc_sale_pric_textview);
    }

    @Override
    protected void setListener() {
        proj_relayout.setOnClickListener(this);
        eq_relayout.setOnClickListener(this);
        large_relayout.setOnClickListener(this);
        medium_relayout.setOnClickListener(this);
        small_relayout.setOnClickListener(this);
        acc_code_relayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {


    }
    private SingleSelectionDialog singleDialog = null;
    @Override
    public void onClick(View v) {
        final List<String> data = new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.proj_relayout) {
            Intent intent = new Intent(mContext, StandardPartSelectProjectActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 100);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } else if (i == R.id.eq_relayout) {
            if (TextUtils.isEmpty(accessoryInquiry.getProj_id())) {
                ToastUtil.showLong(mContext, getString(R.string.msg_17));
                return;
            }
            Intent intent = new Intent(mContext, StandardPartSelectDeviceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("DATA", accessoryInquiry.getProj_id());
            startActivityForResult(intent, 102);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } else if (i == R.id.large_relayout) {
            if (TextUtils.isEmpty(accessoryInquiry.getElevator_id())) {
                ToastUtil.showLong(mContext, getString(R.string.msg_44));
                return;
            }
            param.put("acc_level", "1");
            param.put("category", accessoryInquiry.getElevator_category());

            HttpUtil.getInstance(mContext).request("/mtmo/getbaseaccessory.mo", param, new HttpCallBack<List<BaseAccessory>>() {
                @Override
                public void onSuccess(List<BaseAccessory> list) {
                    singleDialog = new SingleSelectionDialog(mContext, getString(R.string.area), list) {
                        @Override
                        public void confrim(Object object) {
                            BaseAccessory baseAccessory = (BaseAccessory) object;
                            large_textview.setText(baseAccessory.getAcc_name());
                            accessoryInquiry.setLarge_category(baseAccessory.getId());
                            currentAccId = baseAccessory.getId();

                            medium_textview.setText(null);
                            small_textview.setText(null);
                            acc_code_textview.setText(null);
                            acc_name_textview.setText(null);
                            acc_part_spec_textview.setText(null);
                            acc_category_textview.setText(null);
                            acc_size_textview.setText(null);
                            acc_sale_pric_textview.setText(null);


                            accessoryInquiry.setMiddle_category(null);
                            accessoryInquiry.setSmall_category(null);
                            accessoryInquiry.setAcc_code(null);
                            accessoryInquiry.setAcc_name(null);
                            this.dismiss();
                        }

                        @Override
                        public void layout(ViewHolder holder, Object object, int position) {
                            BaseAccessory baseAccessory = (BaseAccessory) object;
                            holder.setText(R.id.name_textview, baseAccessory.getAcc_name());
                        }
                    };
                    singleDialog.show();
                }

                @Override
                public void onFail(String msg) {
                }
            });


        } else if (i == R.id.medium_relayout) {
            if (TextUtils.isEmpty(accessoryInquiry.getLarge_category())) {
                ToastUtil.showLong(mContext, getString(R.string.msg_47));
                return;
            }
            param.put("acc_level", "2");
            param.put("acc_id", accessoryInquiry.getLarge_category());
            param.put("category", accessoryInquiry.getElevator_category());


            HttpUtil.getInstance(mContext).request("/mtmo/getbaseaccessory.mo", param, new HttpCallBack<List<BaseAccessory>>() {
                @Override
                public void onSuccess(List<BaseAccessory> list) {
                    singleDialog = new SingleSelectionDialog(mContext, getString(R.string.position), list) {
                        @Override
                        public void confrim(Object object) {
                            BaseAccessory baseAccessory = (BaseAccessory) object;
                            medium_textview.setText(baseAccessory.getAcc_name());
                            currentAccId = baseAccessory.getId();

                            small_textview.setText(null);
                            acc_code_textview.setText(null);
                            acc_name_textview.setText(null);
                            acc_part_spec_textview.setText(null);
                            acc_category_textview.setText(null);
                            acc_size_textview.setText(null);
                            acc_sale_pric_textview.setText(null);

                            accessoryInquiry.setMiddle_category(baseAccessory.getId());
                            accessoryInquiry.setSmall_category(null);
                            accessoryInquiry.setAcc_code(null);
                            accessoryInquiry.setAcc_name(null);
                            this.dismiss();
                        }

                        @Override
                        public void layout(ViewHolder holder, Object object, int position) {
                            BaseAccessory baseAccessory = (BaseAccessory) object;
                            holder.setText(R.id.name_textview, baseAccessory.getAcc_name());
                        }
                    };
                    singleDialog.show();
                }

                @Override
                public void onFail(String msg) {
                }
            });

        } else if (i == R.id.small_relayout) {
            if (TextUtils.isEmpty(accessoryInquiry.getMiddle_category())) {
                ToastUtil.showLong(mContext, getString(R.string.msg_48));
                return;
            }
            param.put("acc_level", "3");
            param.put("acc_id", accessoryInquiry.getMiddle_category());
            param.put("category", accessoryInquiry.getElevator_category());

            HttpUtil.getInstance(mContext).request("/mtmo/getbaseaccessory.mo", param, new HttpCallBack<List<BaseAccessory>>() {
                @Override
                public void onSuccess(List<BaseAccessory> list) {
                    singleDialog = new SingleSelectionDialog(mContext, getString(R.string.parts), list) {
                        @Override
                        public void confrim(Object object) {
                            BaseAccessory baseAccessory = (BaseAccessory) object;
                            small_textview.setText(baseAccessory.getAcc_name());
                            currentAccId = baseAccessory.getId();

                            acc_code_textview.setText(null);
                            acc_name_textview.setText(null);
                            acc_part_spec_textview.setText(null);
                            acc_category_textview.setText(null);
                            acc_size_textview.setText(null);
                            acc_sale_pric_textview.setText(null);
                            accessoryInquiry.setSmall_category(baseAccessory.getId());
                            accessoryInquiry.setAcc_code(null);
                            accessoryInquiry.setAcc_name(null);
                            this.dismiss();
                        }

                        @Override
                        public void layout(ViewHolder holder, Object object, int position) {
                            BaseAccessory baseAccessory = (BaseAccessory) object;
                            holder.setText(R.id.name_textview, baseAccessory.getAcc_name());
                        }
                    };
                    singleDialog.show();
                }

                @Override
                public void onFail(String msg) {
                }
            });

        } else if (i == R.id.acc_code_relayout) {
            if (TextUtils.isEmpty(currentAccId)) {
                ToastUtil.showLong(mContext, getString(R.string.msg_46));
                return;
            }
            Intent intent = new Intent(mContext, StandardPartSelectAccActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("DATA", currentAccId);
            startActivityForResult(intent, 101);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } else if (i == R.id.set_textview) {
            if (TextUtils.isEmpty(accessoryInquiry.getProj_id())) {
                ToastUtil.showLong(mContext, getString(R.string.msg_17));
                return;
            }
            if (TextUtils.isEmpty(accessoryInquiry.getElevator_id())) {
                ToastUtil.showLong(mContext, getString(R.string.msg_44));
                return;
            }
            if (TextUtils.isEmpty(accessoryInquiry.getAcc_code())) {
                ToastUtil.showLong(mContext, getString(R.string.msg_45));
                return;
            }
            title_textview.setText(getString(R.string.submiting));


            accessoryInquiry.setEmp_id(SPUtils.get(mContext, "emp_id", "").toString());
            accessoryInquiry.setAcc_type("1");
            accessoryInquiry.setStatus("0");

            submit();

        }
    }
    private  List<BaseAccessory> accs = new ArrayList<>();

    private void submit() {
        progressbar.show();
        accessoryInquiry.setEmp_id(SPUtils.get(mContext,"emp_id","admin").toString());
        String s = gson.toJson(accessoryInquiry);

        Map<String, String> param = new HashMap<>();
        param = gson.fromJson(s,new TypeToken<HashMap<String,String>>(){}.getType());
        HttpUtil.getInstance(mContext).request("/mtmo/saveaccessoryinquiry.mo", param, new HttpCallBack<List<PltEmpPosition>>() {
            @Override
            public void onSuccess(List<PltEmpPosition> list) {

                progressbar.dismiss();
                setResult(AccessoryActivity.BACK_REFESH_ACTIVITY);
                finish();
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showLong(mContext, getString(R.string.msg_3));
                progressbar.dismiss();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case 100: {
                if (data != null && data.hasExtra("DATA")) {
                    MtProject project = (MtProject) data.getSerializableExtra("DATA");
                    if (project != null) {
                        proj_textview.setText(project.getProj_name());
                        accessoryInquiry.setProj_id(project.getId());
                        accessoryInquiry.setElevator_id(null);
                        accessoryInquiry.setElevator_code(null);
                        eq_textview.setText(null);
                        large_textview.setText(null);
                        medium_textview.setText(null);
                        small_textview.setText(null);
                        acc_code_textview.setText(null);
                        acc_name_textview.setText(null);
                        acc_part_spec_textview.setText(null);
                        acc_category_textview.setText(null);
                        acc_size_textview.setText(null);
                        acc_sale_pric_textview.setText(null);


                        accessoryInquiry.setAcc_name(null);
                        accessoryInquiry.setAcc_code(null);
                        accessoryInquiry.setLarge_category(null);
                        accessoryInquiry.setMiddle_category(null);
                        accessoryInquiry.setSmall_category(null);
                    }
                }
                break;
            }
            case 101: {
                if (data != null && data.hasExtra("DATA")) {
                    BaseAccessory baseAccessory = (BaseAccessory) data.getSerializableExtra("DATA");
                    if (baseAccessory != null) {
                        accessoryInquiry.setAcc_code(baseAccessory.getAcc_code());
                        accessoryInquiry.setAcc_name(baseAccessory.getAcc_name());
                        acc_code_textview.setText(baseAccessory.getAcc_code());
                        acc_name_textview.setText( baseAccessory.getAcc_name());
                        acc_part_spec_textview.setText(baseAccessory.getAcc_spec());
                        acc_category_textview.setText(baseAccessory.getAcc_category());
                        acc_sale_pric_textview.setText(baseAccessory.getSale_price());
                        if (!TextUtils.isEmpty(baseAccessory.getStore_count())) {
                            acc_size_textview.setText(baseAccessory.getStore_count());
                        }else if (!TextUtils.isEmpty(baseAccessory.getStore_count()) && !TextUtils.isEmpty(baseAccessory.getUnit_name())) {
                            acc_size_textview.setText(baseAccessory.getStore_count()+"("+baseAccessory.getUnit_name()+")");
                        }

                    }
                }
                break;
            }
            case 102: {
                if (data != null && data.hasExtra("DATA")) {
                    BaseElevator elevator = (BaseElevator) data.getSerializableExtra("DATA");
                    if (elevator != null) {
                        eq_textview.setText(elevator.getElevator_code());
                        accessoryInquiry.setElevator_category(elevator.getElevator_category());
                        accessoryInquiry.setElevator_id(elevator.getId());
                        accessoryInquiry.setElevator_code(elevator.getElevator_code());
                        large_textview.setText(null);
                        medium_textview.setText(null);
                        small_textview.setText(null);
                        acc_code_textview.setText(null);
                        acc_name_textview.setText(null);
                        acc_part_spec_textview.setText(null);
                        acc_category_textview.setText(null);
                        acc_size_textview.setText(null);
                        acc_sale_pric_textview.setText(null);

                        accessoryInquiry.setAcc_name(null);
                        accessoryInquiry.setAcc_code(null);
                        accessoryInquiry.setLarge_category(null);
                        accessoryInquiry.setMiddle_category(null);
                        accessoryInquiry.setSmall_category(null);
                    }
                }
                break;
            }
        }
    }
}
