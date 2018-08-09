package com.zxtech.mt.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.mtos.R;


/**
 * Created by Administrator on 2016/7/6.
 */
public class DiscountDialog extends Dialog implements View.OnClickListener
{
    /**
     * 上下文环境。
     */
    private Context mContext;

    /**
     * 文本内容。
     */
    private String mContent;

    /**
     * 提示。
     */
    private TextView hine;

    /**
     * 文本。
     */
    private TextView text;

    /**
     * 确定。
     */
    private Button confirm;

    /** 取消 */
    private Button cancel;

    private LinearLayout main_layout;


    public DiscountDialog(Context context)
    {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_discount);

        // 实例化UI。
        this.findViews();
        // 设置监听事件。
        this.setListeners();
        // 初始化视图。
        this.initViews();
        // 初始化内容。
        this.initContent();
    }

    /**
     * 实例化UI。
     */
    private void findViews()
    {
//        this.hine = (TextView) this.findViewById(R.id.confirm_dialog_hine);
//        this.text = (TextView) this.findViewById(R.id.confirm_dialog_text);
//        this.confirm = (Button) this.findViewById(R.id.confirm_dialog_button);
//        cancel = (Button) this.findViewById(R.id.confirm_dialog_cancel_button);
    }

    /**
     * 设置监听事件。
     */
    private void setListeners()
    {
//        this.confirm.setOnClickListener(this);
//        cancel.setOnClickListener(this);
    }

    /**
     * 初始化视图。
     */
    private void initViews()
    {

    }

    /**
     * 设置标题
     *
     * @author Chw
     * @date 2015年1月6日
     *
     * @param title
     */
    public void setTitle(String title)
    {
//        hine.setText(title);
    }

    /**
     * 加载内容。
     */
    private void initContent()
    {
//        this.text.setText(mContent);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        }
    }

    public void setHeightAndWidth(int width,int height){
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.width = width;
        lp.height = height;
        dialogWindow.setAttributes(lp);
    }


}
