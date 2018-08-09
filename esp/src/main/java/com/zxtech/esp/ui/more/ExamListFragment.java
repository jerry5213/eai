package com.zxtech.esp.ui.more;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxtech.common.util.T;
import com.zxtech.esp.R;
import com.zxtech.esp.data.bean.QuestionsVO;

import java.util.List;

/**
 * Created by SYP521 on 2017/7/28.
 */

public class ExamListFragment extends Fragment {

    private View rootView;
    private RecyclerView rlv;
    private ExamListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_exam_list, container, false);
            setup(rootView);
        }
        return T.getNoParentView(rootView);
    }

    private void setup(View rootView) {

        rootView.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ExamAnswerActivity)getActivity()).closeFragment();
            }
        });

        rlv = (RecyclerView) rootView.findViewById(R.id.rlv);
        GridLayoutManager mgr = new GridLayoutManager(getActivity(),5);
        rlv.setLayoutManager(mgr);
        rlv.setItemAnimator(new DefaultItemAnimator());
        rlv.setHasFixedSize(true);
        adapter = new ExamListAdapter(getActivity());
        rlv.setAdapter(adapter);
        initData();
    }

    private void initData(){

        List<QuestionsVO.Data> datas = ((ExamAnswerActivity) getActivity()).getDatas();
        adapter.setDatas(datas);
        adapter.notifyDataSetChanged();
    }
}
