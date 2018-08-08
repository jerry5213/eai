package com.zxtech.ecs.ui.home.payment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.DropDown;
import com.zxtech.ecs.model.PayNode;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.JavaUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.MyItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp521 on 2018/7/11.
 */

public class AllotInfoFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.money_allot_et)
    EditText money_allot_et;
    @BindView(R.id.allot_rate_et)
    EditText allot_rate_et;
    @BindView(R.id.select_all)
    ImageView select_all;
    @BindView(R.id.money_iv)
    ImageView money_iv;
    @BindView(R.id.rate_iv)
    ImageView rate_iv;
    @BindView(R.id.curr_allot_money_tv)
    TextView curr_allot_money_tv;
    @BindView(R.id.usable_tv)
    TextView usable_tv;
    @BindView(R.id.payment_remark_tv)
    TextView payment_remark_tv;

    private List<DropDown> paymentRemarkDownList = new ArrayList<>();
    private List<PayNode> mDatas = new ArrayList<>();
    private List<PayNode> oldDatas = new ArrayList<>();
    private MyAdapter adapter;
    private double haveAllotMoney = 0;
    private String payNodeValue = "";

    private String orderNumber;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_allot_info;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MyAdapter(mContext,R.layout.item_allot_payment,mDatas);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setAdapter(adapter);

        orderNumber = getArguments().getString("orderNumber");
    }

    public static AllotInfoFragment newInstance(String accountGuid,String orderNumber){

        Bundle args = new Bundle();
        AllotInfoFragment fragment = new AllotInfoFragment();
        args.putString("accountGuid",accountGuid);
        args.putString("orderNumber",orderNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public List<Map> getDicInfoList(){

        List<Map> mapList = new ArrayList<>();
        for(PayNode payNode:mDatas){
            if(!TextUtils.isEmpty(payNode.getRealMoney()) && Double.parseDouble(payNode.getRealMoney())>0){
                Map map = new HashMap();
                map.put("PayNodeGuid",payNode.getGuid());
                map.put("AllotMoney",payNode.getRealMoney());
                mapList.add(map);
            }
        }
        return mapList;
    }

    public String getPayNode() {
        return payNodeValue;
    }

    public String getAllotMoney() {
        return curr_allot_money_tv.getText().toString();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initContactType();
        if(!TextUtils.isEmpty(payNodeValue)){
            initData();
        }
    }


    private boolean isAllSelect = false;

    @OnClick({R.id.select_all,R.id.allot_btn,R.id.payment_remark_tv,R.id.money_iv,R.id.rate_iv})
    public void Onclick(final View view){

        switch (view.getId()){

            case R.id.select_all:
                if(!isAllSelect){
                    adapter.selectAll(mDatas);
                    isAllSelect = true;
                    select_all.setImageResource(R.drawable.match_check);
                }else {
                    adapter.clearPositions();
                    isAllSelect = false;
                    select_all.setImageResource(R.drawable.match);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.allot_btn:
                haveAllotMoney = 0;
                String money = money_allot_et.getText().toString();
                if(!TextUtils.isEmpty(money) && adapter.getPositions().size()>0){
                    haveAllotMoney = 0;
                    for(int a: adapter.getPositions()){
                        mDatas.get(a).setRealMoney(money);
                    }
                    for(PayNode payNode:mDatas){
                        double moneyD = Double.parseDouble(payNode.getRealMoney());
                        haveAllotMoney+=moneyD;
                    }
                    adapter.notifyDataSetChanged();
                }
                String rate = allot_rate_et.getText().toString();
                if(!TextUtils.isEmpty(rate) && adapter.getPositions().size()>0){
                    haveAllotMoney = 0;
                    for(int a: adapter.getPositions()){
                        double singlePrice = Double.parseDouble(mDatas.get(a).getContractMoney());
                        double rateD = Double.parseDouble(rate);
                        mDatas.get(a).setRealMoney((singlePrice*rateD/100)+"");
                        //haveAllotMoney+=(singlePrice*rateD/100);
                    }
                    for(PayNode payNode:mDatas){
                        double moneyD = Double.parseDouble(payNode.getRealMoney());
                        haveAllotMoney+=moneyD;
                    }
                    adapter.notifyDataSetChanged();
                }
                if(TextUtils.isEmpty(money) && TextUtils.isEmpty(rate)){
                    ToastUtil.showLong(getString(R.string.msg55));
                    return;
                }
                String usableMoney = usable_tv.getText().toString();
                double usableMoneyD = Double.parseDouble(usableMoney);
                if(haveAllotMoney>usableMoneyD){
                    ToastUtil.showLong(getString(R.string.msg56));
                    ((AccountPaymentDetailActivity)getActivity()).save_btn.setEnabled(false);
                    //doBack();
                }else{
                    curr_allot_money_tv.setText(String.format("%.2f",haveAllotMoney)+"");
                    ((AccountPaymentDetailActivity)getActivity()).save_btn.setEnabled(true);
                }
                break;
            case R.id.payment_remark_tv:
                new DropDownWindow(mContext, view, (TextView) view, paymentRemarkDownList, view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        ((TextView) view).setText(paymentRemarkDownList.get(p).getText());
                        view.setTag(paymentRemarkDownList.get(p).getValue());
                        payNodeValue = paymentRemarkDownList.get(p).getValue();
                        initData();
                    }
                };
                break;
            case R.id.money_iv:
                allot_rate_et.setEnabled(false);
                allot_rate_et.setText("");
                money_allot_et.setEnabled(true);
                money_iv.setImageResource(R.drawable.match_check);
                rate_iv.setImageResource(R.drawable.match);
                //doBack();
                break;
            case R.id.rate_iv:
                allot_rate_et.setEnabled(true);
                money_allot_et.setEnabled(false);
                money_allot_et.setText("");
                money_iv.setImageResource(R.drawable.match);
                rate_iv.setImageResource(R.drawable.match_check);
                //doBack();
                break;
        }
    }

    /**
     * 账款分配出现异常时回滚
     */
    public void doBack(){

        try {
            curr_allot_money_tv.setText("");
            mDatas.clear();
            mDatas.addAll(JavaUtil.deepCopyList(oldDatas));
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onEvent(Bundle bundle){

        String value = bundle.getString("restMoney");
        payNodeValue = bundle.getString("invoiceAttribution");
        usable_tv.setText(value);
        if(!TextUtils.isEmpty(payNodeValue)){
            payment_remark_tv.setEnabled(false);
            payment_remark_tv.setTextColor(getResources().getColor(R.color.default_text_grey_color));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void initData(){

        String accountGuid = getArguments().getString("accountGuid");

        Map paramMap = new HashMap();
        paramMap.put("orderNumber",orderNumber==null?"":orderNumber);
        paramMap.put("payNode",payNodeValue);
        paramMap.put("accountGuid",accountGuid);

        baseResponseObservable = HttpFactory.getApiService().getPayNodeList(paramMap);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<PayNode>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<PayNode>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<PayNode>> response) {

                        adapter.clearPositions();
                        curr_allot_money_tv.setText("");
                        select_all.setImageResource(R.drawable.match);
                        isAllSelect = false;
                        mDatas.clear();
                        mDatas.addAll(response.getData());
                        try {
                            oldDatas = JavaUtil.deepCopyList(mDatas);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    public void initContactType(){

        baseResponseObservable = HttpFactory.getApiService().getSelectList(30);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<DropDown>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<DropDown>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<DropDown>> response) {
                        paymentRemarkDownList = response.getData();
                        for (DropDown dropDown:paymentRemarkDownList){
                            if(dropDown.getValue().equals(payNodeValue)){
                                payment_remark_tv.setText(dropDown.getText());
                            }
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    public class MyAdapter extends CommonAdapter<PayNode> {


        private List<Integer> positions = new ArrayList<>();

        public MyAdapter(Context context, int layoutId, List<PayNode> datas) {
            super(context, layoutId, datas);
        }

        public List<Integer> getPositions() {
            return positions;
        }

        public void selectAll(List data){

            positions.clear();
            for(int i = 0;i<data.size();i++){
                positions.add(i);
            }
        }

        public void clearPositions(){
            positions.clear();
        }

        @Override
        public void convert(ViewHolder holder, PayNode payNode, final int position) {

            holder.setText(R.id.itemNo_tv,payNode.getItem());
            holder.setText(R.id.elevator_number_tv,payNode.getElevatorNo());
            holder.setText(R.id.wbs_tv,payNode.getWBS());
            holder.setText(R.id.one_contract_price_tv,payNode.getContractMoney());
            holder.setText(R.id.plan_amount_collected_tv,payNode.getReceiveMoney());
            holder.setText(R.id.plan_rate_collected_tv,payNode.getReceivePercent());
            holder.setText(R.id.allot_money_tv,String.format("%.2f",TextUtils.isEmpty(payNode.getRealMoney())?0:Double.parseDouble(payNode.getRealMoney())));
            holder.setText(R.id.total_allot_money_tv,
                    String.format("%.2f",TextUtils.isEmpty(payNode.getRealMoneyTotal())?0:Double.parseDouble(payNode.getRealMoneyTotal())));

            if(positions.contains(position)){
                holder.setImageResource(R.id.select_iv,R.drawable.match_check);
            }else{
                holder.setImageResource(R.id.select_iv,R.drawable.match);
            }

            final ImageView selectIv = holder.getView(R.id.select_iv);
            selectIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(positions.contains(position)){
                        positions.remove(Integer.valueOf(position));
                        selectIv.setImageResource(R.drawable.match);
                    }else{
                        positions.add(position);
                        selectIv.setImageResource(R.drawable.match_check);
                    }
                    if(positions.size()<getDatas().size()){
                        select_all.setImageResource(R.drawable.match);
                    }else{
                        select_all.setImageResource(R.drawable.match_check);
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
}
