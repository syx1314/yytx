package com.trsoft.app.lib.utils;

import android.content.SharedPreferences;

import com.trsoft.app.lib.BaseApplication;

/**
 * Created by Adim on 2018/1/2.
 */

public class PreferenceUtils {
    private static SharedPreferences instance;
    private static PreferenceUtils utils;


    private PreferenceUtils() {
    }

    public static PreferenceUtils getInstance() {
        if (utils == null) {
            synchronized (PreferenceUtils.class) {
                instance = BaseApplication.mContext.getSharedPreferences("yytx", 0);
                utils = new PreferenceUtils();
                return utils;
            }
        }
        return utils;
    }

    public void saveData(String key, Object object) {
        SharedPreferences.Editor edit = instance.edit();
        if (object instanceof String) {
            edit.putString(key, object + "");
        } else if (object instanceof Integer) {
            edit.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            edit.putBoolean(key, (Boolean) object);
        }
        edit.commit();
    }

    public String getString(String key) {
        return instance.getString(key, "");
    }

    public int getInt(String key) {
        return instance.getInt(key, 0);
    }

    public double getDouble(String key) {
        return instance.getLong(key, 0);
    }

    public boolean getBoolean(String key) {
        return instance.getBoolean(key, false);
    }

    public void clear() {
        instance.edit().clear().commit();
    }


}
