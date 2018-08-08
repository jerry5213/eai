package com.zxtech.ecs.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;

import java.io.File;
import java.util.List;

/**
 * Created by syp523 on 2018/1/23.
 */

public class RequireAttachAdapter extends CommonAdapter<String> {
    private RequireAttachAdapterCallBack callBack;

    public RequireAttachAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        ImageView delete_iv = holder.getView(R.id.delete_iv);
        if (s != null) {

            if (s.startsWith("http")) {
                Glide.with(mContext)
                        .load(s)
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_image)
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.attach_iv));
            } else {
                Glide.with(mContext)
                        .load(new File(s))
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_image)
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.attach_iv));
            }
            delete_iv.setVisibility(View.VISIBLE);
            delete_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack != null) {
                        callBack.removeAttach(position);
                    }
                }
            });
        } else {
            Glide.with(mContext)
                    .load(R.drawable.new_attach)
                    .placeholder(R.drawable.new_attach)
                    .error(R.drawable.new_attach)
                    .centerCrop()
                    .dontAnimate()
                    .into((ImageView) holder.getView(R.id.attach_iv));
            delete_iv.setVisibility(View.GONE);

        }
    }


    public void setCallBack(RequireAttachAdapterCallBack callBack) {
        this.callBack = callBack;
    }

    public interface RequireAttachAdapterCallBack {
        void removeAttach(int position);
    }
}
