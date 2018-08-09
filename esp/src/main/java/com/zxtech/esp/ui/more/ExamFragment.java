package com.zxtech.esp.ui.more;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zxtech.common.download.SimpleDialog;
import com.zxtech.common.util.T;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.ExamVO;
import com.zxtech.esp.util.GsonCallBack;

import java.util.List;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/7/27.
 */

public class ExamFragment extends Fragment {

    private View rootView;
    private ListView lv;
    private MyExamAdapter adapter;
    private int reviewStatus;

    private MyExamActivity parentActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (MyExamActivity) getActivity();
    }

    public ExamFragment(){}

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_exam, container, false);
            setup(rootView);
        }
        return T.getNoParentView(rootView);
    }

    private void setup(View rootView) {

        lv = (ListView) rootView.findViewById(R.id.lv);
        lv.setEmptyView(rootView.findViewById(R.id.tv_no_item));
        adapter = new MyExamAdapter();
        lv.setAdapter(adapter);
        initData();

        final String title = getResources().getString(R.string.tip);
        final String desc = getResources().getString(R.string.if_exam);
        final String ok = getResources().getString(R.string.upgrade_dialog_ok);
        final String cancel = getResources().getString(R.string.upgrade_dialog_cancel);
        final String expired = getResources().getString(R.string.expired);
        final String not_started = getResources().getString(R.string.not_started);

        if(reviewStatus == 0){
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                    AlertDialog dialog = SimpleDialog.createDialog(view.getContext(),title,desc,ok,cancel,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ExamVO.ExamInfo examInfo = adapter.getItem(position);
                            String flag = examInfo.getFlag();
                            if("now".equals(flag)){

                                /*Intent intent = new Intent(view.getContext(),ExamFaceContrastActivity.class);
                                intent.putExtra(C.DATA, examInfo);
                                intent.putExtra("reviewStatus", reviewStatus);
                                parentActivity.startActivity(intent);*/

                                Intent intent = new Intent(view.getContext(), ExamAnswerActivity.class);
                                intent.putExtra(C.DATA, examInfo);
                                intent.putExtra("visitor", "exam");
                                intent.putExtra("reviewStatus", reviewStatus);
                                startActivity(intent);

                            }else if("after".equals(flag)){
                                T.showToast(view.getContext(),expired);
                            }else if("before".equals(flag)){
                                T.showToast(view.getContext(),not_started);
                            }
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }, false);
                    dialog.show();
                }
            });
        }else if (reviewStatus == 1){

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                    ExamVO.ExamInfo examInfo = adapter.getItem(position);
                    Intent intent = new Intent(view.getContext(),ExamAnswerActivity.class);
                    intent.putExtra(C.DATA,examInfo);
                    intent.putExtra("visitor","resolve");
                    intent.putExtra("reviewStatus",reviewStatus);
                    parentActivity.startActivityForResult(intent,1);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (parentActivity.getRefreshItemFlag(reviewStatus)){
            parentActivity.setRefreshItemFlag(reviewStatus,false);
            initData();
        }
    }

    private void initData(){

        GsonCallBack<ExamVO> callBack = new GsonCallBack<ExamVO>(getActivity()){
            @Override
            public void onStart() {
                showLoading(getActivity());
            }

            @Override
            protected void onDoSuccess(ExamVO bean) {
                dismissLoading();
                List<ExamVO.ExamInfo> datas = bean.getData().getExamInfos();
                adapter.setDatas(datas);
                adapter.setReviewStatus(reviewStatus);
                adapter.notifyDataSetChanged();
            }
        };
        String url = URL.getInstance().getExamInfoUrl();
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("loginUserId", MyApp.getUserId());
        params.addBodyParameter("reviewStatus",reviewStatus+"");
        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }
}
