package com.app.hrms.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppCookie {

    private static AppCookie instance = null;
    private MemberModel member = new MemberModel();
    private boolean isLogin = false;

    public static AppCookie getInstance() {
        if (instance == null) {
            instance = new AppCookie();
        }
        return instance;
    }

    public void setLogin(boolean bLogin) {
        isLogin = bLogin;
    }
    public boolean isLogin() {
        return isLogin;
    }

    public String getUsername(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String username = settings.getString("userid", null);
        return username;
    }

    public String getPassword(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String password = settings.getString("password", null);
        return password;
    }

    public boolean canAutologin(Context context) {
        if (getUsername(context) == null || "".equals(getUsername(context)))
            return false;
        else
            return true;
    }

    public void saveUserCredentials(Context context, String username, String password) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userid", username);
        editor.putString("password", password);
        editor.commit();
    }
    public void doLogout(Context context){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("userid");
        editor.remove("password");
        editor.commit();
        isLogin = false;
    }
    public MemberModel getCurrentUser() {
        return member;
    }
    public void setCurrentMember(MemberModel member) {
        this.member = member;
    }
}
