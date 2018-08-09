package com.zxtech.mt.activity;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.common.MultipartRequestUpload;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.entity.HttpResult;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mtos.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chw on 2016/11/23.
 */
public class SignatrueActivity extends BaseActivity implements View.OnClickListener{

	Context context;
	LayoutParams p ;

	public static final int BACKGROUND_COLOR = Color.WHITE;
	public static final int BRUSH_COLOR = Color.BLACK;
	
	public PaintView mView;
	
	@Override
	protected void onCreate() {
		View view = mInfalter.inflate(R.layout.person_check_sign, null);
		main_layout.addView(view);
		title_textview.setText(getString(R.string.sign));
		setBottomLayoutHide();
		set_textview.setText(getString(R.string.save));
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		p = getWindow().getAttributes();
		p.width = width;
		p.height = height;

		mView = new PaintView(mContext);
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.pCSView);
		frameLayout.addView(mView);
		mView.requestFocus();

	}

	@Override
	protected void findView() {
		Button btnClear = (Button) findViewById(R.id.pCSDelete);

		btnClear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				mView.clear();
			}
		});
	}

	@Override
	protected void setListener() {
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initView() {

	}


	public  String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "mt");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        return file.getPath();
    }
	
	@Override
	public void onClick(View view) {
		super.onClick(view);
		int i = view.getId();
		if (i == R.id.set_textview) {
			UIController.SIGN_FILE_PATH = saveImageToGallery(mContext, mView.getCachebBitmap());
			Map<String, String> params = new HashMap<>();
			params.put("id", SPUtils.get(mContext, "emp_id", "").toString());
			MultipartRequestUpload upload = new MultipartRequestUpload((String) SPUtils.get(getApplicationContext(), "IP", "") + "/mtmo/updateempinfo.mo?_token=" + SPUtils.get(MyApplication.getContext(), "token", "").toString(), new Response.Listener<String>() {
				@Override
				public void onResponse(String s) {
					HttpResult result = gson.fromJson(s, new TypeToken<HttpResult>() {
					}.getType());
					if (Constants.SUCCESS.equals(result.getStatus())) {
						ToastUtil.showLong(mContext, "上传成功");
						SPUtils.put(mContext, "emp_sign_url", result.getData());
						finish();
					}
				}
			}, "file", new File(UIController.SIGN_FILE_PATH), params);
			upload.setRetryPolicy(new DefaultRetryPolicy(500 * 1000, 1, 1.0f));
			mQueue.add(upload);
			this.finish();

		}
	}


	/**
	 * This view implements the drawing canvas.
	 * 
	 * It handles all of the input events and drawing functions.
	 */
	class PaintView extends View {
		private Paint paint;
		private Canvas cacheCanvas;
		private Bitmap cachebBitmap;
		private Path path;

		public Bitmap getCachebBitmap() {
			return cachebBitmap;
		}

		public PaintView(Context context) {
			super(context);					
			init();			
		}

		private void init(){
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStrokeWidth(3);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.BLACK);					
			path = new Path();
			cachebBitmap = Bitmap.createBitmap(p.width, (int)(p.height*0.8), Config.ARGB_8888);			
			cacheCanvas = new Canvas(cachebBitmap);
			cacheCanvas.drawColor(Color.WHITE);
		}
		public void clear() {
			if (cacheCanvas != null) {
				
				paint.setColor(BACKGROUND_COLOR);
				cacheCanvas.drawPaint(paint);
				paint.setColor(Color.BLACK);
				cacheCanvas.drawColor(Color.WHITE);
				invalidate();			
			}
		}

		
		
		@Override
		protected void onDraw(Canvas canvas) {
			// canvas.drawColor(BRUSH_COLOR);
			canvas.drawBitmap(cachebBitmap, 0, 0, null);
			canvas.drawPath(path, paint);
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			
			int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
			int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
			if (curW >= w && curH >= h) {
				return;
			}

			if (curW < w)
				curW = w;
			if (curH < h)
				curH = h;

			Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Config.ARGB_8888);
			Canvas newCanvas = new Canvas();
			newCanvas.setBitmap(newBitmap);
			if (cachebBitmap != null) {
				newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
			}
			cachebBitmap = newBitmap;
			cacheCanvas = newCanvas;
		}

		private float cur_x, cur_y;

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			
			float x = event.getX();
			float y = event.getY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				cur_x = x;
				cur_y = y;
				path.moveTo(cur_x, cur_y);
				break;
			}

			case MotionEvent.ACTION_MOVE: {
				path.quadTo(cur_x, cur_y, x, y);
				cur_x = x;
				cur_y = y;
				break;
			}

			case MotionEvent.ACTION_UP: {
				cacheCanvas.drawPath(path, paint);
				path.reset();
				break;
			}
			}

			invalidate();

			return true;
		}
	}
}
