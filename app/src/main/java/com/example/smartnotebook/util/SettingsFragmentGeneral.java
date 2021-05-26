package com.example.smartnotebook.util;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.smartnotebook.R;

public class SettingsFragmentGeneral extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting_general, rootKey);
    }
}