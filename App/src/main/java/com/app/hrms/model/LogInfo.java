package com.app.hrms.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/5.
 */
public class LogInfo implements Serializable {
    public static String RELEASE_STATUS_SAVE    = "01";
    public static String RELEASE_STATUS_PUBLISH = "02";

    private long rowId;
    private String pernr;
    private String date;
    private String hour;
    private String place;
    private String property;
    private String content;
    private String release;

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }
    public long getRowId() {
        return rowId;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }
    public String getPernr() {
        return pernr;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
    public String getHour() {
        return hour;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    public String getPlace() {return place;}

    public void setProperty(String property) {
        this.property = property;
    }
    public String getProperty() {return property;}

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setRelease(String release) {
        this.release = release;
    }
    public String getRelease() {
        return release;
    }
}
