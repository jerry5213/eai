package com.zxtech.esp.data.bean;

/**
 * Created by SYP521 on 2017/8/18.
 */

public class ExamResultVO extends BaseVO<ExamResultVO.Data>{

    public static class Data{

        private String errorNum;
        private String rightNum;
        private int total;

        public String getErrorNum() {
            return errorNum;
        }

        public String getRightNum() {
            return rightNum;
        }

        public int getTotal() {
            return total;
        }
    }
}
