package com.panabey.smartnotebook.SettingsApp;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.panabey.smartnotebook.R;

public class SettingNotification extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_notification);
    }
}
