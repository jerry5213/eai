package com.zxtech.esp.data.bean;

import java.util.List;

/**
 * Created by syp521 on 2017/7/20.
 */

public class BbsPostReplyVO extends BaseVO<List<BbsPostReplyVO.Data>>{

    public static class Data{

        /**
         * owner_name : test
         * target_name : test
         * photo_name : headerphoto_1500533071700.png
         * owner_user_id : e8ee61fe-9360-41f5-9620-592eb680f179
         * parent_id : 82608945768946429D4ED8B7F921F6CF
         * id : D80EA34633FE4684A4B1D92492C012F0
         * target_user_id : e8ee61fe-9360-41f5-9620-592eb680f179
         * photo_url : userPhoto/headerphoto_1500533071700.png
         * create_date : 2017-07-19 18:29:41
         * content : 3333
         */

        private String owner_name;
        private String target_name;
        private String photo_name;
        private String owner_user_id;
        private String parent_id;
        private String id;
        private String target_user_id;
        private String photo_url;
        private String create_date;
        private String content;

        public String getOwner_name() {
            return owner_name;
        }

        public void setOwner_name(String owner_name) {
            this.owner_name = owner_name;
        }

        public String getTarget_name() {
            return target_name;
        }

        public void setTarget_name(String target_name) {
            this.target_name = target_name;
        }

        public String getPhoto_name() {
            return photo_name;
        }

        public void setPhoto_name(String photo_name) {
            this.photo_name = photo_name;
        }

        public String getOwner_user_id() {
            return owner_user_id;
        }

        public void setOwner_user_id(String owner_user_id) {
            this.owner_user_id = owner_user_id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTarget_user_id() {
            return target_user_id;
        }

        public void setTarget_user_id(String target_user_id) {
            this.target_user_id = target_user_id;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
