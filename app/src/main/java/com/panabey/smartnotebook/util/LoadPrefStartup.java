package com.panabey.smartnotebook.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * Класс для загрузки настроек при запуске
 */
public class LoadPrefStartup {

    final Activity activity;

    final SharedPreferences Preferences;

    public LoadPrefStartup( Activity activity) {
        this.activity = activity;
        Preferences = activity.getSharedPreferences("com.panabey.smartnotebook_preferences", Context.MODE_PRIVATE);
    }

    /**
     * Загрузка параметров тёмной темы из файла xml.
     * Исползуется для запуска сохранённых настоек после перезапуска приложения.
     */
    public void DarkThemeLoadPref(){
        boolean ChangeDarkTheme = Preferences.getBoolean("key_switch_DarkTheme", false);

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
    public void  AnimLoadPref(){
        boolean ChangeAnim = Preferences.getBoolean("key_preference_animate", false);
    }

    /**
     * Определеям первый запуск приложения
     */
    public void FirstStartupApp(){
        boolean isFirstStartup = Preferences.getBoolean("isFirstStartup", false);
        if (!isFirstStartup) {
            SharedPreferences.Editor edit = Preferences.edit();
            edit.putBoolean("isFirstStartup", true);
            edit.commit();
        }
    }
}
