package com.app.hrms.model;

/**
 * Created by Administrator on 2016/4/5.
 */
public class TrainInfo {

    private int rowId;
    private String pernr;
    private String begda;
    private String endda;
    private String trype;
    private String couna;
    private String trrst;

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

    public void setTrype(String trype) {
        this.trype = trype;
    }
    public String getTrype() {
        return trype;
    }

    public void setCouna(String couna) {
        this.couna = couna;
    }
    public String getCouna() {
        return couna;
    }

    public void setTrrst(String trrst) {
        this.trrst = trrst;
    }
    public String getTrrst() {
        return trrst;
    }
}
