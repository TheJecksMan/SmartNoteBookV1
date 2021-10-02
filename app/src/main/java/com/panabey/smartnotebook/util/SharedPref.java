package com.panabey.smartnotebook.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref extends Activity {
    public static final String APP_PREFERENCES = "SettingsApp";

    public void SaveSetting(String key_value, boolean value){
        SharedPreferences SettingsGeneral;
        SettingsGeneral = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = SettingsGeneral.edit();
        editor.putBoolean(key_value, value);
        editor.apply();
    }

    public boolean LoadSetting(String key_value){

        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean(key_value, true);
        return value;
    }
}
