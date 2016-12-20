package com.app.hrms.model;

import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/4.
 */
public class MemberModel implements Serializable {

    private String pernr;//id
    private String nachn;//name
    private String email;
    private String orgeh;
    private String orgehname;
    private String plans;
    private String plansname;
    private String bukrs;
    private String kostl;
    private String werks;
    private String btrtl;
    private String persg;
    private String persk;
    private String endat;
    private String jwdat;
    private String gesch;
    private String vorna;
    private String perid;
    private String gbdat;
    private String natio;
    private String racky;
    private String gbdep;
    private String gbort;
    private String fatxt;
    private String pcode;
    private String cobjid;
    private String coname;
    private String token;

    // For update pa1001
    private String begda;
    public void setCobjid(String cobjid) {
        this.cobjid = cobjid;
    }
    public String getCobjid() {
        return cobjid;
    }

    public void setConame(String coname) {
        this.coname = coname;
    }
    public String getConame() {
        return coname;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }
    public String getPernr() {
        return pernr;
    }

    public void setNachn(String nachn) {
        this.nachn = nachn;
    }
    public String getNachn() {
        return nachn;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setOrgeh(String orgeh) {this.orgeh = orgeh;}
    public String getOrgeh() { return orgeh; }

    public void setOrgehname(String orgehname) {
        this.orgehname = orgehname;
    }
    public String getOrgehname() {
        return orgehname;
    }

    public void setPlans(String plans) {
        this.plans = plans;
    }
    public String getPlans() {
        return plans;
    }

    public void setPlansname(String plansname) {
        this.plansname = plansname;
    }
    public String getPlansname() {
        return plansname;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }
    public String getBukrs() {
        return bukrs;
    }

    public void setKostl(String kostl) {
        this.kostl = kostl;
    }
    public String getKostl() {
        return kostl;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }
    public String getWerks() {
        return werks;
    }

    public void setBtrtl(String btrtl) {
        this.btrtl = btrtl;
    }
    public String getBtrtl() {
        return btrtl;
    }

    public void setPersg(String persg) {
        this.persg = persg;
    }
    public String getPersg() {
        return persg;
    }

    public void setPersk(String persk) {
        this.persk = persk;
    }
    public String getPersk() {
        return persk;
    }

    public void setEndat(String endat) {
        this.endat = endat;
    }
    public String getEndat() {
        return endat;
    }

    public void setJwdat(String jwdat) {
        this.jwdat = jwdat;
    }
    public String getJwdat() {
        return jwdat;
    }

    public void setGesch(String gesch) {
        this.gesch = gesch;
    }
    public String getGesch() {
        return gesch;
    }

    public void setVorna(String vorna) {
        this.vorna = vorna;
    }
    public String getVorna() {
        return vorna;
    }

    public void setPerid(String perid) {
        this.perid = perid;
    }
    public String getPerid() {
        return perid;
    }

    public void setGbdat(String gbdat) {
        this.gbdat = gbdat;
    }
    public String getGbdat() {
        return gbdat;
    }

    public void setNatio(String natio) {
        this.natio = natio;
    }
    public String getNatio() {
        return natio;
    }

    public void setRacky(String racky) {
        this.racky = racky;
    }
    public String getRacky() {
        return racky;
    }

    public void setGbdep(String gbdep) {
        this.gbdep = gbdep;
    }
    public String getGbdep() {
        return gbdep;
    }

    public void setGbort(String gbort) {
        this.gbort = gbort;
    }
    public String getGbort() {
        return gbort;
    }

    public void setFatxt(String fatxt) {
        this.fatxt = fatxt;
    }
    public String getFatxt() {
        return fatxt;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
    public String getPcode() {
        return pcode;
    }

    public void setBegda(String begda) {
        this.begda = begda;
    }
    public String getBegda() {
        return begda;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}
