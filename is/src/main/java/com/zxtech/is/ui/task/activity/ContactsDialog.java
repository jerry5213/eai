package com.zxtech.is.ui.task.activity;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.s1.ElevatorContact;
import com.zxtech.is.service.task.S1Service;
import com.zxtech.is.ui.task.adpter.ContactAdpter;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by syp600 on 2018/4/27.
 */

public class ContactsDialog extends DialogFragment implements View.OnClickListener {

    //页面标题
    TextView tv_is_address_title;
    //确定
    TextView tv_is_sure;
    //取消
    TextView tv_is_cancel;
    //已选梯号
    TextView tv_is_elevator_no;
    //参数
    private Map<String, String> params = new HashMap<>();

    private ContactAdpter contactAdpter;

    //联系人列表
    @BindView(R2.id.rv_is_contact)
    RecyclerView rv_is_contact;
    List<ElevatorContact> contactList = new ArrayList<>();

    //加
    ImageView iv_is_add;
    //减
    ImageView iv_is_minus;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_is_cancel) {
            closeDialog();

        } else if (id == R.id.tv_is_sure) {
            save();

        } else if (id == R.id.iv_is_add) {
            add();

        } else if (id == R.id.iv_is_minus) {
            minus();

        } else {
        }
    }

    public static ContactsDialog newInstance() {
        ContactsDialog fragment = new ContactsDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加这句话去掉自带的标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        View view = inflater.inflate(R.layout.dialog_contact, null);
        //页面标题
        tv_is_address_title = view.findViewById(R.id.tv_is_address_title);
        //确定
        tv_is_sure = view.findViewById(R.id.tv_is_sure);
        //取消
        tv_is_cancel = view.findViewById(R.id.tv_is_cancel);
        //已选梯号
        tv_is_elevator_no = view.findViewById(R.id.tv_is_elevator_no);
        //加
        iv_is_add = view.findViewById(R.id.iv_is_add);
        //减
        iv_is_minus = view.findViewById(R.id.iv_is_minus);

        rv_is_contact = view.findViewById(R.id.rv_is_contact);

        //联系人列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_is_contact.setLayoutManager(linearLayoutManager);
        rv_is_contact.addItemDecoration(new MyItemDecoration());
        contactAdpter = new ContactAdpter(R.layout.item_contact, contactList);
        contactAdpter.bindToRecyclerView(rv_is_contact);
        rv_is_contact.setAdapter(contactAdpter);


        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager m = getActivity().getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //params.gravity = Gravity.BOTTOM;
        params.width = (int) (d.getWidth() * 0.9);
//        params.height = (int) (d.getHeight() * 0.7);
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    private void initView() {
        /*********************设置监听开始*********************/
        //确定
        tv_is_sure.setOnClickListener(this);
        //取消
        tv_is_cancel.setOnClickListener(this);
        //加
        iv_is_add.setOnClickListener(this);
        //减
        iv_is_minus.setOnClickListener(this);
        /*********************设置监听结束*********************/

        tv_is_elevator_no.setText(params.get("elevatorNo"));

        //初始化时添加一个
        ElevatorContact contact = new ElevatorContact();
        contactList.add(contact);
        contactAdpter.notifyDataSetChanged();

    }

    protected void closeDialog() {
        this.dismiss();
    }

    public interface BackResult {
        void changeElevatorByContact(List<ElevatorContact> contactList);
    }

    private BackResult backResult;

    public BackResult getBackResult() {
        return backResult;
    }

    public void setBackResult(BackResult backResult) {
        this.backResult = backResult;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    protected void add() {
        //将页面中已经填写的数据添加到contactList中
        packDataToContactList();
        ElevatorContact contact = new ElevatorContact();
        contactList.add(contact);
        contactAdpter.notifyDataSetChanged();
    }

    protected void minus() {
        if (contactList.size() == 1) {
            ToastUtil.showLong(getResources().getString(R.string.is_delete_contact_msg));
        } else {
            //将页面中已经填写的数据添加到contactList中
            packDataToContactList();
            List<ElevatorContact> newContactList = new ArrayList<>();
            for (int i = 0; i < contactList.size(); i++) {
                boolean flag = contactList.get(i).isCheck();
                if (!flag) {
                    newContactList.add(contactList.get(i));
                }
            }
            if (newContactList.size() == 0) {
                newContactList.add(new ElevatorContact());
            }
            contactList.clear();
            contactList.addAll(newContactList);
            contactAdpter.notifyDataSetChanged();
        }
    }

    private void packDataToContactList() {
        int itemCount = contactAdpter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            CheckBox checkBox = (CheckBox) contactAdpter.getViewByPosition(i, R.id.cb_is_checkbox);
            EditText nameEditText = (EditText) contactAdpter.getViewByPosition(i, R.id.et_is_contact_name);
            EditText telephoneEditText = (EditText) contactAdpter.getViewByPosition(i, R.id.et_is_contact_telephone);
            EditText postEditText = (EditText) contactAdpter.getViewByPosition(i, R.id.et_is_contact_post);
            boolean flag = checkBox != null ? checkBox.isChecked() : false;
            String name = nameEditText != null ? nameEditText.getText().toString() : "";
            String telephone = telephoneEditText != null ? telephoneEditText.getText().toString() : "";
            String post = postEditText != null ? postEditText.getText().toString() : "";
            contactList.get(i).setCheck(flag);
            contactList.get(i).setName(name);
            contactList.get(i).setTelephone(telephone);
            contactList.get(i).setPost(post);
        }
    }

    protected void save() {
        packDataToContactList();
        if (checkData()) {
            Map<String, String> a = new HashMap<>();
            contactList.get(0).setElevatorguids(params.get("elevatorGuid"));
            contactList.get(0).setProcdefkeys(params.get("procDefKey"));
            contactList.get(0).setProcInstIds(params.get("procInstId"));
            String param = GsonUtils.toJson(contactList, false);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
            S1Service s1Service = HttpFactory.getService(S1Service.class);
            s1Service.saveElevatorContacts(requestBody).compose(RxHelper.<BaseResponse<Object>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<Object>>(getActivity(), true) {
                @Override
                public void onSuccess(BaseResponse<Object> response) {
                    if ("1".equals(response.getFlag())) {
                        backResult.changeElevatorByContact(contactList);
                        ToastUtil.showLong(getResources().getString(R.string.is_successfully_save));
                        closeDialog();
                    }
                }
            });
        }
    }

    private boolean checkData() {
        for (int i = 0; i < contactList.size(); i++) {
            String name = contactList.get(i).getName();
            if (name == null || "".equals(name.trim())) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_contact_name_msg));
                return false;
            }
            if (name.length() > 10) {
                ToastUtil.showLong(getResources().getString(R.string.is_name_max_length_msg));
                return false;
            }
            String telephone = contactList.get(i).getTelephone();
            if (telephone == null || "".equals(telephone.trim())) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_contact_telephone_msg));
                return false;
            }
            if (telephone.length() > 12) {
                ToastUtil.showLong(getResources().getString(R.string.is_telephone_max_length_msg));
                return false;
            }
            String post = contactList.get(i).getPost();
            if (post == null || "".equals(post.trim())) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_contact_post_msg));
                return false;
            }
            if (telephone.length() > 50) {
                ToastUtil.showLong(getResources().getString(R.string.is_post_max_length_msg));
                return false;
            }
        }
        return true;
    }
}
