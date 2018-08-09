package com.zxtech.mt.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zxtech.mtos.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class BottomMenuActivity extends Activity
  implements OnClickListener
{
  private Button photo_button,local_photo_button,cancel_button;
  private LinearLayout layout;
  private static int CAMERA_REQUEST = 0x001;
  private static int PHOTO_REQUEST_GALLERY = 0x002;
    private static  int PHOTO_REQUEST_CUT = 0x003;
    private String photoPath = "";


  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.alert_dialog);
  
    layout = (LinearLayout)findViewById(R.id.pop_layout);
    photo_button = (Button) findViewById(R.id.photo_button);
    local_photo_button = (Button) findViewById(R.id.local_photo_button);
    cancel_button = (Button) findViewById(R.id.cancel_button);
    layout.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			 Toast.makeText(BottomMenuActivity.this, "提示：点击窗口外部关闭窗口！", Toast.LENGTH_LONG).show();
		}
	});
    this.photo_button.setOnClickListener(this);
    this.local_photo_button.setOnClickListener(this);
    this.cancel_button.setOnClickListener(this);
  }


  public void onClick(View paramView)
  {
      int i = paramView.getId();
      if (i == R.id.photo_button) {//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                photoPath = UIController.SD_DIR_PATH + "/"+System.currentTimeMillis()+".png";
//                if( getIntent().getIntExtra("raw",0) == 1) {
//                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(photoPath)));
//                }
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);


      } else if (i == R.id.local_photo_button) {
          Intent intent = new Intent(Intent.ACTION_PICK);
          intent.setType("image/*");
          startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

      } else if (i == R.id.cancel_button) {
          finish();

      }


	}
  

@Override
	public void finish() {
		super.finish();
    this.overridePendingTransition(R.anim.push_bottom_out,0);
}
 

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    finish();
    return true;
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            if (data!=null) {
                Bitmap photo = data.getParcelableExtra("data");
                if (photo!=null) {
                    photoPath = saveImageToGallery(BottomMenuActivity.this,photo);
                }
            }
            Intent intent = getIntent();
            intent.putExtra("photoPath",photoPath);
            setResult(resultCode,intent);
            finish();
        }
        else if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                crop(uri);
            }
        }else if (requestCode == PHOTO_REQUEST_CUT){
            setResult(3,data);
            finish();
        }

    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1.3
//		intent.putExtra("aspectX", 1);
//		intent.putExtra("aspectY", 1.3);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "PNG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
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
}