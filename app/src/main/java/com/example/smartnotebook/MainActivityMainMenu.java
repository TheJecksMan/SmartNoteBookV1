package com.example.smartnotebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivityMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main_menu);
        // badgeDrawable =  bottomNavigation.getBadge(menuItemId)

        BottomNavigationView BottomNavigationMenu = findViewById(R.id.BottomNavigationMenu);
        BottomNavigationMenu.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPager, new notes_fg()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.ItemNotes:
                    selectedFragment = new notes_fg();
                    break;
                case R.id.ItemReminders:
                    selectedFragment = new reminders_fg();
                    break;
                case R.id.ItemFolders:
                    selectedFragment = new folders_fg();
                    break;
                case R.id.ItemAccount:
                    selectedFragment = new account_fg();
                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentPager, selectedFragment)
                    .commit();
            return true;
        }
    };

}

