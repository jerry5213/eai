package com.zxtech.esp.data.bean;

import java.util.List;

/**
 * Created by SYP521 on 2017/7/31.
 */

public class TopicVO extends BaseVO<List<TopicVO.Data>> {

    public static class Data{

        private String num;
        private int isTag;

        public Data(String num, int isTag) {
            this.num = num;
            this.isTag = isTag;
        }

        public String getNum() {
            return num;
        }

        public int getIsTag() {
            return isTag;
        }
    }
}
