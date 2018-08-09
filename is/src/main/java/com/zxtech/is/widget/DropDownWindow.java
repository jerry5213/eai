package com.zxtech.is.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import com.zxtech.is.util.DensityUtil;
import com.zxtech.is.util.ScreenUtils;
import com.zxtech.is.R;
import com.zxtech.is.R2;

import java.util.List;

public abstract class DropDownWindow<T> extends PopupWindow {

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
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        dismissEvents();
    }


    private int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
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

    public abstract void dismissEvents();

    public void init(View view) {
        this.showAsDropDown(view, 0, 0);
    }

    public View findViewById(int id) {
        return mContentView.findViewById(id);
    }
}

