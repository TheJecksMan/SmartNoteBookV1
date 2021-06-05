package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.SeekBarPreference;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.panabey.smartnotebook.SettingsApp.SettingsFragment;
import com.panabey.smartnotebook.SettingsApp.SettingsFragmentGeneral;

public class Setting extends AppCompatActivity {
    private Toolbar toolbarSetting;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();

        toolbarSetting = findViewById(R.id.toolbarSettingBar);
        toolbarSetting.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.settings_container);
                if(fragment instanceof SettingsFragment)
                {
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
