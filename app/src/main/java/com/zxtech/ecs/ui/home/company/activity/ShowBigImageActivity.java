package com.zxtech.ecs.ui.home.company.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.widget.photo.HackyViewPager;
import com.zxtech.ecs.widget.photo.PhotoView;
import com.zxtech.ecs.widget.photo.PhotoViewAttacher;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 相册预览
 */
public class ShowBigImageActivity extends BaseActivity implements OnPageChangeListener {

    private static final String ISLOCKED_ARG = "isLocked";

    @BindView(R.id.viewPager)
    HackyViewPager mViewPager;
    @BindView(R.id.show_big_text)
    TextView mShowBigText;
    @BindView(R.id.viewGroup)
    LinearLayout mViewGroup;

    // 装点点的ImageView数组
    private ImageView[] tips;
    private static ArrayList<String> imgStringArray;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_big_image;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        imgStringArray = getIntent().getStringArrayListExtra("images");
        int location = getIntent().getIntExtra("currentItem", 0);
        if (getIntent().getBooleanExtra("flag", false)) {
            mViewGroup.setVisibility(View.GONE);
        } else {
            // 将点点加入到ViewGroup中
            tips = new ImageView[imgStringArray.size()];
            mShowBigText.setText(location + 1 + "/" + tips.length);
            for (int i = 0; i < tips.length; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LayoutParams(10, 10));
                tips[i] = imageView;
                if (i == 0) {
                    tips[i].setBackgroundResource(R.drawable.shape_red);
                } else {
                    tips[i].setBackgroundResource(R.drawable.shape_gray);
                }

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT));
                layoutParams.leftMargin = 8;
                layoutParams.rightMargin = 8;
                mViewGroup.addView(imageView, layoutParams);
            }

        }
        // 设置Adapter
        mViewPager.setAdapter(new SamplePagerAdapter(imgStringArray, ShowBigImageActivity.this));
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


    static class SamplePagerAdapter extends PagerAdapter {
        private ArrayList<String> mImageViews;
        private Context context;
        private LayoutInflater layoutInflater;

        public SamplePagerAdapter(ArrayList<String> mImageViews, Context context) {
            super();
            this.context = context;
            this.mImageViews = mImageViews;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public View instantiateItem(final ViewGroup container, int position) {

            View view = layoutInflater.inflate(R.layout.item_viewpager, null);
            final PhotoView photoView = view.findViewById(R.id.photo_view);
            final ProgressBar progressBar = view.findViewById(R.id.progress_bar);

            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            photoView.setMaxScale(10);
            progressBar.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(mImageViews.get(position))
                    .crossFade()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(photoView);

            container.addView(view);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    ((ShowBigImageActivity) context).finish();
                }
            });
            return view;
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
                tips[i].setBackgroundResource(R.drawable.shape_red);
            } else {
                tips[i].setBackgroundResource(R.drawable.shape_gray);
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
