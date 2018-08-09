package com.zxtech.esp.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SYP521 on 2017/7/4.
 */

public class CourseVO extends BaseVO<List<CourseVO.Data>>{

    public static class Data implements Serializable{

        private String course_id;
        private String course_name;
        private String course_desc;
        private String create_time;
        private String file_name;
        private String file_url;
        private String image_name;
        private String image_url;
        private String comment_num;
        private String praise_num;
        private String credits;
        private String learningtime;
        private String is_praise;
        private String big_image_url;

        public String getCourse_id() {
            return course_id;
        }

        public String getCourse_name() {
            return course_name;
        }

        public String getCourse_desc() {
            return course_desc;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getFile_name() {
            return file_name;
        }

        public String getFile_url() {
            return file_url;
        }

        public String getImage_name() {
            return image_name;
        }

        public String getImage_url() {
            return image_url;
        }

        public String getComment_num() {
            return comment_num;
        }

        public String getPraise_num() {
            return praise_num;
        }

        public String getCredits() {
            return credits;
        }

        public String getLearningtime() {
            return learningtime;
        }

        public String getIs_praise() {
            return is_praise;
        }

        public String getBig_image_url() {
            return big_image_url;
        }
    }
}
