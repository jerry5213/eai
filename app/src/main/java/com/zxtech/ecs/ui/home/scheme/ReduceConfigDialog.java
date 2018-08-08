package com.zxtech.ecs.ui.home.scheme;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ReduceConfigSchemeAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.model.ReduceConfig;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ConvertUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static com.zxtech.ecs.common.Constants.DIMEN_AQDJ;
import static com.zxtech.ecs.common.Constants.DIMEN_CZFW;
import static com.zxtech.ecs.common.Constants.DIMEN_JNDJ;
import static com.zxtech.ecs.common.Constants.DIMEN_MGD;
import static com.zxtech.ecs.common.Constants.DIMEN_SSD;

/**
 * Created by syp523 on 2018/2/7.
 */

public class ReduceConfigDialog extends DialogFragment implements View.OnClickListener, ReduceConfigSchemeAdapter.ReduceConfigSchemeAdapterCallBack,ReduceConfigDetailDialogFragment.ReduceConfigDetailDialogFragmentCallBack {
    TextView confirm_btn;
    TextView next_btn;
    TextView last_btn;
    ImageView miss_iv;

    RecyclerView scheme_rv;
    private ReduceConfigSchemeAdapter adapter = null;
    private List<ReduceConfig> datas = new ArrayList<>();
    private List<ReduceConfig> totalDatas = new ArrayList<>();
    private static final int FIRST_PAGE = 0;
    private static final int SECOND_PAGE = 1;
    private static final int THIRD_PAGE = 2;
    private int pageSize = 3;

    private int currentPage = FIRST_PAGE;
    LinearLayout dimens_layout;

    private Map<String, Boolean> selectedMap = new HashMap<>();

    private List<Programme> changeList = new ArrayList<>();

    private Map<String, Boolean> fixParams = new HashMap<>();

    private HashMap<String, String> rawMap = new HashMap<>();

    private HashMap<String, String> rawTextMap = new HashMap<>();


    private String budget;

    private Observable baseResponseObservable = null;
    public OnDialogListener mlistener;

    private List<List<ReduceConfig>> reduceConfigs = new ArrayList<>();


    public static ReduceConfigDialog newInstance() {
        ReduceConfigDialog fragment = new ReduceConfigDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_scheme_config, null);
        confirm_btn = view.findViewById(R.id.confirm_btn);
        next_btn = view.findViewById(R.id.next_btn);
        last_btn = view.findViewById(R.id.last_btn);
        scheme_rv = view.findViewById(R.id.scheme_rv);
        dimens_layout = view.findViewById(R.id.dimens_layout);
        miss_iv = view.findViewById(R.id.miss_iv);
        confirm_btn.setOnClickListener(this);
        next_btn.setOnClickListener(this);
        last_btn.setOnClickListener(this);
        miss_iv.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        scheme_rv.setLayoutManager(linearLayoutManager);
        adapter = new ReduceConfigSchemeAdapter(getActivity(), R.layout.item_reduce_config, datas);
        adapter.callBack = this;
        scheme_rv.setAdapter(adapter);
        for (int i = 0; i < dimens_layout.getChildCount(); i++) {
            View childView = dimens_layout.getChildAt(i);
            childView.setBackground(getDefaultColor());
            childView.setBackground(switchStatus(childView, false));
            // ((TextView) childView).setTextColor(getActivity().getResources().getColor(R.color.default_text_black_color));
            selectedMap.put((String) childView.getTag(), false);
            childView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedMap.get(v.getTag()) == null || !selectedMap.get(v.getTag())) {

                        v.setBackground(switchStatus(v, true));
                        // ((TextView) v).setTextColor(getActivity().getResources().getColor(R.color.white));
                        selectedMap.put((String) v.getTag(), true);
                    } else {
                        v.setBackground(getDefaultColor());
                        v.setBackground(switchStatus(v, false));
                        // ((TextView) v).setTextColor(getActivity().getResources().getColor(R.color.default_text_black_color));
                        selectedMap.put((String) v.getTag(), false);
                    }
                }
            });
        }

        return view;
    }

    private GradientDrawable switchStatus(View v, boolean isSelect) {
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int strokeColor = 0;
        switch ((String) v.getTag()) {
            case DIMEN_SSD:
                strokeColor = getActivity().getResources().getColor(R.color.light_blue);
                break;
            case DIMEN_JNDJ:
                strokeColor = getActivity().getResources().getColor(R.color.green);
                break;
            case DIMEN_MGD:
                strokeColor = getActivity().getResources().getColor(R.color.grass_green);
                break;
            case DIMEN_CZFW:
                strokeColor = getActivity().getResources().getColor(R.color.yellow);
                break;
            case DIMEN_AQDJ:
                strokeColor = getActivity().getResources().getColor(R.color.dark_red);
                break;
        }

        if (isSelect) {
            ((TextView) v).setTextColor(getResources().getColor(R.color.white));
        } else {
            ((TextView) v).setTextColor(strokeColor);
        }


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        if (isSelect) {
            gd.setColor(strokeColor);
        }
        return gd;

    }


    public void setRawTextMap(HashMap<String, String> rawTextMap) {
        this.rawTextMap = rawTextMap;
    }

    public void setRawMap(HashMap<String, String> rawMap) {
        this.rawMap = rawMap;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }


    private GradientDrawable getDefaultColor() {
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int strokeColor = getActivity().getResources().getColor(R.color.main_grey);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setColor(strokeColor);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.confirm_btn:
                Gson gson = new Gson();
                JsonObject param = new JsonObject();
                param.add("dimensions", ConvertUtil.joinDimens(rawMap, selectedMap));
                param.add("fixParams", gson.fromJson(gson.toJson(fixParams), JsonObject.class));
                param.add("parameters", gson.fromJson(gson.toJson(rawMap), JsonObject.class));
                param.addProperty("budget", budget == null ? "" : budget);
                param.addProperty("currentPrice", rawMap.get("Price"));
                param.addProperty("type", "4");
                param.addProperty("lower", "true");
                JsonObject salesParamJson = new JsonObject();
                salesParamJson.addProperty("DrawingNumber", rawMap.get("SalesParameterDrawingNo"));
                HashMap<String, String> paramMap = rawMap;
                HashMap<String, String> baseParams = new HashMap<>();
                baseParams.put(Constants.CODE_DIM_SHAFT_DEPTH_WD, paramMap.get(Constants.CODE_DIM_SHAFT_DEPTH_WD));
                baseParams.put(Constants.CODE_DIM_SHAFT_WIDTH_WW, paramMap.get(Constants.CODE_DIM_SHAFT_WIDTH_WW));
                baseParams.put(Constants.CODE_ELE_TYPE, paramMap.get(Constants.CODE_ELE_TYPE));
                baseParams.put(Constants.CODE_MACHINEROOM, paramMap.get(Constants.CODE_MACHINEROOM));
                baseParams.put(Constants.CODE_QTY_NUMBER_OF_FLOORS, paramMap.get(Constants.CODE_QTY_NUMBER_OF_FLOORS));
                salesParamJson.add("InputParameter", gson.fromJson(gson.toJson(baseParams), JsonObject.class));
                param.add("salesParameter", salesParamJson);
                param.add("price", ConvertUtil.joinPrice(rawMap.get("PriceDrawingNo")));
                baseResponseObservable = HttpFactory.getApiService().reduceConfigure(param.toString());
                baseResponseObservable
                        .compose(RxHelper.<BaseResponse<List<Programme>>>rxSchedulerHelper())
                        .subscribe(new DefaultObserver<BaseResponse<List<Programme>>>(getActivity(), true) {
                            @Override
                            public void onSuccess(BaseResponse<List<Programme>> response) {

                                datas.clear();
                                changeList = response.getData();
                                totalDatas.clear();
                                ReduceConfig reduceConfig = null;
                                ReduceConfig.ReduceParam reduceParam = null;
                                HashMap<String, String> stringStringHashMap = rawTextMap;
                                for (Programme programme : response.getData()) {
                                    List<Programme.DimensionsBean.ParamsBean> paramsBeanList = ConvertUtil.convertProgramme(programme);
                                    reduceConfig = new ReduceConfig();
                                    reduceConfig.setReducePrice(String.valueOf(programme.getPrice() - Integer.valueOf(rawMap.get("Price"))));
                                    int i = 0;
                                    for (Programme.DimensionsBean.ParamsBean paramsBean : paramsBeanList) {
                                        if (rawMap.get(paramsBean.getProECode()) != null && rawMap.get(paramsBean.getProECode()).equals(paramsBean.getValue()))
                                            continue; //参数没有改变
                                        reduceParam = new ReduceConfig.ReduceParam();
                                        reduceParam.setParamName(paramsBean.getName());
                                        reduceParam.setChangeBefore(stringStringHashMap.get(paramsBean.getProECode()));
                                        reduceParam.setChangeAfater(paramsBean.getText());
                                        if (i == 0) {
                                            reduceConfig.setFirstReduceParam(reduceParam);
                                        }
                                        i++;
                                        reduceConfig.getReduceParams().add(reduceParam);
                                    }
                                    totalDatas.add(reduceConfig);
                                }

                                reduceConfigs = paging(totalDatas, 3);
                                if (reduceConfigs.size() > 0) {
                                    datas.addAll(reduceConfigs.get(0));
                                    currentPage = FIRST_PAGE;

                                }
                                if (reduceConfigs.size() > 1) {
                                    next_btn.setBackgroundResource(R.drawable.solid_main_border);
                                    next_btn.setTextColor(getResources().getColor(R.color.main));
                                }
                                adapter.setCurrentPage(currentPage);
                                adapter.notifyDataSetChanged();


                            }
                        });


                break;
            case R.id.next_btn:
                if (currentPage != THIRD_PAGE && reduceConfigs.size() > currentPage + 1) {
                    datas.clear();
                    datas.addAll(reduceConfigs.get(currentPage + 1));
                    currentPage++;
                    adapter.setCurrentPage(currentPage);
                    adapter.notifyDataSetChanged();
                    last_btn.setBackgroundResource(R.drawable.solid_main_border);
                    last_btn.setTextColor(getResources().getColor(R.color.main));
                    if (reduceConfigs.size() > currentPage + 1) {
                        next_btn.setBackgroundResource(R.drawable.solid_main_border);
                        next_btn.setTextColor(getResources().getColor(R.color.main));
                    } else {
                        next_btn.setBackgroundResource(R.drawable.text_grey_border);
                        next_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                    }
                }
                break;
            case R.id.last_btn:
                if (currentPage != FIRST_PAGE) {
                    datas.clear();
                    datas.addAll(reduceConfigs.get(currentPage - 1));
                    currentPage--;
                    adapter.setCurrentPage(currentPage);
                    adapter.notifyDataSetChanged();
                    if (currentPage == FIRST_PAGE) {
                        last_btn.setBackgroundResource(R.drawable.text_grey_border);
                        last_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                        next_btn.setBackgroundResource(R.drawable.solid_main_border);
                        next_btn.setTextColor(getResources().getColor(R.color.main));
                    }
                }
                break;
            case R.id.miss_iv:
                dismiss();
                break;
        }

    }


    private List<List<ReduceConfig>> paging(List<ReduceConfig> list, int pageSize) {
        int totalCount = list.size();
        int pageCount;
        int m = totalCount % pageSize;

        if (m > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }

        List<List<ReduceConfig>> totalList = new ArrayList<>();
        for (int i = 1; i <= pageCount; i++) {
            if (m == 0) {
                List<ReduceConfig> subList = list.subList((i - 1) * pageSize, pageSize * (i));
                totalList.add(subList);
            } else {
                if (i == pageCount) {
                    List<ReduceConfig> subList = list.subList((i - 1) * pageSize, totalCount);
                    totalList.add(subList);
                } else {
                    List<ReduceConfig> subList = list.subList((i - 1) * pageSize, pageSize * i);
                    totalList.add(subList);
                }
            }
        }

        return totalList;
    }


    @Override
    public void dismiss() {
        super.dismiss();
        if (baseResponseObservable != null) {
            baseResponseObservable.unsubscribeOn(Schedulers.io());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager m = getDialog().getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = (int) (d.getHeight() * 0.57);
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void application(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.reduction_with_modified).setMessage(R.string.msg3)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mlistener.onDialogClick(changeList.get(position + currentPage * pageSize));
                        dismiss();

                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void reduceDetail(int position) {
        ReduceConfigDetailDialogFragment dialogFragment = ReduceConfigDetailDialogFragment.newInstance();
        dialogFragment.callBack = this;
        dialogFragment.setReduceConfig(adapter.getDatas().get(position));
        dialogFragment.setPosition(position);
        dialogFragment.show(getFragmentManager(), "reduce_detail");
    }

    @Override
    public void detailApplication(int position) {
        mlistener.onDialogClick(changeList.get(position + currentPage * pageSize));
        dismiss();
    }

    public interface OnDialogListener {
        void onDialogClick(Programme programme);
    }

    public void setOnDialogListener(OnDialogListener dialogListener) {
        this.mlistener = dialogListener;
    }
}
