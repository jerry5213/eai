package com.zxtech.is.ui.home.adpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.model.home.AppMenu;
import com.zxtech.is.ui.Engineer.activity.EngineerActivity;
import com.zxtech.is.ui.civilreview.activity.CivilReviewActivity;
import com.zxtech.is.ui.install.activity.InstallActivity;
import com.zxtech.is.ui.person.activity.PersonCheckActivity;
import com.zxtech.is.ui.project.activity.ProjectManageListActivity;
import com.zxtech.is.ui.task.activity.TaskListActivity;
import com.zxtech.is.ui.taskme.activity.TaskMeListActivity;
import com.zxtech.is.ui.team.activity.ElevatorTeamActivity;
import com.zxtech.is.ui.team.activity.ElevatorTeamTemplateActivity;
import com.zxtech.is.ui.transport.activity.TransportActivity;
import com.zxtech.is.util.DensityUtil;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.List;

/**
 * Created by syp523 on 2018/1/12.
 */

public class AppMenuAdapter extends CommonAdapter<AppMenu> {
//    private final static Class[][] subClass = new Class[][]{{CompanyActivity.class},
//            {PRListActivity.class, RecordApprovalActivity.class, CollectionActivity.class, WaitPPAListActivity.class, ProjectPriceApprovalListActivity.class, ContractReviewActivity.class},
//            {QmsMyWantConsultationActivity.class, QmsMyWantFeedBackActivity.class, QmsMyFeedBackActivity.class}};
    private final static Class[][] subClass = new Class[5][7];
    Point screenSize = null;
    int height = 0;

    public AppMenuAdapter(Context context, int layoutId, List<AppMenu> datas) {
        super(context, layoutId, datas);
        screenSize = DensityUtil.getScreenSize(context);

        subClass[3][0] = TaskMeListActivity.class;
        subClass[3][1] = EngineerActivity.class;
        subClass[3][2] = TransportActivity.class;
        subClass[3][3] = InstallActivity.class;
        subClass[3][4] = ProjectManageListActivity.class;
        subClass[3][5] = PersonCheckActivity.class;
        subClass[3][6] = ElevatorTeamTemplateActivity.class;


        //height = (screenSize.y - DensityUtil.dip2px(mContext, 215)) / 4+500;
    }

    @Override
    protected void convert(ViewHolder holder, AppMenu appMenu, final int p) {
//        LinearLayout group_layout = holder.getView(R.id.group_layout);
//        group_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
        holder.setText(R.id.category_tv, appMenu.getCategory());
        RecyclerView menu_rv = holder.getView(R.id.menu_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        menu_rv.setLayoutManager(gridLayoutManager);
        menu_rv.addItemDecoration(new MyItemDecoration(1));

        CommonAdapter<AppMenu.Menu> adapter = new CommonAdapter<AppMenu.Menu>(mContext, R.layout.item_homemenu, appMenu.getSubMenus()) {

            @Override
            protected void convert(ViewHolder holder, AppMenu.Menu menu, int position) {
                holder.setImageResource(R.id.menu_iv, menu.getDrawable());
                holder.setText(R.id.title_tv, menu.getName());
            }
        };
        menu_rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                if(p == 3){
                    intent.setClass(mContext, subClass[p][position]);
                    mContext.startActivity(intent);
                } else {
//                    ToastUtil.showLong("功能暂未开放");
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }
}
