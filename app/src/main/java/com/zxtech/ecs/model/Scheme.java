package com.zxtech.ecs.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zxtech.ecs.adapter.ExpandableItemAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by syp523 on 2018/4/3.
 */

public class Scheme extends AbstractExpandableItem<Scheme.ParamsBeans> implements MultiItemEntity, Serializable {

    @Override
    public int getLevel() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getItemType() {
        return 0;
    }


    public static class ParamsBeans implements MultiItemEntity, Serializable {
        private List<Programme.DimensionsBean.ParamsBean> paramsBeans = new ArrayList<>();
        private boolean different;

        public boolean isDifferent() {
            boolean different = false;
            String s = null;
            for (int i = 0; i < paramsBeans.size(); i++) {
                if (s != null) {
                    if (!s.equals(paramsBeans.get(i).getValue())) {
                        different = true;
                    }
                } else {
                    s = paramsBeans.get(i).getValue();
                }
            }
            return different;
        }

        public void setDifferent(boolean different) {
            this.different = different;
        }

        public List<Programme.DimensionsBean.ParamsBean> getParamsBeans() {
            return paramsBeans;
        }

        public void setParamsBeans(List<Programme.DimensionsBean.ParamsBean> paramsBeans) {
            this.paramsBeans = paramsBeans;
        }

        @Override
        public int getItemType() {
            return 1;
        }
    }

}
