package com.zxtech.is.ui.home;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zxtech.is.BaseFragment;
import com.zxtech.is.BuildConfig;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.Constants;
import com.zxtech.is.model.home.AppMenu;
import com.zxtech.is.ui.home.adpter.AppMenuAdapter;
import com.zxtech.is.util.SPUtils;
import com.zxtech.is.widget.MyItemDecoration;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by chw on 2018/1/12.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R2.id.banner)
    BGABanner banner;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    private String[] menu_title = new String[]{"公司介绍", "销售管理", "质量管理", "安装管理", "售后维保"};

    private String[][] menu_text = new String[][]{{"公司简介", "产品介绍", "宣传视频", "项目案例"}, {"项目申请", "项目审批", "方案推荐", "项目报价", "报价审批", "合同评审"}, {"我要咨询", "我要反馈", "我的反馈"},{"我的任务","土建复核","生产发运","安装交付","项目管理","人员验证","新建班组"},{"销售支持", "维保支持"}};

    private int[][] menu_image = new int[][]{{R.drawable.menu_company, R.drawable.menu_product, R.drawable.men_propaganda, R.drawable.menu_case},
            {R.drawable.menu_apply, R.drawable.menu_report, R.drawable.menu_scheme, R.drawable.menu_price, R.drawable.menu_report_price, R.drawable.menu_contract},
            {R.drawable.menu_consult, R.drawable.menu_feedback, R.drawable.menu_myfeedback},
            {R.drawable.menu_taskme,R.drawable.menu_civilreview,R.drawable.menu_transport,R.drawable.menu_install,R.drawable.menu_is_project_manage,R.drawable.menu_percon_check,R.drawable.menu_percon_check},
            {R.drawable.menu_sale, R.drawable.menu_maintenance}};

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

//        switchLanguage();
//        checkAppVersion();
        initAdv();
        initMenu();

    }

    private void switchLanguage() {
        String defaultLanguage = (String) SPUtils.get(mContext, Constants.SHARED_LANGUAGE, Locale.getDefault().toString());
        String updateLanguage;
        if (Locale.ENGLISH.toString().equals(defaultLanguage)) {
            updateLanguage = Locale.CHINESE.toString();
        } else {
            updateLanguage = Locale.ENGLISH.toString();
        }

        String sta = updateLanguage;
        // 本地语言设置
        Locale myLocale = new Locale(sta);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    private void checkAppVersion() {
//        baseResponseObservable = HttpFactory.getApiService().getVersion("android");
//        baseResponseObservable
//                .compose(RxHelper.<BaseResponse<AppVersion>>rxSchedulerHelper())
//                .subscribe(new DefaultObserver<BaseResponse<AppVersion>>(getActivity()) {
//                    @Override
//                    public void onSuccess(BaseResponse<AppVersion> response) {
//                        int appVersionCode = Util.getVersionCode(mContext);
//                        int serverVerionCode = response.getData().getVersion_code();
//                        if (serverVerionCode > appVersionCode) {
//                            UpdateDialogFragment updateDialog = UpdateDialogFragment.newInstance(response.getData());
//                            updateDialog.show(getActivity().getFragmentManager(), "");
//                        }
//                    }
//                });


    }

    private void initAdv() {

//        baseResponseObservable = HttpFactory.getApiService().getHomeAdvert();
//        baseResponseObservable
//                .compose(RxHelper.<BaseResponse<List<Map<String, String>>>>rxSchedulerHelper())
//                .subscribe(new DefaultObserver<BaseResponse<List<Map<String, String>>>>(getActivity()) {
//                    @Override
//                    public void onSuccess(BaseResponse<List<Map<String, String>>> response) {
//                        initBanner(response.getData());
//                    }
//                });
        initBanner(null);
    }

    private void initMenu() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        AppMenuAdapter mAdapter = new AppMenuAdapter(getContext(), R.layout.item_appmenu, initMenuData());
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setAdapter(mAdapter);
    }

    private List<AppMenu> initMenuData() {
        List<AppMenu> appMenus = new ArrayList<>();
        for (int i = 0; i < menu_title.length; i++) {
            AppMenu appMenu = new AppMenu();
            appMenu.setCategory(menu_title[i]);
            for (int j = 0; j < menu_text[i].length; j++) {
               // if (i == menu_title.length  && j == menu_text[i].length ) continue;
                AppMenu.Menu menu1 = new AppMenu.Menu(menu_image[i][j], menu_text[i][j]);
                appMenu.getSubMenus().add(menu1);
            }
            appMenus.add(appMenu);
        }

        return appMenus;
    }

    private void initBanner(List<Map<String, String>> maps) {
        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
//                itemView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getActivity())
                        .load(model)
                        .placeholder(R.drawable.place_holder)
                        .error(R.drawable.place_holder)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
//        List<String> imagesPaths = new ArrayList<>();
//        List<String> imagesTitls = new ArrayList<>();
//        for (Map<String, String> map : maps) {
//            imagesPaths.add(map.get("coverPath"));
//            imagesTitls.add(map.get("title"));
//        }
        banner.setData(Arrays.asList( BuildConfig.BASE_URL+"appimg/bannerimg/GK_03.png"), Arrays.asList( ""));
        banner.setData(Arrays.asList(BuildConfig.BASE_URL+"appimg/bannerimg/GK_01.png", BuildConfig.BASE_URL+"appimg/bannerimg/GK_03.png"), Arrays.asList("", ""));
//        banner.setData(Arrays.asList(BuildConfig.BASE_URL+"appimg/bannerimg/GK_01.png", BuildConfig.BASE_URL+"appimg/bannerimg/GK_02.png", BuildConfig.BASE_URL+"appimg/bannerimg/GK_03.png",BuildConfig.BASE_URL+"appimg/bannerimg/GK_04.png"), Arrays.asList("", "", "", ""));

    }

    public void start() {
    }
}
