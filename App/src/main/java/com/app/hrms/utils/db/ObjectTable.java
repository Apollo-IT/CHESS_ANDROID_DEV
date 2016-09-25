package com.app.hrms.utils.db;

import java.io.Serializable;

public class ObjectTable {
    HashTableDb table;
    public ObjectTable(HashTableDb table) {
        this.table = table;
    }
    public void put(String key, Serializable obj){
        try {
            String objString = DBUtil.objectToString(obj);
            table.put(key, objString);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public Object get(String key){
        String objString = table.get(key);
        try{
            return DBUtil.stringToObject(objString);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
