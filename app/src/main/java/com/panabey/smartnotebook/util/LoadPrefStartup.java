package com.panabey.smartnotebook.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * Класс для загрузки настроек при запуске
 */
public class LoadPrefStartup {
    Boolean ChangeDarkTheme;
    Boolean ChangeAnim;

    final Context context;
    final Activity activity;

    SharedPreferences Pref;

    public LoadPrefStartup(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    /**
     * Загрузка параметров тёмной темы из файла xml.
     * Исползуется для запуска сохранённых настоек после перезапуска приложения.
     */
    public void DarkThemeLoadPref(){
        Pref = activity.getSharedPreferences("com.panabey.smartnotebook_preferences", Context.MODE_PRIVATE);
        ChangeDarkTheme = Pref.getBoolean("key_switch_DarkTheme", false);

        if (!ChangeDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    /**
     * Включение и отключения анимации во всём приложении.
     * (Изменения применяются только при следующей загрузки приложения)
     */
    public boolean AnimLoadPref(){
        return ChangeAnim = Pref.getBoolean("key_preference_animate", false);
    }
}
