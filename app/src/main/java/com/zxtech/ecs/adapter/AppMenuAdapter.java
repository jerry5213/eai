package com.zxtech.ecs.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.AppMenu;
import com.zxtech.ecs.ui.login.LoginActivity;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;

import java.util.List;

/**
 * Created by syp523 on 2018/1/12.
 */

public class AppMenuAdapter extends CommonAdapter<AppMenu> {

    public AppMenuAdapter(Context context, int layoutId, List<AppMenu> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final AppMenu appMenu, final int p) {
        holder.setText(R.id.category_tv, appMenu.getCategory());
        RecyclerView menu_rv = holder.getView(R.id.menu_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        menu_rv.setLayoutManager(gridLayoutManager);
        //menu_rv.addItemDecoration(new MyItemDecoration(30));
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
                List<AppMenu.Menu> subMenus = appMenu.getSubMenus();
                AppMenu.Menu menu = subMenus.get(position);
                if (menu.getIntentCalss() == null && menu.getRouterUrl() == null) {
                    ToastUtil.showLong(mContext.getString(R.string.msg7));
                } else {
                    String user_id = (String) SPUtils.get(mContext, "user_id", "");
                    if (menu.isNeedUser() && TextUtils.isEmpty(user_id)) {
                        intent.setClass(mContext, LoginActivity.class);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
                    } else {


                        if (menu.getRouterUrl() != null) {
                            ARouter.getInstance().build(menu.getRouterUrl())
                                    .withInt("position", position)
                                    .navigation();
                        }else {
                            intent.setClass(mContext, menu.getIntentCalss());
                            intent.putExtra("position", position);
                            mContext.startActivity(intent);
                        }

                    }

                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }
}
