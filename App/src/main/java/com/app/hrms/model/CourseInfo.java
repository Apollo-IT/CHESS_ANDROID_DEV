package com.app.hrms.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/5.
 */
public class CourseInfo implements Serializable {

    private int rowId;
    private String pernr            = "";
    private String begda            = "";
    private String endda            = "";
    private String trype            = "";
    private String trrst            = "";
    private String coute            = "";
    private String couad            = "";
    private String coudt            = "";
    private String couna            = "";
    private String coust            = "";
    private String couno            = "";
    private String id               = "";

    public SubordinateInfo subordinateInfo = new SubordinateInfo();

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }
    public int getRowId() {
        return rowId;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
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

    public void setTrrst(String trrst) {
        this.trrst = trrst;
    }
    public String getTrrst() {
        return trrst;
    }

    public void setCouna(String couna) {
        this.couna = couna;
    }
    public String getCouna() {
        return couna;
    }

    public void setCoust(String coust) {
        this.coust = coust;
    }
    public String getCoust() {
        return coust;
    }

    public void setCouno(String couno) {
        this.couno = couno;
    }
    public String getCouno() {
        return couno;
    }

    public void setCoute(String coute) {
        this.coute = coute;
    }
    public String getCoute() {
        return coute;
    }

    public void setCouad(String couad) {
        this.couad = couad;
    }
    public String getCouad() {
        return couad;
    }

    public void setCoudt(String coudt) {
        this.coudt = coudt;
    }
    public String getCoudt() {
        return coudt;
    }
}
