package com.zxtech.is.model.team;

/**
 * Created by syp660 on 2018/5/7.
 */

public class TeamAddMemberEla {

    private String elaGuid;

    private String elaName;

    private String prjGuid;

    private String leader;

    private String teamGuid;

    private boolean isCheck;

    public String getTeamGuid() {
        return teamGuid;
    }

    public void setTeamGuid(String teamGuid) {
        this.teamGuid = teamGuid;
    }

    public String getElaGuid() {
        return elaGuid;
    }

    public void setElaGuid(String elaGuid) {
        this.elaGuid = elaGuid;
    }

    public String getElaName() {
        return elaName;
    }

    public void setElaName(String elaName) {
        this.elaName = elaName;
    }

    public String getPrjGuid() {
        return prjGuid;
    }

    public void setPrjGuid(String prjGuid) {
        this.prjGuid = prjGuid;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
