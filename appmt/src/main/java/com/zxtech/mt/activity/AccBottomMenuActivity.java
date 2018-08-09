package com.zxtech.mt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.zxtech.mt.adapter.AccOrderAdapter;
import com.zxtech.mt.entity.MtAccessory;
import com.zxtech.mtos.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chw on 2016/7/28.
 */
public class AccBottomMenuActivity extends Activity implements OnClickCallBack {
    private ListView order_listview;
    private AccOrderAdapter mAdapter;

    private TextView submit_textview;

    private ArrayList<MtAccessory> mtAccessories;

    private TextView price_textview;

    private String totalPrice = "0";
    private HashMap<String, Float> discountMap = null;
    private DecimalFormat df = new DecimalFormat("##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_bottommenu);
        order_listview = (ListView) findViewById(R.id.order_listview);
        submit_textview = (TextView) findViewById(R.id.submit_textview);
        price_textview = (TextView) findViewById(R.id.price_textview);
        Intent intent = getIntent();
        mtAccessories = (ArrayList<MtAccessory>) intent.getSerializableExtra("beans");
        discountMap = (HashMap<String, Float>) intent.getSerializableExtra("map");
        mAdapter = new AccOrderAdapter(this,mtAccessories,R.layout.item_acc_order);
        mAdapter.setCallBack(this);
        order_listview.setAdapter(mAdapter);
        submit_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                setResult(16,intent1);
                for (MtAccessory mtAccessory:mtAccessories){
                    if (mtAccessory.getAcc_real_price()==0){
                        //乘以折扣
                        if (discountMap.containsKey(mtAccessory.getId())) {
                            mtAccessory.setAcc_real_price(mtAccessory.getSale_price()*discountMap.get(mtAccessory.getId())/10f);
                            mtAccessory.setAcc_real_total(mtAccessory.getSale_price()*mtAccessory.getAcc_count()*discountMap.get(mtAccessory.getId())/10f);
                        }else{
                            mtAccessory.setAcc_real_price(mtAccessory.getSale_price());
                            mtAccessory.setAcc_real_total(mtAccessory.getSale_price()*mtAccessory.getAcc_count());
                        }
                    }
                }
                intent1.putExtra("totalPrice",totalPrice);
                intent1.putExtra("beans",mtAccessories);
                finish();
            }
        });

        changeTotalPrice();
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
        finish();
        return true;
    }

    @Override
    public void changeTotalPrice() {
        float total = 0;
        for (MtAccessory mtAccessory:mtAccessories){
            double price = 0;
            //
            if (discountMap.containsKey(mtAccessory.getId())){
                price = discountMap.get(mtAccessory.getId())*mtAccessory.getSale_price()/10*mtAccessory.getAcc_count();
            }else{
                price = mtAccessory.getSale_price()*mtAccessory.getAcc_count();
            }

            total+=price;
        }
        totalPrice = df.format(total);
        price_textview.setText(getString(R.string.total)+totalPrice);

    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.push_bottom_out,0);
    }
}
