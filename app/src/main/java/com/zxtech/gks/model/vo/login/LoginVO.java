package com.zxtech.gks.model.vo.login;

import java.util.List;

/**
 * Created by SYP521 on 2017/12/4.
 */

public class LoginVO {

    private List<Depts> Depts;
    private List<Roles> Roles;
    private UserInfo UserInfo;
    private String UserMenuList;
    private String refresh_token;
    private String access_token;
    private String openid;


    public List<Depts> getDepts() {
        return Depts;
    }


    public List<Roles> getRoles() {
        return Roles;
    }

    public UserInfo getUserInfo() {
        return UserInfo;
    }


    public String getUserMenuList() {
        return UserMenuList;
    }

    public void setUserMenuList(String userMenuList) {
        UserMenuList = userMenuList;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getRoleStr() {
        List<Roles> roles = getRoles();
        StringBuffer roleNo = new StringBuffer();
        if (null != roles && roles.size() > 0) {
            for (int i = 0; i < roles.size(); i++) {
                roleNo.append(roles.get(i).getRoleNo());
                if (i != roles.size() - 1) {
                    roleNo.append(",");
                }
            }
            return roleNo.toString();
        }
        return null;
    }

    public String[] getDeptIdInfo() {
        String[] deptArray = new String[3];
        StringBuffer userDeptId = new StringBuffer();
        StringBuffer userDeptNo = new StringBuffer();
        StringBuffer userDeptName = new StringBuffer();
        List<Depts> deptses = getDepts();
        if (null != deptses && deptses.size() > 0) {
            for (int i = 0; i < deptses.size(); i++) {
                userDeptId.append(deptses.get(i).getDeptId());
                userDeptNo.append(deptses.get(i).getDeptNo());
                userDeptName.append(deptses.get(i).getDeptName());
                if (i != deptses.size() - 1) {
                    userDeptId.append(",");
                    userDeptNo.append(",");
                    userDeptName.append(",");
                }
            }
        }
        deptArray[0] = userDeptId.toString();
        deptArray[1] = userDeptNo.toString();
        deptArray[2] = userDeptName.toString();
        return deptArray;
    }

}
