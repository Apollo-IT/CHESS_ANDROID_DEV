package com.app.hrms.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class ContactInfo {
    private JSONObject jsonObject;

    public ContactInfo(){

    }

    public ContactInfo(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }
    private String getCunum(String cutyp){
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("rows");
            if(jsonArray==null) return "";
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject json = jsonArray.getJSONObject(i);
                String type = json.getString("cutyp");
                if(type!=null && type.equals(cutyp)){
                    return json.getString("cunum");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "";
    }
    public String getPhoneNumber(){
        return  getCunum("02");
    }
    public String getEamil(){
        return getCunum("05");
    }
    public String getQQ(){
        return getCunum("06");
    }
    public String getOfficePhone(){
        return getCunum("03");
    }
}
