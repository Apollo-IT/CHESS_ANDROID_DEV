package com.app.hrms.model;

import org.json.JSONException;
import org.json.JSONObject;

public class PT1004 extends PT100x{
    public long   ROW_ID;
    public String CLODA	= "";
    public String CLOIN	= "00:00:00";
    public String CINAD	= "";
    public String CLOOU	= "00:00:00";
    public String COUAD	= "";
    public String CLORM	= "";
    public Double EXTIM = 0d;

    public PT1004(){

    }
    public PT1004(JSONObject jsonObject) throws JSONException
    {
        this.PERNR = jsonObject.getString("pernr");
        this.CLODA = jsonObject.getString("cloda");
        this.CLOIN = jsonObject.getString("cloin");
        this.CINAD = jsonObject.getString("cinad");
        this.CLOOU = jsonObject.getString("cloou");
        this.COUAD = jsonObject.getString("couad");
        this.CLORM = jsonObject.getString("clorm");
        this.EXTIM = jsonObject.getDouble("extim");
    }
    public double getTime(){
        return  EXTIM;
    }
    public String getDate(){
        return CLODA;
    }
}
