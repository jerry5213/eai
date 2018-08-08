package com.zxtech.ecs.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by syp523 on 2018/2/10.
 */

public class Voice {

    private String app;

    private String language;

    private String userId;

    private String utterance;

    private String answerItem;

    private String intent;

    private String busiId;

    private String trigger;


    private Bean bean = new Bean();


    public String getApp() {
        return app;
    }


    public void setApp(String app) {
        this.app = app;
    }





    public String getLanguage() {
        return language;
    }





    public void setLanguage(String language) {
        this.language = language;
    }





    public String getUserId() {
        return userId;
    }





    public void setUserId(String userId) {
        this.userId = userId;
    }





    public String getUtterance() {
        return utterance;
    }





    public void setUtterance(String utterance) {
        this.utterance = utterance;
    }





    public String getAnswerItem() {
        return answerItem;
    }





    public void setAnswerItem(String answerItem) {
        this.answerItem = answerItem;
    }





    public String getIntent() {
        return intent;
    }





    public void setIntent(String intent) {
        this.intent = intent;
    }





    public String getBusiId() {
        return busiId;
    }





    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }





    public String getTrigger() {
        return trigger;
    }





    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }





    public Bean getBean() {
        return bean;
    }





    public void setBean(Bean bean) {
        this.bean = bean;
    }





    public static class Bean{
        private Map<String,String> ElevatorParameter = new HashMap<>();

        public Map<String, String> getElevatorParameter() {
            return ElevatorParameter;
        }

        public void setElevatorParameter(Map<String, String> elevatorParameter) {
            ElevatorParameter = elevatorParameter;
        }
    }
}
