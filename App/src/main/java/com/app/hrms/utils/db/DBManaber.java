package com.app.hrms.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManaber {
    //------------------------------ constants
    private static final String DB_NAME = "db";
    private static final String APP_DATA_TABLE_NAME = "appdata";
    //------------------------------ local values
    private static Context appContext;
    private static SQLiteDatabase db;
    private static HashTableDb appDataTable;

    //------------------------------ init db
    public static void init(Context context){
        appContext = context;
    }
    //------------------------------ connect db
    public static SQLiteDatabase getDb(){
        if(db==null){
            db = appContext.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        }
        return db;
    }
    //------------------------------- get app data table
    public static HashTableDb getAppDataTable(){
        if(appDataTable == null){
            appDataTable = new HashTableDb(APP_DATA_TABLE_NAME);
        }
        return appDataTable;
    }
}
