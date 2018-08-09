package com.zxtech.esp.data.bean;

import java.util.List;

/**
 * Created by syp521 on 2017/7/20.
 */

public class BbsType extends BaseVO<List<BbsType.Data>>{

    public static class Data{

        public Data(int id,String type_name, String QUERYID, String create_user) {
            this.type_name = type_name;
            this.QUERYID = QUERYID;
            this.create_user = create_user;
            this.id = id;
        }

        private String type_name;
        private String type_name_en;
        private String QUERYID;
        private String create_user;
        private int id;

        public String getType_name() {
            return type_name;
        }

        public String getQUERYID() {
            return QUERYID;
        }

        public String getCreate_user() {
            return create_user;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType_name_en() {
            return type_name_en;
        }
    }
}
