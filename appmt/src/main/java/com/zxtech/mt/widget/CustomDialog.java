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
public class CustomDialog extends Dialog implements View.OnClickListener
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
    private TextView hine_textview;

    /**
     * 文本。
     */
    private TextView text;

    /**
     * 确定。
     */
    private Button confirm_button;

    /** 取消 */
    private Button cancel_button;

    private LinearLayout main_layout;

    private View contentView;



    public CustomDialog(Context context , View contentView)
    {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.mContext = context;
        this.contentView = contentView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_custom1);

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
        confirm_button = (Button) findViewById(R.id.confirm_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        hine_textview = (TextView) findViewById(R.id.hine_textview);
        main_layout = (LinearLayout) findViewById(R.id.main_layout);
//        layoutParams.gravity = Gravity.CENTER;
        main_layout.addView(contentView);


    }

    /**
     * 设置监听事件。
     */
    private void setListeners()
    {
        this.confirm_button.setOnClickListener(this);
        this.cancel_button.setOnClickListener(this);
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
        hine_textview.setText(title);
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
        int i = v.getId();
        if (i == R.id.cancel_button) {
            this.dismiss();

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


    /**
     * 设置取消按钮是否显示
     *
     * @param isShow true-显示，false-隐藏
     */
    public void setIsShowCancelBT(boolean isShow)
    {
        if (isShow)
        {
            cancel_button.setVisibility(View.VISIBLE);
        }
        else
        {
            cancel_button.setVisibility(View.GONE);
//            confirm_button.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
//            confirm_button.setPadding(0,0,30,0);
        }
    }

    public void setIsShowConfirmBT(boolean isShow)
    {
        if (isShow)
        {
            confirm_button.setVisibility(View.VISIBLE);
        }
        else
        {
            confirm_button.setVisibility(View.GONE);
        }
    }
}
