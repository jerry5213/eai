package com.zxtech.ecs.ui.home.qmsmanager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.GenerateAnswer;

import java.util.List;

/**
 * Created by syp521 on 2018/4/11.
 */

public class QmsGenerateAnswerDialog extends Dialog {

    private RecyclerView recycleView;
    private Context context;
    private List<GenerateAnswer.DataBean> data;
    private MyAdapter adapter;

    public QmsGenerateAnswerDialog(@NonNull Context context,List<GenerateAnswer.DataBean> data) {
        super(context);
        this.data = data;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_generate_answer);
        //getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        initDialogSize();

        recycleView = findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(context,R.drawable.divider_line_gray));
        recycleView.addItemDecoration(divider);

        adapter = new MyAdapter(R.layout.item_generate_answer,data);
        recycleView.setAdapter(adapter);
    }

    private void initDialogSize() {
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.9);    //宽度设置为屏幕的0.9
        getWindow().setAttributes(p);     //设置生效
    }

    protected class MyAdapter extends BaseQuickAdapter<GenerateAnswer.DataBean,BaseViewHolder>{

        public MyAdapter(int layoutResId, @Nullable List<GenerateAnswer.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GenerateAnswer.DataBean item) {

            helper.setText(R.id.tv_qms_question_desc,"问："+item.getQuestion())
                    .setText(R.id.tv_qms_answer,"答："+item.getAnswer());
        }
    }
}
