package com.zxtech.ecs.ui.home.company.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.VPAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.CompanySubTitleEntity;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.widget.ViewPagerSlide;
import com.zxtech.ecs.widget.tablayout.OnTabSelectListener;
import com.zxtech.ecs.widget.tablayout.SegmentTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @date: 2018/2/1
 * @desc: 产品介绍
 */

public class ProductIntroductionFragment extends BaseFragment {

    private final List<Fragment> mFragments = new ArrayList<>();

    @BindView(R.id.tab_layout)
    SegmentTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPagerSlide mViewpager;

    public static ProductIntroductionFragment newInstance() {
        return new ProductIntroductionFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mViewpager.setScanScroll(false);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewpager.setCurrentItem(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        requestNet();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void requestNet() {
        HttpFactory.getApiService()
                .getCompanySubTitle("1", "ComTag2")
                .compose(RxHelper.<BaseResponse<CompanySubTitleEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<CompanySubTitleEntity>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<CompanySubTitleEntity> response) {
                        List<CompanySubTitleEntity.ResultInfoDicBean> beans = response.getData().getResultInfoDic();
                        String[] titles = new String[beans.size()];
//						if (!TextUtils.isEmpty(bean.getComTag2A1())) {
//							titles[0] = bean.getComTag2A1();
//						}
//						if (!TextUtils.isEmpty(bean.getComTag2B1())) {
//							titles[1] = bean.getComTag2B1();
//						}
//						if (!TextUtils.isEmpty(bean.getComTag2C1())) {
//							titles[2] = bean.getComTag2C1();
//						}
//						if (!TextUtils.isEmpty(bean.getComTag2D1())) {
//							titles[3] = bean.getComTag2D1();
//						}
//						if (!TextUtils.isEmpty(bean.getComTag2E1())) {
//							titles[4] = bean.getComTag2E1();
//						}
//						if (!TextUtils.isEmpty(bean.getComTag2F1())) {
//							titles[5] = bean.getComTag2F1();
//						}

                        for (int i = 0; i < beans.size(); i++) {
                            if (language.equals(Constants.LANGUAGE_EN)) {
                                titles[i] = beans.get(i).getSeatName_En();
                            } else {
                                titles[i] = beans.get(i).getSeatName();
                            }
                            //根据不同的title动态创建fragment
                            mFragments.add(ProductElevatorFragment.getInstance(beans.get(i)));
                        }
//						for (CompanySubTitleEntity.ResultInfoDicBean subTtile : beans) {
//							//根据不同的title动态创建fragment
//							mFragments.add(ProductElevatorFragment.getInstance(title));
//						}
                        mViewpager.setAdapter(new VPAdapter(getChildFragmentManager(), mFragments, titles));
                        mTabLayout.setTabData(titles);
//						if (titles.length > 0) {
//							//将获取到的list集合转为数组
//							titles = list.toArray(titles);
//
//						}
//						List<CompanySubTitleEntity.CompanyBean> company = response.getData().getCompany();
//						ArrayList<String> list = new ArrayList<>();
//						for (int i = 0; i < company.size(); i++) {
//							if (!TextUtils.isEmpty(company.get(i).getComTag2A1())) {
//								list.add(company.get(i).getComTag2A1());
//							}
//							if (!TextUtils.isEmpty(company.get(i).getComTag2B1())) {
//								list.add(company.get(i).getComTag2B1());
//							}
//							if (!TextUtils.isEmpty(company.get(i).getComTag2C1())) {
//								list.add(company.get(i).getComTag2C1());
//							}
//							if (!TextUtils.isEmpty(company.get(i).getComTag2D1())) {
//								list.add(company.get(i).getComTag2D1());
//							}
//							if (!TextUtils.isEmpty(company.get(i).getComTag2E1())) {
//								list.add(company.get(i).getComTag2E1());
//							}
//							if (!TextUtils.isEmpty(company.get(i).getComTag2F1())) {
//								list.add(company.get(i).getComTag2F1());
//							}
//						}
//
//						String[] titles = new String[list.size()];
//						if (titles.length > 0) {
//							//将获取到的list集合转为数组
//							titles = list.toArray(titles);
//							for (String title : titles) {
//								//根据不同的title动态创建fragment
//								mFragments.add(ProductElevatorFragment.getInstance(title));
//							}
//							mViewpager.setAdapter(new VPAdapter(getChildFragmentManager(), mFragments, titles));
//							mTabLayout.setTabData(titles);
//						}
                    }
                });
    }
}
