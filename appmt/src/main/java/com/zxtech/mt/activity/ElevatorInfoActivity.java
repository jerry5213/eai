package com.zxtech.mt.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zxtech.mt.adapter.ElevatorAdapter;
import com.zxtech.mt.entity.BaseElevator;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.widget.FontView;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电梯信息
 * Created by Chw on 2016/8/10.
 */
@Route(path = "/mt/elevatorinfo")
public class ElevatorInfoActivity extends BaseActivity {
    private ListView elevator_listview;

    private ElevatorAdapter mAdapter;


    private EditText search_edittext;

    private FontView icon_speech;

    private List<BaseElevator> elevators=  new ArrayList<>();

    private List<BaseElevator> searchList=  new ArrayList<>();


    private TextView speech_content_textview;

    private String best_result = "";

    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_elevator, null);
        main_layout.addView(view);
        mContext = this;
        title_textview.setText(getString(R.string.menu_elevator_information));
        setBottomLayoutHide();
    }

    @Override
    protected void findView() {
        elevator_listview = (ListView) findViewById(R.id.elevator_listview);
        search_edittext = (EditText) findViewById(R.id.search_edittext);
        icon_speech = (FontView) findViewById(R.id.icon_speech);

    }

    @Override
    protected void setListener() {
        //icon_speech.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Map<String, String> param = new HashMap<>();
        param.put("grp_id", SPUtils.get(mContext,"grp_id","").toString());
        HttpUtil.getInstance(mContext).request("/mtmo/getelevatorbygroup.mo", param, new HttpCallBack<List<BaseElevator>>() {
            @Override
            public void onSuccess(List<BaseElevator> list) {

                if (list != null) {
                    elevators = list;
                    searchList.addAll(elevators);
                }
                mAdapter = new ElevatorAdapter(mContext,elevators,R.layout.item_elevator);
                elevator_listview.setAdapter(mAdapter);
                searchAction();
            }

            @Override
            public void onFail(String msg) {
            }
        });


    }

    @Override
    protected void initView() {


    }



    private void searchAction() {
        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (elevators != null) {
                    elevators.clear();
                }
                for (BaseElevator elevator:searchList) {
                    if (elevator.getProj_name()!=null&&elevator.getProj_name().contains(text) ){
                        elevators.add(elevator);
                    }else if (elevator.getElevator_code()!=null&&elevator.getElevator_code().contains(text) ){
                        elevators.add(elevator);
                    }
                }
                    mAdapter.notifyDataSetChanged();
            }
        });



    }

    private PopupWindow popupWindow;
    // 声明PopupWindow对应的视图
    private View popupView;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.icon_speech) {
            popupView = View.inflate(this, R.layout.window_speech, null);
            speech_content_textview = (TextView) popupView.findViewById(R.id.speech_content_textview);
            // 参数2,3：指明popupwindow的宽度和高度
            popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }
            });

            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(200);
            popupWindow.showAtLocation(this.findViewById(R.id.icon_speech), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            popupView.startAnimation(animation);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
