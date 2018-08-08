package com.zxtech.ecs.ui.home.qmsmanager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.JsonEntity;
import com.zxtech.ecs.model.QmsMaterialRequirement;
import com.zxtech.ecs.model.SystemCodeListBean;
import com.zxtech.ecs.model.SystemCodeListEntity;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.GsonUtils;
import com.zxtech.ecs.widget.DropDownWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zxtech.ecs.common.Constants.ELEVATOR;
import static com.zxtech.ecs.util.Util.convertQmsLanguage;
import static com.zxtech.ecs.util.Util.hideSoftInput;

/**
 * Created by syp521 on 2018/3/29.
 */

public class QmsMREditActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_qms_question_code)
    TextView mTvQmsQuestionCode;
    @BindView(R.id.tv_qms_unit)
    TextView mTvQmsUnit;
    @BindView(R.id.et_qms_piece_no)
    EditText et_qms_piece_no;
    @BindView(R.id.et_qms_name)
    EditText et_qms_name;
    @BindView(R.id.et_qms_number)
    EditText et_qms_number;

    private List<SystemCodeListBean> mQuestionCodeLists = new ArrayList<>();
    private List<SystemCodeListBean> mUnitLists = new ArrayList<>();
    private String qmsLan;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qms_mr_edit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(mToolbar);
        //隐藏软键盘
        hideSoftInput(this);
        qmsLan = convertQmsLanguage(language);
        initQuestionCode();
        initUnit();
    }

    private void initQuestionCode(){

        //将参数转为json格式
        JsonEntity jsonEntity = new JsonEntity("WTDMCode", qmsLan, "", "", ELEVATOR);
        String params = GsonUtils.toJson(jsonEntity, false);

        HttpFactory.getApiService()
                .getSystemCode(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mQuestionCodeLists = response.getData().getSystemCodeList();
                        }
                    }
                });
    }

    protected void dropDownQuestionCode(View view) {

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mTvQmsQuestionCode.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, mQuestionCodeLists, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                mTvQmsQuestionCode.setText(mQuestionCodeLists.get(p).getValue());
                mTvQmsQuestionCode.setTag(mQuestionCodeLists.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mTvQmsQuestionCode.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    private void initUnit(){

        //将参数转为json格式
        JsonEntity jsonEntity = new JsonEntity("DWCode", qmsLan, "", "", ELEVATOR);
        String params = GsonUtils.toJson(jsonEntity, false);

        HttpFactory.getApiService()
                .getSystemCode(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mUnitLists = response.getData().getSystemCodeList();
                        }
                    }
                });
    }

    protected void dropDownUnit(View view) {

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mTvQmsUnit.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, mUnitLists, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                mTvQmsUnit.setText(mUnitLists.get(p).getValue());
                mTvQmsUnit.setTag(mUnitLists.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mTvQmsUnit.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    @OnClick({R.id.tv_qms_question_code,R.id.tv_qms_unit,R.id.tv_save})
    void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_qms_question_code:
                dropDownQuestionCode(v);
                break;
            case R.id.tv_qms_unit:
                dropDownUnit(v);
                break;
            case R.id.tv_save:
                save();
                break;
        }
    }

    private void save(){

        String param1 = et_qms_piece_no.getText().toString();
        String param2 = et_qms_name.getText().toString();
        String param3 = mTvQmsQuestionCode.getTag().toString();
        String param31 = mTvQmsQuestionCode.getText().toString();
        String param4 = et_qms_number.getText().toString();
        String param5 = mTvQmsUnit.getTag().toString();
        String param51 = mTvQmsUnit.getText().toString();
        QmsMaterialRequirement qmsMr = new QmsMaterialRequirement(param1,param2,param3,param31,param4,param5,param51);
        Intent intent = getIntent();
        intent.putExtra("qmsMr",qmsMr);
        setResult(1,intent);
        finish();
    }
}
