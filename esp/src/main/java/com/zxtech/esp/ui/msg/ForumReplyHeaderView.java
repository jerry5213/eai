package com.zxtech.esp.ui.msg;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxtech.common.util.T;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.AppConfig;
import com.zxtech.esp.R;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.ForumVO;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;
import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/8/23.
 */

public class ForumReplyHeaderView extends FrameLayout {

    private TextView tv_nickname;
    private TextView tv_time;
    private TextView tv_look;
    private TextView tv_reply;
    private ImageView iv_head;
    private EditText et_comment;
    private TextView tv_up;
    private TextView tv_pen;
    private LinearLayout l_layout;
    private TextView tv_content;
    private TextView tv_title;

    private DisplayImageOptions opts;
    private ForumReplyActivity parentActivity;
    private String forumId;

    public ForumReplyHeaderView(Context context) {
        super(context);
        parentActivity = ((ForumReplyActivity)context);
        opts = BizUtil.getDefaultImageOPTSBuilder(T.dip2px(parentActivity,30.0f)).showImageForEmptyUri(R.mipmap.avatar_default).build();
        setup(context);
    }

    public ForumReplyHeaderView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    private void setup(Context context) {

        LayoutInflater.from(context).inflate(R.layout.view_forum_reply_header, this);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_look = (TextView) findViewById(R.id.tv_look);
        tv_reply = (TextView) findViewById(R.id.tv_reply);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        et_comment = (EditText) findViewById(R.id.et_comment);
        tv_up = (TextView) findViewById(R.id.tv_up);
        tv_pen = (TextView) findViewById(R.id.tv_pen);
        l_layout = (LinearLayout) findViewById(R.id.l_layout);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    public void setShowText(ForumVO.Data data){

        forumId = data.getId();

        tv_nickname.setText(data.getPost_user());
        tv_time.setText(data.getCreate_date());
        tv_look.setText(data.getLook_num()+"");
        tv_reply.setText(data.getReply_num()+"");

        String url = AppConfig.getFileHost()+ data.getPost_user_url();
        ImageLoader.getInstance().displayImage(url, iv_head, opts);

        BizUtil.setIconFont(tv_pen, "\ue617");
        BizUtil.setIconFont(tv_up, "\ue635");
        tv_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = et_comment.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    upComment(text);
                }else{
                    T.showToast(v.getContext(),"请输入评论内容");
                }
            }
        });
        tv_content.setText(data.getBody());
        tv_title.setText(data.getSubject());
        initImage(data);
    }

    void upComment(String text){

        GsonCallBack<JsonElementVO> callBack = new GsonCallBack<JsonElementVO>(parentActivity){
            @Override
            public void onStart() {
                showLoading(parentActivity);
            }
            @Override
            protected void onDoSuccess(JsonElementVO bean) {
                dismissLoading();
                parentActivity.initData();
                et_comment.setText("");
            }
        };
        String url = URL.getInstance().replyBbsUrl();
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("ownerUserId", MyApp.getUserId());
        params.addBodyParameter("content",text);
        params.addBodyParameter("postId",forumId);
        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    private void initImage(ForumVO.Data data){

        DisplayImageOptions opts = BizUtil.getDefaultImageOPTSBuilder(0).showImageForEmptyUri(R.mipmap.img_null).build();
        List<ForumVO.Images> images = data.getImages();
        if(images!=null && images.size()>0){

            final ArrayList<PhotoInfo> list = toPhotoInfo(images);

            int sw = getResources().getDisplayMetrics().widthPixels;
            int m = images.size();
            int rule = ((sw - T.dip2px(parentActivity,20)) / 3);

            LinearLayout m_LinearLayout = new LinearLayout(parentActivity);
            m_LinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            l_layout.addView(m_LinearLayout,param);

            LinearLayout m_LinearLayout_two = new LinearLayout(parentActivity);
            m_LinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            l_layout.addView(m_LinearLayout_two,param);

            LinearLayout.LayoutParams iv_param = new LinearLayout.LayoutParams(rule,rule);
            for (int i=0;i<m;i++){
                ForumVO.Images vo = images.get(i);
                ImageView imageView = new ImageView(parentActivity);
                imageView.setPadding(3,3,3,3);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                if(i<=2){
                    m_LinearLayout.addView(imageView,iv_param);
                }else{
                    m_LinearLayout_two.addView(imageView,iv_param);
                }
                ImageLoader.getInstance().displayImage(AppConfig.getFileHost()+vo.getPhoto_url(), imageView, opts);

                imageView.setTag(i);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int)v.getTag();
                        Intent intent = new Intent(v.getContext(), LPhotoPreviewActivity.class);
                        intent.putExtra("photo_list",list);
                        intent.putExtra("position",position);
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }
    }

    private ArrayList<PhotoInfo> toPhotoInfo(List<ForumVO.Images> images){

        ArrayList<PhotoInfo> list = new ArrayList<>();
        int id = 1000;
        for(ForumVO.Images vo:images){

            PhotoInfo info = new PhotoInfo();
            info.setPhotoId(id);
            info.setPhotoPath(AppConfig.getFileHost()+vo.getPhoto_url());
            list.add(info);
            id++;
        }
        return list;
    }
}
