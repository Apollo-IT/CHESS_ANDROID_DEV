package com.app.hrms.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/5.
 */
public class PerformanceInfo implements Serializable {

    private String pernr        = "";
    private String begda        = "";
    private String endda        = "";
    private String pefya        = "";
    private String pefty        = "";
    private String peflv        = "";
    private int pefsc           = 0;

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

    public void setEndda(String endda)  {
        this.endda = endda;
    }
    public String getEndda() {
        return endda;
    }

    public void setPefya(String pefya) {
        this.pefya = pefya;
    }
    public String getPefya() {
        return pefya;
    }

    public void setPefty(String pefty) {
        this.pefty = pefty;
    }
    public String getPefty() {
        return pefty;
    }

    public void setPeflv(String peflv) {
        this.peflv = peflv;
    }
    public String getPeflv() {
        return peflv;
    }

    public void setPefsc(int pefsc) {
        this.pefsc = pefsc;
    }
    public int getPefsc() {
        return pefsc;
    }
}
