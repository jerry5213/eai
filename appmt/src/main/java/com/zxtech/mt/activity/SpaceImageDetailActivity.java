package com.zxtech.mt.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView.ScaleType;


import com.zxtech.mt.widget.SmoothImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.zxtech.mtos.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片放大
 * Created by Chw on 2016/7/19.
 */
public class SpaceImageDetailActivity extends Activity {

	private PhotoView large_photoview = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enlarge_image);
		large_photoview = (PhotoView) findViewById(R.id.large_photoview);
		String url =  getIntent().getStringExtra("url");
//		mLocationX = getIntent().getIntExtra("locationX", 0);
//		mLocationY = getIntent().getIntExtra("locationY", 0);
//		mWidth = getIntent().getIntExtra("width", 0);
//		mHeight = getIntent().getIntExtra("height", 0);
//
//		imageView = new SmoothImageView(this);
//		imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
//		imageView.transformIn();
//		imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
//		imageView.setScaleType(ScaleType.FIT_CENTER);
//		setContentView(imageView);
		ImageLoader.getInstance().displayImage(url, large_photoview,getSimpleOptions());
//		imageView.setImageResource(R.drawable.temp);
		 ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f,
		 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		 0.5f);
		 scaleAnimation.setDuration(300);
		 scaleAnimation.setInterpolator(new AccelerateInterpolator());
		large_photoview.startAnimation(scaleAnimation);
		large_photoview.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
			@Override
			public void onViewTap(View view, float x, float y) {

				ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.5f, 1.0f,
						0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
						0.5f);
				scaleAnimation.setDuration(300);
				scaleAnimation.setInterpolator(new AccelerateInterpolator());
				finish();
			}
		});

	}

	@Override
	public void onBackPressed() {
//		finish();
//		overridePendingTransition(R.anim.fade, R.anim.hold);
	}


    private DisplayImageOptions getSimpleOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_acc)//设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.drawable.default_acc)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_acc)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(false)//设置下载的图片是否缓存在内存中
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .build();//构建完成
        return options;
    }

	@Override
	protected void onPause() {
		super.onPause();
//		if (isFinishing()) {
//			overridePendingTransition(R.anim.fade, R.anim.hold);
//		}
	}

}
