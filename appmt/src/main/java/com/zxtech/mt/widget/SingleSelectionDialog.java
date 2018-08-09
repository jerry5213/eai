package com.zxtech.mt.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;


import com.zxtech.mt.adapter.CommonAdapter;
import com.zxtech.mt.adapter.ViewHolder;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by chw on 2017/5/11.
 */
public abstract class SingleSelectionDialog<T> extends Dialog {
    private List<T> datas;
    private String title;
    private Context mContext;
    private Object object;
    private int selectedPostion = -1;

    public abstract void confrim(Object object);
    public abstract void layout(ViewHolder holder, Object object, int position);

    public SingleSelectionDialog(Context context, String title,  List<T> datas) {
        super(context, R.style.DialogTheme);
        this.mContext = context;
        this.title = title;
        this.datas = datas;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_selection_dialog);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics metric = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels/3*2;     // 屏幕宽度（像素）
        lp.width = width; // 宽度

        TextView title_textview = (TextView) findViewById(R.id.title_textview);
        ListView listview = (ListView) findViewById(R.id.listview);
        Button confirm_button = (Button) findViewById(R.id.confirm_button);
        Button cancel_button = (Button) findViewById(R.id.cancel_button);

        title_textview.setText(this.title);

        final CommonAdapter adapter = new CommonAdapter(mContext,datas,R.layout.item_single_selection) {
            @Override
            public void convert(ViewHolder holder, Object o, int position) {
                RadioButton selected_radiobutton = holder.getView(R.id.selected_radiobutton);
                if (selectedPostion == position) {
                  //  selected_rbutton.setChecked(true);
                    selected_radiobutton.setChecked(true);
                }else{
                  //  selected_rbutton.setChecked(false);
                    selected_radiobutton.setChecked(false);
                }
                layout(holder,o,position);
            }
        };
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                object = parent.getAdapter().getItem(position);
                selectedPostion = position;
                adapter.notifyDataSetChanged();
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object == null) {
                    ToastUtil.showLong(mContext,title);
                }else{
                    confrim(object);
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }
}
