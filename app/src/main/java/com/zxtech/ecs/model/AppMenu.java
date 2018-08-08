package com.zxtech.ecs.model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syp523 on 2018/1/12.
 */

public class AppMenu {

    private String category;

    private List<Menu> subMenus = new ArrayList<>();


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Menu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }

    public static class Menu {
        //权限id
        private String menuId;
        private int drawable;
        private String name;
        private boolean isNeedUser;
        private boolean defaultMenu;
        private String routerUrl;
        private Class intentCalss;
        private String appMenuCategory;

        public int getDrawable() {
            return drawable;
        }

        public void setDrawable(int drawable) {
            this.drawable = drawable;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMenuId() {
            return menuId;
        }

        public void setMenuId(String menuId) {
            this.menuId = menuId;
        }

        public boolean isNeedUser() {
            return isNeedUser;
        }

        public void setNeedUser(boolean needUser) {
            isNeedUser = needUser;
        }

        public Class getIntentCalss() {
            return intentCalss;
        }

        public void setIntentCalss(Class intentCalss) {
            this.intentCalss = intentCalss;
        }

        public String getAppMenuCategory() {
            return appMenuCategory;
        }

        public void setAppMenuCategory(String appMenuCategory) {
            this.appMenuCategory = appMenuCategory;
        }

        public boolean isDefaultMenu() {
            return defaultMenu;
        }

        public void setDefaultMenu(boolean defaultMenu) {
            this.defaultMenu = defaultMenu;
        }

        public String getRouterUrl() {
            return routerUrl;
        }

        public void setRouterUrl(String routerUrl) {
            this.routerUrl = routerUrl;
        }

        public Menu(int drawable, String name) {
            this.drawable = drawable;
            this.name = name;
        }

        public Menu(String menuId, int drawable, String name, boolean isNeedUser,boolean defaultMenu,String routerUrl, Class intentCalss, String appMenuCategory) {
            this.menuId = menuId;
            this.drawable = drawable;
            this.name = name;
            this.isNeedUser = isNeedUser;
            this.defaultMenu = defaultMenu;
            this.routerUrl = routerUrl;
            this.intentCalss = intentCalss;
            this.appMenuCategory = appMenuCategory;
        }
    }
}
