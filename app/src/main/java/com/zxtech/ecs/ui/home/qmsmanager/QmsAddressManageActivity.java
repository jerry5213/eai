package com.zxtech.ecs.ui.home.qmsmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.QmsAddressList;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp521 on 2018/3/30.
 */

public class QmsAddressManageActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private MyAdapter adapter;
    private List<QmsAddressList.QmsAddress> mDatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qms_address_manage;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(mToolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider_line_gray));
        recycleView.addItemDecoration(divider);

        adapter = new MyAdapter(R.layout.item_qms_address_manage,mDatas);
        recycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = getIntent();
                intent.putExtra("address",mDatas.get(position));
                setResult(55,intent);
                finish();
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()){
                    case R.id.tv_edit:
                        Intent intent = new Intent(QmsAddressManageActivity.this,QmsAddressEdit.class);
                        intent.putExtra("address",mDatas.get(position));
                        intent.putExtra("action","1");
                        startActivityForResult(intent,2);
                        break;
                }
            }
        });

        initData();
    }

    @OnClick(R.id.tv_add)
    public void click(){

        Intent intent = new Intent(QmsAddressManageActivity.this,QmsAddressEdit.class);
        intent.putExtra("action","0");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }

    protected void initData(){

        //将参数转为json格式
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("FKR",getUserNo());
        String params = GsonUtils.toJson(paramsMap, false);

        HttpFactory.getApiService()
                .getAppAddressList(params)
                .compose(RxHelper.<BaseResponse<QmsAddressList>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<QmsAddressList>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<QmsAddressList> response) {
                        List<QmsAddressList.QmsAddress> list = response.getData().getAddressList();
                        if(list!=null && list.size()>0){
                            mDatas.clear();
                            mDatas.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private class MyAdapter extends BaseQuickAdapter<QmsAddressList.QmsAddress,BaseViewHolder>{

        public MyAdapter(int layoutResId, @Nullable List<QmsAddressList.QmsAddress> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, QmsAddressList.QmsAddress item) {

            helper.setText(R.id.name_tv,item.getLXR());
            helper.setText(R.id.customer_phone,item.getLXDH());
            helper.setText(R.id.tv_address,item.getLXDZ());
            if(item.getIsMR()){
                helper.setVisible(R.id.tv_default,true);
            }else{
                helper.setVisible(R.id.tv_default,false);
            }
            helper.setText(R.id.tv_label,item.getSX());
            helper.addOnClickListener(R.id.tv_edit);
        }
    }
}
