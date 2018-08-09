package com.zxtech.esp.data.bean;

import java.util.List;

/**
 * Created by SYP521 on 2017/7/3.
 */

public class CourseTypeVO extends BaseVO<List<CourseTypeVO.Data>> {

    public static class Data {
        private List<ChildsBean> childs;
        private String parent_id;
        private String parent_name;
        private String parent_name_en;

        public String getParent_id() {
            return parent_id;
        }

        public String getParent_name() {
            return parent_name;
        }

        public List<ChildsBean> getChilds() {
            return childs;
        }

        public String getParent_name_en() {
            return parent_name_en;
        }
    }

    public static class ChildsBean {
        private String child_id;
        private String child_name;
        private String child_name_en;
        private String image;
        private String image_url;
        private int isFree;
        private int grantFlag;

        public String getChild_id() {
            return child_id;
        }

        public String getChild_name() {
            return child_name;
        }

        public String getChild_name_en() {
            return child_name_en;
        }

        public String getImage() {
            return image;
        }

        public String getImage_url() {
            return image_url;
        }

        public int getIsFree() {
            return isFree;
        }

        public int getGrantFlag() {
            return grantFlag;
        }
    }
}
