package com.panabey.smartnotebook.SettingsApp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.panabey.smartnotebook.MainMenu;
import com.panabey.smartnotebook.R;

public class SettingsFragmentGeneral extends PreferenceFragmentCompat {

    private SwitchPreference darkTheme;
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