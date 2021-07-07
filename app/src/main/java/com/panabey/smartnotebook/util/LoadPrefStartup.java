package com.panabey.smartnotebook.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;


public class LoadPrefStartup {
    Boolean ChangeDarkTheme;
    Boolean ChangeAnim;

    Context context;
    Activity activity;

    SharedPreferences Pref;

    public LoadPrefStartup(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void DarkThemeLoadPref(){
        Pref = activity.getSharedPreferences("com.panabey.smartnotebook_preferences", Context.MODE_PRIVATE);
        ChangeDarkTheme = Pref.getBoolean("key_switch_DarkTheme", false);

        if (ChangeDarkTheme != true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    public boolean AnimLoadPref(){
        return ChangeAnim = Pref.getBoolean("key_preference_animate", false);
    }

}
