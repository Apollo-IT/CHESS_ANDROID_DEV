package com.app.hrms.model;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ContractInfo {

    private int rowId;
    private String pernr;
    private String begda;
    private String cttyp;
    private String prbzt;
    private String prbeh;
    private String ctedt;
    private String ctnum;
    private String sidat;
    private String ctsel;

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

    public void setCttyp(String cttyp) {
        this.cttyp = cttyp;
    }
    public String getCttyp() {
        return cttyp;
    }

    public void setPrbzt(String prbzt) {
        this.prbzt = prbzt;
    }
    public String getPrbzt() {
        return prbzt;
    }

    public void setPrbeh(String prbeh) {
        this.prbeh = prbeh;
    }
    public String getPrbeh() {
        return prbeh;
    }

    public void setCtedt(String ctedt) {
        this.ctedt = ctedt;
    }
    public String getCtedt() {
        return ctedt;
    }

    public void setCtnum(String ctnum) {
        this.ctnum = ctnum;
    }
    public String getCtnum() {
        return ctnum;
    }

    public void setSidat(String sidat) {
        this.sidat = sidat;
    }
    public String getSidat() {
        return sidat;
    }

    public void setCtsel(String ctsel) {
        this.ctsel = ctsel;
    }
    public String getCtsel() {
        return ctsel;
    }
}
