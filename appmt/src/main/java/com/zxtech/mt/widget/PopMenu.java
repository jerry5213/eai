package com.zxtech.mt.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 */
public class PopMenu {

    private List<Map<String,Object>> itemList;
    private Context context;
    private PopupWindow popupWindow ;
    private ListView listView;
    //private OnItemClickListener listener;


    public PopMenu(Context context,int width,int height) {
        // TODO Auto-generated constructor stub
        this.context = context;

        itemList = new ArrayList<Map<String,Object>>();

        View view = LayoutInflater.from(context).inflate(R.layout.popmenu, null);

        listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(new PopAdapter());

        popupWindow = new PopupWindow(view,width,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        //this.listener = listener;
        listView.setOnItemClickListener(listener);
    }

    public void addItems(List<Map<String,Object>> items) {

        itemList=items;
    }

    public void addItem(String item) {
        //itemList.add(item);
    }

    public void showAsDropDown(View parent) {
        popupWindow.showAsDropDown(parent,10,0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    private final class PopAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.popview_item, null);
                holder = new ViewHolder();

                convertView.setTag(holder);

                holder.groupItem = (TextView) convertView.findViewById(R.id.text);
                holder.iconItem=(ImageView)convertView.findViewById(R.id.icon);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            int drawable = 0;
            if (itemList.get(position).containsKey("icon")) {
                drawable = (int) itemList.get(position).get("icon");
            }
            holder.iconItem.setBackgroundResource(drawable);
            holder.groupItem.setText(itemList.get(position).get("text").toString());

            return convertView;
        }

        private final class ViewHolder {
            TextView groupItem;
            ImageView iconItem;
        }
    }
}
