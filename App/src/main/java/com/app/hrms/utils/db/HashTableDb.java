package com.app.hrms.utils.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HashTableDb {
    private String tableName = "";
    private SQLiteDatabase db;

    public HashTableDb(String tableName){
        this.tableName = tableName;
        try{
            db = DBManaber.getDb();
            createTable();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    //-------------- put value
    public void put(String key, String value){
        if(select(key) == null){
            insert(key, value);
        }else{
            update(key, value);
        }
    }
    //-------------- get value
    public String get(String key){
        String value = select(key);
        return value;
    }
    //------------------------- Create table
    private void createTable(){
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName +
                            "(key TEXT PRIMARY KEY NOT NULL,"
                            + "value TEXT);"
            );
        }catch(Exception ex){
            ex.printStackTrace();
        };
    }
    //------------------------- Insert
    private void insert(String key, String value){
        try {
            ContentValues values = new ContentValues();
            values.put("key", key);
            values.put("value", value);
            db.insert(tableName, null, values);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    //------------------------ Update
    private void update(String key, String value){
        try{
            ContentValues values = new ContentValues();
            values.put("key", key);
            values.put("value", value);
            db.update(tableName, values, "key = ?",new String[]{ key });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    //------------------------- select
    private String select(String key){
        try{
            Cursor cursor = db.rawQuery("SELECT value FROM " + tableName + " WHERE key='"+key+"'", null);
            if(cursor.moveToFirst()){
                return cursor.getString(0);
            }else{
                return null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
