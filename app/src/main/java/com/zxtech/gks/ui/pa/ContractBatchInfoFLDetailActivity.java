package com.zxtech.gks.ui.pa;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.format.draw.TextDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.bin.david.form.utils.DensityUtils;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.vo.PrProductDetail.PermissionListBean;
import com.zxtech.gks.model.vo.PrProductDetail.PriceTableListBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by SYP521 on 2017/11/10.
 */

public class ContractBatchInfoFLDetailActivity extends BaseActivity implements IActivity {

    @BindView(R.id.table)
    SmartTable smartTable;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<PriceTableListBean> contentList = new ArrayList<>();
    private PermissionListBean permissionListBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_track_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.batch_details));
        initData();
    }

    public void initData() {

        bindPermission();
        Intent intent = getIntent();
        contentList = (List<PriceTableListBean>)intent.getSerializableExtra(Constants.DATA1);
        permissionListBean = (PermissionListBean)intent.getSerializableExtra("PermissionListBean");

        if(contentList!=null && contentList.size()>0){
            contentList.remove(contentList.size()-1);
            drawTable(contentList);
        }
    }

    private Map<String,String> permissionMap;
    private void bindPermission() {

        permissionMap = new HashMap<>();
        permissionMap.put("FLCMIIRateEqui","colFLCMIIRateEqui");
        permissionMap.put("FLCMIIRate","colFLCMIIRate");
        permissionMap.put("FLCMII","colFLCMII");
        permissionMap.put("FCCMIIRateEqui","colFCCMIIRateEqui");
        permissionMap.put("CMIIRateInst","colCMIIRateInst");
        permissionMap.put("FCCMII","colFCCMII");
    }

    private void drawTable(final List<PriceTableListBean> datas){

        List<Column> list = new ArrayList<>();
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this, 12)); //设置全局字体大小

        Column<String> columnProductNo = new Column<>(getString(R.string.id), "ProductNo");
        Column<String> columnElevatorType = new Column<>(getString(R.string.product_main_parameters), "ElevatorType");
        columnElevatorType.setFixed(true);
        Column<String> columnProductCount = new Column<>(getString(R.string.quantity), "ProductCount");
        Column<String> columnRealPrice_Equi = new Column<>(getString(R.string.unit_price_of_equipment), "RealPrice_Equi");
        Column<String> columnRealPrice_Inst = new Column<>(getString(R.string.unit_price_of_installation), "RealPrice_Inst");
        Column<String> columnTotalPrice = new Column<>(getString(R.string.total_quotatoin), "TotalPrice");
        Column<String> columnFloatRateEqui = new Column<>(getString(R.string.ht_txt6), "FloatRateEqui");
        //Column<String> columnFLCMIIRateEqui = new Column<>("FL设备CMII%", "FLCMIIRateEqui");
        //Column<String> columnCMIIRateInst = new Column<>("FL安装CMII%", "CMIIRateInst");
        //Column<String> columnFLCMIIRate = new Column<>("FL TOTAL CMII%", "FLCMIIRate");
        //Column<String> columnFLCMII = new Column<>("FL TOTAL毛利金额(RMB)", "FLCMII");
        //Column<String> columnFCCMIIRateEqui = new Column<>("全链设备CMII%", "FCCMIIRateEqui");
        //Column<String> columnCMIIRateInst2 = new Column<>("全链安装CMII%", "CMIIRateInst");
        //Column<String> columnFCCMII = new Column<>("全链TOTAL毛利金额(RMB)", "FCCMII");
        Column<String> columnReserveMoneyDetail = new Column<>(getString(R.string.details_of_other_fees), "ReserveMoneyDetail");
        columnReserveMoneyDetail.setDrawFormat(new TextDrawFormat<String>(){
            @Override
            public void setTextPaint(TableConfig config, String s, Paint paint) {
                super.setTextPaint(config, s, paint);
                paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        columnReserveMoneyDetail.setOnColumnItemClickListener(new OnColumnItemClickListener<String>() {
            @Override
            public void onClick(Column<String> column, String s, String s2, int i) {
                Intent intent = new Intent(ContractBatchInfoFLDetailActivity.this,ReserveMoneyDetailActivity.class);
                intent.putExtra(Constants.DATA1,datas.get(i).getEQSProductGuid());
                startActivity(intent);
            }
        });
        Column<String> columnFbReview = new Column<>(getString(R.string.non_standard_review), "FbReview");
        columnFbReview.setDrawFormat(new TextDrawFormat<String>(){
            @Override
            public void setTextPaint(TableConfig config, String s, Paint paint) {
                super.setTextPaint(config, s, paint);
                paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        columnFbReview.setOnColumnItemClickListener(new OnColumnItemClickListener<String>() {
            @Override
            public void onClick(Column<String> column, String s, String s2, int i) {
                Intent intent = new Intent(ContractBatchInfoFLDetailActivity.this,FbReviewWayActivity.class);
                intent.putExtra(Constants.DATA1,datas.get(i).getEQSProductGuid());
                startActivity(intent);
            }
        });

        list.add(columnElevatorType);
        list.add(columnProductNo);
        list.add(columnProductCount);
        list.add(columnRealPrice_Equi);
        list.add(columnRealPrice_Inst);
        list.add(columnTotalPrice);
        list.add(columnFloatRateEqui);
        /*list.add(columnFLCMIIRateEqui);
        list.add(columnCMIIRateInst);
        list.add(columnFLCMIIRate);
        list.add(columnFLCMII);
        list.add(columnFCCMIIRateEqui);
        list.add(columnCMIIRateInst2);
        list.add(columnFCCMII);*/
        list.add(columnReserveMoneyDetail);
        list.add(columnFbReview);

        TableData<PriceTableListBean> tableData  = permissionCtrlViews(list,datas);

        tableData.setShowCount(false);
        smartTable.getConfig().setColumnTitleBackgroundColor(getResources().getColor(R.color.color_background_all_gray));
        smartTable.getConfig()
                .setShowYSequence(true)
                .setShowXSequence(false)
                .setShowTableTitle(false)
                .setColumnTitleStyle(new FontStyle(this, 12, getResources().getColor(R.color.default_text_grey_color)))
                .setContentStyle(new FontStyle(this, 12, getResources().getColor(R.color.default_text_grey_color)));
        smartTable.setTableData(tableData);
    }

    public TableData<PriceTableListBean> permissionCtrlViews(List<Column> list, List<PriceTableListBean> datas) {

        String[] group3 = {};

        if(!TextUtils.isEmpty(permissionListBean.getVisibleFunctionNoList())){
        }
        if(!TextUtils.isEmpty(permissionListBean.getDisabledFunctionNo())){
            //group2 = permissionListBean.getDisabledFunctionNo().split(",");
        }
        if(!TextUtils.isEmpty(permissionListBean.getHiddenFunctionNo())){
            group3 = permissionListBean.getHiddenFunctionNo().split(",");
        }

        List<Column> listTemp = new ArrayList<>();
        TableData<PriceTableListBean> tableData2 = new TableData<>("前线合同批次信息", datas);
        for(Column column:list){

            String fieldName = column.getFieldName();
            if(permissionMap.containsKey(fieldName)){

                String showId = permissionMap.get(fieldName);
                if(!Arrays.asList(group3).contains(showId)){
                    listTemp.add(column);
                }
            }else{
                listTemp.add(column);
            }
        }
        tableData2.setColumns(listTemp);
        return tableData2;
    }
}
