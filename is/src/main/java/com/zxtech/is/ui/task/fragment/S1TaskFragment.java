package com.zxtech.is.ui.task.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;

/**
 * A simple {@link Fragment} subclass.
 */
public class S1TaskFragment extends BaseFragment {


    public static S1TaskFragment newInstance() {
        Bundle args = new Bundle();
        S1TaskFragment fragment = new S1TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_s1_task;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }
}
