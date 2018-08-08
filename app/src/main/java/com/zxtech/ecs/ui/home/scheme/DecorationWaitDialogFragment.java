package com.zxtech.ecs.ui.home.scheme;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/2/27.
 */

public class DecorationWaitDialogFragment extends DialogFragment {

    ImageView wait_gif;
    private LinearLayout wait_layout;
    private int[] topBottomPadding = new int[2];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.WaitDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_decoration_wait, null);
        wait_gif = view.findViewById(R.id.wait_gif);
        wait_layout = view.findViewById(R.id.wait_layout);
        Glide.with(getActivity()).load(R.drawable.decoration_wait).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(wait_gif);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, topBottomPadding[0], 0, topBottomPadding[1]);
        wait_layout.setLayoutParams(layoutParams);
        return view;
    }

    public void setTopBottomPadding(int[] topBottomPadding) {
        this.topBottomPadding = topBottomPadding;
    }

    @Override
    public void onStart() {
        super.onStart();

        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();  //获取对话框当前的参数值
        Window window = getDialog().getWindow();

        DisplayMetrics d = getResources().getDisplayMetrics();
        p.height = d.heightPixels;
        p.width = d.widthPixels;
        window.setAttributes(p);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


}
