package com.zxtech.mt.adapter;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.entity.AccessoryGoods;
import com.zxtech.mt.entity.AccessoryShopCategory;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by syp523 on 2017/8/15.
 */

public class GoodsAdapter extends BaseQuickAdapter<AccessoryGoods, BaseViewHolder> {
    public GoodsAdapter(List<AccessoryGoods> list) {
        super(R.layout.item_shop, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccessoryGoods item) {
        helper.setText(R.id.name_textview,item.getName());
        Glide.with(mContext).load(UIController.SHOP_IP+"/"+item.getMain_photo()).into((ImageView) helper.getView(R.id.photo_imageview));
    }


}
