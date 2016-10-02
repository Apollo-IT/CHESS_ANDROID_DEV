package com.app.hrms.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.hrms.utils.db.DBManaber;
import com.app.hrms.utils.db.DBUtil;
import com.app.hrms.utils.gps.Position;

import java.sql.Date;

public class AppData {
    private static String PREFS_NAME = "app_data";
    private static Context context;
    private static SharedPreferences shared;
    /***********************************************************************************************
     *                                      Init App Data
     * @param context
     */
    public static void init(Context context){
        AppData.context = context;
        shared = context.getSharedPreferences(PREFS_NAME, 0);
    }

    /***********************************************************************************************
     *                                     Signin Time In
     * @param timeIn
     */
    public static void setTimeIn(long timeIn, String location){
        SharedPreferences.Editor editor = shared.edit();
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_timein";
        editor.putLong(key, timeIn);
        editor.putString(key+"_location", location);
        editor.commit();
    }
    public static Long getTimeIn(){
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_timein";
        return shared.getLong(key,0);
    }
    public static String getTimeinLocation(){
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_timein_location";
        return shared.getString(key,"");
    }
    /***********************************************************************************************
     *                                     Signin Time Out
     * @param timeOut
     */
    public static void setTimeOut(Long timeOut, String location){
        SharedPreferences.Editor editor = shared.edit();
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_timeout";
        editor.putLong(key, timeOut);
        editor.putString(key+"_location", location);
        editor.commit();
    }
    public static Long getTimeOut(){
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_timeout";
        return shared.getLong(key,0);
    }
    public static String getTimeoutLocation(){
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_timeout_location";
        return shared.getString(key,"");
    }
    /***********************************************************************************************
     *                                  Signin Status
     * @param status
     */
    public static void setSigninStatus(int status){
        SharedPreferences.Editor editor = shared.edit();
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_signin_status";
        editor.putInt(key, status);
        editor.commit();
    }
    public static int getSigninStatus(){
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_signin_status";
        return shared.getInt(key,0);
    }
    //----------------------------------------------------------------------------------------------
    //                                         GpsInterval
    //----------------------------------------------------------------------------------------------
    public static void setGPSInterval(int interval){
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("GPSInterval", interval);
        editor.commit();
    }
    public static int getGPSInterval(){
        return shared.getInt("GPSInterval", 60);
    }

    //----------------------------------------------------------------------------------------------
    //                                        Position
    //----------------------------------------------------------------------------------------------
    public static void setCurrentPosition(Position position){
        try{
            DBManaber.getAppDataTable().put("current_position", DBUtil.objectToString(position));
        }catch (Exception ex){}
    }
    public static Position getCurrentPosition(){
        Position position = (Position) DBUtil.stringToObject(DBManaber.getAppDataTable().get("current_position"));
        if(position==null){
            position = new Position(0.0f, 0.0f);
        }
        return position;
    }


}
