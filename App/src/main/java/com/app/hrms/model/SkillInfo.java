package com.app.hrms.model;

/**
 * Created by Administrator on 2016/4/5.
 */
public class SkillInfo {

    private int rowId;
    private String pernr;
    private String sktyp;
    private String sklvl;
    private String hsklv;
    private String stunt;
    private String stnum;
    private String stdat;

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

    public void setSktyp(String sktyp) {
        this.sktyp = sktyp;
    }
    public String getSktyp() {
        return sktyp;
    }

    public void setSklvl(String sklvl) {
        this.sklvl = sklvl;
    }
    public String getSklvl() {
        return sklvl;
    }

    public void setHsklv(String hsklv) {
        this.hsklv = hsklv;
    }
    public String getHsklv() {
        return hsklv;
    }

    public void setStunt(String stunt) {
        this.stunt = stunt;
    }
    public String getStunt() {
        return stunt;
    }

    public void setStnum(String stnum) {
        this.stnum = stnum;
    }
    public String getStnum() {
        return stnum;
    }

    public void setStdat(String stdat) {
        this.stdat = stdat;
    }
    public String getStdat() {
        return stdat;
    }
}
