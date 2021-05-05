package com.example.smartnotebook;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;



public class MainActivityMainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Button bntDopMenuLeft;
    BottomNavigationView BottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main_menu);

        drawerLayout =  findViewById(R.id.MainDrawerLayout);
        bntDopMenuLeft = findViewById(R.id.buttonCallDopMenu);
        navigationView = findViewById(R.id.dopMenuLeft);

        BottomNavigationMenu = findViewById(R.id.BottomNavigationMenu);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPager, new notes_fg()).commit();

        BottomNavigationMenu.setOnNavigationItemSelectedListener(navListener);

        //----------------------------NavigationView-------------------------------------//
        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        bntDopMenuLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.editTextItem:
                Intent intentEditText = new Intent(this, EditText.class);
                startActivity(intentEditText);
                break;
            case R.id.TrashItem:
                Intent intentTrash = new Intent(this, Trash.class);
                startActivity(intentTrash);
                break;
            case R.id.SettingItem:
                Intent intentSetting = new Intent(this, Setting.class);
                startActivity(intentSetting);
                break;
            case R.id.InfoItem:
                Intent intentInfo = new Intent(this, Info.class);
                startActivity(intentInfo);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }

    //---------------------------------------Нижняя панель-----------------------------------//
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

