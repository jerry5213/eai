package com.zxtech.is.ui.team.activity;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zxtech.is.util.DensityUtil;
import com.zxtech.is.util.ScreenUtils;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.widget.BoardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by duomi on 2018/4/24.
 */

public class AutographDialogFragment extends DialogFragment {

    ////////////////////////////////////////回调方法//////////////////////////////////////////////////////
    private signSuccess signsuccess;

    public signSuccess getSignsuccess() {
        return signsuccess;
    }

    public void setSignsuccess(signSuccess signsuccess) {
        this.signsuccess = signsuccess;
    }

    public interface signSuccess {

        void mPhoto(Bitmap photoSign);
    }
/////////////////////////////////////////////////////////////////////////////////////////////

    @BindView(R2.id.sign_bv)
    BoardView boardView;


    public static AutographDialogFragment newInstance() {
        AutographDialogFragment fragment = new AutographDialogFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ScreenUtils.getScreenWidth(getActivity()) - DensityUtil.dip2px(getActivity(), 80);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_autograph, null);
        ButterKnife.bind(this, view);

        //加这句话去掉自带的标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
//                showImage();

        return view;
    }

    @OnClick({R2.id.sign_submit, R2.id.sign_redo, R2.id.sign_cancel})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.sign_cancel) {
            this.dismiss();

        } else if (i == R.id.sign_redo) {
            boardView.redo();

        } else if (i == R.id.sign_submit) {
            signsuccess.mPhoto(boardView.getmBitmap());
            this.dismiss();

        } else {
        }
    }
}
