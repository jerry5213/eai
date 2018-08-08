package com.zxtech.ecs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syp523 on 2018/2/10.
 */

public class VoiceResult {


    /**
     * result : {"state":"inconversation","language":"CN","goFlag":true,"moveTo":"FIRE_EMERGENCY_FUNCTION","intent":"询问电梯方案","entity":{"a":"a-val","b":"b-val"},"answerItem":"电梯编号","messages":[{"config":1,"msg_text":"abcdefg","msg_picture":["pic1","pic2"],"msg_video":["home.jpg","vid.mp4"]},{"config":2,"msg_text":"hijk","msg_picture":["pic3"],"msg_video":["home.jpg","vid.mp4"]}]}
     */

    private ResultBean result;

    private Map<String,String> paramsMap = new HashMap<>();


    public ResultBean getResult() {
        return result;
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * state : inconversation
         * language : CN
         * goFlag : true
         * moveTo : FIRE_EMERGENCY_FUNCTION
         * intent : 询问电梯方案
         * entity : {"a":"a-val","b":"b-val"}
         * answerItem : 电梯编号
         * messages : [{"config":1,"msg_text":"abcdefg","msg_picture":["pic1","pic2"],"msg_video":["home.jpg","vid.mp4"]},{"config":2,"msg_text":"hijk","msg_picture":["pic3"],"msg_video":["home.jpg","vid.mp4"]}]
         */

        private String state;
        private String language;
        private boolean goFlag;
        private String type;
        private String moveTo;
        private String intent;
        private EntityBean entity;
        private String answerItem;
        private List<MessagesBean> messages;
        private List<Map<String,String>> actions = new ArrayList<>();

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public boolean isGoFlag() {
            return goFlag;
        }

        public void setGoFlag(boolean goFlag) {
            this.goFlag = goFlag;
        }

        public String getMoveTo() {
            return moveTo;
        }

        public void setMoveTo(String moveTo) {
            this.moveTo = moveTo;
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

        public List<MessagesBean> getMessages() {
            return messages;
        }

        public void setMessages(List<MessagesBean> messages) {
            this.messages = messages;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Map<String, String>> getActions() {
            return actions;
        }

        public void setActions(List<Map<String, String>> actions) {
            this.actions = actions;
        }

        public static class EntityBean {
            /**
             * a : a-val
             * b : b-val
             */

            private String a;
            private String b;

            public String getA() {
                return a;
            }

            public void setA(String a) {
                this.a = a;
            }

            public String getB() {
                return b;
            }

            public void setB(String b) {
                this.b = b;
            }
        }

        public static class MessagesBean {
            /**
             * config : 1
             * msg_text : abcdefg
             * msg_picture : ["pic1","pic2"]
             * msg_video : ["home.jpg","vid.mp4"]
             */

            private int config;
            private String msg_text;
            private String title;
            private List<String> msg_picture;
            private List<String> msg_video = new ArrayList<>();

            public int getConfig() {
                return config;
            }

            public void setConfig(int config) {
                this.config = config;
            }

            public String getMsg_text() {
                return msg_text;
            }

            public void setMsg_text(String msg_text) {
                this.msg_text = msg_text;
            }

            public List<String> getMsg_picture() {
                return msg_picture;
            }

            public void setMsg_picture(List<String> msg_picture) {
                this.msg_picture = msg_picture;
            }

            public List<String> getMsg_video() {
                return msg_video;
            }

            public void setMsg_video(List<String> msg_video) {
                this.msg_video = msg_video;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
