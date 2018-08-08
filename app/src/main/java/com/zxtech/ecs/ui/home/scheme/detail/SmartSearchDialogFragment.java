package com.zxtech.ecs.ui.home.scheme.detail;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.decorationvr.PartType;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.SmartSearchAdapter;
import com.zxtech.ecs.adapter.SmartSearchContentAdapter;
import com.zxtech.ecs.adapter.SmartSearchMaterialAdapter;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.BitmapUtil;
import com.zxtech.ecs.util.FileUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.SelectDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static com.zxtech.ecs.common.Constants.REQ_ALBUM;
import static com.zxtech.ecs.common.Constants.REQ_TAKE_PHOTO;
import static com.zxtech.ecs.common.Constants.REQ_ZOOM;

/**
 * 装潢参数-美梯搜索
 * Created by syp523 on 2018/2/27.
 */

public class SmartSearchDialogFragment extends BaseDialogFragment implements  BGARefreshLayout.BGARefreshLayoutDelegate {

    public OnDialogListener mlistener;

    @BindView(R.id.color_rv)
    RecyclerView color_rv;
    @BindView(R.id.style_rv)
    RecyclerView style_rv;
    @BindView(R.id.material_type_rv)
    RecyclerView material_type_rv;
    @BindView(R.id.content_rv)
    RecyclerView content_rv;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;

    private SmartSearchAdapter colorAdapter;
    private SmartSearchAdapter styleAdapter;
    private SmartSearchMaterialAdapter materialTypeAdapter;
    private SmartSearchContentAdapter smartSearchContentAdapter;
    private List<Map<String, String>> colorConditionDatas = new ArrayList<>();
    private List<Map<String, String>> styleConditionDatas = new ArrayList<>();
    private List<Map<String, String>> materialTypeDatas = new ArrayList<>();

    @BindView(R.id.find_tv)
    TextView find_tv;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.close_iv)
    ImageButton close_iv;
    @BindView(R.id.custom_layout)
    LinearLayout custom_layout;
    @BindView(R.id.build_layout)
    LinearLayout build_layout;
    @BindView(R.id.person_layout)
    LinearLayout person_layout;
    @BindView(R.id.style_layout)
    LinearLayout style_layout;
    @BindView(R.id.color_layout)
    LinearLayout color_layout;
    @BindView(R.id.material_type_layout)
    LinearLayout material_type_layout;
    @BindView(R.id.take_photo_layout)
    LinearLayout take_photo_layout;
    @BindView(R.id.custom_iv)
    ImageView custom_iv;
    @BindView(R.id.build_iv)
    ImageView build_iv;
    @BindView(R.id.person_iv)
    ImageView person_iv;

    private Uri outputUri;
    private String imgPath;
    private String scaleImgPath;


    public static final int PERSON_AND_BUILD = 0;
    public static final int CUSTOMS = 1;
    public static final int GONE = 2;
    public static final int MATERIAL_CUSTOMS = 3;


    public int photoMode = PERSON_AND_BUILD;

    private List<Map<String, String>> goods = new ArrayList<>();
    private int clickPosition = 0;

    private String personImgPath = "";
    private String buildImgPath = "";
    private String customImgPath = "";
    public String title = "";
    private int startPage = 1;
    public int total = 0;
    private String uuid = Util.getUUID();
    private String elevatorSizeId;


    public static SmartSearchDialogFragment newInstance(int partTypeId) {
        SmartSearchDialogFragment fragment = new SmartSearchDialogFragment();
        Bundle args = new Bundle();
        args.putInt("partTypeId", partTypeId);
        fragment.setArguments(args);
        return fragment;
    }


    private void photoMode() {
        int partId = getArguments().getInt("partTypeId");
        if (PartType.PartTypeCarConfig == partId) {
            photoMode = PERSON_AND_BUILD;
        } else if (PartType.PartTypeTop == partId || PartType.PartTypeBottom == partId) {
            photoMode = CUSTOMS;
        } else if (PartType.PartTypeMaterial == partId) {
            photoMode = MATERIAL_CUSTOMS;
        } else {
            photoMode = GONE;
        }


    }

    private void visibilityPhotoMode() {
        switch (photoMode) {
            case PERSON_AND_BUILD:
                custom_layout.setVisibility(View.GONE);
                color_layout.setVisibility(View.GONE);
                material_type_layout.setVisibility(View.GONE);
                break;
            case CUSTOMS:
                build_layout.setVisibility(View.GONE);
                person_layout.setVisibility(View.GONE);
                style_layout.setVisibility(View.GONE);
                material_type_layout.setVisibility(View.GONE);
                break;
            case GONE:
                custom_layout.setVisibility(View.GONE);
                build_layout.setVisibility(View.GONE);
                person_layout.setVisibility(View.GONE);
                style_layout.setVisibility(View.GONE);
                material_type_layout.setVisibility(View.GONE);
                take_photo_layout.setVisibility(View.GONE);
                break;
            case MATERIAL_CUSTOMS:
                build_layout.setVisibility(View.GONE);
                person_layout.setVisibility(View.GONE);
                style_layout.setVisibility(View.GONE);
                break;
        }
    }



    @Override
    public int getLayoutId() {
        return R.layout.dialog_smart_search;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        find_tv.setOnClickListener(this);
        close_iv.setOnClickListener(this);
        custom_layout.setOnClickListener(this);
        build_layout.setOnClickListener(this);
        person_layout.setOnClickListener(this);
        photoMode();
        visibilityPhotoMode();

        title_tv.setText(title);
        LinearLayoutManager colorLayoutManager = new LinearLayoutManager(getActivity());
        colorLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        color_rv.setLayoutManager(colorLayoutManager);
        colorAdapter = new SmartSearchAdapter(getActivity(), R.layout.item_smart_search, colorConditionDatas);
        color_rv.setAdapter(colorAdapter);

        LinearLayoutManager styleLayoutManager = new LinearLayoutManager(getActivity());
        styleLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        style_rv.setLayoutManager(styleLayoutManager);
        styleAdapter = new SmartSearchAdapter(getActivity(), R.layout.item_smart_search_style, styleConditionDatas);
        style_rv.setAdapter(styleAdapter);

        LinearLayoutManager materialLayoutManager = new LinearLayoutManager(getActivity());
        materialLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        material_type_rv.setLayoutManager(materialLayoutManager);
        materialTypeAdapter = new SmartSearchMaterialAdapter(getActivity(), R.layout.item_smart_search_material, materialTypeDatas);
        material_type_rv.setAdapter(materialTypeAdapter);


        GridLayoutManager contentLayoutManager = new GridLayoutManager(getActivity(), 2);
        contentLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        content_rv.setLayoutManager(contentLayoutManager);


        refresh_layout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), true);
        // 设置下拉刷新和上拉加载更多的风格
        refresh_layout.setRefreshViewHolder(refreshViewHolder);
        refresh_layout.setIsShowLoadingMoreView(false);
        smartSearchContentAdapter = new SmartSearchContentAdapter(getActivity(), R.layout.item_smart_content, goods);
        content_rv.setAdapter(smartSearchContentAdapter);


        colorAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (colorAdapter.getSelectedPosition() == position) {
                    colorAdapter.setSelectedPosition(-1);
                } else {
                    colorAdapter.setSelectedPosition(position);
                }
                colorAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        styleAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (styleAdapter.getSelectedPosition() == position) {
                    styleAdapter.setSelectedPosition(-1);
                } else {
                    styleAdapter.setSelectedPosition(position);
                }
                styleAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        materialTypeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (materialTypeAdapter.getSelectedPosition() == position) {
                    materialTypeAdapter.setSelectedPosition(-1);
                } else {
                    materialTypeAdapter.setSelectedPosition(position);
                }
                materialTypeAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        smartSearchContentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (mlistener != null && !goods.get(position).get("commodityName").startsWith("x")) {
                    dismiss();
                    int partTypeId = getArguments().getInt("partTypeId");
                    mlistener.onDialogClick(partTypeId, Integer.parseInt(goods.get(position).get("number")));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public boolean isBottomShow() {
        return true;
    }


    private void requestGoods(final boolean isMore, int page) {

        Map<String, RequestBody> bodyMap = new HashMap<>();
        Map<String, String> paramsMap = new HashMap<>();
        int partId = getArguments().getInt("partTypeId");
        if (!TextUtils.isEmpty(personImgPath)) {
            bodyMap.put("person\";filename=\"person.png", RequestBody.create(MediaType.parse("image/png"), new File(personImgPath)));
            paramsMap.put("person", "person");
        }
        if (!TextUtils.isEmpty(buildImgPath)) {
            bodyMap.put("architect\";filename=\"architect.png", RequestBody.create(MediaType.parse("image/png"), new File(buildImgPath)));
            paramsMap.put("architect", "architect");
        }
        if (!TextUtils.isEmpty(customImgPath)) {
            bodyMap.put(partId + "\";filename=\"" + partId + ".png", RequestBody.create(MediaType.parse("image/png"), new File(customImgPath)));
            paramsMap.put("custom", "custom");
        }
        String tempPath = FileUtil.generateImgePathInStoragePath();
        File fileFile = new File(tempPath);
        try {
            fileFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bodyMap.put("temp\";filename=\"temp.png", RequestBody.create(MediaType.parse("image/png"), fileFile));
        if (isMore) {
            paramsMap.put("startPage", String.valueOf(page));
        }
        paramsMap.put("step", String.valueOf(APPConfig.PAGE_SIZE));
        paramsMap.put("partName", String.valueOf(partId));
        paramsMap.put("color", colorAdapter.getSelectedPosition() == -1 ? "" : colorConditionDatas.get(colorAdapter.getSelectedPosition()).get("id"));
        paramsMap.put("style", styleAdapter.getSelectedPosition() == -1 ? "" : styleConditionDatas.get(styleAdapter.getSelectedPosition()).get("id"));
        paramsMap.put("materialId", materialTypeAdapter.getSelectedPosition() == -1 ? "" : materialTypeDatas.get(materialTypeAdapter.getSelectedPosition()).get("material_id"));
        paramsMap.put("identify", uuid);
        if (isMore) {
            paramsMap.put("cacheFlag", "1");
        }
        paramsMap.put("spec",elevatorSizeId);

        String param = new Gson().toJson(paramsMap);
        baseResponseObservable = HttpFactory.getApiService().photoGoods(param, bodyMap);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<Map<String, String>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Map<String, String>>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Map<String, String>>> response) {
                        if (isMore) {
                            startPage++;
                            refresh_layout.endLoadingMore();
                        } else {
                            startPage = 1;
                            goods.clear();
                        }
                        goods.addAll(response.getData());
                        smartSearchContentAdapter.notifyDataSetChanged();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_tv:
                //查找
                requestGoods(false, 0);
                break;
            case R.id.close_iv:
                this.dismiss();
                break;
            case R.id.custom_layout:
                clickPosition = 3;
                photograph();
                break;
            case R.id.build_layout:
                clickPosition = 2;
                photograph();
                break;
            case R.id.person_layout:

                clickPosition = 1;
                photograph();
                break;
        }
    }
//
//    protected SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
//        SelectDialog dialog = new SelectDialog(getActivity(), R.style
//                .transparentFrameWindowStyle,
//                listener, names);
//        if (!getActivity().isFinishing()) {
//            dialog.show();
//        }
//        return dialog;
//    }


    private void photograph() {
        imgPath = FileUtil.generateImgePathInStoragePath();
        selectMode();
    }


    @Override
    public void handleImage(String filePath) {
        super.handleImage(filePath);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        if (clickPosition == 1) {
            personImgPath = filePath;
            person_iv.setImageBitmap(bitmap);
        } else if (clickPosition == 2) {
            buildImgPath = filePath;
            build_iv.setImageBitmap(bitmap);
        } else if (clickPosition == 3) {
            customImgPath = filePath;
            custom_iv.setImageBitmap(bitmap);
        }
    }



    public List<Map<String, String>> getMaterialTypeDatas() {
        return materialTypeDatas;
    }

    public void setMaterialTypeDatas(List<Map<String, String>> materialTypeDatas) {
        this.materialTypeDatas = materialTypeDatas;
    }

    public List<Map<String, String>> getGoods() {
        return goods;
    }

    public List<Map<String, String>> getColorConditionDatas() {
        return colorConditionDatas;
    }

    public List<Map<String, String>> getStyleConditionDatas() {
        return styleConditionDatas;
    }

    public void setElevatorSizeId(String elevatorSizeId) {
        this.elevatorSizeId = elevatorSizeId;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refreshLayout.endRefreshing();
        return;
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        requestGoods(true, startPage);
        return true;
    }

    public interface OnDialogListener {
        void onDialogClick(final int partTypeId, int goodsId);
    }

    public void setOnDialogListener(OnDialogListener dialogListener) {
        this.mlistener = dialogListener;
    }
}
