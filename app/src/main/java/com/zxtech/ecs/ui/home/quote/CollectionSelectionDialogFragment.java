package com.zxtech.ecs.ui.home.quote;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Collection;
import com.zxtech.ecs.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by syp523 on 2018/2/27.
 */

public class CollectionSelectionDialogFragment extends DialogFragment implements View.OnClickListener {

    private RecyclerView content_rv;
    private MyAdapter mAdatper;
    private ImageButton close_iv;
    private TextView apply_tv;
    public CollectionSelectionDialogFragmentCallBack callBack;
    private List<Collection> data = new ArrayList<>();
    private String type;
    private TextView param1_title,param2_title;


    public static CollectionSelectionDialogFragment newInstance() {
        CollectionSelectionDialogFragment fragment = new CollectionSelectionDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加这句话去掉自带的标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_quote_collection_selection, null);
        content_rv = view.findViewById(R.id.content_rv);
        close_iv = view.findViewById(R.id.close_iv);
        apply_tv = view.findViewById(R.id.apply_tv);
        param1_title = view.findViewById(R.id.param1_title);
        param2_title = view.findViewById(R.id.param2_title);
        apply_tv.setOnClickListener(this);
        close_iv.setOnClickListener(this);
        if (Constants.ELEVATOR.equals(type)) {
            param1_title.setText(getString(R.string.load_capacity));
            param2_title.setText(getString(R.string.speed));
        } else {
            param1_title.setText(getString(R.string.pedal_width));
            param2_title.setText(getString(R.string.angle_of_tilt));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        content_rv.setLayoutManager(layoutManager);
        mAdatper = new MyAdapter(getActivity(), R.layout.item_collection_selection, data);
        content_rv.setAdapter(mAdatper);

        return view;
    }

    public List<Collection> getData() {
        return data;
    }

    public void setData(List<Collection> data) {
        this.data = data;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager m = getDialog().getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();  //获取对话框当前的参数值

        p.height = (int) (d.getHeight() * 0.5);
        p.width = (int) (d.getWidth() * 0.85);
        getDialog().getWindow().setAttributes(p);

        int roundRadius = 20; //
        int strokeColor = getActivity().getResources().getColor(R.color.white);


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setColor(strokeColor);
        getDialog().getWindow().setBackgroundDrawable(gd);//设置生效
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_iv) {
            dismiss();
        } else if (v.getId() == R.id.apply_tv) {
            if (callBack != null) {
                if (this.data.size() > 0) {
                    callBack.confirm(this.data.get(mAdatper.selectedPosition));
                }
            }
            dismiss();
        }
    }

    class MyAdapter extends CommonAdapter<Collection> {
        private Integer selectedPosition = 0;

        public MyAdapter(Context context, int layoutId, List<Collection> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, Collection collection, final int position) {
            ImageView icon_iv = holder.getView(R.id.icon_iv);
            if (selectedPosition != null && selectedPosition == position) {
                icon_iv.setImageResource(R.drawable.match_check);
            }else{
                icon_iv.setImageResource(R.drawable.match);
            }
            holder.setText(R.id.name_tv,collection.getCollectionName());
            holder.setText(R.id.price_tv, Util.numberFormat(collection.getElevatorPrice()));
            holder.setText(R.id.type_tv,collection.getElevatorProduct());
            if (Constants.ELEVATOR.equals(type)) {
                holder.setText(R.id.param1_tv,collection.getEl_Load());
                holder.setText(R.id.param2_tv,collection.getEl_V());
            }else {
                holder.setText(R.id.param1_tv,collection.getEs_SW());
                holder.setText(R.id.param2_tv,collection.getEs_Angle());
            }


            icon_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelectedPosition(position);
                }
            });
        }

        public void setSelectedPosition(Integer selectedPosition) {
            this.notifyItemChanged(this.selectedPosition);
            this.selectedPosition = selectedPosition;
            this.notifyItemChanged(selectedPosition);
        }
    }

    public interface CollectionSelectionDialogFragmentCallBack {
        void confirm(Collection collection);
    }
}
