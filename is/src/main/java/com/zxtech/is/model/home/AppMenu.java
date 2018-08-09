package com.zxtech.is.model.home;

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
        private int drawable;
        private String name;

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

        public Menu(int drawable, String name) {
            this.drawable = drawable;
            this.name = name;
        }
    }
}
