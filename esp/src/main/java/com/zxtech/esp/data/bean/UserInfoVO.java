package com.zxtech.esp.data.bean;

import java.io.Serializable;

/**
 * Created by SYP521 on 2017/7/18.
 */

public class UserInfoVO extends BaseVO<UserInfoVO.Data>{

    public static class Data implements Serializable {

        private String photo_name;
        private String name;
        private String rank;
        private String photo_url;
        private String exam;

        public String getPhoto_name() {
            return photo_name;
        }

        public void setPhoto_name(String photo_name) {
            this.photo_name = photo_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }

        public String getExam() {
            return exam;
        }
    }
}
