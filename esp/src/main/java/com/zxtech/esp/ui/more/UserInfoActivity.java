package com.zxtech.esp.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxtech.common.util.SPCache;
import com.zxtech.common.util.T;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.AppConfig;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.UserInfoVO;
import com.zxtech.esp.util.BizUtil;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by SYP521 on 2017/7/18.
 */

public class UserInfoActivity extends AppCompatActivity{

    private static final int HEADER_SET = 1;
    private static final int NICKNAME_SET = 2;
    private static final int PWD_SET = 3;
    private static final int FACE_SET = 4;
    private UserInfoVO.Data user;

    private DisplayImageOptions opts;
    private ImageView owner_photo;
    private TextView tv_nickname,tv_language,tv_face;
    private int isResult = 0;
    private OptionsPickerView opv;
    private ArrayList<String> options1Items = new ArrayList<>();
    private String lan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lan = Locale.getDefault().getLanguage();
        user = (UserInfoVO.Data) getIntent().getSerializableExtra(C.DATA);
        setContentView(R.layout.activity_etp_user_info);
        step();
    }

    private void step() {

        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        BizUtil.setIconFont(tv_back, "\ue620");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(isResult);
                finish();
            }
        });

        tv_face = findViewById(R.id.tv_face);
        String status = SPCache.getInstance(this).getString(C.FACE_INFO_STATUS,"");
        if("1".equals(status)){
            tv_face.setText(getText(R.string.face_has_record));
        }else{
            tv_face.setText(getText(R.string.face_no_record));
        }

        owner_photo =  findViewById(R.id.owner_photo);
        opts = BizUtil.getDefaultImageOPTSBuilder(T.dip2px(this,40.0f)).showImageOnFail(R.mipmap.avatar_default).build();
        ImageLoader.getInstance().displayImage(URL.getInstance().getFullUrl(user.getPhoto_url()),owner_photo,opts);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_nickname.setText(MyApp.getNick_name());

        RelativeLayout item_head = (RelativeLayout) findViewById(R.id.item_head);
        item_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),UserInfoHeaderActivity.class);
                intent.putExtra(C.DATA,user);
                startActivityForResult(intent,HEADER_SET);
            }
        });
        RelativeLayout item_username = (RelativeLayout) findViewById(R.id.item_username);
        item_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),UserInfoNameActivity.class);
                intent.putExtra(C.DATA,user.getName());
                startActivityForResult(intent,NICKNAME_SET);
            }
        });
        RelativeLayout item_pwd = (RelativeLayout) findViewById(R.id.item_pwd);
        item_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),UserInfoPwdActivity.class);
                startActivityForResult(intent,PWD_SET);
            }
        });
        tv_language = (TextView) findViewById(R.id.tv_language);
        tv_language.setText(getFull());
        RelativeLayout item_language = (RelativeLayout) findViewById(R.id.item_language);
        item_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLanguagePicker();
            }
        });
        RelativeLayout item_face = findViewById(R.id.item_face);
        item_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),UserInfoFaceActivity.class);
                startActivityForResult(intent,FACE_SET);
            }
        });

        if("en".equals(lan)){
            options1Items.clear();
            options1Items.add("Chinese");
            options1Items.add("English");
            options1Items.add("All");
        }else{
            options1Items.clear();
            options1Items.add("中文");
            options1Items.add("英文");
            options1Items.add("全部");
        }
    }

    private void openLanguagePicker() {

        if(opv == null){
            opv = new OptionsPickerView.Builder(this,new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {

                    String lan = options1Items.get(options1);
                    tv_language.setText(lan);
                    AppConfig.LAN= getShort(lan);
                    isResult = 1;
                }
            }).build();
            opv.setPicker(options1Items);
        }
        opv.show();
    }

    protected String getShort(String lan){

        if("Chinese".equals(lan) || "中文".equals(lan)){
            lan = "zh";
        }else if("English".equals(lan) || "英文".equals(lan)){
            lan = "en";
        }else if("All".equals(lan) || "全部".equals(lan)){
            lan = "all";
        }
        return lan;
    }

    protected String getFull(){

        String str = "";
        switch (lan){
            case "zh":
                if("zh".equals(AppConfig.LAN)) str = "中文";
                else if("en".equals(AppConfig.LAN)) str = "英文";
                else if("all".equals(AppConfig.LAN)) str = "全部";
                break;
            case "en":
                if("zh".equals(AppConfig.LAN)) str = "Chinese";
                else if("en".equals(AppConfig.LAN)) str = "English";
                else if("all".equals(AppConfig.LAN)) str = "All";
                break;
        }
        return str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case HEADER_SET:
                String realFilePath = data.getStringExtra("backInfoHeader");
                String url = URL.getInstance().getFullUrl(realFilePath);
                ImageLoader.getInstance().displayImage(url, owner_photo, opts);
                user.setPhoto_url(realFilePath);
                isResult = 1;
                break;
            case NICKNAME_SET:
                String name = data.getStringExtra("name");
                tv_nickname.setText(name);
                isResult = 1;
                break;
            case PWD_SET:
                break;
            case FACE_SET:
                tv_face.setText(getText(R.string.face_has_record));
                break;
            default:
                break;
        }
    }
}
