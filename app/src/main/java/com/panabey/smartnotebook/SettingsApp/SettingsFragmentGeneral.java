package com.panabey.smartnotebook.SettingsApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.panabey.smartnotebook.MainMenu;
import com.panabey.smartnotebook.R;
import com.panabey.smartnotebook.Setting;
import com.panabey.smartnotebook.util.SharedPref;

public class SettingsFragmentGeneral extends PreferenceFragmentCompat {

    SwitchPreference darkTheme;

    public static final String APP_PREFERENCES = "SettingsApp";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting_general, rootKey);
        darkTheme = findPreference("key_switch_DarkTheme");

    }

    @Override
    public void onResume() {
        super.onResume();
        darkTheme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (darkTheme.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                return true;
            }
        });
    }
}