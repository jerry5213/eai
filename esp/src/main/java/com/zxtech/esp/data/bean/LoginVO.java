package com.zxtech.esp.data.bean;

/**
 * Created by SYP521 on 2017/6/29.
 */

public class LoginVO extends BaseVO<LoginVO.Data> {
    public static class Data {
        private String userId;
        private String userName;
        private String logincode;
        private String token;

        private int version_code;
        private int must_update;
        private String filename;
        private String fileurl;

        private String nick_name;
        private String photo_url;
        private String departId;

        public String getDepartId() {
            return departId;
        }

        public void setDepartId(String departId) {
            this.departId = departId;
        }

        public String getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getLogincode() {
            return logincode;
        }

        public String getToken() {
            return token;
        }

        public int getVersion_code() {
            return version_code;
        }

        public int getMust_update() {
            return must_update;
        }

        public String getFilename() {
            return filename;
        }

        public String getFileurl() {
            return fileurl;
        }

        public String getNick_name() {
            return nick_name;
        }

        public String getPhoto_url() {
            return photo_url;
        }
    }
}
