package com.zxtech.mt.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zxtech.mt.adapter.CommonAdapter;
import com.zxtech.mt.adapter.ViewHolder;
import com.zxtech.mt.entity.BaseAccessory;
import com.zxtech.mt.utils.BitmapUtil;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/6.
 */
public class StandardPartSelectAccActivity extends BaseActivity implements View.OnClickListener {
    private InsideListAdapter adapter;
    private ListView lv_list;


    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_standard_part_select_acc, null);
        main_layout.addView(view);
        setBottomLayoutHide();
    }

    @Override
    protected void findView() {
        findViewById(R.id.back_button).setOnClickListener(this);
        lv_list = (ListView) findViewById(R.id.lv_list);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BaseAccessory data = adapter.getItem(i);
                Intent intent = new Intent();
                intent.putExtra("DATA", data);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Map<String, String> param = new HashMap<>();
        param.put("acc_level", "4");
        param.put("acc_id", String.valueOf(getIntent().getStringExtra("DATA")));

        HttpUtil.getInstance(mContext).request("/mtmo/getbaseaccessory.mo", param, new HttpCallBack<List<BaseAccessory>>() {
            @Override
            public void onSuccess(List<BaseAccessory> list) {
                if (list != null && list.size() != 0) {
                    adapter = new InsideListAdapter(mContext,list);
                    lv_list.setAdapter(adapter);
                } else {
                    ToastUtil.showLong(StandardPartSelectAccActivity.this, getString(R.string.msg_69));
                }
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }




    private static class InsideListAdapter extends CommonAdapter<BaseAccessory> {

        private DisplayImageOptions opts;

        public InsideListAdapter(Context context, List<BaseAccessory> datas) {
            super(context,  datas == null ? new ArrayList<BaseAccessory>() : datas, R.layout.item_listview_standard_part_acc_select);
            opts = BitmapUtil.getImageOptionsBuilder(-1, R.drawable.default_acc, null, true, false, false).build();
        }

        public List<BaseAccessory> getDatas() {
            return mDatas;
        }

        public void setDatas(List<BaseAccessory> datas) {
            this.mDatas.clear();
            this.mDatas.addAll(datas);
        }
        @Override
        public void convert(ViewHolder holder, final BaseAccessory data, int position) {
            StringBuilder sb = new StringBuilder();
            sb.append(mContext.getString(R.string.part_number)+" ");
            sb.append(TextUtils.isEmpty(data.getAcc_code()) ? "" : data.getAcc_code());
            sb.append("\n");
            sb.append(mContext.getString(R.string.part_name)+" ");
            sb.append(TextUtils.isEmpty(data.getAcc_name()) ? "" : data.getAcc_name());
            sb.append("\n");
            sb.append(mContext.getString(R.string.part_spec)+" ");
            sb.append(TextUtils.isEmpty(data.getAcc_spec()) ? "" : data.getAcc_spec());
            sb.append("\n");
            sb.append(mContext.getString(R.string.store_count)+" ");
            sb.append(TextUtils.isEmpty(data.getStore_count()) ? "" : data.getStore_count());
            sb.append("\n");
            sb.append(mContext.getString(R.string.sale_pric)+" ");
            sb.append(TextUtils.isEmpty(data.getSale_price()) ? "" : data.getSale_price());
            holder.setText(R.id.tv_name, sb.toString());

            TextView tv_no_pic = holder.getView(R.id.tv_no_pic);
            ImageView iv_pic = holder.getView(R.id.iv_pic);
            final String pic_url = TextUtils.isEmpty(data.getPic_url_one()) ? null : ((data.getPic_url_one().startsWith("http")) ? "" : (SPUtils.get(mContext,"RESOURCE_URL",""))) + data.getPic_url_one();

            if (!TextUtils.isEmpty(pic_url)) {
                tv_no_pic.setVisibility(View.GONE);
                iv_pic.setVisibility(View.VISIBLE);
                iv_pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, SpaceImageDetailActivity.class);
                        intent.putExtra("url", pic_url);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
                        intent.putExtra("locationX", location[0]);
                        intent.putExtra("locationY", location[1]);
                        intent.putExtra("width", v.getWidth());
                        intent.putExtra("height", v.getHeight());
                        mContext.startActivity(intent);

                    }
                });
            } else {
                tv_no_pic.setVisibility(View.VISIBLE);
                iv_pic.setVisibility(View.GONE);
                tv_no_pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showShort(v.getContext(), "无图片");
                    }
                });
            }

            Glide.with(mContext)
                    .load(SPUtils.get(mContext,"RESOURCE_URL","")+pic_url)
                    .placeholder(R.drawable.default_acc)
                    .error(R.drawable.default_acc)
                    .into(iv_pic);

        }

    }

}
