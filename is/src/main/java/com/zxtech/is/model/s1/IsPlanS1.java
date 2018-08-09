package com.zxtech.is.model.s1;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zxtech.is.adapter.ExpandableItemAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by syp688 on 2018/5/03.
 */

public class IsPlanS1 implements Serializable {

    public static class IsPlanPro extends AbstractExpandableItem<IsPlanPro.IsProEle> implements MultiItemEntity, Serializable {
        private static final long serialVersionUID = 8614435410466082908L;
        /**
         * prjId : 项目id
         * prjName : 项目名称
         * proECode : BF-MGD
         * name_En : null
         * params : [{"eleId":"电梯id","eleName":"电梯名称","confirmTime":"报告时间","hasDescription":false,"userName":姓名}]
         */

        private String prjId;

        private String prjName;

        private List<IsProEle> params;

        public String getPrjId() {
            return prjId;
        }

        public void setPrjId(String prjId) {
            this.prjId = prjId;
        }

        public String getPrjName() {
            return prjName;
        }

        public void setPrjName(String prjName) {
            this.prjName = prjName;
        }


        public List<IsProEle> getParams() {
            return params;
        }

        public void setParams(List<IsProEle> params) {
            this.params = params;
        }

        @Override
        public int getLevel() {
            return ExpandableItemAdapter.TYPE_LEVEL_0;
        }

        @Override
        public int getItemType() {
            return 0;
        }


        public static class IsProEle implements MultiItemEntity, Serializable {
            private static final long serialVersionUID = 5388604845220852051L;
            /**
             * eleId : 电梯id
             * eleName : 电梯名称
             * confirmTime : 报告时间
             * hasDescription : false
             * userName : 姓名
             */

            private String eleId;

            private String eleName;

            private String confirmTime;

            private boolean hasDescription;

            private String userName;

            private String endTime;

            private String status;

            public String getProcInstId() {
                return procInstId;
            }

            public void setProcInstId(String procInstId) {
                this.procInstId = procInstId;
            }

            private String projectId;

            private String procInstId;

            public String getEleId() {
                return eleId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setEleId(String eleId) {
                this.eleId = eleId;
            }

            public String getEleName() {
                return eleName;
            }

            public void setEleName(String eleName) {
                this.eleName = eleName;
            }

            public String getConfirmTime() {
                return confirmTime;
            }

            public void setConfirmTime(String confirmTime) {
                this.confirmTime = confirmTime;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public boolean isHasDescription() {
                return hasDescription;
            }

            public void setHasDescription(boolean hasDescription) {
                this.hasDescription = hasDescription;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getProjectId() {
                return projectId;
            }

            public void setProjectId(String projectId) {
                this.projectId = projectId;
            }


            @Override
            public int getItemType() {
                return 1;
            }
        }
    }
}
