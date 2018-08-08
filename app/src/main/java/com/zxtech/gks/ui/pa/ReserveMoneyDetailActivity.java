package com.zxtech.gks.ui.pa;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.bean.ReserveMoneyDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp521 on 2018/2/23.
 */

public class ReserveMoneyDetailActivity extends BaseActivity implements IActivity {

    @BindView(R.id.table)
    SmartTable smartTable;
    @BindView(R.id.tv_eq)
    TextView tv_eq;
    @BindView(R.id.tv_ins)
    TextView tv_ins;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String guid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reserve_money_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.details_of_other_fees));
        initData();
    }

    public void initData() {

        guid = getIntent().getStringExtra(Constants.DATA1);
        getServerData("1");
    }

    public void getServerData(String type){

        Map params = new HashMap();
        params.put("guid", guid);
        params.put("priceType", type);

        baseResponseObservable = HttpFactory.getApiService().getPriceTypeGenList(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<List<ReserveMoneyDetail>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<List<ReserveMoneyDetail>>>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse<List<ReserveMoneyDetail>> response) {

                        drawTable(response.getData());
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        ToastUtil.showLong("获取数据失败");
                        finish();
                    }
                });

    }

    private void drawTable(List<ReserveMoneyDetail> datas) {

        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this, 14)); //设置全局字体大小
        Column<String> columnPriceName = new Column<>(getString(R.string.cost_name), "PriceName");
        Column<String> columnPrimeCost = new Column<>(getString(R.string.estimated_cost), "PrimeCost");
        Column<String> columnSalePrice = new Column<>(getString(R.string.sales_price), "SalePrice");

        //表格数据 datas是需要填充的数据
        TableData<ReserveMoneyDetail> tableData = new TableData<>(
                "预览费明细", datas, columnPriceName, columnPrimeCost,columnSalePrice);
        tableData.setShowCount(false);
        smartTable.getConfig().setColumnTitleBackgroundColor(getResources().getColor(R.color.color_background_all_gray));
        smartTable.getConfig()
                .setShowYSequence(false)
                .setShowXSequence(false)
                .setShowTableTitle(false)
                .setColumnTitleStyle(new FontStyle(this, 12, getResources().getColor(R.color.default_text_grey_color)))
                .setContentStyle(new FontStyle(this, 12, getResources().getColor(R.color.default_text_grey_color)));
        smartTable.setTableData(tableData);
    }

    @OnClick({R.id.tv_eq,R.id.tv_ins})
    public void viewClick(View view){

        switch (view.getId()){

            case R.id.tv_eq:
                getServerData("1");
                tv_eq.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_ins.setTextColor(getResources().getColor(R.color.default_text_black_color));
                break;
            case R.id.tv_ins:
                getServerData("2");
                tv_ins.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_eq.setTextColor(getResources().getColor(R.color.default_text_black_color));
                break;
        }
    }
}
