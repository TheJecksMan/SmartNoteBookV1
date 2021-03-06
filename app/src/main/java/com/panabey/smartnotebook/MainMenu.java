package com.panabey.smartnotebook;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        drawerLayout =  findViewById(R.id.MainDrawerLayout);
        NavigationView navigationView = findViewById(R.id.dopMenuLeft);

        BottomNavigationView bottomNavigationMenu = findViewById(R.id.BottomNavigationMenu);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPager, new notes_fg()).commit();
        bottomNavigationMenu.setOnItemSelectedListener(navListener);

        //----------------------------NavigationView-------------------------------------//
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbarMenuMainBar);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }


    @Override
    public final boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.TrashItem:
                Intent intentTrash = new Intent(this, Trash.class);
                startActivity(intentTrash);
                break;
            case R.id.SettingItem:
                Intent intentSetting = new Intent(this, Setting.class);
                startActivity(intentSetting);
                break;
            case R.id.InfoItem:
                Intent intentInfo = new Intent(MainMenu.this, Info.class);
                startActivity(intentInfo);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //---------------------------------------???????????? ????????????-----------------------------------//
    private final BottomNavigationView.OnItemSelectedListener navListener = item -> {

        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.ItemNotes:
                selectedFragment = new notes_fg();
                break;
            case R.id.ItemFolders:
                selectedFragment = new folders_fg();
                break;
            case R.id.ItemAccount:
                    selectedFragment = new accountLogin_fg();
                break;
        }
        assert selectedFragment != null;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentPager, selectedFragment)
                .commit();
        return true;
    };

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
            finish();
        }
    }
}

