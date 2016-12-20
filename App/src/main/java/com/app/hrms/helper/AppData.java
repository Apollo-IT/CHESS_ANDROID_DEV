package com.app.hrms.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.hrms.model.ContactInfo;
import com.app.hrms.utils.db.DBManaber;
import com.app.hrms.utils.db.DBUtil;
import com.app.hrms.utils.db.HashTableDb;
import com.app.hrms.utils.gps.Position;

import java.sql.Date;

public class AppData {
    private static String PREFS_NAME = "app_data";
    private static Context context;
    private static SharedPreferences shared;

    public static ContactInfo contactInfo = new ContactInfo();
    /***********************************************************************************************
     *                                      Init App Data
     * @param context
     */
    public static void init(Context context){
        AppData.context = context;
        shared = context.getSharedPreferences(PREFS_NAME, 0);
    }
    /***********************************************************************************************
     *                                     Member ID
     */
    public static void setMemberID(String memberID){
        HashTableDb table = DBManaber.getAppDataTable();
        table.put("current_member_id",memberID);
    }
    public static String getMemberID(){
        HashTableDb table = DBManaber.getAppDataTable();
        String value = table.get("current_member_id");
        if(value==null) value="";
        return value;
    }
    /***********************************************************************************************
     *                                     Signin Time In
     * @param timeIn
     */
    public static void setTimeIn(long timeIn, String location){
        SharedPreferences.Editor editor = shared.edit();
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_timein_"+getMemberID();
        editor.putLong(key, timeIn);
        editor.putString(today+"_timein_location_"+getMemberID(), location);
        editor.commit();
    }
    public static Long getTimeIn(){
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_timein_"+getMemberID();
        return shared.getLong(key,0);
    }
    public static String getTimeinLocation(){
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_timein_location_"+getMemberID();
        return shared.getString(key,"");
    }
    /***********************************************************************************************
     *                                     Signin Time Out
     * @param timeOut
     */
    public static void setTimeOut(Long timeOut, String location){
        SharedPreferences.Editor editor = shared.edit();
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_timeout_" + getMemberID();
        editor.putLong(key, timeOut);
        editor.putString(today+"_timeout_location_" + getMemberID(), location);
        editor.commit();
    }
    public static Long getTimeOut(){
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_timeout_" + getMemberID();
        return shared.getLong(key,0);
    }
    public static String getTimeoutLocation(){
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_timeout_location_" + getMemberID();
        return shared.getString(key,"");
    }
    /***********************************************************************************************
     *                                  Signin Status
     * @param status
     */
    public static void setSigninStatus(int status){
        SharedPreferences.Editor editor = shared.edit();
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_signin_status_" + getMemberID();
        editor.putInt(key, status);
        editor.commit();
    }
    public static int getSigninStatus(){
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_signin_status_" + getMemberID();
        return shared.getInt(key,0);
    }
    /***********************************************************************************************
     *                                  Appeal Status
     * @param in_out : "in", "out"
     */
    public static void setAppealStatus(String in_out){
        SharedPreferences.Editor editor = shared.edit();
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_appeal_"+in_out;
        editor.putBoolean(key, true);
        editor.commit();
    }
    public static boolean isAppealed(String in_out){
        String today = new Date(System.currentTimeMillis()).toString();
        String key = today + "_appeal_"+in_out;
        return shared.getBoolean(key,false);
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
        return shared.getInt("GPSInterval", 10);
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
    /***********************************************************************************************
     *                                  Push Sound
     */
    public static void setPushSoundEnable(boolean enable){
        String key = "push_sound_enable";
        HashTableDb table = DBManaber.getAppDataTable();
        table.put(key,enable?"true":"false");
    }
    public static boolean isPushSoundEnabled(){
        String key = "push_sound_enable";
        HashTableDb table = DBManaber.getAppDataTable();
        String value = table.get(key);
        if(value==null)return false;
        return value.equals("true");
    }
    /***********************************************************************************************
     *                                  Push Vibration
     */
    public static void setPushVibrationEnable(boolean enable){
        String key = "push_vibration_enable";
        HashTableDb table = DBManaber.getAppDataTable();
        table.put(key,enable?"true":"false");
    }
    public static boolean isPushVibrationEnabled(){
        String key = "push_vibration_enable";
        HashTableDb table = DBManaber.getAppDataTable();
        String value = table.get(key);
        if(value==null)return false;
        return value.equals("true");
    }


}
