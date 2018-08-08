package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.PayMethod;

import java.util.List;

/**
 * Created by syp523 on 2018/6/21.
 */

public class PayMethodAdapter extends BaseQuickAdapter<PayMethod, BaseViewHolder> {
    private boolean isEdit = true;

    public PayMethodAdapter(int layoutResId, @Nullable List<PayMethod> data,boolean isEdit) {
        super(layoutResId, data);
        this.isEdit = isEdit;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PayMethod item) {

        ImageView check_iv = helper.getView(R.id.check_iv);
        check_iv.setEnabled(isEdit);
        if (item.isCheck()) {
            check_iv.setImageResource(R.drawable.match_check);
        } else {
            check_iv.setImageResource(R.drawable.match);
        }

        helper.setText(R.id.type_tv, item.getProperty());
        helper.setText(R.id.before_tv, item.getBeforeText());
        final EditText first_et = helper.getView(R.id.first_et);
        first_et.setEnabled(isEdit);
        first_et.setText(item.getFirstEditText());
        helper.setText(R.id.middle_tv, item.getMiddleText());
        final EditText second_et = helper.getView(R.id.second_et);
        second_et.setText(item.getSecondEditText());
        second_et.setEnabled(isEdit);
        helper.setText(R.id.after_tv, item.getAfterText());

        check_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isCheck()) {
                    item.setIsChecked("False");
                }else{
                    item.setIsChecked("True");
                }
                notifyItemChanged(helper.getAdapterPosition());
            }
        });


        final TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(first_et.hasFocus()){//判断当前EditText是否有焦点在
                    item.setDays(first_et.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        //设置EditText的焦点监听器判断焦点变化，当有焦点时addTextChangedListener，失去焦点时removeTextChangedListener
        first_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    first_et.addTextChangedListener(textWatcher);
                }else{
                    first_et.removeTextChangedListener(textWatcher);
                }
            }
        });

        final TextWatcher textWatcher1=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(second_et.hasFocus()){//判断当前EditText是否有焦点在
                    item.setPercent(second_et.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        //设置EditText的焦点监听器判断焦点变化，当有焦点时addTextChangedListener，失去焦点时removeTextChangedListener
        second_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    second_et.addTextChangedListener(textWatcher1);
                }else{
                    second_et.removeTextChangedListener(textWatcher1);
                }
            }
        });

    }
}
