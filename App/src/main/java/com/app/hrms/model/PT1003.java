package com.app.hrms.model;

import org.json.JSONException;
import org.json.JSONObject;

public class PT1003 extends PT100x
{
    public String OTTYP				= "";
    public String OTREN				= "";
    public double OTDAY				= 0.0f;
    public double OTTIM				= 0.0f;

    public PT1003(JSONObject jsonObject) throws JSONException{
        this.PERNR = jsonObject.getString("pernr");
        this.BEGDA = jsonObject.getString("begda");
        this.ENDDA = jsonObject.getString("endda");
        this.OTTYP = jsonObject.getString("ottyp");
        this.OTREN = jsonObject.getString("otren");
        this.OTDAY = jsonObject.getDouble("otday");
        this.OTTIM = jsonObject.getDouble("ottim");
    }
    public double getTime(){
        return  OTTIM;
    }
}
