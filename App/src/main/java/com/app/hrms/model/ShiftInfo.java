package com.app.hrms.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ShiftInfo {
    public String id;
    public String pernr;
    public String bukrs;
    public String shfno;
    public String shfna;
    public String timin;
    public String flxin;
    public String timou;
    public String flxou;
    public String wortm;

    public ShiftInfo(JSONObject json)throws JSONException{
        this.id = json.getString("id");
        this.pernr = json.getString("pernr");
        this.bukrs = json.getString("bukrs");
        this.shfno = json.getString("shfno");
        this.shfna = json.getString("shfna");
        this.timin = json.getString("timin");
        this.flxin = json.getString("flxin");
        this.timou = json.getString("timou");
        this.flxou = json.getString("flxou");
        this.wortm = json.getString("wortm");
    }
}
