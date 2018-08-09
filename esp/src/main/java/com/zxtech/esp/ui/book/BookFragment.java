package com.zxtech.esp.ui.book;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxtech.common.util.T;
import com.zxtech.esp.R;

/**
 * Created by SYP521 on 2017/6/29.
 */

public class BookFragment extends Fragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_book, container, false);
            setup(rootView);
        }
        return T.getNoParentView(rootView);
    }

    private void setup(View view) {
        T.fitSystemWindow19(view.findViewById(R.id.sp_fit_system));
        //Toast.makeText(view.getContext(),this.getClass().getSimpleName(),Toast.LENGTH_LONG).show();
    }
}
