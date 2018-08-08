package com.zxtech.ecs.ui.home.scheme.collection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.CollectionAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Collection;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.engineering.FloorStationFragment;
import com.zxtech.ecs.ui.home.scheme.CheckTipsDialogFragment;
import com.zxtech.ecs.ui.home.scheme.SchemeActivity;
import com.zxtech.ecs.ui.home.scheme.SchemePreviewActivity;
import com.zxtech.ecs.ui.home.scheme.detail.RequireFragment;
import com.zxtech.ecs.ui.home.scheme.detail.SchemeDetailActivity;
import com.zxtech.ecs.util.ConvertUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.gks.common.SPUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.zxtech.ecs.common.Constants.CODE_SPECIALNONSTANDARD;

/**
 * 直梯收藏列表
 * Created by syp523 on 2017/12/27.
 */

public class CollectionActivity extends BaseActivity implements CollectionAdapter.OnclickCallBack {
    private CollectionAdapter mAdapter;

    private List<Collection> mDatas = new ArrayList<>();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.top_tool_layout)
    LinearLayout top_tool_layout;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.compare_btn)
    Button compare_btn;
    @BindView(R.id.rename_btn)
    Button rename_btn;
    @BindView(R.id.delete_btn)
    Button delete_btn;
    @BindView(R.id.edit_btn)
    Button edit_btn;
    @BindView(R.id.engineering_btn)
    Button engineering_btn;
    @BindView(R.id.order_btn)
    Button order_btn;
    @BindView(R.id.title_layout)
    LinearLayout title_layout;
    @BindView(R.id.price_tv)
    TextView price_tv;
    @BindView(R.id.date_tv)
    TextView date_tv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final int DEFAULT = 0;
    private static final int DESC = 1;
    private static final int ASC = 2;
    private int priceSort = DEFAULT;
    private int dateSort = DEFAULT;
    Set<String> permissionSet = new HashSet<>();
    private static final String B_ = "B_";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.favorites_list));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CollectionAdapter(mContext, R.layout.item_collection, mDatas);
        mAdapter.setListener(this);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setAdapter(mAdapter);
        String permission = (String) com.zxtech.ecs.util.SPUtils.get(mContext, "permission", "");
        String[] split = permission.split(",");
        for (int i = 0; i < split.length; i++) {
            permissionSet.add(split[i]);
        }

        if (permissionSet.contains("order")) {
            order_btn.setTextColor(getResources().getColor(R.color.purple));
            order_btn.setEnabled(true);
        } else {
            order_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            order_btn.setEnabled(false);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        baseResponseObservable = HttpFactory.getApiService().getCollectionList(getUserId(), Constants.ELEVATOR);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<Collection>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Collection>>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Collection>> response) {
                        mDatas.clear();
                        mDatas.addAll(response.getData());
                        if (priceSort == DESC) {
                            Drawable image = getResources().getDrawable(R.drawable.down);
                            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                            price_tv.setCompoundDrawables(null, null, image, null);
                            Collections.sort(mDatas, new Comparator<Collection>() {
                                @Override
                                public int compare(Collection o1, Collection o2) {
                                    return o2.getElevatorPrice() - o1.getElevatorPrice();
                                }
                            });
                        } else if (priceSort == ASC) {
                            Drawable image = getResources().getDrawable(R.drawable.up);
                            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                            price_tv.setCompoundDrawables(null, null, image, null);
                            Collections.sort(mDatas, new Comparator<Collection>() {
                                @Override
                                public int compare(Collection o1, Collection o2) {
                                    return o1.getElevatorPrice() - o2.getElevatorPrice();
                                }
                            });
                        } else if (dateSort == DESC) {
                            Drawable image = getResources().getDrawable(R.drawable.down);
                            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                            date_tv.setCompoundDrawables(null, null, image, null);
                            Collections.sort(mDatas, new Comparator<Collection>() {
                                @Override
                                public int compare(Collection o1, Collection o2) {
                                    return o2.getCreateDate().compareTo(o1.getCreateDate());
                                }
                            });
                        } else if (dateSort == ASC) {
                            Drawable image = getResources().getDrawable(R.drawable.up);
                            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                            date_tv.setCompoundDrawables(null, null, image, null);
                            Collections.sort(mDatas, new Comparator<Collection>() {
                                @Override
                                public int compare(Collection o1, Collection o2) {
                                    return o1.getCreateDate().compareTo(o2.getCreateDate());

                                }
                            });
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_new) {
            startActivity(SchemeActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getSelectSize(int size) {
        if (size > 0) {
            top_tool_layout.setVisibility(View.VISIBLE);
            title_layout.setVisibility(View.GONE);
            if (size > 1) {
                rename_btn.setEnabled(false);
                rename_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                edit_btn.setEnabled(false);
                edit_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                order_btn.setEnabled(false);
                order_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            } else {
                rename_btn.setEnabled(true);
                rename_btn.setTextColor(getResources().getColor(R.color.yellow));
                edit_btn.setEnabled(true);
                edit_btn.setTextColor(getResources().getColor(R.color.grass_green));
                order_btn.setEnabled(true);
                order_btn.setTextColor(getResources().getColor(R.color.purple));
            }

            if (size == 2) {
                engineering_btn.setEnabled(true);
                engineering_btn.setTextColor(getResources().getColor(R.color.light_blue));
            } else {
                engineering_btn.setEnabled(false);
                engineering_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            }

            if (size < 2 || size > 3) {
                compare_btn.setEnabled(false);
                compare_btn.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            } else {
                compare_btn.setEnabled(true);
                compare_btn.setTextColor(getResources().getColor(R.color.dark_red));
            }
        } else {
            top_tool_layout.setVisibility(View.GONE);
            title_layout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.price_tv)
    public void priceSort() {

        dateSort = DEFAULT;
        if (priceSort == DEFAULT || priceSort == ASC) {
            priceSort = DESC;
            Drawable image = getResources().getDrawable(R.drawable.down);
            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
            price_tv.setCompoundDrawables(null, null, image, null);
            Collections.sort(mDatas, new Comparator<Collection>() {
                @Override
                public int compare(Collection o1, Collection o2) {
                    return o2.getElevatorPrice() - o1.getElevatorPrice();
                }
            });
        } else if (priceSort == DESC) {
            priceSort = ASC;
            Drawable image = getResources().getDrawable(R.drawable.up);
            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
            price_tv.setCompoundDrawables(null, null, image, null);
            Collections.sort(mDatas, new Comparator<Collection>() {
                @Override
                public int compare(Collection o1, Collection o2) {
                    return o1.getElevatorPrice() - o2.getElevatorPrice();
                }
            });
        }

        mAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.new_btn)
    public void newAction(TextView textView) {
        startActivity(SchemeActivity.class);
    }


    @OnClick(R.id.date_tv)
    public void dateSort(TextView textView) {
        priceSort = DEFAULT;
        if (dateSort == DEFAULT || dateSort == ASC) {
            dateSort = DESC;
            Drawable image = getResources().getDrawable(R.drawable.down);
            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
            date_tv.setCompoundDrawables(null, null, image, null);
            Collections.sort(mDatas, new Comparator<Collection>() {
                @Override
                public int compare(Collection o1, Collection o2) {
                    return o2.getCreateDate().compareTo(o1.getCreateDate());
                }
            });
        } else if (dateSort == DESC) {
            dateSort = ASC;
            Drawable image = getResources().getDrawable(R.drawable.up);
            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
            date_tv.setCompoundDrawables(null, null, image, null);
            Collections.sort(mDatas, new Comparator<Collection>() {
                @Override
                public int compare(Collection o1, Collection o2) {
                    return o1.getCreateDate().compareTo(o2.getCreateDate());

                }
            });
        }

        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.engineering_btn)
    public void engineeringAction() {

        String ids = "";
        String lastEleType = "";
        for (int i = 0; i < mAdapter.getSelectList().size(); i++) {
            ids += mDatas.get(mAdapter.getSelectList().get(i)).getGuid();
            String eleType = mDatas.get(mAdapter.getSelectList().get(i)).getElevatorProduct() + mDatas.get(mAdapter.getSelectList().get(i)).isHaveHome();
            if (i != 0 && !eleType.equals(lastEleType)) {
                ToastUtil.showLong(getString(R.string.msg39));
                return;
            }
            lastEleType = eleType;
            if (i != mAdapter.getSelectList().size() - 1) {
                ids += ",";
            }
        }
        baseResponseObservable = HttpFactory.getApiService().compareCollection(ids);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<Programme>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Programme>>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Programme>> response) {

                        HashMap<String, String> allParam = new HashMap<>();
                        allParam.put("UserEmail", getUserEmail());
                        allParam.put("UserGuid", getUserId());
                        for (int i = 0; i < response.getData().size(); i++) {
                            Programme programmeData = response.getData().get(i);
                            HashMap<String, String> paramMap = ConvertUtil.convertParamValueMap(programmeData);
                            if (i == 0) {
                                allParam.putAll(paramMap);
                                allParam.put(CODE_SPECIALNONSTANDARD, programmeData.getSpecialNonStandard());
                                allParam.put("Price", String.valueOf(programmeData.getPrice()));
                                allParam.put(Constants.CODE_ELEVATORPRODUCT, programmeData.getElevatorProduct());
                                allParam.put(Constants.CODE_ELEVATORTYPE, programmeData.getElevatorType());
                            } else { //加前缀B_
                                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                                    allParam.put(B_ + entry.getKey(), entry.getValue());
                                }
                                allParam.put(B_ + CODE_SPECIALNONSTANDARD, programmeData.getSpecialNonStandard());

                            }
                        }

                        applyEngineering(allParam);
                    }
                });

    }

    private void applyEngineering(HashMap<String, String> allParam) {
        baseResponseObservable = HttpFactory.getApiService().applyDrawing(allParam);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        ToastUtil.showLong(getString(R.string.msg32));
                    }
                });
    }


    @OnClick(R.id.rename_btn)
    public void renameAction() {


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = getLayoutInflater();
        final View dialog_edit = inflater.inflate(R.layout.dialog_edit, null);
        builder.setView(dialog_edit).setTitle(R.string.rename)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_userName = (EditText) dialog_edit.findViewById(R.id.content_et);
                        if (TextUtils.isEmpty(et_userName.getText())) {
                            ToastUtil.showLong(getString(R.string.msg29));
                            return;
                        }
                        if (mAdapter.getSelectList().size() > 0) {
                            collection(mAdapter.getSelectList().get(0), et_userName.getText().toString());
                        }

                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    @OnClick(R.id.delete_btn)
    public void deleteAction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.delete_collection).setMessage(R.string.msg14)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mAdapter.getSelectList().size() > 0) {
                            deleteCollection();
                        }
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @OnClick(R.id.order_btn)
    public void orderAction() {
        String id = "";
        if (mAdapter.getSelectList().size() > 0) {
            Collection collection = mDatas.get(mAdapter.getSelectList().get(0));
            String elevatorProduct = collection.getElevatorProduct();
            boolean room = collection.isHaveHome();
            if (elevatorProduct == null || !elevatorProduct.equals("乘客电梯") || !room) {
                ToastUtil.showLong(getString(R.string.msg37));
                return;
            }
            id = collection.getGuid();
        }
        if (TextUtils.isEmpty(id)) {
            return;
        }
        baseResponseObservable = HttpFactory.getApiService().sendBomOrder(id);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        ToastUtil.showLong(getString(R.string.msg38));
                    }
                });
    }

    @OnClick(R.id.edit_btn)
    public void editAction() {
        String id = "";
        if (mAdapter.getSelectList().size() > 0) {
            id = mDatas.get(mAdapter.getSelectList().get(0)).getGuid();
        }
        if (TextUtils.isEmpty(id)) {
            return;
        }
        baseResponseObservable = HttpFactory.getApiService().getCollectionDetail(id);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<Programme>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Programme>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<Programme> response) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, SchemeDetailActivity.class);
                        Programme data = response.getData();
                        HashMap<String, String> paranMap = ConvertUtil.convertParamValueMap(data);
                        paranMap.put("Price", String.valueOf(data.getPrice()));
                        paranMap.put("CollectionName", mDatas.get(mAdapter.getSelectList().get(0)).getCollectionName());
                        paranMap.put("SpecialNonStandard", data.getSpecialNonStandard());
                        paranMap.put(Constants.CODE_ELEVATORTYPE, data.getElevatorType());
                        paranMap.put(Constants.CODE_ELEVATORPRODUCT, data.getElevatorProduct());
                        paranMap.put("PriceDrawingNo", data.getPriceDrawingNo());
                        paranMap.put("SalesParameterDrawingNo", data.getSalesParameterDrawingNo());
                        paranMap.put("SpecialNonStandardPath", data.getSpecialNonStandardPath());
                        paranMap.put("Guid", mDatas.get(mAdapter.getSelectList().get(0)).getGuid());
                        intent.putExtra("datas", paranMap);
                        intent.putExtra("edit_operation", true);
                        startActivityForResult(intent, 1001);

                    }
                });
    }

    @OnClick(R.id.compare_btn)
    public void compareAction() {


        String ids = "";
        String lastEleType = "";
        for (int i = 0; i < mAdapter.getSelectList().size(); i++) {
            ids += mDatas.get(mAdapter.getSelectList().get(i)).getGuid();
            String eleType = mDatas.get(mAdapter.getSelectList().get(i)).getElevatorProduct() + mDatas.get(mAdapter.getSelectList().get(i)).isHaveHome();
            if (i != 0 && !eleType.equals(lastEleType)) {
                ToastUtil.showLong(getString(R.string.msg39));
                return;
            }
            lastEleType = eleType;
            if (i != mAdapter.getSelectList().size() - 1) {
                ids += ",";
            }
        }
        baseResponseObservable = HttpFactory.getApiService().compareCollection(ids);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<Programme>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Programme>>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Programme>> response) {

                        Intent intent = new Intent();
                        intent.putExtra("datas", (Serializable) response.getData());
                        intent.putExtra("budget", "");
                        intent.putExtra("edit_operation", true);
                        startActivity(SchemePreviewActivity.class, intent);
                    }
                });


    }

    private void deleteCollection() {
        String guid = "";
        for (int i = 0; i < mAdapter.getSelectList().size(); i++) {
            int position = mAdapter.getSelectList().get(i);
            guid += mDatas.get(position).getGuid();
            if (i != mAdapter.getSelectList().size() - 1) {
                guid += ",";
            }
        }
        baseResponseObservable = HttpFactory.getApiService().deleteCollection(guid);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        List<Collection> collections = new ArrayList<>();
                        for (int i = 0; i < mAdapter.getSelectList().size(); i++) {
                            int position = mAdapter.getSelectList().get(i);
                            collections.add(mDatas.get(position));
                        }
                        mDatas.removeAll(collections);
                        mAdapter.clearSelect();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


    private void collection(final int position, final String name) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ISVERIFY", "0");
        jsonObject.addProperty("Guid", mDatas.get(position).getGuid());
        jsonObject.addProperty("CollectionName", name);
        bodyMap.put("params", RequestBody.create(MediaType.parse("text/plain"), jsonObject.toString()));
        baseResponseObservable = HttpFactory.getApiService().addOrUpdateCollection(bodyMap);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mDatas.get(position).setCollectionName(name);
                        mAdapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1001 && resultCode == 2) {
            loadData();
        }
    }
}
