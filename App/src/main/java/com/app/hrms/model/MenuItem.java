package com.app.hrms.model;

/**
 * Created by Administrator on 2016/3/6.
 */
public class MenuItem {
    private String menuName = "";
    private int resouceId = 0;

    public MenuItem(String menuName, int resourceId) {
        this.menuName = menuName;
        this.resouceId = resourceId;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getMenuName() {
        return menuName;
    }

    public void setResouceId(int resouceId) {
        this.resouceId = resouceId;
    }
    public int getResouceId() {
        return resouceId;
    }
}
