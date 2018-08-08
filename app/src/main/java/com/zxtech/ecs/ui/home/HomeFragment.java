package com.zxtech.ecs.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.AppMenuAdapter;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.model.AppMenu;
import com.zxtech.ecs.model.AppVersion;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.bid.BidApplyActivity;
import com.zxtech.ecs.ui.home.company.activity.CompanyActivity;
import com.zxtech.ecs.ui.home.contract.ContractApplyActivity;
import com.zxtech.ecs.ui.home.contractchange.ContractChangeActivity;
import com.zxtech.ecs.ui.home.contractchange.ContractChangeApprovalActivity;
import com.zxtech.ecs.ui.home.designapply.DesignApplyActivity;
import com.zxtech.ecs.ui.home.engineering.EngineeringActivity;
import com.zxtech.ecs.ui.home.escscheme.EscCollectionActivity;
import com.zxtech.ecs.ui.home.follow.FollowActivity;
import com.zxtech.ecs.ui.home.map.ProjectMapActivity;
import com.zxtech.ecs.ui.home.payment.PaymentActivity;
import com.zxtech.ecs.ui.home.qmsmanager.QmsMyFeedBackActivity;
import com.zxtech.ecs.ui.home.qmsmanager.QmsMyWantConsultationActivity;
import com.zxtech.ecs.ui.home.qmsmanager.QmsMyWantFeedBackActivity;
import com.zxtech.ecs.ui.home.quote.ProjectQuoteActivity;
import com.zxtech.ecs.ui.home.scheduling.SchedulingActivity;
import com.zxtech.ecs.ui.home.scheme.collection.CollectionActivity;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.gks.ui.cr.ContractReviewActivity;
import com.zxtech.gks.ui.pa.ProjectPriceApprovalListActivity;
import com.zxtech.gks.ui.record.RecordApprovalActivity;
import com.zxtech.gks.ui.record.sale.PRListActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 首页
 * Created by chw on 2018/1/12.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.banner)
    BGABanner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bottom_banner)
    BGABanner bottomBanner;
    private String[] menu_title = null;


    private AppMenuAdapter mAdapter;

    private Set<String> permissionSet = new HashSet<>();

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
        menu_title = new String[]{getString(R.string.about_company), getString(R.string.sales_mgmt), getString(R.string.install_manager), getString(R.string.quality_management), getString(R.string.after_sale_maintenance), getString(R.string.learning_platform)};

        checkAppVersion();
        initAdv();
        initMenu();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshPermission();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventRefreshPermissionSticky(EventAction event) {
        if (event.getCode() == EventAction.LOGIN_REFRESH_MENU) {
            refreshPermission();
        }
    }

    private void refreshPermission() {
        String permission = (String) SPUtils.get(mContext, "permission", "");
        permissionSet.clear();
        if (!TextUtils.isEmpty(permission)) { //默认权限
            String[] split = permission.split(",");
            for (int i = 0; i < split.length; i++) {
                permissionSet.add(split[i]);
            }
        }

        bottomBannerPermission();
        mAdapter.getDatas().clear();
        mAdapter.getDatas().addAll(initMenuData());
        mAdapter.notifyDataSetChanged();
    }

    private void bottomBannerPermission() {
        if (permissionSet.contains("UndersideAdvertisement")) { //底部菜单权限
            bottomBanner.setVisibility(View.VISIBLE);
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 0.47f));
            initBottomAdv();
        } else {
            bottomBanner.setVisibility(View.GONE);
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 0.62f));
        }
    }

    private void checkAppVersion() {
        baseResponseObservable = HttpFactory.getApiService().getVersion("android");
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<AppVersion>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<AppVersion>>(getActivity()) {
                    @Override
                    public void onSuccess(BaseResponse<AppVersion> response) {
                        int appVersionCode = Util.getVersionCode(mContext);
                        int serverVerionCode = response.getData().getVersion_code();
                        if (serverVerionCode > appVersionCode) {
                            UpdateDialogFragment updateDialog = UpdateDialogFragment.newInstance(response.getData());
                            updateDialog.show(getActivity().getFragmentManager(), "");
                        }
                    }
                });


    }

    private void initAdv() {

        baseResponseObservable = HttpFactory.getApiService().getHomeAdvert("ComTag5A1");
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<Map<String, String>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Map<String, String>>>>(getActivity()) {
                    @Override
                    public void onSuccess(BaseResponse<List<Map<String, String>>> response) {
                        initBanner(response.getData());
                    }
                });


    }

    private void initBottomAdv() {
        baseResponseObservable = HttpFactory.getApiService().getHomeAdvert("ComTag5B1");
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<Map<String, String>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Map<String, String>>>>(getActivity()) {
                    @Override
                    public void onSuccess(BaseResponse<List<Map<String, String>>> response) {
                        initBottomBanner(response.getData());
                    }
                });
    }

    private void initMenu() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new AppMenuAdapter(getContext(), R.layout.item_appmenu, initMenuData());
        recyclerView.addItemDecoration(new MyItemDecoration());

        recyclerView.setAdapter(mAdapter);

    }

    private List<AppMenu> initMenuData() {

        List<AppMenu> appMenus = new ArrayList<>();
        List<AppMenu.Menu> allSubMenu = getAllSubMenu();
        for (int i = 0; i < menu_title.length; i++) {
            AppMenu appMenu = new AppMenu();
            String menuCategory = menu_title[i];
            appMenu.setCategory(menuCategory);
            boolean hasSubMenu = false;
            for (int j = 0; j < allSubMenu.size(); j++) {
                AppMenu.Menu menu = allSubMenu.get(j);
                if (menuCategory.equals(menu.getAppMenuCategory()) && (permissionSet.contains(menu.getMenuId()) || menu.isDefaultMenu())) {  //有权限或者默认菜单
                    hasSubMenu = true;
                    appMenu.getSubMenus().add(menu);
                }
            }
            if (hasSubMenu) {
                appMenus.add(appMenu);
            }
        }


        return appMenus;
    }

    private List<AppMenu.Menu> getAllSubMenu() {
        List<AppMenu.Menu> subMens = new ArrayList<>();
        //需按照界面顺序添加
        subMens.add(new AppMenu.Menu("ECS-CompanyProfile", R.drawable.menu_company, getString(R.string.company_profile), false, true, null, CompanyActivity.class, getString(R.string.about_company)));
        subMens.add(new AppMenu.Menu("ProductIntroduction", R.drawable.menu_product, getString(R.string.product_introduction), false, true, null, CompanyActivity.class, getString(R.string.about_company)));
        subMens.add(new AppMenu.Menu("ECS-Videos", R.drawable.men_propaganda, getString(R.string.videos), false, true, null, CompanyActivity.class, getString(R.string.about_company)));
        subMens.add(new AppMenu.Menu("ECS-ReferenceProject", R.drawable.menu_case, getString(R.string.reference_project), false, true, null, CompanyActivity.class, getString(R.string.about_company)));

        subMens.add(new AppMenu.Menu("ECS-ProjectApplication", R.drawable.menu_apply, getString(R.string.project_application), true, false, null, PRListActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-ProjectApproval", R.drawable.menu_report, getString(R.string.project_approval), true, false, null, RecordApprovalActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-ElevatorSolution", R.drawable.menu_ele_scheme, getString(R.string.elevator_scheme), true, true, null, CollectionActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-EscalatorSolution", R.drawable.menu_esc_scheme, getString(R.string.escalator_scheme), true, true, null, EscCollectionActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-QuotatoinApplicatoin", R.drawable.menu_price, getString(R.string.quotatoin_applicatoin), true, false, null, ProjectQuoteActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-Drawings", R.drawable.menu_engineering_apply, getString(R.string.apply_layout), true, false, null, EngineeringActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-DecorationConfiguration", R.drawable.menu_ecoration_config, getString(R.string.decoration), true, false, null, DesignApplyActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-BidingApply", R.drawable.menu_bid_apply, "投标申请", true, false, null, BidApplyActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-QuotatoinApproval", R.drawable.menu_report_price, getString(R.string.quotatoin_approval), true, false, null, ProjectPriceApprovalListActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-ContractApply", R.drawable.menu_contract_apply, getString(R.string.contract_apply), true, false, null, ContractApplyActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-Scheduling", R.drawable.menu_contract_scheduling, "合同排产", true, false, null, SchedulingActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-ContractReview", R.drawable.menu_contract, getString(R.string.contract_review), true, false, null, ContractReviewActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-ContractChange", R.drawable.menu_contract_change, getString(R.string.contract_change), true, false, null, ContractChangeActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-ChangeReview", R.drawable.menu_contract_change_approval, getString(R.string.change_approval), true, false, null, ContractChangeApprovalActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-AccountPayment", R.drawable.menu_account_payment, getString(R.string.account_payment), true, false, null, PaymentActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-ProjectFollow", R.drawable.menu_project_follow, getString(R.string.project_tracking), true, false, null, FollowActivity.class, getString(R.string.sales_mgmt)));
        subMens.add(new AppMenu.Menu("ECS-ProjectMap", R.drawable.menu_project_process, getString(R.string.project_map), true, false, null, ProjectMapActivity.class, getString(R.string.sales_mgmt)));


        subMens.add(new AppMenu.Menu("QMS-Consultations", R.drawable.menu_consult, getString(R.string.consulting), false, false, null, QmsMyWantConsultationActivity.class, getString(R.string.quality_management)));
        subMens.add(new AppMenu.Menu("QMS-Feedbacks", R.drawable.menu_feedback, getString(R.string.report), false, false, null, QmsMyWantFeedBackActivity.class, getString(R.string.quality_management)));
        subMens.add(new AppMenu.Menu("QMS-Myconsultations", R.drawable.menu_myfeedback, getString(R.string.my_report), false, false, null, QmsMyFeedBackActivity.class, getString(R.string.quality_management)));

        //subMens.add(new AppMenu.Menu("SalesSupport", R.drawable.menu_sale, getString(R.string.sales_support), false, null, getString(R.string.after_sale_maintenance)));
        //subMens.add(new AppMenu.Menu("Maintenancesupport", R.drawable.menu_maintenance, getString(R.string.maintenance_support), false, null, getString(R.string.after_sale_maintenance)));
        subMens.add(new AppMenu.Menu("ESS-Maintenance", R.drawable.menu_mt, getString(R.string.eai_maintenance), false, false, "/mt/mt", null, getString(R.string.after_sale_maintenance)));
        subMens.add(new AppMenu.Menu("ESS-CallBack", R.drawable.menu_call, getString(R.string.eai_call), false, false, "/mt/call", null, getString(R.string.after_sale_maintenance)));
        subMens.add(new AppMenu.Menu("ESS-PunchTheClock", R.drawable.menu_clock, getString(R.string.attendance_card), false, false, "/mt/workcheck", null, getString(R.string.after_sale_maintenance)));
        subMens.add(new AppMenu.Menu("ESS-ProjectOrientation", R.drawable.menu_location, getString(R.string.project_location), false, false, "/mt/location", null, getString(R.string.after_sale_maintenance)));
        subMens.add(new AppMenu.Menu("ESS-ElevatorInformation", R.drawable.menu_elevator, getString(R.string.elevator_info), false, false, "/mt/elevatorinfo", null, getString(R.string.after_sale_maintenance)));
        subMens.add(new AppMenu.Menu("ESS-SpareMall", R.drawable.menu_mall, getString(R.string.eai_mall), false, false, "/mt/accessoryshop", null, getString(R.string.after_sale_maintenance)));
        subMens.add(new AppMenu.Menu("ESS-SpareInquiry", R.drawable.menu_ask_price, getString(R.string.eai_inquiry), false, false, "/mt/accessoryinquiry", null, getString(R.string.after_sale_maintenance)));
        subMens.add(new AppMenu.Menu("ESS-SpareTraceability", R.drawable.menu_suyuan, getString(R.string.eai_origin), false, false, "/mt/accessoryscan", null, getString(R.string.after_sale_maintenance)));

        subMens.add(new AppMenu.Menu("STU-PersonalCenter", R.drawable.menu_central, getString(R.string.personal_center), false, false, "/esp/box", null, getString(R.string.learning_platform)));
        subMens.add(new AppMenu.Menu("STU-CurriculumMarket", R.drawable.menu_market, getString(R.string.course_market), false, false, "/esp/box", null, getString(R.string.learning_platform)));
        subMens.add(new AppMenu.Menu("STU-Forum", R.drawable.menu_bbs, getString(R.string.forum), false, false, "/esp/box", null, getString(R.string.learning_platform)));

        subMens.add(new AppMenu.Menu("IN-MyTask", R.drawable.menu_taskme, getString(R.string.install_task_me), false, false, "/is/task", null, getString(R.string.install_manager)));
        subMens.add(new AppMenu.Menu("IN-CivilExploration", R.drawable.menu_civilreview, getString(R.string.install_s1), false, false, "/is/engineer", null, getString(R.string.install_manager)));
        subMens.add(new AppMenu.Menu("IN-ProductionTransportation", R.drawable.menu_transport, getString(R.string.install_s2_s3), false, false, "/is/transport", null, getString(R.string.install_manager)));
        subMens.add(new AppMenu.Menu("IN-InstallDelivery", R.drawable.menu_install, getString(R.string.install_handle), false, false, "/is/install", null, getString(R.string.install_manager)));
        subMens.add(new AppMenu.Menu("IN-ProjectManagement", R.drawable.menu_is_project_manage, getString(R.string.install_project), false, false, "/is/projectmanage", null, getString(R.string.install_manager)));
        subMens.add(new AppMenu.Menu("PersonnelVerification", R.drawable.menu_percon_check, getString(R.string.install_person), false, false, "/is/personcheck", null, getString(R.string.install_manager)));

        return subMens;
    }

    private void initBanner(List<Map<String, String>> maps) {
        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(getActivity())
                        .load(model)
                        .placeholder(R.drawable.place_holder)
                        .error(R.drawable.place_holder)
                        .centerCrop()
                        .into(itemView);
            }
        });
        List<String> imagesPaths = new ArrayList<>();
        List<String> imagesTitls = new ArrayList<>();
        for (Map<String, String> map : maps) {
            imagesPaths.add(map.get("coverPath"));
        }
        banner.setData(imagesPaths, imagesTitls);

    }

    private void initBottomBanner(List<Map<String, String>> maps) {
        bottomBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(getActivity())
                        .load(model)
                        .into(itemView);
            }
        });
        List<String> imagesPaths = new ArrayList<>();
        List<String> imagesTitls = new ArrayList<>();
        for (Map<String, String> map : maps) {
            imagesPaths.add(map.get("coverPath"));
        }
        bottomBanner.setData(imagesPaths, imagesTitls);
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
