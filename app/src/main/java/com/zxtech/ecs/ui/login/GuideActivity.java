package com.zxtech.ecs.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.MainActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.widget.photo.HackyViewPager;
import com.zxtech.ecs.widget.photo.PhotoView;
import com.zxtech.gks.common.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 指引界面
 * Created by syp523 on 2018/4/19.
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private static final String ISLOCKED_ARG = "isLocked";
    @BindView(R.id.viewPager)
    HackyViewPager mViewPager;
    @BindView(R.id.show_big_text)
    TextView mShowBigText;
    @BindView(R.id.viewGroup)
    LinearLayout mViewGroup;
    @BindView(R.id.next_btn)
    TextView next_btn;

    // 装点点的ImageView数组
    private ImageView[] tips;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        int[] a = new int[]{R.drawable.guide1, R.drawable.guide2, R.drawable.guide3, R.drawable.guide4};
        int location = getIntent().getIntExtra("currentItem", 0);
        if (getIntent().getBooleanExtra("flag", false)) {
            mViewGroup.setVisibility(View.GONE);
        } else {
            // 将点点加入到ViewGroup中
            tips = new ImageView[a.length];
            mShowBigText.setText(location + 1 + "/" + tips.length);
            for (int i = 0; i < tips.length; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
                tips[i] = imageView;
                if (i == 0) {
                    tips[i].setBackgroundResource(R.drawable.bga_banner_point_enabled);
                } else {
                    tips[i].setBackgroundResource(R.drawable.bga_banner_point_disabled);
                }

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                layoutParams.leftMargin = 8;
                layoutParams.rightMargin = 8;
                mViewGroup.addView(imageView, layoutParams);
            }

        }
        // 设置Adapter
        mViewPager.setAdapter(new GuideActivity.SamplePagerAdapter(a, GuideActivity.this));
        // 设置监听，主要是设置点点的背景
        mViewPager.addOnPageChangeListener(this);
        // 设置ViewPager的默认项
        mViewPager.setCurrentItem(location);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (outState != null) {
            boolean isLocked = outState.getBoolean(ISLOCKED_ARG, false);
            mViewPager.setLocked(isLocked);
        }
    }

    @OnClick(R.id.next_btn)
    public void onClick(View view) {
        SPUtils.put(mContext,"is_first",false);
        startActivity(MainActivity.class);
        finish();
    }


    static class SamplePagerAdapter extends PagerAdapter {
        private int[] mImageViews = null;
        private Context context;

        public SamplePagerAdapter(int[] mImageViews, Context context) {
            super();
            this.context = context;
            this.mImageViews = mImageViews;
        }

        @Override
        public int getCount() {
            return mImageViews.length;
        }

        @Override
        public View instantiateItem(final ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(context);

            photoView.setScaleType(ImageView.ScaleType.FIT_XY);
            photoView.setMaxScale(10);

            Glide.with(context)
                    .load(mImageViews[position])
                    //.diskCacheStrategy(DiskCacheStrategy.SOURCE)//启动缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(2516, 1718)
                    .into(photoView);

            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//                @Override
//                public void onPhotoTap(View view, float x, float y) {
//                    ((ShowBigImageActivity) context).finish();
//                }
//            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        if (arg0 == 3) {
            next_btn.setVisibility(View.VISIBLE);
        } else {
            next_btn.setVisibility(View.GONE);
        }
        setImageBackground(arg0);
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.bga_banner_point_enabled);
            } else {
                tips[i].setBackgroundResource(R.drawable.bga_banner_point_disabled);
            }
            mShowBigText.setText(selectItems + 1 + "/" + tips.length);
        }
    }

    private boolean isViewPagerActive() {
        return mViewPager != null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(ISLOCKED_ARG, mViewPager.isLocked());
        }
        super.onSaveInstanceState(outState);
    }

}
