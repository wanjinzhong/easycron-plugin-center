package com.github.wanjinzhong.easycronplugincenter.bo.user;
public class UserInfo extends BasicUserBo{

    private boolean roleAddable;

    private String role;

    public boolean isRoleAddable() {
        return roleAddable;
    }

    public void setRoleAddable(boolean roleAddable) {
        this.roleAddable = roleAddable;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
