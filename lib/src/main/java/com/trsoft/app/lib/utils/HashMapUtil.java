package com.trsoft.app.lib.utils;

import java.util.HashMap;
import java.util.Map;



public class HashMapUtil {
    public static Map<String,Object> createMap(String key, String value ){
        Map<String,Object> par=new HashMap<String,Object>();
        par.put(key,value);
        return par;
    }

    public static Map<String,Object> createMap(String key, String value, String key1, String value1, String key2, String value2 ){
        Map<String,Object> par=new HashMap<String,Object>();
        par.put(key,value);
        par.put(key1,value1);
        par.put(key2,value2);
        return par;
    }
    public static Map<String,Object> createMap(String key, String value, String key1, String value1){
        Map<String,Object> par=new HashMap<String,Object>();
        par.put(key,value);
        par.put(key1,value1);
        return par;
    }

    public static Map<String,String> createMapStr(String key, String value ){
        Map<String,String> par=new HashMap<String,String>();
        par.put(key,value);
        return par;
    }

    public static Map<String,String> createMapStr(String key, String value, String key1, String value1, String key2, String value2 ){
        Map<String,String> par=new HashMap<String,String>();
        par.put(key,value);
        par.put(key1,value1);
        par.put(key2,value2);
        return par;
    }
    public static Map<String,String> createMapStr(String key, String value, String key1, String value1){
        Map<String,String> par=new HashMap<String,String>();
        par.put(key,value);
        par.put(key1,value1);
        return par;
    }
}
