package com.app.hrms.ui.home.application;

public class FilterItem {
    public int iconId;
    public String title;
    public boolean selected;

    public FilterItem(int iconId, String title, boolean selected) {
        this.iconId = iconId;
        this.title = title;
        this.selected =selected;
    }
}
