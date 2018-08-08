package com.zxtech.ecs.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by syp521 on 2018/4/10.
 */

public class QmsAddressList {

    private List<QmsAddress> AddressList;

    public List<QmsAddress> getAddressList() {
        return AddressList;
    }

    public void setAddressList(List<QmsAddress> addressList) {
        AddressList = addressList;
    }

    public class QmsAddress implements Serializable{

        private int AutoId;
        private String LXR;
        private String LXDH;
        private String LXDZ;
        private boolean IsMR;
        private String SX;

        public int getAutoId() {
            return AutoId;
        }

        public void setAutoId(int autoId) {
            AutoId = autoId;
        }

        public String getLXR() {
            return LXR;
        }

        public void setLXR(String LXR) {
            this.LXR = LXR;
        }

        public String getLXDH() {
            return LXDH;
        }

        public void setLXDH(String LXDH) {
            this.LXDH = LXDH;
        }

        public String getLXDZ() {
            return LXDZ;
        }

        public void setLXDZ(String LXDZ) {
            this.LXDZ = LXDZ;
        }

        public boolean getIsMR() {
            return IsMR;
        }

        public void setIsMR(boolean isMR) {
            IsMR = isMR;
        }

        public String getSX() {
            return SX;
        }

        public void setSX(String SX) {
            this.SX = SX;
        }
    }
}
