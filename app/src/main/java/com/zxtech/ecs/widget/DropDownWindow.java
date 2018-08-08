package com.zxtech.ecs.widget;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.DropDownAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.DropDownBean;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.ScreenUtils;

import java.util.List;

import static android.content.ContentValues.TAG;
@SuppressWarnings("unchecked")
public abstract class DropDownWindow<T> extends PopupWindow {

    protected View mContentView;

    protected Context mContext;

    private TextView iconTextView;

    @SuppressWarnings("unchecked")
    public DropDownWindow(Context context, View view, List<T> data, int width, int height) {
        super(width, height);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dropdown_window, null);
        mContentView = contentView;
        mContext = context;
        setFocusable(true);
        setOutsideTouchable(true);

        setWidth(width);
        setHeight(data.size() > 5 ? DensityUtil.dip2px(mContext, 5 * 40) : height);
        setContentView(contentView);
//        setAnimationStyle(R.style.Popup_Animation_Alpha);
        setBackgroundDrawable(new ColorDrawable());
        setTouchable(true);
        initViews(data);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        // showAsDropDown(view, 0, 0);
        int windowPos[] = calculatePopWindowPos(view, contentView);
        int xOff = 0;// 可以自己调整偏移
        windowPos[0] -= xOff;
        showAtLocation(view, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
        // showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1]+view.getHeight());
//        init(view);
    }

    @SuppressWarnings("unchecked")
    public DropDownWindow(Context context, View view, TextView textView, List<T> data, int width, int height) {
        this(context,view,data,width,height);
        iconTextView = textView;
        Drawable image = context.getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        textView.setCompoundDrawables(null, null, image, null);
    }



    @SuppressWarnings("unchecked")
    public void initViews(List<T> data) {
        RecyclerView recyclerView = (RecyclerView) mContentView.findViewById(R.id.drop_down);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        CommonAdapter commonAdapter = new CommonAdapter(mContext, R.layout.item_drop_down, data) {
            @Override
            protected void convert(ViewHolder holder, Object o, int position) {
                holder.setText(R.id.text, o.toString());
            }
        };
        recyclerView.addItemDecoration(new MyItemDecoration(1));
        recyclerView.setAdapter(commonAdapter);
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                initEvents(position);
                dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void dismiss() {
        if (iconTextView != null) {
            Drawable image = mContext.getResources().getDrawable(R.drawable.down);
            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
            iconTextView.setCompoundDrawables(null, null, image, null);
        }
        super.dismiss();
        dismissEvents();
    }


    private int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationInWindow(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight() > getHeight() ? getHeight() : contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            // windowPos[0] = screenWidth - windowWidth;
            windowPos[0] = anchorLoc[0];
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            // windowPos[0] = screenWidth - windowWidth;
            windowPos[0] = anchorLoc[0];
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }


    public abstract void initEvents(int p);

    public void dismissEvents() {

    }

    public void init(View view) {
        this.showAsDropDown(view, 0, 0);
    }

}

