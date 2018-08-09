package com.zxtech.esp.data.bean;

import java.util.List;

/**
 * Created by syp521 on 2018/4/20.
 */

public class Face {

    /**
     * result : [{"uid":"wangbaoqiang","scores":[97.783889770508],"group_id":"user_group1","user_info":""}]
     * log_id : 3468164126042016
     * result_num : 1
     * ext_info : {"faceliveness":"0.00040283144335262"}
     */

    private long log_id;
    private int result_num;
    private ExtInfoBean ext_info;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public ExtInfoBean getExt_info() {
        return ext_info;
    }

    public void setExt_info(ExtInfoBean ext_info) {
        this.ext_info = ext_info;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ExtInfoBean {
        /**
         * faceliveness : 0.00040283144335262
         */

        private String faceliveness;

        public String getFaceliveness() {
            return faceliveness;
        }

        public void setFaceliveness(String faceliveness) {
            this.faceliveness = faceliveness;
        }
    }

    public static class ResultBean {
        /**
         * uid : wangbaoqiang
         * scores : [97.783889770508]
         * group_id : user_group1
         * user_info :
         */

        private String uid;
        private String group_id;
        private String user_info;
        private List<Double> scores;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getUser_info() {
            return user_info;
        }

        public void setUser_info(String user_info) {
            this.user_info = user_info;
        }

        public List<Double> getScores() {
            return scores;
        }

        public void setScores(List<Double> scores) {
            this.scores = scores;
        }
    }
}
