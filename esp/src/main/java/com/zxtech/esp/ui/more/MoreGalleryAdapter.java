package com.zxtech.esp.ui.more;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zxtech.common.util.T;
import com.zxtech.esp.R;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.CourseTypeVO;

import java.util.Locale;

import g.api.adapter.GRecyclerAdapter;
import g.api.adapter.GRecyclerViewHolder;


/**
 * Created by SYP521 on 2017/6/30.
 */

public class MoreGalleryAdapter extends GRecyclerAdapter<CourseTypeVO.ChildsBean, MoreGalleryAdapter.ViewHolder> {

    public MoreGalleryAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_recycler_home, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.setData(getItem(i));
        viewHolder.showData();
    }

    public static class ViewHolder extends GRecyclerViewHolder<CourseTypeVO.ChildsBean> {
        LinearLayout mLayout;
        ImageView mImg;
        TextView mText;
        DisplayImageOptions opts;
        private String lan = Locale.getDefault().getLanguage();

        public ViewHolder(View view) {
            super(view);
            opts = new DisplayImageOptions.Builder()
                    .showImageOnFail(R.mipmap.img_null)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .displayer(new RoundedBitmapDisplayer(T.dip2px(view.getContext(), 15.0f)))
                    .build();
            mLayout = (LinearLayout) view.findViewById(R.id.ll_gallery);
            int sw = view.getContext().getResources().getDisplayMetrics().widthPixels;
            mLayout.getLayoutParams().width = (int) ((sw - T.dip2px(view.getContext(), 4 * 15)) / 3.5f);
            mLayout.getLayoutParams().height = mLayout.getLayoutParams().width + T.dip2px(view.getContext(), 42);

            mImg = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
            mText = (TextView) view.findViewById(R.id.id_index_gallery_item_text);
        }

        @Override
        public boolean isOpenItemClick() {
            return true;
        }

        @Override
        protected void onShowData(CourseTypeVO.ChildsBean itemData) {
            if("en".equals(lan)){
                mText.setText(itemData.getChild_name_en());
            }else{
                mText.setText(itemData.getChild_name());
            }
            ImageLoader.getInstance().displayImage(URL.getInstance().getFullUrl(itemData.getImage_url()), mImg, opts);
        }

        @Override
        public void onItemClick(View v, CourseTypeVO.ChildsBean itemData) {
            super.onItemClick(v, itemData);

            Intent in = new Intent(v.getContext(), MoreCourseActivity.class);
            in.putExtra("typeId", itemData.getChild_id());
            v.getContext().startActivity(in);
        }
    }
}
