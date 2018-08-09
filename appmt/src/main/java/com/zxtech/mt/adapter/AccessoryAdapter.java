package com.zxtech.mt.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.zxtech.mt.activity.SpaceImageDetailActivity;
import com.zxtech.mt.entity.MtAccessoryInquiry;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.widget.SimpleDialog;
import com.zxtech.mtos.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chw on 2016/6/30.
 */
public class AccessoryAdapter extends CommonAdapter<MtAccessoryInquiry> {

    private List<String> projectId = new ArrayList<>();
    private HashMap<String, Float> discountMap = new HashMap<String, Float>();
    private DecimalFormat fnum = null;
    private int allVisible;
    private SimpleDialog dialog = null;
    private AccessoryAdapterCallBack callBack = null;

    public AccessoryAdapter(Context context, List<MtAccessoryInquiry> datas, int layoutId) {
        super(context, datas, layoutId);
        fnum = new DecimalFormat("##0.00");
    }

    @Override
    public void convert(ViewHolder holder, final MtAccessoryInquiry accessory, final int position) {
        holder.setText(R.id.proj_textview, accessory.getProj_name());
        holder.setText(R.id.price_textview, accessory.getSale_price() == 0 ? "" : fnum.format(accessory.getSale_price()));
        holder.setText(R.id.accname_textview, accessory.getAcc_name());
        holder.setText(R.id.acccode_textview, accessory.getAcc_code());
        holder.setText(R.id.elevatorcode_textview, accessory.getElevator_code());
        TextView tv_no_pic = holder.getView(R.id.tv_no_pic);
        ImageView iv_pic = holder.getView(R.id.iv_pic);
        final String pic_url = TextUtils.isEmpty(accessory.getPic_url_one()) ? null : ((accessory.getPic_url_one().startsWith("http")) ? "" : (SPUtils.get(mContext, "RESOURCE_URL", ""))) + accessory.getPic_url_one();

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
                .load(SPUtils.get(mContext, "RESOURCE_URL", "") + pic_url)
                .placeholder(R.drawable.default_acc)
                .error(R.drawable.default_acc)
                .into(iv_pic);

        holder.setText(R.id.native_textview, accessory.getStore_count() == null ? "" : accessory.getStore_count());

        final ImageView del_imageview = holder.getView(R.id.del_imageview);
        del_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.delete(position);
            }
        });

    }

    public void setAllVisible(int allVisible) {
        this.allVisible = allVisible;
        this.notifyDataSetChanged();
    }

    public HashMap<String, Float> getDiscountMap() {
        return discountMap;
    }

    public void setDiscountMap(HashMap<String, Float> discountMap) {
        this.discountMap = discountMap;
    }

    public List<String> getProjectId() {
        return projectId;
    }

    public void setProjectId(List<String> projectId) {
        this.projectId = projectId;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void setListener(AccessoryAdapterCallBack callBack) {
        this.callBack = callBack;
    }

    public interface AccessoryAdapterCallBack {
        void delete(int position);
    }
}
