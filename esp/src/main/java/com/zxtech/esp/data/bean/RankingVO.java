package com.zxtech.esp.data.bean;

import java.util.List;

/**
 * Created by SYP521 on 2017/7/14.
 */

public class RankingVO extends BaseVO<List<RankingVO.Data>>{

    public static class Data {

        private String photo_name;
        private String nick_name;
        private double total_score;
        private String id;
        private String photo_url;
        private long update_date;

        public String getPhoto_name() {
            return photo_name;
        }

        public void setPhoto_name(String photo_name) {
            this.photo_name = photo_name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public double getTotal_score() {
            return total_score;
        }

        public void setTotal_score(int total_score) {
            this.total_score = total_score;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }

        public long getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(long update_date) {
            this.update_date = update_date;
        }
    }
}
