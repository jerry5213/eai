package com.zxtech.esp.data.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by SYP521 on 2017/7/31.
 */

public class QuestionsVO extends BaseVO<List<QuestionsVO.Data>>{

    public static class Data implements Serializable {

        /**
         * questionContent : 曳引电梯采用带切口半圆绳槽时，要减小钢丝绳在曳引轮绳槽中的比压，可采取的改进措施是 。
         * questionId : 0162f428-85b7-4957-a636-0d86c0bcfb17
         * userAnswer :
         * problemState : 0
         * data : [{"optionContent":"减小曳引绳速","optionKey":"A"},{"optionContent":"改善曳引轮的材质","optionKey":"B"},{"optionContent":"减小切口角β","optionKey":"C"},{"optionContent":"增大切口角β","optionKey":"D"}]
         * typeName : 单选题
         * questionNum : 1
         * questionScore : 3
         * typeId : T00000000000002
         */

        private String questionContent;
        private String questionId;
        private String userAnswer;
        private int problemState;
        private String typeName;
        private int questionNum;
        private int questionScore;
        private String typeId;
        private int choose = -1;
        private Map<String,Boolean> chooseMap = new TreeMap<String, Boolean>();
        private List<Question> data;
        private String answer;
        private String remarks;
        private String showAnswer;

        public int getChoose() {
            return choose;
        }

        public void setChoose(int choose) {
            this.choose = choose;
        }

        public Map<String, Boolean> getChooseMap() {
            return chooseMap;
        }

        public String getQuestionContent() {
            return questionContent;
        }

        public String getQuestionId() {
            return questionId;
        }

        public String getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(String userAnswer) {
            this.userAnswer = userAnswer;
        }

        public int getProblemState() {
            return problemState;
        }

        public void setProblemState(int problemState) {
            this.problemState = problemState;
        }

        public String getTypeName() {
            return typeName;
        }

        public int getQuestionNum() {
            return questionNum;
        }

        public int getQuestionScore() {
            return questionScore;
        }

        public String getTypeId() {
            return typeId;
        }

        public List<Question> getData() {
            return data;
        }

        public String getAnswer() {
            return answer;
        }

        public String getRemarks() {
            return remarks;
        }

        public String getShowAnswer() {
            return showAnswer;
        }
    }

    public static class Question implements Serializable{
        /**
         * optionContent : 减小曳引绳速
         * optionKey : A
         */
        private String optionContent;
        private String optionKey;
        private String optionId;

        public String getOptionContent() {
            return optionContent;
        }

        public String getOptionKey() {
            return optionKey;
        }

        public String getOptionId() {
            return optionId;
        }
    }
}
