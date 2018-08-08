package com.zxtech.ecs.model;

import android.content.Context;

import com.zxtech.ecs.ui.login.LoginActivity;
import com.zxtech.ecs.util.AppUtil;
import com.zxtech.gks.common.SPUtils;
import com.zxtech.gks.model.vo.login.LoginVO;
import com.zxtech.gks.model.vo.login.UserInfo;

/**
 * Created by syp523 on 2018/6/1.
 */

public class UserManager {

    private String userId; //用户ID

    private String userNo; //用户编号

    private String userName; //用户名称

    private String userDeptId; //部门ID

    private String userDeptNo; //部门编号

    private String userDeptName; //部门名称

    private String userRemark;//备注信息

    private String userEmail;//邮箱

    private String roleNo;//角色

    private String username;//登录用户名

    private String password;//密码

    private String countryCode;//国家编码


    private static UserManager instance = null;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
            //SP中恢复数据
            instance.userId = (String) SPUtils.get(AppUtil.getContext(), "user_id", "");
            instance.userNo = (String) SPUtils.get(AppUtil.getContext(), "user_no", "");
            instance.userName = (String) SPUtils.get(AppUtil.getContext(), "user_name", "");
            instance.userDeptId = (String) SPUtils.get(AppUtil.getContext(), "user_dept_id", "");
            instance.userDeptNo = (String) SPUtils.get(AppUtil.getContext(), "user_dept_no", "");
            instance.userDeptName = (String) SPUtils.get(AppUtil.getContext(), "user_dept_name", "");
            instance.userRemark = (String) SPUtils.get(AppUtil.getContext(), "user_remark", "");
            instance.userEmail = (String) SPUtils.get(AppUtil.getContext(), "user_email", "");
            instance.roleNo = (String) SPUtils.get(AppUtil.getContext(), "role_no", "");
            instance.username = (String) SPUtils.get(AppUtil.getContext(), "username", "");
            instance.password = (String) SPUtils.get(AppUtil.getContext(), "password", "");
            instance.countryCode = (String) SPUtils.get(AppUtil.getContext(), "country_code", "");
        }
        return instance;
    }

    private UserManager() {
    }

    public static void saveUserInfo(Context context,LoginVO loginData,boolean isRemember,String username,String pwd,String country) {
        UserInfo userInfo = loginData.getUserInfo();

        if (loginData.getRoleStr() != null) {
            SPUtils.put(context, "role_no", loginData.getRoleStr());
        }

        if (isRemember) {
            SPUtils.put(context, "username", username);
        } else {
            SPUtils.put(context, "username", "");
        }

        String[] deptIdInfo = loginData.getDeptIdInfo();

        UserManager.getInstance().setUser(userInfo.getUserId(),userInfo.getUserNo(),userInfo.getUserName(),deptIdInfo[0],deptIdInfo[1],deptIdInfo[2],
                userInfo.getUserRemark(),userInfo.getUserEmail(),loginData.getRoleStr(),username,pwd,country);

        SPUtils.put(context, "user_id", userInfo.getUserId());
        SPUtils.put(context, "user_no", userInfo.getUserNo());
        SPUtils.put(context, "user_name", userInfo.getUserName());
        SPUtils.put(context, "user_dept_id", deptIdInfo[0]);
        SPUtils.put(context, "user_dept_no", deptIdInfo[1]);
        SPUtils.put(context, "user_dept_name", deptIdInfo[2]);
        SPUtils.put(context, "user_remark", userInfo.getUserRemark());
        SPUtils.put(context, "user_email", userInfo.getUserEmail());
        SPUtils.put(context, "headimg_url", userInfo.getHeadImgUrl());
        SPUtils.put(context, "password", pwd);
        SPUtils.put(context, "country_code", country);
        SPUtils.put(context, "permission", loginData.getUserMenuList());
        SPUtils.put(context, "wx_access_token", loginData.getAccess_token());
        SPUtils.put(context, "wx_refresh_token", loginData.getRefresh_token());
        SPUtils.put(context, "wx_openid", loginData.getOpenid());

        SPUtils.put(context, "is_first", false);
    }


    public void setUser(String userId, String userNo, String userName, String userDeptId, String userDeptNo, String userDeptName, String userRemark, String userEmail, String roleNo, String username, String password, String countryCode) {
        this.userId = userId;
        this.userNo = userNo;
        this.userName = userName;
        this.userDeptId = userDeptId;
        this.userDeptNo = userDeptNo;
        this.userDeptName = userDeptName;
        this.userRemark = userRemark;
        this.userEmail = userEmail;
        this.roleNo = roleNo;
        this.username = username;
        this.password = password;
        this.countryCode = countryCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDeptNo() {
        return userDeptNo;
    }

    public void setUserDeptNo(String userDeptNo) {
        this.userDeptNo = userDeptNo;
    }

    public String getUserDeptName() {
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(String userDeptId) {
        this.userDeptId = userDeptId;
    }
}
