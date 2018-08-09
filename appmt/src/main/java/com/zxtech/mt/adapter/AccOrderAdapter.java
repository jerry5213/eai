package com.zxtech.mt.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.zxtech.mt.activity.OnClickCallBack;
import com.zxtech.mt.entity.MtAccessory;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by Chw on 2016/7/28.
 */
public class AccOrderAdapter extends CommonAdapter<MtAccessory>{

    private OnClickCallBack callBack;

    public AccOrderAdapter(Context context, List<MtAccessory> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final MtAccessory accessory, int position) {
        holder.setText(R.id.acccode_textview,accessory.getAcc_code());
        TextView minus_textview = holder.getView(R.id.minus_textview);
        TextView plus_textview = holder.getView(R.id.plus_textview);
        final TextView number_textview = holder.getView(R.id.number_textview);
        plus_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int number = Integer.parseInt(number_textview.getText().toString());
                int after = number+1;
                number_textview.setText(String.valueOf(after));
                accessory.setAcc_count(after);
                accessory.setAcc_real_total(after*accessory.getAcc_real_price());
                callBack.changeTotalPrice();
            }
        });

        minus_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(number_textview.getText().toString());
                if (number==1){
                    return;
                }
                int after = number-1;
                number_textview.setText(String.valueOf(after));
                accessory.setAcc_count(after);
                accessory.setAcc_real_total(after*accessory.getAcc_real_price());
                callBack.changeTotalPrice();

            }
        });


    }

    public OnClickCallBack setCallBack(OnClickCallBack callBack){
        this.callBack = callBack;
        return callBack;
    }


}


