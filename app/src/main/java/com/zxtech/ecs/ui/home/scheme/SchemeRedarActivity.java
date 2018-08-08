package com.zxtech.ecs.ui.home.scheme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Ability;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.ecs.widget.SeekSpiderWeb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改五维
 * Created by syp523 on 2017/9/29.
 */

public class SchemeRedarActivity extends BaseActivity implements  SeekSpiderWeb.TouchUpListener, View.OnClickListener {

    private TextView parm1_ms, parm2_ms, parm3_ms, parm4_ms, parm5_ms;

    private List<Ability> abilityList = new ArrayList<>();


    private SeekSpiderWeb sw;

    private List<String> displayText = new ArrayList<>();


    private Button save_btn;

    private HashMap<String, String> data = new HashMap<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String budget;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scheme_redar;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initData();
        initTitleBar(toolbar, getString(R.string.edit_grade));
        parm1_ms = findViewById(R.id.parm1_ms);
        parm2_ms = findViewById(R.id.parm2_ms);
        parm3_ms = findViewById(R.id.parm3_ms);
        parm4_ms = findViewById(R.id.parm4_ms);
        parm5_ms = findViewById(R.id.parm5_ms);

        save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        sw = findViewById(R.id.sw);
        sw.setListener(this);
        int[] parms = new int[]{Integer.parseInt(this.data.get(Constants.DIMEN_SSD)) / 2, Integer.parseInt(this.data.get(Constants.DIMEN_JNDJ)) / 2, Integer.parseInt(this.data.get(Constants.DIMEN_MGD)) / 2, Integer.parseInt(this.data.get(Constants.DIMEN_CZFW)) / 2, Integer.parseInt(this.data.get(Constants.DIMEN_AQDJ)) / 2};
        sw.setData(5, parms, getResources().getStringArray(R.array.dimension_full));
        sw.setShowThumbDrawable(true);
        initSpinner();
    }


    private void initSpinner() {
        parm1_ms.setText(abilityList.get(Integer.parseInt(this.data.get(Constants.DIMEN_SSD)) / 2 - 1).getAbility());
        parm2_ms.setText(abilityList.get(Integer.parseInt(this.data.get(Constants.DIMEN_SSD)) / 2 - 1).getAbility());
        parm3_ms.setText(abilityList.get(Integer.parseInt(this.data.get(Constants.DIMEN_SSD)) / 2 - 1).getAbility());
        parm4_ms.setText(abilityList.get(Integer.parseInt(this.data.get(Constants.DIMEN_SSD)) / 2 - 1).getAbility());
        parm5_ms.setText(abilityList.get(Integer.parseInt(this.data.get(Constants.DIMEN_SSD)) / 2 - 1).getAbility());

    }

    private void initData() {
        this.data = (HashMap<String, String>) getIntent().getSerializableExtra("data");
        this.budget = getIntent().getStringExtra("budget");
        abilityList.add(new Ability("2", getResources().getString(R.string.low)));
        abilityList.add(new Ability("4", getResources().getString(R.string.low_medium)));
        abilityList.add(new Ability("6", getResources().getString(R.string.medium)));
        abilityList.add(new Ability("8", getResources().getString(R.string.medium_high)));
        abilityList.add(new Ability("10", getResources().getString(R.string.high)));

        for (Ability ab : abilityList) {
            displayText.add(ab.getAbility());
        }


    }

    @OnClick({R.id.save_btn, R.id.parm1_ms, R.id.parm2_ms, R.id.parm3_ms, R.id.parm4_ms, R.id.parm5_ms})
    public void onClick(View view) {
        if (view.getId() == R.id.save_btn) {
            data.put("budget", TextUtils.isEmpty(budget) ? "0" : budget); //0 作为参考
            baseResponseObservable = HttpFactory.getApiService().getSchemeParam(data);
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<Programme>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<Programme>>(this, true) {
                        @Override
                        public void onSuccess(BaseResponse<Programme> response) {

                            Intent intent = getIntent();
                            intent.putExtra("data", (Serializable) response.getData());
                            setResult(100, intent);
                            finish();
                        }
                    });

        } else {
            showWindows(view);
        }
    }

    private void showWindows(final View view) {
        DropDownWindow mWindow = new DropDownWindow(mContext, view, (TextView) view, abilityList, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                Ability ability = abilityList.get(p);
                ((TextView)view).setText(ability.getAbility());
                switch (view.getId()) {
                    case R.id.parm1_ms:
                        data.put(Constants.DIMEN_SSD, ability.getId());
                        break;
                    case R.id.parm2_ms:
                        data.put(Constants.DIMEN_JNDJ, ability.getId());
                        break;
                    case R.id.parm3_ms:
                        data.put(Constants.DIMEN_MGD, ability.getId());
                        break;
                    case R.id.parm4_ms:
                        data.put(Constants.DIMEN_CZFW, ability.getId());
                        break;
                    case R.id.parm5_ms:
                        data.put(Constants.DIMEN_AQDJ, ability.getId());
                        break;
                }
                int[] parms = new int[]{Integer.parseInt(data.get(Constants.DIMEN_SSD)) / 2, Integer.parseInt(data.get(Constants.DIMEN_JNDJ)) / 2, Integer.parseInt(data.get(Constants.DIMEN_MGD)) / 2, Integer.parseInt(data.get(Constants.DIMEN_CZFW)) / 2, Integer.parseInt(data.get(Constants.DIMEN_AQDJ)) / 2};
                sw.setData(5, parms, getResources().getStringArray(R.array.dimension_full));
                sw.refresh();

            }
        };
    }



    @Override
    public void onProgressValue(int ability_id, int val) {
        String progressValue = String.valueOf((val + 1) * 2);
        switch (ability_id) {
            case 0:
                data.put(Constants.DIMEN_SSD, progressValue);
                parm1_ms.setText(abilityList.get(val).getAbility());
                break;
            case 1:
                data.put(Constants.DIMEN_JNDJ, progressValue);
                parm2_ms.setText(abilityList.get(val).getAbility());
                break;
            case 2:
                data.put(Constants.DIMEN_MGD, progressValue);
                parm3_ms.setText(abilityList.get(val).getAbility());
                break;
            case 3:
                data.put(Constants.DIMEN_CZFW, progressValue);
                parm4_ms.setText(abilityList.get(val).getAbility());
                break;
            case 4:
                data.put(Constants.DIMEN_AQDJ, progressValue);
                parm5_ms.setText(abilityList.get(val).getAbility());
                break;

        }
    }

}
