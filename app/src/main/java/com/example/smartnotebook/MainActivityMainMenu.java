package com.example.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityMainMenu extends AppCompatActivity implements View.OnClickListener {

    private Button btnActCreateNotes;
    private Button btnActEditNoteBook;
    private Button btnActReminders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main_menu);

        btnActCreateNotes = (Button) findViewById(R.id.buttonSelectNotes);
        btnActCreateNotes.setOnClickListener(this);
        /*
        btnActEditNoteBook = (Button) findViewById(R.id.buttonSelectEditText);
        btnActEditNoteBook.setOnClickListener(this);

        btnActReminders = (Button) findViewById(R.id.buttonReminders);
        btnActReminders.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSelectNotes:
                Intent intent1 = new Intent(this, MainActivityNotes.class);
                startActivity(intent1);
                break;
            /*
            case R.id.buttonSelectEditText:
                Intent intent2 = new Intent(this, MainActivityNoteBook.class);
                startActivity(intent2);
                break;

            case R.id.buttonReminders:
                Intent intent3 = new Intent(this, MainActivityReminders.class);
                startActivity(intent3);
                break;*/
            default:
                break;
        }
    }


}