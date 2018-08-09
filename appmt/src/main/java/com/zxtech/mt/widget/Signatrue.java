package com.zxtech.mt.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zxtech.mt.common.UIController;
import com.zxtech.mtos.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Signatrue extends Dialog implements View.OnClickListener{
	
	Context context;
	LayoutParams p ;

	private Dialog alertDialog;

    private ImageView sign_imageview;

	public Signatrue(Context context,ImageView sign_imageview
			) {
		super(context);
		this.context = context;
        this.sign_imageview = sign_imageview;

	}

	static final int BACKGROUND_COLOR = Color.WHITE;
	static final int BRUSH_COLOR = Color.BLACK;
	
	public PaintView mView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.person_check_sign);
		
		WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		int judge = context.getResources().getConfiguration().orientation;
		p = getWindow().getAttributes();  //��ȡ�Ի���ǰ�Ĳ���ֵ 
		
		if(judge == 1){	//����
			p.width = (int) (width*0.85);
			p.height = (int) (height*0.45);
		}else{
			p.width = (int) (width*0.85);
			p.height = (int) (height*0.85);
		}
		
		getWindow().setAttributes(p);     //������Ч
		
		mView = new PaintView(context);
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.pCSView);
		frameLayout.addView(mView);
		mView.requestFocus();
		Button btnClear = (Button) findViewById(R.id.pCSDelete);
		
		btnClear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 mView.clear();
			}
		});

//		final Button btnOk = (Button) findViewById(R.id.pCSSure);
//		btnOk.setOnClickListener(this);
//
//		Button btnCancel = (Button)findViewById(R.id.pCSSCancel);
//		btnCancel.setOnClickListener(this);
	}


    public  String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
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
		
		switch (view.getId()) {
//		case R.id.pCSSure:
//            sign_imageview.setImageBitmap(mView.getCachebBitmap());
//            UIController.SIGN_FILE_PATH = saveImageToGallery(context,mView.getCachebBitmap());
//            this.dismiss();
//            break;
//
//		case R.id.pCSSCancel:
//			this.dismiss();
//			break;
//		case R.id.positiveButton:
//
//			String signName = Rate.signName;
//			String signNameList = mCache.getAsString("comSignList");
//			if(signNameList!=null){
//
//				signNameList +=","+signName;
//				mCache.put("comSignList", signNameList);
//			}else{
//
//				mCache.put("comSignList",signName);
//			}
//			Toast.makeText(context, "����ɹ�", Toast.LENGTH_SHORT).show();
//
//			alertDialog.dismiss();
//			Signatrue.this.dismiss();
//			break;
//		case R.id.negativeButton:
//			/** �ر��б� */
//			alertDialog.dismiss();
//			Signatrue.this.dismiss();
//			break;
//
//		default:
//			break;
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
