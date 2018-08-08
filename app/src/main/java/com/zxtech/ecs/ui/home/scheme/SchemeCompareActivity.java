package com.zxtech.ecs.ui.home.scheme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.CompareAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.scheme.detail.SchemeDetailActivity;
import com.zxtech.ecs.util.ConvertUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.zxtech.ecs.common.Constants.CODE_SPECIALNONSTANDARD;

/**
 * 直梯方案对比
 * Created by syp523 on 2018/1/22.
 */

public class SchemeCompareActivity extends BaseActivity implements CompareAdapter.CompareCallBack {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.compare_btn)
    Button compare_btn;
    @BindView(R.id.edit_btn)
    Button edit_btn;
    @BindView(R.id.collection_btn)
    Button collection_btn;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    List<Programme> datas = new ArrayList<>();
    HashMap<Integer, List<String>> attachMap = new HashMap<>();
    private CompareAdapter adapter;
    private final static int INTENT_DETAIL = 0x001;
    private final static int INTENT_COMPARE = 0x002;
    private String budget;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scheme_compare;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.scheme_compare));
        datas = (List<Programme>) getIntent().getSerializableExtra("datas");
        budget = getIntent().getStringExtra("budget");

        adapter = new CompareAdapter(mContext, R.layout.item_compare, datas);
        adapter.setListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration(8));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (adapter.getSelectedPositions().contains(position)) {
                    adapter.getSelectedPositions().remove(Integer.valueOf(position));
                } else {
                    adapter.getSelectedPositions().add(position);
                }
                adapter.notifyItemChanged(position);
                getSelectSize(adapter.getSelectedPositions().size());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    @Override
    public void collection(final int position) {


    }

    public void getSelectSize(int size) {
        switch (size) {
            case 0:
                compare_btn.setBackgroundResource(R.drawable.button_grey_radius);
                compare_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                compare_btn.setEnabled(false);
                edit_btn.setBackgroundResource(R.drawable.button_grey_radius);
                edit_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                edit_btn.setEnabled(false);
                collection_btn.setBackgroundResource(R.drawable.button_grey_radius);
                collection_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                collection_btn.setEnabled(false);
                break;
            case 1:
                compare_btn.setBackgroundResource(R.drawable.button_grey_radius);
                compare_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                compare_btn.setEnabled(false);
                edit_btn.setBackgroundResource(R.drawable.button_main_radius);
                edit_btn.setTextColor(getResources().getColor(R.color.white));
                edit_btn.setEnabled(true);
                collection_btn.setBackgroundResource(R.drawable.button_main_radius);
                collection_btn.setTextColor(getResources().getColor(R.color.white));
                collection_btn.setEnabled(true);
                break;
            case 2:
                compare_btn.setBackgroundResource(R.drawable.button_main_radius);
                compare_btn.setTextColor(getResources().getColor(R.color.white));
                compare_btn.setEnabled(true);
                edit_btn.setBackgroundResource(R.drawable.button_grey_radius);
                edit_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                edit_btn.setEnabled(false);
                collection_btn.setBackgroundResource(R.drawable.button_grey_radius);
                collection_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                collection_btn.setEnabled(false);
                break;
            case 3:
                compare_btn.setBackgroundResource(R.drawable.button_main_radius);
                compare_btn.setTextColor(getResources().getColor(R.color.white));
                compare_btn.setEnabled(true);
                edit_btn.setBackgroundResource(R.drawable.button_grey_radius);
                edit_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                edit_btn.setEnabled(false);
                collection_btn.setBackgroundResource(R.drawable.button_grey_radius);
                collection_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                collection_btn.setEnabled(false);
                break;
        }
    }

    @Override
    public void compare(int position) {
    }

    @OnClick(R.id.collection_btn)
    public void collectionAction() {
        final int selectedPosition = adapter.getSelectedPositions().get(0);
        if (adapter.getIsCollections().contains(selectedPosition)) {
            ToastUtil.showLong(mContext.getString(R.string.msg11));
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = getLayoutInflater();
        final View dialog_edit = inflater.inflate(R.layout.dialog_edit, null);
        builder.setView(dialog_edit).setTitle(getString(R.string.favorites_name))
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_userName = (EditText) dialog_edit.findViewById(R.id.content_et);

                        if (!TextUtils.isEmpty(et_userName.getText())) {
                            Programme programme = datas.get(selectedPosition);
                            JsonObject jsonObject = ConvertUtil.convertALLParamJSON(programme);
                            jsonObject.addProperty("ISVERIFY", "1");
                            jsonObject.addProperty("Guid", "");
                            jsonObject.addProperty("CollectionName", et_userName.getText().toString());
                            jsonObject.addProperty(Constants.CODE_ELEVATORTYPE, programme.getElevatorType());
                            jsonObject.addProperty(Constants.CODE_ELEVATORPRODUCT, programme.getElevatorProduct());
                            jsonObject.addProperty("PriceDrawingNo", programme.getPriceDrawingNo());
                            jsonObject.addProperty("SalesParameterDrawingNo", programme.getSalesParameterDrawingNo());
                            jsonObject.addProperty("TypeId", Constants.ELEVATOR);
                            //用户id
                            jsonObject.addProperty("userGuid", getUserId());
                            if (datas.get(selectedPosition).getSpecialNonStandard() != null) {
                                jsonObject.addProperty(CODE_SPECIALNONSTANDARD, datas.get(selectedPosition).getSpecialNonStandard());
                            }

                            Map<String, RequestBody> bodyMap = new HashMap<>();
                            bodyMap.put("params", RequestBody.create(MediaType.parse("text/plain"), jsonObject.toString()));
                            if (attachMap.get(selectedPosition) != null) {
                                for (int i = 0; i < attachMap.get(selectedPosition).size(); i++) {
                                    if (!TextUtils.isEmpty(attachMap.get(selectedPosition).get(i))) {
                                        bodyMap.put("files\";filename=\"attach"+i+".png", RequestBody.create(MediaType.parse("image/png"), new File(attachMap.get(selectedPosition).get(i))));
                                    }
                                }
                            }

                            updateCollection(bodyMap,selectedPosition);

                        }

                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void updateCollection(Map<String, RequestBody> bodyMap, final int selectedPosition) {

        baseResponseObservable = HttpFactory.getApiService().addOrUpdateCollection(bodyMap);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<Map<String,String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String,String>>>(SchemeCompareActivity.this, true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String,String>> response) {
                        adapter.getDatas().get(selectedPosition).setSpecialNonStandardPath(response.getData().get("specialNonStandardPath"));

                        ToastUtil.showLong(getString(R.string.msg34));
                        adapter.getIsCollections().add(selectedPosition);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @OnClick(R.id.edit_btn)
    public void editAction() {
        int selectedPosition = adapter.getSelectedPositions().get(0);

        Intent intent = new Intent();
        intent.setClass(mContext, SchemeDetailActivity.class);
        Programme programme = datas.get(selectedPosition);
        HashMap<String, String> paramMap = ConvertUtil.convertParamValueMap(programme);
        paramMap.put(Constants.CODE_ELEVATORTYPE, programme.getElevatorType());
        paramMap.put(Constants.CODE_ELEVATORPRODUCT, programme.getElevatorProduct());
        paramMap.put("PriceDrawingNo", programme.getPriceDrawingNo());
        paramMap.put("SalesParameterDrawingNo", programme.getSalesParameterDrawingNo());
        paramMap.put("SpecialNonStandardPath", programme.getSpecialNonStandardPath());
        paramMap.put("SpecialNonStandard", programme.getSpecialNonStandard());
        paramMap.put(Constants.CODE_PRICE, String.valueOf(programme.getPrice()));
        intent.putExtra("datas", paramMap);
        startActivityForResult(intent, INTENT_DETAIL);
    }

    @OnClick(R.id.compare_btn)
    public void compareAction() {
        if (adapter.getSelectedPositions().size() < 2) {
            return;
        }
        List<Programme> programmes = new ArrayList<>();
        List<String> schemeNum = new ArrayList<>();
        Collections.sort(adapter.getSelectedPositions());
        for (Integer position : adapter.getSelectedPositions()) {
            schemeNum.add(String.valueOf(position));
            programmes.add(datas.get(position));
        }
        Intent intent = new Intent(mContext, SchemePreviewActivity.class);
        intent.putExtra("datas", (Serializable) programmes);
        intent.putExtra("schemeNum", (Serializable) schemeNum);
        intent.putExtra("budget", budget);
        startActivityForResult(intent, INTENT_COMPARE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_DETAIL && resultCode == 2) {
            int selectedPosition = adapter.getSelectedPositions().get(0);
            Programme bean = (Programme) data.getSerializableExtra("data");
            List<String> attachDatas = (List<String>) data.getSerializableExtra("attachDatas");
            datas.set(selectedPosition, bean);
            attachMap.put(selectedPosition, attachDatas);
            adapter.notifyDataSetChanged();
        } else if (requestCode == INTENT_COMPARE && resultCode == 3) {
            Programme bean = (Programme) data.getSerializableExtra("data");
            int changePosition = data.getIntExtra("changePosition", 0);
            datas.set(changePosition, bean);
            adapter.notifyDataSetChanged();
        }
    }
}
