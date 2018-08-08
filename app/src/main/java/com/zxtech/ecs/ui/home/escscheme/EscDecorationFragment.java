package com.zxtech.ecs.ui.home.escscheme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.PartAdapter;
import com.zxtech.ecs.adapter.PartConentAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.scheme.detail.BaseSchemeDetailFragment;
import com.zxtech.ecs.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 扶梯装潢
 * Created by syp523 on 2018/1/22.
 */

public class EscDecorationFragment extends BaseSchemeDetailFragment {


    @BindView(R.id.part_rv)
    RecyclerView part_rv;
    @BindView(R.id.part_content_rv)
    RecyclerView part_content_rv;
    @BindView(R.id.decoration_framelayout)
    FrameLayout decoration_framelayout;


    private PartAdapter adapter = null;
    private PartConentAdapter optionsAdapter = null;
    private List<Parameters> parts = new ArrayList<>();
    private List<Parameters.Option> options = new ArrayList<>();
    private Parameters selectedParameters = new Parameters();
    //部位code 装潢图片控件map
    private HashMap<String, ImageView> decorationMap = new HashMap<>();

    public static EscDecorationFragment newInstance(List<Parameters> parameters) {
        Bundle args = new Bundle();
        EscDecorationFragment fragment = new EscDecorationFragment();
        args.putSerializable("data", (Serializable) parameters);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_esc_decoration;
    }


    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new PartAdapter(getActivity(), R.layout.item_part, parts, language);
        part_rv.setLayoutManager(layoutManager);
        part_rv.setAdapter(adapter);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        part_content_rv.setLayoutManager(layoutManager1);
        optionsAdapter = new PartConentAdapter(getActivity(), R.layout.item_part_content, options);
        part_content_rv.setAdapter(optionsAdapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                if (parts.get(position).getOption() != null) {
                    options.clear();
                    selectedParameters = parts.get(position);

                    options.addAll(parts.get(position).getOption());
                    optionsAdapter.setSelectedValue(getParamsValue(selectedParameters.getProECode())); //选中值
                    optionsAdapter.notifyDataSetChanged();
                    //选中变色
                    adapter.setSelectedPosition(position);
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showLong(getString(R.string.msg8));
                }


            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        optionsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String selectValue = options.get(position).getValue();
                if (selectValue == null || selectValue.equals(optionsAdapter.getSelectedValue())) {
                    ToastUtil.showLong(getString(R.string.msg2));
                    return; //已选择的商品不再选择
                }
                putParams(selectedParameters.getProECode(), selectValue);
                optionsAdapter.setSelectedValue(selectValue);
                optionsAdapter.notifyDataSetChanged();
                //替换大图
                updateEscDecoration(selectedParameters.getProECode(), options.get(position).getEscImagePath());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        List<Parameters> data = (List<Parameters>) getArguments().getSerializable("data");

        if (data != null && data.size() > 0) {
            initView(data);
        }else {
            baseResponseObservable = HttpFactory.getApiService().getParams(Constants.ESCALATOR, getElevatorType(), getElevatorProduct(), Constants.ESC_DECORATION_PARAMETERS, null);
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<List<Parameters>>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<List<Parameters>>>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse<List<Parameters>> response) {
                            initView(response.getData());
                        }
                    });
        }

    }

    private void initView(List<Parameters> initData){
        parts.addAll(initData);
        //初始化第一项
        options.addAll(parts.get(0).getOption());
        selectedParameters = parts.get(0);
        optionsAdapter.setSelectedValue(getParamsValue(selectedParameters.getProECode())); //选中值
        optionsAdapter.notifyDataSetChanged();
        //选中变色
        adapter.setSelectedPosition(0);
        adapter.notifyDataSetChanged();
        //初始化大图
        initEscDecoration();
    }

    /**
     * 初始化装潢区
     */
    private void initEscDecoration() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < parts.size(); i++) {
            Parameters parameters = parts.get(i);
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(layoutParams);
            decoration_framelayout.addView(imageView);
            decorationMap.put(parameters.getProECode(), imageView);
            String bigImageUrl = parameters.getOptionBigimage(getParamsValue(parameters.getProECode()));
            if (bigImageUrl != null) {
                Glide.with(mContext).load(bigImageUrl).crossFade().into(imageView);
            }
        }
    }

    /**
     * @param code 部位编码
     * @param url  装潢大图 url
     */
    private void updateEscDecoration(String code, String url) {
        ImageView imageView = decorationMap.get(code);
        if (imageView != null && url != null) {
            showProgress();
            Glide.with(mContext).load(url).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    dismissProgress();
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    dismissProgress();
                    return false;
                }
            }).into(imageView);
        }
    }


    @Override
    public void scriptReturnParam(List<ScriptReturnParam> returnParams) {

    }
}
