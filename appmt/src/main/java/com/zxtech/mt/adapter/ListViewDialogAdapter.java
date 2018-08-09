package com.zxtech.mt.adapter;

import android.content.Context;
import android.widget.RadioButton;


import com.zxtech.mtos.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chw on 2016/8/2.
 */
public class ListViewDialogAdapter extends CommonAdapter<String> {
    private Map<Integer,Integer> selectCheckBoxMap = new HashMap<>();

    public ListViewDialogAdapter(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, String string, int position) {
        holder.setText(R.id.name_textview,string);
        RadioButton selected_radiobutton = holder.getView(R.id.selected_radiobutton);
        if (selectCheckBoxMap.containsKey(position)&&selectCheckBoxMap.get(position)==1){
            selected_radiobutton.setChecked(true);
        }else{
            selected_radiobutton.setChecked(false);
        }
    }

    public Map<Integer, Integer> getSelectCheckBoxMap() {
        return selectCheckBoxMap;
    }

    public void setSelectCheckBoxMap(Map<Integer, Integer> selectCheckBoxMap) {
        this.selectCheckBoxMap = selectCheckBoxMap;
    }
}
