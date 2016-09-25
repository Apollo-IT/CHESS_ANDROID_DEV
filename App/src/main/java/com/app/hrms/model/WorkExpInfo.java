package com.app.hrms.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/5.
 */
public class WorkExpInfo implements Serializable {

    private int rowId;
    private String pernr;
    private String begda;
    private String endda;
    private String untur;
    private String unnam;
    private String unpos;

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }
    public int getRowId() {
        return rowId;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }
    public String getPernr() {
        return pernr;
    }

    public void setBegda(String begda) {
        this.begda = begda;
    }
    public String getBegda() {
        return begda;
    }

    public void setEndda(String endda) {
        this.endda = endda;
    }
    public String getEndda() {
        return endda;
    }

    public void setUntur(String untur) {
        this.untur = untur;
    }
    public String getUntur() {
        return untur;
    }

    public void setUnnam(String unnam) {
        this.unnam = unnam;
    }
    public String getUnnam() {
        return unnam;
    }

    public void setUnpos(String unpos) {
        this.unpos = unpos;
    }
    public String getUnpos() {
        return unpos;
    }
}
