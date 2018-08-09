package com.zxtech.esp.ui.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxtech.esp.R;
import com.zxtech.esp.data.bean.CourseTypeVO;

import java.util.Locale;

import g.api.adapter.GBaseAdapter;
import g.api.adapter.GViewHolder;

/**
 * Created by SYP521 on 2017/7/3.
 */

public class HomeAdapter extends GBaseAdapter<CourseTypeVO.Data> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder(parent.getContext());
            convertView = viewHolder.getConvertView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.showData(getItem(position),position,getCount());

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    static class ViewHolder extends GViewHolder {

        private TextView firstCate;
        private RecyclerView recyclerView;
        private GalleryAdapter mAdapter;
        private TextView mLine;
        private String lan = Locale.getDefault().getLanguage();

        public ViewHolder(Context context) {
            super(context);
            mLine = (TextView) findViewById(R.id.tv_line);
            firstCate = (TextView) findViewById(R.id.tv_first_cate);
            recyclerView = (RecyclerView) findViewById(R.id.id_recyclerview_horizontal);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            mAdapter = new GalleryAdapter(context);
            recyclerView.setAdapter(mAdapter);
        }

        @Override
        protected int onSetConvertViewResId() {
            return R.layout.adapter_list_home;
        }

        public void showData(CourseTypeVO.Data itemData,int position,int count) {

            if(position == count-1){
                mLine.setVisibility(View.GONE);
            }else{
                mLine.setVisibility(View.VISIBLE);
            }
            if("en".equals(lan)){
                firstCate.setText(itemData.getParent_name_en());
            }else{
                firstCate.setText(itemData.getParent_name());
            }
            mAdapter.setDatas(itemData.getChilds());
            mAdapter.notifyDataSetChanged();
        }
    }
}
