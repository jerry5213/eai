package com.zxtech.mt.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zxtech.mt.adapter.AccessoryAdapter;
import com.zxtech.mt.entity.MtAccessoryInquiry;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.widget.PopMenu;
import com.zxtech.mt.widget.SimpleDialog;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp523 on 2017/7/31.
 */
@Route(path = "/mt/accessoryinquiry")
public class AccessoryActivity extends BaseActivity implements AccessoryAdapter.AccessoryAdapterCallBack,BGARefreshLayout.BGARefreshLayoutDelegate {

    private BGARefreshLayout mRefreshLayout;
    private ListView listView;
    private EditText search_edittext;
    private AccessoryAdapter orderAdapter;

    private List<MtAccessoryInquiry> data = new ArrayList<>();
    private List<MtAccessoryInquiry> tempAskData = new ArrayList<>();

    public final static int BACK_REFESH_ACTIVITY = 1;


    @Override
    protected void onCreate() {

        View view = mInfalter.inflate(R.layout.viewpager_order, null);
        main_layout.addView(view);
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lps);
        title_textview.setText(getString(R.string.menu_accessory_inquiry));
        set_textview.setBackgroundResource(R.drawable.setting);
        setBottomLayoutHide();
    }

    @Override
    protected void findView() {
        mRefreshLayout = findViewById(R.id.rl_refresh);
        listView = findViewById(R.id.listView);
        search_edittext = findViewById(R.id.search_edittext);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {

        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, false);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        orderAdapter = new AccessoryAdapter(mContext, data, R.layout.item_ask_price);
        orderAdapter.setListener(this);
        listView.setAdapter(orderAdapter);
    }

    @Override
    protected void initView() {

        handleSearch();
        mRefreshLayout.beginRefreshing();
    }

    public void doFresh(){

        data.clear();
        tempAskData.clear();
        Map<String, String> param = new HashMap<>();
        param.put("emp_id", SPUtils.get(mContext, "emp_id", "admin").toString());

        HttpUtil.getInstance(mContext).request("/mtmo/getaccessoryinquiry.mo", param, new HttpCallBack<List<MtAccessoryInquiry>>() {
            @Override
            public void onSuccess(List<MtAccessoryInquiry> list) {
                if (list != null) {
                    data.addAll(list);
                    tempAskData.addAll(list);
                }
                search_edittext.setText(null);
                orderAdapter.notifyDataSetChanged();
                mRefreshLayout.endRefreshing();
            }

            @Override
            public void onFail(String msg) {
                mRefreshLayout.endRefreshing();
            }
        });
    }


    private void handleSearch() {
        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                data.clear();
                for (MtAccessoryInquiry accessory : tempAskData) {
                    if (accessory.getProj_name() != null && (accessory.getProj_name().contains(text))) {
                        data.add(accessory);
                    } else if (accessory.getAcc_code() != null && (accessory.getAcc_code().contains(text))) {
                        data.add(accessory);
                    } else if (accessory.getElevator_code() != null && (accessory.getElevator_code().contains(text))) {
                        data.add(accessory);
                    }
                }
                orderAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.set_textview) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int width = (int) (displayMetrics.widthPixels * 0.4);
            int height = (int) (displayMetrics.heightPixels * 0.4);

            final PopMenu pm = new PopMenu(this, width, height);

            List<Map<String, Object>> resources = new ArrayList<>();
            Map<String, Object> maps1 = new HashMap<>();
            maps1.put("text", getString(R.string.standard_inquiry));
            resources.add(maps1);

            pm.addItems(resources);
            pm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            pm.dismiss();
                            startActivityForResult(new Intent(mContext, StandardPartActivity.class), 19);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            break;
                    }
                }
            });
            pm.showAsDropDown(set_textview);

        }
    }

    @Override
    public void delete(final int position) {
        dialog = SimpleDialog.createDialog(mContext, mContext.getString(R.string.hint), "是否确认删除?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                Map<String, String> param = new HashMap<>();
                param.put("id", data.get(position).getId());

                HttpUtil.getInstance(mContext).request("/mtmo/deleteaccessoryinquiry.mo", param, new HttpCallBack<String>() {
                    @Override
                    public void onSuccess(String list) {
                        ToastUtil.showLong(mContext, "删除成功");
                        data.remove(position);
                        orderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFail(String msg) {
                    }
                });

            }
        }, false);
        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == BACK_REFESH_ACTIVITY) {
            mRefreshLayout.beginRefreshing();
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        doFresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
