package com.panabey.smartnotebook.util;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.panabey.smartnotebook.R;


public class SettingsFragmentAbout extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting_about, rootKey);
    }
}
