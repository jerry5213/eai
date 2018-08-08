package com.zxtech.ecs.ui.tool.flowanalysis;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/4/4.
 */

public class FlowAnalysisActivity extends BaseActivity {

    private List<String> leftData = new ArrayList<>();
    private List<String> rightData = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_flow_analysis;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar);

        leftData.add("别墅");
        leftData.add("住宅");
        leftData.add("公共交通");
        leftData.add("办公楼");
        leftData.add("机场");

        rightData.add("服务梯");
        rightData.add("客梯");
//        left_wheelview.setTextSize(14);
//        left_wheelview.setTextColorCenter(mContext.getResources().getColor(R.color.dark_red));
//        left_wheelview.setAdapter(new LeftAdapter());
//
//        left_wheelview.setCurrentItem(leftData.size() / 2);
//        right_wheelview.setCyclic(false);
//        right_wheelview.setTextSize(14);
//        right_wheelview.setTextColorCenter(mContext.getResources().getColor(R.color.dark_red));
//        right_wheelview.setAdapter(new RightAdapter());

    }


//    public class LeftAdapter implements WheelAdapter {
//
//
//        @Override
//        public int getItemsCount() {
//            return leftData.size();
//        }
//
//        @Override
//        public Object getItem(int index) {
//            return leftData.get(index);
//        }
//
//        @Override
//        public int indexOf(Object o) {
//            return 0;
//        }
//    }
//
//
//    public class RightAdapter implements WheelAdapter {
//
//
//        @Override
//        public int getItemsCount() {
//            return rightData.size();
//        }
//
//        @Override
//        public Object getItem(int index) {
//            return rightData.get(index);
//        }
//
//        @Override
//        public int indexOf(Object o) {
//            return 0;
//        }
//    }
}
