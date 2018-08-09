package com.zxtech.mt.entity;

/**
 * Created by Chw on 2016/7/27.
 */
public class CalSurveyResult {


    private String id;

    private String sur_id;

    private String question_id;

    private Integer question_score;

    private String remark;

    private String question_content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSur_id() {
        return sur_id;
    }

    public void setSur_id(String sur_id) {
        this.sur_id = sur_id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public Integer getQuestion_score() {
        return question_score;
    }

    public void setQuestion_score(Integer question_score) {
        this.question_score = question_score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }
}
