package com.app.hrms.model;

import org.json.JSONException;
import org.json.JSONObject;

public class PT1001 extends PT100x {
    public String ABTYP				= "";
    public String ABREN				= "";
    public double ABDAY				= 0.0f;
    public double ABTIM				= 0.0f;

    public PT1001(JSONObject jsonObject) throws JSONException{
        this.PERNR = jsonObject.getString("pernr");
        this.BEGDA = jsonObject.getString("begda");
        this.ENDDA = jsonObject.getString("endda");
        this.ABTYP = jsonObject.getString("abtyp");
        this.ABREN = jsonObject.getString("abren");
        this.ABDAY = jsonObject.getDouble("abday");
        this.ABTIM = jsonObject.getDouble("abtim");
    }
    public double getTime(){
        return  ABTIM;
    }
}
