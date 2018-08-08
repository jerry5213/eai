package com.zxtech.ecs.ui.home.escscheme;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.EscCollectionAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Collection;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.scheme.SchemeActivity;
import com.zxtech.ecs.ui.home.scheme.SchemePreviewActivity;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.gks.common.SPUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 扶梯收藏列表
 * Created by syp523 on 2017/12/27.
 */

public class EscCollectionActivity extends BaseActivity implements EscCollectionAdapter.OnclickCallBack {
    private EscCollectionAdapter mAdapter;

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
    private int selectedPosition = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_esc_collection;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.favorites_list));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new EscCollectionAdapter(mContext, R.layout.item_esc_collection, mDatas);
        mAdapter.setListener(this);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        baseResponseObservable = HttpFactory.getApiService().getCollectionList(getUserId(), Constants.ESCALATOR);
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
            startActivity(EscalatorSchemeActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.new_btn)
    public void newAction() {
        startActivity(EscalatorSchemeActivity.class);
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
            } else {
                rename_btn.setEnabled(true);
                rename_btn.setTextColor(getResources().getColor(R.color.yellow));
                edit_btn.setEnabled(true);
                edit_btn.setTextColor(getResources().getColor(R.color.grass_green));
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
    public void priceSort(TextView textView) {
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
        AlertDialog dialog = builder.show();

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

    @OnClick(R.id.edit_btn)
    public void editAction() {
        String id = "";
        if (mAdapter.getSelectList().size() > 0) {
            id = mDatas.get(mAdapter.getSelectList().get(0)).getGuid();
        }
        if (TextUtils.isEmpty(id)) {
            return;
        }
        selectedPosition = mAdapter.getSelectList().get(0);
        baseResponseObservable = HttpFactory.getApiService().getEscCollectionDetail(id);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<Map<String, String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, String>>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, String>> response) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, EscalatorSchemeActivity.class);
                        Map<String, String> paranMap = response.getData();
                        intent.putExtra("data", (Serializable) paranMap);
                        startActivityForResult(intent, 1001);

                    }
                });
    }

    @OnClick(R.id.compare_btn)
    public void compareAction() {


        String ids = "";
        for (int i = 0; i < mAdapter.getSelectList().size(); i++) {
            ids += mDatas.get(mAdapter.getSelectList().get(i)).getGuid();
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
