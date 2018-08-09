package com.zxtech.is.model.team;

import java.util.List;

/**
 * 分公司
 * Created by syp692 on 2018/4/21.
 */
//
public class IsSlTeam {


    public String getUnitguid() {
        return unitguid;
    }

    public void setUnitguid(String unitguid) {
        this.unitguid = unitguid;
    }

    private String unitguid;

    private String unitName;

    private String teamGuid;

    private String teamName;


    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getTeamGuid() {
        return teamGuid;
    }

    public void setTeamGuid(String teamGuid) {
        this.teamGuid = teamGuid;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }


    public List<IsSlInstallationmember> getIsSlTeamMemberList() {
        return isSlTeamMemberList;
    }

    public void setIsSlTeamMemberList(List<IsSlInstallationmember> isSlTeamMemberList) {
        this.isSlTeamMemberList = isSlTeamMemberList;
    }

    private List<IsSlInstallationmember> isSlTeamMemberList;


}
