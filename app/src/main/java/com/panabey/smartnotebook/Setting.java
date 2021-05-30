package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.panabey.smartnotebook.SettingsApp.SettingsFragment;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();
    }

}
