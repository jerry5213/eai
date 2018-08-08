package com.zxtech.ecs.ui.home.scheme;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.VoiceResult;
import com.zxtech.ecs.ui.home.scheme.bdtts.MainHandlerConstant;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by syp523 on 2018/2/1.
 */

public  class ShowContentDialog extends Dialog implements MainHandlerConstant {

    private Context context;

    private VoiceResult.ResultBean.MessagesBean message;

    private TextView content_tv;
    private TextView title_tv;
    private ImageView close_iv;
   // private ImageView display_iv;
    private ImageView pet_iv;
    //private Button look_btn;
    //private JCVideoPlayerStandard videoplayer;
    private String videoUrl = "";
    //private View line;
    private JZVideoPlayerStandard videoplayer;

    private boolean isRun;


    public ShowContentDialog(@NonNull Context context, VoiceResult.ResultBean.MessagesBean message) {
        super(context);
        this.context = context;
        this.message = message;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_show_content);
        content_tv = findViewById(R.id.content_tv);
        title_tv = findViewById(R.id.title_tv);
        close_iv = findViewById(R.id.close_iv);
      //  display_iv = findViewById(R.id.display_iv);
        pet_iv = findViewById(R.id.pet_iv);
      //  look_btn = findViewById(R.id.look_btn);
        //line = findViewById(R.id.line);

        Glide.with(context).load(R.drawable.male_announcer).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(pet_iv);
        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
         videoplayer = findViewById(R.id.videoplayer);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        initDialogSize();
        initDispaly();
//        look_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (message.getConfig() < 4 && message.getConfig() > 1) {
//                    dismiss();
//                    Intent it = new Intent(context,ShowBigImageActivity.class);
//                    it.putStringArrayListExtra("images", (ArrayList<String>) message.getMsg_picture());
//                    ((Activity)context).startActivity(it);
//                }else if (message.getConfig() >= 4) {
//                    dismiss();
//                    ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    JCVideoPlayerStandard.startFullscreen(context, JCVideoPlayerStandard.class,videoUrl , "");
//                }
//
//            }
//        });
    }

    private void initDispaly() {

        if (message.getConfig() == 1) {
            //look_btn.setVisibility(View.GONE);
            videoplayer.setVisibility(View.GONE);
           // display_iv.setVisibility(View.GONE);
        } else if (message.getConfig() < 4) {
//            Glide.with(context)
//                    .load( message.getMsg_picture().get(0))
//                    .placeholder(R.drawable.place_holder)
//                    .error(R.drawable.place_holder)
//                    .fitCenter()
//                    .dontAnimate()
//                    .into(display_iv);
        } else {
            videoUrl = message.getMsg_video().get(1);
            videoplayer.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context)
                    .load(message.getMsg_video().get(0))
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder)
                    .dontAnimate()
                    .into(videoplayer.thumbImageView);
            videoplayer.setUp(message.getMsg_video().get(1)
                    , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
        }

        content_tv.setText(message.getMsg_text());
        title_tv.setText(message.getTitle());
    }

    public void stopAnnouncer(){
        Glide.with(context).load(R.drawable.male_announcer_stop).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(pet_iv);
    }




    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onBackPressed() {
//        if (JCVideoPlayer.backPress()) {
//            return;
//        }
        super.onBackPressed();
    }



    private void initDialogSize() {
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值

          //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.85);    //宽度设置为屏幕的0.9
        getWindow().setAttributes(p);     //设置生效

    }
}
