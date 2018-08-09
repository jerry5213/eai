package com.zxtech.mt.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.entity.AccessoryGoods;
import com.zxtech.mtos.R;

/**
 * Created by syp523 on 2017/8/15.
 */

public class AccessoryGoodsDetailActivity extends BaseActivity {
    private AccessoryGoods goods;

    private ImageView photo_imageview;
    private TextView detail_textview;

    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_goods_detail, null);
        main_layout.addView(view);
    }

    @Override
    protected void findView() {
        photo_imageview = (ImageView) findViewById(R.id.photo_imageview);
        detail_textview = (TextView) findViewById(R.id.detail_textview);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        goods = (AccessoryGoods) getIntent().getSerializableExtra("data");
    }

    @Override
    protected void initView() {
        Glide.with(mContext).load(UIController.SHOP_IP+"/"+goods.getMain_photo()).into(photo_imageview);

        StringBuffer sb = new StringBuffer();
        sb.append(getString(R.string.goods_name));
        sb.append(goods.getName() == null ?"": goods.getName());
        sb.append("\n");
        sb.append(getString(R.string.market_price));
        sb.append(goods.getPrice()+getString(R.string.$));
        sb.append("\n");
        sb.append(getString(R.string.mall_price));
        sb.append(goods.getCurrent_price() +getString(R.string.$));
        sb.append("\n");
        detail_textview.setText(sb.toString());
    }
}
