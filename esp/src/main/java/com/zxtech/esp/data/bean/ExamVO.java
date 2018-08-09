package com.zxtech.esp.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SYP521 on 2017/7/27.
 */

public class ExamVO extends BaseVO<ExamVO.Data>{

    public static class Data implements Serializable {

        private int noExamNum;
            private int examedNum;
        private List<ExamInfo> data;

        public int getNoExamNum() {
            return noExamNum;
        }

        public int getExamedNum() {
            return examedNum;
        }

        public List<ExamInfo> getExamInfos() {
            return data;
        }
    }

    public static class ExamInfo implements Serializable{

        /**
         * score : 16
         * examtime : 30
         * flag : now
         * examkhzl : 电梯作业
         * examname : 检验员考试
         * continuetime : 30
         * examid : 5071a24d-565a-44ab-9b87-c194481abc3c
         * endtime : 2017/08/17
         * showpoint : 1
         * starttime : 2017/08/16
         */

        private int score;
        private int examtime;
        private String flag;
        private String examkhzl;
        private String examname;
        private int continuetime;
        private String examid;
        private String endtime;
        private int showpoint;
        private String starttime;
        private String paperid;
        private int examscore;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getExamtime() {
            return examtime;
        }

        public void setExamtime(int examtime) {
            this.examtime = examtime;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getExamkhzl() {
            return examkhzl;
        }

        public void setExamkhzl(String examkhzl) {
            this.examkhzl = examkhzl;
        }

        public String getExamname() {
            return examname;
        }

        public void setExamname(String examname) {
            this.examname = examname;
        }

        public int getContinuetime() {
            return continuetime;
        }

        public void setContinuetime(int continuetime) {
            this.continuetime = continuetime;
        }

        public String getExamid() {
            return examid;
        }

        public void setExamid(String examid) {
            this.examid = examid;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public int getShowpoint() {
            return showpoint;
        }

        public void setShowpoint(int showpoint) {
            this.showpoint = showpoint;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getPaperid() {
            return paperid;
        }

        public int getExamscore() {
            return examscore;
        }
    }
}
