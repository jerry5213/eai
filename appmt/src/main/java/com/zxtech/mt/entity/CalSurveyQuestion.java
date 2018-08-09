package com.zxtech.mt.entity;

/**
 * Created by Chw on 2016/8/17.
 */
public class CalSurveyQuestion {

    private String id;

    private String sur_type;

    private String question_content;

    private Integer high_score;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSur_type() {
        return sur_type;
    }

    public void setSur_type(String sur_type) {
        this.sur_type = sur_type;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public Integer getHigh_score() {
        return high_score;
    }

    public void setHigh_score(Integer high_score) {
        this.high_score = high_score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
