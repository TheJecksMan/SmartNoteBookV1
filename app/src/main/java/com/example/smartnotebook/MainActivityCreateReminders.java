package com.example.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivityCreateReminders extends AppCompatActivity implements View.OnClickListener{

    Button btnActBackReminders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_create_reminders);
        btnActBackReminders = (Button) findViewById(R.id.buttonBackReminders);
        btnActBackReminders.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonBackReminders:
                Intent intent = new Intent(this, MainActivityReminders.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}