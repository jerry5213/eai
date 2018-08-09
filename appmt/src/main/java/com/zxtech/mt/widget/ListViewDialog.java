package com.zxtech.mt.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.zxtech.mt.adapter.ListViewDialogAdapter;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6.
 */
public abstract class ListViewDialog extends Dialog implements View.OnClickListener
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

//    /**
//     * 确定。
//     */
//    private Button confirm_button;

    /** 取消 */
    private Button cancel_button;

    private LinearLayout main_layout;

    private View contentView;

    private ListView my_listview;

    private ListViewDialogAdapter mAdapter;

    private List<String> data;

    private String title;


    public ListViewDialog(Context context, List<String> data,String title)
    {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.mContext = context;
        this.data = data;
        this.title = title;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_custom);

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
//        confirm_button = (Button) findViewById(R.id.confirm_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        hine_textview = (TextView) findViewById(R.id.hine_textview);
        my_listview = (ListView) findViewById(R.id.my_listview);

    }

    /**
     * 设置监听事件。
     */
    private void setListeners()
    {
//        this.confirm_button.setOnClickListener(this);
        this.cancel_button.setOnClickListener(this);
    }

    /**
     * 初始化视图。
     */
    private void initViews()
    {
        TextView emptyView = new TextView(mContext);
        emptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        emptyView.setText("没有查询到相关数据");
        emptyView.setVisibility(View.GONE);
        emptyView.setGravity(Gravity.CENTER);
        ((ViewGroup) my_listview.getParent()).addView(emptyView);
        my_listview.setEmptyView(emptyView);
        mAdapter = new ListViewDialogAdapter(mContext,data,R.layout.item_listview_dialog);
        my_listview.setAdapter(mAdapter);
        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.getSelectCheckBoxMap().clear();
                mAdapter.getSelectCheckBoxMap().put(position, 1);
                mAdapter.notifyDataSetChanged();
                handleEven(position);
            }
        });
        hine_textview.setText(title);
    }

    public abstract void handleEven(int p);
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
}
