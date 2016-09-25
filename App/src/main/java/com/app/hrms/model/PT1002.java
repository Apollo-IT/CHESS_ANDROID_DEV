package com.app.hrms.model;

import org.json.JSONException;
import org.json.JSONObject;

public class PT1002 extends PT100x{
    public String TRREN				= "";
    public double TRDAY				= 0.0f;
    public double TRTIM				= 0.0f;

    public PT1002(JSONObject jsonObject) throws JSONException{
        this.PERNR = jsonObject.getString("pernr");
        this.BEGDA = jsonObject.getString("begda");
        this.ENDDA = jsonObject.getString("endda");
        this.TRREN = jsonObject.getString("trren");
        this.TRDAY = jsonObject.getDouble("trday");
        this.TRTIM = jsonObject.getDouble("trtim");
    }
    public double getTime(){
        return  TRTIM;
    }
}
