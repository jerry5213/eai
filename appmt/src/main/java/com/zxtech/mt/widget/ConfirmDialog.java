package com.zxtech.mt.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zxtech.mtos.R;


/**
 * 确认提示框
 * Created by Chw on 2016/7/6.
 */
public class ConfirmDialog extends Dialog implements View.OnClickListener
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
     * 确定。
     */
    private Button confirm_button;

    /** 取消 */
    private Button cancel_button;


    private TextView content_textview;

    private String mTitle = null;

    private TextView hine_textview;



    public ConfirmDialog(Context context, String content, String title)
    {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.mContext = context;
        this.mContent = content;
        this.mTitle= title;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_confirm);

        // 实例化UI。
        this.findViews();
        // 设置监听事件。
        this.setListeners();
        // 初始化视图。
        this.initViews();
    }

    /**
     * 实例化UI。
     */
    private void findViews()
    {
        confirm_button = (Button) findViewById(R.id.confirm_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        content_textview = (TextView) findViewById(R.id.content_textview);
        hine_textview = (TextView) findViewById(R.id.hine_textview);

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
        content_textview.setText(mContent);
        hine_textview.setText(mTitle);

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
