package com.zxtech.ecs.model;

import java.util.List;

/**
 * Created by syp521 on 2018/4/8.
 */

public class OcrResult {

    private String log_id;
    private String direction;
    private int words_result_num;
    private List<Words> words_result;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<Words> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<Words> words_result) {
        this.words_result = words_result;
    }

    public class Words{

        private String words;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }
}
