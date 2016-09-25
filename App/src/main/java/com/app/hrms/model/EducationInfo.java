package com.app.hrms.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/5.
 */
public class EducationInfo implements Serializable {

    private int rowId;
    private String pernr;
    private String begda;
    private String endda;
    private String insti;
    private String etype;
    private String etypename;
    private String acdeg;
    private String hetyp;
    private String hacde;
    private String dacde;
    private String actur;
    private String spec1;
    private String spec2;

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

    public void setInsti(String insti) {
        this.insti = insti;
    }
    public String getInsti() {
        return insti;
    }

    public void setEtype(String etype) {
        this.etype = etype;
    }
    public String getEtype() {
        return etype;
    }
    public void setEtypename(String etypename) {
        this.etypename = etypename;
    }
    public String getEtypename() {
        if(etypename!=null && etypename.length()>0)
            return  etypename;

        HashMap<String, String> map = new HashMap();
        map.put("01","中专");
        map.put("02","高中及以下");
        map.put("03","大专");
        map.put("04","本科");
        map.put("05","硕士研究生");
        map.put("06","博士研究");

        String result = map.get(etype);
        if(result==null) result = map.get(hetyp);
        return  result;
    }

    public void setAcdeg(String acdeg) {
        this.acdeg = acdeg;
    }
    public String getAcdeg() {
        return acdeg;
    }

    public void setHetyp(String hetyp) {
        this.hetyp = hetyp;
    }
    public String getHetyp() {
        return hetyp;
    }

    public void setHacde(String hacde) {
        this.hacde = hacde;
    }
    public String getHacde() {
        return hacde;
    }

    public void setDacde(String dacde) {
        this.dacde = dacde;
    }
    public String getDacde() {
        return dacde;
    }

    public void setActur(String actur) {
        this.actur = actur;
    }
    public String getActur() {
        return actur;
    }

    public void setSpec1(String spec1) {
        this.spec1 = spec1;
    }
    public String getSpec1() { return spec1; }

    public void setSpec2(String spec2) {
        this.spec2 = spec2;
    }
    public String getSpec2() {
        return spec2;
    }
}
