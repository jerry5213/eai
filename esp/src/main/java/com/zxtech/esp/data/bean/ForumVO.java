package com.zxtech.esp.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SYP521 on 2017/7/19.
 */

public class ForumVO extends BaseVO<List<ForumVO.Data>>{

    public static class Data implements Serializable{

        /**
         * type_name : 帮助
         * subject :
         * reply_num : 0
         * look_num : 0
         * id : 2AB715556D464120985E332B55D2950C
         * type : 1
         * body : 内容：
         * create_date : 17-07-19 11:44:02
         */

        private String type_name;
        private String type_name_en;
        private String subject;
        private int reply_num;
        private int look_num;
        private String id;
        private int type;
        private String body;
        private String create_date;
        private List<Images> images;
        private String post_user_url;
        private String post_user;

        public String getType_name() {
            return type_name;
        }

        public String getType_name_en() {
            return type_name_en;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getReply_num() {
            return reply_num;
        }

        public void setReply_num(int reply_num) {
            this.reply_num = reply_num;
        }

        public int getLook_num() {
            return look_num;
        }

        public void setLook_num(int look_num) {
            this.look_num = look_num;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public List<Images> getImages() {
            return images;
        }

        public String getPost_user_url() {
            return post_user_url;
        }

        public String getPost_user() {
            return post_user;
        }
    }

    public static class Images implements Serializable{

        private String QUERYID;
        private String post_id;
        private String photo_name;
        private String enable_flag;
        private String photo_url;
        private String id;

        public String getQUERYID() {
            return QUERYID;
        }

        public void setQUERYID(String QUERYID) {
            this.QUERYID = QUERYID;
        }

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public String getPhoto_name() {
            return photo_name;
        }

        public void setPhoto_name(String photo_name) {
            this.photo_name = photo_name;
        }

        public String getEnable_flag() {
            return enable_flag;
        }

        public void setEnable_flag(String enable_flag) {
            this.enable_flag = enable_flag;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
