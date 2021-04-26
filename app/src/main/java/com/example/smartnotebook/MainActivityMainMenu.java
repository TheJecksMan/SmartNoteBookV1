package com.example.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityMainMenu extends AppCompatActivity implements View.OnClickListener {

    Button btnActCreateReminders;
    Button btnActEditNoteBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main_menu);
        btnActCreateReminders = (Button) findViewById(R.id.buttonSelectReminders);
        btnActCreateReminders.setOnClickListener(this);

        btnActEditNoteBook = (Button) findViewById(R.id.buttonSelectEditText);
        btnActEditNoteBook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSelectReminders:
                Intent intent1 = new Intent(this, MainActivityReminders.class);
                startActivity(intent1);
                break;

            case R.id.buttonSelectEditText:
                Intent intent2 = new Intent(this, MainActivityNoteBook.class);
                startActivity(intent2);
                break;

            default:
                break;
        }
    }
}