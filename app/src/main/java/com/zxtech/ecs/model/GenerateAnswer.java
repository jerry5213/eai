package com.zxtech.ecs.model;

import java.util.List;

/**
 * Created by syp521 on 2018/4/11.
 */

public class GenerateAnswer {

    /**
     * flag : true
     * total : 5
     * data : [{"question":"合格证附页增加两行：1、轿厢意外移动保护装置、型号、编号；2、层门、型号、编号。","answer":"合格证附页增加两行：1、轿厢意外移动保护装置、型号、编号；2、层门、型号、编号。"},{"question":"合格证附页给予更换","answer":"产品合格证附页上未体现轿厢意外移动铭牌编号，现场轿厢意外移动铭牌是有编号的，特检院要求合格证上要体现铭牌编号，请尽快处理。"},{"question":"合格证附页给予更换","answer":"产品合格证附页需增加两行：1、轿厢意外移动保护装置、型号、编号；2、层门、型号、编号。"},{"question":"合格证附页给予更换","answer":"该项目F8N4N789-92（四台）合格证附页上面没有&quot;轿厢意外移动保护装置&quot;型号和编号，请尽快处理！"},{"question":"合格证附页给予更换","answer":"该项目合格证上的上行超速保护装置跟现场配件上不符，请工厂更改补发！！"}]
     */

    private boolean flag;
    private int total;
    private List<DataBean> data;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * question : 合格证附页增加两行：1、轿厢意外移动保护装置、型号、编号；2、层门、型号、编号。
         * answer : 合格证附页增加两行：1、轿厢意外移动保护装置、型号、编号；2、层门、型号、编号。
         */

        private String question;
        private String answer;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}
