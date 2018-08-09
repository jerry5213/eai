package com.zxtech.mt.entity;

import java.util.List;

/**
 * Created by syp523 on 2017/8/15.
 */

public class AccessoryShopCategory {

    private String id;

    private String name;

    private String level;

    private List<AccessoryShopCategoryChild> second_class;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<AccessoryShopCategoryChild> getSecond_class() {
        return second_class;
    }

    public void setSecond_class(List<AccessoryShopCategoryChild> second_class) {
        this.second_class = second_class;
    }

    public class AccessoryShopCategoryChild {

        private String id;

        private String name;

        private String level;

        private String parent_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }
}


