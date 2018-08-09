package com.zxtech.is.model.s1;

import com.zxtech.is.model.attach.Attach;

import java.util.List;

/**
 * Created by syp600 on 2018/4/21.
 */

public class S1Response {

    //项目信息
    private Project projectInfo;

    public S1Elevator getElevatorInfo() {
        return elevatorInfo;
    }

    public void setElevatorInfo(S1Elevator elevatorInfo) {
        this.elevatorInfo = elevatorInfo;
    }

    //产品信息
    //项目信息
    private S1Elevator elevatorInfo;
    //项目附件


    private List<Attach> projectAttachList;
    //产品列表
    private List<S1Elevator> elevatorList;
    //产品级附件
    private List<Attach> elevatorAttachList;
    //合并的交货地址

    public List<ElevatorContact> getContactList() {
        return contactList;
    }

    public void setContactList(List<ElevatorContact> contactList) {
        this.contactList = contactList;
    }

    private List<AddressBean> addressCombineList;
    //合并的安装地址
    private List<AddressBean> installAddressCombineList;

    //联系人
    private List<ElevatorContact> contactList;

    public Project getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(Project projectInfo) {
        this.projectInfo = projectInfo;
    }

    public List<Attach> getProjectAttachList() {
        return projectAttachList;
    }

    public void setProjectAttachList(List<Attach> projectAttachList) {
        this.projectAttachList = projectAttachList;
    }

    public List<S1Elevator> getElevatorList() {
        return elevatorList;
    }

    public void setElevatorList(List<S1Elevator> elevatorList) {
        this.elevatorList = elevatorList;
    }

    public List<AddressBean> getAddressCombineList() {
        return addressCombineList;
    }

    public void setAddressCombineList(List<AddressBean> addressCombineList) {
        this.addressCombineList = addressCombineList;
    }

    public List<AddressBean> getInstallAddressCombineList() {
        return installAddressCombineList;
    }

    public void setInstallAddressCombineList(List<AddressBean> installAddressCombineList) {
        this.installAddressCombineList = installAddressCombineList;
    }

    public List<Attach> getElevatorAttachList() {
        return elevatorAttachList;
    }

    public void setElevatorAttachList(List<Attach> elevatorAttachList) {
        this.elevatorAttachList = elevatorAttachList;
    }

    public static class AddressBean {
        private String elevatorguid;
        private String projectaddprovince;
        private String projectaddcity;
        private String projectaddarea;
        private String projectaddother;

        public String getElevatorguid() {
            return elevatorguid;
        }

        public void setElevatorguid(String elevatorguid) {
            this.elevatorguid = elevatorguid;
        }

        public String getProjectaddprovince() {
            return projectaddprovince;
        }

        public void setProjectaddprovince(String projectaddprovince) {
            this.projectaddprovince = projectaddprovince;
        }

        public String getProjectaddcity() {
            return projectaddcity;
        }

        public void setProjectaddcity(String projectaddcity) {
            this.projectaddcity = projectaddcity;
        }

        public String getProjectaddarea() {
            return projectaddarea;
        }

        public void setProjectaddarea(String projectaddarea) {
            this.projectaddarea = projectaddarea;
        }

        public String getProjectaddother() {
            return projectaddother;
        }

        public void setProjectaddother(String projectaddother) {
            this.projectaddother = projectaddother;
        }
    }
}
