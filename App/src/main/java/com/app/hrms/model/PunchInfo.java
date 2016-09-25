package com.app.hrms.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/4/5.
 */
public class PunchInfo {

    public static final int ABSENCE         = 1;
    public static final int TRAVEL          = 2;
    public static final int OVERTIME        = 3;
    public static final int MISSING_CARD    = 4;
    public static final int LATE            = 5;
    public static final int LEAVE_EARLY     = 6;
    public static final int NORMAL          = 7;

    private Date date;
    private int intype;
    private int outype;
    private String reason;
    private int type;

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public int getIntype() {
        return intype;
    }
    public void setIntype(int intype) {
        this.intype = intype;
    }

    public int getOutype() {
        return outype;
    }
    public void setOutype(int outype) {
        this.outype = outype;
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
}
