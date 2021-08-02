package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.panabey.smartnotebook.SettingsApp.SettingsFragment;

public class Setting extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();

        Toolbar toolbarSetting = findViewById(R.id.toolbarSettingBar);
        toolbarSetting.setNavigationOnClickListener(v -> {
            fragmentManager.popBackStack();
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.settings_container);
            if(fragment instanceof SettingsFragment)
            {
                finish();
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
