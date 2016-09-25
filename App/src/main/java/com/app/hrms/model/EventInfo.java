package com.app.hrms.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/5.
 */
public class EventInfo implements Serializable {

    private int rowId;
    private String pernr;
    private String begda;
    private String massn;
    private String massg;
    private String estua;

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

    public void setMassn(String massn) {
        this.massn = massn;
    }
    public String getMassn() {
        return massn;
    }

    public void setMassg(String massg) {
        this.massg = massg;
    }
    public String getMassg() {
        return massg;
    }

    public void setEstua(String estua) {
        this.estua = estua;
    }
    public String getEstua() {
        return estua;
    }

}
