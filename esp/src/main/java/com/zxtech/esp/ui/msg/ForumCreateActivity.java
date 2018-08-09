package com.zxtech.esp.ui.msg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.zxtech.common.util.T;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.BbsType;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;
import com.zxtech.esp.util.MyItemTouchCallback;
import com.zxtech.esp.util.VibratorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import g.api.http.GHttp;
import g.api.http.GRequestData;
import g.api.http.GRequestParams;
import g.api.http.volley.Request;

/**
 * Created by Administrator on 2017/7/19.
 */

public class ForumCreateActivity extends AppCompatActivity implements MyItemTouchCallback.OnDragListener,TextWatcher{

    private OptionsPickerView opv;
    private TextView tv_type;
    private EditText et_title;
    private EditText et_content;
    private ProgressBar progressBar;
    private TextView tv_save;
    private LinearLayout delete_layout;
    private TextView tv_delete;
    private ImageView iv_trash;
    private ArrayList<String> options1Items = new ArrayList<>();

    private BbsCreateImageAdapter adapter;
    private ItemTouchHelper itemTouchHelper;
    private String lan;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lan = Locale.getDefault().getLanguage();
        setContentView(R.layout.activity_forum_create);
        step();
    }

    private void step() {

        delete_layout = (LinearLayout) findViewById(R.id.delete_layout);
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        iv_trash = (ImageView) findViewById(R.id.iv_trash);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        BizUtil.setIconFont(tv_back, "\ue620");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = et_title.getText().toString();
                String content = et_content.getText().toString();
                releasePost(title,content);
            }
        });

        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_type.addTextChangedListener(this);
        tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBbsType();
            }
        });

        et_title = (EditText) findViewById(R.id.et_title);
        et_title.addTextChangedListener(this);
        et_content = (EditText) findViewById(R.id.et_content);
        et_content.addTextChangedListener(this);

        RecyclerView rlv = (RecyclerView) findViewById(R.id.rlv);
        GridLayoutManager mgr = new GridLayoutManager(this,3);
        rlv.setLayoutManager(mgr);
        rlv.setItemAnimator(new DefaultItemAnimator());
        rlv.setHasFixedSize(true);
        adapter = new BbsCreateImageAdapter(this);

        ArrayList<PhotoInfo> photoInfos = new ArrayList<>();
        PhotoInfo info = new PhotoInfo();
        photoInfos.add(info);
        info.setPhotoPath("default");

        adapter.setDatas(photoInfos);
        rlv.setAdapter(adapter);

        itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(adapter).setOnDragListener(this));
        itemTouchHelper.attachToRecyclerView(rlv);

        rlv.addOnItemTouchListener(new OnRecyclerItemClickListener(rlv) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition()!=adapter.getDatas().size()-1) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(ForumCreateActivity.this, 70);   //震动70ms
                }
            }
            /*@Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Item item = results.get(vh.getLayoutPosition());
                Toast.makeText(this,item.getId()+" "+item.getName(),Toast.LENGTH_SHORT).show();
            }*/
        });
    }

    void openTypePicker(final List<BbsType.Data> data){

        opv = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String typeName;
                if("en".equals(lan)){
                    typeName = data.get(options1).getType_name_en();
                }else{
                    typeName = data.get(options1).getType_name();
                }
                int typeId = data.get(options1).getId();
                tv_type.setText(typeName);
                tv_type.setTag(typeId);
            }
        }).build();
        opv.setPicker(options1Items);
        opv.show();
    }

    private void releasePost(String title, final String content){

        GsonCallBack<JsonElementVO> callBack = new GsonCallBack<JsonElementVO>(this){
            @Override
            public void onStart() {
                showLoading(ForumCreateActivity.this);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                if(isUploading){
                    progressBar.setProgress((int) (1f*current/total*100f));
                }
            }

            @Override
            protected void onDoSuccess(JsonElementVO bean) {
                T.showToast(ForumCreateActivity.this,bean.getMsg());
                setResult(1);
                finish();
                dismissLoading();
            }

            @Override
            public void onFailure(String info) {
                super.onFailure(info);
                dismissLoading();
            }
        };
        String url = URL.getInstance().releasePostUrl();
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("type",tv_type.getTag().toString());
        params.addBodyParameter("subject",title);
        params.addBodyParameter("body",content);
        params.addBodyParameter("createUser", MyApp.getUserId());

        List<PhotoInfo> images = adapter.getDatas();
        for (int i=0;i<images.size()-1;i++){
            params.addBodyParameter(GHttp.FILE_PREFIX + "file"+i,images.get(i).getPhotoPath());
        }

        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().sendFile(requestData, callBack,true);
    }

    private void getBbsType(){

        GsonCallBack<BbsType> callBack = new GsonCallBack<BbsType>(this){
            @Override
            public void onStart() {
                showLoading(ForumCreateActivity.this);
            }
            @Override
            protected void onDoSuccess(BbsType bean) {
                List<BbsType.Data> data = bean.getData();
                options1Items.clear();
                for (BbsType.Data item:data){
                    if("en".equals(lan)){
                        options1Items.add(item.getType_name_en());
                    }else{
                        options1Items.add(item.getType_name());
                    }
                }
                openTypePicker(data);
                dismissLoading();
            }
        };
        String url = URL.getInstance().getBbsTypeUrl();
        GRequestData requestData = new GRequestData(Request.Method.GET,url,null);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    public void muti() {

        FunctionConfig functionConfig = GalleryFinal.getCoreConfig().getFunctionConfig();
        //带配置
        GalleryFinal.openGalleryMuti(1,functionConfig,new GalleryFinal.OnHanlderResultCallback(){

            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {

                int size = adapter.getDatas().size();
                for (int i=(size-1);i>=0;i--){
                    if (!adapter.getItem(i).getPhotoPath().contains("default")){
                        adapter.onSwiped(i);
                    }
                }
                for(PhotoInfo photoInfo:resultList){
                    adapter.addData(0,photoInfo);
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                T.showToast(ForumCreateActivity.this,errorMsg);
            }
        });
    }

    @Override
    public void onFinishDrag() {
    }

    @Override
    public void deleteState(boolean delete) {

        if(delete){
            delete_layout.setBackgroundResource(R.color.holo_red_dark);
            tv_delete.setText(getResources().getString(R.string.post_delete_tv_s));
            iv_trash.setImageResource(R.mipmap.ic_trash_open);
        }else{
            tv_delete.setText(getResources().getString(R.string.post_delete_tv_d));
            delete_layout.setBackgroundResource(R.color.holo_red_light);
            iv_trash.setImageResource(R.mipmap.ic_trash);
        }
    }

    @Override
    public void dragState(boolean start) {

        if(start){
            delete_layout.setVisibility(View.VISIBLE);
        }else{
            delete_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void moveItem(int position){
        adapter.onSwiped(position);
    }

    private boolean checkEmpty(){

        String type = tv_type.getText().toString();
        String title = et_title.getText().toString();
        String content = et_content.getText().toString();
        if(TextUtils.isEmpty(type) || TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
            return false;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if(checkEmpty()){
            tv_save.setEnabled(true);
            tv_save.setClickable(true);
            tv_save.setTextColor(getResources().getColor(R.color.text_color_white));
        }else{
            tv_save.setClickable(false);
            tv_save.setEnabled(false);
            tv_save.setTextColor(getResources().getColor(R.color.text_color_gray));
        }
    }
}
