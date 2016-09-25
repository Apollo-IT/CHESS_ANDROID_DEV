package com.app.hrms.model;

import org.json.JSONObject;

public class PA1012 {
    public String 	PERNR			= "";
    public String   NACHN           = "";
    public String 	BEGDA			= "";
    public String 	ENDDA			= "";
    public String 	PEFYA			= "";
    public String 	PEFTY			= "";
    public String   PEFLV			= "";
    public int      PEFSC           = 0;

    public PA1012(JSONObject jsonObject)throws Exception{
        this.PERNR = jsonObject.getString("PERNR");
        this.NACHN = jsonObject.getString("NACHN");
        this.BEGDA = jsonObject.getString("BEGDA");
        this.ENDDA = jsonObject.getString("ENDDA");
        this.PEFSC = jsonObject.getInt("PEFSC");
    }
}
