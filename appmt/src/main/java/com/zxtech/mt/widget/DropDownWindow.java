package com.zxtech.mt.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;


import com.zxtech.mt.adapter.DropDownAdapter;
import com.zxtech.mtos.R;

import java.util.List;


public abstract class DropDownWindow extends PopupWindow {

    protected View mContentView;
    
    protected Context mContext;

    public DropDownWindow() {
        super();
    }

    public DropDownWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DropDownWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DropDownWindow(Context context) {
        super(context);
    }

    public DropDownWindow(int width, int height) {
        super(width, height);
    }

    public DropDownWindow(View contentView, int width, int height,
            boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public DropDownWindow(View contentView) {
        super(contentView);
    }

    public DropDownWindow(Context context, View view,List<String> data,int width, int height) {
    	 super(width,height);
    	View contentView = LayoutInflater.from(context).inflate(R.layout.dropdown_window, null);
        mContentView = contentView;
        mContext = context;
        setFocusable(true);
        setOutsideTouchable(true);
        setWidth(width);
        setHeight(height);
        setContentView(contentView);
//        setAnimationStyle(R.style.Popup_Animation_Alpha);
        setBackgroundDrawable(new ColorDrawable());
        setTouchable(true);
        initViews(data);
        showAsDropDown(view, 0, 0);
//        init(view);
    }

    public  void initViews(List<String> data) {
    	ListView listView = (ListView) mContentView.findViewById(R.id.drop_down);
    	DropDownAdapter adapter = new DropDownAdapter(mContext, data, R.layout.item_drop_down);
    	listView.setAdapter(adapter);
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long l) {
				initEvents(position);
				
			}
		});
    }

    public abstract void initEvents(int p);

    public void init(View view){
    	 this.showAsDropDown(view, 0, 0);
    }

    public View findViewById(int id) {
        return mContentView.findViewById(id);
    }
}

