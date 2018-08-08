package com.zxtech.ecs.model;

import java.util.List;

/**
 * Created by syp521 on 2018/4/12.
 */

public class ChatMessage2<T> {

    private String language;
    private boolean flag;
    private String actionType;
    private String intent;
    private EntityBean entity;
    private String answerItem;
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public EntityBean getEntity() {
        return entity;
    }

    public void setEntity(EntityBean entity) {
        this.entity = entity;
    }

    public String getAnswerItem() {
        return answerItem;
    }

    public void setAnswerItem(String answerItem) {
        this.answerItem = answerItem;
    }

    public static class EntityBean {
        /**
         * Keyword : 蒂森
         */

        private String Keyword;

        public String getKeyword() {
            return Keyword;
        }

        public void setKeyword(String Keyword) {
            this.Keyword = Keyword;
        }
    }
}
