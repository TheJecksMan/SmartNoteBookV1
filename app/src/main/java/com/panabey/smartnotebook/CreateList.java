package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

public class CreateList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        Toolbar toolbarCreateList = findViewById(R.id.toolbarUpPanelList);

        toolbarCreateList.setNavigationOnClickListener(v -> {
            Intent intentBackMenu = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intentBackMenu);
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentBackList = new Intent(this, MainMenu.class);
        startActivity(intentBackList);
        finish();
    }
}