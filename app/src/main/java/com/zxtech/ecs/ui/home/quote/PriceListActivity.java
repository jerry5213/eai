package com.zxtech.ecs.ui.home.quote;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ToolAdapter;
import com.zxtech.ecs.model.QuotePrice;
import com.zxtech.ecs.model.ToolBean;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.ItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by SYP521 on 2018/4/5.
 */

public class PriceListActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleView_tool)
    RecyclerView recycleViewTool;
    @BindView(R.id.recycleView_content)
    RecyclerView recycleViewContent;
    @BindViews({R.id.count1, R.id.count2, R.id.count3, R.id.count4, R.id.count5, R.id.count6, R.id.count7, R.id.count8, R.id.count9, R.id.count10, R.id.count11, R.id.count12, R.id.count13, R.id.count14, R.id.count15, R.id.count16})
    TextView totalTvs[];

    private ToolAdapter toolAdapter;
    private ContentAdapter contentAdapter;
    private List<ToolBean> toolBeanLists = new ArrayList<>();
    private String[] toolText;
    private String projectGuid;
    private List<QuotePrice> mDatas = new ArrayList<>();
    private List<QuotePrice> beforeEditDatas = new ArrayList<>();
    @BindView(R.id.equi_total_tv)
    TextView equi_total_tv;
    @BindView(R.id.inst_total_tv)
    TextView inst_total_tv;
    @BindView(R.id.edit_layout)
    LinearLayout edit_layout;

    private boolean isEdit;
    private TotalPrice currentTotalPrice = new TotalPrice();

    private String[] editTitles;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_price_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.price_list));

        projectGuid = getIntent().getStringExtra("projectGuid");

        toolText = new String[]{
                getString(R.string.product_details),
                getString(R.string.details_of_installation_fee),
                getString(R.string.applied_discount),
                getString(R.string.details_of_other_fees),
                getString(R.string.retrodict_of_other_fees)
        };

        editTitles = new String[]{
                getString(R.string.actual_equipment_price),
                getString(R.string.actual_installation_price),
                getString(R.string.adjustable_rate_of_equipment_price),
                getString(R.string.adjustable_rate_of_installation_price),
                getString(R.string.total_price_of_equipment_contract),
                getString(R.string.total_price_of_installation_contract)
        };

        int[] colorfulArr = getResources().getIntArray(R.array.colorful);
        for (int a = 0; a < colorfulArr.length - 3; a++) {
            ToolBean toolBean = new ToolBean(toolText[a], colorfulArr[a]);
            toolBeanLists.add(toolBean);
        }

        toolAdapter = new ToolAdapter(R.layout.item_corner_radius_textview, toolBeanLists);
        recycleViewTool.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleViewTool.addItemDecoration(new ItemDivider().setDividerWith(20).setDividerColor(Color.TRANSPARENT));
        recycleViewTool.setAdapter(toolAdapter);

        recycleViewContent.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        contentAdapter = new ContentAdapter(R.layout.item_quote_price, mDatas);
        recycleViewContent.setAdapter(contentAdapter);

        toolAdapter.setOnItemClickListener(this);
        contentAdapter.setOnItemChildClickListener(this);

        initData();
    }

    private void initData() {

        baseResponseObservable = HttpFactory.getApiService().getPriceTableList(projectGuid, getUserNo());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<QuotePrice>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<QuotePrice>>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<List<QuotePrice>> response) {
                        //最后一项记录为 合计
                        List<QuotePrice> data = response.getData();
                        if (data != null && data.size() > 0) {

                            data.remove(data.size() - 1);
                            mDatas.addAll(data);
                            initTotalPrice();
                            contentAdapter.notifyDataSetChanged();
                        }

                    }

                });
    }


    //计算合计价格
    private void initTotalPrice() {
        int sumStdPrice_Equi = 0;
        int sumStdPrice_Inst = 0;
        int sumEquiDiscountPrice = 0;
        int sumInstDiscountPrice = 0;
        int sumFreight = 0;
        int sumInspectionFeeForSale = 0;
        int sumFreePremium = 0;
        int sumEquiObligatePriceForSale = 0;
        int sumInstOtherPriceForSale = 0;
        int sumEquiRealPrice = 0;
        int sumInstRealPrice = 0;
        Float sumFloatRate_Equi = 0f;
        Float sumFloatRate_Inst = 0f;
        int productCount = 0;
        int sumEquiRealTotalPrice = 0;
        int sumInstRealTotalPrice = 0;

        for (int i = 0; i < mDatas.size(); i++) {
            QuotePrice quotePrice = mDatas.get(i);
            if (quotePrice.getStdPrice_Equi() != null) {
                sumStdPrice_Equi += Integer.valueOf(quotePrice.getStdPrice_Equi());
            }
            if (quotePrice.getStdPrice_Inst() != null) {
                sumStdPrice_Inst += Integer.valueOf(quotePrice.getStdPrice_Inst());
            }
            if (quotePrice.getEquiDiscountPrice() != null) {
                sumEquiDiscountPrice += Integer.valueOf(quotePrice.getEquiDiscountPrice());
            }
            if (quotePrice.getInstDiscountPrice() != null) {
                sumInstDiscountPrice += Integer.valueOf(quotePrice.getInstDiscountPrice());
            }
            if (quotePrice.getFreight() != null) {
                sumFreight += Integer.valueOf(quotePrice.getFreight());
            }
            if (quotePrice.getInspectionFeeForSale() != null) {
                sumInspectionFeeForSale += Integer.valueOf(quotePrice.getInspectionFeeForSale());
            }
            if (quotePrice.getFreePremium() != null) {
                sumFreePremium += Integer.valueOf(quotePrice.getFreePremium());
            }
            if (quotePrice.getEquiObligatePriceForSale() != null) {
                sumEquiObligatePriceForSale += Integer.valueOf(quotePrice.getEquiObligatePriceForSale());
            }
            if (quotePrice.getInstOtherPriceForSale() != null) {
                sumInstOtherPriceForSale += Integer.valueOf(quotePrice.getInstOtherPriceForSale());
            }

            sumEquiRealPrice += quotePrice.getEquiRealPrice();

            sumInstRealPrice += quotePrice.getInstRealPrice();

            if (quotePrice.getFloatRate_Equi() != null) {
                sumFloatRate_Equi += Float.valueOf(quotePrice.getFloatRate_Equi());
            }
            if (quotePrice.getFloatRate_Inst() != null) {
                sumFloatRate_Inst += Float.valueOf(quotePrice.getFloatRate_Inst());
            }

            sumEquiRealTotalPrice += quotePrice.getEquiRealPrice() * quotePrice.getProductCount();
            sumInstRealTotalPrice += quotePrice.getInstRealPrice() * quotePrice.getProductCount();
            productCount += quotePrice.getProductCount();
        }

        totalTvs[0].setText(String.valueOf(productCount));
        totalTvs[1].setText(Util.numberFormat(sumStdPrice_Equi));
        totalTvs[2].setText(Util.numberFormat(sumStdPrice_Inst));
        totalTvs[3].setText(Util.numberFormat(sumEquiDiscountPrice));
        totalTvs[4].setText(Util.numberFormat(sumInstDiscountPrice));
        totalTvs[5].setText(Util.numberFormat(sumFreight));
        totalTvs[6].setText(Util.numberFormat(sumInspectionFeeForSale));
        totalTvs[7].setText(Util.numberFormat(sumFreePremium));
        totalTvs[8].setText(Util.numberFormat(sumEquiObligatePriceForSale));
        totalTvs[9].setText(Util.numberFormat(sumInstOtherPriceForSale));
        totalTvs[10].setText(Util.numberFormat(sumEquiRealPrice));
        totalTvs[11].setText(Util.numberFormat(sumInstRealPrice));
        totalTvs[12].setText(Util.numberFormat(sumEquiRealTotalPrice));
        totalTvs[13].setText(Util.numberFormat(sumInstRealTotalPrice));
        //totalTvs[14].setText(String.valueOf(sumFloatRate_Equi));
       // totalTvs[15].setText(String.valueOf(sumFloatRate_Inst));

        equi_total_tv.setText(Util.numberFormat(sumEquiRealTotalPrice));
        inst_total_tv.setText(Util.numberFormat(sumInstRealTotalPrice));

        currentTotalPrice.setEquiContractTotal(sumEquiRealTotalPrice);
        currentTotalPrice.setInstContractTotal(sumInstRealTotalPrice);
        currentTotalPrice.setEquiUnitPrice(sumEquiRealPrice);
        currentTotalPrice.setInstUnitPrice(sumInstRealPrice);

    }

    @OnClick({R.id.complete_btn, R.id.cancel_btn, R.id.equi_total_tv, R.id.inst_total_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.complete_btn:
                completeEdit();
                break;
            case R.id.cancel_btn:
                cancelEdit();
                contentAdapter.notifyDataSetChanged();
                break;
            case R.id.equi_total_tv:
                if (isEdit) {
                    showEditwindow(4, view, -1);
                }
                break;
            case R.id.inst_total_tv:
                if (isEdit) {
                    showEditwindow(5, view, -2);
                }
                break;
        }
    }

    private void completeEdit() {
        JsonArray array = new JsonArray();
        for (int i = 0; i < mDatas.size(); i++) {
            QuotePrice quotePrice = mDatas.get(i);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("guid",quotePrice.getGuid());
            jsonObject.addProperty("deviceDiscount",quotePrice.getFloatRate_Equi());
            jsonObject.addProperty("buildDiscount",quotePrice.getFloatRate_Inst());
            array.add(jsonObject);
        }

        baseResponseObservable = HttpFactory.getApiService().updateDiscount(array.toString());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        ToastUtil.showLong("保存成功");
                        toolAdapter.clearSelectedPosition();

                        equi_total_tv.setCompoundDrawables(null, null, null, null);
                        inst_total_tv.setCompoundDrawables(null, null, null, null);
                        edit_layout.setVisibility(View.GONE);
                        isEdit = false;
                        contentAdapter.notifyDataSetChanged();
                    }

                });

    }

    private void cancelEdit() {
        toolAdapter.clearSelectedPosition();

        equi_total_tv.setCompoundDrawables(null, null, null, null);
        inst_total_tv.setCompoundDrawables(null, null, null, null);
        edit_layout.setVisibility(View.GONE);
        isEdit = false;
        mDatas.clear();
        this.mDatas.addAll(beforeEditDatas);
        initTotalPrice();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        switch (position) {
            case 0:
                if (contentAdapter.getSelectList().size() == 1) {
                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                    intent.putExtra("guid", mDatas.get(contentAdapter.getSelectList().get(0)).getGuid());
                    startActivity(intent);
                } else {
                    ToastUtil.showLong(getString(R.string.toast1));
                }
                break;
            case 1:
                if (contentAdapter.getSelectList().size() == 1) {
                    Intent intent = new Intent(mContext, InstallationFeeActivity.class);
                    intent.putExtra("EQSProductGuid", mDatas.get(contentAdapter.getSelectList().get(0)).getEQS_ProductGuid());
                    startActivity(intent);
                } else {
                    ToastUtil.showLong(getString(R.string.toast1));
                }
                break;

            case 2:

                if (!isEdit) {
                    toolAdapter.setSelectedPosition(position);
                    edit_layout.setVisibility(View.VISIBLE);
                    Drawable editDrw = getResources().getDrawable(R.drawable.edit);
                    editDrw.setBounds(0, 0, editDrw.getMinimumWidth(), editDrw.getMinimumHeight());
                    equi_total_tv.setCompoundDrawables(null, null, editDrw, null);
                    inst_total_tv.setCompoundDrawables(null, null, editDrw, null);

                    isEdit = true;
                    this.beforeEditDatas.clear();
                    for (QuotePrice quotePrice : this.mDatas) {
                        try {
                            this.beforeEditDatas.add(quotePrice.clone());
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    cancelEdit();
                }

                contentAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (!isEdit) return;
        switch (view.getId()) {
            case R.id.column12:
                showEditwindow(0, view, position);
                break;
            case R.id.column13:
                showEditwindow(1, view, position);
                break;
            case R.id.column16:
                showEditwindow(2, view, position);
                break;
            case R.id.column17:
                showEditwindow(3, view, position);
                break;
        }
    }

    private void showEditwindow(final int flagPosition, final View view, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = getLayoutInflater();
        final View dialog_edit = inflater.inflate(R.layout.dialog_edit, null);
        final EditText et_userName = dialog_edit.findViewById(R.id.content_et);
        et_userName.setHint(((TextView) view).getText());
        et_userName.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(dialog_edit).setTitle(editTitles[flagPosition])
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = et_userName.getText().toString();
                        if (TextUtils.isEmpty(text)) {
                            return;
                        }

                        ((TextView) view).setText(text);

                        if (position < 0) { //修改总价
                            int newPrice = Integer.valueOf(text);
                            int oldPrice = 0;
                            if (position == -1) {
                                oldPrice = currentTotalPrice.getEquiContractTotal();
                            } else if (position == -2) {
                                oldPrice = currentTotalPrice.getInstContractTotal();
                            }
                            int diffPrice = newPrice - oldPrice;
                            for (int i = 0; i < mDatas.size(); i++) {
                                QuotePrice item = mDatas.get(i);
                                if (position == -1) {
                                    int diffRealPrice = (-diffPrice * item.getEquiRealPrice()) / (currentTotalPrice.getEquiUnitPrice()*item.getProductCount());
                                    item.setEquiRealPrice(String.valueOf(item.getEquiRealPrice() - diffRealPrice));
                                } else {
                                    int diffRealPrice = (-diffPrice * item.getInstRealPrice()) / (currentTotalPrice.getInstUnitPrice()*item.getProductCount());
                                    item.setInstRealPrice(String.valueOf(item.getInstRealPrice() - diffRealPrice));
                                }

                            }
                            contentAdapter.notifyDataSetChanged();

                        } else {
                            if (flagPosition == 0) {
                                mDatas.get(position).setEquiRealPrice(text);
                            } else if (flagPosition == 1) {
                                mDatas.get(position).setInstRealPrice(text);
                            } else if (flagPosition == 2) {
                                mDatas.get(position).setEquiFloatRate(text);
                            } else if (flagPosition == 3) {
                                mDatas.get(position).setInstFloatRate(text);
                            }
                            contentAdapter.notifyItemChanged(position);
                        }

                        initTotalPrice();

                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }



    class ContentAdapter extends BaseQuickAdapter<QuotePrice, BaseViewHolder> {

        private List<Integer> selectList = new ArrayList<>();
        private Drawable match = getResources().getDrawable(R.drawable.match);
        private Drawable match_check = getResources().getDrawable(R.drawable.match_check);
        private Drawable edit = getResources().getDrawable(R.drawable.edit);

        public ContentAdapter(int layoutResId, @Nullable List<QuotePrice> data) {
            super(layoutResId, data);
            match.setBounds(0, 0, match.getMinimumWidth(), match.getMinimumHeight());
            match_check.setBounds(0, 0, match_check.getMinimumWidth(), match_check.getMinimumHeight());
            edit.setBounds(0, 0, edit.getMinimumWidth(), edit.getMinimumHeight());
        }

        @Override
        protected void convert(final BaseViewHolder helper, QuotePrice item) {
            final TextView column0 = helper.getView(R.id.column0);
            column0.setText(getString(R.string.product) + (helper.getAdapterPosition() + 1));
            helper.setText(R.id.column1, item.getEQS_ProductNo()); //询价编号
            helper.setText(R.id.column2, String.valueOf(item.getProductCount()));//数量
            helper.setText(R.id.column3, Util.numberFormat(item.getStdPrice_Equi()));//设备标准价
            helper.setText(R.id.column4, Util.numberFormat(item.getStdPrice_Inst()));//安装标准价
            helper.setText(R.id.column5, Util.numberFormat(item.getEquiDiscountPrice()));//折后设备价 = 设备标准价 * 设备浮动率，结果取整
            helper.setText(R.id.column6, Util.numberFormat(item.getInstDiscountPrice()));//折后安装费 = 安装标准价 * 安装浮动率，结果取整
            helper.setText(R.id.column7, Util.numberFormat(item.getFreight()));//运输费
            helper.setText(R.id.column8, Util.numberFormat(item.getInspectionFeeForSale()));//验收费
            helper.setText(R.id.column9, Util.numberFormat(item.getFreePremium()));//免维保 = 设备免保费 + 安装免保费
            helper.setText(R.id.column10, Util.numberFormat(item.getEquiObligatePriceForSale()));//设备预留费
            helper.setText(R.id.column11, Util.numberFormat(item.getInstOtherPriceForSale()));//安装其他费
            final TextView column12 = helper.getView(R.id.column12);
            column12.setText(Util.numberFormat(item.getEquiRealPrice()));//设备实际单价 = 折后设备价 + 运输费 + 免保费 + 设备预留费，结果精确到十位
            final TextView column13 = helper.getView(R.id.column13);
            column13.setText(Util.numberFormat(item.getInstRealPrice()));//安装实际单价 = 折后安装费 + 验收费(含开工费) + 安装其他费，结果精确到十位

            helper.setText(R.id.column14, Util.numberFormat(item.getEquiRealPrice() * item.getProductCount()));//设备实际总价
            helper.setText(R.id.column15, Util.numberFormat(item.getInstRealPrice() * item.getProductCount()));//安装实际总价
            final TextView column16 = helper.getView(R.id.column16);
            column16.setText(item.getFloatRate_Equi());//设备浮动率
            final TextView column17 = helper.getView(R.id.column17);
            column17.setText(item.getFloatRate_Inst());//安装浮动率
            helper.addOnClickListener(R.id.column12).addOnClickListener(R.id.column13).addOnClickListener(R.id.column16).addOnClickListener(R.id.column17);


            if (selectList.contains(helper.getAdapterPosition())) {
                column0.setCompoundDrawables(null, null, match_check, null);
            } else {
                column0.setCompoundDrawables(null, null, match, null);
            }

            if (isEdit) {
                column12.setCompoundDrawables(null, null, edit, null);
                column13.setCompoundDrawables(null, null, edit, null);
                column16.setCompoundDrawables(null, null, edit, null);
                column17.setCompoundDrawables(null, null, edit, null);
            } else {
                column12.setCompoundDrawables(null, null, null, null);
                column13.setCompoundDrawables(null, null, null, null);
                column16.setCompoundDrawables(null, null, null, null);
                column17.setCompoundDrawables(null, null, null, null);
            }

            column0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectList.contains(helper.getAdapterPosition())) {
                        selectList.remove(Integer.valueOf(helper.getAdapterPosition()));
                        column0.setCompoundDrawables(null, null, match, null);
                    } else {
                        selectList.add(helper.getAdapterPosition());
                        column0.setCompoundDrawables(null, null, match_check, null);
                    }
                }
            });

        }

        public List<Integer> getSelectList() {
            return selectList;
        }

        public void setSelectList(List<Integer> selectList) {
            this.selectList = selectList;
        }
    }



    class TotalPrice {
        private int equiContractTotal = 0;
        private int instContractTotal = 0;
        private int equiUnitPrice = 0;
        private int instUnitPrice = 0;


        public int getEquiContractTotal() {
            return equiContractTotal;
        }

        public void setEquiContractTotal(int equiContractTotal) {
            this.equiContractTotal = equiContractTotal;
        }

        public int getInstContractTotal() {
            return instContractTotal;
        }

        public void setInstContractTotal(int instContractTotal) {
            this.instContractTotal = instContractTotal;
        }

        public int getEquiUnitPrice() {
            return equiUnitPrice;
        }

        public void setEquiUnitPrice(int equiUnitPrice) {
            this.equiUnitPrice = equiUnitPrice;
        }

        public int getInstUnitPrice() {
            return instUnitPrice;
        }

        public void setInstUnitPrice(int instUnitPrice) {
            this.instUnitPrice = instUnitPrice;
        }

        public TotalPrice() {
        }

        public TotalPrice(int equiContractTotal, int instContractTotal, int equiUnitPrice, int instUnitPrice) {
            this.equiContractTotal = equiContractTotal;
            this.instContractTotal = instContractTotal;
            this.equiUnitPrice = equiUnitPrice;
            this.instUnitPrice = instUnitPrice;
        }
    }
}
