package com.zxtech.mt.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.zxtech.mt.adapter.GoodsAdapter;
import com.zxtech.mt.entity.AccessoryGoods;
import com.zxtech.mt.entity.AccessoryShopCategory;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syp523 on 2017/8/15.
 */
@Route(path = "/mt/accessoryshop")
public class AccessoryShopActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private RecyclerView shop_recyview;
    private GoodsAdapter adapter;
    private MaterialSpinner spinner;
    private MaterialSpinner spinner1;

    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_shop, null);
        main_layout.addView(view);
        title_textview.setText(getString(R.string.menu_accessory_mall));
        setBottomLayoutHide();
    }

    @Override
    protected void findView() {
        shop_recyview = (RecyclerView) findViewById(R.id.shop_recyview);
        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner1 =  (MaterialSpinner) findViewById(R.id.spinner1);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        progressbar.show();
        StringRequest request1 = new StringRequest("http://demo.qiban365.net/admin/goods_class_list_phone.htm", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                JsonObject result = gson.fromJson(s, JsonObject.class);
                final List<AccessoryShopCategory> categories = gson.fromJson(result.get("classList"),new TypeToken<List<AccessoryShopCategory>>(){}.getType());
                List<String> display = new ArrayList<>();
                for (AccessoryShopCategory g: categories) {
                    display.add(g.getName());
                }
                spinner.setItems(display);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                        initSecondeSpinner(categories.get(position));
                        initGoods(categories.get(position).getId());
                    }
                });
                spinner.setSelectedIndex(0);
                progressbar.dismiss();
                initGoods(categories.get(0).getId());

                //initSecondeSpinner(categories.get(0));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressbar.dismiss();
            }
        });
        mQueue.add(request1);


    }

    private void initGoods(String gc_id){
        progressbar.show();
        StringRequest request = new StringRequest("http://demo.qiban365.net/store_goods_list_phone.htm?gc_id="+gc_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                JsonObject result = gson.fromJson(s, JsonObject.class);
                List<AccessoryGoods> goods = gson.fromJson(result.get("goods"),new TypeToken<List<AccessoryGoods>>(){}.getType());

                GridLayoutManager mgr = new GridLayoutManager(mContext, 3);
                shop_recyview.setLayoutManager(mgr);
                adapter = new GoodsAdapter(goods);
                adapter.openLoadAnimation();
                shop_recyview.setAdapter(adapter);
                adapter.setOnItemClickListener(AccessoryShopActivity.this);
                progressbar.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressbar.dismiss();
            }
        });
        mQueue.add(request);


    }


    private void initSecondeSpinner(final AccessoryShopCategory category){

        List<String> displaySeconde = new ArrayList<>();
        for (AccessoryShopCategory.AccessoryShopCategoryChild child:category.getSecond_class()) {
            displaySeconde.add(child.getName());
        }
        spinner1.setItems(displaySeconde);
        spinner1.setText("");
        spinner1.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                initGoods(category.getSecond_class().get(position).getId());
            }
        });

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(mContext,AccessoryGoodsDetailActivity.class);
        intent.putExtra("data",(AccessoryGoods)adapter.getItem(position));
        startActivity(intent);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
    }
}
